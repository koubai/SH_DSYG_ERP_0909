package com.cn.dsyg.service;

import java.util.List;

import com.cn.common.util.Page;
import com.cn.dsyg.dto.BarcodeLogDto;

public interface BarcodeLogService {

	/**
	 * 分页查询数据
	 * @param productid
	 * @param batchno
	 * @param barcode
	 * @param barcodetype
	 * @param operatetype
	 * @param page
	 * @return
	 */
	public Page queryBarcodeLogByPage(String productid, String batchno,
			String barcode, String barcodetype, String operatetype, Page page);
	
	/**
	 * 根据条件查询所有记录
	 * @param productid
	 * @param batchno
	 * @param barcodetype
	 * @return
	 */
	public List<BarcodeLogDto> queryAllBarcodeLogList(String productid, String batchno, String barcodetype);
	
	/**
	 * 根据ID查询数据
	 * @param id
	 * @return
	 */
	public BarcodeLogDto queryBarcodeLogByID(String id);
	
	/**
	 * 插入数据
	 * @param barcodeLog
	 */
	public void insertBarcodeLog(BarcodeLogDto barcodeLog);
	
	/**
	 * 更新数据
	 * @param barcodeLog
	 */
	public void updateBarcodeLog(BarcodeLogDto barcodeLog);
}
