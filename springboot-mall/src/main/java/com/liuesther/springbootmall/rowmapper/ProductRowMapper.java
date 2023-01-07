package com.liuesther.springbootmall.rowmapper;

import com.liuesther.springbootmall.constant.ProductCategory;
import com.liuesther.springbootmall.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {// implement 的 RowMapper 然後我們在後面去加上一個 ＜Product＞ 的泛型 那表示要轉換成 Product 這個 Java class

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();

        product.setProductId(rs.getInt("product_id"));
        product.setProductName(rs.getString("product_name"));

        String categoryStr = rs.getString("category");//資料庫中所取出來的數據是 String 類型 但是 product 裡面的 category 他要求的是 ProductCategory 類型
        ProductCategory category = ProductCategory.valueOf(categoryStr);//根據傳進去的字串的值 去找尋對應中的 ProductCategory 中的固定值 然後會存放在這個 category 的變數裡面
        product.setCategory(category);//就可以將這個 category 的變數 去傳入到 product 的 set 方法裡面

        //product.setCategory(ProductCategory.valueOf(rs.getString("category")));//改寫成一行

        product.setImageUrl(rs.getString("image_url"));
        product.setPrice(rs.getInt("price"));
        product.setStock(rs.getInt("stock"));
        product.setDescription(rs.getString("description"));
        product.setCreatedDate(rs.getTimestamp("created_date"));
        product.setLastModifiedDate(rs.getTimestamp("last_modified_date"));


        return product;
    }
}
