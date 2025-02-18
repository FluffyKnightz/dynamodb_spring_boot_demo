package org.flutty_knightz.dynamodb_test.entity;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.time.LocalDate;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@DynamoDbBean
public class User {

    @Getter(onMethod_ = {@DynamoDbPartitionKey})
    private long userId;

    @Getter(onMethod_ = {@DynamoDbSortKey})
    private String createdDate;

    @Getter(onMethod_ = {@DynamoDbAttribute("name")})
    private String name;

    @Getter(onMethod_ = {@DynamoDbAttribute("email")})
    private String email;

}
