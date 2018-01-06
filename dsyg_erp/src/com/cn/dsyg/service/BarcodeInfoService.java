package com.cn.dsyg.service;

import java.util.List;

import com.cn.common.util.Page;
import com.cn.dsyg.dto.BarcodeInfoDto;

public interface BarcodeInfoService {
	
	/**
	 * 批量生成条形码
	 * @param productids
	 * @param barcodeSeq
	 * @param barcodeQuantity
	 * @param productItem14
	 * @param userid
	 * @return
	 */
	public List<BarcodeInfoDto> createBarcodeBatch(String productids, String barcodeSeq,
			String barcodeQuantity, String productItem14, String userid);
	
	/**
	 * 分页查询数据
	 * @param productid
	 * @param batchno
	 * @param barcodetype
	 * @param createdateHigh
	 * @param createdateLow
	 * @param page
	 * @return
	 */
	public Page queryBarcodeInfoByPage(String productid, String batchno, String barcodetype,
			String createdateHigh, String createdateLow, Page page);
	
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
	 * 根据逻辑主键查询数据
	 * @param batchno
	 * @return
	 */
	public BarcodeInfoDto queryBarcodeInfoByLogicID(String batchno);
	
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
