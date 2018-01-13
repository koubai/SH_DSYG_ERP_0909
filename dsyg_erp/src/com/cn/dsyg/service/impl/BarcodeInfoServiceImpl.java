package com.cn.dsyg.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.cn.common.util.Constants;
import com.cn.common.util.Page;
import com.cn.common.util.PropertiesConfig;
import com.cn.common.util.StringUtil;
import com.cn.dsyg.dao.BarcodeInfoDao;
import com.cn.dsyg.dao.ProductDao;
import com.cn.dsyg.dto.BarcodeInfoDto;
import com.cn.dsyg.dto.ProductDto;
import com.cn.dsyg.service.BarcodeInfoService;

public class BarcodeInfoServiceImpl implements BarcodeInfoService {
	
	private BarcodeInfoDao barcodeInfoDao;
	private ProductDao productDao;
	
	@Override
	public List<BarcodeInfoDto> barcodeInfoInBatch(String[] barcodeList, String userid) {
		List<BarcodeInfoDto> barcodeInfoList = new ArrayList<BarcodeInfoDto>();
		//生成条形码
		for(int i = 0; i < barcodeList.length; i++) {
			if(StringUtil.isNotBlank(barcodeList[i])) {
				String productid = "";
				String barcodeno = "";
				String areano = "";
				String barcode = "";
				String scanno = "";
				
				if(barcodeList[i].indexOf(",") >= 0) {
					String[] ll = barcodeList[i].split(",");
					barcode = ll[0];
					String[] info = barcode.split("-");
					productid = info[0];
					areano = info[1];
					barcodeno = info[2];
					scanno = ll[1];
				} else {
					//没有扫码枪ID
					barcode = barcodeList[i];
					String[] info = barcode.split("-");
					productid = info[0];
					areano = info[1];
					barcodeno = info[2];
				}
				BarcodeInfoDto barcodeInfo = barcodeInfoInBatchSub(barcode, productid, userid, areano, barcodeno, scanno, barcodeList[i]);
				if (barcodeInfo!= null)
					barcodeInfoList.add(barcodeInfo);
			}
		}
		return barcodeInfoList;
	}

	public BarcodeInfoDto barcodeInfoInBatchSub(String barcode, String productid, String userid, String areano, String barcodeno, String scanno, String barcodenote) {
		//判断产品是否存在
		Integer i_productid = Integer.valueOf(productid);
		ProductDto product = productDao.queryProductByID(i_productid.toString());
		BarcodeInfoDto retDto = null;
		
		if (product != null){					
			//判断barcode是否存在
			BarcodeInfoDto oldBarcodeInfo = barcodeInfoDao.queryBarcodeInfoByLogicId(barcode);
			if(oldBarcodeInfo != null) {
				//更新原来的记录
				//扫码入库
				oldBarcodeInfo.setScanno(scanno);
				oldBarcodeInfo.setNote(barcodenote);
				oldBarcodeInfo.setOperatetype(Constants.BARCODE_LOG_OPERATE_TYPE_IN);
				oldBarcodeInfo.setUpdateuid(userid);
				barcodeInfoDao.updateBarcodeInfo(oldBarcodeInfo);
				return oldBarcodeInfo;
			} else {
				//新增
				BarcodeInfoDto barcodeInfo = new BarcodeInfoDto();
				barcodeInfo.setBelongto(areano);
				barcodeInfo.setProductid("" + Integer.valueOf(productid));
				barcodeInfo.setBarcode(barcode);
				barcodeInfo.setBarcodeno(barcodeno);
				
				barcodeInfo.setScanno(scanno);
				barcodeInfo.setBarcodetype(1);
				barcodeInfo.setQuantity(Integer.valueOf(product.getItem14()));
				barcodeInfo.setNote(barcodenote);
				//扫码入库
				barcodeInfo.setOperatetype(Constants.BARCODE_LOG_OPERATE_TYPE_IN);
				barcodeInfo.setStatus(Constants.STATUS_NORMAL);
				barcodeInfo.setCreateuid(userid);
				barcodeInfo.setUpdateuid(userid);
				barcodeInfoDao.insertBarcodeInfo(barcodeInfo);
				retDto = barcodeInfo;
			}
		}
		return retDto;
	}
	
	
	@Override
	public Page queryBarcodeInfoByPage(String productid, String batchno, String barcode, String barcodetype,
			String operatetype, Page page) {
		//查询总记录数
		int totalCount = barcodeInfoDao.queryBarcodeInfoCountByPage(productid, batchno, barcode, barcodetype, operatetype);
		page.setTotalCount(totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		//翻页查询记录
		List<BarcodeInfoDto> list = barcodeInfoDao.queryBarcodeInfoByPage(productid, batchno, barcode, barcodetype, operatetype,
				page.getStartIndex() * page.getPageSize(), page.getPageSize());
		page.setItems(list);
		return page;
	}

	@Override
	public List<BarcodeInfoDto> queryAllBarcodeInfoList(String productid, String batchno, String barcodetype) {
		return barcodeInfoDao.queryAllBarcodeInfoList(productid, batchno, barcodetype);
	}

	@Override
	public BarcodeInfoDto queryBarcodeInfoByID(String id) {
		return barcodeInfoDao.queryBarcodeInfoByID(id);
	}
	
	@Override
	public BarcodeInfoDto queryBarcodeInfoByLogicId(String barcode) {
		return barcodeInfoDao.queryBarcodeInfoByLogicId(barcode);
	}

	@Override
	public void insertBarcodeInfo(BarcodeInfoDto BarcodeInfo) {
		barcodeInfoDao.insertBarcodeInfo(BarcodeInfo);
	}

	@Override
	public void updateBarcodeInfo(BarcodeInfoDto BarcodeInfo) {
		barcodeInfoDao.updateBarcodeInfo(BarcodeInfo);
	}

	public BarcodeInfoDao getBarcodeInfoDao() {
		return barcodeInfoDao;
	}

	public void setBarcodeInfoDao(BarcodeInfoDao barcodeInfoDao) {
		this.barcodeInfoDao = barcodeInfoDao;
	}
	
	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
}
