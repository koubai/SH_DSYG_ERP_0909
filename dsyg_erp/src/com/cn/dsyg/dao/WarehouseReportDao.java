package com.cn.dsyg.dao;

import java.util.List;

import com.cn.dsyg.dto.WarehouseReportDto;

/**
 * @name WarehouserptDao.java
 * @author Pei
 * @time 2022-6-16下午9:44:07
 * @version 1.0
 */
public interface WarehouseReportDao {
	
	/**
	 * 翻页查询
	 * @param strWarehouseReportNoLow
	 * @param strWarehouseReportNoHigh
	 * @param strWarehouseReportName
	 * @return
	 */
	public int queryWarehouseReportCountByPage(String strWarehouseReportNoLow, String strWarehouseReportNoHigh, String strWarehouseReportName);
	
	/**
	 * 翻页查询
	 * @param strWarehouseReportNoLow
	 * @param strWarehouseReportNoHigh
	 * @param strWarehouseReportName
	 * @param start
	 * @param end
	 * @return
	 */
	public List<WarehouseReportDto> queryWarehouseReportByPage(String strWarehouseReportNoLow, String strWarehouseReportNoHigh, String strWarehouseReportName, int start, int end);
	
	/**
	 * 根据入出库单查询数据
	 * @param warehouseno
	 * @return
	 */
	public WarehouseReportDto queryWarehouseReportById(String Id);
	
	/**
	 * 新增数据
	 * @param warehouserpt
	 */
	public void insertWarehouseReport(WarehouseReportDto warehousereport);
	
	/**
	 * 修改数据
	 * @param warehouserpt
	 */
	public void updateWarehouseReport(WarehouseReportDto warehousereport);
	
	/**
	 * 删除数据
	 * @param warehouserpt
	 */
	public void deleteWarehouseReport(String warehousereportno, String updateuid);
}
