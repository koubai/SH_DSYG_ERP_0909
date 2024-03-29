package com.cn.dsyg.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.cn.common.util.Constants;
import com.cn.common.util.DateUtil;
import com.cn.common.util.Page;
import com.cn.common.util.PropertiesConfig;
import com.cn.common.util.StringUtil;
import com.cn.common.util.WarehouseUtil;
import com.cn.dsyg.dao.BarcodeInfoDao;
import com.cn.dsyg.dao.CustomerDao;
import com.cn.dsyg.dao.CustomerOnlineDao;
import com.cn.dsyg.dao.Dict01Dao;
import com.cn.dsyg.dao.FinanceDao;
import com.cn.dsyg.dao.OrderDao;
import com.cn.dsyg.dao.PositionDao;
import com.cn.dsyg.dao.ProductBarcodeDao;
import com.cn.dsyg.dao.ProductDao;
import com.cn.dsyg.dao.PurchaseDao;
import com.cn.dsyg.dao.PurchaseItemDao;
import com.cn.dsyg.dao.SalesDao;
import com.cn.dsyg.dao.SalesHisDao;
import com.cn.dsyg.dao.SalesItemDao;
import com.cn.dsyg.dao.SalesItemHisDao;
import com.cn.dsyg.dao.SupplierDao;
import com.cn.dsyg.dao.UserDao;
import com.cn.dsyg.dao.WarehouseDao;
import com.cn.dsyg.dao.WarehouseSZADao;
import com.cn.dsyg.dao.WarehouseSZDao;
import com.cn.dsyg.dao.WarehouserptDao;
import com.cn.dsyg.dao.WarehouserptSZADao;
import com.cn.dsyg.dto.AjaxResultDto;
import com.cn.dsyg.dto.BarcodeInfoDto;
import com.cn.dsyg.dto.CustomerDto;
import com.cn.dsyg.dto.CustomerOnlineDto;
import com.cn.dsyg.dto.Dict01Dto;
import com.cn.dsyg.dto.FinanceDto;
import com.cn.dsyg.dto.InOutStockDto;
import com.cn.dsyg.dto.OrderDto;
import com.cn.dsyg.dto.PositionDto;
import com.cn.dsyg.dto.ProductDto;
import com.cn.dsyg.dto.ProductQuantityDto;
import com.cn.dsyg.dto.PurchaseDto;
import com.cn.dsyg.dto.PurchaseItemDto;
import com.cn.dsyg.dto.SalesDto;
import com.cn.dsyg.dto.SalesItemDto;
import com.cn.dsyg.dto.SalesStatisticsDto;
import com.cn.dsyg.dto.SupplierDto;
import com.cn.dsyg.dto.UserDto;
import com.cn.dsyg.dto.WarehouseCheckDto;
import com.cn.dsyg.dto.WarehouseDetailDto;
import com.cn.dsyg.dto.WarehouseDto;
import com.cn.dsyg.dto.WarehouseInOutOkDto;
import com.cn.dsyg.dto.WarehouseOkDto;
import com.cn.dsyg.dto.WarehouseProductDto;
import com.cn.dsyg.dto.WarehouserptDto;
import com.cn.dsyg.service.ProductService;
import com.cn.dsyg.service.WarehouseService;

/**
 * @name WarehouseServiceImpl.java
 * @author Frank
 * @time 2015-6-7下午9:05:51
 * @version 1.0
 */
public class WarehouseServiceImpl implements WarehouseService {
	
	private PurchaseDao purchaseDao;
	private PurchaseItemDao purchaseItemDao;
	private SalesDao salesDao;
	private SalesItemDao salesItemDao;
	private SalesHisDao salesHisDao;
	private SalesItemHisDao salesItemHisDao;
	private static WarehouseDao warehouseDao;
	private WarehouserptDao warehouserptDao;
	private WarehouseSZADao warehouseSZADao;
	private WarehouserptSZADao warehouserptSZADao;
	private SupplierDao supplierDao;
	private CustomerDao customerDao;
	private CustomerOnlineDao customerOnlineDao;
	private FinanceDao financeDao;
	private ProductDao productDao;
	private PositionDao positionDao;
	private UserDao userDao;
	private Dict01Dao dict01Dao;
	private OrderDao orderDao;
	private BarcodeInfoDao barcodeInfoDao;
	private ProductBarcodeDao productBarcodeDao;
	private ProductService productService;
	private BigDecimal totalQty;
	
	//SZ数据
	private WarehouseSZDao warehouseSZDao;
	
	@Override
	public WarehouseDto queryPrimecostByProductId(String productId) {
		return warehouseDao.queryPrimecostByProductId(productId);
	}
	
	@Override
	public AjaxResultDto barcodeWarehouseInOutCheck(String rptId, String scanBarcodeInfo, Integer type, String userid, boolean checktype) {
		AjaxResultDto ajaxResult = new AjaxResultDto();
		//验证条形码是否为空
		if(!StringUtil.isNotBlank(scanBarcodeInfo)) {
			ajaxResult.setCode(2);
			ajaxResult.setMsg("条形码为空！");
		} else {
			Map<String, BigDecimal> productQtyMap = new HashMap<String, BigDecimal>();
			//验证条形码是否存在
			String[] barcodeList = scanBarcodeInfo.split(Constants.BARCODE_SPLIT);
			//产品对应的条形码最大编号MAP
			Map<String, String> productBarcodeMap = new HashMap<String, String>();
			String errormsg = "";
			for(int i = 0; i < barcodeList.length; i++) {
				if(StringUtil.isNotBlank(barcodeList[i])) {
					String barcode = "";
					if(barcodeList[i].indexOf(",") >= 0) {
						String[] ll = barcodeList[i].split(",");
						barcode = ll[0];
					} else {
						//没有扫码枪ID
						barcode = barcodeList[i];
					}
					
					BarcodeInfoDto barcodeInfo = barcodeInfoDao.queryBarcodeInfoByLogicId(barcode);
					if(barcodeInfo == null) {
						errormsg += "条形码" + barcode + "不存在！\n";
					} else {
						//查询产品信息
						ProductDto product = productService.queryProductByID(barcodeInfo.getProductid());
						if(product != null) {
							//产品对应的条形码最大编号MAP
							if(productBarcodeMap.containsKey(barcodeInfo.getProductid())) {
								if(Integer.valueOf(productBarcodeMap.get(barcodeInfo.getProductid()).split("-")[2]) < Integer.valueOf(barcodeInfo.getBarcodeno())) {
									productBarcodeMap.put(barcodeInfo.getProductid(), barcodeInfo.getBarcode());
								}
							} else {
								productBarcodeMap.put(barcodeInfo.getProductid(), barcodeInfo.getBarcode());
							}
							
							if(type == Constants.WAREHOUSERPT_TYPE_IN) {
								//验证状态是否是扫码入库状态
								if(barcodeInfo.getOperatetype() >= Constants.BARCODE_LOG_OPERATE_TYPE_IN) {
									errormsg += "条形码" + barcode + "已入库！\n";
								} else {
									if(productQtyMap.containsKey(barcodeInfo.getProductid() + "###" + product.getTradename())) {
										BigDecimal qty = productQtyMap.get(barcodeInfo.getProductid() + "###" + product.getTradename()).add(new BigDecimal(barcodeInfo.getQuantity()));
										productQtyMap.put(barcodeInfo.getProductid() + "###" + product.getTradename(), qty);
									} else {
										if(barcodeInfo.getQuantity() != null) {
											productQtyMap.put(barcodeInfo.getProductid() + "###" + product.getTradename(), new BigDecimal(barcodeInfo.getQuantity()));
										}
									}
								}
							} else if(type == Constants.WAREHOUSERPT_TYPE_OUT) {
								//验证状态是否是扫码入库状态
								if(barcodeInfo.getOperatetype() != Constants.BARCODE_LOG_OPERATE_TYPE_IN) {
									errormsg += "条形码" + barcode + "未入库或已出库！\n";
								} else {
									if(productQtyMap.containsKey(barcodeInfo.getProductid() + "###" + product.getTradename())) {
										BigDecimal qty = productQtyMap.get(barcodeInfo.getProductid() + "###" + product.getTradename()).add(new BigDecimal(barcodeInfo.getQuantity()));
										productQtyMap.put(barcodeInfo.getProductid() + "###" + product.getTradename(), qty);
									} else {
										if(barcodeInfo.getQuantity() != null) {
											productQtyMap.put(barcodeInfo.getProductid() + "###" + product.getTradename(), new BigDecimal(barcodeInfo.getQuantity()));
										}
									}
								}
							}
						} else {
							errormsg += "条形码" + barcode + "对应的商品未找到\n";
						}
					}
				}
			}
			//商品数量提示
			String allmsg = "";
			if(type == Constants.WAREHOUSERPT_TYPE_IN) {
				allmsg = "当前入库条形码商品信息：\n";
			} else if(type == Constants.WAREHOUSERPT_TYPE_OUT) {
				allmsg = "当前出库条形码商品信息：\n";
			}
			for(Map.Entry<String, BigDecimal> entry : productQtyMap.entrySet()) {
				String key = entry.getKey();
				String[] ll = key.split("###");
				allmsg += ll[1] + "数量" + entry.getValue() + "\n";
			}
			
			String maxcheckmsg = "";
			if(type == Constants.WAREHOUSERPT_TYPE_OUT) {
				//验证是否还有条形码番号低的未出库
				for(Map.Entry<String, String> entry : productBarcodeMap.entrySet()) {
					//根据条形码编号，查询较小编号的条形码列表
					List<BarcodeInfoDto> lessBarcodeList = barcodeInfoDao.queryBarcodeInfoListLessBarcodeno(entry.getKey(),
							"" + Constants.BARCODE_LOG_OPERATE_TYPE_IN, entry.getValue().split("-")[2], "");
					if(lessBarcodeList != null && lessBarcodeList.size() > 0) {
						for(BarcodeInfoDto lessBarcode : lessBarcodeList) {
							if(scanBarcodeInfo.indexOf(lessBarcode.getBarcode()) < 0) {
								maxcheckmsg += "有比" + entry.getValue() + "较小编号的条形码未出库！\n";
								break;
							}
						}
					}
				}
			}
			
			if(StringUtil.isNotBlank(errormsg)) {
				ajaxResult.setCode(1);
				if(!checktype) {
					allmsg = errormsg;
				} else {
					allmsg += "其中错误信息：" + errormsg;
				}
			} else {
				ajaxResult.setCode(0);
			}
			allmsg += maxcheckmsg;
			ajaxResult.setMsg(allmsg);
		}
		return ajaxResult;
	}
	
	@Override
	public AjaxResultDto barcodeWarehouseInOut(String rptId, String scanBarcodeInfo, Integer type, String userid) {
		AjaxResultDto ajaxResult = new AjaxResultDto();
		//验证条形码是否为空
		if(!StringUtil.isNotBlank(scanBarcodeInfo)) {
			ajaxResult.setCode(2);
			ajaxResult.setMsg("条形码为空！");
		} else {
			ajaxResult = barcodeWarehouseInOutCheck(rptId, scanBarcodeInfo, type, userid, false);
			if(ajaxResult.getCode() != 0) {
				return ajaxResult;
			}
			Map<String, BarcodeInfoDto> barcodeInfoMap = new HashMap<String, BarcodeInfoDto>();
			//验证条形码是否存在
			String[] barcodeList = scanBarcodeInfo.split(Constants.BARCODE_SPLIT);
			//产品对应的条形码最大编号MAP
			Map<String, String> productBarcodeMap = new HashMap<String, String>();
			for(int i = 0; i < barcodeList.length; i++) {
				if(StringUtil.isNotBlank(barcodeList[i])) {
					String barcode = "";
					String scanno = "";
					if(barcodeList[i].indexOf(",") >= 0) {
						String[] ll = barcodeList[i].split(",");
						barcode = ll[0];
						scanno = ll[1];
					} else {
						//没有扫码枪ID
						barcode = barcodeList[i];
					}
					
					BarcodeInfoDto barcodeInfo = barcodeInfoDao.queryBarcodeInfoByLogicId(barcode);
					if(barcodeInfo == null) {
						ajaxResult.setCode(3);
						ajaxResult.setMsg("条形码" + barcode + "不存在！");
						return ajaxResult;
					}
					
					//产品对应的条形码最大编号MAP
					if(productBarcodeMap.containsKey(barcodeInfo.getProductid())) {
						if(Integer.valueOf(productBarcodeMap.get(barcodeInfo.getProductid())) < Integer.valueOf(barcodeInfo.getBarcodeno())) {
							productBarcodeMap.put(barcodeInfo.getProductid(), barcodeInfo.getBarcodeno());
						}
					} else {
						productBarcodeMap.put(barcodeInfo.getProductid(), barcodeInfo.getBarcodeno());
					}
					
					if(type == Constants.WAREHOUSERPT_TYPE_IN) {
						//验证状态是否是扫码入库状态
						if(barcodeInfo.getOperatetype() >= Constants.BARCODE_LOG_OPERATE_TYPE_IN) {
							ajaxResult.setCode(4);
							ajaxResult.setMsg("条形码" + barcode + "已入库！");
							return ajaxResult;
						}
					} else if(type == Constants.WAREHOUSERPT_TYPE_OUT) {
						//验证状态是否是扫码入库状态
						if(barcodeInfo.getOperatetype() != Constants.BARCODE_LOG_OPERATE_TYPE_IN) {
							ajaxResult.setCode(4);
							ajaxResult.setMsg("条形码" + barcode + "未入库或已出库！");
							return ajaxResult;
						}
					}
					if(type == Constants.WAREHOUSERPT_TYPE_IN) {
						barcodeInfo.setScannoin(scanno);
					} else if(type == Constants.WAREHOUSERPT_TYPE_OUT) {
						barcodeInfo.setScannoout(scanno);
					}
					
					barcodeInfoMap.put(barcode, barcodeInfo);
				}
			}
			WarehouserptDto rpt = warehouserptDao.queryWarehouserptByID(rptId);
			if(rpt != null) {
				//验证数量
				ajaxResult = checkBarcodeQuantity(rpt, barcodeInfoMap);
				if(ajaxResult.getCode() != 0) {
					return ajaxResult;
				}
				
				//更新rpt
				rpt.setRes02("1");
				rpt.setUpdateuid(userid);
				warehouserptDao.updateWarehouserpt(rpt);
				
				//更新barcodeInfo
				for(Map.Entry<String, BarcodeInfoDto> entry : barcodeInfoMap.entrySet()) {
					BarcodeInfoDto barcodeInfo = entry.getValue();
					if(type == Constants.WAREHOUSERPT_TYPE_IN) {
						//扫码入库
						barcodeInfo.setOperatetype(Constants.BARCODE_LOG_OPERATE_TYPE_IN);
						barcodeInfo.setRptnoin(rpt.getWarehouseno());
					} else {
						//扫码出库
						barcodeInfo.setOperatetype(Constants.BARCODE_LOG_OPERATE_TYPE_OUT);
						barcodeInfo.setRptnoout(rpt.getWarehouseno());
					}
					barcodeInfo.setUpdateuid(userid);
					//类型=入出库类型（有疑问，条形码生成时默认为入库单？）
					//barcodeInfo.setBarcodetype(type);
					barcodeInfoDao.updateBarcodeInfo(barcodeInfo);
				}
				
				ajaxResult.setCode(0);
				if(type == Constants.WAREHOUSERPT_TYPE_IN) {
					ajaxResult.setMsg("入库单" + rpt.getWarehouseno() + "入库成功！");
				} else if(type == Constants.WAREHOUSERPT_TYPE_OUT) {
					//验证是否还有条形码番号低的未出库
					boolean hasLessBarcode = false;
					for(Map.Entry<String, String> entry : productBarcodeMap.entrySet()) {
						//根据条形码编号，查询较小编号的条形码列表
						List<BarcodeInfoDto> lessBarcodeList = barcodeInfoDao.queryBarcodeInfoListLessBarcodeno(entry.getKey(),
								"" + Constants.BARCODE_LOG_OPERATE_TYPE_IN, entry.getValue(), "");
						if(lessBarcodeList != null && lessBarcodeList.size() > 0) {
							hasLessBarcode = true;
							break;
						}
					}
					if(hasLessBarcode) {
						ajaxResult.setMsg("出库单" + rpt.getWarehouseno() + "出库成功！有较小编号的条形码未出库！");
					} else {
						ajaxResult.setMsg("出库单" + rpt.getWarehouseno() + "出库成功！");
					}
				} else {
					ajaxResult.setMsg("succ");
				}
			} else {
				ajaxResult.setCode(1);
				ajaxResult.setMsg("单据数据不存在！");
			}
		}
		return ajaxResult;
	}
	
	/**
	 * 验证rpt产品数量是否和条形码数量完全相等
	 * @param rpt
	 * @param barcodeInfoMap
	 * @return
	 */
	private AjaxResultDto checkBarcodeQuantity(WarehouserptDto rpt, Map<String, BarcodeInfoDto> barcodeInfoMap) {
		AjaxResultDto ajaxResult = new AjaxResultDto();
		String rptQuantity = "";
		String barcodeQuantity = "";
		Map<String, BigDecimal> rptQuantityMap = new HashMap<String, BigDecimal>();
		//Map<String, BigDecimal> barcodeProductMap = new HashMap<String, BigDecimal>();
		Map<String, BigDecimal> barcodeQuantityMap = new HashMap<String, BigDecimal>();
		ProductDto product = null;
		
		//条形码对应的产品数量
		for(Map.Entry<String, BarcodeInfoDto> entry : barcodeInfoMap.entrySet()) {
			BarcodeInfoDto barcodeInfo = entry.getValue();
			if(barcodeQuantityMap.containsKey(barcodeInfo.getProductid())) {
				BigDecimal quantity = barcodeQuantityMap.get(barcodeInfo.getProductid());
				quantity = quantity.add(new BigDecimal(barcodeInfo.getQuantity()));
				barcodeQuantityMap.put(barcodeInfo.getProductid(), quantity);
			} else {
				barcodeQuantityMap.put(barcodeInfo.getProductid(), new BigDecimal(barcodeInfo.getQuantity()));
			}
		}
		
		//入出库单对应的产品数量
		String[] infos = rpt.getProductinfo().split("#");
		for(int i = 0; i < infos.length; i++) {
			String info = infos[i];
			if(StringUtil.isNotBlank(info) && !"null".equalsIgnoreCase(info)) {
				String[] ll = info.split(",");
				if(rptQuantityMap.containsKey(ll[0])) {
					BigDecimal quantity = rptQuantityMap.get(ll[0]);
					quantity = quantity.add(new BigDecimal(ll[1]));
					rptQuantityMap.put(ll[0], quantity);
				} else {
					rptQuantityMap.put(ll[0], new BigDecimal(ll[1]));
				}
			}
		}
		//判断两个字符串内容是否一致
		List<String> rptQuantityList = new ArrayList<String>();
		List<String> barcodeQuantityList = new ArrayList<String>();
		for(Map.Entry<String, BigDecimal> entry : rptQuantityMap.entrySet()) {
			String key = entry.getKey();
			BigDecimal value = entry.getValue().abs().setScale(2, BigDecimal.ROUND_HALF_UP);
			rptQuantityList.add(key + "_" + value);
		}
		for(Map.Entry<String, BigDecimal> entry : barcodeQuantityMap.entrySet()) {
			String key = entry.getKey();
			BigDecimal value = entry.getValue().abs().setScale(2, BigDecimal.ROUND_HALF_UP);
			barcodeQuantityList.add(key + "_" + value);
		}
		Collections.sort(barcodeQuantityList);
		Collections.sort(rptQuantityList);
		for(String s : rptQuantityList) {
			rptQuantity += s + ",";
		}
		for(String s : barcodeQuantityList) {
			barcodeQuantity += s + ",";
		}
//		System.out.println("barcodeQuantity=" + barcodeQuantity);
//		System.out.println("rptQuantity=" + rptQuantity);
		if(rptQuantity.equals(barcodeQuantity)) {
			ajaxResult.setCode(0);
			ajaxResult.setMsg("");
			return ajaxResult;
		}
		ajaxResult.setCode(5);
		ajaxResult.setMsg("扫码产品数量与当前单据产品数量不匹配！");
		return ajaxResult;
	}
	
	@Override
	public List<InOutStockDto> queryInOutStockDetail(String productid, String warehousetype,
			String startdate, String enddate) {
		return warehouseDao.queryInOutStockDetail(productid, warehousetype, startdate, enddate);
	}
	
	@Override
	public InOutStockDto querySumInOutStock(String startdate, String enddate,
			String fieldno, String tradename, String item10, String keyword, String productid) {
		tradename = StringUtil.replaceDatabaseKeyword_mysql(tradename);
		item10 = StringUtil.replaceDatabaseKeyword_mysql(item10);
		keyword = StringUtil.replaceDatabaseKeyword_mysql(keyword);
		return warehouseDao.querySumInOutStock(startdate, enddate, fieldno, tradename, item10, keyword, productid);
	}
	
	@Override
	public Page queryInOutStockByPage(String startdate, String enddate,
			String fieldno, String tradename, String item10, String keyword,
			Page page) {
		tradename = StringUtil.replaceDatabaseKeyword_mysql(tradename);
		item10 = StringUtil.replaceDatabaseKeyword_mysql(item10);
		keyword = StringUtil.replaceDatabaseKeyword_mysql(keyword);
		//查询总记录数
		int totalCount = warehouseDao.queryInOutStockCountByPage(startdate,
				enddate, fieldno, tradename, item10, keyword);
		page.setTotalCount(totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		//翻页查询记录
		List<InOutStockDto> list = warehouseDao.queryInOutStockByPage(startdate,
				enddate, fieldno, tradename, item10, keyword,
				page.getStartIndex() * page.getPageSize(), page.getPageSize());
		page.setItems(list);
		return page;
	}
	
	@Override
	public SalesStatisticsDto querySumSalesStatistics(String startdate, String enddate,
			String fieldno, String tradename, String item10, String keyword, String productid) {
		tradename = StringUtil.replaceDatabaseKeyword_mysql(tradename);
		item10 = StringUtil.replaceDatabaseKeyword_mysql(item10);
		keyword = StringUtil.replaceDatabaseKeyword_mysql(keyword);
        return warehouseDao.querySumSalesStatistics(startdate, enddate, fieldno, tradename, item10, keyword, productid);
	}
	
	@Override
	public Page querySalesStatisticsByPage(String startdate, String enddate,
			String fieldno, String tradename, String item10, String keyword,
			Page page) { 
        
		tradename = StringUtil.replaceDatabaseKeyword_mysql(tradename);
		item10 = StringUtil.replaceDatabaseKeyword_mysql(item10);
		keyword = StringUtil.replaceDatabaseKeyword_mysql(keyword);
		//查询总记录数
		int totalCount = warehouseDao.querySalesStatisticsCountByPage(startdate,
				enddate, fieldno, tradename, item10, keyword);
		page.setTotalCount(totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		//翻页查询记录
		List<SalesStatisticsDto> list = warehouseDao.querySalesStatisticsByPage(startdate,
				enddate, fieldno, tradename, item10, keyword,
				page.getStartIndex() * page.getPageSize(), page.getPageSize());  
		page.setItems(list);
		return page;
	}

	@Override
	public String checkProductAmount(String productInfo) {
		String result = "";
		if(StringUtil.isNotBlank(productInfo)) {
			String[] list = productInfo.split("#");
			ProductDto product = null;
			int index = 1;
			for(String info : list) {
				if(StringUtil.isNotBlank(info)) {
					String ss[] = info.split(",");
					Double dd = warehouseDao.queryAmountByProductId(ss[0]);
					BigDecimal warehouseAmount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal salesAmount = new BigDecimal(ss[1]);
					//只CHECK预出库数量!=0的
					if(salesAmount.compareTo(new BigDecimal(0)) != 0) {
						//数量为空，则默认为0
						if(dd != null) {
							warehouseAmount = new BigDecimal(dd).setScale(2, BigDecimal.ROUND_HALF_UP);
						}
						if(warehouseAmount.compareTo(salesAmount) < 0) {
							product = productDao.queryProductByID(ss[0]);
							//tradename typeno packaging item10 
							//说明库存数量不够
							result += "NO" + index + "【" + StringUtil.getStr(product.getTradename()) + " "
									+ StringUtil.getStr(product.getTypeno()) + " "
									+ StringUtil.getStr(product.getPackaging()) + " "
									+ StringUtil.getStr(product.getItem10()) + "】库存不足" + ss[1] + "，现库存" + warehouseAmount + "，\\n";
						}
					}
					index++;
				}
			}
			if(StringUtil.isNotBlank(result)) {
				result = result.substring(0, result.length() - 3) + "。";
			}
		}
		return result;
	}
	
	@Override
	public boolean checkProductQuantity(String productid, String num, String productposition, String userid) {
		//查询原始库存
		ProductQuantityDto p = warehouseDao.queryProductQuantityById(productid);
		//仓库
		String warehousename = PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_WAREHOUSE_NAME);
		String belongto = PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_BELONG);
		if(p != null) {
			if(p.getQuantity() != null && !"".equals(p.getQuantity())) {
				//有库存数据
				//判断库存数量和输入的数量是否相等
				BigDecimal oldNum = new BigDecimal(p.getQuantity());
				BigDecimal newNum = new BigDecimal(num);
				if(oldNum.compareTo(newNum) != 0) {
					//需要新增库存数据
					BigDecimal addNum = newNum.subtract(oldNum);
					
					ProductDto product = productDao.queryProductByID(productid);
					
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					
//					WarehouseDto warehouse = new WarehouseDto();
//					//数据来源单号=盘点
//					warehouse.setParentid("PD");
//					//库存类型=盘点
//					warehouse.setWarehousetype(Constants.WAREHOUSE_TYPE_PD);
//					//仓库
//					warehouse.setWarehousename(warehousename);
//					//预入库时间
//					warehouse.setPlandate(DateUtil.dateToShortStr(date));
//					
//					//库存单号
//					String uuid = UUID.randomUUID().toString();
//					uuid = uuid.substring(uuid.length() - 8, uuid.length());
//					String warehouseno = Constants.WAREHOUSE_NO_PRE + belongto + sdf.format(date) + uuid;
//					warehouse.setWarehouseno(warehouseno);
//					
//					//支付方式
//					warehouse.setRes01("");
//					
//					warehouse.setBelongto(belongto);
//					//主题
//					warehouse.setTheme1(product.getFieldno());
//					//产品ID
//					warehouse.setProductid("" + productid);
//					//入库数量=预入库数
//					warehouse.setQuantity(addNum);
//					
//					//入库日期=当天
//					warehouse.setWarehousedate(DateUtil.dateToShortStr(new Date()));
//					warehouse.setRank(Constants.ROLE_RANK_OPERATOR);
//					//入库单数据状态=新增
//					warehouse.setStatus(Constants.WAREHOUSE_STATUS_NEW);
//					
//					warehouse.setUpdateuid(userid);
//					warehouse.setCreateuid(userid);
//					
//					warehouseDao.insertWarehouse(warehouse);
				}
				String checkday = DateUtil.dateToShortStr(new Date());
				//查询盘点表的数据
				List<PositionDto> list = positionDao.queryPositionByLogicId("", productid, checkday);
				if(list != null && list.size() > 0) {
					PositionDto position = list.get(0);
					//更新数据
					position.setAmount(newNum);
					position.setBeforeamount(oldNum);
					position.setHandler(userid);
					position.setUpdateuid(userid);
					position.setProductposition(productposition);
					positionDao.updatePosition(position);
				} else {
					//没有位置数据，则新增一条记录
					//新增盘点记录
					PositionDto position = new PositionDto();
					position = new PositionDto();
					position.setAmount(newNum);
					position.setBeforeamount(oldNum);
					position.setBelongto(belongto);
					position.setCreateuid(userid);
					position.setUpdateuid(userid);
					position.setProductid(productid);
					position.setCheckday(checkday);
					position.setProductposition(productposition);
					position.setRank(Constants.ROLE_RANK_OPERATOR);
					position.setStatus(Constants.STATUS_NORMAL);
					position.setWarehousename(warehousename);
					position.setHandler(userid);
					positionDao.insertPosition(position);
				}
				return true;
			}
		} else {
			//没有库存数据
		}
		return false;
	}

	
	
	@Override
	public boolean checkProductQuantity(WarehouseCheckDto chkdto, String userid) {
		//查询原始库存
		ProductQuantityDto p = warehouseDao.queryProductQuantityById(chkdto.getProductid());
		//仓库
		String warehousename = PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_WAREHOUSE_NAME);
		String belongto = PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_BELONG);
		if(p != null) {
			if(p.getQuantity() != null && !"".equals(p.getQuantity())) {
				//有库存数据
				//判断库存数量和输入的数量是否相等
				BigDecimal oldNum = new BigDecimal(p.getQuantity());
				BigDecimal newNum = new BigDecimal(0);
				if (chkdto.getCheckAmount()!= null)
					newNum = chkdto.getCheckAmount();
				if(oldNum.compareTo(newNum) != 0) {
					//需要新增库存数据
					BigDecimal addNum = newNum.subtract(oldNum);
					
					ProductDto product = productDao.queryProductByID(chkdto.getProductid());
					
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					
				}
				String checkday = DateUtil.dateToShortStr(new Date());
				//查询盘点表的数据
				List<PositionDto> list = positionDao.queryPositionByLogicId("", chkdto.getProductid(), checkday);
				if(list != null && list.size() > 0) {
					PositionDto position = list.get(0);
					//更新数据
					position.setAmount(newNum);
					position.setBeforeamount(oldNum);
					position.setHandler(userid);
					position.setUpdateuid(userid);
					position.setProductposition(chkdto.getWarehouseposition());
					position.setRes01(chkdto.getRes03());
					positionDao.updatePosition(position);
				} else {
					//没有位置数据，则新增一条记录
					//新增盘点记录
					PositionDto position = new PositionDto();
					position = new PositionDto();
					position.setAmount(newNum);
					position.setBeforeamount(oldNum);
					position.setBelongto(belongto);
					position.setCreateuid(userid);
					position.setUpdateuid(userid);
					position.setProductid(chkdto.getProductid());
					position.setCheckday(checkday);
					position.setProductposition(chkdto.getWarehouseposition());
					position.setRes01(chkdto.getRes03());
					position.setRank(Constants.ROLE_RANK_OPERATOR);
					position.setStatus(Constants.STATUS_NORMAL);
					position.setWarehousename(warehousename);
					position.setHandler(userid);
					positionDao.insertPosition(position);
				}
				return true;
			}
		} else {
			//没有库存数据
		}
		return false;
	}
	

	@Override
	public ProductQuantityDto queryProductQuantityById(String productid) {
		return warehouseDao.queryProductQuantityById(productid);
	}
	
	@Override
	public Page queryWarehouseRefundByPage(String warehousetype, String theme1,
			String warehousename, Page page) {
		warehousename = StringUtil.replaceDatabaseKeyword_mysql(warehousename);
		//查询总记录数
		int totalCount = warehouseDao.queryWarehouseRefundCountByPage(warehousetype, theme1, warehousename);
		page.setTotalCount(totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		//翻页查询记录
		List<WarehouseDto> list = warehouseDao.queryWarehouseRefundByPage(warehousetype, theme1, warehousename,
				page.getStartIndex() * page.getPageSize(), page.getPageSize());
		if(list != null && list.size() > 0) {
			for(WarehouseDto warehouseDto : list) {
				ProductDto product = productDao.queryProductByID(warehouseDto.getProductid());
				if(product != null) {
					warehouseDto.setProductname(product.getTradename());
					warehouseDto.setTypeno(product.getTypeno());
					warehouseDto.setColor(product.getColor());
					warehouseDto.setPackaging(product.getPackaging());
					warehouseDto.setUnit(product.getUnit());
					warehouseDto.setItem10(product.getItem10());
					warehouseDto.setMakearea(product.getMakearea());
				}
			}
		}
		page.setItems(list);
		return page;
	}
	
	@Override
	public Page queryWarehouseCheckByPage(String parentid,
			String warehousetype, String warehouseno, String theme1,
			String productid, String tradename, String typeno, String color,
			String warehousename, Page page) {
		tradename = StringUtil.replaceDatabaseKeyword_mysql(tradename);
		typeno = StringUtil.replaceDatabaseKeyword_mysql(typeno);
		warehousename = StringUtil.replaceDatabaseKeyword_mysql(warehousename);
		//查询总记录数
		int totalCount = warehouseDao.queryWarehouseCheckCountByPage(parentid, warehousetype,
				warehouseno, theme1, productid, tradename, typeno, color, warehousename);
		page.setTotalCount(totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		//翻页查询记录
		List<WarehouseCheckDto> list = warehouseDao.queryWarehouseCheckByPage(parentid, warehousetype,
				warehouseno, theme1, productid, tradename, typeno, color, warehousename,
				page.getStartIndex() * page.getPageSize(), page.getPageSize());
		if(list != null && list.size() > 0) {
			for(WarehouseCheckDto warehouseCheck : list) {
				List<PositionDto> listPosition = positionDao.queryPositionByLogicId("", warehouseCheck.getProductid(), "");
				if(listPosition != null && listPosition.size() > 0) {
					PositionDto position = listPosition.get(0);
					warehouseCheck.setWarehouseposition(position.getProductposition());
//					warehouseCheck.setCheckAmount(position.getAmount());
					warehouseCheck.setRes03(position.getRes01());
					UserDto user = userDao.queryUserByID(position.getHandler());
					if(user != null) {
						warehouseCheck.setHandlename(user.getUsername());
					}
				} else {
					warehouseCheck.setWarehouseposition("");
				}
			}
		}
		page.setItems(list);
		return page;
	}
	
	@Override
	public Page queryWarehouseProductByPage(
			String parentid, String warehousetype, String warehouseno,
			String theme1, String productid, String tradename,
			String typeno, String color, String warehousename, Page page) {
		tradename = StringUtil.replaceDatabaseKeyword_mysql(tradename);
		typeno = StringUtil.replaceDatabaseKeyword_mysql(typeno);
		warehousename = StringUtil.replaceDatabaseKeyword_mysql(warehousename);
		//查询总记录数
		int totalCount = warehouseDao.queryWarehouseProductCountByPage(parentid, warehousetype,
				warehouseno, theme1, productid, tradename, typeno, color, warehousename);
		page.setTotalCount(totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		//翻页查询记录
		List<WarehouseProductDto> list = warehouseDao.queryWarehouseProductByPage(parentid, warehousetype,
				warehouseno, theme1, productid, tradename, typeno, color, warehousename,
				page.getStartIndex() * page.getPageSize(), page.getPageSize());
		page.setItems(list);
		return page;
	}
	
	@Override
	public void warehouseInOk(String ids, String userid, String strWarehouseNo) throws RuntimeException {
		if(StringUtil.isNotBlank(ids)) {
			String belongto = PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_BELONG);
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
			
			String[] idList = ids.split(",");
			WarehouseDto warehouse = null;
			
			List<PurchaseDto> updPurchaseList = new ArrayList<PurchaseDto>();

			//验证是否是同一个仓库，相同的预入库时间
			String warehousename = "";
			String plandate = "";
			String suppid = "";
			for(int i = 0; i < idList.length; i++) {
				String id = idList[i];
				if(StringUtil.isNotBlank(id)) {
					//根据ID查询库存记录
					warehouse = warehouseDao.queryWarehouseByID(id);
					if(warehouse != null) {
						//数据check
						if(warehouse.getStatus() != Constants.WAREHOUSE_STATUS_NEW) {
							throw new RuntimeException("当前记录状态已更改，不能生成入库单！");
						}
						if(StringUtil.isBlank(warehousename)) {
							warehousename = warehouse.getWarehousename();
						}
						if(StringUtil.isBlank(plandate)) {
							plandate = warehouse.getPlandate();
						}
						if(StringUtil.isBlank(suppid)) {
							suppid = "" + warehouse.getSupplierid();
						}
						
						if(!warehousename.equals(warehouse.getWarehousename())) {
							throw new RuntimeException("不同仓库记录不能合并成一个入库单！");
						}
						if(!suppid.equals("" + warehouse.getSupplierid())) {
							throw new RuntimeException("不同供应商的记录不能合并成一个入库单！");
						}
//						if(!plandate.equals(warehouse.getPlandate())) {
//							throw new RuntimeException("不同预入库时间记录不能合并成一个入库单！");
//						}
					}
				}
			}
			
			//供应商ID
			String supplierid = "";
			//产品合集
			Map<String, BigDecimal> quantityMap = new HashMap<String, BigDecimal>();
			Map<String, BigDecimal> amountMap = new HashMap<String, BigDecimal>();
			//入库单号集合
			String warehousenos = "";
			BigDecimal count = new BigDecimal(0);
			//产品信息
			String productinfo = "";
			//含税金额合计
			BigDecimal totaltaxamount = new BigDecimal(0);
			for(int i = 0; i < idList.length; i++) {
				String id = idList[i];
				if(StringUtil.isNotBlank(id)) {
					//根据ID查询库存记录
					warehouse = warehouseDao.queryWarehouseByID(id);
					if(warehouse != null) {
						supplierid = "" + warehouse.getSupplierid();
						
						if(quantityMap.get(warehouse.getProductid()) != null) {
							quantityMap.put(warehouse.getProductid(), quantityMap.get(warehouse.getProductid()).add(warehouse.getQuantity()));
							amountMap.put(warehouse.getProductid(), amountMap.get(warehouse.getProductid()).add(warehouse.getTaxamount()));
						} else {
							quantityMap.put(warehouse.getProductid(), warehouse.getQuantity());
							amountMap.put(warehouse.getProductid(), warehouse.getTaxamount());
						}
						
						warehouse.setUpdateuid(userid);
						warehouse.setApproverid(userid);
						warehouse.setStatus(Constants.WAREHOUSE_STATUS_OK);
						
						//计算当前集集的库存数量
						count = count.add(warehouse.getQuantity());
						warehousenos += warehouse.getWarehouseno() + ",";
						
						productinfo += warehouse.getProductid() + "," + warehouse.getQuantity() + "," + warehouse.getTaxamount() + "," + StringUtil.getStr(warehouse.getRes09()) + "," + StringUtil.getStr(warehouse.getRes02()) + "#";
						
						//计算含税金额
						totaltaxamount = totaltaxamount.add(warehouse.getTaxamount());
						
						warehouseDao.updateWarehouse(warehouse);
						
						// SZA 仓库追加
						// 特殊发货场合 加上仓库编号 SZ为深圳SZ
						if (!strWarehouseNo.isEmpty() && !strWarehouseNo.equals("")){
							WarehouseDto warehouse_sza = warehouseSZADao.queryWarehouseByWarehouseno(warehouse.getWarehouseno());
							if (warehouse_sza!= null){
//								warehouseSZADao.updateWarehouse(warehouse);
							}else {
								warehouseSZADao.insertWarehouse(warehouse);							
							}	
						}						

						
						List<PurchaseItemDto> purchaseItemList = purchaseItemDao.queryPurchaseItemByPurchaseno(warehouse.getParentid());
						if(purchaseItemList != null && purchaseItemList.size() > 0) {
							boolean b = true;
							//判断当前的采购单对应的货物是否都已入库：采购数量=入库数量
							for(PurchaseItemDto item : purchaseItemList) {
								if(item.getQuantity() != null && item.getQuantity().floatValue() > item.getInquantity().floatValue()) {
									b = false;
									break;
								}
							}
							if(b) {
								//判断所有的库存记录均为已确认
								List<WarehouseDto> listWarehouse = warehouseDao.queryWarehouseByParentid(warehouse.getParentid(), "");
								for(WarehouseDto warehouseDto : listWarehouse) {
									if(warehouseDto.getStatus() <= Constants.WAREHOUSE_STATUS_NEW) {
										b = false;
										break;
									}
								}
							}
							//以上2个条件均满足，则更新采购单状态
							if(b) {
								PurchaseDto purchaseDto = purchaseDao.queryPurchaseByNo(warehouse.getParentid());
								//需要更新采购单状态=入库确认
								purchaseDto.setStatus(Constants.PURCHASE_STATUS_WAREHOUSE_OK);
								purchaseDto.setUpdateuid(userid);
								purchaseDao.updatePurchase(purchaseDto);
							} else {
								PurchaseDto purchaseDto = purchaseDao.queryPurchaseByNo(warehouse.getParentid());
								//需要更新采购单状态=部分入库
								purchaseDto.setStatus(Constants.PURCHASE_STATUS_WAREHOUSE_PART);
								purchaseDto.setUpdateuid(userid);
								purchaseDao.updatePurchase(purchaseDto);
							}
						}
					}
				}
			}
			
			WarehouserptDto warehouserpt = new WarehouserptDto();
			//数据来源类型=入库单
			warehouserpt.setWarehousetype(Constants.WAREHOUSERPT_TYPE_IN);
			warehouserpt.setBelongto(PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_BELONG));
			
			//入库单号
			//String warehouseno = Constants.WAREHOUSERPT_IN_NO_PRE + belongto + sdf.format(date);
			int newVal = 1;
			SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
			String year = sdfYear.format(date);
			//根据入库单+年份查询入库单当前番号
			List<Dict01Dto> dictList = dict01Dao.queryDict01ByFieldcode(Constants.WAREHOUSERPT_IN_NO_PRE + year, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
			if(dictList != null && dictList.size() > 0) {
				Dict01Dto dict = dictList.get(0);
				//入库单番号+1
				newVal = Integer.valueOf(dict.getCode()) + 1;
				dict.setCode("" + newVal);
				//更新入库单番号
				dict01Dao.updateDict01(dict);
			} else {
				//新增入库单番号
				Dict01Dto dict = new Dict01Dto();
				dict.setCode("1");
				dict.setCreateuid("admin");
				dict.setUpdateuid("admin");
				dict.setFieldcode(Constants.WAREHOUSERPT_IN_NO_PRE + year);
				dict.setFieldname(year + "入库单番号");
				dict.setNote(year + "入库单番号");
				dict.setLang(PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
				dict.setMean(Constants.WAREHOUSERPT_IN_NO_PRE + year);
				dict.setStatus(Constants.STATUS_NORMAL);
				dict01Dao.insertDict01(dict);
			}
			String warehouseno = Constants.WAREHOUSERPT_IN_NO_PRE + belongto + year.substring(2, 4) + StringUtil.replenishStr("" + newVal, 6);
			
			// 特殊发货场合 加上仓库编号 SZ为深圳SZ
			if (!strWarehouseNo.isEmpty() && !strWarehouseNo.equals(""))
				warehouseno = warehouseno.concat(strWarehouseNo);

			warehouserpt.setWarehouseno(warehouseno);
			//仓库名
			warehouserpt.setWarehousename(warehousename);
			//主题
			//warehouserpt.setTheme1(list.get(0).getTheme1());
			//产品信息
			/*
			String productinfo = "";


			Set<?> key = quantityMap.keySet();
			for (Iterator<?> it = key.iterator(); it.hasNext();) {
				String k = (String) it.next();
				productinfo += k + "," + quantityMap.get(k) + "," + amountMap.get(k) + "#";
			} */
			
			warehouserpt.setProductinfo(productinfo);
			//入库单RPT日期
			warehouserpt.setWarehousedate(DateUtil.dateToShortStr(date));
			//入库数量
			warehouserpt.setTotalnum(count);
			//含税金额
			warehouserpt.setTotaltaxamount(totaltaxamount);
			//收货人
			warehouserpt.setHandler("");
			
			//查询供应商信息
			SupplierDto supplier = supplierDao.queryAllSupplierByID(supplierid);
			//获得采购单的供应商
			warehouserpt.setSupplierid(supplierid);
			
			if(supplier != null) {
				warehouserpt.setSuppliername(supplier.getSuppliername());
				warehouserpt.setSupplieraddress(supplier.getSupplieraddress1());
				warehouserpt.setSuppliermail(supplier.getSuppliermail1());
				warehouserpt.setSuppliermanager(supplier.getSuppliermanager1());
				warehouserpt.setSuppliertel(supplier.getSuppliertel1());
				warehouserpt.setSupplierfax(supplier.getSupplierfax1());
				
				// 用友账套编码  1：贸易   2：发展
				warehouserpt.setRes08(supplier.getRes03());
				//获得采购单的供应商用友编码
				if (supplier.getRes03() != null){
					//发展
					if (supplier.getRes03().equals("2")){
						warehouserpt.setRes04(supplier.getRes02());
					}
					//贸易
					if (supplier.getRes03().equals("1")){
						warehouserpt.setRes04(supplier.getRes04());
					} 								
				}
			}
			//快递公司ID==============================这里不做填充，等发货单时填充
			
			warehouserpt.setRank(Constants.ROLE_RANK_OPERATOR);
			warehouserpt.setStatus(Constants.FINANCE_STATUS_NEW);
			//warehouserpt.setProductid(Long.valueOf(productid));
			//入库单单号集合
			warehouserpt.setParentid(warehousenos);
			warehouserpt.setCreateuid(userid);
			warehouserpt.setUpdateuid(userid);
			
			warehouserptDao.insertWarehouserpt(warehouserpt);
			
			// 深圳仓库SZ追加
			// 特殊发货场合 加上仓库编号 SZ为深圳SZ
			if (!strWarehouseNo.isEmpty() && !strWarehouseNo.equals(""))
				warehouserptSZADao.insertWarehouserpt(warehouserpt);			
			
			//新增一条财务记录（这里财务记录和入库单关联）
			FinanceDto finance = new FinanceDto();
			//类型=入库单
			finance.setFinancetype(Constants.FINANCE_TYPE_PURCHASE);
			//采购单（付款）
			finance.setMode("2");
			finance.setBelongto(belongto);
			//单据号=入库单号
			finance.setInvoiceid(warehouseno);
			//发票号
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
			String receiptid = Constants.FINANCE_NO_PRE + belongto + sdf1.format(date);
			finance.setReceiptid(receiptid);
			//开票日期
			//finance.setReceiptdate(receiptdate);
			//结算日期=当天
			finance.setAccountdate(DateUtil.dateToShortStr(date));
			//金额=采购金额含税
			finance.setAmount(totaltaxamount);
			//负责人
			finance.setHandler(userid);
			//供应商信息
			finance.setCustomerid(Long.valueOf(supplierid));
			finance.setCustomername(supplier.getSuppliername());
			finance.setCustomertel(supplier.getSuppliertel1());
			finance.setCustomermanager(supplier.getSuppliermanager1());
			finance.setCustomeraddress(supplier.getSupplieraddress1());
			finance.setCustomermail(supplier.getSuppliermail1());
			finance.setRank(Constants.ROLE_RANK_OPERATOR);
			//状态=付款申请
			finance.setStatus(Constants.FINANCE_STATUS_NEW);
			finance.setCreateuid(userid);
			finance.setUpdateuid(userid);
			financeDao.insertFinance(finance);
		}
	}
	
	@Override
	public void warehouseOutOk(String ids, String userid, String strWarehouseNo) throws RuntimeException {

		if(StringUtil.isNotBlank(ids)) {
			String belongto = PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_BELONG);
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
			
			String[] idList = ids.split(",");
			WarehouseDto warehouse = null;
			
			//部分入库的销售单列表
			List<SalesDto> updSalesList = new ArrayList<SalesDto>();
			//已入库的销售单列表
			List<SalesDto> updSalesFinishList = new ArrayList<SalesDto>();
			//部分入库的销售单ID map，用于保证不重复更新his记录
			Map<String, String> salesHisMap = new HashMap<String, String>();
			//已入库的销售单ID map，用于保证不重复更新his记录
			Map<String, String> salesFinishHisMap = new HashMap<String, String>();

			//验证是否是同一个仓库，相同的预出库时间
			String warehousename = "";
			String supplierid = "";
			String plandate = "";
			String res06 = "";
			for(int i = 0; i < idList.length; i++) {
				String id = idList[i];
				if(StringUtil.isNotBlank(id)) {
					//根据ID查询库存记录
					warehouse = warehouseDao.queryWarehouseByID(id);
					if(warehouse != null) {
						//数据check
						if(warehouse.getStatus() != Constants.WAREHOUSE_STATUS_NEW) {
							throw new RuntimeException("当前记录状态已更改，不能生成出库单！");
						}
						
						if(StringUtil.isBlank(warehousename)) {
							warehousename = warehouse.getWarehousename();
						}
						//是否是online订单
						if(StringUtil.isBlank(res06)) {
							res06 = warehouse.getRes06();
						}
						if(StringUtil.isBlank(plandate)) {
							plandate = warehouse.getPlandate();
						}
						if(StringUtil.isBlank(supplierid)) {
							supplierid = "" + warehouse.getSupplierid();
						}
						
						if(!warehousename.equals(warehouse.getWarehousename())) {
							throw new RuntimeException("不同仓库记录不能合并成一个出库单！");
						}
						//判断是否来源一致
						if(warehouse.getRes06() != null && !res06.equals("" + warehouse.getRes06())) {
							throw new RuntimeException("Online订单和非Online订单记录不能合并成一个出库单！");
						}
						//对于出库单，这里记录的是客户ID
						if(!supplierid.equals("" + warehouse.getSupplierid())) {
							throw new RuntimeException("不同客户的记录不能合并成一个出库单！");
						}
//						if(!plandate.equals(warehouse.getPlandate())) {
//							throw new RuntimeException("不同预出库时间记录不能合并成一个出库单！");
//						}
					}
				}
			}
			
			//客户ID
			String customerid = "";
			//入库单号集合
			String warehousenos = "";
			//产品信息
			String productinfo = "";
			//warehouse theme2
			String theme2 = "";
			//产品合集
			Map<String, BigDecimal> quantityMap = new HashMap<String, BigDecimal>();
			Map<String, BigDecimal> amountMap = new HashMap<String, BigDecimal>();
			BigDecimal count = new BigDecimal(0);
			//含税金额合计
			BigDecimal totaltaxamount = new BigDecimal(0);
			
			//计算出库单利润率-----update by frank
			//成本金额合计
			BigDecimal totalprimeamount = new BigDecimal(0);
			//税率=（1+税率）
			List<Dict01Dto> listRate = dict01Dao.queryDict01ByFieldcode(Constants.DICT_RATE, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
			//默认为0
			BigDecimal rate = new BigDecimal(0);
			if(listRate != null && listRate.size() > 0) {
				rate = new BigDecimal(listRate.get(0).getCode());
				rate = rate.add(new BigDecimal(1));
			}
			
			
			for(int i = 0; i < idList.length; i++) {
				String id = idList[i];
				if(StringUtil.isNotBlank(id)) {
					//根据ID查询库存记录
					warehouse = warehouseDao.queryWarehouseByID(id);
					if(warehouse != null) {
						customerid = "" + warehouse.getSupplierid();
						
						theme2 = warehouse.getTheme2();
						
						if(quantityMap.get(warehouse.getProductid()) != null) {
							//发货单数量是负数，所以需要变成正的
							if(warehouse.getQuantity().floatValue() < 0) {
								quantityMap.put(warehouse.getProductid(), quantityMap.get(warehouse.getProductid()).add(warehouse.getQuantity().multiply(new BigDecimal(-1))));
							} else {
								quantityMap.put(warehouse.getProductid(), warehouse.getQuantity());
							}
							amountMap.put(warehouse.getProductid(), amountMap.get(warehouse.getProductid()).add(warehouse.getTaxamount()));
						} else {
							//发货单数量是负数，所以需要变成正的
							if(warehouse.getQuantity().floatValue() < 0) {
								quantityMap.put(warehouse.getProductid(), warehouse.getQuantity().multiply(new BigDecimal(-1)));
							} else {
								quantityMap.put(warehouse.getProductid(), warehouse.getQuantity());
							}
							amountMap.put(warehouse.getProductid(), warehouse.getTaxamount());
						}
						
						warehouse.setUpdateuid(userid);
						warehouse.setApproverid(userid);
						warehouse.setRes08(strWarehouseNo);
						warehouse.setStatus(Constants.WAREHOUSE_STATUS_OK);
						
						//计算当前集集的库存数量
						count = count.add(warehouse.getQuantity());
						warehousenos += warehouse.getWarehouseno() + ",";
						productinfo += warehouse.getProductid() + "," + warehouse.getQuantity() + "," + warehouse.getTaxamount() + "," + StringUtil.getStr(warehouse.getRes09()) + "," + StringUtil.getStr(warehouse.getRes02()) + "#";
						
						//计算含税金额
						totaltaxamount = totaltaxamount.add(warehouse.getTaxamount());
						
						//成本价合计
						totalprimeamount = totalprimeamount.add(WarehouseUtil.calcPrimeAmount(warehouse, rate));
						
						warehouseDao.updateWarehouse(warehouse);
						
						// SZA 仓库追加
						// 特殊发货场合 加上仓库编号 SZ为深圳SZ
						if (!strWarehouseNo.isEmpty() && !strWarehouseNo.equals("")){
							WarehouseDto warehouse_sza = warehouseSZADao.queryWarehouseByWarehouseno(warehouse.getWarehouseno());
							if (warehouse_sza!= null){
//								warehouseSZADao.updateWarehouse(warehouse);
							}else {
								warehouseSZADao.insertWarehouse(warehouse);							
							}	
						}						
						List<SalesItemDto> itemList = salesItemDao.querySalesItemBySalesno(warehouse.getParentid());
						if(itemList != null && itemList.size() > 0) {
							boolean b = true;
							//判断当前的采购单对应的货物是否都已入库：采购数量=入库数量
							for(SalesItemDto item : itemList) {
								if(item.getQuantity() != null && item.getQuantity().floatValue() > item.getOutquantity().floatValue()) {
									b = false;
									break;
								}
							}
							if(b) {
								//判断所有的库存记录均为已确认
								List<WarehouseDto> listWarehouse = warehouseDao.queryWarehouseByParentid(warehouse.getParentid(), "");
								for(WarehouseDto warehouseDto : listWarehouse) {
									if(warehouseDto.getStatus() <= Constants.WAREHOUSE_STATUS_NEW) {
										b = false;
										break;
									}
								}
							}
							//以上2个条件均满足，则更新采购单状态
							if(b) {
								//需要更新销售单状态=已入库
								SalesDto salesDto = salesDao.querySalesByNo(warehouse.getParentid());
								salesDto.setStatus(Constants.SALES_STATUS_WAREHOUSE_OK);
								salesDto.setUpdateuid(userid);
								salesDao.updateSales(salesDto);
								//已入库的销售单列表
								if(!salesFinishHisMap.containsKey(salesDto.getSalesno())) {
									salesFinishHisMap.put(salesDto.getSalesno(), salesDto.getSalesno());
									updSalesFinishList.add(salesDto);
								}
							} else {
								//需要更新销售单状态=部分入库
								SalesDto salesDto = salesDao.querySalesByNo(warehouse.getParentid());
								if(Constants.SALES_STATUS_WAREHOUSE_PART != salesDto.getStatus()) {
									salesDto.setStatus(Constants.SALES_STATUS_WAREHOUSE_PART);
									salesDto.setUpdateuid(userid);
									salesDao.updateSales(salesDto);
									//部分入库的销售单列表
									if(!salesHisMap.containsKey(salesDto.getSalesno())) {
										salesHisMap.put(salesDto.getSalesno(), salesDto.getSalesno());
										updSalesList.add(salesDto);
									}
								}
							}
						}
					}
				}
			}
			// 更新部分入库销售履历
			for(SalesDto updSalesDto : updSalesList) {
				long updSalesid= salesHisDao.insertSalesHis(updSalesDto);
				List <SalesItemDto> updSalesItemDto = new ArrayList<SalesItemDto>();
				updSalesItemDto = salesItemDao.querySalesItemBySalesno(updSalesDto.getSalesno());
				if(updSalesItemDto != null) {
					for(SalesItemDto salesItem : updSalesItemDto) {
						//销售单号
						salesItem.setSalesno(updSalesDto.getSalesno().toString());
						salesItem.setUpdateuid(userid);
						salesItem.setCreateuid(userid);
						salesItem.setStatus(Constants.STATUS_NORMAL);
						salesItem.setBelongto(belongto);
						//添加履历
						salesItem.setRes06("" + updSalesid);
						salesItemHisDao.insertSalesItemHis(salesItem);
					}
				}
			}
			// 更新已入库的销售履历
			for(SalesDto updSalesDto : updSalesFinishList) {
				long updSalesid= salesHisDao.insertSalesHis(updSalesDto);
				List <SalesItemDto> updSalesItemDto = new ArrayList<SalesItemDto>();
				updSalesItemDto = salesItemDao.querySalesItemBySalesno(updSalesDto.getSalesno());
				if(updSalesItemDto != null) {
					for(SalesItemDto salesItem : updSalesItemDto) {
						//销售单号
						salesItem.setSalesno(updSalesDto.getSalesno().toString());
						salesItem.setUpdateuid(userid);
						salesItem.setCreateuid(userid);
						salesItem.setStatus(Constants.STATUS_NORMAL);
						salesItem.setBelongto(belongto);
						//添加履历
						salesItem.setRes06("" + updSalesid);
						salesItemHisDao.insertSalesItemHis(salesItem);
					}
				}
			}
			
			WarehouserptDto warehouserpt = new WarehouserptDto();
			//数据来源类型=出库单
			warehouserpt.setWarehousetype(Constants.WAREHOUSERPT_TYPE_OUT);
			warehouserpt.setBelongto(PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_BELONG));
			
			//出库单号
			//String warehouseno = Constants.WAREHOUSERPT_OUT_NO_PRE + belongto + sdf.format(date);
			int newVal = 1;
			SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
			String year = sdfYear.format(date);
			//根据出库单+年份查询出库单当前番号
			List<Dict01Dto> dictList = dict01Dao.queryDict01ByFieldcode(Constants.WAREHOUSERPT_OUT_NO_PRE + year, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
			if(dictList != null && dictList.size() > 0) {
				Dict01Dto dict = dictList.get(0);
				//出库单番号+1
				newVal = Integer.valueOf(dict.getCode()) + 1;
				dict.setCode("" + newVal);
				//更新出库单番号
				dict01Dao.updateDict01(dict);
			} else {
				//新增出库单番号
				Dict01Dto dict = new Dict01Dto();
				dict.setCode("1");
				dict.setCreateuid("admin");
				dict.setUpdateuid("admin");
				dict.setFieldcode(Constants.WAREHOUSERPT_OUT_NO_PRE + year);
				dict.setFieldname(year + "出库单番号");
				dict.setNote(year + "出库单番号");
				dict.setLang(PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
				dict.setMean(Constants.WAREHOUSERPT_OUT_NO_PRE + year);
				dict.setStatus(Constants.STATUS_NORMAL);
				dict01Dao.insertDict01(dict);
			}
			String warehouseno = Constants.WAREHOUSERPT_OUT_NO_PRE + belongto + year.substring(2, 4)+ StringUtil.replenishStr("" + newVal, 6);
			
			// 特殊发货场合 加上仓库编号 SZ为深圳SZ
			if (!strWarehouseNo.isEmpty() && !strWarehouseNo.equals(""))
				warehouseno = warehouseno.concat(strWarehouseNo);
			warehouserpt.setWarehouseno(warehouseno);
			
			
			//仓库名
			warehouserpt.setWarehousename(warehousename);
			//主题
			//warehouserpt.setTheme1(list.get(0).getTheme1());
			//产品信息
			/*
			String productinfo = "";
			Set<?> key = quantityMap.keySet();
			for (Iterator<?> it = key.iterator(); it.hasNext();) {
				String k = (String) it.next();
				productinfo += k + "," + quantityMap.get(k) + "," + amountMap.get(k) + "#";
			} */
			warehouserpt.setProductinfo(productinfo);
			//入库单RPT日期
			warehouserpt.setWarehousedate(DateUtil.dateToShortStr(date));
			
			//入库数量
			if(count.floatValue() < 0) {
				count = count.multiply(new BigDecimal(-1));
			}
			warehouserpt.setTotalnum(count);
			
			//含税金额
			warehouserpt.setTotaltaxamount(totaltaxamount);
			//收货人
			warehouserpt.setHandler("");
			
			//获得销售单的客户信息
			warehouserpt.setSupplierid(customerid);

			CustomerDto customer = null;
			CustomerOnlineDto customerOnline = null;
			if("1".equals(res06)) {
				//online订单
				customerOnline = customerOnlineDao.queryCustomerOnlineByID(customerid);
				if(customerOnline != null) {
					warehouserpt.setSuppliername(customerOnline.getCompanycn());
					//默认地址=客户信息收货地址，但是订单的收货地址可能不是客户的收货地址，所以优先取订单的收货地址
					warehouserpt.setSupplieraddress(customerOnline.getAddress());
					warehouserpt.setSuppliermanager(customerOnline.getName());
					warehouserpt.setSuppliertel(customerOnline.getTell());
					warehouserpt.setSuppliermail(customerOnline.getCustomeremail());					
					warehouserpt.setSupplierfax("");
					
					//查询订单信息
					if(StringUtil.isNotBlank(theme2)) {
						String ordercode = theme2.substring(0, theme2.length() - 3);
						OrderDto order = orderDao.queryOrderByOrdercode(ordercode);
						if(order != null) {
						}
					}
					if (customerOnline.getCompanycn2()!= null)
						warehouserpt.setSuppliername(customerOnline.getCompanycn2());
					//订单的收货地址
					if (customerOnline.getAddress2()!= null)
						warehouserpt.setSupplieraddress(customerOnline.getAddress2());
					if (customerOnline.getName2()!= null)
						warehouserpt.setSuppliermanager(customerOnline.getName2());
					if (customerOnline.getTell2()!= null)
						warehouserpt.setSuppliertel(customerOnline.getTell2());
					// 用友账套编码  1：贸易   2：发展
					warehouserpt.setRes08(customerOnline.getRes03());
					//获得订单的客户用友编码
					if (customerOnline.getRes03()!= null) {
						//发展
						if (customerOnline.getRes03().equals("2")){
							warehouserpt.setRes04(customerOnline.getRes02());
						}
						//贸易
						if (customerOnline.getRes03().equals("1")){
							warehouserpt.setRes04(customerOnline.getRes04());
						} 									
					}
				}
			} else {
				//非online订单
				//查询客户信息
				customer = customerDao.queryEtbCustomerByID(customerid);
				if(customer != null) {
					warehouserpt.setSuppliername(customer.getCustomername());
					warehouserpt.setSupplieraddress(customer.getCustomeraddress1());
					
					warehouserpt.setSuppliermail(customer.getCustomermail1());
					warehouserpt.setSuppliermanager(customer.getCustomermanager1());
					warehouserpt.setSuppliertel(customer.getCustomertel1());
					warehouserpt.setSupplierfax(customer.getCustomerfax1());
					
					//订单的收货地址
					if (customer.getCustomeraddress2()!= null && !customer.getCustomeraddress2().equals(""))
						warehouserpt.setSupplieraddress(customer.getCustomeraddress2());
					if (customer.getCustomermanager2()!= null && !customer.getCustomermanager2().equals(""))
						warehouserpt.setSuppliermanager(customer.getCustomermanager2());
					if (customer.getCustomertel2()!= null && !customer.getCustomertel2().equals(""))
						warehouserpt.setSuppliertel(customer.getCustomertel2());
					
					if (customer.getCustomeraddress3()!= null && !customer.getCustomeraddress3().equals(""))
						warehouserpt.setSupplieraddress(customer.getCustomeraddress3());
					if (customer.getCustomermanager3()!= null && !customer.getCustomermanager3().equals(""))
						warehouserpt.setSuppliermanager(customer.getCustomermanager3());
					if (customer.getCustomertel3()!= null && !customer.getCustomertel3().equals(""))
						warehouserpt.setSuppliertel(customer.getCustomertel3());

					// 用友账套编码  1：贸易   2：发展
					warehouserpt.setRes08(customer.getRes03());
					//获得订单的客户用友编码
					if (customer.getRes03() != null){
						//发展
						if (customer.getRes03().equals("2")){
							warehouserpt.setRes04(customer.getRes02());
						}
						//贸易
						if (customer.getRes03().equals("1")){
							warehouserpt.setRes04(customer.getRes04());
						} 									
					}
				}
			}
			
			//订单利润率
			warehouserpt.setRes09(WarehouseUtil.calcProfitRate(totalprimeamount, totaltaxamount));
			
			//快递公司ID==============================这里不做填充，等发货单时填充
			
			warehouserpt.setRank(Constants.ROLE_RANK_OPERATOR);
			warehouserpt.setStatus(Constants.FINANCE_STATUS_NEW);
			//入库单单号集合
			warehouserpt.setParentid(warehousenos);
			warehouserpt.setCreateuid(userid);
			warehouserpt.setUpdateuid(userid);
			
			warehouserptDao.insertWarehouserpt(warehouserpt);

			// 深圳仓库SZ追加
			// 特殊发货场合 加上仓库编号 SZ为深圳SZ
			if (!strWarehouseNo.isEmpty() && !strWarehouseNo.equals(""))
				warehouserptSZADao.insertWarehouserpt(warehouserpt);
			
			//新增一条财务记录（这里财务记录和出库单关联）
			FinanceDto finance = new FinanceDto();
			//类型=销售单
			finance.setFinancetype(Constants.FINANCE_TYPE_SALES);
			//销售单（收款）
			finance.setMode("1");
			finance.setBelongto(belongto);
			//单据号=出库单号
			finance.setInvoiceid(warehouseno);
			//发票号
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
			String receiptid = Constants.FINANCE_NO_PRE + belongto + sdf1.format(date);
			finance.setReceiptid(receiptid);
			//开票日期
			//finance.setReceiptdate(receiptdate);
			//结算日期=当天
			finance.setAccountdate(DateUtil.dateToShortStr(date));
			//金额=销售金额含税
			finance.setAmount(totaltaxamount);
			//负责人
			finance.setHandler(userid);
			
			if("1".equals(res06)) {
				//online订单
				//客户信息
				finance.setCustomerid(Long.valueOf(customerid));
				finance.setCustomername(customerOnline.getCompanycn());
				finance.setCustomertel(customerOnline.getTell());
				finance.setCustomermanager(customerOnline.getName());
				finance.setCustomeraddress(customerOnline.getAddress());
				finance.setCustomermail(customerOnline.getCustomeremail());
			} else {
				//非online订单
				//客户信息
				finance.setCustomerid(Long.valueOf(customerid));
				finance.setCustomername(customer.getCustomername());
				finance.setCustomertel(customer.getCustomertel1());
				finance.setCustomermanager(customer.getCustomermanager1());
				finance.setCustomeraddress(customer.getCustomeraddress1());
				finance.setCustomermail(customer.getCustomermail1());
			}
			finance.setRank(Constants.ROLE_RANK_OPERATOR);
			//状态=开票申请
			finance.setStatus(Constants.FINANCE_STATUS_NEW);
			finance.setCreateuid(userid);
			finance.setUpdateuid(userid);
			financeDao.insertFinance(finance);
		}
	}
	
	@Override
	public Page queryWarehouseInOkByPage(String suppliername, String theme, String tradename,
			String typeno, String color, String warehousename, String status, Page page) {
		tradename = StringUtil.replaceDatabaseKeyword_mysql(tradename);
		typeno = StringUtil.replaceDatabaseKeyword_mysql(typeno);
		warehousename = StringUtil.replaceDatabaseKeyword_mysql(warehousename);
		//查询总记录数
		int totalCount = warehouseDao.queryWarehouseInOutOkCountByPage("" + Constants.WAREHOUSE_TYPE_IN,
				suppliername, theme, tradename, typeno, color, warehousename, status);
		page.setTotalCount(totalCount);
//		System.out.println("totalCount: " + totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		
		//翻页查询记录
		List<WarehouseInOutOkDto> list = new ArrayList<WarehouseInOutOkDto>();
		list = warehouseDao.queryWarehouseInOkByPage("" + Constants.WAREHOUSE_TYPE_IN, suppliername, theme, tradename,
				typeno, color, warehousename, status,
				page.getStartIndex() * page.getPageSize(), page.getPageSize());
		page.setItems(list);
		return page;
	}

	@Override
	public Page queryWarehouseOutOkByPage(String suppliername, String theme, String tradename,
			String typeno, String color, String warehousename, String status, Page page) {
		tradename = StringUtil.replaceDatabaseKeyword_mysql(tradename);
		typeno = StringUtil.replaceDatabaseKeyword_mysql(typeno);
		warehousename = StringUtil.replaceDatabaseKeyword_mysql(warehousename);
		//查询总记录数
		int totalCount = warehouseDao.queryWarehouseOutOkCountByPage("" + Constants.WAREHOUSE_TYPE_OUT,
				suppliername, theme, tradename, typeno, color, warehousename, status);
		page.setTotalCount(totalCount);
//		System.out.println("totalCount: " + totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		
		//翻页查询记录
		List<WarehouseInOutOkDto> list = new ArrayList<WarehouseInOutOkDto>();
		list = warehouseDao.queryWarehouseOutOkByPage("" + Constants.WAREHOUSE_TYPE_OUT, suppliername, theme, tradename,
				typeno, color, warehousename, status,
				page.getStartIndex() * page.getPageSize(), page.getPageSize());
		page.setItems(list);
		return page;
	}
	
	
	@Override
	public Page queryWarehouseOutOk1ByPage(String suppliername, String theme, String tradename,
			String typeno, String color, String warehousename, String status, Page page) {
		tradename = StringUtil.replaceDatabaseKeyword_mysql(tradename);
		typeno = StringUtil.replaceDatabaseKeyword_mysql(typeno);
		warehousename = StringUtil.replaceDatabaseKeyword_mysql(warehousename);
		//查询总记录数
		int totalCount = warehouseDao.queryWarehouseOutOk1CountByPage("" + Constants.WAREHOUSE_TYPE_OUT,
				suppliername, theme, tradename, typeno, color, warehousename, status);
		page.setTotalCount(totalCount);
//		System.out.println("totalCount: " + totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		
		//翻页查询记录
		List<WarehouseInOutOkDto> list = new ArrayList<WarehouseInOutOkDto>();
		list = warehouseDao.queryWarehouseOutOk1ByPage("" + Constants.WAREHOUSE_TYPE_OUT, suppliername, theme, tradename,
				typeno, color, warehousename, status,
				page.getStartIndex() * page.getPageSize(), page.getPageSize());
		page.setItems(list);
		return page;
	}
	
	@Override
	public Page queryWarehouseOutOk2ByPage(String suppliername, String theme, String tradename,
			String typeno, String color, String warehousename, String status, Page page) {
		tradename = StringUtil.replaceDatabaseKeyword_mysql(tradename);
		typeno = StringUtil.replaceDatabaseKeyword_mysql(typeno);
		warehousename = StringUtil.replaceDatabaseKeyword_mysql(warehousename);
		//查询总记录数
		int totalCount = warehouseDao.queryWarehouseOutOk2CountByPage("" + Constants.WAREHOUSE_TYPE_OUT,
				suppliername, theme, tradename, typeno, color, warehousename, status);
		page.setTotalCount(totalCount);
//		System.out.println("totalCount: " + totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		
		//翻页查询记录
		List<WarehouseInOutOkDto> list = new ArrayList<WarehouseInOutOkDto>();
		list = warehouseDao.queryWarehouseOutOk2ByPage("" + Constants.WAREHOUSE_TYPE_OUT, suppliername, theme, tradename,
				typeno, color, warehousename, status,
				page.getStartIndex() * page.getPageSize(), page.getPageSize());
		page.setItems(list);
		return page;
	}
	
	
	
	@Override
	public Page queryWarehouseOkByPage(String warehouseType, String theme, String tradename,
			String typeno, String color, String warehousename, String status, Page page) {
		tradename = StringUtil.replaceDatabaseKeyword_mysql(tradename);
		typeno = StringUtil.replaceDatabaseKeyword_mysql(typeno);
		warehousename = StringUtil.replaceDatabaseKeyword_mysql(warehousename);
		//查询总记录数
		int totalCount = warehouseDao.queryWarehouseOkCountByPage(warehouseType,
				theme, tradename, typeno, color, warehousename, status);
		page.setTotalCount(totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		
		//翻页查询记录
		List<WarehouseOkDto> list = new ArrayList<WarehouseOkDto>();
		list = warehouseDao.queryWarehouseOkByPage(warehouseType, theme, tradename,
				typeno, color, warehousename, status,
				page.getStartIndex() * page.getPageSize(), page.getPageSize());
		page.setItems(list);
		return page;
	}

	@Override
	public Page queryWarehouseByPage(String parentid, String warehousetype,
			String warehouseno, String theme1, String supplierid,
			String productid, String status, String warehousename, Page page) {
		warehousename = StringUtil.replaceDatabaseKeyword_mysql(warehousename);
		//查询总记录数
		int totalCount = warehouseDao.queryWarehouseCountByPage(parentid, warehousetype, warehouseno,
				theme1, supplierid, productid, status, warehousename);
		page.setTotalCount(totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		//翻页查询记录
		List<WarehouseDto> list = warehouseDao.queryWarehouseByPage(parentid, warehousetype,
				warehouseno, theme1, supplierid, productid, status, warehousename,
				page.getStartIndex() * page.getPageSize(), page.getPageSize());
		page.setItems(list);
		return page;
	}

	@Override
	public WarehouseDto queryWarehouseByID(String id) {
		WarehouseDto warehouse = warehouseDao.queryWarehouseByID(id);
		if(warehouse != null) {
			ProductDto product = productDao.queryProductByID(warehouse.getProductid());
			if(product != null) {
				warehouse.setProductname(product.getTradename());
				warehouse.setTypeno(product.getTypeno());
				warehouse.setColor(product.getColor());
				warehouse.setPackaging(product.getPackaging());
				warehouse.setUnit(product.getUnit());
				warehouse.setItem10(product.getItem10());
			}
		}
		return warehouse;
	}
	
	@Override
	public List<WarehouseDto> queryWarehouseByIds(String ids) {
		return warehouseDao.queryWarehouseByIds(ids);
	}

	@Override
	public WarehouseDto queryWarehouseByParentidAndProductid(String parentid,
			String productid) {
		return warehouseDao.queryWarehouseByParentidAndProductid(parentid, productid);
	}
	
	@Override
	public List<WarehouseDto> queryWarehouseByParentid(String parentid) {
		return warehouseDao.queryWarehouseByParentid(parentid, "");
	}

	@Override
	public WarehouseDto queryWarehouseByWarehouseno(String warehouseno) {
		return warehouseDao.queryWarehouseByWarehouseno(warehouseno);
	}

	@Override
	public void insertWarehouse(WarehouseDto warehouse) {
		warehouseDao.insertWarehouse(warehouse);
	}
	
	@Override
	public String insertRefundWarehouse(WarehouseDto warehouse) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String belongto = PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_BELONG);
		//库存单号
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.substring(uuid.length() - 8, uuid.length());
		String warehouseno = Constants.WAREHOUSE_NO_PRE + belongto + sdf.format(date) + uuid;
		
		warehouse.setWarehouseno(warehouseno);
		warehouse.setWarehousename(PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_WAREHOUSE_NAME));
		ProductDto product = productDao.queryProductByID(warehouse.getProductid());
		//产品信息
		warehouse.setTheme1(product.getFieldno());
		warehouse.setWarehousedate(DateUtil.dateToShortStr(date));
		warehouse.setPlandate(DateUtil.dateToShortStr(date));
		warehouse.setHandler(warehouse.getUpdateuid());
		warehouse.setSupplierid(product.getSupplierid());
		warehouse.setRank(Constants.ROLE_RANK_OPERATOR);
		warehouse.setStatus(Constants.FINANCE_STATUS_PAY_INVOICE);
		
		if(warehouse.getQuantity().floatValue() >= 0) {
			//库存数量大于0，则单价为0
			warehouse.setRes04("0.000000");
			//res07
			warehouse.setRes07("" + warehouse.getQuantity());
		} else {
			//数量小于0，计算成本价
			WarehouseDto cbj = warehouseDao.calcCurrentCbjByProductid(warehouse.getProductid(), warehouse.getQuantity());
			if(cbj != null) {
				warehouse.setRes04(cbj.getRes04());
			}
		}
		
		warehouseDao.insertWarehouse(warehouse);
		return warehouseno;
	}

	@Override
	public void updateWarehouse(WarehouseDto warehouse) {
		warehouseDao.updateWarehouse(warehouse);
	}
	
	@Override
	public void updateRefundWarehouse(WarehouseDto warehouse) {
//		if(warehouse.getQuantity().floatValue() >= 0) {
//			//库存数量大于0，则单价为0
//			warehouse.setRes04("0.000000");
//		} else {
//			//数量小于0，计算成本价
//			warehouseDao.calcCurrentCbjByProductid(warehouse.getProductid(), warehouse.getQuantity());
//		}
		warehouseDao.updateWarehouse(warehouse);
	}

	public WarehouseDao getWarehouseDao() {
		return warehouseDao;
	}

	public void setWarehouseDao(WarehouseDao warehouseDao) {
		this.warehouseDao = warehouseDao;
	}

	public WarehouserptDao getWarehouserptDao() {
		return warehouserptDao;
	}

	public void setWarehouserptDao(WarehouserptDao warehouserptDao) {
		this.warehouserptDao = warehouserptDao;
	}

	public SupplierDao getSupplierDao() {
		return supplierDao;
	}

	public void setSupplierDao(SupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

	public PurchaseDao getPurchaseDao() {
		return purchaseDao;
	}

	public void setPurchaseDao(PurchaseDao purchaseDao) {
		this.purchaseDao = purchaseDao;
	}

	public PurchaseItemDao getPurchaseItemDao() {
		return purchaseItemDao;
	}

	public void setPurchaseItemDao(PurchaseItemDao purchaseItemDao) {
		this.purchaseItemDao = purchaseItemDao;
	}

	public SalesDao getSalesDao() {
		return salesDao;
	}

	public void setSalesDao(SalesDao salesDao) {
		this.salesDao = salesDao;
	}

	public SalesItemDao getSalesItemDao() {
		return salesItemDao;
	}

	public void setSalesItemDao(SalesItemDao salesItemDao) {
		this.salesItemDao = salesItemDao;
	}

	public FinanceDao getFinanceDao() {
		return financeDao;
	}

	public void setFinanceDao(FinanceDao financeDao) {
		this.financeDao = financeDao;
	}

	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	@Override
	public Page queryWarehouseDetailByPage(String parentid, String keyword,
			String warehousetype, String warehouseno, String theme1,
			String productid, String tradename, String typeno, String color,
			String warehousename, String zerodisplay, String totalQtyDisplay, String expiredDisplay, String whFlg, Page page) {
		keyword = StringUtil.replaceDatabaseKeyword_mysql(keyword);
		tradename = StringUtil.replaceDatabaseKeyword_mysql(tradename);
		typeno = StringUtil.replaceDatabaseKeyword_mysql(typeno);
		warehousename = StringUtil.replaceDatabaseKeyword_mysql(warehousename);
		List<WarehouseDetailDto> list = null;
		int totalCount=0;
		
		if (!whFlg.equals("1")){
			//查询总记录数
			totalCount = warehouseDao.queryWarehouseDetailCountByPage(parentid, keyword, 
					warehousetype, warehouseno, theme1, productid, tradename, typeno, color, warehousename, zerodisplay);
			page.setTotalCount(totalCount);
			if(totalCount % page.getPageSize() > 0) {
				page.setTotalPage(totalCount / page.getPageSize() + 1);
			} else {
				page.setTotalPage(totalCount / page.getPageSize());
			}
			//翻页查询记录
			list = warehouseDao.queryWarehouseDetailByPage(parentid, keyword,
					warehousetype, warehouseno, theme1, productid, tradename, typeno, color, warehousename, zerodisplay,
					page.getStartIndex() * page.getPageSize(), page.getPageSize());			
		}else {
			//SZA 
			totalCount = warehouseDao.queryWarehouseDetailCountByPageWH(parentid, keyword, 
					warehousetype, warehouseno, theme1, productid, tradename, typeno, color, warehousename, zerodisplay);
			page.setTotalCount(totalCount);
			if(totalCount % page.getPageSize() > 0) {
				page.setTotalPage(totalCount / page.getPageSize() + 1);
			} else {
				page.setTotalPage(totalCount / page.getPageSize());
			}
			//翻页查询记录
			list = warehouseDao.queryWarehouseDetailByPageWH(parentid, keyword,
					warehousetype, warehouseno, theme1, productid, tradename, typeno, color, warehousename, zerodisplay,
					page.getStartIndex() * page.getPageSize(), page.getPageSize());			
		}
		
		if(list != null && list.size() > 0) {
			for(WarehouseDetailDto warehouseDetailDto : list) {
				
				// 20220422 Pei for SZA start --->
//				int sentQty = warehouseDao.queryWarehouseSendQty(warehousetype, warehouseDetailDto.getProductid(), "A" );
//				BigDecimal qtyOtherSend = new BigDecimal(0);
//				if (sentQty != 0)
//					qtyOtherSend = (new BigDecimal(sentQty)).multiply(new BigDecimal(-1));
//				warehouseDetailDto.setQtyOtherSend(qtyOtherSend);

				Double szaWarehouseQty = warehouseSZADao.queryAmountByProductId(warehouseDetailDto.getProductid());
				BigDecimal qtySZAWarehouse = new BigDecimal(0);
				if (szaWarehouseQty != null)
					qtySZAWarehouse = new BigDecimal(szaWarehouseQty);
				warehouseDetailDto.setQtySZAWarehouse(qtySZAWarehouse.setScale(2));

				// 20220422 Pei for SZA  <--- end
				
				//默认为0
				warehouseDetailDto.setDiffquantity_sz(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP));
				warehouseDetailDto.setExp3M_quantitys(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP));
				//查询深圳数据， 以及根据需要查逾期3个月订单未发货数据
				if(StringUtil.isNotBlank(warehouseDetailDto.getProductid())) {
					//先根据产品ID查询上海这边的产品数据
					ProductDto productDto = productDao.queryProductByID(warehouseDetailDto.getProductid());
					if(productDto != null) {
						//根据产品特征查询深圳产品记录
						ProductDto productSZDto = warehouseSZDao.queryProductByLogicId(productDto.getFieldno(), productDto.getTradename(), productDto.getTypeno(),
								productDto.getColor(), productDto.getItem10(), productDto.getPackaging(), productDto.getUnit(), productDto.getMakearea());
						
						if(productSZDto != null) {
							WarehouseDetailDto szdetail = warehouseSZDao.queryWarehouseSZDetail(parentid, keyword, warehousetype,
									warehouseno, theme1, "" + productSZDto.getId(), tradename, typeno, color, warehousename, zerodisplay);
							if(szdetail != null) {
								//System.out.println("szdetail diffquantity=[" + szdetail.getDiffquantity() + "]");
								warehouseDetailDto.setDiffquantity_sz(szdetail.getDiffquantity());
							} else {
								//System.out.println("szdetail is null,SZproductid=[" + productSZDto.getId() + "]");
							}
						} else {
							//System.out.println("sz product is null....Shang hai productid=" + warehouseDetailDto.getProductid());
						}
						// 需要显示逾期3个月的未发货订单数量
						if (expiredDisplay.equals("1")){
//							System.out.println("XXX 3M productid=" + warehouseDetailDto.getProductid());
							List<SalesItemDto> explist = salesItemDao.query3MUnSndItemsByProductId(productDto.getId().toString());
							BigDecimal expiredRemainQuantity = new BigDecimal(0);
							if(explist != null && explist.size() > 0) {
							//	System.out.println("XXX 3M explist.size()=" + explist.size());
								for(SalesItemDto item : explist){
									expiredRemainQuantity = expiredRemainQuantity.add(item.getRemainquantity());
							//		System.out.println("XXX 3M item.getRemainquantity()=" + item.getRemainquantity());
								} 
								warehouseDetailDto.setExp3M_quantitys(expiredRemainQuantity);								
							//	System.out.println("XXX 3M expiredRemainQuantity=" + expiredRemainQuantity);
							}
						}
					} else {
						//没有产品记录，则数据问题，不做任何操作
					}
				}
			}
		}
		//翻页查询记录
		if (totalQtyDisplay!= null && totalQtyDisplay.equals("1")){
			List<WarehouseDetailDto> list2 = warehouseDao.queryWarehouseDetail(parentid, keyword,
					warehousetype, warehouseno, theme1, productid, tradename, typeno, color, warehousename, zerodisplay);			
			if(list2 != null && list2.size() > 0) {
				for(WarehouseDetailDto warehouseDetailDto2 : list2) {
					//默认为0
					if (warehouseDetailDto2.getQuantity()!=null){
						totalQty = totalQty.add(warehouseDetailDto2.getQuantity());	
					}
				}	
			}
		}
		page.setItems(list);
		return page;
	}

	@Override
	public List<WarehouseCheckDto> queryWarehouseCheckToExcel(String parentid,
			String warehousetype, String warehouseno, String theme1,
			String productid, String tradename, String typeno, String color,
			String warehousename) {
		List<WarehouseCheckDto> list = warehouseDao.queryWarehouseCheckToExcel(parentid, warehousetype, warehouseno, theme1, productid, tradename, typeno, color, warehousename);
		for(WarehouseCheckDto wdt: list) {
			List<PositionDto> listPosition = positionDao.queryPositionByLogicId("", wdt.getProductid(), "");
			if(listPosition != null && listPosition.size() > 0) {
				PositionDto position = listPosition.get(0);
				wdt.setRes02(position.getRes01());
			} else {
				wdt.setWarehouseposition("");
			}	
		}
		return list;
	}
	
	public void getSalesItemPrimecost(SalesItemDto item) {	
		WarehouseDto warehouseDto = null;
		BigDecimal rate = new BigDecimal(1);
		BigDecimal primecost= new BigDecimal(0);
		//税率
		List<Dict01Dto> listRate = dict01Dao.queryDict01ByFieldcode(Constants.DICT_RATE, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
		if(listRate != null && listRate.size() > 0) {
			rate = rate.add(new BigDecimal(listRate.get(0).getCode()));
		}
		
		//计算成本单价
		if (item != null){
			warehouseDto = warehouseDao.queryPrimecostByProductId(item.getProductid());
			primecost = WarehouseUtil.calcPrimecost(warehouseDto, rate);
		}	
		item.setPrimecost(primecost);					
	}	
		
	@Override
	public void loadWarehouseCheck(List<WarehouseCheckDto> list){
		warehouseDao.loadWarehouseCheck(list);		
	}
	
	@Override
	public List<WarehouseOkDto> queryProductBookByProductid(String productid) {
		return warehouseDao.queryProductBookByProductid(productid);
	}

	public PositionDao getPositionDao() {
		return positionDao;
	}

	public void setPositionDao(PositionDao positionDao) {
		this.positionDao = positionDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public Dict01Dao getDict01Dao() {
		return dict01Dao;
	}

	public void setDict01Dao(Dict01Dao dict01Dao) {
		this.dict01Dao = dict01Dao;
	}

	public CustomerOnlineDao getCustomerOnlineDao() {
		return customerOnlineDao;
	}

	public void setCustomerOnlineDao(CustomerOnlineDao customerOnlineDao) {
		this.customerOnlineDao = customerOnlineDao;
	}

	public OrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public WarehouseSZDao getWarehouseSZDao() {
		return warehouseSZDao;
	}

	public void setWarehouseSZDao(WarehouseSZDao warehouseSZDao) {
		this.warehouseSZDao = warehouseSZDao;
	}

	public BarcodeInfoDao getBarcodeInfoDao() {
		return barcodeInfoDao;
	}

	public void setBarcodeInfoDao(BarcodeInfoDao barcodeInfoDao) {
		this.barcodeInfoDao = barcodeInfoDao;
	}

	public ProductBarcodeDao getProductBarcodeDao() {
		return productBarcodeDao;
	}

	public void setProductBarcodeDao(ProductBarcodeDao productBarcodeDao) {
		this.productBarcodeDao = productBarcodeDao;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	public SalesHisDao getSalesHisDao() {
		return salesHisDao;
	}

	public void setSalesHisDao(SalesHisDao salesHisDao) {
		this.salesHisDao = salesHisDao;
	}

	public SalesItemHisDao getSalesItemHisDao() {
		return salesItemHisDao;
	}

	public void setSalesItemHisDao(SalesItemHisDao salesItemHisDao) {
		this.salesItemHisDao = salesItemHisDao;
	}

	public BigDecimal getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(BigDecimal totalQty) {
		this.totalQty = totalQty;
	}

	public WarehouseSZADao getWarehouseSZADao() {
		return warehouseSZADao;
	}

	public void setWarehouseSZADao(WarehouseSZADao warehouseSZADao) {
		this.warehouseSZADao = warehouseSZADao;
	}

	public WarehouserptSZADao getWarehouserptSZADao() {
		return warehouserptSZADao;
	}

	public void setWarehouserptSZADao(WarehouserptSZADao warehouserptSZADao) {
		this.warehouserptSZADao = warehouserptSZADao;
	}
	
}
