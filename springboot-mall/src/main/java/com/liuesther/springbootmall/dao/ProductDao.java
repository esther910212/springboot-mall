package com.liuesther.springbootmall.dao;

import com.liuesther.springbootmall.constant.ProductCategory;
import com.liuesther.springbootmall.dto.ProductRequest;
import com.liuesther.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts(ProductCategory category,String search);

    Product getProductById(Integer productId);
    //方法的返回類型是 Proudct 類型的 getProductById 參數就是傳一個 Integer 類型的 productId 進來 根據 productId 去查詢這個商品的數據出來

    Integer createProduct(ProductRequest productRequest);


    void updateProduct(Integer productId, ProductRequest productRequest);


    void deleteProductById(Integer productId);
}
