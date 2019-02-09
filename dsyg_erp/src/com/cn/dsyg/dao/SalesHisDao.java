package com.cn.dsyg.dao;

import java.util.List;

import com.cn.dsyg.dto.SalesDto;

public interface SalesHisDao {

	public int querySalesHisCountByPage(String salesNo);
	
	public List<SalesDto> querySalesHisByPage(String salesNo, int start, int end);
	
	public SalesDto querySalesHisByID(String id);
	
	public long insertSalesHis(SalesDto salesDto);
}
