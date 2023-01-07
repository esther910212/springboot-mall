package com.liuesther.springbootmall.controller;

import com.liuesther.springbootmall.dto.ProductRequest;
import com.liuesther.springbootmall.model.Product;
import com.liuesther.springbootmall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController //先加上一個 @RestController 的註解 那表示他是一個 Controller 層的 bean
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productId}") //根據 RESTful 的設計原則 如果我們是想要去取得某一筆商品的數據的話 那就會是使用 GET 方法來請求 表示要去取得的是某一筆商品的數據
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){ //方法的返回類型是 ResponseEntity＜Product＞ //PathVariable 表示這個 productId 的值 是從 url 路徑裡面給傳進來
        //當前端來請求這個 api 的時候
        //我們就會去回傳一個 Product 類型的 json 給前端
        Product product = productService.getProductById(productId);

        //當前端來請求這個 url 路徑 那我們就會去透過 productService 的 getProductById 方法 去資料庫中去查詢這一筆商品的數據出來
        if(product != null){ //如果這個查詢出來的商品數據 他的值不是 null 的話 那就表示有找到這一筆商品的數據
            return ResponseEntity.status(HttpStatus.OK).body(product); //就回傳一個 ResponseEntity 那他的 http 狀態碼 是 200 那這個 response body 裡面的值 就是我們從資料庫中所查詢出來的 product 的數據 返回前端
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
//據 RESTful 的設計原則 新增商品是使用 POST 方法來請求
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){ //一定要記得 要在這個參數的前面 去加上一個 @Valid 的註解  @NotNull 才會生效
       // 傳入 ProductRequest 這個參數 並且要在他的前面 去加上一個 @RequestBody 的註解 表示他是要去接住 前端所傳過來的 json 參數
        Integer productId = productService.createProduct(productRequest); //這個方法 會去資料庫中創建這個商品出來 這個 createProduct 的方法 要去返回資料庫所生成的 productId

        Product product = productService.getProductById(productId); //使用這個 productId 去查詢這個商品的數據回來
        return ResponseEntity.status(HttpStatus.CREATED).body(product); //去回傳一個 ResponseEntity 給前端 他的 http 狀態碼 會是 201 Created 表示有一筆數據被創建出來 並且我們把這個創建出來的商品數據 放在 body 的裡面 然後傳回去給前端

    }
}
