package com.cn.dsyg.service;

import com.cn.common.util.Page;
import com.cn.dsyg.dto.CuPriceDto;

public interface CuPriceService {

	/**
	 * 翻页查询数据
	 * @param setdateLow
	 * @param setdateHigh
	 * @param cuPriceCode
	 * @param page
	 * @return
	 */
	public Page queryCuPriceByPage(String setdateLow, String setdateHigh, String cuPriceCode, Page page);
	
	/**
	 * 根据ID查询数据
	 * @param id
	 * @return
	 */
	public CuPriceDto queryCuPriceByID(String id);
	
	/**
	 * 根据逻辑主键查询数据
	 * @param setdate
	 * @return
	 */
	public CuPriceDto queryCuPriceByLogicId(String setdate);
	
	/**
	 * 根据时间查询最近设置的价位区间
	 * @param setdate
	 * @return
	 */
	public CuPriceDto queryLastCuPriceBySetDate(String setdate);
	
	/**
	 * 根据ID删除数据（逻辑删除）
	 * @param id
	 * @param userid
	 */
	public void deleteCuPriceLogic(String id, String userid);
	
	/**
	 * 根据ID删除数据（物理删除）
	 * @param id
	 */
	public void deleteCuPrice(String id);
	
	/**
	 * 新增数据
	 * @param cuPrice
	 */
	public void insertCuPrice(CuPriceDto cuPrice);
	
	/**
	 * 修改数据
	 * @param cuPrice
	 */
	public void updateCuPrice(CuPriceDto cuPrice);
}
