package com.cn.dsyg.dto;

import java.math.BigDecimal;

import com.cn.common.dto.BaseDto;

public class FinanceProductDetailDto extends BaseDto {

	private static final long serialVersionUID = 8203275419119201813L;
	
	/**
	 * 库存ID（用逗号分割）
	 */
	private String warehouseids;
	
	/**
	 * 库存编号（用逗号分割）
	 */
	private String warehousenos;
	
	/**
	 * 类型，1为采购单，2位销售单
	 */
	private Integer warehousetype;
	
	/**
	 * 供应商/客户ID
	 */
	private String customerid;

	/**
	 * 产品ID号
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
	 * 单位
	 */
	private String unit;
	
	/**
	 * 产地
	 */
	private String makearea;
	
	/**
	 * 入出库数量
	 */
	private BigDecimal quantity;
	
	/**
	 * 金额（含税）
	 */
	private BigDecimal amounttax;
	
	/**
	 * 已开票数量
	 */
	private BigDecimal invoicequantity;
	
	/**
	 * 已开票金额=已开票数量*含税单价
	 */
	private BigDecimal invoiceamount;
	
	/**
	 * 含税单价
	 */
	private BigDecimal unitprice;
	
	/**
	 * 未开票数量
	 */
	private BigDecimal remainquantity;
	
	/**
	 * 未开票金额=未开票数量*含税单价
	 */
	private BigDecimal remainamount;
	
	/**
	 * 当前开票数量
	 */
	private BigDecimal currquantity;
	
	/**
	 * 当前开票金额=当前开票数量*含税单价
	 */
	private BigDecimal curramount;

	public String getWarehouseids() {
		return warehouseids;
	}

	public void setWarehouseids(String warehouseids) {
		this.warehouseids = warehouseids;
	}

	public String getWarehousenos() {
		return warehousenos;
	}

	public void setWarehousenos(String warehousenos) {
		this.warehousenos = warehousenos;
	}

	public Integer getWarehousetype() {
		return warehousetype;
	}

	public void setWarehousetype(Integer warehousetype) {
		this.warehousetype = warehousetype;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getMakearea() {
		return makearea;
	}

	public void setMakearea(String makearea) {
		this.makearea = makearea;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getAmounttax() {
		return amounttax;
	}

	public void setAmounttax(BigDecimal amounttax) {
		this.amounttax = amounttax;
	}

	public BigDecimal getInvoicequantity() {
		return invoicequantity;
	}

	public void setInvoicequantity(BigDecimal invoicequantity) {
		this.invoicequantity = invoicequantity;
	}

	public BigDecimal getInvoiceamount() {
		return invoiceamount;
	}

	public void setInvoiceamount(BigDecimal invoiceamount) {
		this.invoiceamount = invoiceamount;
	}

	public BigDecimal getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(BigDecimal unitprice) {
		this.unitprice = unitprice;
	}

	public BigDecimal getRemainquantity() {
		return remainquantity;
	}

	public void setRemainquantity(BigDecimal remainquantity) {
		this.remainquantity = remainquantity;
	}

	public BigDecimal getRemainamount() {
		return remainamount;
	}

	public void setRemainamount(BigDecimal remainamount) {
		this.remainamount = remainamount;
	}

	public BigDecimal getCurrquantity() {
		return currquantity;
	}

	public void setCurrquantity(BigDecimal currquantity) {
		this.currquantity = currquantity;
	}

	public BigDecimal getCurramount() {
		return curramount;
	}

	public void setCurramount(BigDecimal curramount) {
		this.curramount = curramount;
	}
}
