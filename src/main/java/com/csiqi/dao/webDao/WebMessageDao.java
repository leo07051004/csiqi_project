package com.csiqi.dao.webDao;

import com.csiqi.model.webVo.MessageVo;

import java.util.List;


public interface WebMessageDao {
    List<MessageVo> selectMessage(MessageVo vo);
    int insertMessage(MessageVo vo);

}
