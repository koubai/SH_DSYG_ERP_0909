package com.cn.dsyg.service.impl;

import java.util.List;

import com.cn.common.util.Page;
import com.cn.dsyg.dao.SalesHisDao;
import com.cn.dsyg.dto.SalesDto;
import com.cn.dsyg.service.SalesHisService;

public class SalesHisServiceImpl implements SalesHisService {
	
	private SalesHisDao salesHisDao;

	@Override
	public Page querySalesHisByPage(String salesNo, Page page) {
		//查询总记录数
		int totalCount = salesHisDao.querySalesHisCountByPage(salesNo);
		page.setTotalCount(totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		//翻页查询记录
		List<SalesDto> list = salesHisDao.querySalesHisByPage(salesNo, page.getStartIndex() * page.getPageSize(), page.getPageSize());
		page.setItems(list);
		return page;
	}

	@Override
	public SalesDto querySalesHisByID(String id) {
		return salesHisDao.querySalesHisByID(id);
	}

	@Override
	public void insertSalesHis(SalesDto salesDto) {
		salesHisDao.insertSalesHis(salesDto);
	}

	public SalesHisDao getSalesHisDao() {
		return salesHisDao;
	}

	public void setSalesHisDao(SalesHisDao salesHisDao) {
		this.salesHisDao = salesHisDao;
	}

}
