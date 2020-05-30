package com.csiqi.dao.webDao;

import com.csiqi.model.webVo.AcAdminVo;

import java.util.List;

public interface AcDao {

    List<AcAdminVo> acList();
    int acAdd(AcAdminVo av);
}
