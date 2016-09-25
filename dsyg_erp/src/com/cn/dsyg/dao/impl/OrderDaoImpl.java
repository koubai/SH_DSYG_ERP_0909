package com.cn.dsyg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.common.dao.BaseDao;
import com.cn.dsyg.dao.OrderDao;
import com.cn.dsyg.dto.OrderDto;

/**
 * @name OrderDaoImpl.java
 * @author Frank
 * @time 2016-9-4下午2:45:17
 * @version 1.0
 */
public class OrderDaoImpl extends BaseDao implements OrderDao {

	@Override
	public List<OrderDto> queryOrderByPage(String ordercode, String customerid,
			String status, int start, int end) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ordercode", ordercode);
		paramMap.put("customerid", customerid);
		paramMap.put("status", status);
		paramMap.put("start", start);
		paramMap.put("end", end);
		@SuppressWarnings("unchecked")
		List<OrderDto> list = getSqlMapClientTemplate().queryForList("queryOrderByPage", paramMap);
		return list;
	}

	@Override
	public int queryOrderCountByPage(String ordercode, String customerid,
			String status) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ordercode", ordercode);
		paramMap.put("customerid", customerid);
		paramMap.put("status", status);
		return (Integer) getSqlMapClientTemplate().queryForObject("queryOrderCountByPage", paramMap);
	}

	@Override
	public OrderDto queryOrderByID(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		@SuppressWarnings("unchecked")
		List<OrderDto> list = getSqlMapClientTemplate().queryForList("queryOrderByID", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public OrderDto queryOrderByOrdercode(String ordercode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ordercode", ordercode);
		@SuppressWarnings("unchecked")
		List<OrderDto> list = getSqlMapClientTemplate().queryForList("queryOrderByOrdercode", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertOrder(OrderDto order) {
		getSqlMapClientTemplate().insert("insertOrder", order);
	}

	@Override
	public void updateOrder(OrderDto order) {
		getSqlMapClientTemplate().update("updateOrder", order);
	}

}
