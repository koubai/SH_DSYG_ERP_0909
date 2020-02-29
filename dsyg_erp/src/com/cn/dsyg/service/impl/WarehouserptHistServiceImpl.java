package com.cn.dsyg.service.impl;

import java.util.List;

import com.cn.dsyg.dao.WarehouserptHistDao;
import com.cn.dsyg.dto.WarehouserptHistDto;
import com.cn.dsyg.service.WarehouserptHistService;

public class WarehouserptHistServiceImpl implements WarehouserptHistService {
	
	private WarehouserptHistDao warehouserptHistDao;

	@Override
	public List<WarehouserptHistDto> queryWarehouserpthistByRprid(String warehousetype, String warehouseno,
			String rptid, String histtype, String createdateLow, String createdateHigh) {
		return warehouserptHistDao.queryWarehouserpthistByRprid(warehousetype, warehouseno,
				rptid, histtype, createdateLow, createdateHigh);
	}

	@Override
	public void insertWarehouserpthist(WarehouserptHistDto hist) {
		warehouserptHistDao.insertWarehouserpthist(hist);
	}

	public WarehouserptHistDao getWarehouserptHistDao() {
		return warehouserptHistDao;
	}

	public void setWarehouserptHistDao(WarehouserptHistDao warehouserptHistDao) {
		this.warehouserptHistDao = warehouserptHistDao;
	}

}
