package com.cn.dsyg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.common.dao.BaseDao;
import com.cn.dsyg.dao.SalesItemHisDao;
import com.cn.dsyg.dto.SalesItemDto;

public class SalesItemHisDaoImpl extends BaseDao implements SalesItemHisDao {

	@Override
	public List<SalesItemDto> querySalesItemhisBySalesid(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("salesid", id);
		@SuppressWarnings("unchecked")
		List<SalesItemDto> list = getSqlMapClientTemplate().queryForList("querySalesItemHisBySalesid", paramMap);
		return list;
	}

	@Override
	public SalesItemDto querySalesItemHisByID(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		List<SalesItemDto> list = getSqlMapClientTemplate().queryForList("querySalesItemHisByID", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertSalesItemHis(SalesItemDto salesItem) {
		getSqlMapClientTemplate().insert("insertSalesItemHis", salesItem);
	}

}
