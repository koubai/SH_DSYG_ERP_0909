package com.cn.dsyg.dto;

import java.util.Date;

import com.cn.common.dto.BaseDto;

/**
 * MailAuthDto
 * @author Frank
 * @version 1.0
 * @create 2016-8-5下午2:44:19
 */
public class MailAuthDto extends BaseDto {

	private static final long serialVersionUID = 8115777844582271686L;

	/**
	 * ID
	 */
	private Integer id;
	
	/**
	 * 类型，1为订单确认
	 */
	private Integer authtype;
	
	/**
	 * 用户ID
	 */
	private String userid;
	
	/**
	 * 认证内容，加密后
	 */
	private String mailauthcode;
	
	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 1为有效，0为无效
	 */
	private Integer status;
	
	/**
	 * 数据更新IP
	 */
	private String updateip;
	
	/**
	 * 数据创建者
	 */
	private String createuid;
	
	/**
	 * 创建时间
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

	public Integer getId() {
		return id;
	}

	public String getUserid() {
		return userid;
	}

	public String getMailauthcode() {
		return mailauthcode;
	}

	public String getNote() {
		return note;
	}

	public Integer getStatus() {
		return status;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public String getUpdateip() {
		return updateip;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setMailauthcode(String mailauthcode) {
		this.mailauthcode = mailauthcode;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public void setUpdateip(String updateip) {
		this.updateip = updateip;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getCreateuid() {
		return createuid;
	}

	public String getUpdateuid() {
		return updateuid;
	}

	public void setCreateuid(String createuid) {
		this.createuid = createuid;
	}

	public void setUpdateuid(String updateuid) {
		this.updateuid = updateuid;
	}

	public Integer getAuthtype() {
		return authtype;
	}

	public void setAuthtype(Integer authtype) {
		this.authtype = authtype;
	}
}
