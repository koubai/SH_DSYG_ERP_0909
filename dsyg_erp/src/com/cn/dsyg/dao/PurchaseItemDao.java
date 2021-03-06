package com.cn.dsyg.dao;

import java.util.List;

import com.cn.dsyg.dto.PurchaseItemDto;

/**
 * @name PurchaseItemDao.java
 * @author Frank
 * @time 2015-5-17下午10:34:54
 * @version 1.0
 */
public interface PurchaseItemDao {
	
	/**
	 * 根据对应产品ID以及当前设置的价格区间数据查询最近的一次询价信息
	 * @param productid
	 * @param supplierid
	 * @return
	 */
	public PurchaseItemDto queryPurchaseCuPriceByProductID(String productid, String supplierid);
	
	/**
	 * 根据对应产品以及当前设置的价格区间数据查询最近的一次询价信息
	 * @param fieldno 类型
	 * @param tradename 品名
	 * @param typeno 规格
	 * @param packaging 包装
	 * @param unit 单位
	 * @param makearea 产地
	 * @param cupricecode 对应价格区间code
	 * @param supplierid
	 * @return
	 */
	public PurchaseItemDto queryPurchaseCuPriceByProductInfo(String fieldno, String tradename,
			String typeno, String packaging, String unit, String makearea, String cupricecode, String supplierid);
	
	/**
	 * 根据采购单号，物理删除status=0的数据
	 * @param purchaseno
	 */
	public void deleteNoUsePurchaseItemByPurchaseno(String purchaseno);
	
	/**
	 * 根据采购单号，物理删除所有item数据
	 * @param purchaseno
	 */
	public void deleteAllPurchaseItemByPurchaseno(String purchaseno);
	
	/**
	 * 根据采购单号查询采购单货物列表
	 * @param purchaseno
	 * @return
	 */
	public List<PurchaseItemDto> queryPurchaseItemByPurchaseno(String purchaseno);
	
	/**
	 * 根据ID查询采购单货物
	 * @param id
	 * @return
	 */
	public PurchaseItemDto queryPurchaseItemByID(String id);
	
	/**
	 * 根据产品ID和供应商ID查询采购单货物
	 * @param productid
	 * @param supplierid
	 * @param start
	 * @param end
	 * @return
	 */
	public List<PurchaseItemDto> queryPurchaseItemByProductid(String productid, String supplierid, int start, int end);
	
	/**
	 * 根据产品ID和供应商ID查询采购单货物
	 * @param productid
	 * @param supplierid
	 * @return
	 */
	public List<PurchaseItemDto> queryPurchaseItemByProductidForCompare(String productid, String supplierid);
	
	/**
	 * 根据采购单ID逻辑删除采购单货物
	 * @param purchaseno
	 * @param updateuid
	 */
	public void deletePurchaseItemByPurchaseno(String purchaseno, String updateuid);
	
	/**
	 * 新增采购单货物
	 * @param purchaseItem
	 */
	public void insertPurchaseItem(PurchaseItemDto purchaseItem);
	
	/**
	 * 修改采购单货物
	 * @param purchaseItem
	 */
	public void updatePurchaseItem(PurchaseItemDto purchaseItem);
	
	/**
	 * 根据条件查询未入库产品一览数量
	 * @param suppliername
	 * @return
	 */
	public int queryRemainPurchaseCountByPage(String suppliername);
	
	/**
	 * 翻页查询未入库产品一览数据
	 * @param suppliername
	 * @param start
	 * @param end
	 * @return
	 */
	public List<PurchaseItemDto> queryRemainPurchaseByPage(String suppliername, int start, int end);
}
