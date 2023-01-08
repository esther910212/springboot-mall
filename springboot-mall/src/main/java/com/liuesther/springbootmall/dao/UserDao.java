package com.liuesther.springbootmall.dao;

import com.liuesther.springbootmall.dto.UserRegisterRequest;
import com.liuesther.springbootmall.model.User;

public interface UserDao {
    User getUserById(Integer userId);

    Integer createUser(UserRegisterRequest userRegisterRequest);
}
