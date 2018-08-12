package com.cn.dsyg.dto;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class PositionHistDto {

	/**
	 * 盘点商品号
	 */
	private String productid;
	/**
	 * 入库日期
	 */
	private String in_wdate;
	/**
	 * 入库日期
	 */
	private String out_wdate;
	/**
	 * 入库数量
	 */
	private BigDecimal in_quantity;
	
	private Map<String, String>  in_map = new HashMap<String, String>();
	
	public Map<String, String> getIn_map() {
		in_map.put("in_wdate", getIn_wdate());
		in_map.put("in_wquantity", getIn_quantity().toString());
		return in_map;
	}

	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getIn_wdate() {
		return in_wdate;
	}
	public void setIn_wdate(String in_wdate) {
		this.in_wdate = in_wdate;
	}
	public String getOut_wdate() {
		return out_wdate;
	}
	public void setOut_wdate(String out_wdate) {
		this.out_wdate = out_wdate;
	}
	public BigDecimal getIn_quantity() {
		return in_quantity;
	}
	public void setIn_quantity(BigDecimal in_quantity) {
		this.in_quantity = in_quantity;
	}

	public String getJSON_self() {
		String json_self = "";
//		json_self += "\"productid\":" + "\""+ getProductid()+ "\";";
		json_self += "\"in_wdate\":" + "\""+ getIn_wdate()+ "\";";
		json_self += "\"in_quantity\":" + "\""+ getIn_quantity()+ "\";";
		json_self = "{" + json_self +"}";
		return json_self;
	}

}
