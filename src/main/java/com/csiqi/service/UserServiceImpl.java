package com.csiqi.service;

import com.csiqi.dao.UserDao;
import com.csiqi.model.UserDomain;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
@CacheConfig(cacheNames = "userCache")
@Slf4j
@Service(value = "userService")
public class UserServiceImpl implements UserService {
@Autowired
    private UserDao userDao ;//这里会报错，但是并不会影响
    @CachePut
    @Override
    public int addUser(UserDomain user) {
        return userDao.insertUser(user);
    }

    /*
     * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
     * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
     * pageNum 开始页数
     * pageSize 每页显示的数据条数
     * */
    //@Cacheable()
    @Override
    public PageInfo<UserDomain> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<UserDomain> userDomains = userDao.findAllUser();
        PageInfo result = new PageInfo(userDomains);
       // log.debug("");
       // log.debug("result:"+result.getTotal());
        return result;
    }
    @Cacheable(key = "#userId",unless = "#result.userId==0")
    @Override
    public UserDomain selectUserById(int userId) {
        UserDomain user =new UserDomain();
        user.setUserId(userId);
        UserDomain userDomain = userDao.selectUserById( user);
       // log.debug("--执行数据库查询操作userId:"+userId);
        return userDomain;
    }

    @Override
    public Object removeUserById(int userId) {
        UserDomain user =new UserDomain();
        user.setUserId(userId);
        Object count = userDao.removeUserById( user);
       // log.debug("--执行数据库删除操作count:"+count);
        return count;
    }

}


