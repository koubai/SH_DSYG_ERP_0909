package com.cn.dsyg.service;

import java.io.IOException;
import java.util.List;

import com.cn.common.util.Page;
import com.cn.dsyg.dto.WarehouserptDto;

/**
 * @name WarehouserptService.java
 * @author Frank
 * @time 2015-6-3下午9:49:20
 * @version 1.0
 */
public interface WarehouserptService {
	
	/**
	 * 根据入出库单查询数据
	 * @param warehouseno
	 * @param type 1为预开票查询，预开票按产品ID+平均价格进行聚合。非预开票按产品ID+订单ID+产品价格聚合
	 * @return
	 */
	public WarehouserptDto queryWarehouserptByNo(String warehouseno, Integer type);
	
	/**
	 * 修改数据
	 * @param warehouserpt
	 */
	public void updateWarehouserpt(WarehouserptDto warehouserpt);
	
	/**
	 * 导出入出库单数据
	 * @param status
	 * @param warehousetype
	 * @param warehouseno
	 * @param theme1
	 * @param parentid
	 * @param supplierid
	 * @param productid
	 * @return
	 */
	public List<WarehouserptDto> queryAllWarehouserptToExport(String status, String warehousetype,
			String warehouseno, String theme1, String parentid, String supplierid, String productid);
	
	/**
	 * 翻页查询
	 * @param no 采购单OR订单
	 * @param status
	 * @param warehousetype
	 * @param warehouseno
	 * @param theme1
	 * @param parentid
	 * @param supplierid
	 * @param productid
	 * @param beginDate
	 * @param endDate
	 * @param strSuppliername
	 * @param strWarehouseno
	 * @param createdateLow
	 * @param createdateHigh
	 * @param page
	 * @return
	 */
	public Page queryWarehouserptByPage(String no, String status, String warehousetype,
			String warehouseno, String theme1, String parentid, String supplierid,
			String productid, String beginDate, String endDate, String strSuppliername,String strWarehouseno,
			String createdateLow, String createdateHigh, Page page);
	
	/**
	 * 查询RPT数据---出库单导出用 ---add by frank 20200226
	 * @param no
	 * @param status
	 * @param warehousetype
	 * @param warehouseno
	 * @param theme1
	 * @param parentid
	 * @param supplierid
	 * @param productid
	 * @param beginDate
	 * @param endDate
	 * @param strSuppliername
	 * @param strWarehouseno
	 * @param createdateLow
	 * @param createdateHigh
	 * @return
	 */
	public List<WarehouserptDto> queryWarehouserptByCondition(String no, String status, String warehousetype,
			String warehouseno, String theme1, String parentid, String supplierid,
			String productid, String beginDate, String endDate, String strSuppliername,String strWarehouseno,
			String createdateLow, String createdateHigh);
	
	
	/**
	 * 合计含税金额查询
	 * @param no 采购单OR订单
	 * @param status
	 * @param warehousetype
	 * @param warehouseno
	 * @param theme1
	 * @param parentid
	 * @param supplierid
	 * @param productid
	 * @param beginDate
	 * @param endDate
	 * @param strSuppliername
	 * @param strWarehouseno
	 * @param createdateLow
	 * @param createdateHigh
	 * @return
	 */
	public String queryWarehouserptTotalAmount(String no, String status, String warehousetype,
			String warehouseno, String theme1, String parentid, String supplierid,
			String productid, String beginDate, String endDate, String strSuppliername,String strWarehouseno,
			String createdateLow, String createdateHigh);

	
	
	/**
	 * 根据ID查询数据
	 * @param id
	 * @param type 1为预开票查询，预开票按产品ID+平均价格进行聚合。非预开票按产品ID+订单ID+产品价格聚合
	 * @return
	 */
	public WarehouserptDto queryWarehouserptByID(String id, Integer type);
	
	/**
	 * 根据ID查询数据（不查询其他数据）
	 * @param id
	 * @return
	 */
	public WarehouserptDto queryWarehouserptByID(String id);
	
	/**
	 * 根据ID查询数据
	 * @param id
	 * @return
	 */
	public WarehouserptDto queryWarehouserptInterByID(String id);
	
	/**
	 * 根据ID查询数据
	 * @param id
	 * @return
	 */
	public WarehouserptDto queryWarehouserptInterByID2(String id);
	
	/**
	 * 新增数据
	 * @param warehouserpt
	 */
	public void insertWarehouserpt(WarehouserptDto warehouserpt);
	
	/**
	 * 修改数据
	 * @param warehouserpt
	 * @param type 1为入库单，2为出库单
	 */
	public void updateWarehouserpt(WarehouserptDto warehouserpt, Integer type);
	
	/**
	 * 入出库单财务审核
	 * @param id
	 * @param userid
	 * @param status
	 */
	public void approveWarehouserpt(String id, String userid, String status);
	
	/**
	 * 入出库单财务审核（发票号和开票日期）
	 * @param id
	 * @param userid
	 * @param res10
	 * @param receiptdate
	 * @param status
	 */
	public void approveWarehouserpt(String id, String userid, String res10, String receiptdate, String status);
	
	
	/**
	 * 发货单查询（产品号和客户号）
	 * @param productid
	 * @param customerid
	 */
	public List<WarehouserptDto> queryWarehouseInfoList(String strProductid, String strCustomerid);
	
	/**
	 * 用友账套序号更新
	 * @param strAccountFlg 账套选择 0 或空为 上海贸易或深圳发展  1为上海发展
	 * @param strAccountNo  用友账套序号
	 */
	public String updateAccountNo(String strAccountFlg, String strAccountNo);

}
