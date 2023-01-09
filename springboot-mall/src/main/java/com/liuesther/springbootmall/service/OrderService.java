package com.liuesther.springbootmall.service;

import com.liuesther.springbootmall.dto.CreateOrderRequest;
import com.liuesther.springbootmall.model.Order;

public interface OrderService {


    Order getOrderById(Integer orderId);
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
