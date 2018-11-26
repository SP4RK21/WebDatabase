package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.Event;
import ru.itmo.webmail.model.domain.User;

import java.util.List;

public interface EventRepository {
    void createEvent(Event event);
}
