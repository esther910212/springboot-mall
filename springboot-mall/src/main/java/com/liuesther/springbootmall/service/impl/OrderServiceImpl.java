package com.liuesther.springbootmall.service.impl;

import com.liuesther.springbootmall.dao.OrderDao;
import com.liuesther.springbootmall.dao.ProductDao;
import com.liuesther.springbootmall.dao.UserDao;
import com.liuesther.springbootmall.dto.BuyItem;
import com.liuesther.springbootmall.dto.CreateOrderRequest;
import com.liuesther.springbootmall.dto.OrderQueryParams;
import com.liuesther.springbootmall.model.Order;
import com.liuesther.springbootmall.model.OrderItem;
import com.liuesther.springbootmall.model.Product;
import com.liuesther.springbootmall.model.User;
import com.liuesther.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService { //可以在一個 Service 裡面去注入多個 Dao 的
    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        return orderDao.countOrder(orderQueryParams);
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        List<Order> orderList = orderDao.getOrders(orderQueryParams);

        for(Order order : orderList){
            List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(order.getOrderId());
            order.setOrderItemList(orderItemList);
            //針對每一個 order 我們都去取得他的 order items 然後把這個 orderItemList 去放在每一個 order 的底下
            //那等到這個 for loop 做完 每一個訂單都去找到他的詳細資訊之後 那最後我們再將這個 orderList 給返回回去就可以了
        }
        return orderList;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);
        //合併order和orderItemList=>到order類別新增orderItem的List
        order.setOrderItemList(orderItemList);
        //這個 order 裡面 除了會去包含訂單的總資訊之外 那也會去包含 這一筆訂單他分別購買的是哪一些商品的資訊
        return order;
    }

    @Transactional //修改多張資料庫 table 的話=> 萬一中間噴出了 exception 的話，會去復原已經執行過的資料庫操作
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        // 檢查 user 是否存在
        User user = userDao.getUserById(userId);

        if(user == null){
            log.warn("該 userId {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            //保護我們的資料庫 那就會去中斷這一次前端的請求了
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());

            // 檢查 product 是否存在、庫存是否足夠
            if(product == null){
                log.warn("商品 {} 不存在", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }else if (product.getStock() < buyItem.getQuantity()){
                log.warn("商品 {} 庫存數量不足，無法購買。剩餘庫存 {} ，欲購買數量 {}",
                        buyItem.getProductId(),product.getStock(),buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // 扣除商品庫存
            productDao.updateStock( product.getProductId(), product.getStock() - buyItem.getQuantity());


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
