package com.cn.dsyg.service;

import com.cn.common.util.Page;
import com.cn.dsyg.dto.SalesDto;

public interface SalesHisService {

	public Page querySalesHisByPage(String salesNo, Page page);
	
	public SalesDto querySalesHisByID(String id);
	
	public void insertSalesHis(SalesDto salesDto);
}
