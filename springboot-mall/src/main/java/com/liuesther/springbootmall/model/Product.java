package com.liuesther.springbootmall.model;

import com.liuesther.springbootmall.constant.ProductCategory;

import java.util.Date;

public class Product {

    private Integer productId;
    private String productName;
    private ProductCategory category; //用 String 類型來儲存有一個缺點 就是我們沒有辦法從 Spring Boot 的程式中去知道說 這個商品的分類 他到底是有哪一些分類
    //提高可讀性 所以改Enum：以後別人在看這一段程式的時候 可以去點進去這個 ProductCategory 去查看說這個商品裡面到底是分成了哪一些的分類
    //將這個 Product 的數據 去返回給前端的時候 也可以去保證說 我們所返回的 category 的值 一定是雙方預先定義好的值 那就不會去回傳一個未知的值給前端
    private String imageUrl;
    private Integer price;
    private Integer stock;
    private String description;
    private Date createdDate;
    private Date lastModifiedDate;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
