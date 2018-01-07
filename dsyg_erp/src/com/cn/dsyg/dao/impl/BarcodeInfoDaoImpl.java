package com.cn.dsyg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.common.dao.BaseDao;
import com.cn.dsyg.dao.BarcodeInfoDao;
import com.cn.dsyg.dto.BarcodeInfoDto;

public class BarcodeInfoDaoImpl extends BaseDao implements BarcodeInfoDao {

	@Override
	public int queryBarcodeInfoCountByPage(String productid, String batchno, String barcode, String barcodetype,
			String operatetype) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productid", productid);
		paramMap.put("batchno", batchno);
		paramMap.put("barcode", barcode);
		paramMap.put("barcodetype", barcodetype);
		paramMap.put("operatetype", operatetype);
		return (Integer) getSqlMapClientTemplate().queryForObject("queryBarcodeInfoCountByPage", paramMap);
	}

	@Override
	public List<BarcodeInfoDto> queryBarcodeInfoByPage(String productid, String batchno, String barcode,
			String barcodetype, String operatetype, int start, int end) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productid", productid);
		paramMap.put("batchno", batchno);
		paramMap.put("barcode", barcode);
		paramMap.put("barcodetype", barcodetype);
		paramMap.put("operatetype", operatetype);
		paramMap.put("start", start);
		paramMap.put("end", end);
		@SuppressWarnings("unchecked")
		List<BarcodeInfoDto> list = getSqlMapClientTemplate().queryForList("queryBarcodeInfoByPage", paramMap);
		return list;
	}

	@Override
	public List<BarcodeInfoDto> queryAllBarcodeInfoList(String productid, String batchno, String barcodetype) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productid", productid);
		paramMap.put("batchno", batchno);
		paramMap.put("barcodetype", barcodetype);
		@SuppressWarnings("unchecked")
		List<BarcodeInfoDto> list = getSqlMapClientTemplate().queryForList("queryAllBarcodeInfoList", paramMap);
		return list;
	}

	@Override
	public BarcodeInfoDto queryBarcodeInfoByID(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		@SuppressWarnings("unchecked")
		List<BarcodeInfoDto> list = getSqlMapClientTemplate().queryForList("queryBarcodeInfoByID", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertBarcodeInfo(BarcodeInfoDto barcodeInfo) {
		getSqlMapClientTemplate().insert("insertBarcodeInfo", barcodeInfo);
	}

	@Override
	public void updateBarcodeInfo(BarcodeInfoDto barcodeInfo) {
		getSqlMapClientTemplate().update("updateBarcodeInfo", barcodeInfo);
	}
}
