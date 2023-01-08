package com.liuesther.springbootmall.service;

import com.liuesther.springbootmall.dto.UserLoginRequest;
import com.liuesther.springbootmall.dto.UserRegisterRequest;
import com.liuesther.springbootmall.model.User;

public interface UserService {

    User getUserById(Integer userId);
    Integer register(UserRegisterRequest userRegisterRequest);
    User login(UserLoginRequest userLoginRequest);
}
