package com.liuesther.springbootmall.dao.impl;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import com.liuesther.springbootmall.dao.UserDao;
import com.liuesther.springbootmall.dto.UserRegisterRequest;
import com.liuesther.springbootmall.model.User;
import com.liuesther.springbootmall.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public User getUserById(Integer userId) {
        String sql = "SELECT user_id,email,password,created_date,last_modified_date FROM user WHERE user_id=:userId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        List<User> userList = namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());
        //UserRowMapper 他就是負責去將資料庫的結果 去轉換成是一個 User object 的 List

        if(userList.size() > 0){
            return userList.get(0);
        }else{
            return null;
        }

    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT user_id,email,password,created_date,last_modified_date FROM user WHERE email=:email";

        Map<String, Object> map = new HashMap<>();
        map.put("email", email);

        List<User> userList = namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());
        //UserRowMapper 他就是負責去將資料庫的結果 去轉換成是一個 User object 的 List

        if(userList.size() > 0){
            return userList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        String sql="INSERT INTO user(email,password,created_date,last_modified_date) " +
                "VALUES (:email, :password, :createdDate, :lastModifiedDate)";

        Map<String,Object> map = new HashMap<>();
        map.put("email",userRegisterRequest.getEmail());
        map.put("password", userRegisterRequest.getPassword());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        //使用 KeyHolder 就可以去接住 MySQL 資料庫自動生成的 userId

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map), keyHolder);

        int userId = keyHolder.getKey().intValue();

        return userId;
        //最後再將這個 userId 的值給回傳回去

    }
}
