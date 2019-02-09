package com.cn.dsyg.dao;

import java.util.List;

import com.cn.dsyg.dto.SalesItemDto;

/**
 * @name SalesItemDao.java
 * @author Frank
 * @time 2015-5-17下午10:34:54
 * @version 1.0
 */
public interface SalesItemHisDao {

	/**
	 * 根据销售单ID查询销售单货物列表
	 * @param salesno
	 * @return
	 */
	public List<SalesItemDto> querySalesItemhisBySalesid(String id);
	
	/**
	 * 根据ID查询销售单货物
	 * @param id
	 * @return
	 */
	public SalesItemDto querySalesItemHisByID(String id);
	
	/**
	 * 新增销售单货物
	 * @param salesItem
	 */
	public void insertSalesItemHis(SalesItemDto salesItem);
}
