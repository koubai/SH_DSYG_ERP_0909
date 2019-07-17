package com.cn.dsyg.service.impl;

import java.util.List;

import com.cn.dsyg.dao.DeliveryPriceDao;
import com.cn.dsyg.dto.DeliveryPriceDto;
import com.cn.dsyg.service.DeliveryPriceService;

public class DeliveryPriceServiceImpl implements DeliveryPriceService {
	
	private DeliveryPriceDao deliveryPriceDao;

	@Override
	public List<DeliveryPriceDto> queryDeliveryPriceByDeliveryID(String deliveryID) {
		return deliveryPriceDao.queryDeliveryPriceByDeliveryID(deliveryID);
	}

	@Override
	public List<DeliveryPriceDto> queryAllDeliveryPrice() {
		return deliveryPriceDao.queryAllDeliveryPrice();
	}
	
	@Override
	public List<DeliveryPriceDto> queryDeliveryPriceByCondition(String marketcity, String arrivalcity) {
		return deliveryPriceDao.queryDeliveryPriceByCondition(marketcity, arrivalcity);
	}

	@Override
	public void delDeliveryPrice(String deliveryId, String userid) {
		deliveryPriceDao.delDeliveryPrice(deliveryId, userid);
	}

	@Override
	public void insertDeliveryPrice(DeliveryPriceDto deliveryPrice) {
		deliveryPriceDao.insertDeliveryPrice(deliveryPrice);
	}

	@Override
	public void updateDeliveryPrice(DeliveryPriceDto deliveryPrice) {
		deliveryPriceDao.updateDeliveryPrice(deliveryPrice);
	}

	public DeliveryPriceDao getDeliveryPriceDao() {
		return deliveryPriceDao;
	}

	public void setDeliveryPriceDao(DeliveryPriceDao deliveryPriceDao) {
		this.deliveryPriceDao = deliveryPriceDao;
	}
}
