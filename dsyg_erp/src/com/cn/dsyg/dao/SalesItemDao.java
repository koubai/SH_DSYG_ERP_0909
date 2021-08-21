package com.cn.dsyg.dao;

import java.util.List;

import com.cn.dsyg.dto.SalesItemDto;

/**
 * @name SalesItemDao.java
 * @author Frank
 * @time 2015-5-17下午10:34:54
 * @version 1.0
 */
public interface SalesItemDao {
	
	/**
	 * 根据对应产品ID以及当前设置的价格区间数据查询最近的一次询价信息
	 * @param productid
	 * @param customerid
	 * @return
	 */
	public SalesItemDto queryCuPriceByProductID(String productid, String customerid);
	
	/**
	 * 根据对应产品以及当前设置的价格区间数据查询最近的一次询价信息
	 * @param fieldno 类型
	 * @param tradename 品名
	 * @param typeno 规格
	 * @param packaging 包装
	 * @param unit 单位
	 * @param makearea 产地
	 * @param cupricecode 对应价格区间code
	 * @param customerid
	 * @return
	 */
	public SalesItemDto queryCuPriceByProductInfo(String fieldno, String tradename,
			String typeno, String packaging, String unit, String makearea, String cupricecode, String customerid);

	//detail start
	/**
	 * 根据条件查询满足条件的销售单数量
	 * @param customerid
	 * @return
	 */
	public int queryDetailProductCountByPage(String customerid);
	
	/**
	 * 翻页查询满足条件的销售数据
	 * @param customerid
	 * @param start
	 * @param end
	 * @return
	 */
	public List<SalesItemDto> queryDetailProductByPage(String customerid, int start, int end);
	//detail end
	
	/**
	 * 根据销售单号查询销售单货物列表
	 * @param salesno
	 * @return
	 */
	public List<SalesItemDto> querySalesItemBySalesno(String salesno);
	
	/**
	 * 根据ID查询销售单货物
	 * @param id
	 * @return
	 */
	public SalesItemDto querySalesItemByID(String id);
	
	/**
	 * 根据ProductId查询销售单逾期3M未发货物
	 * @param productid
	 * @return
	 */
	public List<SalesItemDto> query3MUnSndItemsByProductId(String productid);
	
	/**
	 * 根据销售单ID逻辑删除销售单货物
	 * @param salesno
	 * @param updateuid
	 */
	public void deleteSalesItemBySalesno(String salesno, String updateuid);
	
	/**
	 * 根据销售单编号，物理删除status=0的数据
	 * @param salesno
	 */
	public void deleteNoUseSalesItemBySalesno(String salesno);
	
	/**
	 * 根据销售单编号，物理删除item数据
	 * @param salesno
	 */
	public void deleteAllSalesItemBySalesno(String salesno);
	
	/**
	 * 新增销售单货物
	 * @param salesItem
	 */
	public void insertSalesItem(SalesItemDto salesItem);
	
	/**
	 * 修改销售单货物
	 * @param salesItem
	 */
	public void updateSalesItem(SalesItemDto salesItem);
	
	/**
	 * 根据产品ID和客户ID查询销售单货物
	 * @param productid
	 * @param customerid
	 * @param start
	 * @param end
	 * @return
	 */
	public List<SalesItemDto> querySalesItemByProductid(String productid, String customerid, int start, int end);
	
	/**
	 * 根据产品ID和客户ID查询销售单货物（产品对照用）
	 * @param productid
	 * @param customerid
	 * @return
	 */
	public List<SalesItemDto> querySalesItemByProductidForCompare(String productid, String customerid);
	
	/**
	 * 根据条件查询未出库产品一览数量
	 * @param customername
	 * @return
	 */
	public int queryRemainSalesCountByPage(String customername);
	
	/**
	 * 翻页查询未出库产品一览数据
	 * @param customername
	 * @param start
	 * @param end
	 * @return
	 */
	public List<SalesItemDto> queryRemainSalesByPage(String customername, int start, int end);
}
