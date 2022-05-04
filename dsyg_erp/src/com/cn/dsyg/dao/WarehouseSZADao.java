package com.cn.dsyg.dao;

import com.cn.dsyg.dto.ProductDto;
import com.cn.dsyg.dto.WarehouseDetailDto;
import com.cn.dsyg.dto.WarehouseDto;

/**
 * @name WarehouseSZDao.java
 * @author Frank
 * @time 2015-6-3下午9:44:07
 * @version 1.0
 */
public interface WarehouseSZADao {
	
	/**
	 * 根据产品名称，型号，颜色（逻辑主键）查询产品
	 * @param fieldno
	 * @param tradename
	 * @param typeno
	 * @param color
	 * @param item10
	 * @param packaging
	 * @param unit
	 * @param makearea
	 * @return
	 */
	public ProductDto queryProductByLogicId(String fieldno, String tradename, String typeno,
			String color, String item10, String packaging, String unit, String makearea);
	
	/**
	 * 新增数据
	 * @param warehouse
	 */
	public void insertWarehouse(WarehouseDto warehouse);
	
	/**
	 * 修改数据
	 * @param warehouse
	 */
	public void updateWarehouse(WarehouseDto warehouse);
	
	/**
	 * 根据采购单OR订单号删除库存记录
	 * @param parentid
	 * @param productid
	 * @param status
	 */
	public void deleteWarehouseByParentid(String parentid, String productid, String status);
	

	/**
	 * 根据ID查询数据
	 * @param id
	 * @return
	 */
	public WarehouseDto queryWarehouseByID(String id);
	
	/**
	 * 根据入库单号查询数据
	 * @param warehouseno
	 * @return
	 */
	public WarehouseDto queryWarehouseByWarehouseno(String warehouseno);

	
	/**
	 * 查询特殊仓库库存记录数
	 * @param warehousetype
	 * @param productid
	 * @param warehouseno
	 * @return
	 */
	public Double queryAmountByProductId(String productid);
	
}
