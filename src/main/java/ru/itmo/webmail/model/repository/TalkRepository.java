package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.service.UserService;

import java.util.List;

public interface TalkRepository {
    void sendMessage(Talk talk);
    List<Talk> findAll(Long userId);
}
