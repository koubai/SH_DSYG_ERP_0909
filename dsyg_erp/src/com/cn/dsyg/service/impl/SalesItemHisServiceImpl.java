package com.cn.dsyg.service.impl;

import java.util.List;

import com.cn.dsyg.dao.SalesItemHisDao;
import com.cn.dsyg.dto.SalesItemDto;
import com.cn.dsyg.service.SalesItemHisService;

public class SalesItemHisServiceImpl implements SalesItemHisService {
	
	private SalesItemHisDao salesItemHisDao;

	@Override
	public List<SalesItemDto> querySalesItemHisBySalesid(String id) {
		return salesItemHisDao.querySalesItemhisBySalesid(id);
	}

	@Override
	public SalesItemDto querySalesItemHisByID(String id) {
		return salesItemHisDao.querySalesItemHisByID(id);
	}

	@Override
	public void insertSalesItemHis(SalesItemDto salesItem) {
		salesItemHisDao.insertSalesItemHis(salesItem);
	}

	public SalesItemHisDao getSalesItemHisDao() {
		return salesItemHisDao;
	}

	public void setSalesItemHisDao(SalesItemHisDao salesItemHisDao) {
		this.salesItemHisDao = salesItemHisDao;
	}

}
