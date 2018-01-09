package com.cn.dsyg.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.cn.common.util.Constants;
import com.cn.common.util.DateUtil;
import com.cn.common.util.Page;
import com.cn.common.util.PropertiesConfig;
import com.cn.common.util.StringUtil;
import com.cn.dsyg.dao.FinanceDao;
import com.cn.dsyg.dao.InvoiceDao;
import com.cn.dsyg.dao.ProductDao;
import com.cn.dsyg.dao.UserDao;
import com.cn.dsyg.dao.WarehouseDao;
import com.cn.dsyg.dto.FinanceDto;
import com.cn.dsyg.dto.InvoiceDto;
import com.cn.dsyg.dto.ProductDto;
import com.cn.dsyg.dto.UserDto;
import com.cn.dsyg.dto.WarehouseDto;
import com.cn.dsyg.dto.WarehouserptDto;
import com.cn.dsyg.service.FinanceService;
import com.cn.dsyg.service.WarehouserptService;

/**
 * @name FinanceServiceImpl.java
 * @author Frank
 * @time 2015-6-27下午11:46:02
 * @version 1.0
 */
public class FinanceServiceImpl implements FinanceService {
	
	private FinanceDao financeDao;
	private WarehouserptService warehouserptService;
	private WarehouseDao warehouseDao;
	private UserDao userDao;
	private InvoiceDao invoiceDao;
	private ProductDao productDao;
	
	@Override
	public void kaiPiao(String ids, String billno, String userid) {
		if(StringUtil.isNotBlank(ids)) {
			String list[] = ids.split(",");
			FinanceDto finance = null;
			Date date = new Date();
			for(String id : list) {
				if(StringUtil.isNotBlank(id)) {
					finance = financeDao.queryFinanceByID(id);
					//出库单和入库单不做处理
					if(finance.getFinancetype() != Constants.FINANCE_TYPE_PURCHASE && finance.getFinancetype() != Constants.FINANCE_TYPE_SALES ) {
						finance.setRes10(billno);
						//开票日期+金额
						finance.setRes09(DateUtil.dateToShortStr(date) + "&&" + finance.getAmount());
					}
					finance.setUpdateuid(userid);
					financeDao.updateFinance(finance);
				}
			}
		}
	}
	
	@Override
	public List<FinanceDto> queryFinanceByCpDate(String status, String customerid, String accountdateLow, String accountdateHigh){
		return financeDao.queryFinanceByCpDate(status, customerid, accountdateLow, accountdateHigh);		
	}
	
	@Override
	public List<FinanceDto> queryFinanceByStatus(String status, String res10, String customername, String invoiceid) {
		customername = StringUtil.replaceDatabaseKeyword_mysql(customername);
		invoiceid = StringUtil.replaceDatabaseKeyword_mysql(invoiceid);
		
		String res10sql = "";
		if("1".equals(res10)) {
			//发票号不为空
			res10sql = " T.res10 IS NOT NULL ";
		} else if("2".equals(res10)) {
			//发票号为空
			res10sql = " T.res10 IS NULL ";
		}
		return financeDao.queryFinanceByStatus(status, res10sql, customername, invoiceid);
	}

	@Override
	public Page queryFinanceByPage(String expressno, String status, String financetype,
			String invoiceid, String receiptid, String customerid,
			String receiptdateLow, String receiptdateHigh, String billno, String res02, String expressName, Page page) {
		expressName = StringUtil.replaceDatabaseKeyword_mysql(expressName);
		expressno = StringUtil.replaceDatabaseKeyword_mysql(expressno);
		invoiceid = StringUtil.replaceDatabaseKeyword_mysql(invoiceid);
		receiptid = StringUtil.replaceDatabaseKeyword_mysql(receiptid);
		billno = StringUtil.replaceDatabaseKeyword_mysql(billno);
		
		//查询总记录数
		int totalCount = financeDao.queryFinanceCountByPage(expressno, status, financetype,
				invoiceid, receiptid, customerid, receiptdateLow, receiptdateHigh, billno, res02, expressName);
		page.setTotalCount(totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		//翻页查询记录
		List<FinanceDto> list = financeDao.queryFinanceByPage(expressno, status, financetype, invoiceid, receiptid,
				customerid, receiptdateLow, receiptdateHigh, billno, res02, expressName,
				page.getStartIndex() * page.getPageSize(), page.getPageSize());
		if(list != null && list.size() > 0) {
			for(FinanceDto finance : list) {
				UserDto user = userDao.queryUserByID(finance.getHandler());
				if(user != null) {
					finance.setHandlername(user.getUsername());
				}
				//已开票金额含税，这里只查询status=1的
				BigDecimal invoiceAmount = invoiceDao.querySumInvoiceByFinanceno(finance.getReceiptid(), "" + Constants.INVOICE_STATUS_OK);
				finance.setInvoiceAmount(invoiceAmount);
			}
		}
		page.setItems(list);
		return page;
	}
	
	@Override
	public String queryInvoiceTotalAmount(String expressno, String status, String financetype, String invoiceid,
			String receiptid, String customerid, String receiptdateLow, String receiptdateHigh, String billno,
			String res02, String expressName) {
		expressName = StringUtil.replaceDatabaseKeyword_mysql(expressName);
		expressno = StringUtil.replaceDatabaseKeyword_mysql(expressno);
		invoiceid = StringUtil.replaceDatabaseKeyword_mysql(invoiceid);
		receiptid = StringUtil.replaceDatabaseKeyword_mysql(receiptid);
		billno = StringUtil.replaceDatabaseKeyword_mysql(billno);
		
		//查询财务记录
		BigDecimal totalInvoiceAmount = new BigDecimal(0);
		List<FinanceDto> financeList = financeDao.queryAllFinance(expressno, status, financetype, invoiceid, receiptid,
				customerid, receiptdateLow, receiptdateHigh, billno, res02, expressName);
		if(financeList != null && financeList.size() > 0) {
			Map<String, String> financeMap = new HashMap<String, String>();
			for(FinanceDto finance : financeList) {
				if(!financeMap.containsKey(finance.getReceiptid())) {
					financeMap.put(finance.getReceiptid(), "");
				}
			}
			//查询发票记录列表
			List<InvoiceDto> invoiceList = invoiceDao.queryAllInvoiceList("" + Constants.INVOICE_STATUS_OK);
			if(invoiceList != null && invoiceList.size() > 0) {
				for(InvoiceDto invoiceDto : invoiceList) {
					if(financeMap.containsKey(invoiceDto.getFinanceno()) && invoiceDto.getAmounttax() != null) {
						totalInvoiceAmount = totalInvoiceAmount.add(invoiceDto.getAmounttax());
					}
				}
			}
		}
		
//		Double totalInvoiceAmount = financeDao.queryInvoiceTotalAmount(expressno, status, "" + Constants.INVOICE_STATUS_OK, financetype,
//				invoiceid, receiptid, customerid, receiptdateLow, receiptdateHigh, billno, res02, expressName);
//		if (totalInvoiceAmount == null)
//			totalInvoiceAmount = new Double(0);
//		BigDecimal bdamount = new BigDecimal(totalInvoiceAmount).setScale(2, BigDecimal.ROUND_HALF_UP);  
		return totalInvoiceAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}

	@Override
	public String  queryFinanceTotalAmount(String expressno, String status, String financetype,
			String invoiceid, String receiptid, String customerid,
			String receiptdateLow, String receiptdateHigh, String billno, String res02, String expressName) {
		expressName = StringUtil.replaceDatabaseKeyword_mysql(expressName);
		expressno = StringUtil.replaceDatabaseKeyword_mysql(expressno);
		invoiceid = StringUtil.replaceDatabaseKeyword_mysql(invoiceid);
		receiptid = StringUtil.replaceDatabaseKeyword_mysql(receiptid);
		billno = StringUtil.replaceDatabaseKeyword_mysql(billno);
		
		//查询记录
		Double dblTotalAmount;
		dblTotalAmount = financeDao.queryFinance(expressno, status, financetype, invoiceid, receiptid,
				customerid, receiptdateLow, receiptdateHigh, billno, res02, expressName);
		if (dblTotalAmount == null)
			dblTotalAmount = new Double(0);
		BigDecimal bdamount = new BigDecimal(Double.toString(dblTotalAmount));  
		System.out.println("dblTotalAmount:" +dblTotalAmount);
		System.out.println("bdamount:" +bdamount);
		return String.valueOf(bdamount);
	}


	@Override
	public FinanceDto queryFinanceByID(String id) {
		FinanceDto finance = financeDao.queryFinanceByID(id);
		if(finance != null) {
			UserDto user = userDao.queryUserByID(finance.getHandler());
			if(user != null) {
				finance.setHandlername(user.getUsername());
			}
			//根据账目编号查询已开票数量invoiceList
			List<InvoiceDto> invoiceList = invoiceDao.queryInvoiceByFinanceno(finance.getReceiptid(),
					"" + Constants.INVOICE_STATUS_NEW + "," + Constants.INVOICE_STATUS_OK);
			
			List<ProductDto> productList = new ArrayList<ProductDto>();
			if(StringUtil.isNotBlank(finance.getInvoiceid()) && (finance.getFinancetype() == 1 || finance.getFinancetype() == 2)) {
				//关联单据编号不为空，并且为出入库单的情况
				WarehouserptDto rpt = warehouserptService.queryWarehouserptByNo(finance.getInvoiceid(), 1);
				if(rpt != null) {
					//将开票的产品信息和RPT产品信息汇总
					if(rpt.getListProduct() != null && rpt.getListProduct().size() > 0) {
						for(ProductDto product : rpt.getListProduct()) {
							//总数量， 总金额
							BigDecimal num = new BigDecimal(product.getNumabs());
							BigDecimal amount = new BigDecimal(product.getAmount());
							if(amount.compareTo(new BigDecimal(0)) < 0) {
								//金额为负数，则说明是退货，由于RPT中记录的数量可能为正数，所以要处理成负数
								num = num.abs().multiply(new BigDecimal(-1));
							}
							product.setNum("" + num);
							
							//平均价格（取绝对值）
							BigDecimal averagePrice = amount.divide(num, 6, BigDecimal.ROUND_HALF_UP).abs();
							product.setAveragePrice(averagePrice);
							
							//查询退票列表
							List<InvoiceDto> invoiceReturnList = invoiceDao.queryReturnInvoiceByFinancedelno(finance.getReceiptid(), "" + product.getId(), "" + Constants.INVOICE_STATUS_RETURN);
							//退票数量（取绝对值）
							BigDecimal returnNum = new BigDecimal(0);
							if(invoiceReturnList != null && invoiceReturnList.size() > 0) {
								for(InvoiceDto invoice : invoiceReturnList) {
									if(invoice.getQuantity() != null) {
										returnNum = returnNum.add(invoice.getQuantity()).setScale(2, BigDecimal.ROUND_HALF_UP);
									}
								}
							}
							product.setReturnNum(returnNum.abs());
							
							//已开票数量，已开票金额
							BigDecimal invoicednum = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
							BigDecimal invoicedamount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
							//未开票数量，未开票金额
							BigDecimal remaininvoicenum = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
							BigDecimal remaininvoiceamount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
							if(invoiceList != null && invoiceList.size() > 0) {
								for(InvoiceDto invoice : invoiceList) {
									String invoiceKey = invoice.getProductid() + "_" + invoice.getPricetax();
									String productKey = "" + product.getId() + "_" + product.getAveragePrice();
									if(invoiceKey.equals(productKey)) {
										//已开票数量，已开票金额
										invoicednum = invoice.getQuantity();
										invoicedamount = invoice.getAmounttax();
										break;
									}
								}
							}
							//未开票数量和未开票金额
							remaininvoicenum = num.subtract(invoicednum).subtract(returnNum.abs()).setScale(2, BigDecimal.ROUND_HALF_UP);
							remaininvoiceamount = amount.subtract(invoicedamount).subtract(returnNum.abs().multiply(averagePrice)).setScale(2, BigDecimal.ROUND_HALF_UP);
							
							//已开票数量
							product.setInvoicednum(invoicednum);
							product.setInvoicedamount(invoicedamount);
							product.setRemaininvoiceamount(remaininvoiceamount);
							product.setRemaininvoicenum(remaininvoicenum);
							//已开票数量old，为了显示时用
							product.setOldinvoicednum(invoicednum);
							product.setOldinvoicedamount(invoicedamount);
							product.setOldremaininvoiceamount(remaininvoiceamount);
							product.setOldremaininvoicenum(remaininvoicenum);
							productList.add(product);
						}
					}
				}
			} else {
				//非出入库单的情况，不做任何处理
//				if(invoiceList != null && invoiceList.size() > 0) {
//					for(InvoiceDto invoice : invoiceList) {
//						ProductDto product = productDao.queryProductByID(invoice.getProductid());
//						//发票数量=已经开票数量，所以未开票数量=0
//						product.setNum(invoice.getQuantity());
//						product.setAmount(invoice.getAmounttax());
//						product.setInvoicednum(invoice.getQuantity());
//						product.setInvoicedamount(invoice.getAmounttax());
//						//未开票数量和未开票金额=0
//						product.setRemaininvoiceamount(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP));
//						product.setRemaininvoicenum(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP));
//						//已开票数量old，为了显示时用
//						product.setOldinvoicednum(invoicednum);
//						product.setOldinvoicedamount(invoicedamount);
//						product.setOldremaininvoiceamount(remaininvoiceamount);
//						product.setOldremaininvoicenum(remaininvoicenum);
//						productList.add(product);
//					}
//				}
			}
			finance.setProductList(productList);
		}
		return finance;
	}

	@Override
	public String insertFinance(FinanceDto finance) {
		String no = "";
		String belongto = PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_BELONG);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.substring(uuid.length() - 8, uuid.length());
		no = Constants.FINANCE_NO_PRE + belongto + sdf.format(date);// + uuid;
		
		if(StringUtil.isBlank(finance.getReceiptdate())) {
			finance.setReceiptdate(null);
		}
		finance.setRank(Constants.ROLE_RANK_OPERATOR);
		finance.setReceiptid(no);
		finance.setBelongto(belongto);
		financeDao.insertFinance(finance);
		//插入invoice记录
		if(StringUtil.isNotBlank(finance.getRes10())) {
			String s09[] = finance.getRes09().split("&&");
			String s10[] = finance.getRes10().split(";");
			String s09date[] = s09[0].split(";");
			String s09amount[] = s09[1].split(";");
			for(int i = 0; i < s10.length; i++) {
				if(StringUtil.isNotBlank(s10[i])) {
					InvoiceDto invoice = new InvoiceDto();
					invoice.setBelongto(belongto);
					invoice.setWarehouserptno(finance.getInvoiceid());
					invoice.setFinanceno(finance.getReceiptid());
					invoice.setInvoiceno(s10[i]);
					invoice.setCustomerid(finance.getCustomerid());
					invoice.setCustomername(finance.getCustomername());
					invoice.setQuantity(new BigDecimal(0));
					invoice.setPrice(new BigDecimal(0));
					invoice.setPricetax(new BigDecimal(0));
					invoice.setAmount(new BigDecimal(0));
					invoice.setAmounttax(new BigDecimal(s09amount[i]));
					//新增的财务记录
					//方式：1为收款，2为付款
					if("1".equals(finance.getMode())) {
						//收款
						invoice.setRecpay(1);
					} else {
						//付款，收到开出
						invoice.setRecpay(0);
					}
					//发票号不为空，则直接是开票完了
					invoice.setStatus(1);
					invoice.setInvoice_date(DateUtil.strToDate(s09date[i], DateUtil.DATE_FORMAT_SHORT));
					invoice.setInvoide_mem_id(finance.getCreateuid());
					invoice.setCreateuid(finance.getCreateuid());
					invoice.setUpdateuid(finance.getUpdateuid());
					invoiceDao.insertInvoice(invoice);
				}
			}
		}
		
		return no;
	}
	
	@Override
	public void deleteFinance(String id) {
		//物理删除
		financeDao.deleteFinance(id);
//		//逻辑删除
//		FinanceDto finance = financeDao.queryFinanceByID(id);
//		if(finance != null) {
//			finance.setStatus(status);
//		}
	}

	@Override
	public void updateFinance(FinanceDto finance) {
		if(StringUtil.isBlank(finance.getReceiptdate())) {
			finance.setReceiptdate(null);
		}
		FinanceDto oldFinance = financeDao.queryFinanceByID("" + finance.getId());
		//判断是否是入出库单的财务记录
		if(oldFinance.getFinancetype() == Constants.FINANCE_TYPE_PURCHASE || oldFinance.getFinancetype() == Constants.FINANCE_TYPE_SALES) {
			//处理发票预出库
			if(finance.getProductList() != null && finance.getProductList().size() > 0) {
				for(ProductDto product : finance.getProductList()) {
					//判断是否需要预开票
					if("1".equals(product.getChecked())) {
						//判断预出库数量是否不等于0
						if(product.getCurrinvoicenum().compareTo(new BigDecimal(0)) > 0) {
							//发票预出库，这里区分下预出库数量，预留用
							addInvoice(finance, product);
						} else if(product.getCurrinvoicenum().compareTo(new BigDecimal(0)) < 0) {
							//退票预出库，这里区分下预出库数量，预留用
							addInvoice(finance, product);
						} else {
							//预出库数量为0，则什么都不做。
						}
					}
				}
			}
			//判断财务记录的状态是否修改
			if(oldFinance.getStatus() != finance.getStatus()) {
				//修改对应的入出库单状态
				WarehouserptDto warehouserpt = warehouserptService.queryWarehouserptByNo(finance.getInvoiceid(), 1);
				if(warehouserpt != null) {
					warehouserpt.setStatus(finance.getStatus());
					warehouserpt.setUpdateuid(finance.getUpdateuid());
					warehouserptService.updateWarehouserpt(warehouserpt);
					//判断是否需要修改库存表的状态，当且仅当更新前的财务记录状态=99
					if(oldFinance.getStatus() == Constants.FINANCE_STATUS_PAY_INVOICE) {
						String parentid = warehouserpt.getParentid();
						if(StringUtil.isNotBlank(parentid)) {
							String[] ids = parentid.split(",");
							for(String warehouseno : ids) {
								if(StringUtil.isNotBlank(warehouseno)) {
									WarehouseDto warehouse = warehouseDao.queryWarehouseByWarehouseno(warehouseno);
									if(warehouse != null) {
										warehouse.setStatus(Constants.WAREHOUSE_STATUS_OK);
										warehouseDao.updateWarehouse(warehouse);
									}
								}
							}
						}
					} else {
						String parentid = warehouserpt.getParentid();
						if(StringUtil.isNotBlank(parentid)) {
							String[] ids = parentid.split(",");
							for(String warehouseno : ids) {
								if(StringUtil.isNotBlank(warehouseno)) {
									WarehouseDto warehouse = warehouseDao.queryWarehouseByWarehouseno(warehouseno);
									if(warehouse != null) {
										warehouse.setStatus(Constants.WAREHOUSE_STATUS_FINISHED);
										warehouseDao.updateWarehouse(warehouse);
									}
								}
							}
						}						
					}
				}
			}
		} else {
			//先删除invoice记录
			invoiceDao.deleteAllInvoiceByFinanceno(finance.getReceiptid());
			//非入出库单，插入发票1，2，3数据到invoice中
			if(StringUtil.isNotBlank(finance.getRes10())) {
				String s09[] = finance.getRes09().split("&&");
				String s10[] = finance.getRes10().split(";");
				String s09date[] = s09[0].split(";");
				String s09amount[] = s09[1].split(";");
				for(int i = 0; i < s10.length; i++) {
					if(StringUtil.isNotBlank(s10[i])) {
						InvoiceDto invoice = new InvoiceDto();
						invoice.setBelongto(finance.getBelongto());
						invoice.setWarehouserptno(finance.getInvoiceid());
						invoice.setFinanceno(finance.getReceiptid());
						invoice.setInvoiceno(s10[i]);
						invoice.setCustomerid(finance.getCustomerid());
						invoice.setCustomername(finance.getCustomername());
						invoice.setQuantity(new BigDecimal(0));
						invoice.setPrice(new BigDecimal(0));
						invoice.setPricetax(new BigDecimal(0));
						invoice.setAmount(new BigDecimal(0));
						invoice.setAmounttax(new BigDecimal(s09amount[i]));
						//方式：1为收款，2为付款
						if("1".equals(finance.getMode())) {
							//收款
							invoice.setRecpay(1);
						} else {
							//付款，收到开出
							invoice.setRecpay(0);
						}
						//发票号不为空，则直接是开票完了
						invoice.setStatus(1);
						invoice.setInvoice_date(DateUtil.strToDate(s09date[i], DateUtil.DATE_FORMAT_SHORT));
						invoice.setInvoide_mem_id(finance.getCreateuid());
						invoice.setCreateuid(finance.getCreateuid());
						invoice.setUpdateuid(finance.getUpdateuid());
						invoiceDao.insertInvoice(invoice);
					}
				}
			}
		}
		financeDao.updateFinance(finance);
	}
	
	/**
	 * 发票预出库
	 * @param finance
	 * @param product
	 */
	private void addInvoice(FinanceDto finance, ProductDto product) {
		InvoiceDto invoice = new InvoiceDto();
		invoice.setBelongto(PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_BELONG));
		//invoice.setWarehouseno(finance.getInvoiceid());
		invoice.setWarehouserptno(finance.getInvoiceid());
		invoice.setProductid("" + product.getId());
		invoice.setFinanceno(finance.getReceiptid());
		//发票号，在预开票的时候用户自己填写
		invoice.setInvoiceno("");
		invoice.setCustomerid(finance.getCustomerid());
		invoice.setCustomername(finance.getCustomername());
		invoice.setCustomer_info1("");
		invoice.setCustomer_info2("");
		invoice.setCustomer_info3("");
		invoice.setCustomer_info4("");
		invoice.setCustomer_info5("");
		invoice.setQuantity(product.getCurrinvoicenum());
		//invoice.setPrice("");
		invoice.setPricetax(product.getAveragePrice());
		//invoice.setAmount("");
		invoice.setAmounttax(product.getCurrinvoiceamount());
		if(finance.getFinancetype() == Constants.FINANCE_TYPE_PURCHASE) {
			//收票
			invoice.setRecpay(0);
		} else if(finance.getFinancetype() == Constants.FINANCE_TYPE_SALES) {
			//开票
			invoice.setRecpay(1);
		}
		//状态=预开票0
		invoice.setStatus(Constants.INVOICE_STATUS_NEW);
		
		//开票作废信息
		invoice.setInvoicedelno("");
		invoice.setQuantitydel(new BigDecimal(0));
		invoice.setFinanacedelno("");
		invoice.setWarehouserptdelno("");
		
		invoice.setNote("");
		//开票时间
		invoice.setInvoice_date(new Date());
		//开票人
		invoice.setInvoide_mem_id(finance.getUpdateuid());
		invoice.setCreateuid(finance.getUpdateuid());
		invoice.setUpdateuid(finance.getUpdateuid());
		invoiceDao.insertInvoice(invoice);
	}

	public FinanceDao getFinanceDao() {
		return financeDao;
	}

	public void setFinanceDao(FinanceDao financeDao) {
		this.financeDao = financeDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public WarehouseDao getWarehouseDao() {
		return warehouseDao;
	}

	public void setWarehouseDao(WarehouseDao warehouseDao) {
		this.warehouseDao = warehouseDao;
	}

	public InvoiceDao getInvoiceDao() {
		return invoiceDao;
	}

	public void setInvoiceDao(InvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}

	public WarehouserptService getWarehouserptService() {
		return warehouserptService;
	}

	public void setWarehouserptService(WarehouserptService warehouserptService) {
		this.warehouserptService = warehouserptService;
	}

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
}
