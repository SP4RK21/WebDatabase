package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.Event;
import ru.itmo.webmail.model.domain.User;

import java.sql.SQLException;

public interface EmailConfirmationRepository  {
    void createEmailConfirmation(User user);
    boolean confirm (String secret);
}
