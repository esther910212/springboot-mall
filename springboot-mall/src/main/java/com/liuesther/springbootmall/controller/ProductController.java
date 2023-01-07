package com.liuesther.springbootmall.controller;

import com.liuesther.springbootmall.constant.ProductCategory;
import com.liuesther.springbootmall.dao.ProductDao;
import com.liuesther.springbootmall.dto.ProductQuertParams;
import com.liuesther.springbootmall.dto.ProductRequest;
import com.liuesther.springbootmall.model.Product;
import com.liuesther.springbootmall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //先加上一個 @RestController 的註解 那表示他是一個 Controller 層的 bean
public class ProductController {
    @Autowired
    private ProductService productService;

    //url 路徑他代表的是每一個資源之間的階層關係那在 url 路徑裡面每出現一個斜線就代表是一個階層也就是一個子集合的概念
    //所以 GET /products他是取得一堆商品的話那 GET /products/｛producId｝他就是去取得這堆商品中裡面的某一個特定的商品的數據
    @GetMapping("/products")//查詢商品列表，所以要加s
    public ResponseEntity<List<Product>>getProducts( //@RequestParam 的註解 表示這個 category 的參數他是從 url 中所取得到的請求參數
            @RequestParam(required = false) ProductCategory category, //針對這種有預先定義好的 category 的值 可以使用 ProductCategory 這個 Enum 去當作這個參數的類型 Spring Boot 他會自動幫我們將前端傳過來的字串 去轉換成是 ProductCategory 這個 Enum
            @RequestParam(required = false) String search //(required = false)允許此參數為非必要
    ){
        ProductQuertParams productQuertParams = new ProductQuertParams();
        productQuertParams.setCategory(category);//把前端傳過來的 category 的參數去 set 到 productQueryParams 的 category 變數裡面降低不小心去填錯參數的一個機率
        productQuertParams.setSearch(search);
        //以後不論我們在這個 productQueryParams 裡面去添加了多少新的變數(添加新的查詢條件)那我們就不用再去修改 Service 層還有 Dao 層他們的 getProducts 方法的定義了

        List<Product> productList = productService.getProducts(productQuertParams);//category,search=>替換掉productQuertParams

        return ResponseEntity.status(HttpStatus.OK).body(productList);

        //不管有沒有去查詢到商品的數據 都是會去回傳 200 OK 的 http 狀態碼給前端
        //所以當取得了 productList 的商品列表的時候 並沒有去判斷 這個 productList 是否為一個空的 list
        //反而是將這個 producList 直接去返回給前端

        //因為 RESTful 他在設計上的一些理念 對 RESTful 對url的資源定義來說
        //每一個 url 都是一個資源 所以當前端來請求 GET /products 這個資源的時候 那即使商品的數據不存在 但是 GET /products 這個資源是存在的 所以這個時候 就要回 200 OK 給前端
        //假設前端他是去請求 GET /products/｛producId｝ 這個 url 的時候 如果這時候找不到這個商品數據 那就表示這個 url 的資源是不存在的 所以這時候就要回 404 Not Found 給前端
    }



    @GetMapping("/products/{productId}") //根據 RESTful 的設計原則 如果我們是想要去取得某一筆商品的數據的話 那就會是使用 GET 方法來請求 表示要去取得的是某一筆商品的數據
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){ //方法的返回類型是 ResponseEntity＜Product＞ //PathVariable 表示這個 productId 的值 是從 url 路徑裡面給傳進來
        //當前端來請求這個 api 的時候
        //我們就會去回傳一個 Product 類型的 json 給前端
        Product product = productService.getProductById(productId);

        //當前端來請求這個 url 路徑 那我們就會去透過 productService 的 getProductById 方法 去資料庫中去查詢這一筆商品的數據出來
        if(product != null){ //如果這個查詢出來的商品數據 他的值不是 null 的話 那就表示有找到這一筆商品的數據
            return ResponseEntity.status(HttpStatus.OK).body(product); //就回傳一個 ResponseEntity 那他的 http 狀態碼 是 200 那這個 response body 裡面的值 就是我們從資料庫中所查詢出來的 product 的數據 返回前端
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();//因為/products/{productId}的api資源是無效的=> 所以not found
        }
    }


    //根據 RESTful 的設計原則 新增商品是使用 POST 方法來請求
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){ //一定要記得 要在這個參數的前面 去加上一個 @Valid 的註解  @NotNull 才會生效
       // 傳入 ProductRequest 這個參數 並且要在他的前面 去加上一個 @RequestBody 的註解 表示他是要去接住 前端所傳過來的 json 參數
        Integer productId = productService.createProduct(productRequest); //這個方法 會去資料庫中創建這個商品出來 這個 createProduct 的方法 要去返回資料庫所生成的 productId

        Product product = productService.getProductById(productId); //使用這個 productId 去查詢這個商品的數據回來
        return ResponseEntity.status(HttpStatus.CREATED).body(product); //去回傳一個 ResponseEntity 給前端 他的 http 狀態碼 會是 201 Created 表示有一筆數據被創建出來 並且我們把這個創建出來的商品數據 放在 body 的裡面 然後傳回去給前端

    }



    //根據 RESTful 的設計原則 修改商品是使用 PUT 方法來請求
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId, //去接住從 url 路徑傳過來的 productId 的值 所以我在參數這邊可以寫一個 @PathVariable 註解
                                                 @RequestBody @Valid ProductRequest productRequest){ //去接住 這個商品修改過後的數據 我們在這邊就可以去沿用 上面有創建的 ProductRequest(剛好根要給user修改的欄位相同) 來使用

        // 檢查 product 是否存在
        Product product = productService.getProductById(productId);

        if(product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // 修改商品的數據： 商品如果存在的話 就去更新這個商品 並且將更新過後的商品數據 再去回傳給前端
        //productId 表示要更新的是哪一個商品 第二個參數就是 ProductRequest 用來表示這個商品修改過後的值是什麼
        productService.updateProduct(productId,productRequest);

        //使用這個 productId 去查詢更新後的商品數據回來
        Product updatedProduct = productService.getProductById(productId);

        //回傳一個 ResponseEntity 給前端
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }



    //根據 RESTful 的設計原則刪除商品是使用 DELETE 方法來請求

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){
        productService.deleteProductById(productId);

        //不管我們有沒有去刪到這個商品 只要確定他消失不見
        //不需要多去加一些 404 Not Found 的檢查判斷 這些判斷對刪除的 api 來說 反而是不正確的設計
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //去返回 204 No Content 的資訊給前端就可以
    }
}
