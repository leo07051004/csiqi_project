package com.csiqi.dao.webDao;

import com.csiqi.model.webVo.UserVo;

import java.util.List;


public interface WebUserDao {
    List<UserVo> selectUser(UserVo vo);

}
