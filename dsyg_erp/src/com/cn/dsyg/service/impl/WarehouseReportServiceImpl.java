package com.cn.dsyg.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.cn.common.util.Constants;
import com.cn.common.util.Page;
import com.cn.common.util.PropertiesConfig;
import com.cn.dsyg.dao.WarehouseReportDao;
import com.cn.dsyg.dto.SalesReport2Dto;
import com.cn.dsyg.dto.WarehouseReportDto;
import com.cn.dsyg.service.WarehouseReportService;


/**
 * @name WarehouserptServiceImpl.java
 * @author Pei
 * @time 2022-6-16下午9:50:32
 * @version 1.0
 */
public class WarehouseReportServiceImpl implements WarehouseReportService {
	
	private static final Logger log = LogManager.getLogger(WarehouseReportServiceImpl.class);
	
	private WarehouseReportDao warehouseReportDao;
	
	public void updateWarehouseReport(WarehouseReportDto warehousereport) {
		warehouseReportDao.updateWarehouseReport(warehousereport);
	}
	
	@Override
	public Page queryWarehouseReportByPage(Page page, String strWarehouseReportNoLow, String strWarehouseReportNoHigh, String strWarehouseReportName){
		System.out.println("queryWarehouseReportByPage: start");

		int totalCount = warehouseReportDao.queryWarehouseReportCountByPage(strWarehouseReportNoLow, strWarehouseReportNoHigh, strWarehouseReportName);
		System.out.println("totalCount: " + totalCount);
		page.setTotalCount(totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		//翻页查询记录
		List<WarehouseReportDto> list = warehouseReportDao.queryWarehouseReportByPage(strWarehouseReportNoLow, strWarehouseReportNoHigh, strWarehouseReportName, page.getStartIndex() * page.getPageSize(), page.getPageSize());
		if(list != null && list.size() > 0) {
			for (WarehouseReportDto warehousereportdto : list){
				//文件显示地址
				String pdfurl = PropertiesConfig.getPropertiesValueByKey(Constants.PROPERTIES_PDF_URL);
				warehousereportdto.setReporturl(pdfurl);
			}
			page.setItems(list);
		}
		return page;
	}

	
	@Override
	public WarehouseReportDto queryWarehouseReportById(String id) {
		return warehouseReportDao.queryWarehouseReportById(id);
	}
	
		
	@Override
	public String insertWarehouseReport(WarehouseReportDto warehousereport) {
		String warehousereportno = "";
		String belongto = PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_BELONG);
		warehousereport.setBelongto(belongto);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		warehousereportno = Constants.WAREHOUSEREPORT_NO_PRE + belongto + sdf.format(date);
		warehousereport.setWarehousereportno(warehousereportno);

		warehouseReportDao.insertWarehouseReport(warehousereport);
		log.info("WarehouseReport inserted:" + warehousereportno);
		return warehousereportno;
	}

	@Override
	public void deleteWarehouseReport(String warehousereportno, String userid) {
		warehouseReportDao.deleteWarehouseReport(warehousereportno, userid);
	}

	public WarehouseReportDao getWarehouseReportDao() {
		return warehouseReportDao;
	}

	public void setWarehouseReportDao(WarehouseReportDao warehouseReportDao) {
		this.warehouseReportDao = warehouseReportDao;
	}

}
