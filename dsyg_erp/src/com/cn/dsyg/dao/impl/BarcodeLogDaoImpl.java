package com.cn.dsyg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.common.dao.BaseDao;
import com.cn.dsyg.dao.BarcodeLogDao;
import com.cn.dsyg.dto.BarcodeLogDto;

public class BarcodeLogDaoImpl extends BaseDao implements BarcodeLogDao {

	@Override
	public int queryBarcodeLogCountByPage(String productid, String batchno, String barcodetype, String createdateHigh,
			String createdateLow) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productid", productid);
		paramMap.put("batchno", batchno);
		paramMap.put("barcodetype", barcodetype);
		paramMap.put("createdateHigh", createdateHigh);
		paramMap.put("createdateLow", createdateLow);
		return (Integer) getSqlMapClientTemplate().queryForObject("queryBarcodeLogCountByPage", paramMap);
	}

	@Override
	public List<BarcodeLogDto> queryBarcodeLogByPage(String productid, String batchno, String barcodetype,
			String createdateHigh, String createdateLow, int start, int end) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productid", productid);
		paramMap.put("batchno", batchno);
		paramMap.put("barcodetype", barcodetype);
		paramMap.put("createdateHigh", createdateHigh);
		paramMap.put("createdateLow", createdateLow);
		paramMap.put("start", start);
		paramMap.put("end", end);
		@SuppressWarnings("unchecked")
		List<BarcodeLogDto> list = getSqlMapClientTemplate().queryForList("queryBarcodeLogByPage", paramMap);
		return list;
	}

	@Override
	public List<BarcodeLogDto> queryAllBarcodeLogList(String productid, String batchno, String barcodetype) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productid", productid);
		paramMap.put("batchno", batchno);
		paramMap.put("barcodetype", barcodetype);
		@SuppressWarnings("unchecked")
		List<BarcodeLogDto> list = getSqlMapClientTemplate().queryForList("queryAllBarcodeLogList", paramMap);
		return list;
	}

	@Override
	public BarcodeLogDto queryBarcodeLogByID(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		@SuppressWarnings("unchecked")
		List<BarcodeLogDto> list = getSqlMapClientTemplate().queryForList("queryBarcodeLogByID", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public BarcodeLogDto queryBarcodeLogByLogicID(String batchno) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("batchno", batchno);
		@SuppressWarnings("unchecked")
		List<BarcodeLogDto> list = getSqlMapClientTemplate().queryForList("queryBarcodeLogByLogicID", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertBarcodeLog(BarcodeLogDto barcodeLog) {
		getSqlMapClientTemplate().insert("insertBarcodeLog", barcodeLog);
	}

	@Override
	public void updateBarcodeLog(BarcodeLogDto barcodeLog) {
		getSqlMapClientTemplate().update("updateBarcodeLog", barcodeLog);
	}
}
