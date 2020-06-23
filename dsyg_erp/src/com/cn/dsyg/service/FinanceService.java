package com.cn.dsyg.service;

import java.util.List;

import com.cn.common.util.Page;
import com.cn.dsyg.dto.FinanceDto;

/**
 * @name FinanceService.java
 * @author Frank
 * @time 2015-6-27下午11:45:41
 * @version 1.0
 */
public interface FinanceService {
	
	/**
	 * 开票
	 * @param ids
	 * @param billno
	 * @param userid
	 */
	public void kaiPiao(String ids, String billno, String userid);
	
	/**
	 * 开票（新开票逻辑20191022）
	 * @param status
	 * @param ids
	 * @param billno
	 * @param userid
	 */
	public void newkaiPiao(String status, String ids, String billno, String userid);
	
	/**
	 * 根据状态查询财务记录(客户名，日期)
	 * @param status
	 * @param customername
	 * @param accountdateLow
	 * @param accountdateHigh
	 * @return
	 */
	public List<FinanceDto> queryFinanceByCpDate(String status, String customerid, String accountdateLow, String accountdateHigh);

	/**
	 * 根据状态查询财务记录
	 * @param status
	 * @param res10
	 * @param customername
	 * @param invoiceid
	 * @return
	 */
	public List<FinanceDto> queryFinanceByStatus(String status, String res10, String customername, String invoiceid);

	/**
	 * 翻页查询财务信息
	 * @param expressno
	 * @param status
	 * @param financetype
	 * @param invoiceid
	 * @param receiptid
	 * @param customerid
	 * @param receiptdateLow
	 * @param receiptdateHigh
	 * @param billno
	 * @param res02
	 * @param expressName
	 * @param invoiceddsp_flg
	 * @param page
	 * @return
	 */
	public Page queryFinanceByPage(String expressno, String status, String financetype, String invoiceid,
			String receiptid, String customerid, String receiptdateLow, String receiptdateHigh, String billno, String res02, String expressName, String invoiceddsp_flg, Page page);
	
	/**
	 * 查询财务金额信息
	 * @param expressno
	 * @param status
	 * @param financetype
	 * @param invoiceid
	 * @param receiptid
	 * @param customerid
	 * @param receiptdateLow
	 * @param receiptdateHigh
	 * @param billno
	 * @param res02
	 * @param expressName
	 * @return
	 */
	public String queryFinanceTotalAmount(String expressno, String status, String financetype, String invoiceid,
			String receiptid, String customerid, String receiptdateLow, String receiptdateHigh, String billno, String res02, String expressName,String strInvoiceddsp_flg);
	
	/**
	 * 查询已开票金额信息
	 * @param expressno
	 * @param status
	 * @param financetype
	 * @param invoiceid
	 * @param receiptid
	 * @param customerid
	 * @param receiptdateLow
	 * @param receiptdateHigh
	 * @param billno
	 * @param res02
	 * @param expressName
	 * @return
	 */
	public String queryInvoiceTotalAmount(String expressno, String status, String financetype, String invoiceid,
			String receiptid, String customerid, String receiptdateLow, String receiptdateHigh, String billno, String res02, String expressName, String strInvoiceddsp_flg);

	/**
	 * 根据ID查询记录
	 * @param id
	 * @return
	 */
	public FinanceDto queryFinanceByID(String id);
	
	/**
	 * 根据ID列表查询记录
	 * @param ids
	 * @return
	 */
	public List<FinanceDto> queryFinanceByIDs(String ids);
	
	/**
	 * 新增记录
	 * @param finance
	 */
	public String insertFinance(FinanceDto finance);
	
	/**
	 * 修改记录
	 * @param finance
	 */
	public void updateFinance(FinanceDto finance);
	
	/**
	 * 删除记录
	 * @param id
	 */
	public void deleteFinance(String id);
}
