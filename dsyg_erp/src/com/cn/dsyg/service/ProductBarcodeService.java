package com.cn.dsyg.service;

import java.util.List;

import com.cn.dsyg.dto.ProductBarcodeDto;

public interface ProductBarcodeService {

	/**
	 * 根据条件查询所有记录
	 * @param productid
	 * @return
	 */
	public List<ProductBarcodeDto> queryAllProductBarcodeList(String productid);
	
	/**
	 * 根据ID查询数据
	 * @param id
	 * @return
	 */
	public ProductBarcodeDto queryProductBarcodeByID(String id);
	
	/**
	 * 根据逻辑主键查询数据
	 * @param productid
	 * @return
	 */
	public ProductBarcodeDto queryProductBarcodeByProductID(String productid);
	
	/**
	 * 根据产品ID删除数据
	 * @param productid
	 */
	public void deleteProductBarcodeByProductID(String productid);
	
	/**
	 * 插入数据
	 * @param productBarcode
	 */
	public void insertProductBarcode(ProductBarcodeDto productBarcode);
	
	/**
	 * 更新数据
	 * @param productBarcode
	 */
	public void updateProductBarcode(ProductBarcodeDto productBarcode);
}
