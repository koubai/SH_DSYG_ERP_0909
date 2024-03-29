package com.cn.dsyg.service;

import java.math.BigDecimal;
import java.util.List;

import com.cn.common.util.Page;
import com.cn.dsyg.dto.AjaxResultDto;
import com.cn.dsyg.dto.InOutStockDto;
import com.cn.dsyg.dto.ProductQuantityDto;
import com.cn.dsyg.dto.SalesItemDto;
import com.cn.dsyg.dto.SalesStatisticsDto;
import com.cn.dsyg.dto.WarehouseCheckDto;
import com.cn.dsyg.dto.WarehouseDto;
import com.cn.dsyg.dto.WarehouseOkDto;

/**
 * @name WarehouseService.java
 * @author Frank
 * @time 2015-6-7下午9:04:59
 * @version 1.0
 */
public interface WarehouseService {
	
	/**
	 * 根据产品ID查询成本单价
	 * @param productId
	 * @return
	 */
	public WarehouseDto queryPrimecostByProductId(String productId);
	
	/**
	 * 条形码入出库前验证
	 * @param rptId
	 * @param scanBarcodeInfo
	 * @param type 1为入库单，2为出库单
	 * @param userid
	 * @param checktype
	 * @return
	 */
	public AjaxResultDto barcodeWarehouseInOutCheck(String rptId, String scanBarcodeInfo, Integer type, String userid, boolean checktype);
	
	/**
	 * 条形码入出库
	 * @param rptId
	 * @param scanBarcodeInfo
	 * @param type 1为入库单，2为出库单
	 * @param userid
	 * @return
	 */
	public AjaxResultDto barcodeWarehouseInOut(String rptId, String scanBarcodeInfo, Integer type, String userid);
	
	/**
	 * 查询明细
	 * @param productid
	 * @param warehousetype
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	public List<InOutStockDto> queryInOutStockDetail(String productid, String warehousetype, String startdate, String enddate);
	
	/**
	 * 查询入出库总数量
	 * @param startdate
	 * @param enddate
	 * @param fieldno
	 * @param tradename
	 * @param item10
	 * @param keyword
	 * @param productid
	 * @return
	 */
	public InOutStockDto querySumInOutStock(String startdate, String enddate, String fieldno,
			String tradename, String item10, String keyword, String productid);
	
	/**
	 * 翻页查询出入库明细数据
	 * @param startdate
	 * @param enddate
	 * @param fieldno
	 * @param tradename
	 * @param item10
	 * @param keyword
	 * @param page
	 * @return
	 */
	public Page queryInOutStockByPage(String startdate, String enddate, String fieldno,
			String tradename, String item10, String keyword, Page page);
	
	/**
	 * 查询产品销售记录
	 * @param startdate
	 * @param enddate
	 * @param fieldno
	 * @param tradename
	 * @param item10
	 * @param keyword
	 * @param productid
	 * @return
	 */
	public SalesStatisticsDto querySumSalesStatistics(String startdate, String enddate, String fieldno,
			String tradename, String item10, String keyword, String productid);
	
	/**
	 * 翻页查询产品销售记录
	 * @param startdate
	 * @param enddate
	 * @param fieldno
	 * @param tradename
	 * @param item10
	 * @param keyword
	 * @param page
	 * @return
	 */
	public Page querySalesStatisticsByPage(String startdate, String enddate, String fieldno,
			String tradename, String item10, String keyword, Page page);
	
	/**
	 * 产品数量验证
	 * @param productInfo
	 * @return
	 */
	public String checkProductAmount(String productInfo);
	
	/**
	 * 根据产品ID盘点库存数量
	 * @param productid
	 * @param num
	 * @param position
	 * @param userid
	 * @return
	 */
	public boolean checkProductQuantity(String productid, String num, String position, String userid);
	
	/**
	 * 根据产品ID查询库存数量
	 * @param productid
	 * @return
	 */
	public ProductQuantityDto queryProductQuantityById(String productid);
	
	/**
	 * 翻页查询退换货记录
	 * @param warehousetype
	 * @param theme1
	 * @param warehousename
	 * @param page
	 * @return
	 */
	public Page queryWarehouseRefundByPage(String warehousetype,
			String theme1, String warehousename, Page page);
	
	/**
	 * 库存盘点
	 * @param parentid
	 * @param warehousetype
	 * @param warehouseno
	 * @param theme1
	 * @param productid
	 * @param tradename
	 * @param typeno
	 * @param color
	 * @param warehousename
	 * @param page
	 * @return
	 */
	public Page queryWarehouseCheckByPage(String parentid, String warehousetype,
			String warehouseno, String theme1, String productid, String tradename,
			String typeno, String color, String warehousename, Page page);
	
	/**
	 * 库存盘点
	 * @param parentid
	 * @param warehousetype
	 * @param warehouseno
	 * @param theme1
	 * @param productid
	 * @param tradename
	 * @param typeno
	 * @param color
	 * @param warehousename
	 * @return
	 */
	public List<WarehouseCheckDto> queryWarehouseCheckToExcel(String parentid, String warehousetype,
			String warehouseno, String theme1, String productid, String tradename,
			String typeno, String color, String warehousename);
	
	//库存产品
	/**
	 * 翻页查询库存产品
	 * @param parentid
	 * @param warehousetype
	 * @param warehouseno
	 * @param theme1
	 * @param productid
	 * @param tradename
	 * @param typeno
	 * @param color
	 * @param warehousename
	 * @param page
	 * @return
	 */
	public Page queryWarehouseProductByPage(String parentid, String warehousetype,
			String warehouseno, String theme1, String productid, String tradename,
			String typeno, String color, String warehousename, Page page);
	
	/**
	 * 预入库确认
	 * @param ids
	 * @param userid
	 * @throws Exception
	 */
	public void warehouseInOk(String ids, String userid, String strWarehouseNo) throws RuntimeException;
	
	/**
	 * 预出库确认
	 * @param ids
	 * @param userid
	 * @throws Exception
	 */
	public void warehouseOutOk(String ids, String userid, String strWarehouseNo) throws RuntimeException;
	
	/**
	 * 库存汇总数据
	 * @param warehouseType
	 * @param theme
	 * @param tradename
	 * @param typeno
	 * @param color
	 * @param warehousename
	 * @param status
	 * @param page
	 * @return
	 */
	public Page queryWarehouseOkByPage(String warehouseType, String theme, String tradename,
			String typeno, String color, String warehousename, String status, Page page);
	
	/**
	 * @param theme
	 * @param tradename
	 * @param typeno
	 * @param color
	 * @param warehousename
	 * @param status
	 * @return
	 */
	public Page queryWarehouseInOkByPage(String suppliername, String theme, String tradename,
			String typeno, String color, String warehousename, String status, Page page);
	
	/**
	 * @param theme
	 * @param tradename
	 * @param typeno
	 * @param color
	 * @param warehousename
	 * @param status
	 * @return
	 */
	public Page queryWarehouseOutOkByPage(String suppliername, String theme, String tradename,
			String typeno, String color, String warehousename, String status, Page page);
	
	/**
	 * ERP
	 * @param theme
	 * @param tradename
	 * @param typeno
	 * @param color
	 * @param warehousename
	 * @param status
	 * @return
	 */
	public Page queryWarehouseOutOk1ByPage(String suppliername, String theme, String tradename,
			String typeno, String color, String warehousename, String status, Page page);
	
	/**
	 * online
	 * @param theme
	 * @param tradename
	 * @param typeno
	 * @param color
	 * @param warehousename
	 * @param status
	 * @return
	 */
	public Page queryWarehouseOutOk2ByPage(String suppliername, String theme, String tradename,
			String typeno, String color, String warehousename, String status, Page page);

	/**
	 * 翻页查询
	 * @param parentid
	 * @param warehousetype
	 * @param warehouseno
	 * @param theme1
	 * @param supplierid
	 * @param productid
	 * @param status
	 * @param warehousename
	 * @param page
	 * @return
	 */
	public Page queryWarehouseByPage(String parentid, String warehousetype,
			String warehouseno, String theme1, String supplierid, String productid,
			String status, String warehousename, Page page);
	
	/**
	 * 产品对比
	 * @param parentid
	 * @param keyword
	 * @param warehousetype
	 * @param warehouseno
	 * @param theme1
	 * @param productid
	 * @param tradename
	 * @param typeno
	 * @param color
	 * @param warehousename
	 * @param page
	 * @return
	 */
	public Page queryWarehouseDetailByPage(String parentid, String keyword, String warehousetype,
			String warehouseno, String theme1, String productid, String tradename,
			String typeno, String color, String warehousename, String zeroDisplay, String totalQtyDisplay, String expiredDisplay, String whFlg, Page page);
	
	/**
	 * 根据ID查询数据
	 * @param id
	 * @return
	 */
	public WarehouseDto queryWarehouseByID(String id);
	
	/**
	 * 根据ID列表查询库存记录
	 * @param ids
	 * @return
	 */
	public List<WarehouseDto> queryWarehouseByIds(String ids);
	
	/**
	 * 根据父单号和产品ID查询数据
	 * @param parentid
	 * @param productid
	 * @return
	 */
	public WarehouseDto queryWarehouseByParentidAndProductid(String parentid, String productid);
	
	/**
	 * 根据父单号查询数据
	 * @param parentid
	 * @return
	 */
	public List<WarehouseDto> queryWarehouseByParentid(String parentid);
	
	/**
	 * 根据入库单号查询数据
	 * @param warehouseno
	 * @return
	 */
	public WarehouseDto queryWarehouseByWarehouseno(String warehouseno);
	
	/**
	 * 新增数据
	 * @param warehouse
	 */
	public void insertWarehouse(WarehouseDto warehouse);
	
	/**
	 * 新增退换货数据
	 * @param warehouse
	 * @return
	 */
	public String insertRefundWarehouse(WarehouseDto warehouse);
	
	/**
	 * 修改数据
	 * @param warehouse
	 */
	public void updateWarehouse(WarehouseDto warehouse);
	
	/**
	 * 修改数据（库存修正及退换货时，需要计算成本价）
	 * @param warehouse
	 */
	public void updateRefundWarehouse(WarehouseDto warehouse);
	
	/**
	 * 根据产品id查询流水
	 * @param productid
	 * @return
	 */
	public List<WarehouseOkDto> queryProductBookByProductid(String productid);
	
	/**
	 * 保存EXCEL信息进DB
	 * @param List<WarehouseCheckDto>
	 * @return
	 */
	public void loadWarehouseCheck(List<WarehouseCheckDto> list);
	
	
	/**
	 * 根据产品信息盘点库存数量
	 * @param chkdto
	 * @param userid
	 * @return
	 */
	public boolean checkProductQuantity(WarehouseCheckDto chkdto, String userid);
	
	public BigDecimal getTotalQty();
	public void setTotalQty(BigDecimal totalQty);
	public void getSalesItemPrimecost(SalesItemDto item);	

}
