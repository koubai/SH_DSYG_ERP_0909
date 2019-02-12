package com.cn.dsyg.service.impl;

import java.util.List;

import com.cn.common.util.Constants;
import com.cn.common.util.Page;
import com.cn.dsyg.dao.CuPriceDao;
import com.cn.dsyg.dto.CuPriceDto;
import com.cn.dsyg.service.CuPriceService;

public class CuPriceServiceImpl implements CuPriceService {
	
	private CuPriceDao cuPriceDao;

	@Override
	public Page queryCuPriceByPage(String setdateLow, String setdateHigh, String cuPriceCode, Page page) {
		int totalCount = cuPriceDao.queryCuPriceCountByPage(setdateLow, setdateHigh, cuPriceCode);
		page.setTotalCount(totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		//翻页查询记录
		List<CuPriceDto> list = cuPriceDao.queryCuPriceByPage(setdateLow, setdateHigh, cuPriceCode,
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
	public void deleteCuPriceLogic(String id, String userid) {
		CuPriceDto cuPrice = cuPriceDao.queryCuPriceByID(id);
		if(cuPrice != null) {
			cuPrice.setStatus(Constants.STATUS_DEL);
			cuPrice.setUpdateuid(userid);
			cuPriceDao.updateCuPrice(cuPrice);
		}
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
