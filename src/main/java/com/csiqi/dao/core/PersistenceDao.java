package com.csiqi.dao.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;



/**
 * PersistenceDao
 * @author <a href="mailto:qiuyp@aliyun.com">Joe.qiu</a>
 * @version 3.0
 * @since 2014-11-13下午4:49:34
 */
public abstract class PersistenceDao<T, PK extends Serializable> extends MybatisDao<T, PK> {
	@Resource
	private SqlSession sqlSession;

	@Override
	public SqlSession getSqlSession(){
		return sqlSession;
	}
	
	protected Map<String, Object> createConditionMap(){
		return new HashMap<String, Object>();
	}

	protected Pagination<T> findPage(Map<String, Object> map, int curPage, int pageSize){
		Pagination<T> page = new Pagination<T>();
		Integer rowCount = findQuantity(map);
		page.setRowCount(rowCount);
		page.setCurPage(curPage);
		page.setPageSize(pageSize);
		if(rowCount > 0 && curPage > 0 && pageSize > 0){
			if(map == null)
				map = new HashMap<String, Object>();
			map.put("firstResult", (curPage-1)*pageSize);
			map.put("maxResults", pageSize);
			List<T> results = findPageList(map);
			page.setResults(results);
		}
		return page;
	}
}
