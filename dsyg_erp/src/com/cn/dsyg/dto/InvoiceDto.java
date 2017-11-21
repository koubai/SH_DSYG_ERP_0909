package com.cn.dsyg.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.cn.common.dto.BaseDto;

public class InvoiceDto extends BaseDto {

	private static final long serialVersionUID = 2977043077356677242L;

	/**
	 * id
	 */
	private Long id;
	
	/**
	 * belongto
	 */
	private String belongto;

	/**
	 * warehouse表的NO，多个用逗号分割
	 */
	private String warehouseno;

	/**
	 * 帐目编号对应的warehouserpt表的NO
	 */
	private String warehouserptno;
	
	/**
	 * 产品ID
	 */
	private String productid;
	
	/**
	 * 帐目编号
	 */
	private String financeno;

	/**
	 * 发票号,对于退单的发票号保持空
	 */
	private String invoiceno;

	/**
	 * 客户ID
	 */
	private Long customerid;

	/**
	 * 客户名
	 */
	private String customername;

	/**
	 * 客户备用信息1
	 */
	private String customer_info1;

	/**
	 * 客户备用信息2
	 */
	private String customer_info2;

	/**
	 * 客户备用信息3
	 */
	private String customer_info3;

	/**
	 * 客户备用信息4
	 */
	private String customer_info4;

	/**
	 * 客户备用信息5
	 */
	private String customer_info5;

	/**
	 * 开票数量
	 */
	private BigDecimal quantity;

	/**
	 * 未税单价
	 */
	private BigDecimal price;

	/**
	 * 含税单价
	 */
	private BigDecimal pricetax;

	/**
	 * 开票金额
	 */
	private BigDecimal amount;

	/**
	 * 开票金额（含税）
	 */
	private BigDecimal amounttax;

	/**
	 * 0收到发票（支出），1开出发票（收款）
	 * 对于进货单的发票0，对于发货单的发票:1
	 */
	private Integer recpay;

	/**
	 * 状态，预开票:0，开票:1，退货记录:2，作废:99
	 */
	private Integer status;

	/**
	 * 作废发票号
	 */
	private String invoicedelno;

	/**
	 * 作废数量
	 */
	private BigDecimal quantitydel;

	/**
	 * 作废financeNo
	 */
	private String finanacedelno;

	/**
	 * 作废warehouserptNo
	 */
	private String warehouserptdelno;

	/**
	 * 备注
	 */
	private String note;

	/**
	 * 开票日
	 */
	private Date invoice_date;

	/**
	 * 开票人
	 */
	private String invoide_mem_id;

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
	 * 作成者
	 */
	private String createuid;

	/**
	 * 作成时间
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBelongto() {
		return belongto;
	}

	public void setBelongto(String belongto) {
		this.belongto = belongto;
	}

	public String getWarehouseno() {
		return warehouseno;
	}

	public void setWarehouseno(String warehouseno) {
		this.warehouseno = warehouseno;
	}

	public String getWarehouserptno() {
		return warehouserptno;
	}

	public void setWarehouserptno(String warehouserptno) {
		this.warehouserptno = warehouserptno;
	}

	public String getFinanceno() {
		return financeno;
	}

	public void setFinanceno(String financeno) {
		this.financeno = financeno;
	}

	public String getInvoiceno() {
		return invoiceno;
	}

	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}

	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getCustomer_info1() {
		return customer_info1;
	}

	public void setCustomer_info1(String customer_info1) {
		this.customer_info1 = customer_info1;
	}

	public String getCustomer_info2() {
		return customer_info2;
	}

	public void setCustomer_info2(String customer_info2) {
		this.customer_info2 = customer_info2;
	}

	public String getCustomer_info3() {
		return customer_info3;
	}

	public void setCustomer_info3(String customer_info3) {
		this.customer_info3 = customer_info3;
	}

	public String getCustomer_info4() {
		return customer_info4;
	}

	public void setCustomer_info4(String customer_info4) {
		this.customer_info4 = customer_info4;
	}

	public String getCustomer_info5() {
		return customer_info5;
	}

	public void setCustomer_info5(String customer_info5) {
		this.customer_info5 = customer_info5;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getPricetax() {
		return pricetax;
	}

	public void setPricetax(BigDecimal pricetax) {
		this.pricetax = pricetax;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmounttax() {
		return amounttax;
	}

	public void setAmounttax(BigDecimal amounttax) {
		this.amounttax = amounttax;
	}

	public Integer getRecpay() {
		return recpay;
	}

	public void setRecpay(Integer recpay) {
		this.recpay = recpay;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getInvoicedelno() {
		return invoicedelno;
	}

	public void setInvoicedelno(String invoicedelno) {
		this.invoicedelno = invoicedelno;
	}

	public BigDecimal getQuantitydel() {
		return quantitydel;
	}

	public void setQuantitydel(BigDecimal quantitydel) {
		this.quantitydel = quantitydel;
	}

	public String getFinanacedelno() {
		return finanacedelno;
	}

	public void setFinanacedelno(String finanacedelno) {
		this.finanacedelno = finanacedelno;
	}

	public String getWarehouserptdelno() {
		return warehouserptdelno;
	}

	public void setWarehouserptdelno(String warehouserptdelno) {
		this.warehouserptdelno = warehouserptdelno;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getInvoice_date() {
		return invoice_date;
	}

	public void setInvoice_date(Date invoice_date) {
		this.invoice_date = invoice_date;
	}

	public String getInvoide_mem_id() {
		return invoide_mem_id;
	}

	public void setInvoide_mem_id(String invoide_mem_id) {
		this.invoide_mem_id = invoide_mem_id;
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

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}
}
