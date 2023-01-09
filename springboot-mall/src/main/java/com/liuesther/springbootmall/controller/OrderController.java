package com.liuesther.springbootmall.controller;

import com.liuesther.springbootmall.dto.CreateOrderRequest;
import com.liuesther.springbootmall.model.Order;
import com.liuesther.springbootmall.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/users/{userId}/orders") //根據 RESTful 的設計原則 創建訂單這個行為 他是會去資料庫中新增一筆數據出來 所以就會去對應到 POST 方法
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest){

        Integer orderId = orderService.createOrder(userId,createOrderRequest);

        Order order = orderService.getOrderById(orderId);
        //除了可以去資料庫中插入數據之外 那同時也會把這整筆訂單的資訊 去回傳給前端

        return ResponseEntity.status(HttpStatus.CREATED).body(order);

    }
}
