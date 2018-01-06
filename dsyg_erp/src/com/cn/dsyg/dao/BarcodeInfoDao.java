package com.cn.dsyg.dao;

import java.util.List;

import com.cn.dsyg.dto.BarcodeInfoDto;

public interface BarcodeInfoDao {

	/**
	 * 查询数据数量
	 * @param productid
	 * @param batchno
	 * @param barcodetype
	 * @param createdateHigh
	 * @param createdateLow
	 * @return
	 */
	public int queryBarcodeInfoCountByPage(String productid, String batchno,
			String barcodetype, String createdateHigh, String createdateLow);
	
	/**
	 * 分页查询数据
	 * @param productid
	 * @param batchno
	 * @param barcodetype
	 * @param createdateHigh
	 * @param createdateLow
	 * @param start
	 * @param end
	 * @return
	 */
	public List<BarcodeInfoDto> queryBarcodeInfoByPage(String productid, String batchno, String barcodetype,
			String createdateHigh, String createdateLow, int start, int end);
	
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
