package com.cn.dsyg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.common.dao.BaseDao;
import com.cn.dsyg.dao.WarehouserptHistDao;
import com.cn.dsyg.dto.WarehouserptHistDto;

public class WarehouserptHistDaoImpl extends BaseDao implements WarehouserptHistDao {

	@Override
	public List<WarehouserptHistDto> queryWarehouserpthistByRprid(String warehousetype, String warehouseno,
			String rptid, String histtype, String createdateLow, String createdateHigh) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("warehousetype", warehousetype);
		paramMap.put("warehouseno", warehouseno);
		paramMap.put("rptid", rptid);
		paramMap.put("histtype", histtype);
		paramMap.put("createdateLow", createdateLow);
		paramMap.put("createdateHigh", createdateHigh);
		@SuppressWarnings("unchecked")
		List<WarehouserptHistDto> list = getSqlMapClientTemplate().queryForList("queryWarehouserpthistByRprid", paramMap);
		return list;
	}

	@Override
	public void insertWarehouserpthist(WarehouserptHistDto hist) {
		getSqlMapClientTemplate().insert("insertWarehouserpthist", hist);
	}
}
