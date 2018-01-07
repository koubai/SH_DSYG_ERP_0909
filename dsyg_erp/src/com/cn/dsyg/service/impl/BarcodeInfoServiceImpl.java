package com.cn.dsyg.service.impl;

import java.util.List;

import com.cn.common.util.Page;
import com.cn.dsyg.dao.BarcodeInfoDao;
import com.cn.dsyg.dto.BarcodeInfoDto;
import com.cn.dsyg.service.BarcodeInfoService;

public class BarcodeInfoServiceImpl implements BarcodeInfoService {
	
	private BarcodeInfoDao barcodeInfoDao;

	@Override
	public Page queryBarcodeInfoByPage(String productid, String batchno, String barcode, String barcodetype,
			String operatetype, Page page) {
		//查询总记录数
		int totalCount = barcodeInfoDao.queryBarcodeInfoCountByPage(productid, batchno, barcode, barcodetype, operatetype);
		page.setTotalCount(totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		//翻页查询记录
		List<BarcodeInfoDto> list = barcodeInfoDao.queryBarcodeInfoByPage(productid, batchno, barcode, barcodetype, operatetype,
				page.getStartIndex() * page.getPageSize(), page.getPageSize());
		page.setItems(list);
		return page;
	}

	@Override
	public List<BarcodeInfoDto> queryAllBarcodeInfoList(String productid, String batchno, String barcodetype) {
		return barcodeInfoDao.queryAllBarcodeInfoList(productid, batchno, barcodetype);
	}

	@Override
	public BarcodeInfoDto queryBarcodeInfoByID(String id) {
		return barcodeInfoDao.queryBarcodeInfoByID(id);
	}

	@Override
	public void insertBarcodeInfo(BarcodeInfoDto BarcodeInfo) {
		barcodeInfoDao.insertBarcodeInfo(BarcodeInfo);
	}

	@Override
	public void updateBarcodeInfo(BarcodeInfoDto BarcodeInfo) {
		barcodeInfoDao.updateBarcodeInfo(BarcodeInfo);
	}

	public BarcodeInfoDao getBarcodeInfoDao() {
		return barcodeInfoDao;
	}

	public void setBarcodeInfoDao(BarcodeInfoDao barcodeInfoDao) {
		this.barcodeInfoDao = barcodeInfoDao;
	}
}
