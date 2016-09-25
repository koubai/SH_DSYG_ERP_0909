package com.cn.dsyg.dao;

import java.util.List;

import com.cn.dsyg.dto.OrderDto;

/**
 * OrderDao
 * @author Frank
 * @version 1.0
 * @create 2016-8-5下午4:05:17
 */
public interface OrderDao {
	
	/**
	 * 翻页查询数据
	 * @param ordercode
	 * @param customerid
	 * @param status
	 * @param start
	 * @param end
	 * @return
	 */
	public List<OrderDto> queryOrderByPage(String ordercode, String customerid, String status, int start, int end);
	
	/**
	 * 查询总记录数
	 * @param ordercode
	 * @param customerid
	 * @param status
	 * @return
	 */
	public int queryOrderCountByPage(String ordercode, String customerid, String status);

	/**
	 * 根据ID查询数据
	 * @param id
	 * @return
	 */
	public OrderDto queryOrderByID(String id);
	
	/**
	 * 根据逻辑主键查询数据
	 * @param ordercode
	 * @return
	 */
	public OrderDto queryOrderByOrdercode(String ordercode);
	
	/**
	 * 新增数据
	 * @param order
	 */
	public void insertOrder(OrderDto order);
	
	/**
	 * 修改数据
	 * @param order
	 */
	public void updateOrder(OrderDto order);
}
