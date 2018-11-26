package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.database.DatabaseUtils;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.EmailConfirmationRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.UUID;

public class EmailConfirmationRepositoryImpl implements EmailConfirmationRepository {
    private static final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public void createEmailConfirmation(User user) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO EmailConfirmation (userId, secret, creationTime) VALUES (?, ?, NOW())",
                    Statement.RETURN_GENERATED_KEYS)) {
                String secret = UUID.randomUUID().toString().replace("-", "");
                secret = secret.substring(0, 8) + user.getLogin();
                statement.setString(1, Long.toString(user.getId()));
                statement.setString(2, secret);
                if (statement.executeUpdate() == 1) {
                    ResultSet generatedIdResultSet = statement.getGeneratedKeys();
                    if (generatedIdResultSet.next()) {
                        user.setId(generatedIdResultSet.getLong(1));
                    } else {
                        throw new RepositoryException("Can't find id of saved User.");
                    }
                } else {
                    throw new RepositoryException("Can't save User.");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save Event.", e);
        }
    }

    @Override
    public boolean confirm(String sec) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE User INNER JOIN EmailConfirmation ON (User.id = EmailConfirmation.userId AND EmailConfirmation.secret = ?) SET confirmed = 1 ")) {
                if (sec != null && !sec.isEmpty()) {
                    statement.setString(1, sec);
                } else {
                    return false;
                }
                int count = statement.executeUpdate();
                try (PreparedStatement statement1 = connection.prepareStatement(
                        "DELETE FROM EmailConfirmation WHERE secret = ?")) {
                    statement1.setString(1, sec);
                    statement1.executeUpdate();
                }
                return count > 0;
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't confirm email", e);
        }
    }
}
