package com.cn.dsyg.service;

import java.util.List;

import com.cn.dsyg.dto.SalesItemDto;

public interface SalesItemHisService {

	/**
	 * 根据销售单ID查询销售单货物列表
	 * @param salesno
	 * @return
	 */
	public List<SalesItemDto> querySalesItemHisBySalesid(String id);
	
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
