package com.cn.dsyg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.common.dao.BaseDao;
import com.cn.dsyg.dao.WarehouseReportDao;
import com.cn.dsyg.dao.WarehouserptDao;
import com.cn.dsyg.dto.WarehouseReportDto;
import com.cn.dsyg.dto.WarehouserptDto;

/**
 * @name WarehouserptDaoImpl.java
 * @author Pei
 * @time 2022-6-17 下午9:44:36
 * @version 1.0
 */
public class WarehouseReportDaoImpl extends BaseDao implements WarehouseReportDao {
	
	@Override
	public WarehouseReportDto queryWarehouseReportById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ID", id);
		@SuppressWarnings("unchecked")
		List<WarehouseReportDto> list = getSqlMapClientTemplate().queryForList("queryWarehouseReportById", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public void insertWarehouseReport(WarehouseReportDto warehousereport) {
		getSqlMapClientTemplate().insert("insertWarehouseReport", warehousereport);
	}

	@Override
	public void updateWarehouseReport(WarehouseReportDto warehousereport) {
		getSqlMapClientTemplate().update("updateWarehouseReport", warehousereport);
	}

	@Override
	public List<WarehouseReportDto> queryWarehouseReportByPage(String strWarehouseReportNoLow, String strWarehouseReportNoHigh, String strWarehouseReportName, int start, int end) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		System.out.println("strWarehouseReportNoLow:" + strWarehouseReportNoLow);
		System.out.println("strWarehouseReportNoHigh:" + strWarehouseReportNoHigh);
		System.out.println("strWarehouseReportName:" + strWarehouseReportName);
		System.out.println("start:" + start);
		System.out.println("end:" + end);
		paramMap.put("ID_LOW", strWarehouseReportNoLow);
		paramMap.put("ID_HIGH", strWarehouseReportNoHigh);
		paramMap.put("WAREHOUSEREPORT_NAME", strWarehouseReportName);
		paramMap.put("start", start);
		paramMap.put("end", end);
		@SuppressWarnings("unchecked")
		List<WarehouseReportDto> list = getSqlMapClientTemplate().queryForList("queryWarehouseReportByPage", paramMap);
		System.out.println("list.size():" + list.size());
		if(list != null && list.size() > 0) {
			return list;
		}
		return null;
	}
	
	/**
	 * 删除数据
	 * @param warehouserpt
	 */
	public void deleteWarehouseReport(String warehousereportno, String username) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("warehousereportno", warehousereportno);
		paramMap.put("updateuid", username);
		getSqlMapClientTemplate().update("delWarehouseReport", paramMap);
	}

	public int queryWarehouseReportCountByPage(String strWarehouseReportNoLow, String strWarehouseReportNoHigh, String strWarehouseReportName){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ID_LOW", strWarehouseReportNoLow);
		paramMap.put("ID_HIGH", strWarehouseReportNoHigh);
		paramMap.put("WAREHOUSEREPORT_NAME", strWarehouseReportName);
		return (Integer) getSqlMapClientTemplate().queryForObject("queryWarehouseReportCountByPage", paramMap);
	}
	
}

