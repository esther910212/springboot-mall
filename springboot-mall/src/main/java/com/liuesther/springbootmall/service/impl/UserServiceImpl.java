package com.liuesther.springbootmall.service.impl;

import com.liuesther.springbootmall.dao.UserDao;
import com.liuesther.springbootmall.dto.UserLoginRequest;
import com.liuesther.springbootmall.dto.UserRegisterRequest;
import com.liuesther.springbootmall.model.User;
import com.liuesther.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    //如果要在 class 裡面去加上 log 的話 我們要先在最上面 去定義這個 log 的變數出來 + 寫上這個 class 的名字
    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        // 檢查註冊的 email
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
        //透過 前端所傳進去的 email 的值 去資料庫中查詢對應的 user 出來

        if(user!=null){
            //添加log描述我們的程式遇到了什麼狀況
            log.warn("該email {} 已經被註冊",userRegisterRequest.getEmail());//指定這個 log 的等級 + 一對｛｝去表示一個變數 (可以有很多對｛｝後面對應變數即可)

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);//噴出exception後，後面的程式都不會執行
        } //指定要回傳 400 Bad Request 的狀態碼給前端
        //當 user 不為 null 時 就表示這個 email 已經註冊過帳號了 所以我們可以在這邊 就可以直接去噴出一個 ResponseStatusException
        //那這樣子前端這一次的請求 就會在這邊被迫停止 Spring Boot 程式就會去回傳一個錯誤碼給前端 表示這個請求參數是有問題的

        // 創建帳號
        return userDao.createUser(userRegisterRequest);
    }


    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        if(user == null){
            log.warn("該 email {} 尚未註冊", userLoginRequest.getEmail());
            //並且我們在下面 就去噴出一個 ResponseStatusException 那去強制停止這一次前端的請求
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if(user.getPassword().equals(userLoginRequest.getPassword())){ //比較String的值時要用equals
            return user;
        }else{
            log.warn("該 email {} 的密碼不正確",userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
