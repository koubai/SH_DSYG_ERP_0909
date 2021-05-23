package com.cn.dsyg.dto;

import java.math.BigDecimal;

import com.cn.common.dto.BaseDto;

public class CalcDeliveryPriceDto extends BaseDto {

	private static final long serialVersionUID = -8628173959058128877L;
	
	private int deliveryid;

	private String deliveryname;
	
	private String unitprice;

	private BigDecimal expressincamount;
	
	private BigDecimal deliveryprice;

	public String getDeliveryname() {
		return deliveryname;
	}

	public void setDeliveryname(String deliveryname) {
		this.deliveryname = deliveryname;
	}

	public String getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(String unitprice) {
		this.unitprice = unitprice;
	}

	public BigDecimal getDeliveryprice() {
		return deliveryprice;
	}

	public void setDeliveryprice(BigDecimal deliveryprice) {
		this.deliveryprice = deliveryprice;
	}

	public int getDeliveryid() {
		return deliveryid;
	}

	public void setDeliveryid(int deliveryid) {
		this.deliveryid = deliveryid;
	}

	public BigDecimal getExpressincamount() {
		return expressincamount;
	}

	public void setExpressincamount(BigDecimal expressincamount) {
		this.expressincamount = expressincamount;
	}
}
