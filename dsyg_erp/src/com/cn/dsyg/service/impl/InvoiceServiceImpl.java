package com.cn.dsyg.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.common.util.Constants;
import com.cn.common.util.DateUtil;
import com.cn.common.util.Page;
import com.cn.common.util.StringUtil;
import com.cn.dsyg.dao.FinanceDao;
import com.cn.dsyg.dao.InvoiceDao;
import com.cn.dsyg.dto.FinanceDto;
import com.cn.dsyg.dto.InvoiceDto;
import com.cn.dsyg.service.InvoiceService;

public class InvoiceServiceImpl implements InvoiceService {
	
	private InvoiceDao invoiceDao;
	private FinanceDao financeDao;
	
	@Override
	public void cancelInvoice(String invoiceno, String note, String ids, String operator) {
		//根据发票号检索数据
		List<InvoiceDto> invoiceOKList = invoiceDao.queryInvoiceByInvoiceno(invoiceno, "" + Constants.INVOICE_STATUS_OK);
		if(invoiceOKList == null || invoiceOKList.size() == 0) {
			throw new RuntimeException("未找到发票号=" + invoiceno + "的记录！");
		}
		if(StringUtil.isNotBlank(ids)) {
			String[] ll = ids.split(",");
			//用一个map记录账目编号，更新发票号时用
			Map<String, String> financenoMap = new HashMap<String, String>();
			for(String id : ll) {
				if(StringUtil.isNotBlank(id)) {
					InvoiceDto invoice = invoiceDao.queryInvoiceByID(id);
					if(invoice != null) {
						//验证作废的发票和当前记录的客户信息是否一致
						//同为入库或者出库记录，并且客户ID一致的情况下才可以进行作废处理
						if(invoice.getCustomerid().equals(invoiceOKList.get(0).getCustomerid()) && invoice.getRecpay().equals(invoiceOKList.get(0).getRecpay())) {
						} else {
							throw new RuntimeException("客户ID不一致不能进行作废处理！");
						}
						//验证数据状态
						if(invoice.getStatus() == Constants.INVOICE_STATUS_NEW) {
							//当前发票号为空
							invoice.setInvoiceno("");
							//作废的发票号=输入的发票号
							invoice.setInvoicedelno(invoiceno);
							//作废的财务编号
							invoice.setFinanacedelno(invoiceOKList.get(0).getFinanceno());
							//作废的RPT编号
							invoice.setWarehouserptdelno(invoiceOKList.get(0).getWarehouserptno());
							invoice.setNote(note);
							invoice.setUpdateuid(operator);
							if(invoice.getQuantity().compareTo(new BigDecimal(0)) <= 0) {
								//预出库数量为负数，状态=退票
								invoice.setStatus(Constants.INVOICE_STATUS_RETURN);
							} else {
								//预出库数量为正数，废票失败
								throw new RuntimeException("预出库数量为正数不能进行作废，操作失败！");
								//invoice.setStatus(Constants.INVOICE_STATUS_RETURN);
							}
							invoiceDao.updateInvoice(invoice);
						} else {
							throw new RuntimeException("数据记录状态已改变，操作失败！");
						}
					} else {
						throw new RuntimeException("选择的作废数据未找到，操作失败！");
					}
				}
			}
			//废除发票记录下所有invoice
			for(InvoiceDto invoice : invoiceOKList) {
				invoice.setNote(note);
				invoice.setUpdateuid(operator);
				invoice.setStatus(Constants.INVOICE_STATUS_CANCEL);
				invoiceDao.updateInvoice(invoice);
				if(!financenoMap.containsKey(invoice.getFinanceno())) {
					String invoiceInfo = DateUtil.dateToShortStr(invoice.getInvoice_date()) + "&&" + invoice.getAmounttax();
					financenoMap.put(invoice.getFinanceno(), invoiceInfo);
				}
			}
			//将发票号从finance中去掉
			for(Map.Entry<String, String> entry : financenoMap.entrySet()) {
				FinanceDto finance = financeDao.queryFinanceByReceiptid(entry.getKey());
				if(finance != null) {
					String oldres09 = finance.getRes09();
					String oldres10 = finance.getRes10();
					//判断原发票信息是否为空
					if(oldres10 != null) {
						//判断是否存在当前发票信息
						if((";" + oldres10).indexOf(";" + invoiceno + ";") >= 0) {
							//存在该发票号，则去掉发票信息
							String s09[] = oldres09.split("&&");
							String s09date[] = s09[0].split(";");
							String s09amount[] = s09[1].split(";");
							String s10[] = oldres10.split(";");
							String newres09date = "";
							String newres09amount = "";
							String newres10 = "";
							for(int i = 0; i < s10.length; i++) {
								if(!s10[i].equals(invoiceno)) {
									newres09date += s09date[i] + ";";
									newres09amount += s09amount[i] + ";";
									newres10 += s10[i] + ";";
								}
							}
							if(!"".equals(newres09date)) {
								finance.setRes09(newres09date + "&&" + newres09amount);
							} else {
								finance.setRes09("");
							}
							finance.setRes10(newres10);
							financeDao.updateFinance(finance);
						} else {
							//已记录该发票号，则什么都不做
						}
					} else {
						//原发票信息为空，则什么都不做
					}
				}
			}
		}
	}

	@Override
	public void invoiceOK(String invoiceno, String note, String ids, String operator) {
		if(StringUtil.isNotBlank(ids)) {
			String[] ll = ids.split(",");
			String customerid = "";
			//用一个map记录账目编号，更新发票号时用
			Map<String, String> financenoMap = new HashMap<String, String>();
			for(String id : ll) {
				if(StringUtil.isNotBlank(id)) {
					InvoiceDto invoice = invoiceDao.queryInvoiceByID(id);
					if("".equals(customerid)) {
						customerid = "" + invoice.getCustomerid();
					} else {
						if(!customerid.equals("" + invoice.getCustomerid())) {
							//客户ID不一致，不能进行开票
							throw new RuntimeException("客户ID不一致不能进行开票，开票失败！");
						}
					}
					//验证数据状态
					if(invoice.getStatus() == Constants.INVOICE_STATUS_NEW) {
						invoice.setInvoiceno(invoiceno);
						invoice.setNote(note);
						invoice.setUpdateuid(operator);
						if(invoice.getQuantity().compareTo(new BigDecimal(0)) >= 0) {
							//预出库数量为正数，则是正常开票
							invoice.setStatus(Constants.INVOICE_STATUS_OK);
						} else {
							//预出库数量为负数，则是退票
							throw new RuntimeException("预开票金额为负数不能进行开票，开票失败！");
							//invoice.setStatus(Constants.INVOICE_STATUS_RETURN);
						}
						invoiceDao.updateInvoice(invoice);
						if(!financenoMap.containsKey(invoice.getFinanceno())) {
							String invoiceInfo = DateUtil.dateToShortStr(invoice.getInvoice_date()) + "&&" + invoice.getAmounttax();
							financenoMap.put(invoice.getFinanceno(), invoiceInfo);
						}
					} else {
						throw new RuntimeException("数据记录状态已改变，开票失败！");
					}
				}
			}
			//将发票号添加到finance表中
			for(Map.Entry<String, String> entry : financenoMap.entrySet()) {
				FinanceDto finance = financeDao.queryFinanceByReceiptid(entry.getKey());
				if(finance != null) {
					String val = entry.getValue();
					String s[] = val.split("&&");
					String invoicedate = s[0];
					String amount = s[1];
					String oldres09 = finance.getRes09();
					String oldres10 = finance.getRes10();
					//判断原发票信息是否为空
					if(oldres10 != null) {
						//判断是否存在当前发票信息
						if((";" + oldres10).indexOf(";" + invoiceno + ";") < 0) {
							//未记录该发票号
							String s1[] = oldres09.split("&&");
							finance.setRes09(s1[0] + invoicedate + ";" + "&&" + s1[1] + amount + ";");
							finance.setRes10(finance.getRes10() + invoiceno + ";");
							financeDao.updateFinance(finance);
						} else {
							//已记录该发票号，则什么都不做
						}
					} else {
						//原发票信息为空，直接添加当前发票信息
						finance.setRes09(invoicedate + ";" + "&&" + amount + ";");
						finance.setRes10(invoiceno + ";");
						financeDao.updateFinance(finance);
					}
				}
			}
		}
	}
	
	@Override
	public Page queryInvoiceOKByPage(String financeno, String invoiceno, String invoiceDateHigh, String invoiceDateLow,
			String status, String customername, Page page) {
		financeno = StringUtil.replaceDatabaseKeyword_mysql(financeno);
		customername = StringUtil.replaceDatabaseKeyword_mysql(customername);
		if(StringUtil.isNotBlank(invoiceDateHigh)) {
			invoiceDateHigh = invoiceDateHigh + " 23:59:59";
		}
		//查询总记录数
		int totalCount = invoiceDao.queryInvoiceOKCountByPage(financeno, invoiceno, invoiceDateHigh, invoiceDateLow, status, customername);
		page.setTotalCount(totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		//翻页查询记录
		List<InvoiceDto> list = invoiceDao.queryInvoiceOKByPage(financeno, invoiceno, invoiceDateHigh, invoiceDateLow, status, customername,
				page.getStartIndex() * page.getPageSize(), page.getPageSize());
		page.setItems(list);
		return page;
	}

	@Override
	public Page queryInvoiceByPage(String financeno, String invoiceno, String invoiceDateHigh, String invoiceDateLow, String status,
			String recpay, String customerid, String customername, Page page) {
		customername = StringUtil.replaceDatabaseKeyword_mysql(customername);
		if(StringUtil.isNotBlank(invoiceDateHigh)) {
			invoiceDateHigh = invoiceDateHigh + " 23:59:59";
		}
		//查询总记录数
		int totalCount = invoiceDao.queryInvoiceCountByPage(financeno, invoiceno, invoiceDateHigh, invoiceDateLow, status, recpay, customerid, customername);
		page.setTotalCount(totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		//翻页查询记录
		List<InvoiceDto> list = invoiceDao.queryInvoiceByPage(financeno, invoiceno, invoiceDateHigh, invoiceDateLow, status, recpay, customerid, customername,
				page.getStartIndex() * page.getPageSize(), page.getPageSize());
		page.setItems(list);
		return page;
	}

	@Override
	public InvoiceDto queryInvoiceByID(String id) {
		return invoiceDao.queryInvoiceByID(id);
	}

	@Override
	public List<InvoiceDto> queryInvoiceByFinanceno(String financeno, String status) {
		return invoiceDao.queryInvoiceByFinanceno(financeno, status);
	}
	
	@Override
	public void deleteInvoiceBatch(String ids) {
		if(StringUtil.isNotBlank(ids)) {
			String[] ll = ids.split(",");
			for(String id : ll) {
				if(StringUtil.isNotBlank(id)) {
					InvoiceDto invoice = invoiceDao.queryInvoiceByID(id);
					//验证数据状态
					if(invoice.getStatus() == Constants.INVOICE_STATUS_NEW) {
						invoiceDao.deleteInvoice(id);
					} else {
						throw new RuntimeException("数据记录状态已改变，删除失败！");
					}
				}
			}
		}
	}

	@Override
	public void deleteInvoice(String id) {
		invoiceDao.deleteInvoice(id);
	}

	@Override
	public void insertInvoice(InvoiceDto invoice) {
		invoiceDao.insertInvoice(invoice);
	}

	@Override
	public void updateInvoice(InvoiceDto invoice) {
		invoiceDao.updateInvoice(invoice);
	}

	public InvoiceDao getInvoiceDao() {
		return invoiceDao;
	}

	public void setInvoiceDao(InvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}

	public FinanceDao getFinanceDao() {
		return financeDao;
	}

	public void setFinanceDao(FinanceDao financeDao) {
		this.financeDao = financeDao;
	}
}
