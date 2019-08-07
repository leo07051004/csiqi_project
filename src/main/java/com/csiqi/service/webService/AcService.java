package com.csiqi.service.webService;

import com.csiqi.model.webVo.AcAdminVo;
import com.github.pagehelper.PageInfo;

public interface AcService {
 PageInfo<AcAdminVo> acList(int pageNum, int pageSize);
}
