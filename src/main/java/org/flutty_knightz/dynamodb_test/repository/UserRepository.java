package org.flutty_knightz.dynamodb_test.repository;

import org.flutty_knightz.dynamodb_test.entity.User;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class UserRepository {

    private final DynamoDbTable<User> userTable;
    private final DynamoDbIndex<User> userGsi;

    public UserRepository(DynamoDbEnhancedClient enhancedClient) {
        // "Users" must match your table name in DynamoDB
//        TableSchema<User> userTableSchema = TableSchema.builder(User.class)
//                                                       .newItemSupplier(User::new)
//                                                       .addAttribute(Long.class, a -> a.name("user_id")
//                                                                                       .getter(User::getUserId)
//                                                                                       .setter(User::setUserId)
//                                                                                       .tags(StaticAttributeTags.primaryPartitionKey()))
//                                                       .addAttribute(String.class, a -> a.name("created_date")
//                                                                                         .getter(User::getCreatedDate)
//                                                                                         .setter(User::setCreatedDate)
//                                                                                         .tags(StaticAttributeTags.primarySortKey()))
//                                                       .addAttribute(String.class, a -> a.name("name")
//                                                                                         .getter(User::getName)
//                                                                                         .setter(User::setName))
//                                                       .addAttribute(String.class, a -> a.name("email")
//                                                                                         .getter(User::getEmail)
//                                                                                         .setter(User::setEmail))
//                                                       .build();
//        this.userTable = enhancedClient.table("users", userTableSchema);

        this.userTable = enhancedClient.table("users",
                TableSchema.fromBean(User.class));
        this.userGsi = this.userTable.index("name_user_id_index");
    }

    public void save(User user) {
        userTable.putItem(user);
    }

    public User getById(int id, String createdDate) {
        return userTable.getItem(Key.builder().partitionValue(id).sortValue(createdDate).build());
    }

    public void update(User user) {
        // For DynamoDB, a putItem will replace the item. Adjust as needed.
        userTable.putItem(user);
    }

    public void delete(int id, String createdDate) {
        userTable.deleteItem(Key.builder().partitionValue(id).sortValue(createdDate).build());
    }

    // ---------- Query Example (Using a Secondary Index) ----------

    public List<User> queryByName(int id, String name) {

        QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
                                                                           .partitionValue(name)
                                                                           .sortValue(id)
                                                                           .build());

        Iterator<Page<User>> results = userGsi.query(r -> r.queryConditional(queryConditional)).iterator();
        List<User> users = new ArrayList<>();
        results.forEachRemaining(page -> users.addAll(page.items()));
        return users;
    }

    // ---------- Scan Example (Standard style) ----------

    public List<User> scanAll() {
        Iterator<Page<User>> results = userTable.scan().iterator();
        List<User> users = new ArrayList<>();
        results.forEachRemaining(page -> users.addAll(page.items()));
        return users;
    }

    // ---------- Lambda-Style Request (Scan with a Filter Expression) ----------

    public List<User> scanByName(String name, String email) {
        Expression filterExpression = Expression.builder()
                                                .expression("#n = :name and email = :email")
//                                                dont use 'name' directly because dynamodb use certain keyword as reserved word like name, year, order, so it throw error that why use #n and replace with name
                                                .putExpressionName("#n",
                                                        "name")
                                                .putExpressionValue(":name",
                                                        AttributeValue.builder().s(name).build())
                                                .putExpressionValue(":email",
                                                        AttributeValue.builder().s(email).build())
                                                .build();

        Iterator<Page<User>> results = userTable.scan(r -> r.filterExpression(filterExpression)).iterator();
        List<User> users = new ArrayList<>();
        results.forEachRemaining(page -> users.addAll(page.items()));
        return users;
    }
}
