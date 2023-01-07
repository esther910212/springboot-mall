package com.liuesther.springbootmall.dao.impl;

import com.liuesther.springbootmall.dao.ProductDao;
import com.liuesther.springbootmall.model.Product;
import com.liuesther.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    //去注入 NamedParameterJdbcTemplate 這個 bean 下面才能使用
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public Product getProductById(Integer productId) {
        String sql="SELECT product_id, product_name, category, image_url, price, stock, description, " +
                "created_date, last_modified_date " +
                "FROM product WHERE product_id = :productId"; //冒號:java變數

        // new 一個新的 map 出來，將這個 sql 中的參數 productId 給傳進去
        Map<String,Object> map = new HashMap<>();
        map.put("productId",productId);

        //第一個參數就填 sql 的變數、第二個參數是 map 的變數、那 query 方法要傳三個參數轉換數據的 RowMapper
        //那所以我們要再去寫一個 product 的 RowMapper 出來 那才可以將我們從資料庫中所查詢出來的商品數據 去轉換成是 Java object
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        //加上一個判斷 productList有值 才取得第一個 product 的值傳回去
        if(productList.size() > 0){
            return productList.get(0);
        }else{
            return null;
        }

    }
}
