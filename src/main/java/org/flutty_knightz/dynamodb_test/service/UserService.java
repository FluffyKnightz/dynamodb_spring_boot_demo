package org.flutty_knightz.dynamodb_test.service;

import org.flutty_knightz.dynamodb_test.entity.User;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

import java.util.Iterator;
import java.util.List;

public interface UserService {

    void createUser(User user);

    User getUser(int userId, String createdDate);

    void updateUser(User user);

    void deleteUser(int userId, String createdDate);

    List<User> getAllUsers();

    List<User> getUserByName(String name);
}
