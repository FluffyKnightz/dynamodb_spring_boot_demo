package org.flutty_knightz.dynamodb_test.service;

import org.flutty_knightz.dynamodb_test.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void createUser(User user);

    Optional<User> getUser(int userId, String createdDate);

    void updateUser(User user);

    void deleteUser(int userId, String createdDate);

    List<User> getAllUsers();

    Optional<User> getUserByName(int id, String name);
}
