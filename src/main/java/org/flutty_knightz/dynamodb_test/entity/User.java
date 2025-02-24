package org.flutty_knightz.dynamodb_test.entity;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.io.Serializable;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
public class User implements Serializable {

    @Getter(onMethod_ = {@DynamoDbAttribute("user_id"), @DynamoDbPartitionKey, @DynamoDbSecondarySortKey(indexNames = "name_user_id_index")})
    private long userId;

    @Getter(onMethod_ = {@DynamoDbAttribute("created_date"), @DynamoDbSortKey})
    private String createdDate;

    @Getter(onMethod_ = {@DynamoDbAttribute("name"), @DynamoDbSecondaryPartitionKey(indexNames = "name_user_id_index")})
    private String name;

    @Getter(onMethod_ = {@DynamoDbAttribute("email")})
    private String email;

    @Getter(onMethod_ = {@DynamoDbAttribute("phone")})
    private long phone;

    @Getter(onMethod_ = {@DynamoDbAttribute("address")})
    private String address;

    @Getter(onMethod_ = {@DynamoDbAttribute("nrc")})
    private String nrc;
}
