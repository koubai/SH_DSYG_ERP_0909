package com.cn.dsyg.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.cn.common.dto.BaseDto;

/**
 * @name OrderDetailDto.java
 * @author Frank
 * @time 2016-9-4下午2:10:05
 * @version 1.0
 */
public class OrderDetailDto extends BaseDto {

	private static final long serialVersionUID = -5843901725073619762L;

	/**
	 * ID
	 */
	private Integer id;

	/**
	 * 订单ID
	 */
	private Integer orderid;
	
	/**
	 * 批次号
	 */
	private String batchno;

	/**
	 * 产品ID
	 */
	private Integer productid;
	
	/**
	 * 交期
	 */
	private Date deliverydate;
	
	//产品信息START
	/**
	 * 最小包装
	 */
	private String minnum;
	
	/**
	 * 产品名
	 */
	private String tradename;
	
	/**
	 * 类型01：电线，02：套管，03：扁平线，04：线束，05：连接器，06：FPC
	 */
	private String fieldno;
	
	/**
	 * 品牌
	 */
	private String brand;
	
	/**
	 * 规格
	 */
	private String typeno;
	
	/**
	 * 颜色
	 */
	private String color;
	
	/**
	 * 产地
	 */
	private String makearea;
	
	/**
	 * 形式
	 */
	private String packaging;
	
	/**
	 * 单位
	 */
	private String unit;
	//产品信息END

	/**
	 * 数量
	 */
	private BigDecimal num;

	/**
	 * 销售基础含税单价
	 */
	private BigDecimal price;
	
	/**
	 * 实际销售含税单价
	 */
	private BigDecimal taxprice;
	
	/**
	 * 金额
	 */
	private BigDecimal amount;
	
	/**
	 * 含税金额
	 */
	private BigDecimal taxamount;

	/**
	 * 1有效，0无效
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

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	public Integer getProductid() {
		return productid;
	}

	public void setProductid(Integer productid) {
		this.productid = productid;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getTaxprice() {
		return taxprice;
	}

	public void setTaxprice(BigDecimal taxprice) {
		this.taxprice = taxprice;
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

	public BigDecimal getNum() {
		return num;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
	}

	public BigDecimal getTaxamount() {
		return taxamount;
	}

	public void setTaxamount(BigDecimal taxamount) {
		this.taxamount = taxamount;
	}

	public String getTradename() {
		return tradename;
	}

	public void setTradename(String tradename) {
		this.tradename = tradename;
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

	public String getMakearea() {
		return makearea;
	}

	public void setMakearea(String makearea) {
		this.makearea = makearea;
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getMinnum() {
		return minnum;
	}

	public void setMinnum(String minnum) {
		this.minnum = minnum;
	}

	public Date getDeliverydate() {
		return deliverydate;
	}

	public void setDeliverydate(Date deliverydate) {
		this.deliverydate = deliverydate;
	}
}
