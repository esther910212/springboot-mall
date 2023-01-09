package com.liuesther.springbootmall.service.impl;

import com.liuesther.springbootmall.dao.OrderDao;
import com.liuesther.springbootmall.dao.ProductDao;
import com.liuesther.springbootmall.dto.BuyItem;
import com.liuesther.springbootmall.dto.CreateOrderRequest;
import com.liuesther.springbootmall.model.Order;
import com.liuesther.springbootmall.model.OrderItem;
import com.liuesther.springbootmall.model.Product;
import com.liuesther.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());
            // 計算總價錢
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount + amount;

            // 轉換 BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        //創建訂單
        //因為在資料庫中 訂單是由兩張 table 所去管理的 分別是 order table 以及 order_item table
        //分別去 call 兩次 Dao 那分別在這兩張 table 中 去插入相對應的數據出來
        Integer orderId = orderDao.createOrder(userId,totalAmount); // 看table需要傳入userId、totalAmount

        orderDao.createOrderItems(orderId,orderItemList);//插入多筆數據 到 order_item table 裡面

        return orderId;
    }
}
