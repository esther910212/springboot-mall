package com.liuesther.springbootmall.controller;

import com.liuesther.springbootmall.dto.UserLoginRequest;
import com.liuesther.springbootmall.dto.UserRegisterRequest;
import com.liuesther.springbootmall.model.User;
import com.liuesther.springbootmall.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
        // @RequestBody 的註解 表示我們要去接住前端傳過來的 request body
        // @Valid 註解 表示要去驗證這個 POST 請求的 request body 參數

        Integer userId = userService.register(userRegisterRequest);
        //在資料庫中創建一筆新的 user 數據出來 這個 register 的方法 他執行成功之後 會去返回資料庫生成的 userId 給我們

        User user = userService.getUserById(userId);
        //創建帳號成功之後 我們就可以去使用這個 userId 去查詢這個帳號的數據回來

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("users/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest userLoginRequest){ //RequestBody表示要接住前端傳來的RequestBody值
        User user = userService.login(userLoginRequest);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
