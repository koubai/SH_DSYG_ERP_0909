package com.cn.dsyg.dao;

import java.util.List;

import com.cn.dsyg.dto.DeliveryPriceDto;

/**
 * @name 
 * @author 
 * @time 
 * @version 1.0
 */
public interface DeliveryPriceDao {

	/**
	 * 根据快递ID查询快递单价记录（查询有效记录）
	 * @param deliveryID
	 * @return
	 */
	public List<DeliveryPriceDto> queryDeliveryPriceByDeliveryID(String deliveryID);
	
	/**
	 * 查询所有的快递单价记录
	 * @return
	 */
	public List<DeliveryPriceDto> queryAllDeliveryPrice();
	
	/**
	 * 根据起点终点查询快递费用
	 * @param marketcity 起点
	 * @param arrivalcity 终点
	 * @return
	 */
	public List<DeliveryPriceDto> queryDeliveryPriceByCondition(String marketcity, String arrivalcity);
	
	/**
	 * 删除快递单价记录
	 * @param deliveryId
	 * @param userid
	 */
	public void delDeliveryPrice(String deliveryId, String userid);
	
	/**
	 * 新增快递单价记录
	 * @param deliveryPrice
	 */
	public void insertDeliveryPrice(DeliveryPriceDto deliveryPrice);
	
	/**
	 * 修改快递单价记录
	 * @param delivery
	 */
	public void updateDeliveryPrice(DeliveryPriceDto deliveryPrice);
}
