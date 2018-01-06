package com.cn.dsyg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.common.dao.BaseDao;
import com.cn.dsyg.dao.ProductBarcodeDao;
import com.cn.dsyg.dto.ProductBarcodeDto;

public class ProductBarcodeDaoImpl extends BaseDao implements ProductBarcodeDao {

	@Override
	public List<ProductBarcodeDto> queryAllProductBarcodeList(String productid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productid", productid);
		@SuppressWarnings("unchecked")
		List<ProductBarcodeDto> list = getSqlMapClientTemplate().queryForList("queryAllProductBarcodeList", paramMap);
		return list;
	}

	@Override
	public ProductBarcodeDto queryProductBarcodeByID(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		@SuppressWarnings("unchecked")
		List<ProductBarcodeDto> list = getSqlMapClientTemplate().queryForList("queryProductBarcodeByID", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public ProductBarcodeDto queryProductBarcodeByProductID(String productid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productid", productid);
		@SuppressWarnings("unchecked")
		List<ProductBarcodeDto> list = getSqlMapClientTemplate().queryForList("queryProductBarcodeByProductID", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void deleteProductBarcodeByProductID(String productid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productid", productid);
		getSqlMapClientTemplate().delete("deleteProductBarcodeByProductID", paramMap);
	}

	@Override
	public void insertProductBarcode(ProductBarcodeDto productBarcode) {
		getSqlMapClientTemplate().insert("insertProductBarcode", productBarcode);
	}

	@Override
	public void updateProductBarcode(ProductBarcodeDto productBarcode) {
		getSqlMapClientTemplate().update("updateProductBarcode", productBarcode);
	}
}
