package com.cn.dsyg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.common.dao.BaseDao;
import com.cn.dsyg.dao.SalesHisDao;
import com.cn.dsyg.dto.SalesDto;

public class SalesHisDaoImpl extends BaseDao implements SalesHisDao {

	@Override
	public int querySalesHisCountByPage(String salesNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("salesno", salesNo);
		return (Integer) getSqlMapClientTemplate().queryForObject("querySalesHisCountByPage", paramMap);
	}

	@Override
	public List<SalesDto> querySalesHisByPage(String salesNo, int start, int end) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("salesno", salesNo);
		paramMap.put("start", start);
		paramMap.put("end", end);
		@SuppressWarnings("unchecked")
		List<SalesDto> list = getSqlMapClientTemplate().queryForList("querySalesHisByPage", paramMap);
		return list;
	}

	@Override
	public SalesDto querySalesHisByID(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		@SuppressWarnings("unchecked")
		List<SalesDto> list = getSqlMapClientTemplate().queryForList("querySalesHisByID", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public long insertSalesHis(SalesDto salesDto) {
		return (Long) getSqlMapClientTemplate().insert("insertSalesHis", salesDto);
	}

}
