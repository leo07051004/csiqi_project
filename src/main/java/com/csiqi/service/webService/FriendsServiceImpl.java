package com.csiqi.service.webService;

import com.csiqi.dao.webDao.WebFriendsDao;
import com.csiqi.model.webVo.FriendsVo;
import com.csiqi.model.webVo.UserVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class FriendsServiceImpl implements  FriendsService{
    @Autowired
    private WebFriendsDao fDao;
    @Override
    public PageInfo<UserVo> friendsList(FriendsVo vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<UserVo> acs = fDao.selectFriends(vo);
        PageInfo result = new PageInfo(acs);
        return result;
    }

    @Override
    public int insertFriends(FriendsVo av) {
        return fDao.insertFriends(av);
    }
}
