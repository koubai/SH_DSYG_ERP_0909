package com.cn.dsyg.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.cn.common.action.BaseAction;
import com.cn.common.util.Constants;
import com.cn.common.util.DateUtil;
import com.cn.common.util.Page;
import com.cn.common.util.PropertiesConfig;
import com.cn.dsyg.dto.Dict01Dto;
import com.cn.dsyg.dto.InOutStockDto;
import com.cn.dsyg.service.Dict01Service;
import com.cn.dsyg.service.WarehouseService;

/**
 * 产品出入库查询Action
 * @name InOutStockAction.java
 * @author Frank
 * @time 2016-12-14下午10:28:01
 * @version 1.0
 */
public class InOutStockAction extends BaseAction {

	private static final long serialVersionUID = 5219503355358302574L;
	private static final Logger log = LogManager.getLogger(InOutStockAction.class);
	private Dict01Service dict01Service;
	private WarehouseService warehouseService;
	
	private List<InOutStockDto> listInOutStock;
	//页码
	private int startIndex;
	//翻页page
	private Page page;
	//一页显示数据条数
	private Integer intPageSize;
	
	//采购主题
	private List<Dict01Dto> goodsList;
	//颜色
	private List<Dict01Dto> colorList;
	//单位
	private List<Dict01Dto> unitList;
	//产地
	private List<Dict01Dto> makeareaList;
	
	//查询条件
	private String strStartdate;
	private String strEnddate;
	//类型
	private String strTheme1;
	//品名
	private String strTradename;
	//包装
	private String strItem10;
	//关键字
	private String strKeyword;
	//日期范围
	private String strDateType;
	//合计数量
	private InOutStockDto sumInOutStockDto;
	
	//明细
	private String strProductid;
	private InOutStockDto inOutStockDetail;
	//入库明细
	private List<InOutStockDto> inDetailList;
	//合计
	private BigDecimal sumInQuantity;
	
	//出库明细
	private List<InOutStockDto> outDetailList;
	//合计
	private BigDecimal sumOutQuantity;
	
	
	/**
	 * 显示明细页面
	 * @return
	 */
	public String showInOutStockDetailAction() {
		try {
			this.clearMessages();
			//初期化字典数据
			initDictList();
			
			sumInQuantity = new BigDecimal(0);
			sumOutQuantity = new BigDecimal(0);
			
			//入库明细
			inDetailList = warehouseService.queryInOutStockDetail(strProductid, "1", strStartdate, strEnddate);
			if(inDetailList != null && inDetailList.size() > 0) {
				for(InOutStockDto in : inDetailList) {
					if(in.getQuantity() != null) {
						sumInQuantity = sumInQuantity.add(in.getQuantity());
					}
				}
			}
			
			//出库明细
			outDetailList = warehouseService.queryInOutStockDetail(strProductid, "2", strStartdate, strEnddate);
			if(outDetailList != null && outDetailList.size() > 0) {
				for(InOutStockDto out : outDetailList) {
					if(out.getQuantity() != null) {
						sumOutQuantity = sumOutQuantity.add(out.getQuantity());
					}
				}
			}
			
			//按产品ID查询总入出库数量
			inOutStockDetail = warehouseService.querySumInOutStock(strStartdate,
							strEnddate, "", "", "", "", strProductid);
			
		} catch(Exception e) {
			log.error("showInOutStockDetailAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 显示产品出入库查询页面
	 * @return
	 */
	public String showInOutStockAction() {
		try {
			this.clearMessages();
			//默认最近一个月
			Date date = new Date();
			strEnddate = DateUtil.dateToShortStr(date);
			strStartdate = DateUtil.dateToShortStr(DateUtil.addMonths(date, -1));
			
			strProductid = "";
			inOutStockDetail = new InOutStockDto();
			
			sumInOutStockDto = new InOutStockDto();
			listInOutStock = new ArrayList<InOutStockDto>();
			strDateType = "";
			strTheme1 = "";
			strTradename = "";
			strItem10 = "";
			strKeyword = "";
			startIndex = 0;
			//默认10条
			intPageSize = 10;
			page = new Page(intPageSize);
			queryData();
		} catch(Exception e) {
			log.error("showInOutStockAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询产品出入库数据
	 * @return
	 */
	public String queryInOutStockAction() {
		try {
			this.clearMessages();
			startIndex = 0;
			//默认10条
			if(intPageSize == null) {
				intPageSize = 10;
			}
			page = new Page(intPageSize);
			queryData();
		} catch(Exception e) {
			log.error("queryInOutStockAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询产品出入库数据（按日期）
	 * @return
	 */
	public String queryInOutStockByDateAction() {
		try {
			this.clearMessages();
			Date date = new Date();
			if("1".equals(strDateType)) {
				//近3个月
				strEnddate = DateUtil.dateToShortStr(date);
				strStartdate = DateUtil.dateToShortStr(DateUtil.addMonths(date, -3));
			} else if("2".equals(strDateType)) {
				//近6个月
				strEnddate = DateUtil.dateToShortStr(date);
				strStartdate = DateUtil.dateToShortStr(DateUtil.addMonths(date, -6));
			} else if("3".equals(strDateType)) {
				//近12个月
				strEnddate = DateUtil.dateToShortStr(date);
				strStartdate = DateUtil.dateToShortStr(DateUtil.addMonths(date, -12));
			} else {
				//不设定日期
			}
			startIndex = 0;
			//默认10条
			if(intPageSize == null) {
				intPageSize = 10;
			}
			page = new Page(intPageSize);
			queryData();
		} catch(Exception e) {
			log.error("queryInOutStockByDateAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 翻页产品出入库数据
	 * @return
	 */
	public String turnInOutStockAction() {
		try {
			this.clearMessages();
			queryData();
		} catch(Exception e) {
			log.error("turnInOutStockAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 初期化字典数据
	 */
	private void initDictList() {
		//税率
		List<Dict01Dto> listRate = dict01Service.queryDict01ByFieldcode(Constants.DICT_RATE, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
		if(listRate != null && listRate.size() > 0) {
			common_rate = listRate.get(0).getCode();
		}
		//采购主题
		goodsList = dict01Service.queryDict01ByFieldcode(Constants.DICT_GOODS_TYPE, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
		//单位
		unitList = dict01Service.queryDict01ByFieldcode(Constants.DICT_UNIT_TYPE, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
		//产地
		makeareaList = dict01Service.queryDict01ByFieldcode(Constants.DICT_MAKEAREA, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
		//颜色
		colorList = dict01Service.queryDict01ByFieldcode(Constants.DICT_COLOR_TYPE, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
	}
	
	/**
	 * 数据查询
	 */
	@SuppressWarnings("unchecked")
	private void queryData() {
		if(page == null) {
			page = new Page(intPageSize);
		}
		//初期化字典数据
		initDictList();
		
		//查询总数量
		sumInOutStockDto = warehouseService.querySumInOutStock(strStartdate,
				strEnddate, strTheme1, strTradename, strItem10, strKeyword, strProductid);
		
		//翻页查询所有入库汇总记录
		this.page.setStartIndex(startIndex);
		page = warehouseService.queryInOutStockByPage(strStartdate,
				strEnddate, strTheme1, strTradename, strItem10, strKeyword, page);
		listInOutStock = (List<InOutStockDto>) page.getItems();
		this.setStartIndex(page.getStartIndex());
	}

	public Dict01Service getDict01Service() {
		return dict01Service;
	}

	public void setDict01Service(Dict01Service dict01Service) {
		this.dict01Service = dict01Service;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Integer getIntPageSize() {
		return intPageSize;
	}

	public void setIntPageSize(Integer intPageSize) {
		this.intPageSize = intPageSize;
	}

	public List<Dict01Dto> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<Dict01Dto> goodsList) {
		this.goodsList = goodsList;
	}

	public List<Dict01Dto> getColorList() {
		return colorList;
	}

	public void setColorList(List<Dict01Dto> colorList) {
		this.colorList = colorList;
	}

	public List<Dict01Dto> getUnitList() {
		return unitList;
	}

	public void setUnitList(List<Dict01Dto> unitList) {
		this.unitList = unitList;
	}

	public List<Dict01Dto> getMakeareaList() {
		return makeareaList;
	}

	public void setMakeareaList(List<Dict01Dto> makeareaList) {
		this.makeareaList = makeareaList;
	}

	public String getStrTheme1() {
		return strTheme1;
	}

	public void setStrTheme1(String strTheme1) {
		this.strTheme1 = strTheme1;
	}

	public String getStrTradename() {
		return strTradename;
	}

	public void setStrTradename(String strTradename) {
		this.strTradename = strTradename;
	}

	public String getStrKeyword() {
		return strKeyword;
	}

	public void setStrKeyword(String strKeyword) {
		this.strKeyword = strKeyword;
	}

	public WarehouseService getWarehouseService() {
		return warehouseService;
	}

	public void setWarehouseService(WarehouseService warehouseService) {
		this.warehouseService = warehouseService;
	}

	public String getStrItem10() {
		return strItem10;
	}

	public void setStrItem10(String strItem10) {
		this.strItem10 = strItem10;
	}

	public String getStrStartdate() {
		return strStartdate;
	}

	public void setStrStartdate(String strStartdate) {
		this.strStartdate = strStartdate;
	}

	public String getStrEnddate() {
		return strEnddate;
	}

	public void setStrEnddate(String strEnddate) {
		this.strEnddate = strEnddate;
	}

	public List<InOutStockDto> getListInOutStock() {
		return listInOutStock;
	}

	public void setListInOutStock(List<InOutStockDto> listInOutStock) {
		this.listInOutStock = listInOutStock;
	}

	public String getStrDateType() {
		return strDateType;
	}

	public void setStrDateType(String strDateType) {
		this.strDateType = strDateType;
	}

	public InOutStockDto getSumInOutStockDto() {
		return sumInOutStockDto;
	}

	public void setSumInOutStockDto(InOutStockDto sumInOutStockDto) {
		this.sumInOutStockDto = sumInOutStockDto;
	}

	public InOutStockDto getInOutStockDetail() {
		return inOutStockDetail;
	}

	public void setInOutStockDetail(InOutStockDto inOutStockDetail) {
		this.inOutStockDetail = inOutStockDetail;
	}

	public String getStrProductid() {
		return strProductid;
	}

	public void setStrProductid(String strProductid) {
		this.strProductid = strProductid;
	}

	public List<InOutStockDto> getInDetailList() {
		return inDetailList;
	}

	public void setInDetailList(List<InOutStockDto> inDetailList) {
		this.inDetailList = inDetailList;
	}

	public List<InOutStockDto> getOutDetailList() {
		return outDetailList;
	}

	public void setOutDetailList(List<InOutStockDto> outDetailList) {
		this.outDetailList = outDetailList;
	}

	public BigDecimal getSumInQuantity() {
		return sumInQuantity;
	}

	public void setSumInQuantity(BigDecimal sumInQuantity) {
		this.sumInQuantity = sumInQuantity;
	}

	public BigDecimal getSumOutQuantity() {
		return sumOutQuantity;
	}

	public void setSumOutQuantity(BigDecimal sumOutQuantity) {
		this.sumOutQuantity = sumOutQuantity;
	}
}
