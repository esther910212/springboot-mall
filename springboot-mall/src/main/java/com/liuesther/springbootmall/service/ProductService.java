package com.liuesther.springbootmall.service;

import com.liuesther.springbootmall.dto.ProductRequest;
import com.liuesther.springbootmall.model.Product;

public interface ProductService {

    //表示這個 ProductService 提供了一個功能 就是可以去根據 product 的 id 去取得商品的數據
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
