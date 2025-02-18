package org.flutty_knightz.dynamodb_test.service_implement;

import org.flutty_knightz.dynamodb_test.entity.User;
import org.flutty_knightz.dynamodb_test.service.UserService;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags;

import java.util.List;

@Service
public class UserServiceImplement implements UserService {

    private final DynamoDbTable<User> userTable;

    public UserServiceImplement(DynamoDbEnhancedClient enhancedClient) {
        // "Users" must match your table name in DynamoDB
        TableSchema<User> userTableSchema = TableSchema.builder(User.class).newItemSupplier(User::new).addAttribute(Long.class, a -> a.name("user_id").getter(User::getUserId).setter(User::setUserId).tags(StaticAttributeTags.primaryPartitionKey())).addAttribute(String.class, a -> a.name("created_date").getter(User::getCreatedDate).setter(User::setCreatedDate).tags(StaticAttributeTags.primarySortKey())).addAttribute(String.class, a -> a.name("name").getter(User::getName).setter(User::setName)).addAttribute(String.class, a -> a.name("email").getter(User::getEmail).setter(User::setEmail)).build();

        this.userTable = enhancedClient.table("users", userTableSchema);
    }


    @Override
    public void createUser(User user) {
        userTable.putItem(user);
    }

    @Override
    public User getUser(int userId, String createdDate) {
        Key key = Key.builder().partitionValue(userId).sortValue(createdDate).build();
        return userTable.getItem(key);
    }

    @Override
    public void updateUser(User user) {
        userTable.updateItem(user);
    }

    @Override
    public void deleteUser(int userId, String createdDate) {
        Key key = Key.builder().partitionValue(userId).sortValue(createdDate).build();
        userTable.deleteItem(key);
    }

    @Override
    public List<User> getAllUsers() {
        return userTable.scan().items().stream().toList();
    }
}
