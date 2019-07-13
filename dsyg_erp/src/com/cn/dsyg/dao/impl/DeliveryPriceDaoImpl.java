package com.cn.dsyg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.common.dao.BaseDao;
import com.cn.dsyg.dao.DeliveryPriceDao;
import com.cn.dsyg.dto.DeliveryPriceDto;

/**
 * @name 
 * @author 
 * @time 
 * @version 1.0
 */
public class DeliveryPriceDaoImpl extends BaseDao implements DeliveryPriceDao {

	@Override
	public List<DeliveryPriceDto> queryDeliveryPriceByDeliveryID(String deliveryId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ID", deliveryId);
		@SuppressWarnings("unchecked")
		List<DeliveryPriceDto> list = getSqlMapClientTemplate().queryForList("queryDeliveryPriceByDeliveryID", paramMap);
		return list;
	}

	@Override
	public List<DeliveryPriceDto> queryAllDeliveryPrice() {
		@SuppressWarnings("unchecked")
		List<DeliveryPriceDto> list = getSqlMapClientTemplate().queryForList("queryAllDeliveryPrice");
		return list;
	}

	@Override
	public void insertDeliveryPrice(DeliveryPriceDto delivery) {
		getSqlMapClientTemplate().insert("insertDeliveryPrice", delivery);
	}

	@Override
	public void updateDeliveryPrice(DeliveryPriceDto delivery) {
		getSqlMapClientTemplate().update("updateDeliveryPrice", delivery);
	}

	@Override
	public void delDeliveryPrice(String deliveryId, String userid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ID", deliveryId);
		paramMap.put("updateuid", userid);
		getSqlMapClientTemplate().update("deletePriceItemByDeliveryId", paramMap);
	}
}
