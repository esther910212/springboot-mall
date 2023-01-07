package com.liuesther.springbootmall.dto;

import com.liuesther.springbootmall.constant.ProductCategory;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class ProductRequest { //決定一下 在創建商品的時候 前端需要傳哪一些參數過來

    //因為 proudctId 他是 MySQL 資料庫會自動生成的 不需要前端傳過來
    //創建時間還有最後修改時間：這兩個我們到時候讓 Spring Boot 程式自動去設定 所以也不需要前端傳過來
    @NotNull
    private String productName;
    @NotNull
    private ProductCategory category;
    @NotNull
    private String imageUrl;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stock;
    private String description;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
