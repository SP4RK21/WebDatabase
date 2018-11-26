package ru.itmo.webmail.model.service;

import com.google.common.hash.Hashing;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.repository.UserRepository;
import ru.itmo.webmail.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class UserService {
    private static final String USER_PASSWORD_SALT = "dc3475f2b301851b";

    private UserRepository userRepository = new UserRepositoryImpl();

    public void validateRegistration(User user, String password) throws ValidationException {
        if (!(user.getEmail().indexOf('@') >= 0 && user.getEmail().indexOf('@') == user.getEmail().lastIndexOf('@'))) {
            throw new ValidationException("Not valid email");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new ValidationException("Login is required");
        }
        if (!user.getLogin().matches("[a-z]+")) {
            throw new ValidationException("Login can contain only lowercase Latin letters");
        }
        if (user.getLogin().length() > 8) {
            throw new ValidationException("Login can't be longer than 8");
        }
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new ValidationException("Login is already in use");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new ValidationException("Email is already used");
        }
        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4");
        }
        if (password.length() > 32) {
            throw new ValidationException("Password can't be longer than 32");
        }
    }

    public void register(User user, String password) {
        String passwordSha = getPasswordSha(password);
        userRepository.save(user, passwordSha);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }


    public void validateReciever(String login) throws ValidationException {
        if (userRepository.findByLogin(login) == null) {
            throw new ValidationException("No user with that login");
        }
    }

    public void validateEnter(String authData, String password) throws ValidationException {
        if (authData == null || authData.isEmpty()) {
            throw new ValidationException("Login or email is required");
        }
        if (authData.contains("@")) {
            if (!(authData.indexOf('@') >= 0 && authData.indexOf('@') == authData.lastIndexOf('@'))) {
                throw new ValidationException("Not valid email");
            }
        } else {
            if (!authData.matches("[a-z]+")) {
                throw new ValidationException("Login can contain only lowercase Latin letters");
            }
            if (authData.length() > 8) {
                throw new ValidationException("Login can't be longer than 8");
            }
        }
        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4");
        }
        if (password.length() > 32) {
            throw new ValidationException("Password can't be longer than 32");
        }
        User user = userRepository.findByLoginOrEmailAndPasswordSha(authData, getPasswordSha(password));
        if (user == null) {
            throw new ValidationException("User with such login and email is not found or password is wrong");
        }
        if(!userRepository.checkConfirmation(user)) {
            throw new ValidationException("Email is not confirmed");
        }
    }

    private String getPasswordSha(String password) {
        return Hashing.sha256().hashString(USER_PASSWORD_SALT + password,
                StandardCharsets.UTF_8).toString();
    }

    public User authorize(String authData, String password) {
        return userRepository.findByLoginOrEmailAndPasswordSha(authData, getPasswordSha(password));
    }

    public User find(long userId) {
        return userRepository.find(userId);
    }

    public User find(String login) {
        return userRepository.findByLogin(login);
    }
}
