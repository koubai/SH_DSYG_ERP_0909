package com.cn.dsyg.dao;

import java.util.List;

import com.cn.dsyg.dto.BarcodeLogDto;

public interface BarcodeLogDao {

	/**
	 * 查询数据数量
	 * @param productid
	 * @param batchno
	 * @param barcode
	 * @param barcodetype
	 * @param operatetype
	 * @return
	 */
	public int queryBarcodeLogCountByPage(String productid, String batchno,
			String barcode, String barcodetype, String operatetype);
	
	/**
	 * 分页查询数据
	 * @param productid
	 * @param batchno
	 * @param barcode
	 * @param barcodetype
	 * @param operatetype
	 * @param start
	 * @param end
	 * @return
	 */
	public List<BarcodeLogDto> queryBarcodeLogByPage(String productid, String batchno,
			String barcode, String barcodetype, String operatetype, int start, int end);
	
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
