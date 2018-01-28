package com.cn.dsyg.service;

import java.util.List;

import com.cn.common.util.Page;
import com.cn.dsyg.dto.BarcodeInfoDto;

public interface BarcodeInfoService {
	
	/**
	 * 条形码入库
	 * @param barcodeList
	 * @param userid
	 * @return
	 */
	public List<BarcodeInfoDto> barcodeInfoInBatch(String[] barcodeList, String userid);
	
	
	/**
	 * 条形码入库SUB
	 * @param barcode
	 * @param productid
	 * @param userid
	 * @param areano
	 * @param barcodeno
	 * @param scanno
	 * @param barcodenote
	 * @param userid
	 * @return
	 */
	public BarcodeInfoDto barcodeInfoInBatchSub(String barcode, String productid, String userid, String areano, String barcodeno, String scanno, String barcodenote);

	/**
	 * 分页查询数据
	 * @param productid
	 * @param tradename
	 * @param batchno
	 * @param barcode
	 * @param barcodetype
	 * @param operatetype
	 * @param page
	 * @return
	 */
	public Page queryBarcodeInfoByPage(String productid, String tradename, String batchno,
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
	 * 根据逻辑主键查询数据
	 * @param barcode
	 * @return
	 */
	public BarcodeInfoDto queryBarcodeInfoByLogicId(String barcode);
	
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
