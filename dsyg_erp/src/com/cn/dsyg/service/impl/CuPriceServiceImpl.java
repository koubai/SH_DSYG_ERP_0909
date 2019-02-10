package com.cn.dsyg.service.impl;

import java.util.List;

import com.cn.common.util.Page;
import com.cn.dsyg.dao.CuPriceDao;
import com.cn.dsyg.dto.CuPriceDto;
import com.cn.dsyg.service.CuPriceService;

public class CuPriceServiceImpl implements CuPriceService {
	
	private CuPriceDao cuPriceDao;

	@Override
	public Page queryCuPriceByPage(String cu_price_code, Page page) {
		int totalCount = cuPriceDao.queryCuPriceCountByPage(cu_price_code);
		page.setTotalCount(totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		//翻页查询记录
		List<CuPriceDto> list = cuPriceDao.queryCuPriceByPage(cu_price_code,
				page.getStartIndex() * page.getPageSize(), page.getPageSize());
		page.setItems(list);
		return page;
	}

	@Override
	public CuPriceDto queryCuPriceByID(String id) {
		return cuPriceDao.queryCuPriceByID(id);
	}

	@Override
	public CuPriceDto queryCuPriceByLogicId(String setdate) {
		return cuPriceDao.queryCuPriceByLogicId(setdate);
	}

	@Override
	public CuPriceDto queryLastCuPriceBySetDate(String setdate) {
		return cuPriceDao.queryLastCuPriceBySetDate(setdate);
	}

	@Override
	public void deleteCuPrice(String id) {
		cuPriceDao.deleteCuPrice(id);
	}

	@Override
	public void insertCuPrice(CuPriceDto cuPrice) {
		cuPriceDao.insertCuPrice(cuPrice);
	}

	@Override
	public void updateCuPrice(CuPriceDto cuPrice) {
		cuPriceDao.updateCuPrice(cuPrice);
	}

	public CuPriceDao getCuPriceDao() {
		return cuPriceDao;
	}

	public void setCuPriceDao(CuPriceDao cuPriceDao) {
		this.cuPriceDao = cuPriceDao;
	}

}
