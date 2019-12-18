package com.cn.dsyg.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.cn.common.dto.BaseDto;
import com.cn.common.util.StringUtil;

/**
 * @name 产品
 * @author Frank
 * @time 2014-12-16下午11:51:36
 * @version 1.0
 */
public class ProductDto extends BaseDto {
	
	private static final long serialVersionUID = -4977327319491155374L;

	/**
	 * ID
	 */
	private Long id;
	
	/**
	 * 采购单所属地（以后可能分上海和深圳）
	 */
	private String belongto;

	/**
	 * 类型01：电线，02：套管，03：扁平线，04：线束，05：连接器，06：FPC
	 */
	private String fieldno;
	
	/**
	 * BARCODE顺序号（来自productBarcode）
	 */
	private Integer barcodeseq;

	/**
	 * 品牌
	 */
	private String brand;

	/**
	 * 类型1
	 */
	private String item1;

	/**
	 * 类型2
	 */
	private String item2;

	/**
	 * 类型3
	 */
	private String item3;
	
	/**
	 * 品名
	 */
	private String tradename;

	/**
	 * 规格
	 */
	private String typeno;

	/**
	 * 颜色
	 */
	private String color;

	/**
	 * 形式
	 */
	private String packaging;
	
	/**
	 * 单位
	 */
	private String unit;

	/**
	 * 是否样品
	 */
	private String sampleflag;

	/**
	 * 品牌-类型1-品名-规格-颜色-包装-是否样品
	 */
	private String productname;

	/**
	 * 产地
	 */
	private String makearea;
	
	/**
	 * 供应商ID（备用）
	 */
	private Long supplierid;
	
	/**
	 * 销售价
	 */
	private String salesprice;
	
	/**
	 * 采购价
	 */
	private String purchaseprice;
	
	//铜价区间价格
	//单价
	private BigDecimal cuprice;
	//（税后）
	private BigDecimal taxcuprice;
	
	//是否被选中，1为选中
	private String checked;
	//退票数量
	private BigDecimal returnNum = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
	//当前发票数量
	private BigDecimal currinvoicenum = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
	//当前发票金额
	private BigDecimal currinvoiceamount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
	//已开票数量
	private BigDecimal invoicednum = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
	//已开票金额
	private BigDecimal invoicedamount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
	//未开票数量
	private BigDecimal remaininvoicenum = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
	//未开票金额
	private BigDecimal remaininvoiceamount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
	//已开票数量（old）
	private BigDecimal oldinvoicednum = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
	//已开票金额（old）
	private BigDecimal oldinvoicedamount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
	//未开票数量（old）
	private BigDecimal oldremaininvoicenum = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
	//未开票金额（old）
	private BigDecimal oldremaininvoiceamount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
	//平均价格（6位有效数字）
	private BigDecimal averagePrice;
	
	//rpt含税单价（计算已开票用）
	private BigDecimal rpttaxprice;
	
	//数量（入出库单显示用）
	private String num;
	//数量（入出库单显示用）
	private String numabs;
	//单价（入出库单显示用）
	private String unitprice;
	//金额（入出库单显示用）
	private String amount;
	//是否退货或破损
	private String hasbroken;
	//退货或破损数量
	private String brokennum;
	//订单号
	private String parentid;
	
	/**
	 * 备注
	 */
	private String note;

	/**
	 * 项目1
	 */
	private String item01;

	/**
	 * 项目2
	 */
	private String item02;

	/**
	 * 项目3
	 */
	private String item03;

	/**
	 * 项目4
	 */
	private String item04;

	/**
	 * 项目5
	 */
	private String item05;

	/**
	 * 项目6
	 */
	private String item06;

	/**
	 * 项目7
	 */
	private String item07;

	/**
	 * 项目8
	 */
	private String item08;

	/**
	 * 项目9
	 */
	private String item09;

	/**
	 * 包装
	 */
	private String item10;

	/**
	 * 住友代码
	 */
	private String item11;

	/**
	 * 项目12
	 */
	private String item12;

	/**
	 * 项目13
	 */
	private String item13;
	private String scale;
	private String gear1;
	private String scale1;
	private String gear2;
	private String scale2;
	private String gear3;
	private String scale3;
	private String gear4;
	private String scale4;
	private String gear5;
	private String scale5;
	private String gear6;
	private String scale6;
	private String gear7;
	private String scale7;
	private String gear8;
	private String scale8;
	private String gear9;
	private String scale9;
	private String gear10;
	private String scale10;

	/**
	 * 项目14
	 */
	private String item14;

	/**
	 * 项目15
	 */
	private String item15;

	/**
	 * 项目16
	 */
	private String item16;

	/**
	 * 项目17
	 */
	private String item17;

	/**
	 * 项目18
	 */
	private String item18;

	/**
	 * 项目19
	 */
	private String item19;

	/**
	 * 项目20
	 */
	private String item20;

	/**
	 * 项目21
	 */
	private String item21;

	/**
	 * 项目22
	 */
	private String item22;

	/**
	 * 项目23
	 */
	private String item23;

	/**
	 * 项目24
	 */
	private String item24;

	/**
	 * 项目25
	 */
	private String item25;

	/**
	 * 项目26
	 */
	private String item26;

	/**
	 * 项目27
	 */
	private String item27;

	/**
	 * 项目28
	 */
	private String item28;

	/**
	 * 项目29
	 */
	private String item29;

	/**
	 * 项目30
	 */
	private String item30;
	
	/**
	 * 图片网络访问地址
	 */
	private String imageurl;
	
	/**
	 * PDF文件访问地址
	 */
	private String pdfurl;

	/**
	 * 图片1路径
	 */
	private String pic01;

	/**
	 * 图片2路径
	 */
	private String pic02;

	/**
	 * 图片3路径
	 */
	private String pic03;

	/**
	 * 图片4路径
	 */
	private String pic04;

	/**
	 * 图片5路径
	 */
	private String pic05;

	/**
	 * PDF文件路径
	 */
	private String pdfpath;

	/**
	 * 级别(0-99)
	 */
	private Integer rank;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 预备项目1(预计发货量)
	 */
	private String res01;

	/**
	 * 预备项目2(3月内发货量)
	 */
	private String res02;

	/**
	 * 预备项目3(6月内发货量)
	 */
	private String res03;

	/**
	 * 预备项目4(12月内发货量)
	 */
	private String res04;

	/**
	 * 预备项目5(0/null 自动预计发货量，1：人工设定发货量)
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
	 * 关键字，模糊查询用
	 */
	private String keyword;

	/**
	 * 数据创建者
	 */
	private String createuid;

	/**
	 * 数据创建时间
	 */
	private Date createdate;

	/**
	 * 数据更新者
	 */
	private String updateuid;

	/**
	 * 数据更新时间
	 */
	private Date updatedate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFieldno() {
		return fieldno;
	}

	public void setFieldno(String fieldno) {
		this.fieldno = fieldno;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getItem1() {
		return item1;
	}

	public void setItem1(String item1) {
		this.item1 = item1;
	}

	public String getItem2() {
		return item2;
	}

	public void setItem2(String item2) {
		this.item2 = item2;
	}

	public String getItem3() {
		return item3;
	}

	public void setItem3(String item3) {
		this.item3 = item3;
	}

	public String getTradename() {
		return tradename;
	}

	public void setTradename(String tradename) {
		this.tradename = tradename;
	}

	public String getTypeno() {
		return typeno;
	}

	public void setTypeno(String typeno) {
		this.typeno = typeno;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public String getSampleflag() {
		return sampleflag;
	}

	public void setSampleflag(String sampleflag) {
		this.sampleflag = sampleflag;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getMakearea() {
		return makearea;
	}

	public void setMakearea(String makearea) {
		this.makearea = makearea;
	}

	public String getItem01() {
		return item01;
	}

	public void setItem01(String item01) {
		this.item01 = item01;
	}

	public String getItem02() {
		return item02;
	}

	public void setItem02(String item02) {
		this.item02 = item02;
	}

	public String getItem03() {
		return item03;
	}

	public void setItem03(String item03) {
		this.item03 = item03;
	}

	public String getItem04() {
		return item04;
	}

	public void setItem04(String item04) {
		this.item04 = item04;
	}

	public String getItem05() {
		return item05;
	}

	public void setItem05(String item05) {
		this.item05 = item05;
	}

	public String getItem06() {
		return item06;
	}

	public void setItem06(String item06) {
		this.item06 = item06;
	}

	public String getItem07() {
		return item07;
	}

	public void setItem07(String item07) {
		this.item07 = item07;
	}

	public String getItem08() {
		return item08;
	}

	public void setItem08(String item08) {
		this.item08 = item08;
	}

	public String getItem09() {
		return item09;
	}

	public void setItem09(String item09) {
		this.item09 = item09;
	}

	public String getItem10() {
		return item10;
	}

	public void setItem10(String item10) {
		this.item10 = item10;
	}

	public String getItem11() {
		return item11;
	}

	public void setItem11(String item11) {
		this.item11 = item11;
	}

	public String getItem12() {
		return item12;
	}

	public void setItem12(String item12) {
		this.item12 = item12;
	}

	public String getItem13() {
		return item13;
	}

	public void setItem13(String item13) {
		this.item13 = item13;
	}

	public String getItem14() {
		return item14;
	}

	public void setItem14(String item14) {
		this.item14 = item14;
	}

	public String getItem15() {
		return item15;
	}

	public void setItem15(String item15) {
		this.item15 = item15;
	}

	public String getItem16() {
		return item16;
	}

	public void setItem16(String item16) {
		this.item16 = item16;
	}

	public String getItem17() {
		return item17;
	}

	public void setItem17(String item17) {
		this.item17 = item17;
	}

	public String getItem18() {
		return item18;
	}

	public void setItem18(String item18) {
		this.item18 = item18;
	}

	public String getItem19() {
		return item19;
	}

	public void setItem19(String item19) {
		this.item19 = item19;
	}

	public String getItem20() {
		return item20;
	}

	public void setItem20(String item20) {
		this.item20 = item20;
	}

	public String getItem21() {
		return item21;
	}

	public void setItem21(String item21) {
		this.item21 = item21;
	}

	public String getItem22() {
		return item22;
	}

	public void setItem22(String item22) {
		this.item22 = item22;
	}

	public String getItem23() {
		return item23;
	}

	public void setItem23(String item23) {
		this.item23 = item23;
	}

	public String getItem24() {
		return item24;
	}

	public void setItem24(String item24) {
		this.item24 = item24;
	}

	public String getItem25() {
		return item25;
	}

	public void setItem25(String item25) {
		this.item25 = item25;
	}

	public String getItem26() {
		return item26;
	}

	public void setItem26(String item26) {
		this.item26 = item26;
	}

	public String getItem27() {
		return item27;
	}

	public void setItem27(String item27) {
		this.item27 = item27;
	}

	public String getItem28() {
		return item28;
	}

	public void setItem28(String item28) {
		this.item28 = item28;
	}

	public String getItem29() {
		return item29;
	}

	public void setItem29(String item29) {
		this.item29 = item29;
	}

	public String getItem30() {
		return item30;
	}

	public void setItem30(String item30) {
		this.item30 = item30;
	}

	public String getPic01() {
		return pic01;
	}

	public void setPic01(String pic01) {
		this.pic01 = pic01;
	}

	public String getPic02() {
		return pic02;
	}

	public void setPic02(String pic02) {
		this.pic02 = pic02;
	}

	public String getPic03() {
		return pic03;
	}

	public void setPic03(String pic03) {
		this.pic03 = pic03;
	}

	public String getPic04() {
		return pic04;
	}

	public void setPic04(String pic04) {
		this.pic04 = pic04;
	}

	public String getPic05() {
		return pic05;
	}

	public void setPic05(String pic05) {
		this.pic05 = pic05;
	}

	public String getPdfpath() {
		return pdfpath;
	}

	public void setPdfpath(String pdfpath) {
		this.pdfpath = pdfpath;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
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

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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

	public String getBelongto() {
		return belongto;
	}

	public void setBelongto(String belongto) {
		this.belongto = belongto;
	}

	public Long getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(Long supplierid) {
		this.supplierid = supplierid;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getHasbroken() {
		return hasbroken;
	}

	public void setHasbroken(String hasbroken) {
		this.hasbroken = hasbroken;
	}

	public String getBrokennum() {
		return brokennum;
	}

	public void setBrokennum(String brokennum) {
		this.brokennum = brokennum;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public String getPdfurl() {
		return pdfurl;
	}

	public void setPdfurl(String pdfurl) {
		this.pdfurl = pdfurl;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getSalesprice() {
		return salesprice;
	}

	public void setSalesprice(String salesprice) {
		this.salesprice = salesprice;
	}

	public String getPurchaseprice() {
		return purchaseprice;
	}

	public void setPurchaseprice(String purchaseprice) {
		this.purchaseprice = purchaseprice;
	}

	public String getNumabs() {
		return numabs;
	}

	public void setNumabs(String numabs) {
		this.numabs = numabs;
	}

	public String getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(String unitprice) {
		this.unitprice = unitprice;
	}

	public String getScale() {
		if(StringUtil.isNotBlank(item13)) {
			scale = item13.substring(item13.indexOf("-")+1, item13.indexOf(";"));
		}
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getGear1() {
		String[] item13Online;
		if(StringUtil.isNotBlank(item13)) {
			item13Online = item13.split(";");
			BigDecimal it12 = new BigDecimal(item12);
			if(StringUtil.isNotBlank(item13Online[1])){
				BigDecimal it13 = new BigDecimal(item13Online[1].substring(0,item13Online[1].indexOf("-")));
				gear1 = it13.multiply(it12).toString();
			}
		}
		return gear1;
	}

	public void setGear1(String gear1) {
		this.gear1 = gear1;
	}

	public String getScale1() {
		String[] item13Online;
		if(StringUtil.isNotBlank(item13)) {
			item13Online = item13.split(";");
			if(StringUtil.isNotBlank(item13Online[1])){
				scale1 = item13Online[1].substring(item13Online[1].indexOf("-")+1, item13Online[1].length());
			}
		}
		return scale1;
	}

	public void setScale1(String scale1) {
		this.scale1 = scale1;
	}

	public String getGear2() {
		String[] item13Online;
		if(StringUtil.isNotBlank(item13)) {
			item13Online = item13.split(";");
			BigDecimal it12 = new BigDecimal(item12);
			if(StringUtil.isNotBlank(item13Online[2])){
				BigDecimal it13 = new BigDecimal(item13Online[2].substring(0,item13Online[2].indexOf("-")));
				gear2 = it13.multiply(it12).toString();
			}
		}
		return gear2;
	}

	public void setGear2(String gear2) {
		this.gear2 = gear2;
	}

	public String getScale2() {
		String[] item13Online;
		if(StringUtil.isNotBlank(item13)) {
			item13Online = item13.split(";");
			if(StringUtil.isNotBlank(item13Online[2])){
				scale2 = item13Online[2].substring(item13Online[2].indexOf("-")+1, item13Online[2].length());
			}
		}
		return scale2;
	}

	public void setScale2(String scale2) {
		this.scale2 = scale2;
	}

	public String getGear3() {
		String[] item13Online;
		if(StringUtil.isNotBlank(item13)) {
			item13Online = item13.split(";");
			BigDecimal it12 = new BigDecimal(item12);
			if(StringUtil.isNotBlank(item13Online[3])){
				BigDecimal it13 = new BigDecimal(item13Online[3].substring(0,item13Online[3].indexOf("-")));
				gear3 = it13.multiply(it12).toString();
			}
		}
		return gear3;
	}

	public void setGear3(String gear3) {
		this.gear3 = gear3;
	}

	public String getScale3() {
		String[] item13Online;
		if(StringUtil.isNotBlank(item13)) {
			item13Online = item13.split(";");
			if(StringUtil.isNotBlank(item13Online[3])){
				scale3 = item13Online[3].substring(item13Online[3].indexOf("-")+1, item13Online[3].length());
			}
		}
		return scale3;
	}

	public void setScale3(String scale3) {
		this.scale3 = scale3;
	}

	public String getGear4() {
		String[] item13Online;
		if(StringUtil.isNotBlank(item13)) {
			item13Online = item13.split(";");
			BigDecimal it12 = new BigDecimal(item12);
			if(StringUtil.isNotBlank(item13Online[4])){
				BigDecimal it13 = new BigDecimal(item13Online[4].substring(0,item13Online[4].indexOf("-")));
				gear4 = it13.multiply(it12).toString();
			}
		}
		return gear4;
	}

	public void setGear4(String gear4) {
		this.gear4 = gear4;
	}

	public String getScale4() {
		String[] item13Online;
		if(StringUtil.isNotBlank(item13)) {
			item13Online = item13.split(";");
			if(StringUtil.isNotBlank(item13Online[4])){
				scale4 = item13Online[4].substring(item13Online[4].indexOf("-")+1, item13Online[4].length());
			}
		}
		return scale4;
	}

	public void setScale4(String scale4) {
		this.scale4 = scale4;
	}

	public String getGear5() {
		String[] item13Online;
		if(StringUtil.isNotBlank(item13)) {
			item13Online = item13.split(";");
			BigDecimal it12 = new BigDecimal(item12);
			if(StringUtil.isNotBlank(item13Online[5])){
				BigDecimal it13 = new BigDecimal(item13Online[5].substring(0,item13Online[5].indexOf("-")));
				gear5 = it13.multiply(it12).toString();
			}
		}
		return gear5;
	}

	public void setGear5(String gear5) {
		this.gear5 = gear5;
	}

	public String getScale5() {
		String[] item13Online;
		if(StringUtil.isNotBlank(item13)) {
			item13Online = item13.split(";");
			if(StringUtil.isNotBlank(item13Online[5])){
				scale5 = item13Online[5].substring(item13Online[5].indexOf("-")+1, item13Online[5].length());
			}
		}
		return scale5;
	}

	public void setScale5(String scale5) {
		this.scale5 = scale5;
	}

	public String getGear6() {
		String[] item13Online;
		if(StringUtil.isNotBlank(item13)) {
			item13Online = item13.split(";");
			BigDecimal it12 = new BigDecimal(item12);
			if(StringUtil.isNotBlank(item13Online[6])){
				BigDecimal it13 = new BigDecimal(item13Online[6].substring(0,item13Online[6].indexOf("-")));
				gear6 = it13.multiply(it12).toString();
			}
		}
		return gear6;
	}

	public void setGear6(String gear6) {
		this.gear6 = gear6;
	}

	public String getScale6() {
		String[] item13Online;
		if(StringUtil.isNotBlank(item13)) {
			item13Online = item13.split(";");
			if(StringUtil.isNotBlank(item13Online[6])){
				scale6 = item13Online[6].substring(item13Online[6].indexOf("-")+1, item13Online[6].length());
			}
		}
		return scale6;
	}

	public void setScale6(String scale6) {
		this.scale6 = scale6;
	}

	public String getGear7() {
		String[] item13Online;
		if(StringUtil.isNotBlank(item13)) {
			item13Online = item13.split(";");
			BigDecimal it12 = new BigDecimal(item12);
			if(StringUtil.isNotBlank(item13Online[7])){
				BigDecimal it13 = new BigDecimal(item13Online[7].substring(0,item13Online[7].indexOf("-")));
				gear7 = it13.multiply(it12).toString();
			}
		}
		return gear7;
	}

	public void setGear7(String gear7) {
		this.gear7 = gear7;
	}

	public String getScale7() {
		String[] item13Online;
		if(StringUtil.isNotBlank(item13)) {
			item13Online = item13.split(";");
			if(StringUtil.isNotBlank(item13Online[7])){
				scale7 = item13Online[7].substring(item13Online[7].indexOf("-")+1, item13Online[7].length());
			}
		}
		return scale7;
	}

	public void setScale7(String scale7) {
		this.scale7 = scale7;
	}

	public String getGear8() {
		String[] item13Online;
		if(StringUtil.isNotBlank(item13)) {
			item13Online = item13.split(";");
			BigDecimal it12 = new BigDecimal(item12);
			if(StringUtil.isNotBlank(item13Online[8])){
				BigDecimal it13 = new BigDecimal(item13Online[8].substring(0,item13Online[8].indexOf("-")));
				gear8 = it13.multiply(it12).toString();
			}
		}
		return gear8;
	}

	public void setGear8(String gear8) {
		this.gear8 = gear8;
	}

	public String getScale8() {
		String[] item13Online;
		if(StringUtil.isNotBlank(item13)) {
			item13Online = item13.split(";");
			if(StringUtil.isNotBlank(item13Online[8])){
				scale8 = item13Online[8].substring(item13Online[8].indexOf("-")+1, item13Online[8].length());
			}
		}
		return scale8;
	}

	public void setScale8(String scale8) {
		this.scale8 = scale8;
	}

	public String getGear9() {
		String[] item13Online;
		if(StringUtil.isNotBlank(item13)) {
			item13Online = item13.split(";");
			BigDecimal it12 = new BigDecimal(item12);
			if(StringUtil.isNotBlank(item13Online[9])){
				BigDecimal it13 = new BigDecimal(item13Online[9].substring(0,item13Online[9].indexOf("-")));
				gear9 = it13.multiply(it12).toString();
			}
		}
		return gear9;
	}

	public void setGear9(String gear9) {
		this.gear9 = gear9;
	}

	public String getScale9() {
		String[] item13Online;
		if(StringUtil.isNotBlank(item13)) {
			item13Online = item13.split(";");
			if(StringUtil.isNotBlank(item13Online[9])){
				scale9 = item13Online[9].substring(item13Online[9].indexOf("-")+1, item13Online[9].length());
			}
		}
		return scale9;
	}

	public void setScale9(String scale9) {
		this.scale9 = scale9;
	}

	public String getGear10() {
		String[] item13Online;
		if(StringUtil.isNotBlank(item13)) {
			item13Online = item13.split(";");
			BigDecimal it12 = new BigDecimal(item12);
			if(StringUtil.isNotBlank(item13Online[10])){
				BigDecimal it13 = new BigDecimal(item13Online[10].substring(0,item13Online[10].indexOf("-")));
				gear10 = it13.multiply(it12).toString();
			}
		}
		return gear10;
	}

	public void setGear10(String gear10) {
		this.gear10 = gear10;
	}

	public String getScale10() {
		String[] item13Online;
		if(StringUtil.isNotBlank(item13)) {
			item13Online = item13.split(";");
			if(StringUtil.isNotBlank(item13Online[10])){
				scale10 = item13Online[10].substring(item13Online[10].indexOf("-")+1, item13Online[10].length());
			}
		}
		return scale10;
	}

	public void setScale10(String scale10) {
		this.scale10 = scale10;
	}

	public BigDecimal getCurrinvoicenum() {
		return currinvoicenum;
	}

	public void setCurrinvoicenum(BigDecimal currinvoicenum) {
		this.currinvoicenum = currinvoicenum;
	}

	public BigDecimal getCurrinvoiceamount() {
		return currinvoiceamount;
	}

	public void setCurrinvoiceamount(BigDecimal currinvoiceamount) {
		this.currinvoiceamount = currinvoiceamount;
	}

	public BigDecimal getInvoicednum() {
		return invoicednum;
	}

	public void setInvoicednum(BigDecimal invoicednum) {
		this.invoicednum = invoicednum;
	}

	public BigDecimal getInvoicedamount() {
		return invoicedamount;
	}

	public void setInvoicedamount(BigDecimal invoicedamount) {
		this.invoicedamount = invoicedamount;
	}

	public BigDecimal getRemaininvoicenum() {
		return remaininvoicenum;
	}

	public void setRemaininvoicenum(BigDecimal remaininvoicenum) {
		this.remaininvoicenum = remaininvoicenum;
	}

	public BigDecimal getRemaininvoiceamount() {
		return remaininvoiceamount;
	}

	public void setRemaininvoiceamount(BigDecimal remaininvoiceamount) {
		this.remaininvoiceamount = remaininvoiceamount;
	}

	public BigDecimal getOldinvoicednum() {
		return oldinvoicednum;
	}

	public void setOldinvoicednum(BigDecimal oldinvoicednum) {
		this.oldinvoicednum = oldinvoicednum;
	}

	public BigDecimal getOldinvoicedamount() {
		return oldinvoicedamount;
	}

	public void setOldinvoicedamount(BigDecimal oldinvoicedamount) {
		this.oldinvoicedamount = oldinvoicedamount;
	}

	public BigDecimal getOldremaininvoicenum() {
		return oldremaininvoicenum;
	}

	public void setOldremaininvoicenum(BigDecimal oldremaininvoicenum) {
		this.oldremaininvoicenum = oldremaininvoicenum;
	}

	public BigDecimal getOldremaininvoiceamount() {
		return oldremaininvoiceamount;
	}

	public void setOldremaininvoiceamount(BigDecimal oldremaininvoiceamount) {
		this.oldremaininvoiceamount = oldremaininvoiceamount;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public void setAveragePrice(BigDecimal averagePrice) {
		this.averagePrice = averagePrice;
	}

	public BigDecimal getAveragePrice() {
		return averagePrice;
	}

	public BigDecimal getReturnNum() {
		return returnNum;
	}

	public void setReturnNum(BigDecimal returnNum) {
		this.returnNum = returnNum;
	}

	public Integer getBarcodeseq() {
		return barcodeseq;
	}

	public void setBarcodeseq(Integer barcodeseq) {
		this.barcodeseq = barcodeseq;
	}

	public BigDecimal getRpttaxprice() {
		return rpttaxprice;
	}

	public void setRpttaxprice(BigDecimal rpttaxprice) {
		this.rpttaxprice = rpttaxprice;
	}

	public BigDecimal getCuprice() {
		return cuprice;
	}

	public void setCuprice(BigDecimal cuprice) {
		this.cuprice = cuprice;
	}

	public BigDecimal getTaxcuprice() {
		return taxcuprice;
	}

	public void setTaxcuprice(BigDecimal taxcuprice) {
		this.taxcuprice = taxcuprice;
	}
}
