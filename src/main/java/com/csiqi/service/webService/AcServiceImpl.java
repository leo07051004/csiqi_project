package com.csiqi.service.webService;

import com.csiqi.dao.webDao.AcDao;
import com.csiqi.model.webVo.AcAdminVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcServiceImpl implements  AcService{
    @Autowired
    private AcDao acDao;
    @Override
    public PageInfo<AcAdminVo> acList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AcAdminVo> acs = acDao.acList();
        PageInfo result = new PageInfo(acs);
        return result;
    }
}
