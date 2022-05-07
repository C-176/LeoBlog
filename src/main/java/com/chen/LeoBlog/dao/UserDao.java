package com.chen.LeoBlog.dao;

import com.chen.LeoBlog.po.User;
import org.springframework.stereotype.Component;


/**
 * @author C-137
 */

@Component
public interface UserDao {
    User getUserByName(String userName);

    User getUserByEmail(String Email);

    Integer insertUser(User user);

    Integer deleteUserById(Integer userId);

    User getUserById(Integer userId);

    Integer updateUser(User user);

}
