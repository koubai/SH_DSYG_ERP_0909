package com.cn.dsyg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.common.dao.BaseDao;
import com.cn.dsyg.dao.OrderDetailDao;
import com.cn.dsyg.dto.OrderDetailDto;

/**
 * @name OrderDetailDaoImpl.java
 * @author Frank
 * @time 2016-9-4下午2:55:18
 * @version 1.0
 */
public class OrderDetailDaoImpl extends BaseDao implements OrderDetailDao {

	@Override
	public List<OrderDetailDto> queryOrderDetailByOrderid(String orderid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderid", orderid);
		@SuppressWarnings("unchecked")
		List<OrderDetailDto> list = getSqlMapClientTemplate().queryForList("queryOrderDetailByOrderid", paramMap);
		return list;
	}

	@Override
	public OrderDetailDto queryOrderDetailByID(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		@SuppressWarnings("unchecked")
		List<OrderDetailDto> list = getSqlMapClientTemplate().queryForList("queryOrderDetailByID", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void deleteAllOrderDetailByOrderid(String updateuid, String orderid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("updateuid", updateuid);
		paramMap.put("orderid", orderid);
		getSqlMapClientTemplate().update("deleteAllOrderDetailByOrderid", paramMap);
	}

	@Override
	public void deleteOrderDetailByOrderid(String orderid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderid", orderid);
		getSqlMapClientTemplate().delete("deleteOrderDetailByOrderid", paramMap);
	}

	@Override
	public void insertOrderDetail(OrderDetailDto orderDetail) {
		getSqlMapClientTemplate().insert("insertOrderDetail", orderDetail);
	}

	@Override
	public void updateOrderDetail(OrderDetailDto orderDetail) {
		getSqlMapClientTemplate().update("updateOrderDetail", orderDetail);
	}
}
