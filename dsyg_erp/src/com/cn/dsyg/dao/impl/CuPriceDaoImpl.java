package com.cn.dsyg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.common.dao.BaseDao;
import com.cn.dsyg.dao.CuPriceDao;
import com.cn.dsyg.dto.CuPriceDto;

public class CuPriceDaoImpl extends BaseDao implements CuPriceDao {

	@Override
	public int queryCuPriceCountByPage(String cu_price_code) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cu_price_code", cu_price_code);
		return (Integer) getSqlMapClientTemplate().queryForObject("queryCuPriceCountByPage", paramMap);
	}

	@Override
	public List<CuPriceDto> queryCuPriceByPage(String cu_price_code, int start, int end) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cu_price_code", cu_price_code);
		paramMap.put("start", start);
		paramMap.put("end", end);
		@SuppressWarnings("unchecked")
		List<CuPriceDto> list = getSqlMapClientTemplate().queryForList("queryCuPriceByPage", paramMap);
		return list;
	}

	@Override
	public CuPriceDto queryCuPriceByID(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		@SuppressWarnings("unchecked")
		List<CuPriceDto> list = getSqlMapClientTemplate().queryForList("queryCuPriceByID", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public CuPriceDto queryCuPriceByLogicId(String setdate) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("setdate", setdate);
		@SuppressWarnings("unchecked")
		List<CuPriceDto> list = getSqlMapClientTemplate().queryForList("queryCuPriceByLogicId", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public CuPriceDto queryLastCuPriceBySetDate(String setdate) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("setdate", setdate);
		@SuppressWarnings("unchecked")
		List<CuPriceDto> list = getSqlMapClientTemplate().queryForList("queryLastCuPriceBySetDate", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void deleteCuPrice(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		getSqlMapClientTemplate().delete("deleteCuPrice", paramMap);
	}

	@Override
	public void insertCuPrice(CuPriceDto cuPrice) {
		getSqlMapClientTemplate().insert("insertCuPrice", cuPrice);
	}

	@Override
	public void updateCuPrice(CuPriceDto cuPrice) {
		getSqlMapClientTemplate().update("updateCuPrice", cuPrice);
	}
}
