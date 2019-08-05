package com.csiqi.dao.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

//import com.hzqy.commons.utils.ReflectionUtils;

/**
 * Mybatis实现简单Dao功能
 * 
 * @author joe.qiu
 * @date 2010-3-24上午02:56:35
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public abstract class MybatisDao<T, PK extends Serializable> {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());

	private Class<T> clazz;

	private String namespace;

	public abstract SqlSession getSqlSession();

	// public MybatisDao(){
	// clazz=ReflectionUtils.getSuperClassGenricType(this.getClass());
	// StringBuffer buffer = new StringBuffer();
	// buffer.append(clazz.getSimpleName());
	// buffer.append("Mapper.");
	// namespace=buffer.toString();
	// }

	public MybatisDao() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.getClass().getName()+".");
//		buffer.append("Mapper.");
		namespace = buffer.toString();
//		System.out.println(namespace);
	}

	// public MybatisDao(Class<T> clazz){
	// this.clazz=clazz;
	// String packageName=clazz.getPackage().getName();
	// packageName=packageName.substring(0,packageName.lastIndexOf("."));
	// StringBuffer buffer = new StringBuffer();
	// buffer.append(packageName);
	// buffer.append(".mapper.");
	// buffer.append(clazz.getSimpleName()+"Mapper.");
	// namespace=buffer.toString();
	// }

	public String getNamespace() {
		return namespace;
	}

	public T findById(String methodId, final PK id) {
		Assert.notNull(id, "id不能为空");
		return (T) getSqlSession().selectOne(namespace + methodId, id);
	}

	public T findById(final PK id) {
		return findById("findById", id);
	}

	public T findOne(String methodId, Object param) {
		return getSqlSession().selectOne(namespace + methodId, param);
	}

	public PK findId(String methodId, Object param) {
		return getSqlSession().selectOne(namespace + methodId, param);
	}

	public Integer findQuantity(String methodId, Map<String, Object> map) {
		return getSqlSession().selectOne(namespace + methodId, map);
	}

	public Integer findQuantity(String methodId, Object object) {
		return getSqlSession().selectOne(namespace + methodId, object);
	}

	public Integer findQuantity(Object object) {
		return findQuantity("findQuantity", object);
	}

	public List<T> findByIdList(String methodId, final List<PK> idList) {
		return (List<T>) getSqlSession().selectList(namespace + methodId,
				idList);
	}

	public List<T> findByIdList(final List<PK> idList) {
		return findByIdList("findByIdsList", idList);
	}

	public List<T> findByIds(String methodId, final PK[] ids) {
		return (List<T>) getSqlSession().selectList(namespace + methodId, ids);
	}

	public List<T> findByIds(final PK[] ids) {
		return findByIds("findByIdsList", ids);
	}

	public List<T> findList(String methodId, Object values) {
		return getSqlSession().selectList(namespace + methodId, values);
	}

	/**
	 * 获取全部对象.
	 */
	public List<T> findAll(String methodId) {
		return getSqlSession().selectList(namespace + methodId);
	}

	/**
	 * 获取全部对象.
	 */
	public List<T> findAll() {
		return findAll("findAll");
	}

	/**
	 * 根据条件参数查询对象集合
	 * 
	 * @param methodId
	 *            方法ID
	 * @param map
	 *            条件参数
	 * @return
	 */
	public List<T> findList(String methodId, Map<String, Object> map) {
		return getSqlSession().selectList(namespace + methodId, map);
	}

	public List<T> findPageList(Map<String, Object> map) {
		return findList("findPageList", map);
	}

	/**
	 * 保存新增的对象.
	 */
	public int insert(String methodId, final T entity) {
		Assert.notNull(entity, "entity不能为空");
		setNullTime(entity);
		return getSqlSession().insert(namespace + methodId, entity);
		//LOG.debug("save entity: {}", entity);
	}

	/**
	 * 保存新增的对象.
	 */
	public int insert(final T entity) {
		return insert("insert", entity);
	}
	
//	public int selectUserName(final T entity) {
//		return selectUserName("selectUserName",entity);
//	}
	public int update(String methodId, int id) {
		return getSqlSession().update(namespace + methodId, id);
	}
	
	/**
	 * 保存修改的对象.
	 */
	public void update(String methodId, final T entity) {
		Assert.notNull(entity, "entity不能为空");
		setNullTime(entity);
		getSqlSession().update(namespace + methodId, entity);
		LOG.debug("save entity: {}", entity);
	}

	/**
	 * 保存修改的对象.
	 */
	public void update(final T entity) {
		update("update", entity);
	}

	/**
	 * 按id删除对象.
	 */
	public void delete(String methodId, final PK id) {
		Assert.notNull(id, "id不能为空");
		getSqlSession().delete(namespace + methodId, id);
		LOG.debug("delete entity {},id is {}", clazz.getSimpleName(), id);
	}

	/**
	 * 按id删除对象.
	 */
	public void delete(final PK id) {
		delete("deleteById", id);
	}

	/**
	 * 删除所有
	 * 
	 * @param entities
	 */
	public void delete(final Collection<PK> ids) {
		if (ids == null)
			return;
		for (PK id : ids) {
			delete(id);
		}
	}

	/**
	 * 删除所有
	 * 
	 * @param entities
	 */
	public void delete(String methodId, final Collection<PK> ids) {
		if (ids == null)
			return;
		for (PK id : ids) {
			delete(methodId, id);
		}
	}

	/**
	 * 删除所有
	 * 
	 * @param entities
	 */
	public void delete(PK[] ids) {
		if (ids == null)
			return;
		for (PK id : ids) {
			delete(id);
		}
	}

	/**
	 * 删除所有
	 * 
	 * @param entities
	 */
	public void delete(String methodId, PK[] ids) {
		if (ids == null)
			return;
		for (PK id : ids) {
			delete(methodId, id);
		}
	}

	private void setNullTime(Object entity) {
		if (entity instanceof TimeEntity) {
			TimeEntity model = (TimeEntity) entity;
			if (model.getCreatedAt() == null)
				model.setCreatedAt(new Date());
			model.setUpdatedAt(new Date());
		}
	}
}
