package com.liuesther.springbootmall.controller;

import com.liuesther.springbootmall.model.Product;
import com.liuesther.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController //先加上一個 @RestController 的註解 那表示他是一個 Controller 層的 bean
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productId}") //根據 RESTful 的設計原則 如果我們是想要去取得某一筆商品的數據的話 那就會是使用 GET 方法來請求 表示要去取得的是某一筆商品的數據
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){ //方法的返回類型是 ResponseEntity＜Product＞ //PathVariable 表示這個 productId 的值 是從 url 路徑裡面給傳進來

        Product product = productService.getProductById(productId);

        //當前端來請求這個 url 路徑 那我們就會去透過 productService 的 getProductById 方法 去資料庫中去查詢這一筆商品的數據出來
        if(product != null){ //如果這個查詢出來的商品數據 他的值不是 null 的話 那就表示有找到這一筆商品的數據
            return ResponseEntity.status(HttpStatus.OK).body(product); //就回傳一個 ResponseEntity 那他的 http 狀態碼 是 200 那這個 response body 裡面的值 就是我們從資料庫中所查詢出來的 product 的數據 返回前端
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
