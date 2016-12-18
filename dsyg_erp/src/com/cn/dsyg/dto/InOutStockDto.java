package com.cn.dsyg.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.cn.common.dto.BaseDto;

/**
 * 出入库明细
 * @name InOutStockDto.java
 * @author Frank
 * @time 2016-12-18上午11:18:37
 * @version 1.0
 */
public class InOutStockDto extends BaseDto {

	private static final long serialVersionUID = 8528960428345878272L;
	
	//detail
	private String id;//warehouseid
	private String parentid;
	private String warehousetype;
	private BigDecimal quantity;
	private Date warehousedate;
	private String theme2;
	private String customername;

	/**
	 * 产品ID
	 */
	private String productid;
	
	/**
	 * 类型01：电线，02：套管，03：扁平线，04：线束，05：连接器，06：FPC
	 */
	private String fieldno;
	
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
	 * 包装
	 */
	private String item10;
	
	/**
	 * 单位
	 */
	private String unit;
	
	/**
	 * 形式
	 */
	private String packaging;
	
	/**
	 * 产地
	 */
	private String makearea;
	
	/**
	 * 关键字，模糊查询用
	 */
	private String keyword;
	
	/**
	 * 产品入库数量
	 */
	private BigDecimal quantityin;
	
	/**
	 * 产品出库数量
	 */
	private BigDecimal quantityout;

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getFieldno() {
		return fieldno;
	}

	public void setFieldno(String fieldno) {
		this.fieldno = fieldno;
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

	public String getItem10() {
		return item10;
	}

	public void setItem10(String item10) {
		this.item10 = item10;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public String getMakearea() {
		return makearea;
	}

	public void setMakearea(String makearea) {
		this.makearea = makearea;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public BigDecimal getQuantityin() {
		return quantityin;
	}

	public void setQuantityin(BigDecimal quantityin) {
		this.quantityin = quantityin;
	}

	public BigDecimal getQuantityout() {
		if(quantityout != null) {
			quantityout = quantityout.abs();
		}
		return quantityout;
	}

	public void setQuantityout(BigDecimal quantityout) {
		this.quantityout = quantityout;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getWarehousetype() {
		return warehousetype;
	}

	public void setWarehousetype(String warehousetype) {
		this.warehousetype = warehousetype;
	}

	public String getTheme2() {
		return theme2;
	}

	public void setTheme2(String theme2) {
		this.theme2 = theme2;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getQuantity() {
		if(quantity != null) {
			quantity = quantity.abs();
		}
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public Date getWarehousedate() {
		return warehousedate;
	}

	public void setWarehousedate(Date warehousedate) {
		this.warehousedate = warehousedate;
	}
}
