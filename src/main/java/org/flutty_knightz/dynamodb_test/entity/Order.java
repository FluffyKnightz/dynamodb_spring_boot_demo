package org.flutty_knightz.dynamodb_test.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@DynamoDbBean
public class Order {
    @Getter(onMethod_ = {@DynamoDbAttribute("order_id"), @DynamoDbPartitionKey})
    private int orderId;

    @Getter(onMethod_ = {@DynamoDbAttribute("created_date"), @DynamoDbSortKey, @DynamoDbSecondarySortKey(indexNames = "GSI_CreatedDate_Order")})
    private LocalDate createdDate;

    @Getter(onMethod_ = {@DynamoDbSecondaryPartitionKey(indexNames = "GSI_UserID_CreatedDate")})
    private int userId;

    @Getter(onMethod_ = {@DynamoDbAttribute("order_item")})
    private String orderItem;

}
