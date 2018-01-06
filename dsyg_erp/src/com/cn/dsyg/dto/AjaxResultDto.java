package com.cn.dsyg.dto;

public class AjaxResultDto {

	/**
	 * 0为成功，其他为失败
	 */
	private int code;
	
	/**
	 * 返回的消息
	 */
	private String msg;
	
	/**
	 * 返回的数据内容
	 */
	private Object data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
