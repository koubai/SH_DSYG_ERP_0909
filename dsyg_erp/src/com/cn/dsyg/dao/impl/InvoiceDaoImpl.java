package com.cn.dsyg.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.common.dao.BaseDao;
import com.cn.dsyg.dao.InvoiceDao;
import com.cn.dsyg.dto.InvoiceDto;

public class InvoiceDaoImpl extends BaseDao implements InvoiceDao {
	
	@Override
	public BigDecimal querySumInvoiceByFinanceno(String financeno, String status) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("financeno", financeno);
		paramMap.put("status", status);
		return (BigDecimal) getSqlMapClientTemplate().queryForObject("querySumInvoiceByFinanceno", paramMap);
	}

	@Override
	public int queryInvoiceCountByPage(String financeno, String invoiceno, String invoiceDateHigh, String invoiceDateLow, String status,
			String recpay, String customerid, String customername) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("financeno", financeno);
		paramMap.put("invoiceno", invoiceno);
		paramMap.put("invoiceDateHigh", invoiceDateHigh);
		paramMap.put("invoiceDateLow", invoiceDateLow);
		paramMap.put("status", status);
		paramMap.put("recpay", recpay);
		paramMap.put("customerid", customerid);
		paramMap.put("customername", customername);
		return (Integer) getSqlMapClientTemplate().queryForObject("queryInvoiceCountByPage", paramMap);
	}

	@Override
	public List<InvoiceDto> queryInvoiceByPage(String financeno, String invoiceno, String invoiceDateHigh, String invoiceDateLow,
			String status, String recpay, String customerid, String customername, int start, int end) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("financeno", financeno);
		paramMap.put("invoiceno", invoiceno);
		paramMap.put("invoiceDateHigh", invoiceDateHigh);
		paramMap.put("invoiceDateLow", invoiceDateLow);
		paramMap.put("status", status);
		paramMap.put("recpay", recpay);
		paramMap.put("customerid", customerid);
		paramMap.put("customername", customername);
		paramMap.put("start", start);
		paramMap.put("end", end);
		@SuppressWarnings("unchecked")
		List<InvoiceDto> list = getSqlMapClientTemplate().queryForList("queryInvoiceByPage", paramMap);
		return list;
	}

	@Override
	public InvoiceDto queryInvoiceByID(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		@SuppressWarnings("unchecked")
		List<InvoiceDto> list = getSqlMapClientTemplate().queryForList("queryInvoiceByID", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public List<InvoiceDto> queryAllInvoiceList(String status) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", status);
		@SuppressWarnings("unchecked")
		List<InvoiceDto> list = getSqlMapClientTemplate().queryForList("queryAllInvoiceList", paramMap);
		return list;
	}

	@Override
	public List<InvoiceDto> queryInvoiceByFinanceno(String financeno, String status) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("financeno", financeno);
		paramMap.put("status", status);
		@SuppressWarnings("unchecked")
		List<InvoiceDto> list = getSqlMapClientTemplate().queryForList("queryInvoiceByFinanceno", paramMap);
		return list;
	}
	
	@Override
	public List<InvoiceDto> queryReturnInvoiceByFinancedelno(String finanacedelno, String productid, String status) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("finanacedelno", finanacedelno);
		paramMap.put("productid", productid);
		paramMap.put("status", status);
		@SuppressWarnings("unchecked")
		List<InvoiceDto> list = getSqlMapClientTemplate().queryForList("queryReturnInvoiceByFinancedelno", paramMap);
		return list;
	}
	
	@Override
	public List<InvoiceDto> queryInvoiceByInvoiceno(String invoiceno, String status) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("invoiceno", invoiceno);
		paramMap.put("status", status);
		@SuppressWarnings("unchecked")
		List<InvoiceDto> list = getSqlMapClientTemplate().queryForList("queryInvoiceByInvoiceno", paramMap);
		return list;
	}
	
	@Override
	public void deleteAllInvoiceByFinanceno(String financeno) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("financeno", financeno);
		getSqlMapClientTemplate().delete("deleteAllInvoiceByFinanceno", paramMap);
	}

	@Override
	public void deleteInvoice(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		getSqlMapClientTemplate().delete("deleteInvoice", paramMap);
	}

	@Override
	public void insertInvoice(InvoiceDto invoice) {
		getSqlMapClientTemplate().insert("insertInvoice", invoice);
	}

	@Override
	public void updateInvoice(InvoiceDto invoice) {
		getSqlMapClientTemplate().update("updateInvoice", invoice);
	}
}
