package com.cn.dsyg.service.impl;

import java.util.Date;
import java.util.List;

import com.cn.common.mail.MailSender;
import com.cn.common.util.Constants;
import com.cn.common.util.DateUtil;
import com.cn.common.util.EncryptionDecryption;
import com.cn.common.util.Page;
import com.cn.common.util.PropertiesConfig;
import com.cn.common.util.StringUtil;
import com.cn.dsyg.dao.CustomerOnlineDao;
import com.cn.dsyg.dao.Dict01Dao;
import com.cn.dsyg.dao.MailAuthDao;
import com.cn.dsyg.dao.OrderDao;
import com.cn.dsyg.dao.OrderDetailDao;
import com.cn.dsyg.dao.ProductDao;
import com.cn.dsyg.dto.CustomerOnlineDto;
import com.cn.dsyg.dto.MailAuthDto;
import com.cn.dsyg.dto.OrderDetailDto;
import com.cn.dsyg.dto.OrderDto;
import com.cn.dsyg.dto.ProductDto;
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
}
