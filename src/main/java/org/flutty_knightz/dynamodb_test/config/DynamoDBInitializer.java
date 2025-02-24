package org.flutty_knightz.dynamodb_test.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

@Component
public class DynamoDBInitializer {
    private final DynamoDbClient dynamoDbClient;
    private static final String USERS_TABLE = "users";
    private static final String ORDERS_TABLE = "orders";

    public DynamoDBInitializer(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    @PostConstruct
    public void initializeTables() {
        createUsersTable();
        createOrdersTableWithGSI();
    }

    private void createUsersTable() {
        CreateTableRequest request = CreateTableRequest.builder()
                                                       .tableName(USERS_TABLE)
                                                       .keySchema(KeySchemaElement.builder()
                                                                                  .attributeName("user_id")
                                                                                  .keyType(KeyType.HASH)
                                                                                  .build(),
                                                               KeySchemaElement.builder()
                                                                               .attributeName("created_date")
                                                                               .keyType(KeyType.RANGE)
                                                                               .build())
                                                       .provisionedThroughput(builder -> {
                                                           builder.writeCapacityUnits(5L);
                                                           builder.readCapacityUnits(5L);
                                                       })
                                                       .attributeDefinitions(AttributeDefinition.builder()
                                                                                                .attributeName("user_id")
                                                                                                .attributeType(ScalarAttributeType.N)
                                                                                                .build(),
                                                               AttributeDefinition.builder()
                                                                                  .attributeName("created_date")
                                                                                  .attributeType(ScalarAttributeType.S)
                                                                                  .build(),
                                                               AttributeDefinition.builder()
                                                                                  .attributeName("name")
                                                                                  .attributeType(ScalarAttributeType.S)
                                                                                  .build())
                                                       .globalSecondaryIndexes(GlobalSecondaryIndex.builder()
                                                                                                   .indexName("name_user_id_index")
                                                                                                   .keySchema(KeySchemaElement.builder()
                                                                                                                              .attributeName("name")
                                                                                                                              .keyType(KeyType.HASH)
                                                                                                                              .build(),
                                                                                                           KeySchemaElement.builder()
                                                                                                                           .attributeName("user_id")
                                                                                                                           .keyType(KeyType.RANGE)
                                                                                                                           .build())
                                                                                                   .projection(builder -> builder.projectionType(ProjectionType.ALL))
                                                                                                   .provisionedThroughput(builder -> {
                                                                                                       builder.writeCapacityUnits(5L);
                                                                                                       builder.readCapacityUnits(5L);
                                                                                                   })
                                                                                                   .build())

                                                       .build();
        try {
            dynamoDbClient.createTable(request);
            System.out.println("Users table created successfully.");
        } catch (ResourceInUseException e) {
            System.out.println("Users table already exists.");
        }
    }

    private void createOrdersTableWithGSI() {
        CreateTableRequest request = CreateTableRequest.builder()
                                                       .tableName(ORDERS_TABLE)
                                                       .keySchema(KeySchemaElement.builder()
                                                                                  .attributeName("order_id")
                                                                                  .keyType(KeyType.HASH)
                                                                                  .build(),
                                                               KeySchemaElement.builder()
                                                                               .attributeName("created_date")
                                                                               .keyType(KeyType.RANGE)
                                                                               .build())
                                                       .attributeDefinitions(AttributeDefinition.builder()
                                                                                                .attributeName("order_id")
                                                                                                .attributeType(ScalarAttributeType.N)
                                                                                                .build(),
                                                               AttributeDefinition.builder()
                                                                                  .attributeName("user_id")
                                                                                  .attributeType(ScalarAttributeType.N)
                                                                                  .build(),
                                                               AttributeDefinition.builder()
                                                                                  .attributeName("created_date")
                                                                                  .attributeType(ScalarAttributeType.S)
                                                                                  .build())
                                                       .globalSecondaryIndexes(GlobalSecondaryIndex.builder()
                                                                                                   .indexName("user_id_index")
                                                                                                   .keySchema(KeySchemaElement.builder()
                                                                                                                              .attributeName("user_id")
                                                                                                                              .keyType(KeyType.HASH)
                                                                                                                              .build())
                                                                                                   .projection(builder -> builder.projectionType(ProjectionType.ALL))
                                                                                                   .provisionedThroughput(builder -> {
                                                                                                       builder.writeCapacityUnits(5L);
                                                                                                       builder.readCapacityUnits(5L);
                                                                                                   })
                                                                                                   .build())
                                                       .provisionedThroughput(builder -> {
                                                           builder.writeCapacityUnits(5L);
                                                           builder.readCapacityUnits(5L);
                                                       })
                                                       .build();
        try {
            dynamoDbClient.createTable(request);
            System.out.println("Orders table with GSI created successfully.");
        } catch (ResourceInUseException e) {
            System.out.println("Orders table already exists.");
        }
    }
}
