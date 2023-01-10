package com.liuesther.springbootmall.controller;

import com.liuesther.springbootmall.dto.CreateOrderRequest;
import com.liuesther.springbootmall.dto.OrderQueryParams;
import com.liuesther.springbootmall.model.Order;
import com.liuesther.springbootmall.service.OrderService;
import com.liuesther.springbootmall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0")  @Min(0) Integer offset
    ){
        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setUserId(userId);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);
        //把所有的參數 統一的收集到 OrderQueryParams 裡面 然後下面在取得 orderList 以及取得 order 總數的時候 就可以直接把這個 orderQueryParams 去傳到這兩個方法裡面

        // 取得 order list
        List<Order> orderList = orderService.getOrders(orderQueryParams);

        // 取得 order 總數
        Integer count = orderService.countOrder(orderQueryParams);

        // 分頁
        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.getOffset(offset);
        page.setTotal(count);
        page.setResult(orderList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }



    @PostMapping("/users/{userId}/orders") //根據 RESTful 的設計原則 創建訂單這個行為 他是會去資料庫中新增一筆數據出來 所以就會去對應到 POST 方法
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest){

        Integer orderId = orderService.createOrder(userId,createOrderRequest);

        Order order = orderService.getOrderById(orderId);
        //除了可以去資料庫中插入數據之外 那同時也會把這整筆訂單的資訊 去回傳給前端

        return ResponseEntity.status(HttpStatus.CREATED).body(order);

    }
}
