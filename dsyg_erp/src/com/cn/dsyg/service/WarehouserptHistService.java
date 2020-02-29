package com.cn.dsyg.service;

import java.util.List;

import com.cn.dsyg.dto.WarehouserptHistDto;

public interface WarehouserptHistService {

	/**
	 * 查询RPT修改日志
	 * @param warehousetype
	 * @param warehouseno
	 * @param rptid
	 * @param histtype
	 * @param createdateLow
	 * @param createdateHigh
	 * @return
	 */
	public List<WarehouserptHistDto> queryWarehouserpthistByRprid(String warehousetype, String warehouseno,
			String rptid, String histtype, String createdateLow, String createdateHigh);
	
	/**
	 * 新增RPT修改日志
	 * @param hist
	 */
	public void insertWarehouserpthist(WarehouserptHistDto hist);
}
