package com.cn.dsyg.service;

import java.util.List;

import com.cn.common.util.Page;
import com.cn.dsyg.dto.BarcodeInfoDto;

public interface BarcodeInfoService {
	
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
	public Page queryBarcodeInfoByPage(String productid, String batchno,
			String barcode, String barcodetype, String operatetype, Page page);
	
	/**
	 * 根据条件查询所有记录
	 * @param productid
	 * @param batchno
	 * @param barcodetype
	 * @return
	 */
	public List<BarcodeInfoDto> queryAllBarcodeInfoList(String productid, String batchno, String barcodetype);
	
	/**
	 * 根据ID查询数据
	 * @param id
	 * @return
	 */
	public BarcodeInfoDto queryBarcodeInfoByID(String id);
	
	/**
	 * 插入数据
	 * @param barcodeInfo
	 */
	public void insertBarcodeInfo(BarcodeInfoDto barcodeInfo);
	
	/**
	 * 更新数据
	 * @param barcodeInfo
	 */
	public void updateBarcodeInfo(BarcodeInfoDto barcodeInfo);
}
