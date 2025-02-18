package org.flutty_knightz.dynamodb_test.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DynamoDbBean
public class Order {
    @Getter(onMethod_ = {@DynamoDbPartitionKey})
    @Setter
    private int orderId;

    @Getter(onMethod_ = {@DynamoDbSortKey, @DynamoDbSecondarySortKey(indexNames = {"order_by_created_date", "order_by_user_id"})})
    @Setter
    private LocalDate createdDate;

    @Getter(onMethod_ = {@DynamoDbSecondaryPartitionKey(indexNames = "order_by_user_id")})
    @Setter
    private int userId;

    @Getter(onMethod_ = {@DynamoDbAttribute("order_item")})
    @Setter
    private String orderItem;

}
