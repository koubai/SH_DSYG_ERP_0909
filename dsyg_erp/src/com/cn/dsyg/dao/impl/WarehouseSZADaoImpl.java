package com.cn.dsyg.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.common.dao.BaseDao;
import com.cn.dsyg.dao.WarehouseDao;
import com.cn.dsyg.dao.WarehouseSZADao;
import com.cn.dsyg.dto.InOutStockDto;
import com.cn.dsyg.dto.ProductDto;
import com.cn.dsyg.dto.ProductQuantityDto;
import com.cn.dsyg.dto.SalesStatisticsDto;
import com.cn.dsyg.dto.WarehouseCheckDto;
import com.cn.dsyg.dto.WarehouseDetailDto;
import com.cn.dsyg.dto.WarehouseDto;
import com.cn.dsyg.dto.WarehouseInOutOkDto;
import com.cn.dsyg.dto.WarehouseOkDto;
import com.cn.dsyg.dto.WarehouseProductDto;
import com.cn.dsyg.dto.WarehouserptDto;

/**
 * @name WarehouseSZDaoImpl.java
 * @author Frank
 * @time 2017-1-8下午12:50:13
 * @version 1.0
 */
public class WarehouseSZADaoImpl extends BaseDao implements WarehouseSZADao {
	
	public ProductDto queryProductByLogicId(String fieldno, String tradename,
			String typeno, String color, String item10, String packaging,
			String unit, String makearea) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fieldno", fieldno);
		paramMap.put("tradename", tradename);
		paramMap.put("typeno", typeno);
		paramMap.put("color", color);
		paramMap.put("item10", item10);
		paramMap.put("packaging", packaging);
		paramMap.put("unit", unit);
		paramMap.put("makearea", makearea);
		@SuppressWarnings("unchecked")
		List<ProductDto> list = getSqlMapClientTemplate().queryForList("queryProductByLogicId", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertWarehouse(WarehouseDto warehouse) {
		getSqlMapClientTemplate().insert("insertWarehouse", warehouse);
	}

	@Override
	public void updateWarehouse(WarehouseDto warehouse) {
		getSqlMapClientTemplate().update("updateWarehouse", warehouse);
	}

	@Override
	public void deleteWarehouseByParentid(String parentid, String productid,
			String status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public WarehouseDto queryWarehouseByID(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		@SuppressWarnings("unchecked")
		List<WarehouseDto> list = getSqlMapClientTemplate().queryForList("queryWarehouseByID", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public WarehouseDto queryWarehouseByWarehouseno(String warehouseno) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("warehouseno", warehouseno);
		@SuppressWarnings("unchecked")
		List<WarehouseDto> list = getSqlMapClientTemplate().queryForList("queryWarehouseByWarehouseno", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public Double queryAmountByProductId(String productid){
//		System.out.println("productid: " + productid);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productid", productid);
		return (Double) getSqlMapClientTemplate().queryForObject("queryAmountByProductId", paramMap);		
	}


}
