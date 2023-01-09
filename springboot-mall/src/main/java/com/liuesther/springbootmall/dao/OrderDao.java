package com.liuesther.springbootmall.dao;

import com.liuesther.springbootmall.model.OrderItem;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
