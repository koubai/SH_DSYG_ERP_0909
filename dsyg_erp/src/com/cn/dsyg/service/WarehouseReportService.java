package com.cn.dsyg.service;

import java.io.IOException;
import java.util.List;

import com.cn.common.util.Page;
import com.cn.dsyg.dto.WarehouseReportDto;
import com.cn.dsyg.dto.WarehouserptDto;

/**
 * @name WarehouserptService.java
 * @author Pei
 * @time 2022-6-16下午9:49:20
 * @version 1.0
 */
public interface WarehouseReportService {
	

	/**
	 * 翻页查询
	 * @param no 采购单OR订单
	 * @param status
	 * @param warehousetype
	 * @param warehouseno
	 * @param theme1
	 * @param parentid
	 * @param supplierid
	 * @param productid
	 * @param beginDate
	 * @param endDate
	 * @param strSuppliername
	 * @param strWarehouseno
	 * @param createdateLow
	 * @param createdateHigh
	 * @param page
	 * @return
	 */
	public Page queryWarehouseReportByPage(Page page, String strWarehouseReportNoLow, String strWarehouseReportNoHigh, String strWarehouseReportName);
	
	/**
	 * 根据ID查询数据
	 * @param id
	 * @return
	 */
	public WarehouseReportDto queryWarehouseReportById(String id);
	
	/**
	 * 新增数据
	 * @param warehouserpt
	 */
	public String insertWarehouseReport(WarehouseReportDto warehousereport);
	
	/**
	 * 修改数据
	 * @param warehouserpt
	 */
	public void updateWarehouseReport(WarehouseReportDto warehousereport);
	
	/**
	 * 删除库存盘数据
	 * @param warehouserpt
	 */
	public void deleteWarehouseReport(String warehousereportno, String updateuid);

}
