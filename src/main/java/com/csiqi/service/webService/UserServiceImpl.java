package com.csiqi.service.webService;

import com.csiqi.dao.webDao.WebUserDao;
import com.csiqi.model.webVo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl  implements  UserService{
    @Autowired
    private WebUserDao webUserDao;
    @Override
    public Boolean loginSuccess(String name,String pwd) {
        UserVo uvo=new UserVo();
        uvo.setUserName(name);
        uvo.setPassword(pwd);
        List<UserVo> uvos = webUserDao.selectUser(uvo);
        if(uvos!=null && uvos.size()>0){
            return true;
        }
        return false;
    }
}
