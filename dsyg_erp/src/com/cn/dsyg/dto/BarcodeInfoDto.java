package com.cn.dsyg.dto;

import java.util.Date;
import java.util.List;

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
	 * 生成barcode批号，逻辑主键
	 */
	private String batchno;

	/**
	 * 产品ID号
	 */
	private String productid;

	/**
	 * BARCODE起始编号
	 */
	private Integer barcodenostart;
	
	/**
	 * BARCODE生成的数量
	 */
	private Integer quantity;

	/**
	 * BARCODE类型，1为入库单，2为出库单
	 */
	private Integer barcodetype;
	
	private Integer barcodeseq;
	private List<BarcodeLogDto> barcodeLogList;

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

	public Integer getBarcodenostart() {
		return barcodenostart;
	}

	public void setBarcodenostart(Integer barcodenostart) {
		this.barcodenostart = barcodenostart;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getBarcodetype() {
		return barcodetype;
	}

	public void setBarcodetype(Integer barcodetype) {
		this.barcodetype = barcodetype;
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

	public List<BarcodeLogDto> getBarcodeLogList() {
		return barcodeLogList;
	}

	public void setBarcodeLogList(List<BarcodeLogDto> barcodeLogList) {
		this.barcodeLogList = barcodeLogList;
	}

	public Integer getBarcodeseq() {
		return barcodeseq;
	}

	public void setBarcodeseq(Integer barcodeseq) {
		this.barcodeseq = barcodeseq;
	}
}
