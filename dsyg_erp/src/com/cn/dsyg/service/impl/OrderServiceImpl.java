package com.cn.dsyg.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.cn.common.mail.MailSender;
import com.cn.common.util.Constants;
import com.cn.common.util.DateUtil;
import com.cn.common.util.EncryptionDecryption;
import com.cn.common.util.Page;
import com.cn.common.util.PropertiesConfig;
import com.cn.common.util.StringUtil;
import com.cn.dsyg.dao.CustomerOnlineDao;
import com.cn.dsyg.dao.Dict01Dao;
import com.cn.dsyg.dao.FinanceDao;
import com.cn.dsyg.dao.MailAuthDao;
import com.cn.dsyg.dao.OrderDao;
import com.cn.dsyg.dao.OrderDetailDao;
import com.cn.dsyg.dao.ProductDao;
import com.cn.dsyg.dao.WarehouseDao;
import com.cn.dsyg.dao.WarehouserptDao;
import com.cn.dsyg.dto.CustomerOnlineDto;
import com.cn.dsyg.dto.Dict01Dto;
import com.cn.dsyg.dto.FinanceDto;
import com.cn.dsyg.dto.MailAuthDto;
import com.cn.dsyg.dto.OrderDetailDto;
import com.cn.dsyg.dto.OrderDto;
import com.cn.dsyg.dto.ProductDto;
import com.cn.dsyg.dto.WarehouseDto;
import com.cn.dsyg.dto.WarehouserptDto;
import com.cn.dsyg.service.OrderService;

/**
 * @name OrderServiceImpl.java
 * @author Frank
 * @time 2016-9-4下午2:48:32
 * @version 1.0
 */
public class OrderServiceImpl implements OrderService {
	
	private OrderDao orderDao;
	private OrderDetailDao orderDetailDao;
	private Dict01Dao dict01Dao;
	private CustomerOnlineDao customerOnlineDao;
	private ProductDao productDao;
	private MailAuthDao mailAuthDao;
	private WarehouseDao warehouseDao;
	private WarehouserptDao warehouserptDao;
	private FinanceDao financeDao;

	@Override
	public Page queryOrderByPage(String ordercode, String customerid,
			String status, Page page) {
		ordercode = StringUtil.replaceDatabaseKeyword_mysql(ordercode);
		//查询总记录数
		int totalCount = orderDao.queryOrderCountByPage(ordercode, customerid, status);
		page.setTotalCount(totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		//翻页查询记录
		List<OrderDto> list = orderDao.queryOrderByPage(ordercode, customerid, status,
				page.getStartIndex() * page.getPageSize(), page.getPageSize());
		//查询明细数据
		if(list != null && list.size() > 0) {
			List<OrderDetailDto> orderDetailList = null;
			for(OrderDto order : list) {
				orderDetailList = orderDetailDao.queryOrderDetailByOrderid("" + order.getId());
				if(orderDetailList != null && orderDetailList.size() > 0) {
					ProductDto product = null;
					for(OrderDetailDto detail : orderDetailList) {
						product = productDao.queryProductByID("" + detail.getProductid());
						detail.setFieldno(product.getFieldno());
						detail.setTypeno(product.getTypeno());
						detail.setColor(product.getColor());
						detail.setBrand(product.getBrand());
						detail.setMakearea(product.getMakearea());
						detail.setPackaging(product.getPackaging());
						detail.setUnit(product.getUnit());
						detail.setTradename(product.getTradename());
					}
				}
				order.setOrderDetailList(orderDetailList);
			}
		}
		page.setItems(list);
		return page;
	}

	@Override
	public OrderDto queryOrderByID(String id) {
		OrderDto order = orderDao.queryOrderByID(id);
		
		//客户交期回复时，需要填写公司税号、公司开户行和公司银行账户信息，这里直接从配置文件中读取
		if(order.getStatus() == Constants.ONLINE_ORDER_STATUS_REF_DELIVERY) {
			String belongto = PropertiesConfig.getPropertiesValueByKey("belongto");
			String sh_tax_number = PropertiesConfig.getPropertiesValueByKey("sh_tax_number");
			String sh_bank = PropertiesConfig.getPropertiesValueByKey("sh_bank");
			String sh_bank_account = PropertiesConfig.getPropertiesValueByKey("sh_bank_account");
			
			String sz_tax_number = PropertiesConfig.getPropertiesValueByKey("sz_tax_number");
			String sz_bank = PropertiesConfig.getPropertiesValueByKey("sz_bank");
			String sz_bank_account = PropertiesConfig.getPropertiesValueByKey("sz_bank_account");
			
			if("1".equals(belongto)) {
				//深圳
				if(order.getTransfer() == 1) {
					//订单转移，则取上海的账户信息
					order.setCompanytax(sh_tax_number);
					order.setAccountbank(sh_bank);
					order.setAccountid(sh_bank_account);
				} else {
					//取深圳账户信息
					order.setCompanytax(sz_tax_number);
					order.setAccountbank(sz_bank);
					order.setAccountid(sz_bank_account);
				}
			} else {
				//默认上海
				if(order.getTransfer() == 1) {
					//转移订单，取深圳账户信息
					order.setCompanytax(sz_tax_number);
					order.setAccountbank(sz_bank);
					order.setAccountid(sz_bank_account);
				} else {
					//上海的账户信息
					order.setCompanytax(sh_tax_number);
					order.setAccountbank(sh_bank);
					order.setAccountid(sh_bank_account);
				}
			}
		}
		if(order != null) {
			//查询明细数据
			List<OrderDetailDto> orderDetailList = orderDetailDao.queryOrderDetailByOrderid("" + order.getId());
			if(orderDetailList != null && orderDetailList.size() > 0) {
				ProductDto product = null;
				for(OrderDetailDto detail : orderDetailList) {
					product = productDao.queryProductByID("" + detail.getProductid());
					detail.setFieldno(product.getFieldno());
					detail.setTypeno(product.getTypeno());
					detail.setColor(product.getColor());
					detail.setBrand(product.getBrand());
					detail.setMakearea(product.getMakearea());
					detail.setPackaging(product.getPackaging());
					detail.setUnit(product.getUnit());
					detail.setTradename(product.getTradename());
					detail.setMinnum(product.getItem12());
				}
			}
			order.setOrderDetailList(orderDetailList);
		}
		return order;
	}

	@Override
	public OrderDto queryOrderByOrdercode(String ordercode) {
		return orderDao.queryOrderByOrdercode(ordercode);
	}
	
	@Override
	public void confirmDelivery(OrderDto order) throws Exception {
		//状态=交期确认
		order.setStatus(Constants.ONLINE_ORDER_STATUS_DELIVERY);
		orderDao.updateOrder(order);
		//更新交期
		for(OrderDetailDto detail : order.getOrderDetailList()) {
			detail.setUpdateip(order.getUpdateip());
			detail.setUpdateuid(order.getUpdateuid());
			orderDetailDao.updateOrderDetail(detail);
		}
		
		CustomerOnlineDto customerOnline = customerOnlineDao.queryCustomerOnlineByID("" + order.getCustomerid());
		String url = "";
		MailAuthDto mailauth = new MailAuthDto();
		String before = "orderId=" + order.getId() + "&orderStatus=" + order.getStatus() + "&time=" + new Date().getTime();;
		EncryptionDecryption ee = new EncryptionDecryption();
		String mailauthcode = ee.encrypt(before);
		
		//订单确认
		mailauth.setAuthtype(1);
		mailauth.setCreateuid("system");
		mailauth.setUpdateuid("system");
		mailauth.setMailauthcode(mailauthcode);
		mailauth.setUserid("" + order.getCustomerid());
		mailauth.setStatus(Constants.STATUS_NORMAL);
		mailAuthDao.insertMailAuth(mailauth);
		
		url = PropertiesConfig.getPropertiesValueByKey("dsyg_online_host") + "mailauth/orderConfirmAction.action?strUserid=" + order.getCustomerid() + "&strAuthCode=" + mailauthcode;

		//邮件发送人，MailSender有默认发送人。
		String from = "";
		//收件人姓名，若不填则使用MailSender的默认收件人。
		String to = order.getCustomermail();
		//发件人名
		String username = "DSYG";
		//附件，格式：filename1,filename2,filename3...（这里需要在global.properties配置文件中指定附件目录）
		String attachfile = "";
		//邮件标题
		String subject = "【DSYG】受理交期确认（" + order.getOrdercode() + "）";
		//邮件内容
		String body = "";
		body += customerOnline.getCompanycn() + " 先生/女士<br/>";
		body += "<br/>";
		body += "以下为来自DSYG-Online的交期回复。<br/>";
		body += "<br/>";
		body += "订购多样产品且交期不同时，将统一到货后交货。<br/>";
		body += "如订单需拆分配送，请在下单时使用“订货组”进行分批下单。<br/>";
		body += "<br/>";
		body += "请点击下记URL链接。<br/>";
		body += "在交期确认的网站上可以确认交期以及下订单。<br/>";
		body += url + "<br/>";
		body += "<br/>";
		body += "<br/>";
		body += "注意：<br/>";
		body += "※交期确认的网站有效期为本公司的7个工作日。<br/>";
		body += "　3个工作日后数据将被删除，不能再确认交期及下订单。<br/>";
		body += "※所回复的交期仅为当时确认本公司现有在库后的交期。<br/>";
		body += "　在此之后根据在库的变动，所回复的交期与实际交期会存在变动。<br/>";
		body += "　如有大变动的情况下，在下单后会另行通知。<br/>";
		body += "※所回复的交期是按照本公司的工作日，从货款到账日开始计算的天数。<br/>";
		body += "（并非从下单日开始计算）<br/>";
		body += "<br/>";
		body += "<br/>";
		body += "===================================================<br/>";
		body += "DSYG-Online<br/>";
		body += "东升盈港企业发展有限公司<br/>";
		body += "电话：021－65388038－0（总机）<br/>";
		body += "受理时间: 08:30～12:00、12:45～17:15 (工作日)<br/>";
		body += "Mail：sales@shdsyg.com<br/>";
		body += "https://www.dsyg.com.cn/dsygonline/<br/>";
		body += "===================================================<br/>";
		MailSender.send(from, to, subject, body, username, attachfile);
	}
	
	@Override
	public void confirmOrder(OrderDto order) throws Exception {
		//状态=订单生成
		order.setStatus(Constants.ONLINE_ORDER_STATUS_ORDER);
		orderDao.updateOrder(order);
		
		CustomerOnlineDto customerOnline = customerOnlineDao.queryCustomerOnlineByID("" + order.getCustomerid());
		
		//邮件发送人，MailSender有默认发送人。
		String from = "";
		//收件人姓名，若不填则使用MailSender的默认收件人。
		String to = order.getCustomermail();
		//发件人名
		String username = "DSYG";
		//附件，格式：filename1,filename2,filename3...（这里需要在global.properties配置文件中指定附件目录）
		String attachfile = "";
		//邮件标题
		String subject = "【DSYG】订单已受理（" + order.getOrdercode() + "）";
		//邮件内容
		String body = "";
		body += "非常感谢本次下订单给DSYG-Online。下记订单已受理。<br/>";
		body += "<br/>";
		body += "订单数          : " + DateUtil.dateToLongStr(new Date()) + "<br/>";
		body += "订单编号        : " + order.getOrdercode() + "<br/>";
		body += "<br/>";
		body += "■购买方<br/>";
		body += "--------------------------------------------------------------------<br/>";
		body += "公司名          : " + order.getCompanycn() + "<br/>";
		body += "英文公司名      : " + order.getCompanyen() + "<br/>";
		body += "所属部门        :" + order.getDepartment() + "<br/>";
		body += "姓名            : " + order.getName() + "<br/>";
		body += "邮编            : " + order.getPostcode() + "<br/>";
		body += "地址            : " + order.getAddress() + "<br/>";
		body += "电话号码        : " + order.getTell() + "<br/>";
		body += "E-mail地址      : " + customerOnline.getCustomeremail() + "<br/>";
		body += "<br/>";
		body += "■开户银行<br/>";
		body += "--------------------------------------------------------------------<br/>";
		body += "公司名          : 上海东升营港<br/>";
		body += "公司税号        : " + order.getCompanytax() + "<br/>";
		body += "地址            : 上海市宝山区1000号<br/>";
		body += "电话号码        : 62507788<br/>";
		body += "公司开户行      : " + order.getAccountbank() + "<br/>";
		body += "开户行帐号      : " + order.getAccountid() + "<br/>";
		body += "发票            : 普通发票<br/>";
		body += "<br/>";
		body += "■收件人<br/>";
		body += "--------------------------------------------------------------------<br/>";
		body += "公司名          : " + order.getCompanycn2() + "<br/>";
		body += "英文公司名      : " + order.getCompanyen2() + "<br/>";
		body += "所属部门        :" + order.getDepartment2() + "<br/>";
		body += "姓名            : " + order.getName2() + "<br/>";
		body += "邮编            : " + order.getPostcode2() + "<br/>";
		body += "地址            : " + order.getAddress2() + "<br/>";
		body += "电话号码        : " + order.getTell2() + "<br/>";
		if("2".equals(order.getPaytype())) {
			body += "交货方法        : 自提<br/>";
		} else {
			body += "交货方法        : 配送<br/>";
		}
		body += "<br/>";
		
		for(int i = 0; i < order.getOrderDetailList().size(); i++) {
			OrderDetailDto detail = order.getOrderDetailList().get(i);
			body += "■商品 No." + (i + 1) + "<br/>";
			body += "--------------------------------------------------------------------<br/>";
			body += "商品            : " + detail.getTradename() + "<br/>";
			body += "单价            : " + detail.getTaxprice() + "元<br/>";
			body += "变更数量        : " + detail.getNum() + "个<br/>";
			body += "合计            : " + detail.getTaxamount() + "元<br/>";
			body += "<br/>";
		}
		body += "<br/>";
		body += "--------------------------------------------------------------------<br/>";
		body += "合计            : " + order.getAmount() + "元<br/>";
		body += "含增值税        : " + order.getTaxamount() + "元<br/>";
		body += "--------------------------------------------------------------------<br/>";
		body += "<br/>";
		body += "提示            : 7天内未支付货款，订单将被自动取消。<br/>";
		MailSender.send(from, to, subject, body, username, attachfile);
	}
	
	@Override
	public void confirmPay(OrderDto order) throws Exception {
		//状态=确认收款
		order.setStatus(Constants.ONLINE_ORDER_STATUS_CONFIRM);
		orderDao.updateOrder(order);
		
		if(order.getTransfer() != 1) {
			//这里需要生成warehouse、rpt和财务记录
			confirmWarehouse(order);
		}
		
		CustomerOnlineDto customerOnline = customerOnlineDao.queryCustomerOnlineByID("" + order.getCustomerid());
		
		//邮件发送人，MailSender有默认发送人。
		String from = "";
		//收件人姓名，若不填则使用MailSender的默认收件人。
		String to = order.getCustomermail();
		//发件人名
		String username = "DSYG";
		//附件，格式：filename1,filename2,filename3...（这里需要在global.properties配置文件中指定附件目录）
		String attachfile = "";
		//邮件标题
		String subject = "【DSYG】订单已受理（" + order.getOrdercode() + "）";
		//邮件内容
		String body = "";
		body += customerOnline.getCompanycn() + " 先生/女士<br/>";
		body += "<br/>";
		body += "订单[" + order.getOrdercode() + "] 款项已收到<br/>";
		body += "===================================================<br/>";
		body += "DSYG-Online<br/>";
		body += "东升盈港企业发展有限公司<br/>";
		body += "电话：021－65388038－0（总机）<br/>";
		body += "受理时间: 08:30～12:00、12:45～17:15 (工作日)<br/>";
		body += "Mail：sales@shdsyg.com<br/>";
		body += "https://www.dsyg.com.cn/dsygonline/<br/>";
		body += "===================================================<br/>";
		MailSender.send(from, to, subject, body, username, attachfile);
	}
	
	@Override
	public void sendProduct(OrderDto order) throws Exception {
		//状态=已发货
		order.setStatus(Constants.ONLINE_ORDER_STATUS_SEND);
		orderDao.updateOrder(order);
		//发送邮件
	}
	
	@Override
	public void cancelOrder(OrderDto order) throws Exception {
		//状态=关闭
		order.setStatus(Constants.ONLINE_ORDER_STATUS_CLOSE);
		orderDao.updateOrder(order);
	}

	@Override
	public void insertOrder(OrderDto order) {
		orderDao.insertOrder(order);
	}

	@Override
	public void updateOrder(OrderDto order) {
		orderDao.updateOrder(order);
	}
	
	/**
	 * 生成warehouse、rpt和财务记录
	 * @param order
	 */
	private void confirmWarehouse(OrderDto order) {
		//查询明细数据
		List<OrderDetailDto> orderDetailList = orderDetailDao.queryOrderDetailByOrderid("" + order.getId());
		
		Map<String, String> mapProduct = new HashMap<String, String>();
		Map<String, String> mapParentid = new HashMap<String, String>();
		String belongto = PropertiesConfig.getPropertiesValueByKey("belongto");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		for(OrderDetailDto detail : orderDetailList) {
			ProductDto product = productDao.queryProductByID("" + detail.getProductid());
			
			WarehouseDto warehouse = new WarehouseDto();
			//数据来源单号=采购单
			warehouse.setParentid(order.getOrdercode());
			//online订单号+批号
			String theme2 = order.getOrdercode() + detail.getBatchno();
			warehouse.setTheme2(theme2);
			//库存类型=online订单
			warehouse.setWarehousetype(Constants.WAREHOUSE_TYPE_ONLINE);
			//仓库
			warehouse.setWarehousename(PropertiesConfig.getPropertiesValueByKey("warehouse_name"));
			//预入库时间
			warehouse.setPlandate(DateUtil.dateToShortStr(new Date()));
			
			//出库单号
			String uuid = UUID.randomUUID().toString();
			uuid = uuid.substring(uuid.length() - 8, uuid.length());
			String warehouseno = "OL" + belongto + sdf.format(new Date()) + uuid;
			warehouse.setWarehouseno(warehouseno);
			
			//支付方式=款到发货
			warehouse.setRes01("01");
			
			warehouse.setBelongto(belongto);
			//主题
			warehouse.setTheme1(product.getFieldno());
			//产品ID
			warehouse.setProductid("" + detail.getProductid());
			//入库数量=预入库数
			warehouse.setQuantity(detail.getNum());
			
			//单价
			warehouse.setUnitprice(detail.getTaxprice());
			//含税单价
			warehouse.setRes02("" + detail.getTaxprice());
			//产地
			warehouse.setRes03("" + product.getMakearea());
			
			warehouse.setAmount(detail.getAmount());
			//入出库金额（含税）
			warehouse.setTaxamount(detail.getTaxamount());
			//入库日期=当天
			warehouse.setWarehousedate(DateUtil.dateToShortStr(new Date()));
			//供应商ID
			warehouse.setSupplierid(Long.valueOf(order.getCustomerid()));
			//收货人
			warehouse.setHandler(order.getName2());
	
			warehouse.setRank(Constants.ROLE_RANK_OPERATOR);
			//入库单数据状态=已付款
			warehouse.setStatus(Constants.WAREHOUSE_STATUS_FINISHED);
			
			warehouse.setUpdateuid(order.getCreateuid());
			warehouse.setCreateuid(order.getCreateuid());
			
			warehouseDao.insertWarehouse(warehouse);
			
			//货物信息：产品ID,产品数量,产品金额,备考#产品ID,产品数量,产品金额,备考
			String info = product.getId() + "," + detail.getNum() + "," + detail.getTaxamount() + "," + StringUtil.getStr(warehouse.getRes09()) + "," + StringUtil.getStr(warehouse.getRes02());
			if(mapProduct.get(theme2) != null) {
				String productinfo = mapProduct.get(theme2) + "#" + info;
				mapProduct.put(theme2, productinfo);
				
				String parentids = mapParentid.get(theme2) + "#" + warehouseno;
				mapParentid.put(theme2, parentids);
			} else {
				mapProduct.put(theme2, info);
				mapParentid.put(theme2, warehouseno);
			}
		}
		
		//新增RPT数据
		for(Map.Entry<String, String> entry : mapProduct.entrySet()) {
			WarehouserptDto warehouserpt = new WarehouserptDto();
			//数据来源类型=出库单
			warehouserpt.setWarehousetype(Constants.WAREHOUSERPT_TYPE_OUT);
			warehouserpt.setBelongto(PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_BELONG));
			
			//出库单号
			int newVal = 1;
			SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
			String year = sdfYear.format(new Date());
			//根据出库单+年份查询出库单当前番号
			List<Dict01Dto> dictList = dict01Dao.queryDict01ByFieldcode(Constants.WAREHOUSERPT_OUT_NO_PRE + year, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
			if(dictList != null && dictList.size() > 0) {
				Dict01Dto dict = dictList.get(0);
				//出库单番号+1
				newVal = Integer.valueOf(dict.getCode()) + 1;
				dict.setCode("" + newVal);
				//更新出库单番号
				dict01Dao.updateDict01(dict);
			} else {
				//新增出库单番号
				Dict01Dto dict = new Dict01Dto();
				dict.setCode("1");
				dict.setCreateuid("admin");
				dict.setUpdateuid("admin");
				dict.setFieldcode(Constants.WAREHOUSERPT_OUT_NO_PRE + year);
				dict.setFieldname(year + "出库单番号");
				dict.setNote(year + "出库单番号");
				dict.setLang(PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
				dict.setMean(Constants.WAREHOUSERPT_OUT_NO_PRE + year);
				dict.setStatus(Constants.STATUS_NORMAL);
				dict01Dao.insertDict01(dict);
			}
			String warehouseno = Constants.WAREHOUSERPT_OUT_NO_PRE + belongto + year.substring(2, 4)+ StringUtil.replenishStr("" + newVal, 6);
			
			warehouserpt.setWarehouseno(warehouseno);
			//仓库名
			warehouserpt.setWarehousename(PropertiesConfig.getPropertiesValueByKey("warehouse_name"));
			warehouserpt.setProductinfo(entry.getValue());
			//入库单RPT日期
			warehouserpt.setWarehousedate(DateUtil.dateToShortStr(new Date()));
			
			//计算数量和含税金额
			warehouserpt.setTotalnum(calcTotalnum(entry.getValue()));
			
			//含税金额
			warehouserpt.setTotaltaxamount(calcTaxamount(entry.getValue()));
			//收货人
			warehouserpt.setHandler("");
			
			//获得销售单的客户信息
			warehouserpt.setSupplierid("" + order.getCustomerid());
			warehouserpt.setSuppliername(order.getCompanycn());
			warehouserpt.setSupplieraddress(order.getAddress());
			warehouserpt.setSuppliermail(order.getCustomermail());
			warehouserpt.setSuppliermanager(order.getName());
			warehouserpt.setSuppliertel(order.getTell());
			warehouserpt.setSupplierfax("");
			//快递公司ID==============================这里不做填充，等发货单时填充
			
			warehouserpt.setRank(Constants.ROLE_RANK_OPERATOR);
			//RPT记录状态=已收款
			warehouserpt.setStatus(Constants.FINANCE_STATUS_PAY_INVOICE);
			//入库单单号集合
			warehouserpt.setParentid(mapParentid.get(entry.getKey()));
			warehouserpt.setCreateuid(order.getCreateuid());
			warehouserpt.setUpdateuid(order.getCreateuid());
			
			warehouserptDao.insertWarehouserpt(warehouserpt);
			
			//新增一条财务记录（这里财务记录和出库单关联）
			FinanceDto finance = new FinanceDto();
			//类型=销售单
			finance.setFinancetype(Constants.FINANCE_TYPE_SALES);
			//销售单（收款）
			finance.setMode("1");
			finance.setBelongto(belongto);
			//单据号=出库单号
			finance.setInvoiceid(warehouseno);
			//发票号
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
			String receiptid = Constants.FINANCE_NO_PRE + belongto + sdf1.format(new Date());
			finance.setReceiptid(receiptid);
			//开票日期
			//finance.setReceiptdate(receiptdate);
			//结算日期=当天
			finance.setAccountdate(DateUtil.dateToShortStr(new Date()));
			//金额=销售金额含税
			finance.setAmount(calcTaxamount(entry.getValue()));
			//负责人
			finance.setHandler("online");
			//客户信息
			finance.setCustomerid(Long.valueOf(order.getCustomerid()));
			
			finance.setCustomername(order.getCompanycn());
			finance.setCustomertel(order.getTell());
			finance.setCustomermanager(order.getName());
			finance.setCustomeraddress(order.getAddress());
			finance.setCustomermail(order.getCustomermail());
			finance.setRank(Constants.ROLE_RANK_OPERATOR);
			//状态=已开票已经收款
			finance.setStatus(Constants.FINANCE_STATUS_PAY_INVOICE);
			finance.setCreateuid(order.getCreateuid());
			finance.setUpdateuid(order.getCreateuid());
			financeDao.insertFinance(finance);
		}
	}
	
	/**
	 * 计算总数量
	 * @param productinfo
	 * @return
	 */
	private BigDecimal calcTotalnum(String productinfo) {
		String[] list = productinfo.split("#");
		BigDecimal num = new BigDecimal(0);
		for(String s : list) {
			if(StringUtil.isNotBlank(s)) {
				String ll[] = s.split(",");
				num = num.add(new BigDecimal(ll[1]));
			}
		}
		return num;
	}
	
	/**
	 * 计算含税金额
	 * @param productinfo
	 * @return
	 */
	private BigDecimal calcTaxamount(String productinfo) {
		String[] list = productinfo.split("#");
		BigDecimal taxamount = new BigDecimal(0);
		for(String s : list) {
			if(StringUtil.isNotBlank(s)) {
				String ll[] = s.split(",");
				taxamount = taxamount.add(new BigDecimal(ll[2]));
			}
		}
		return taxamount;
	}
	
	public OrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public OrderDetailDao getOrderDetailDao() {
		return orderDetailDao;
	}

	public void setOrderDetailDao(OrderDetailDao orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
	}

	public Dict01Dao getDict01Dao() {
		return dict01Dao;
	}

	public void setDict01Dao(Dict01Dao dict01Dao) {
		this.dict01Dao = dict01Dao;
	}

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public CustomerOnlineDao getCustomerOnlineDao() {
		return customerOnlineDao;
	}

	public void setCustomerOnlineDao(CustomerOnlineDao customerOnlineDao) {
		this.customerOnlineDao = customerOnlineDao;
	}

	public MailAuthDao getMailAuthDao() {
		return mailAuthDao;
	}

	public void setMailAuthDao(MailAuthDao mailAuthDao) {
		this.mailAuthDao = mailAuthDao;
	}
	
	public static void main(String[] args) throws Exception {
		String before = "orderId=8&orderStatus=20&time=" + new Date().getTime();
		EncryptionDecryption ee = new EncryptionDecryption();
		String mailauthcode = ee.encrypt(before);
		String customerid = "100019";
		String url = PropertiesConfig.getPropertiesValueByKey("dsyg_online_host") + "mailauth/orderConfirmAction.action?strUserid=" + customerid + "&strAuthCode=" + mailauthcode;
		System.out.println(url);
	}

	public WarehouseDao getWarehouseDao() {
		return warehouseDao;
	}

	public void setWarehouseDao(WarehouseDao warehouseDao) {
		this.warehouseDao = warehouseDao;
	}

	public WarehouserptDao getWarehouserptDao() {
		return warehouserptDao;
	}

	public void setWarehouserptDao(WarehouserptDao warehouserptDao) {
		this.warehouserptDao = warehouserptDao;
	}

	public FinanceDao getFinanceDao() {
		return financeDao;
	}

	public void setFinanceDao(FinanceDao financeDao) {
		this.financeDao = financeDao;
	}
}
