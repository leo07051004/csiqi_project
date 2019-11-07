package com.csiqi.service.webService;

import com.csiqi.model.webVo.MessageVo;
import com.github.pagehelper.PageInfo;


public interface MessageService {
 PageInfo<MessageVo> selectMessage(MessageVo vo);
 int insertMessage(MessageVo vo);
}
