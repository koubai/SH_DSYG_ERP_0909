package com.cn.dsyg.service.impl;

import java.util.List;

import com.cn.common.util.Page;
import com.cn.dsyg.dao.BarcodeLogDao;
import com.cn.dsyg.dto.BarcodeLogDto;
import com.cn.dsyg.service.BarcodeLogService;

public class BarcodeLogServiceImpl implements BarcodeLogService {
	
	private BarcodeLogDao barcodeLogDao;

	@Override
	public Page queryBarcodeLogByPage(String productid, String batchno, String barcode, String barcodetype,
			String operatetype, Page page) {
		//查询总记录数
		int totalCount = barcodeLogDao.queryBarcodeLogCountByPage(productid, batchno, barcode, barcodetype, operatetype);
		page.setTotalCount(totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		//翻页查询记录
		List<BarcodeLogDto> list = barcodeLogDao.queryBarcodeLogByPage(productid, batchno, barcode, barcodetype, operatetype,
				page.getStartIndex() * page.getPageSize(), page.getPageSize());
		page.setItems(list);
		return page;
	}

	@Override
	public List<BarcodeLogDto> queryAllBarcodeLogList(String productid, String batchno, String barcodetype) {
		return barcodeLogDao.queryAllBarcodeLogList(productid, batchno, barcodetype);
	}

	@Override
	public BarcodeLogDto queryBarcodeLogByID(String id) {
		return barcodeLogDao.queryBarcodeLogByID(id);
	}

	@Override
	public void insertBarcodeLog(BarcodeLogDto barcodeLog) {
		barcodeLogDao.insertBarcodeLog(barcodeLog);
	}

	@Override
	public void updateBarcodeLog(BarcodeLogDto barcodeLog) {
		barcodeLogDao.updateBarcodeLog(barcodeLog);
	}

	public BarcodeLogDao getBarcodeLogDao() {
		return barcodeLogDao;
	}

	public void setBarcodeLogDao(BarcodeLogDao barcodeLogDao) {
		this.barcodeLogDao = barcodeLogDao;
	}
}
