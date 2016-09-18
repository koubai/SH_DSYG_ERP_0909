package com.cn.dsyg.dto;

import com.cn.common.dto.BaseDto;

/**
 * @name CustomerDto.java
 * @author lql
 * @time 
 * @version 1.0
 */
public class CustomerOnlineDto extends BaseDto {
	
	private static final long serialVersionUID = -7085743121359018683L;

	/**
	 * 用户登录ID（主键）
	 */
	private int customerid;
	
	/**
	 * 用户邮件地址
	 */
	private String customeremail;
	
	/**
	 * belongto
	 */
	private String belongto;

	/**
	 * 旧登录密码，修改密码用
	 */
	private String oldpassword;

	/**
	 * 登录密码
	 */
	private String password;
	
	/**
	 * 重复密码
	 */
	private String repassword;
	
	/**
	 * 登录验证码
	 */
	private String verificationcode;
	
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
	 * 收件人信息同购买方信息，0为不同，1为相同
	 */
	private String infoflag;
	
	/**
	 * 收件人公司名
	 */
	private String companycn2;
	
	/**
	 * 收件人英文公司名或拼音
	 */
	private String companyen2;
	
	/**
	 * 收件人所属部门
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
	 * 交货方法，1为配送(运费到付)，2为自提
	 */
	private int paytype;
	
	/**
	 * 账户类型，1为企业，2为个人
	 */
	private int accounttype;
	
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
	private int receipttype;
	
	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 数据状态：1为临时登录，2为正式登录，其他为无效
	 */
	private int status;

	/**
	 * 预备项目1
	 */
	private String res01;

	/**
	 * 预备项目2
	 */
	private String res02;

	/**
	 * 预备项目3
	 */
	private String res03;

	/**
	 * 预备项目4
	 */
	private String res04;

	/**
	 * 预备项目5
	 */
	private String res05;

	/**
	 * 预备项目6
	 */
	private String res06;

	/**
	 * 预备项目7
	 */
	private String res07;

	/**
	 * 预备项目8
	 */
	private String res08;

	/**
	 * 预备项目9
	 */
	private String res09;

	/**
	 * 预备项目10
	 */
	private String res10;

	/**
	 * 数据创建者
	 */
	private String createuid;

	/**
	 * 创建时间
	 */
	private String createdate;

	/**
	 * 创建时间（显示用）
	 */
	private String showcreatedate;

	/**
	 * 更新者
	 */
	private String updateuid;

	/**
	 * 更新时间
	 */
	private String updatedate;
	
	public int getCustomerid() {
		return customerid;
	}

	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}

	public String getCustomeremail() {
		return customeremail;
	}

	public void setCustomeremail(String customeremail) {
		this.customeremail = customeremail;
	}

	public String getBelongto() {
		return belongto;
	}

	public void setBelongto(String belongto) {
		this.belongto = belongto;
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
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

	public String getRes06() {
		return res06;
	}

	public void setRes06(String res06) {
		this.res06 = res06;
	}

	public String getRes07() {
		return res07;
	}

	public void setRes07(String res07) {
		this.res07 = res07;
	}

	public String getRes08() {
		return res08;
	}

	public void setRes08(String res08) {
		this.res08 = res08;
	}

	public String getRes09() {
		return res09;
	}

	public void setRes09(String res09) {
		this.res09 = res09;
	}

	public String getRes10() {
		return res10;
	}

	public void setRes10(String res10) {
		this.res10 = res10;
	}

	public String getCreateuid() {
		return createuid;
	}

	public void setCreateuid(String createuid) {
		this.createuid = createuid;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getUpdateuid() {
		return updateuid;
	}

	public void setUpdateuid(String updateuid) {
		this.updateuid = updateuid;
	}

	public String getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}

	public String getVerificationcode() {
		return verificationcode;
	}

	public void setVerificationcode(String verificationcode) {
		this.verificationcode = verificationcode;
	}

	public int getPaytype() {
		return paytype;
	}

	public void setPaytype(int paytype) {
		this.paytype = paytype;
	}

	public int getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(int accounttype) {
		this.accounttype = accounttype;
	}

	public int getReceipttype() {
		return receipttype;
	}

	public void setReceipttype(int receipttype) {
		this.receipttype = receipttype;
	}

	public String getInfoflag() {
		return infoflag;
	}

	public void setInfoflag(String infoflag) {
		this.infoflag = infoflag;
	}

	public String getShowcreatedate() {
		if(getCreatedate() != null && !getCreatedate().equals("")){
			showcreatedate =  getCreatedate().substring(0, 10);
		} else {
			showcreatedate="";
		}
		return showcreatedate;
	}

	public void setShowcreatedate(String showcreatedate) {
		this.showcreatedate = showcreatedate;
	}
}
