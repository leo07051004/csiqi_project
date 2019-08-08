package com.csiqi.service.webService;

import com.csiqi.dao.webDao.AcDao;
import com.csiqi.model.webVo.AcAdminVo;
import com.csiqi.utils.Result;
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

    @Override
    public int acAdd(AcAdminVo av) {
        av.setF_ac_begintime(av.getDate1().replace("T"," ").replace("Z",""));
        av.setF_ac_endtime(av.getDate2().replace("T"," ").replace("Z",""));
        av.setF_ac_applyendtime(av.getDate3().replace("T"," ").replace("Z",""));
        av.setF_ac_adminid(1);
        av.setF_ac_stats(0);
        av.setF_ac_address("杭州");
        return acDao.acAdd(av);
    }
}
