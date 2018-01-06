package com.cn.dsyg.dto;

import java.util.Date;

import com.cn.common.dto.BaseDto;

public class ProductBarcodeDto extends BaseDto {

	private static final long serialVersionUID = 6850737908910169374L;

	/**
	 * id
	 */
	private Long id;
	
	/**
	 * belongto
	 */
	private String belongto;

	/**
	 * 产品ID号
	 */
	private String productid;

	/**
	 * BARCODE顺序号
	 */
	private Integer barcodeseq;
	
	/**
	 * 产品包装最小单位数量，例如100米
	 */
	private Integer productqty;

	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 状态，预开票:0，开票:1，退货记录:2，作废:99
	 */
	private Integer status;

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
	private Date res08;
	
	/**
	 * 预备项目9
	 */
	private Date res09;
	
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

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public Integer getBarcodeseq() {
		return barcodeseq;
	}

	public void setBarcodeseq(Integer barcodeseq) {
		this.barcodeseq = barcodeseq;
	}

	public Integer getProductqty() {
		return productqty;
	}

	public void setProductqty(Integer productqty) {
		this.productqty = productqty;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public Date getRes08() {
		return res08;
	}

	public void setRes08(Date res08) {
		this.res08 = res08;
	}

	public Date getRes09() {
		return res09;
	}

	public void setRes09(Date res09) {
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
}
