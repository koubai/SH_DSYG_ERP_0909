package com.cn.dsyg.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cn.common.dto.BaseDto;

/**
 * 产品销售记录
 * @name SalesStatisticsDto.java
 * @author liu
 * @time 2017-11-10上午11:18:37
 * @version 1.0
 */
public class SalesStatisticsDto extends BaseDto {

	private static final long serialVersionUID = -64756351326219310L;

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
	
	private String X_Year;
	
	/**
	 * 产品销售数量
	 */	
	private BigDecimal Y_quantity_Month_01;
	private BigDecimal Y_quantity_Month_02;
	private BigDecimal Y_quantity_Month_03;
	private BigDecimal Y_quantity_Month_04;
	private BigDecimal Y_quantity_Month_05;
	private BigDecimal Y_quantity_Month_06;
	private BigDecimal Y_quantity_Month_07;
	private BigDecimal Y_quantity_Month_08;
	private BigDecimal Y_quantity_Month_09;
	private BigDecimal Y_quantity_Month_10;
	private BigDecimal Y_quantity_Month_11;
	private BigDecimal Y_quantity_Month_12;
	
	/**
	 * 产品销售含税金额
	 */
	private BigDecimal Y_taxamount_Month_01;
	private BigDecimal Y_taxamount_Month_02;
	private BigDecimal Y_taxamount_Month_03;
	private BigDecimal Y_taxamount_Month_04;
	private BigDecimal Y_taxamount_Month_05;
	private BigDecimal Y_taxamount_Month_06;
	private BigDecimal Y_taxamount_Month_07;
	private BigDecimal Y_taxamount_Month_08;
	private BigDecimal Y_taxamount_Month_09;
	private BigDecimal Y_taxamount_Month_10;
	private BigDecimal Y_taxamount_Month_11;
	private BigDecimal Y_taxamount_Month_12;
	
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

	public String getX_Year() {
		return X_Year;
	}

	public void setX_Year(String x_Year) {
		X_Year = x_Year;
	}

	public BigDecimal getY_quantity_Month_01() {
		return Y_quantity_Month_01;
	}

	public void setY_quantity_Month_01(BigDecimal y_quantity_Month_01) {
		Y_quantity_Month_01 = y_quantity_Month_01;
	}

	public BigDecimal getY_quantity_Month_02() {
		return Y_quantity_Month_02;
	}

	public void setY_quantity_Month_02(BigDecimal y_quantity_Month_02) {
		Y_quantity_Month_02 = y_quantity_Month_02;
	}

	public BigDecimal getY_quantity_Month_03() {
		return Y_quantity_Month_03;
	}

	public void setY_quantity_Month_03(BigDecimal y_quantity_Month_03) {
		Y_quantity_Month_03 = y_quantity_Month_03;
	}

	public BigDecimal getY_quantity_Month_04() {
		return Y_quantity_Month_04;
	}

	public void setY_quantity_Month_04(BigDecimal y_quantity_Month_04) {
		Y_quantity_Month_04 = y_quantity_Month_04;
	}

	public BigDecimal getY_quantity_Month_05() {
		return Y_quantity_Month_05;
	}

	public void setY_quantity_Month_05(BigDecimal y_quantity_Month_05) {
		Y_quantity_Month_05 = y_quantity_Month_05;
	}

	public BigDecimal getY_quantity_Month_06() {
		return Y_quantity_Month_06;
	}

	public void setY_quantity_Month_06(BigDecimal y_quantity_Month_06) {
		Y_quantity_Month_06 = y_quantity_Month_06;
	}

	public BigDecimal getY_quantity_Month_07() {
		return Y_quantity_Month_07;
	}

	public void setY_quantity_Month_07(BigDecimal y_quantity_Month_07) {
		Y_quantity_Month_07 = y_quantity_Month_07;
	}

	public BigDecimal getY_quantity_Month_08() {
		return Y_quantity_Month_08;
	}

	public void setY_quantity_Month_08(BigDecimal y_quantity_Month_08) {
		Y_quantity_Month_08 = y_quantity_Month_08;
	}

	public BigDecimal getY_quantity_Month_09() {
		return Y_quantity_Month_09;
	}

	public void setY_quantity_Month_09(BigDecimal y_quantity_Month_09) {
		Y_quantity_Month_09 = y_quantity_Month_09;
	}

	public BigDecimal getY_quantity_Month_10() {
		return Y_quantity_Month_10;
	}

	public void setY_quantity_Month_10(BigDecimal y_quantity_Month_10) {
		Y_quantity_Month_10 = y_quantity_Month_10;
	}

	public BigDecimal getY_quantity_Month_11() {
		return Y_quantity_Month_11;
	}

	public void setY_quantity_Month_11(BigDecimal y_quantity_Month_11) {
		Y_quantity_Month_11 = y_quantity_Month_11;
	}

	public BigDecimal getY_quantity_Month_12() {
		return Y_quantity_Month_12;
	}

	public void setY_quantity_Month_12(BigDecimal y_quantity_Month_12) {
		Y_quantity_Month_12 = y_quantity_Month_12;
	}

	public BigDecimal getY_taxamount_Month_01() {
		return Y_taxamount_Month_01;
	}

	public void setY_taxamount_Month_01(BigDecimal y_taxamount_Month_01) {
		Y_taxamount_Month_01 = y_taxamount_Month_01;
	}

	public BigDecimal getY_taxamount_Month_02() {
		return Y_taxamount_Month_02;
	}

	public void setY_taxamount_Month_02(BigDecimal y_taxamount_Month_02) {
		Y_taxamount_Month_02 = y_taxamount_Month_02;
	}

	public BigDecimal getY_taxamount_Month_03() {
		return Y_taxamount_Month_03;
	}

	public void setY_taxamount_Month_03(BigDecimal y_taxamount_Month_03) {
		Y_taxamount_Month_03 = y_taxamount_Month_03;
	}

	public BigDecimal getY_taxamount_Month_04() {
		return Y_taxamount_Month_04;
	}

	public void setY_taxamount_Month_04(BigDecimal y_taxamount_Month_04) {
		Y_taxamount_Month_04 = y_taxamount_Month_04;
	}

	public BigDecimal getY_taxamount_Month_05() {
		return Y_taxamount_Month_05;
	}

	public void setY_taxamount_Month_05(BigDecimal y_taxamount_Month_05) {
		Y_taxamount_Month_05 = y_taxamount_Month_05;
	}

	public BigDecimal getY_taxamount_Month_06() {
		return Y_taxamount_Month_06;
	}

	public void setY_taxamount_Month_06(BigDecimal y_taxamount_Month_06) {
		Y_taxamount_Month_06 = y_taxamount_Month_06;
	}

	public BigDecimal getY_taxamount_Month_07() {
		return Y_taxamount_Month_07;
	}

	public void setY_taxamount_Month_07(BigDecimal y_taxamount_Month_07) {
		Y_taxamount_Month_07 = y_taxamount_Month_07;
	}

	public BigDecimal getY_taxamount_Month_08() {
		return Y_taxamount_Month_08;
	}

	public void setY_taxamount_Month_08(BigDecimal y_taxamount_Month_08) {
		Y_taxamount_Month_08 = y_taxamount_Month_08;
	}

	public BigDecimal getY_taxamount_Month_09() {
		return Y_taxamount_Month_09;
	}

	public void setY_taxamount_Month_09(BigDecimal y_taxamount_Month_09) {
		Y_taxamount_Month_09 = y_taxamount_Month_09;
	}

	public BigDecimal getY_taxamount_Month_10() {
		return Y_taxamount_Month_10;
	}

	public void setY_taxamount_Month_10(BigDecimal y_taxamount_Month_10) {
		Y_taxamount_Month_10 = y_taxamount_Month_10;
	}

	public BigDecimal getY_taxamount_Month_11() {
		return Y_taxamount_Month_11;
	}

	public void setY_taxamount_Month_11(BigDecimal y_taxamount_Month_11) {
		Y_taxamount_Month_11 = y_taxamount_Month_11;
	}

	public BigDecimal getY_taxamount_Month_12() {
		return Y_taxamount_Month_12;
	}

	public void setY_taxamount_Month_12(BigDecimal y_taxamount_Month_12) {
		Y_taxamount_Month_12 = y_taxamount_Month_12;
	}

}
