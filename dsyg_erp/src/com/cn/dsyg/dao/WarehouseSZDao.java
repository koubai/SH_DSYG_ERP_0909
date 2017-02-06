package com.cn.dsyg.dao;

import com.cn.dsyg.dto.ProductDto;
import com.cn.dsyg.dto.WarehouseDetailDto;

/**
 * @name WarehouseSZDao.java
 * @author Frank
 * @time 2015-6-3下午9:44:07
 * @version 1.0
 */
public interface WarehouseSZDao {
	
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
	 * 查询深圳产品对比
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
	 * @param zerodisplay
	 * @param start
	 * @param end
	 * @return
	 */
	public WarehouseDetailDto queryWarehouseSZDetail(String parentid, String keyword,
			String warehousetype, String warehouseno, String theme1, String productid, String tradename,
			String typeno, String color, String warehousename, String zerodisplay);
	
}
