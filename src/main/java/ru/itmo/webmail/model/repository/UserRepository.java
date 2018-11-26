package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.User;

import java.util.List;

public interface UserRepository {
    User find(long userId);
    User findByLogin(String login);
    User findByEmail(String email);
    boolean checkConfirmation(User user);
    User findByLoginOrEmailAndPasswordSha(String authData, String passwordSha);
    List<User> findAll();
    void save(User user, String passwordSha);
}
