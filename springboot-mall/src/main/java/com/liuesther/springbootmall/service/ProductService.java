package com.liuesther.springbootmall.service;

import com.liuesther.springbootmall.constant.ProductCategory;
import com.liuesther.springbootmall.dto.ProductQuertParams;
import com.liuesther.springbootmall.dto.ProductRequest;
import com.liuesther.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    Integer countProduct(ProductQuertParams productQuertParams);

    List<Product> getProducts(ProductQuertParams productQuertParams);//(ProductCategory category,String search)

    //表示這個 ProductService 提供了一個功能 就是可以去根據 product 的 id 去取得商品的數據
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
