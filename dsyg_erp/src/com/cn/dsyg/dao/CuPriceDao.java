package com.cn.dsyg.dao;

import java.util.List;

import com.cn.dsyg.dto.CuPriceDto;

public interface CuPriceDao {

	/**
	 * 查询记录数
	 * @param setdateLow
	 * @param setdateHigh
	 * @param cuPriceCode
	 * @return
	 */
	public int queryCuPriceCountByPage(String setdateLow, String setdateHigh, String cuPriceCode);
	
	/**
	 * 翻页查询数据
	 * @param setdateLow
	 * @param setdateHigh
	 * @param cuPriceCode
	 * @param start
	 * @param end
	 * @return
	 */
	public List<CuPriceDto> queryCuPriceByPage(String setdateLow, String setdateHigh, String cuPriceCode, int start, int end);
	
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
