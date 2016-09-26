package com.cn.dsyg.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.cn.common.dto.BaseDto;

/**
 * @name OrderDto.java
 * @author Frank
 * @time 2016-9-4下午2:10:05
 * @version 1.0
 */
public class OrderDto extends BaseDto {

	private static final long serialVersionUID = -5277224653340637835L;

 	/**
	 * ID
	 */
	private Integer id;

 	/**
	 * 订单号，逻辑主键
	 */
	private String ordercode;
	
	/**
	 * 1为订单转移，其他为非转移订单
	 */
	private Integer transfer;
	
	/**
	 * 订单产品明细
	 */
	private List<OrderDetailDto> orderDetailList;

 	/**
	 * 线上客户ID
	 */
	private Integer customerid;
	
	/**
	 * 邮件地址
	 */
	private String customermail;

 	/**
	 * 总金额
	 */
	private BigDecimal amount;

 	/**
	 * 总金额含税
	 */
	private BigDecimal taxamount;

 	/**
	 * 交期日
	 */
	private Date deliverydate;

 	/**
	 * 付款日
	 */
	private Date cashdate;

 	/**
	 * 账户类型，1为企业，2为个人
	 */
	private String accounttype;

 	/**
	 * 公司税号
	 */
	private String companytax;

 	/**
	 * 公司开户行
	 */
	private String accountbank;

 	/**
	 * 开户行账号
	 */
	private String accountid;

 	/**
	 * 发票类型，1为普通发票，2为专用发票
	 */
	private Integer receipttype;
	
	/**
	 * 客户开户公司名
	 */
	private String bankcompanyname;
	
	/**
	 * 客户开户公司税号
	 */
	private String bankcompanytax;
	
	/**
	 * 客户开户地址
	 */
	private String bankcompanyaddress;
	
	/**
	 * 客户开户电话
	 */
	private String banktell;
	
	/**
	 * 客户开户行
	 */
	private String bankname;
	
	/**
	 * 客户开户行帐号
	 */
	private String bankaccount;
	
	/**
	 * 客户开户发票
	 */
	private String bankreceipt;

 	/**
	 * 购买方公司名
	 */
	private String companycn;

 	/**
	 * 购买方英文公司名或拼音
	 */
	private String companyen;

 	/**
	 * 购买方所属部门
	 */
	private String department;

 	/**
	 * 购买方姓名
	 */
	private String name;

 	/**
	 * 购买方邮编
	 */
	private String postcode;

 	/**
	 * 购买方地址
	 */
	private String address;

 	/**
	 * 购买方电话号码
	 */
	private String tell;

 	/**
	 * 收件人公司名
	 */
	private String companycn2;

 	/**
	 * 收件人英文公司名或拼音
	 */
	private String companyen2;

 	/**
	 * 所属部门
	 */
	private String department2;

 	/**
	 * 收件人姓名
	 */
	private String name2;

 	/**
	 * 收件人邮编
	 */
	private String postcode2;

 	/**
	 * 收件人地址
	 */
	private String address2;

 	/**
	 * 收件人电话号码
	 */
	private String tell2;

 	/**
	 * 交货方法2：自提，1：配送
	 */
	private String paytype;
	
	/**
	 * 备注
	 */
	private String note;

	/**
	 * 10新建，20交期确认，30交期回复，40下单成功，50付款成功，60收款成功，70已发货，80已收货订单完成，99订单关闭
	 */
	private Integer status;

	/**
	 * 备用字段1
	 */
	private String res01;

	/**
	 * 备用字段2
	 */
	private String res02;

	/**
	 * 备用字段3
	 */
	private String res03;

	/**
	 * 备用字段4
	 */
	private String res04;

	/**
	 * 备用字段5
	 */
	private String res05;

	/**
	 * 最后操作者电脑IP
	 */
	private String updateip;

	/**
	 * 创建者
	 */
	private String createuid;

	/**
	 * 创建时间
	 */
	private Date createdate;

	/**
	 * 更新者
	 */
	private String updateuid;

	/**
	 * 更新时间
	 */
	private Date updatedate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrdercode() {
		return ordercode;
	}

	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}

	public Integer getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Integer customerid) {
		this.customerid = customerid;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getTaxamount() {
		return taxamount;
	}

	public void setTaxamount(BigDecimal taxamount) {
		this.taxamount = taxamount;
	}

	public Date getDeliverydate() {
		return deliverydate;
	}

	public void setDeliverydate(Date deliverydate) {
		this.deliverydate = deliverydate;
	}

	public Date getCashdate() {
		return cashdate;
	}

	public void setCashdate(Date cashdate) {
		this.cashdate = cashdate;
	}

	public String getCompanytax() {
		return companytax;
	}

	public void setCompanytax(String companytax) {
		this.companytax = companytax;
	}

	public String getAccountbank() {
		return accountbank;
	}

	public void setAccountbank(String accountbank) {
		this.accountbank = accountbank;
	}

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public Integer getReceipttype() {
		return receipttype;
	}

	public void setReceipttype(Integer receipttype) {
		this.receipttype = receipttype;
	}

	public String getCompanycn() {
		return companycn;
	}

	public void setCompanycn(String companycn) {
		this.companycn = companycn;
	}

	public String getCompanyen() {
		return companyen;
	}

	public void setCompanyen(String companyen) {
		this.companyen = companyen;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTell() {
		return tell;
	}

	public void setTell(String tell) {
		this.tell = tell;
	}

	public String getCompanycn2() {
		return companycn2;
	}

	public void setCompanycn2(String companycn2) {
		this.companycn2 = companycn2;
	}

	public String getCompanyen2() {
		return companyen2;
	}

	public void setCompanyen2(String companyen2) {
		this.companyen2 = companyen2;
	}

	public String getDepartment2() {
		return department2;
	}

	public void setDepartment2(String department2) {
		this.department2 = department2;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getPostcode2() {
		return postcode2;
	}

	public void setPostcode2(String postcode2) {
		this.postcode2 = postcode2;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getTell2() {
		return tell2;
	}

	public void setTell2(String tell2) {
		this.tell2 = tell2;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRes01() {
		return res01;
	}

	public void setRes01(String res01) {
		this.res01 = res01;
	}

	public String getRes02() {
		return res02;
	}

	public void setRes02(String res02) {
		this.res02 = res02;
	}

	public String getRes03() {
		return res03;
	}

	public void setRes03(String res03) {
		this.res03 = res03;
	}

	public String getRes04() {
		return res04;
	}

	public void setRes04(String res04) {
		this.res04 = res04;
	}

	public String getRes05() {
		return res05;
	}

	public void setRes05(String res05) {
		this.res05 = res05;
	}

	public String getUpdateip() {
		return updateip;
	}

	public void setUpdateip(String updateip) {
		this.updateip = updateip;
	}

	public String getCreateuid() {
		return createuid;
	}

	public void setCreateuid(String createuid) {
		this.createuid = createuid;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getUpdateuid() {
		return updateuid;
	}

	public void setUpdateuid(String updateuid) {
		this.updateuid = updateuid;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public List<OrderDetailDto> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<OrderDetailDto> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getCustomermail() {
		return customermail;
	}

	public void setCustomermail(String customermail) {
		this.customermail = customermail;
	}

	public Integer getTransfer() {
		return transfer;
	}

	public void setTransfer(Integer transfer) {
		this.transfer = transfer;
	}

	public String getBankcompanyname() {
		return bankcompanyname;
	}

	public void setBankcompanyname(String bankcompanyname) {
		this.bankcompanyname = bankcompanyname;
	}

	public String getBankcompanytax() {
		return bankcompanytax;
	}

	public void setBankcompanytax(String bankcompanytax) {
		this.bankcompanytax = bankcompanytax;
	}

	public String getBankcompanyaddress() {
		return bankcompanyaddress;
	}

	public void setBankcompanyaddress(String bankcompanyaddress) {
		this.bankcompanyaddress = bankcompanyaddress;
	}

	public String getBanktell() {
		return banktell;
	}

	public void setBanktell(String banktell) {
		this.banktell = banktell;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getBankaccount() {
		return bankaccount;
	}

	public void setBankaccount(String bankaccount) {
		this.bankaccount = bankaccount;
	}

	public String getBankreceipt() {
		return bankreceipt;
	}

	public void setBankreceipt(String bankreceipt) {
		this.bankreceipt = bankreceipt;
	}
}
