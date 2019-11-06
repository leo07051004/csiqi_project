package com.csiqi.dao.webDao;

import com.csiqi.model.webVo.FriendsVo;
import com.csiqi.model.webVo.UserVo;

import java.util.List;


public interface WebFriendsDao {
    List<UserVo> selectFriends(FriendsVo vo);
    int insertFriends(FriendsVo vo);
}
