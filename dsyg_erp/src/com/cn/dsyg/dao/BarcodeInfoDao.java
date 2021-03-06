package com.cn.dsyg.dao;

import java.util.List;

import com.cn.dsyg.dto.BarcodeInfoDto;

public interface BarcodeInfoDao {
	
	/**
	 * 根据条形码编号，查询较小编号的条形码列表
	 * @param productid
	 * @param operatetype
	 * @param barcodeno
	 * @param barcodetype
	 * @return
	 */
	public List<BarcodeInfoDto> queryBarcodeInfoListLessBarcodeno(String productid, String operatetype, String barcodeno, String barcodetype);
	
	/**
	 * 查询数据数量
	 * @param productid
	 * @param tradename
	 * @param batchno
	 * @param barcode
	 * @param barcodetype
	 * @param operatetype
	 * @return
	 */
	public int queryBarcodeInfoCountByPage(String productid, String tradename, String batchno,
			String barcode, String barcodetype, String operatetype);
	
	/**
	 * 分页查询数据
	 * @param productid
	 * @param tradename
	 * @param batchno
	 * @param barcode
	 * @param barcodetype
	 * @param operatetype
	 * @param start
	 * @param end
	 * @return
	 */
	public List<BarcodeInfoDto> queryBarcodeInfoByPage(String productid, String tradename, String batchno,
			String barcode, String barcodetype, String operatetype, int start, int end);
	
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
