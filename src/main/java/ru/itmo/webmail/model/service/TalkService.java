package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.Event;
import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.repository.EventRepository;
import ru.itmo.webmail.model.repository.TalkRepository;
import ru.itmo.webmail.model.repository.impl.EventRepositoryImpl;
import ru.itmo.webmail.model.repository.impl.TalkRepositoryImpl;

import java.util.List;

public class TalkService {

    private TalkRepository talkRepository = new TalkRepositoryImpl();

    public void saveTalk(Talk talk) {
        talkRepository.sendMessage(talk);
    }

    public List<Talk> findAll(Long userId) {
        return talkRepository.findAll(userId);
    }

}
