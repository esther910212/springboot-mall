package com.liuesther.springbootmall.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRegisterRequest {
    // 1.為了接住requestBody傳來的資訊要用 dto 的 UserRegisterRequest 類別
    // 2.驗證前端傳回來的請求參數
    @NotBlank //除了不可以是 null 值之外 也不可以是一個空白的字串
    @Email //只有當前端傳過來的值是 email 的格式 譬如是 test@gmail.com 那這樣才會通過 Spring Boot 程式的驗證
    private String email;
    @NotBlank
    private String password;

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
}
