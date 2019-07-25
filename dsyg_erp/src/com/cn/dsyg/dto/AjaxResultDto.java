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
	
	/**
	 * 返回的数据内容1
	 */
	private Object data1;
	
	private String minWeight;
	private String minCube;

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

	public Object getData1() {
		return data1;
	}

	public void setData1(Object data1) {
		this.data1 = data1;
	}

	public String getMinWeight() {
		return minWeight;
	}

	public void setMinWeight(String minWeight) {
		this.minWeight = minWeight;
	}

	public String getMinCube() {
		return minCube;
	}

	public void setMinCube(String minCube) {
		this.minCube = minCube;
	}
}
