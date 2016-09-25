package com.cn.dsyg.service.impl;

import java.util.List;

import com.cn.dsyg.dao.OrderDetailDao;
import com.cn.dsyg.dto.OrderDetailDto;
import com.cn.dsyg.service.OrderDetailService;

/**
 * @name OrderDetailServiceImpl.java
 * @author Frank
 * @time 2016-9-4下午2:58:32
 * @version 1.0
 */
public class OrderDetailServiceImpl implements OrderDetailService {
	
	private OrderDetailDao orderDetailDao;

	@Override
	public List<OrderDetailDto> queryOrderDetailByOrderid(String orderid) {
		return orderDetailDao.queryOrderDetailByOrderid(orderid);
	}

	@Override
	public OrderDetailDto queryOrderDetailByID(String id) {
		return orderDetailDao.queryOrderDetailByID(id);
	}

	@Override
	public void deleteAllOrderDetailByOrderid(String updateuid, String orderid) {
		orderDetailDao.deleteAllOrderDetailByOrderid(updateuid, orderid);
	}

	@Override
	public void deleteOrderDetailByOrderid(String orderid) {
		orderDetailDao.deleteOrderDetailByOrderid(orderid);
	}

	@Override
	public void insertOrderDetail(OrderDetailDto orderDetail) {
		orderDetailDao.insertOrderDetail(orderDetail);
	}

	@Override
	public void updateOrderDetail(OrderDetailDto orderDetail) {
		orderDetailDao.updateOrderDetail(orderDetail);
	}

	public OrderDetailDao getOrderDetailDao() {
		return orderDetailDao;
	}

	public void setOrderDetailDao(OrderDetailDao orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
	}
}
