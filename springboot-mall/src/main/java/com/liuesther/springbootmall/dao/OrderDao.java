package com.liuesther.springbootmall.dao;

import com.liuesther.springbootmall.dto.OrderQueryParams;
import com.liuesther.springbootmall.model.Order;
import com.liuesther.springbootmall.model.OrderItem;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;

import java.util.List;

public interface OrderDao {

    Integer countOrder(OrderQueryParams orderQueryParams);
    List<Order> getOrders(OrderQueryParams orderQueryParams);
    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
