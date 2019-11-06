package com.csiqi.dao.webDao;

import com.csiqi.model.webVo.FriendsVo;

import java.util.List;


public interface WebFriendsDao {
    List<FriendsVo> selectFriends(FriendsVo vo);
    int insertFriends(FriendsVo vo);

}
