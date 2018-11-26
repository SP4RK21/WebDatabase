package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.EmailConfirmation;
import ru.itmo.webmail.model.domain.Event;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.repository.EmailConfirmationRepository;
import ru.itmo.webmail.model.repository.EventRepository;
import ru.itmo.webmail.model.repository.impl.EmailConfirmationRepositoryImpl;
import ru.itmo.webmail.model.repository.impl.EventRepositoryImpl;

public class EmailConfirmationService {

    private EmailConfirmationRepository emailConfirmationRepository = new EmailConfirmationRepositoryImpl();

    public void emailConfirmationAdd(User user) {
        emailConfirmationRepository.createEmailConfirmation(user);
    }

    public boolean confirm(String secret) {
        return emailConfirmationRepository.confirm(secret);
    }
}
