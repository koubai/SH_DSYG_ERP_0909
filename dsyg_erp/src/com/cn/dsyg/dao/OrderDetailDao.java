package com.cn.dsyg.dao;

import java.util.List;

import com.cn.dsyg.dto.OrderDetailDto;

/**
 * OrderDetailDao
 * @author Frank
 * @version 1.0
 * @create 2016-8-5下午4:05:17
 */
public interface OrderDetailDao {
	
	/**
	 * 根据订单ID查询所有明细数据
	 * @param orderid
	 * @return
	 */
	public List<OrderDetailDto> queryOrderDetailByOrderid(String orderid);
	
	/**
	 * 根据ID查询数据
	 * @param id
	 * @return
	 */
	public OrderDetailDto queryOrderDetailByID(String id);
	
	/**
	 * 根据订单号逻辑删除明细数据
	 * @param updateuid
	 * @param orderid
	 */
	public void deleteAllOrderDetailByOrderid(String updateuid, String orderid);
	
	/**
	 * 根据订单号物理删除数据
	 * @param orderDetail
	 */
	public void deleteOrderDetailByOrderid(String orderid);
	
	/**
	 * 新增数据
	 * @param orderDetail
	 */
	public void insertOrderDetail(OrderDetailDto orderDetail);
	
	/**
	 * 修改数据
	 * @param orderDetail
	 */
	public void updateOrderDetail(OrderDetailDto orderDetail);
}
