package com.liuesther.springbootmall.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class User {

    private Integer userId;
    @JsonProperty("e_mail") //當 Spring Boot 將這個 User class 去轉換成是一個 json 格式的時候 就會將 email 變數的 key 去轉換成是 e_mail
    private String email;

    @JsonIgnore //隱藏此變數 不回傳給前端
    private String password;//在回傳 response body 給前端的時候 就要去將這個 password 的值給隱藏起來 避免我們去洩漏使用者的密碼給其他人
    private Date createdDate;
    private Date lastModifiedDate;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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