package com.cn.dsyg.dao;

import java.math.BigDecimal;
import java.util.List;

import com.cn.dsyg.dto.InvoiceDto;

public interface InvoiceDao {
	
	/**
	 * 根据财务编号查询已开票金额汇总
	 * @param financeno
	 * @param status
	 * @return
	 */
	public BigDecimal querySumInvoiceByFinanceno(String financeno, String status);

	/**
	 * 根据条件查询满足条件的发票数量
	 * @param financeno
	 * @param invoiceno
	 * @param invoiceDateHigh
	 * @param invoiceDateLow
	 * @param status
	 * @param recpay
	 * @param customerid
	 * @param customername
	 * @return
	 */
	public int queryInvoiceCountByPage(String financeno, String invoiceno, String invoiceDateHigh,
			String invoiceDateLow, String status, String recpay, String customerid, String customername);
	
	/**
	 * 翻页查询满足条件的发票记录
	 * @param financeno
	 * @param invoiceno
	 * @param invoiceDateHigh
	 * @param invoiceDateLow
	 * @param status
	 * @param recpay
	 * @param customerid
	 * @param customername
	 * @param start
	 * @param end
	 * @return
	 */
	public List<InvoiceDto> queryInvoiceByPage(String financeno, String invoiceno, String invoiceDateHigh, String invoiceDateLow,
			String status, String recpay, String customerid, String customername, int start, int end);
	
	/**
	 * 根据ID查询记录
	 * @param id
	 * @return
	 */
	public InvoiceDto queryInvoiceByID(String id);
	
	/**
	 * 根据账目编号查询记录
	 * @param id
	 * @return
	 */
	public List<InvoiceDto> queryInvoiceByFinanceno(String financeno, String status);
	
	/**
	 * 根据作废financeNo和productid查询退货数量
	 * @param finanacedelno
	 * @param productid
	 * @param status
	 * @return
	 */
	public List<InvoiceDto> queryReturnInvoiceByFinancedelno(String finanacedelno,
			String productid, String status);
	
	/**
	 * 根据发票号查询记录
	 * @param invoiceno
	 * @param status
	 * @return
	 */
	public List<InvoiceDto> queryInvoiceByInvoiceno(String invoiceno, String status);
	
	/**
	 * 物理删除
	 * @param id
	 */
	public void deleteInvoice(String id);
	
	/**
	 * 新增记录
	 * @param invoice
	 */
	public void insertInvoice(InvoiceDto invoice);
	
	/**
	 * 修改记录
	 * @param invoice
	 */
	public void updateInvoice(InvoiceDto invoice);
}