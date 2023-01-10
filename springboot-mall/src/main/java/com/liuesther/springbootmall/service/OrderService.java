package com.liuesther.springbootmall.service;

import com.liuesther.springbootmall.dto.CreateOrderRequest;
import com.liuesther.springbootmall.dto.OrderQueryParams;
import com.liuesther.springbootmall.model.Order;

import java.util.List;

public interface OrderService {
    Integer countOrder(OrderQueryParams orderQueryParams);
    List<Order> getOrders(OrderQueryParams orderQueryParams);
    Order getOrderById(Integer orderId);
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
