package com.csiqi.service.webService;

import com.csiqi.dao.webDao.WebMessageDao;
import com.csiqi.model.webVo.MessageVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MessageServiceImpl implements  MessageService{
    @Autowired
    private WebMessageDao mDao;
    @Override
    public PageInfo<MessageVo>  selectMessage(MessageVo vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<MessageVo> acs = mDao.selectMessage(vo);
        PageInfo result = new PageInfo(acs);
        return result;
    }

    @Override
    public int insertMessage(MessageVo vo) {
        return mDao.insertMessage(vo);
    }

    @Override
    public PageInfo<MessageVo> selectMessageByFromUId(MessageVo vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<MessageVo> acs = mDao.selectMessageByFromUId(vo);
        PageInfo result = new PageInfo(acs);
        return result;
    }
}
