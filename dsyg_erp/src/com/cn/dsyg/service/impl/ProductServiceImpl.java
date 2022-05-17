package com.cn.dsyg.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.cn.common.util.Constants;
import com.cn.common.util.DateUtil;
import com.cn.common.util.Page;
import com.cn.common.util.PropertiesConfig;
import com.cn.common.util.StringUtil;
import com.cn.common.util.WarehouseUtil;
import com.cn.dsyg.dao.CuPriceDao;
import com.cn.dsyg.dao.Dict01Dao;
import com.cn.dsyg.dao.ProductBarcodeDao;
import com.cn.dsyg.dao.ProductDao;
import com.cn.dsyg.dao.ProductSZADao;
import com.cn.dsyg.dao.PurchaseDao;
import com.cn.dsyg.dao.PurchaseItemDao;
import com.cn.dsyg.dao.SalesDao;
import com.cn.dsyg.dao.SalesItemDao;
import com.cn.dsyg.dao.WarehouseDao;
import com.cn.dsyg.dto.CuPriceDto;
import com.cn.dsyg.dto.Dict01Dto;
import com.cn.dsyg.dto.ProductBarcodeDto;
import com.cn.dsyg.dto.ProductDto;
import com.cn.dsyg.dto.PurchaseDto;
import com.cn.dsyg.dto.PurchaseItemDto;
import com.cn.dsyg.dto.SalesDto;
import com.cn.dsyg.dto.SalesItemDto;
import com.cn.dsyg.dto.WarehouseDto;
import com.cn.dsyg.service.ProductService;

/**
 * @name ProductServiceImpl.java
 * @author Frank
 * @time 2015-5-21下午10:58:31
 * @version 1.0
 */
public class ProductServiceImpl implements ProductService {
	
	private ProductDao productDao;
	private ProductSZADao productSZADao;
	private Dict01Dao dict01Dao;
	private ProductBarcodeDao productBarcodeDao;
	private SalesDao salesDao;
	private SalesItemDao salesItemDao;
	private PurchaseDao purchaseDao;
	private PurchaseItemDao purchaseItemDao;
	private CuPriceDao cuPriceDao;
	private WarehouseDao warehouseDao;
	
	@Override
	public ProductDto queryProductByLogicId(String tradename, String typeno,
			String color, String item10, String packaging, String makearea ) {
		ProductDto product = productDao.queryProductByLogicId(tradename, typeno, color, item10, packaging, makearea );
		if(product != null) {
			//图片和PDF文件显示地址
			String imageurl = PropertiesConfig.getPropertiesValueByKey(Constants.PROPERTIES_IMAGES_URL);
			String pdfurl = PropertiesConfig.getPropertiesValueByKey(Constants.PROPERTIES_PDF_URL);
			product.setImageurl(imageurl);
			product.setPdfurl(pdfurl);
			return product;
		}
		return null;
	}

	@Override
	public Page queryProductByPage(String flag, String fieldno, String item01, String keyword, String packaging, String tradename,
				String typeno, String color, String supplierId, String status, String customerid, Page page) {
		keyword = StringUtil.replaceDatabaseKeyword_mysql(keyword);
		tradename = StringUtil.replaceDatabaseKeyword_mysql(tradename);
		typeno = StringUtil.replaceDatabaseKeyword_mysql(typeno);
		item01 = StringUtil.replaceDatabaseKeyword_mysql(item01);
		
		//查询总记录数
		int totalCount = productDao.queryProductCountByPage(fieldno, item01, keyword, packaging, tradename, typeno, color, supplierId, status);
		page.setTotalCount(totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		//翻页查询记录
		List<ProductDto> list = productDao.queryProductByPage(fieldno, item01, keyword, packaging, tradename, typeno, color, supplierId, status,
				page.getStartIndex() * page.getPageSize(), page.getPageSize());
		if(list != null && list.size() > 0) {
			WarehouseDto warehouseDto = null;
			
			BigDecimal rate = new BigDecimal(1);
			//税率
			List<Dict01Dto> listRate = dict01Dao.queryDict01ByFieldcode(Constants.DICT_RATE, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
			if(listRate != null && listRate.size() > 0) {
				rate = rate.add(new BigDecimal(listRate.get(0).getCode()));
			}
			
			//查询条形码码序列号信息
			for(ProductDto product : list) {
				if (product != null){
					ProductBarcodeDto productBarcode = productBarcodeDao.queryProductBarcodeByProductID("" + product.getId());
					if(productBarcode != null) {
						product.setBarcodeseq(productBarcode.getBarcodeseq() + 1);
					} else {
						//没有记录则默认1
						product.setBarcodeseq(1);
					}
					//查询单价
					if("2".equals(flag)) {
						//销售单铜价
						SalesItemDto salesItemDto = getSalesCuPriceByProduct("" + product.getId(), customerid, product.getFieldno());
						if(salesItemDto != null) {
							SalesDto salesDto = salesDao.querySalesByNo(salesItemDto.getSalesno());
							if (salesDto.getRank()!=null && !salesDto.getRank().equals("") && salesDto.getRank()>=85){
								product.setCuprice(salesItemDto.getUnitprice());
								product.setTaxcuprice(salesItemDto.getTaxunitprice());							
							}
						}
					} else if("1".equals(flag)) {
						//采购单铜价
						PurchaseItemDto purchaseItemDto = getPurchaseCuPriceByProduct("" + product.getId(), customerid, product.getFieldno());
						if(purchaseItemDto != null) {
//							PurchaseDto purchaseDto = purchaseDao.queryPurchaseByNo(purchaseItemDto.getPurchaseno());
//							if (purchaseDto.getRank()!=null && !purchaseDto.getRank().equals("") && purchaseDto.getRank()>=85){
								product.setCuprice(purchaseItemDto.getUnitprice());
								product.setTaxcuprice(purchaseItemDto.getTaxunitprice());
//							}
						}
					} else {
						//什么都不做
					}
					
					//计算成本单价
					warehouseDto = warehouseDao.queryPrimecostByProductId("" + product.getId());
					BigDecimal primecost = WarehouseUtil.calcPrimecost(warehouseDto, rate);
					if (primecost!= null)
						product.setPrimecost(primecost);
				}
			}
			//productBarcodeDao
		}
		page.setItems(list);
		return page;
	}
	
	/**
	 * 采购单铜价信息
	 * @param productid
	 * @param supplierId
	 * @param fieldno
	 * @return
	 */
	private PurchaseItemDto getPurchaseCuPriceByProduct(String productid, String supplierId, String fieldno) {
		if(StringUtil.isNotBlank(supplierId)) {
			if(Constants.DICT_GOODS_TYPE_CODE_01.equals(fieldno)) {
				//电子线，查询当前最近一次设置的价格区间
				String setdate = DateUtil.dateToShortStr(new Date());
				CuPriceDto cuPriceDto = cuPriceDao.queryLastCuPriceBySetDate(setdate);
				if(cuPriceDto != null) {
					ProductDto productDto = productDao.queryProductByID(productid);
					if(productDto != null) {
						PurchaseItemDto purchaseItemDto = purchaseItemDao.queryPurchaseCuPriceByProductInfo(productDto.getFieldno(), productDto.getTradename(),
								productDto.getTypeno(), productDto.getPackaging(), productDto.getUnit(),
								productDto.getMakearea(), cuPriceDto.getCu_price_code(), supplierId);
						return purchaseItemDto;
					}
				}
			} else {
				//非电子线，直接按产品ID查询价格
				PurchaseItemDto purchaseItemDto = purchaseItemDao.queryPurchaseCuPriceByProductID(productid, supplierId);
				return purchaseItemDto;
			}
		}
		return null;
	}

	/**
	 * 销售单铜价信息
	 * @param productid
	 * @param customerid
	 * @param fieldno
	 * @return
	 */
	private SalesItemDto getSalesCuPriceByProduct(String productid, String customerid, String fieldno) {
		if(StringUtil.isNotBlank(customerid)) {
			if(Constants.DICT_GOODS_TYPE_CODE_01.equals(fieldno)) {
				//电子线，查询当前最近一次设置的价格区间
				String setdate = DateUtil.dateToShortStr(new Date());
				CuPriceDto cuPriceDto = cuPriceDao.queryLastCuPriceBySetDate(setdate);
				if(cuPriceDto != null) {
					ProductDto productDto = productDao.queryProductByID(productid);
					if(productDto != null) {
						SalesItemDto salesItemDto = salesItemDao.queryCuPriceByProductInfo(productDto.getFieldno(), productDto.getTradename(),
								productDto.getTypeno(), productDto.getPackaging(), productDto.getUnit(),
								productDto.getMakearea(), cuPriceDto.getCu_price_code(), customerid);
						return salesItemDto;
					}
				}
			} else {
				//非电子线，直接按产品ID查询价格
				SalesItemDto salesItemDto = salesItemDao.queryCuPriceByProductID(productid, customerid);
				return salesItemDto;
			}
		}
		return null;
	}
	
	@Override
	public Page queryProductCostCheckByPage(String fieldno, String item01, String keyword, String tradename,
				String typeno, String color, String supplierId, String belongto, String status, Page page) {
		keyword = StringUtil.replaceDatabaseKeyword_mysql(keyword);
		tradename = StringUtil.replaceDatabaseKeyword_mysql(tradename);
		typeno = StringUtil.replaceDatabaseKeyword_mysql(typeno);
		item01 = StringUtil.replaceDatabaseKeyword_mysql(item01);
		
		//查询总记录数
		int totalCount = productDao.queryProductCostCountByPage(fieldno, item01, keyword, tradename, typeno, color, supplierId, belongto, status);
		page.setTotalCount(totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		//翻页查询记录
		List<ProductDto> list = productDao.queryProductCostCheckByPage(fieldno, item01, keyword, tradename, typeno, color, supplierId, belongto, status,
				page.getStartIndex() * page.getPageSize(), page.getPageSize());
		page.setItems(list);
		return page;
	}
	
	
	@Override
	public ProductDto queryProductByID(String id) {
		ProductDto product = productDao.queryProductByID(id);
		if(product != null) {
			//图片和PDF文件显示地址
			String imageurl = PropertiesConfig.getPropertiesValueByKey(Constants.PROPERTIES_IMAGES_URL);
			String pdfurl = PropertiesConfig.getPropertiesValueByKey(Constants.PROPERTIES_PDF_URL);
			product.setImageurl(imageurl);
			product.setPdfurl(pdfurl);
			return product;
		}
		return null;
	}

	@Override
	public void insertProduct(ProductDto product) {
		if (StringUtil.isBlank(Integer.toString(product.getRank()))) {
			product.setRank(Constants.ROLE_RANK_OPERATOR);
		}
		product.setBelongto(PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_BELONG));
		product.setStatus(Constants.STATUS_NORMAL);
		
		//keyword
		product.setKeyword(getKeyword(product));
		
		//productname
		Dict01Dto dict = null;
		String color = "";
		//颜色
		if(StringUtil.isNotBlank(product.getColor())) {
			dict = dict01Dao.queryDict01ByLogicId(Constants.DICT_COLOR_TYPE,
					product.getColor(), PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
			if(dict != null) {
				color = dict.getFieldname();
			}
		}
		//包装
		String packaging = "";
		if(StringUtil.isNotBlank(product.getPackaging())) {
			if(Constants.DICT_PACKAGING_TYPE_CODE_1.equals(product.getPackaging())) {
				packaging = "乱尺";
			} else if(Constants.DICT_PACKAGING_TYPE_CODE_0.equals(product.getPackaging())) {
				packaging = "整箱";
			}
		}
		//类型
		String type = "";
		Dict01Dto dictType = dict01Dao.queryDict01ByLogicId(Constants.DICT_GOODS_TYPE, product.getFieldno(), PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
		if(dictType != null) {
			type = dictType.getFieldname();
		}
		//productname=品牌-类型-品名-规格-颜色-包装-是否样品
		String productname = product.getBrand() + "-" + type + "-" + product.getTradename() + "-" + product.getTypeno() + "-" + color + "-" + packaging;
		product.setProductname(productname);
		
		productDao.insertProduct(product);
		productSZADao.insertProduct(product);
	}
	
	@Override
	public void updateProduct(ProductDto product) {
		//keyword
		product.setKeyword(getKeyword(product));
		
		//productname
		Dict01Dto dict = null;
		String color = "";
		//颜色
		if(StringUtil.isNotBlank(product.getColor())) {
			dict = dict01Dao.queryDict01ByLogicId(Constants.DICT_COLOR_TYPE,
					product.getColor(), PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
			if(dict != null) {
				color = dict.getFieldname();
			}
		}
		//包装
		String packaging = "";
		if(StringUtil.isNotBlank(product.getPackaging())) {
			if(Constants.DICT_PACKAGING_TYPE_CODE_1.equals(product.getPackaging())) {
				packaging = "乱尺";
			} else if(Constants.DICT_PACKAGING_TYPE_CODE_0.equals(product.getPackaging())) {
				packaging = "整箱";
			}
		}
		//类型
		String type = "";
		Dict01Dto dictType = dict01Dao.queryDict01ByLogicId(Constants.DICT_GOODS_TYPE, product.getFieldno(), PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
		if(dictType != null) {
			type = dictType.getFieldname();
		}
		//productname=品牌-类型-品名-规格-颜色-包装-是否样品		
		String productname = product.getBrand() + "-" + type + "-" + product.getTradename() + "-" + product.getTypeno() + "-" + color + "-" + packaging;
		product.setProductname(productname);
		productDao.updateProduct(product);
		productSZADao.updateProduct(product);
	}
	
	/**
	 * 关键字
	 * @param product
	 * @return
	 */
	private String getKeyword(ProductDto product) {
		String keyword = "";
		if(product == null) {
			return keyword;
		}
		//类型
		Dict01Dto dict = dict01Dao.queryDict01ByLogicId(Constants.DICT_GOODS_TYPE, product.getFieldno(), PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
		if(dict != null) {
			keyword += dict.getFieldname() + "," + dict.getNote() + ",";
		}
		//品牌
		if(StringUtil.isNotBlank(product.getBrand())) {
			keyword += product.getBrand()+ ",";
		}
		//类型1
		if(StringUtil.isNotBlank(product.getItem1())) {
			keyword += product.getItem1() + ",";
		}
		//品名
		if(StringUtil.isNotBlank(product.getTradename())) {
			keyword += product.getTradename() + ",";
		}
		//规格
		if(StringUtil.isNotBlank(product.getTypeno())) {
			keyword += product.getTypeno() + ",";
		}
		//颜色
		if(StringUtil.isNotBlank(product.getColor())) {
			dict = dict01Dao.queryDict01ByLogicId(Constants.DICT_COLOR_TYPE,
					product.getColor(), PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
			if(dict != null) {
				keyword += dict.getFieldname() + ",";
			}
		}
		//形式
		if(StringUtil.isNotBlank(product.getPackaging())) {
			if(Constants.DICT_PACKAGING_TYPE_CODE_1.equals(product.getPackaging())) {
				keyword += "乱尺,";
			} else if(Constants.DICT_PACKAGING_TYPE_CODE_0.equals(product.getPackaging())) {
				keyword += "整箱,";
			}
		}
		//包装
		if(StringUtil.isNotBlank(product.getItem10())) {
			keyword += product.getItem10() + ",";
		}
		//产地
		if(StringUtil.isNotBlank(product.getMakearea())) {
			dict = dict01Dao.queryDict01ByLogicId(Constants.DICT_MAKEAREA,
					product.getMakearea(), PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
			if(dict != null) {
				keyword += dict.getFieldname() + ",";
			}
		}
		//UL型号/编号
		if(StringUtil.isNotBlank(product.getItem09())) {
			keyword += product.getItem09() + ",";
		}
		return keyword;
	}
	
	@Override
	public void deleteProduct(String productId, String userid) {
		ProductDto product = productDao.queryProductByID(productId);
		if(product != null) {
			//逻辑删除
			product.setStatus(Constants.STATUS_DEL);
			product.setUpdateuid(userid);
			productDao.updateProduct(product);
		}
		product = productSZADao.queryProductByID(productId);
		if(product != null) {
			//逻辑删除
			product.setStatus(Constants.STATUS_DEL);
			product.setUpdateuid(userid);
			productSZADao.updateProduct(product);
		}
	}

	public Dict01Dao getDict01Dao() {
		return dict01Dao;
	}

	public void setDict01Dao(Dict01Dao dict01Dao) {
		this.dict01Dao = dict01Dao;
	}

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	@Override
	public List<ProductDto> queryProductCostToExport(String fieldno,
			String item01, String keyword, String tradename, String typeno,
			String color, String supplierId, String belongto, String status) {
		keyword = StringUtil.replaceDatabaseKeyword_mysql(keyword);
		tradename = StringUtil.replaceDatabaseKeyword_mysql(tradename);
		typeno = StringUtil.replaceDatabaseKeyword_mysql(typeno);
		item01 = StringUtil.replaceDatabaseKeyword_mysql(item01);
		
		//查询记录
		return productDao.queryProductCostToExport(fieldno, item01, keyword, tradename, typeno, color, supplierId, belongto, status);
	}

	public ProductBarcodeDao getProductBarcodeDao() {
		return productBarcodeDao;
	}

	public void setProductBarcodeDao(ProductBarcodeDao productBarcodeDao) {
		this.productBarcodeDao = productBarcodeDao;
	}

	public SalesItemDao getSalesItemDao() {
		return salesItemDao;
	}

	public void setSalesItemDao(SalesItemDao salesItemDao) {
		this.salesItemDao = salesItemDao;
	}

	public CuPriceDao getCuPriceDao() {
		return cuPriceDao;
	}

	public void setCuPriceDao(CuPriceDao cuPriceDao) {
		this.cuPriceDao = cuPriceDao;
	}

	public PurchaseItemDao getPurchaseItemDao() {
		return purchaseItemDao;
	}

	public void setPurchaseItemDao(PurchaseItemDao purchaseItemDao) {
		this.purchaseItemDao = purchaseItemDao;
	}

	public WarehouseDao getWarehouseDao() {
		return warehouseDao;
	}

	public void setWarehouseDao(WarehouseDao warehouseDao) {
		this.warehouseDao = warehouseDao;
	}

	public SalesDao getSalesDao() {
		return salesDao;
	}

	public void setSalesDao(SalesDao salesDao) {
		this.salesDao = salesDao;
	}

	public PurchaseDao getPurchaseDao() {
		return purchaseDao;
	}

	public void setPurchaseDao(PurchaseDao purchaseDao) {
		this.purchaseDao = purchaseDao;
	}

	public ProductSZADao getProductSZADao() {
		return productSZADao;
	}

	public void setProductSZADao(ProductSZADao productSZADao) {
		this.productSZADao = productSZADao;
	}

}
