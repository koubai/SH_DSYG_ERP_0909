package com.cn.dsyg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.common.dao.BaseDao;
import com.cn.dsyg.dao.WarehouseSZDao;
import com.cn.dsyg.dto.ProductDto;
import com.cn.dsyg.dto.WarehouseDetailDto;

/**
 * @name WarehouseSZDaoImpl.java
 * @author Frank
 * @time 2017-1-8下午12:50:13
 * @version 1.0
 */
public class WarehouseSZDaoImpl extends BaseDao implements WarehouseSZDao {
	
	@Override
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
	public WarehouseDetailDto queryWarehouseSZDetail(String parentid,
			String keyword, String warehousetype, String warehouseno,
			String theme1, String productid, String tradename, String typeno,
			String color, String warehousename, String zerodisplay) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("parentid", parentid);
		paramMap.put("keyword", keyword);
		paramMap.put("warehousetype", warehousetype);
		paramMap.put("warehouseno", warehouseno);
		paramMap.put("theme1", theme1);
		paramMap.put("productid", productid);
		paramMap.put("tradename", tradename);
		paramMap.put("typeno", typeno);
		paramMap.put("color", color);
		paramMap.put("warehousename", warehousename);
		System.out.println("zerodisplay:" + zerodisplay);
		if (zerodisplay == null)
			zerodisplay="";
		if (zerodisplay.equals("0"))
			paramMap.put("zerodisplay", zerodisplay);
		@SuppressWarnings("unchecked")
		List<WarehouseDetailDto> list = getSqlMapClientTemplate().queryForList("queryWarehouseSZDetail", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
