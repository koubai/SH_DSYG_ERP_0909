package com.cn.dsyg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.common.dao.BaseDao;
import com.cn.common.util.StringUtil;
import com.cn.dsyg.dao.CustomerOnlineDao;
import com.cn.dsyg.dto.CustomerOnlineDto;

/**
 * @name 
 * @author 
 * @time 
 * @version 1.0
 */
public class CustomerOnlineDaoImpl extends BaseDao implements CustomerOnlineDao {

	@Override
	public CustomerOnlineDto queryAllCustomerOnlineByID(String customerId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ID", customerId);
		@SuppressWarnings("unchecked")
		List<CustomerOnlineDto> list = getSqlMapClientTemplate().queryForList("queryAllCustomerOnlineByID", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public CustomerOnlineDto queryAllCustomerOnlineByEmail(String customerEmail) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("CUSTOMER_EMAIL", customerEmail);
		@SuppressWarnings("unchecked")
		List<CustomerOnlineDto> list = getSqlMapClientTemplate().queryForList("queryAllCustomerOnlineByEmail", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<CustomerOnlineDto> queryCustomerOnlineByPage(String customerIdLow,
			String customerIdHigh, String customerEmail, String customerName,
			int start, int end) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//这里按照需求，若客户名称存在，则忽略客户代码按客户名称来查询。
		if(StringUtil.isNotBlank(customerEmail) || StringUtil.isNotBlank(customerName)) {
			paramMap.put("CUSTOMER_EMAIL", StringUtil.replaceDatabaseKeyword_mysql(customerEmail));
			paramMap.put("CUSTOMER_NAME", StringUtil.replaceDatabaseKeyword_mysql(customerName));
		} else {
			paramMap.put("ID_LOW", customerIdLow);
			paramMap.put("ID_HIGH", customerIdHigh);
		}
		paramMap.put("start", start);
		paramMap.put("end", end);
		@SuppressWarnings("unchecked")
		List<CustomerOnlineDto> list = getSqlMapClientTemplate().queryForList("queryCustomerOnlineByPage", paramMap);
		return list;
	}

	@Override
	public int queryCustomerOnlineCountByPage(String customerIdLow,
			String customerIdHigh, String customerEmail, String customerName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//这里按照需求，若客户名称存在，则忽略客户代码按客户名称来查询。
		if(StringUtil.isNotBlank(customerEmail) || StringUtil.isNotBlank(customerName)) {
			paramMap.put("CUSTOMER_EMAIL", StringUtil.replaceDatabaseKeyword_mysql(customerEmail));
			paramMap.put("CUSTOMER_NAME", StringUtil.replaceDatabaseKeyword_mysql(customerName));
		} else {
			paramMap.put("ID_LOW", customerIdLow);
			paramMap.put("ID_HIGH", customerIdHigh);
		}
		return (Integer) getSqlMapClientTemplate().queryForObject("queryCustomerOnlineCountByPage", paramMap);
	}

	@Override
	public CustomerOnlineDto queryCustomerOnlineByID(String customerId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ID", customerId);
		@SuppressWarnings("unchecked")
		List<CustomerOnlineDto> list = getSqlMapClientTemplate().queryForList("queryCustomerOnlineByID", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<CustomerOnlineDto> queryAllCustomerOnline() {
		@SuppressWarnings("unchecked")
		List<CustomerOnlineDto> list = getSqlMapClientTemplate().queryForList("queryAllCustomerOnline");
		return list;
	}

	@Override
	public void insertCustomerOnline(CustomerOnlineDto customer) {
		getSqlMapClientTemplate().insert("insertCustomerOnline", customer);
	}

	@Override
	public void updateCustomerOnline(CustomerOnlineDto customer) {
		getSqlMapClientTemplate().update("updateCustomerOnline", customer);
	}

	@Override
	public List<CustomerOnlineDto> queryAllCustomerOnlineExport(String customerIdLow,
			String customerIdHigh) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ID_LOW", customerIdLow);
		paramMap.put("ID_HIGH", customerIdHigh);
		@SuppressWarnings("unchecked")
		List<CustomerOnlineDto> list = getSqlMapClientTemplate().queryForList("queryAllCustomerOnlineExport", paramMap);
		return list;
	}
}
