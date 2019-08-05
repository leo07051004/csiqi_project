package com.csiqi.dao.core;

import java.io.Serializable;
import java.util.List;

/**
 * Pagination 数据查询分页类
 * @author joe.qiu
 * May 8, 2010 10:09:03 AM
 * @version 1.0.0
 */
public class Pagination<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 当前数据页数
	 */
	private int curPage;

	/**
	 * 当前数据查询量
	 */
	private int pageSize;

	/**
	 * 当前条件数据总量
	 */
	private int rowCount;

	/**
	 * 数据查询结果集
	 */
	private List<T> results;

	/**
	 * 获取当前页数，当当前页小于1时，自动调整为1
	 * 
	 * @return 当前数据页数
	 */
	public int getCurPage() {
		return curPage < 1 ? 1 : curPage;
	}

	/**
	 * 设置当前数据页数
	 * @param curPage 当前数据页数
	 */
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	/**
	 * 获取当前分页记录数，当当前页记录数小于1时，自动调整为1
	 * 
	 * @return 当前数据查询量
	 */
	public int getPageSize() {
		return pageSize < 1 ? 1 : pageSize;
	}

	/**
	 * 设置当前数据查询量
	 * @param pgsize 当前数据查询量
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获取当前条件数据总量
	 * @return 当前条件数据总量
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * 设置当前条件数据总量
	 * @param totalCount 当前条件数据总量
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * 根据总记录数计算总页数
	 * 
	 * @return 总页数
	 */
	public int getPageCount() {
		if (getRowCount() < 0) {
			return -1;
		}
		int pageCount = getRowCount() / getPageSize();
		if (getRowCount() % getPageSize() > 0) {
			pageCount++;
		}
		return pageCount;
	}

	/**
	 * 获取页内的查询结果集
	 * 
	 * @return 查询结果集
	 */
	public List<T> getResults() {
		return results;
	}

	/**
	 * 设置页内的查询结果集
	 * 
	 * @param results
	 *            查询结果集
	 */
	public void setResults(List<T> results) {
		this.results = results;
	}

	/**
	 * 记录当前查询记录数的位置,记录数从1开始记
	 * 
	 * @return
	 */
	public int getFirstNum() {
		return (getCurPage() - 1) * getPageSize() + 1;
	}

	/**
	 * 判断查询结果是否还有下一页
	 * 
	 * @return
	 */
	public boolean isHasNext() {
		return getCurPage() + 1 <= this.getPageCount();
	}

	/**
	 * 获取下一页数
	 * 
	 * @return
	 */
	public int getNextPage() {
		return isHasNext() ? getCurPage() + 1 : getCurPage();
	}

	/**
	 * 判断查询结果是否还有上一页
	 * 
	 * @return
	 */
	public boolean isHasPre() {
		return getCurPage() - 1 >= 1;
	}

	/**
	 * 获取上一页数
	 * 
	 * @return
	 */
	public int getPrePage() {
		return isHasPre() ? getCurPage() - 1 : 1;
	}
}
