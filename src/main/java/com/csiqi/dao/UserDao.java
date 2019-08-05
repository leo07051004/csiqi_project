package com.csiqi.dao;

import com.csiqi.model.UserDomain;

import java.util.List;
public interface UserDao {
    int insertUser(UserDomain record);
    List<UserDomain> findAllUser();
    UserDomain selectUserById(UserDomain user);
    Object removeUserById(UserDomain user);
}
