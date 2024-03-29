package com.cn.dsyg.dto;

import java.util.Date;

import com.cn.common.dto.BaseDto;

/**
 * 库存盘点报告
 * @author 
 * @time 
 * @version 1.0
 */
public class WarehouseReportDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 资产ID
	 */
	private int id;

	/**
	 * 资产所属地（以后可能分上海和深圳）
	 */
	private String belongto;

	/**
	 * 库存盘点报告编号
	 */
	private String warehousereportno;

	/**
	 * 库存盘点报告名
	 */
	private String warehousereportname;

	/**
	 * 负责人
	 */
	private String handler;

	/**
	 * 登记日期
	 */
	private String registerdate;

	/**
	 * 登记日期（显示用）
	 */
	private String showregisterdate;


	/**
	 * 用途
	 */
	private String purpose;

	/**
	 * 确认者
	 */
	private String approverid;

	/**
	 * 内容介绍
	 */
	private String note;
	
	/**
	 * 文件访问地址
	 */
	private String reporturl;

	/**
	 * 库存盘点报告1文件路径
	 */
	private String reportpath01;

	/**
	 * 库存盘点报告2文件路径
	 */
	private String reportpath02;


	/**
	 * 库存盘点报告3文件路径
	 */
	private String reportpath03;


	/**
	 * 库存盘点告4文件路径
	 */
	private String reportpath04;


	/**
	 * 库存盘点报告5文件路径
	 */
	private String reportpath05;
	
	/**
	 * 级别(0-99)
	 */
	private int rank;
	
	/**
	 * 状态
	 */
	private int status;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBelongto() {
		return belongto;
	}

	public void setBelongto(String belongto) {
		this.belongto = belongto;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
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

	public String getRegisterdate() {
		return registerdate;
	}

	public void setRegisterdate(String registerdate) {
		this.registerdate = registerdate;
	}

	public String getApproverid() {
		return approverid;
	}

	public void setApproverid(String approverid) {
		this.approverid = approverid;
	}

	public String getShowregisterdate() {
		if(getRegisterdate() != null && !getRegisterdate().equals("")){
			showregisterdate =  getRegisterdate().substring(0, 10);
		} else {
			showregisterdate="";
		}
		return showregisterdate;
	}

	public void setShowregisterdate(String showregisterdate) {
		this.showregisterdate = showregisterdate;
	}

	public String getReportpath01() {
		return reportpath01;
	}

	public void setReportpath01(String reportpath01) {
		this.reportpath01 = reportpath01;
	}

	public String getReportpath02() {
		return reportpath02;
	}

	public void setReportpath02(String reportpath02) {
		this.reportpath02 = reportpath02;
	}

	public String getReportpath03() {
		return reportpath03;
	}

	public void setReportpath03(String reportpath03) {
		this.reportpath03 = reportpath03;
	}

	public String getReportpath04() {
		return reportpath04;
	}

	public void setReportpath04(String reportpath04) {
		this.reportpath04 = reportpath04;
	}

	public String getReportpath05() {
		return reportpath05;
	}

	public void setReportpath05(String reportpath05) {
		this.reportpath05 = reportpath05;
	}

	public String getReporturl() {
		return reporturl;
	}

	public void setReporturl(String reporturl) {
		this.reporturl = reporturl;
	}

	public String getWarehousereportno() {
		return warehousereportno;
	}

	public void setWarehousereportno(String warehousereportno) {
		this.warehousereportno = warehousereportno;
	}

	public String getWarehousereportname() {
		return warehousereportname;
	}

	public void setWarehousereportname(String warehousereportname) {
		this.warehousereportname = warehousereportname;
	}
	
}
