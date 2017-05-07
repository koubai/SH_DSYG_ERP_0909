package com.cn.dsyg.dto;

import com.cn.common.dto.BaseDto;

/**
 * 客户
 * @author 
 * @time 
 * @version 1.0
 */
public class CustomerUnInvoiceDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7633880659334172990L;

	/**
	 * 客户ID
	 */
	private String customerid;

	/**
	 * 客户名
	 */
	private String customername;

	/**
	 * 客户未选择已开票的数据
	 */
	private String uninvoiceamount;


	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getUninvoiceamount() {
		return uninvoiceamount;
	}

	public void setUninvoiceamount(String uninvoiceamount) {
		this.uninvoiceamount = uninvoiceamount;
	}

}
