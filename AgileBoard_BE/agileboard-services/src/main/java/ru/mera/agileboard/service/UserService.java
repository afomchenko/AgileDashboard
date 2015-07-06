package ru.mera.agileboard.service;

import ru.mera.agileboard.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by antfom on 10.02.2015.
 */
public interface UserService {

    Optional<User> findUserByName(String name);

    Optional<User> findUserByID(int id);

    List<User> getAllUsers();

    User createUser(String name, String email);


}
