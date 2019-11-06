package com.csiqi.service.webService;

import com.csiqi.model.webVo.FriendsVo;
import com.csiqi.model.webVo.UserVo;
import com.github.pagehelper.PageInfo;

public interface FriendsService {
 PageInfo<UserVo> friendsList(FriendsVo vo);
 int insertFriends(FriendsVo av);
}
