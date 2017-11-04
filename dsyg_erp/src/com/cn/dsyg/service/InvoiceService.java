package com.cn.dsyg.service;

import java.util.List;

import com.cn.common.util.Page;
import com.cn.dsyg.dto.InvoiceDto;

public interface InvoiceService {
	
	/**
	 * 废票
	 * @param invoiceno
	 * @param note
	 * @param ids
	 * @param operator
	 */
	public void cancelInvoice(String invoiceno, String note, String ids, String operator);
	
	/**
	 * 开票
	 * @param invoiceno
	 * @param note
	 * @param ids
	 * @param operator
	 */
	public void invoiceOK(String invoiceno, String note, String ids, String operator);

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
	 * @param page
	 * @return
	 */
	public Page queryInvoiceByPage(String financeno, String invoiceno, String invoiceDateHigh, String invoiceDateLow,
			String status, String recpay, String customerid, String customername, Page page);
	
	/**
	 * 根据ID查询记录
	 * @param id
	 * @return
	 */
	public InvoiceDto queryInvoiceByID(String id);
	
	/**
	 * 根据账目编号查询记录
	 * @param financeno
	 * @param status
	 * @return
	 */
	public List<InvoiceDto> queryInvoiceByFinanceno(String financeno, String status);
	
	/**
	 * 批量删除记录（物理删除）
	 * @param ids
	 * @return 返回空则删除成功，否则删除失败
	 */
	public void deleteInvoiceBatch(String ids);
	
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