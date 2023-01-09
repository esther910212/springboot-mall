package com.liuesther.springbootmall.rowmapper;

import com.liuesther.springbootmall.model.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {

    //使用 OrderItemRowMapper 去這些資料庫查詢出來的數據 去轉換成是 Java 的 orderItemList
    //對 row mapper 來說 他其實不在意這些欄位 從資料庫的哪張 table 出來的 row mapper 所提供的功能 就是讓我們可以去取得 SELECT 出來的那一些數據

    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        Order order = new Order();
        order.setOrderId(resultSet.getInt("order_id"));
        order.setUserId(resultSet.getInt("user_id"));
        order.setTotalAmount(resultSet.getInt("total_amount"));
        order.setCreatedDate(resultSet.getTimestamp("created_date"));
        order.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));

        return order;
    }
}
