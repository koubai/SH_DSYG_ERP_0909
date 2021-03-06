package com.cn.dsyg.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.cn.common.util.Constants;
import com.cn.common.util.Page;
import com.cn.common.util.PropertiesConfig;
import com.cn.common.util.StringUtil;
import com.cn.dsyg.dao.BarcodeInfoDao;
import com.cn.dsyg.dao.BarcodeLogDao;
import com.cn.dsyg.dao.ProductBarcodeDao;
import com.cn.dsyg.dao.ProductDao;
import com.cn.dsyg.dto.BarcodeInfoDto;
import com.cn.dsyg.dto.BarcodeLogDto;
import com.cn.dsyg.dto.ProductBarcodeDto;
import com.cn.dsyg.dto.ProductDto;
import com.cn.dsyg.service.BarcodeLogService;

public class BarcodeLogServiceImpl implements BarcodeLogService {
	
	private BarcodeInfoDao barcodeInfoDao;
	private ProductDao productDao;
	private BarcodeLogDao barcodeLogDao;
	private ProductBarcodeDao productBarcodeDao;
	
	@Override
	public List<BarcodeLogDto> createBarcodeBatch(String productids, String barcodeSeq, String barcodeQuantity,
			String productItem14, String userid) {
		List<BarcodeLogDto> list = new ArrayList<BarcodeLogDto>();
		String initLoadFlg = PropertiesConfig.getPropertiesValueByKey("initLoadFlg");

		if(StringUtil.isNotBlank(productids)) {
			String belongto = PropertiesConfig.getPropertiesValueByKey("belongto");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String productidList[] = productids.split(",");
			String barcodeSeqList[] = barcodeSeq.split(",");
			String barcodeQuantityList[] = barcodeQuantity.split(",");
			String productItem14List[] = productItem14.split(",");
			Date date = null;
			for(int i = 0; i < productidList.length; i++) {
				String productid = productidList[i];
				int seq = Integer.valueOf(barcodeSeqList[i]);
				int quantity = Integer.valueOf(barcodeQuantityList[i]);
				int item14 = Integer.valueOf(productItem14List[i]);
				//根据产品ID查询产品数据
				ProductDto product = productDao.queryProductByID(productid);
				if(product != null) {
					//条形码信息记录
					BarcodeLogDto barcodeLog = new BarcodeLogDto();
					barcodeLog.setBelongto(belongto);
					
					//批号，自动生成
					String uuid = UUID.randomUUID().toString();
					uuid = uuid.substring(uuid.length() - 7, uuid.length());
					date = new Date();
					String batchno = Constants.BARCODE_NO_PRE + belongto + sdf.format(date) + uuid;
					barcodeLog.setBatchno(batchno);
					
					barcodeLog.setProductid(productid);
					barcodeLog.setBarcodenostart(seq);
					barcodeLog.setQuantity(quantity);
					//类型，1为采购单，2为订单
					//barcodeLog.setBarcodetype();
					
					//barcodeLog.setNote("");
					barcodeLog.setStatus(Constants.STATUS_NORMAL);
					barcodeLog.setCreateuid(userid);
					barcodeLog.setUpdateuid(userid);
					barcodeLogDao.insertBarcodeLog(barcodeLog);
					
					//该产品序列号信息
					ProductBarcodeDto productBarcode = productBarcodeDao.queryProductBarcodeByProductID(productid);
					int maxSeq = seq + quantity;
					if(productBarcode != null) {
						if(productBarcode.getBarcodeseq() != null && productBarcode.getBarcodeseq() > seq + quantity) {
							maxSeq = productBarcode.getBarcodeseq();
						} else {
							//更新最大序列号
							productBarcode.setBarcodeseq(seq + quantity - 1);
							productBarcode.setProductqty(item14);
							productBarcode.setUpdateuid(userid);
							productBarcodeDao.updateProductBarcode(productBarcode);
						}
					} else {
						//新增
						productBarcode = new ProductBarcodeDto();
						productBarcode.setBelongto(belongto);
						productBarcode.setProductid(productid);
						productBarcode.setProductqty(item14);
						//默认取最大值
						productBarcode.setBarcodeseq(maxSeq - 1);
						productBarcode.setStatus(Constants.STATUS_NORMAL);
						productBarcode.setCreateuid(userid);
						productBarcode.setUpdateuid(userid);
						productBarcodeDao.insertProductBarcode(productBarcode);
						
					}					
					//返回该产品最大序列号（显示用）
					barcodeLog.setBarcodeseq(maxSeq);
					//对应的条形码列表
					//barcodeLog.setBarcodeLogList(barcodeInfoList);
					
					list.add(barcodeLog);
					
					//当期初导入时,如果定义文件里initLoadFlg为1,则生成的条码直接入库
					
					for (int j=seq; j<maxSeq; j++){
						//Set barcodeInfoDto
						String tmpbarcode = StringUtil.leftPadZero(productid,5)+"-"+ StringUtil.leftPadZero(belongto.toString(),3)+"-" + StringUtil.leftPadZero(Integer.toString(j),13);
						
						BarcodeInfoDto barcodeInfoDto = new BarcodeInfoDto();
						barcodeInfoDto.setBarcode(tmpbarcode);
						barcodeInfoDto.setBarcodeno(StringUtil.leftPadZero(Integer.toString(j),13));
						barcodeInfoDto.setBarcodetype(1);
						barcodeInfoDto.setBatchno(batchno);
						barcodeInfoDto.setBelongto(belongto);
						barcodeInfoDto.setCreateuid(userid);
						barcodeInfoDto.setUpdateuid(userid);
						barcodeInfoDto.setProductid(productid);
						if ("1".equals(initLoadFlg)){
							barcodeInfoDto.setOperatetype(Constants.BARCODE_LOG_OPERATE_TYPE_IN);
						} else {
							barcodeInfoDto.setOperatetype(Constants.BARCODE_LOG_OPERATE_TYPE_NEW);
						}
						barcodeInfoDto.setQuantity(item14);
						barcodeInfoDto.setNote(tmpbarcode +",0");
						barcodeInfoDto.setScannoin("0");
						barcodeInfoDto.setStatus(Constants.STATUS_NORMAL);
						
						BarcodeInfoDto tmpbarcodeInfoDto = barcodeInfoDao.queryBarcodeInfoByLogicId(tmpbarcode);
						if (tmpbarcodeInfoDto != null) {
							if(tmpbarcodeInfoDto.getOperatetype() > Constants.BARCODE_LOG_OPERATE_TYPE_IN) {
								
							}
							// barcorde 存在, update
							//barcodeInfoDao.updateBarcodeInfo(barcodeInfoDto);
						} else {
							// barcorde 不存在, insert
							barcodeInfoDao.insertBarcodeInfo(barcodeInfoDto);								
						}
					}
				}
			}
		}
		return list;
	}

	@Override
	public Page queryBarcodeLogByPage(String productid, String batchno, String barcodetype, String createdateHigh,
			String createdateLow, Page page) {
		//查询总记录数
		int totalCount = barcodeLogDao.queryBarcodeLogCountByPage(productid, batchno, barcodetype, createdateHigh, createdateLow);
		page.setTotalCount(totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		//翻页查询记录
		List<BarcodeLogDto> list = barcodeLogDao.queryBarcodeLogByPage(productid, batchno, barcodetype, createdateHigh, createdateLow,
				page.getStartIndex() * page.getPageSize(), page.getPageSize());
		page.setItems(list);
		return page;
	}

	@Override
	public List<BarcodeLogDto> queryAllBarcodeLogList(String productid, String batchno, String barcodetype) {
		return barcodeLogDao.queryAllBarcodeLogList(productid, batchno, barcodetype);
	}

	@Override
	public BarcodeLogDto queryBarcodeLogByID(String id) {
		return barcodeLogDao.queryBarcodeLogByID(id);
	}

	@Override
	public BarcodeLogDto queryBarcodeLogByLogicID(String batchno) {
		return barcodeLogDao.queryBarcodeLogByLogicID(batchno);
	}

	@Override
	public void insertBarcodeLog(BarcodeLogDto BarcodeLog) {
		barcodeLogDao.insertBarcodeLog(BarcodeLog);
	}

	@Override
	public void updateBarcodeLog(BarcodeLogDto BarcodeLog) {
		barcodeLogDao.updateBarcodeLog(BarcodeLog);
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

	public BarcodeLogDao getBarcodeLogDao() {
		return barcodeLogDao;
	}

	public void setBarcodeLogDao(BarcodeLogDao barcodeLogDao) {
		this.barcodeLogDao = barcodeLogDao;
	}

	public ProductBarcodeDao getProductBarcodeDao() {
		return productBarcodeDao;
	}

	public void setProductBarcodeDao(ProductBarcodeDao productBarcodeDao) {
		this.productBarcodeDao = productBarcodeDao;
	}
}
