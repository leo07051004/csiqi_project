package com.csiqi.service;

import com.csiqi.model.UserDomain;
import com.github.pagehelper.PageInfo;

public interface UserService {
    int addUser(UserDomain user);
    PageInfo<UserDomain> findAllUser(int pageNum, int pageSize);
    UserDomain selectUserById(int userId);
    Object removeUserById(int userId);
}
