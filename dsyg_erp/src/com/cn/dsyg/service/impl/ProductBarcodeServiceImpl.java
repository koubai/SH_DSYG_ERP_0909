package com.cn.dsyg.service.impl;

import java.util.List;

import com.cn.dsyg.dao.ProductBarcodeDao;
import com.cn.dsyg.dto.ProductBarcodeDto;
import com.cn.dsyg.service.ProductBarcodeService;

public class ProductBarcodeServiceImpl implements ProductBarcodeService {
	
	private ProductBarcodeDao productBarcodeDao;

	@Override
	public List<ProductBarcodeDto> queryAllProductBarcodeList(String productid) {
		return productBarcodeDao.queryAllProductBarcodeList(productid);
	}

	@Override
	public ProductBarcodeDto queryProductBarcodeByID(String id) {
		return productBarcodeDao.queryProductBarcodeByID(id);
	}

	@Override
	public ProductBarcodeDto queryProductBarcodeByProductID(String productid) {
		return productBarcodeDao.queryProductBarcodeByProductID(productid);
	}

	@Override
	public void deleteProductBarcodeByProductID(String productid) {
		productBarcodeDao.deleteProductBarcodeByProductID(productid);
	}

	@Override
	public void insertProductBarcode(ProductBarcodeDto productBarcode) {
		productBarcodeDao.insertProductBarcode(productBarcode);
	}

	@Override
	public void updateProductBarcode(ProductBarcodeDto productBarcode) {
		productBarcodeDao.updateProductBarcode(productBarcode);
	}

	public ProductBarcodeDao getProductBarcodeDao() {
		return productBarcodeDao;
	}

	public void setProductBarcodeDao(ProductBarcodeDao productBarcodeDao) {
		this.productBarcodeDao = productBarcodeDao;
	}
}
