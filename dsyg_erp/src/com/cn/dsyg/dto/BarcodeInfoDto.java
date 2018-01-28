package com.cn.dsyg.dto;

import java.util.Date;

import com.cn.common.dto.BaseDto;

public class BarcodeInfoDto extends BaseDto {

	private static final long serialVersionUID = 4943983873584554716L;

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
	 * 产品名称（显示用）
	 */
	private String tradename;
	
	/**
	 * 生成barcode批号
	 */
	private String batchno;
	
	/**
	 * BARCODE
	 */
	private String barcode;
	
	/**
	 * BARCODE后面15位编号
	 */
	private String barcodeno;
	
	/**
	 * 扫码枪编号
	 */
	private String scanno;
	
	/**
	 * BARCODE类型，1为入库单，2为出库单
	 */
	private Integer barcodetype;
	
	/**
	 * 数量
	 */
	private Integer quantity;
	
	/**
	 * 操作类型，10为生成，20为打印，30为贴标，40为扫码入库，50为扫码出库，60为作废，99为删除
	 */
	private Integer operatetype;

	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 入出库单号
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

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBarcodeno() {
		return barcodeno;
	}

	public void setBarcodeno(String barcodeno) {
		this.barcodeno = barcodeno;
	}

	public String getScanno() {
		return scanno;
	}

	public void setScanno(String scanno) {
		this.scanno = scanno;
	}

	public Integer getBarcodetype() {
		return barcodetype;
	}

	public void setBarcodetype(Integer barcodetype) {
		this.barcodetype = barcodetype;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getOperatetype() {
		return operatetype;
	}

	public void setOperatetype(Integer operatetype) {
		this.operatetype = operatetype;
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

	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	public String getTradename() {
		return tradename;
	}

	public void setTradename(String tradename) {
		this.tradename = tradename;
	}
}
