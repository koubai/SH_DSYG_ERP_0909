package com.cn.dsyg.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.cn.common.action.BaseAction;
import com.cn.common.util.Constants;
import com.cn.common.util.DateUtil;
import com.cn.common.util.Page;
import com.cn.common.util.PropertiesConfig;
import com.cn.common.util.StringUtil;
import com.cn.dsyg.dto.Dict01Dto;
import com.cn.dsyg.dto.SalesStatisticsDto;
import com.cn.dsyg.service.Dict01Service;
import com.cn.dsyg.service.ProductService;
import com.cn.dsyg.service.WarehouseService;

/**
 * 产品销售统计Action
 * @name SalesStatisticsAction.java
 * @author liu
 * @time 2017-11-10下午10:28:01
 * @version 1.0
 */
public class SalesStatisticsAction extends BaseAction {

	private static final long serialVersionUID = 9177270363971042801L;
	private static final Logger log = LogManager.getLogger(SalesStatisticsAction.class);
	private Dict01Service dict01Service;
	private WarehouseService warehouseService;
	private ProductService productService;
	
	private List<SalesStatisticsDto> listSalesStatistics;
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
	//年份
	private List<String> yearArray;
	//月份
	private List<String> monthArray;
	//显示年份
	private String yearOnline;
	//合计数量
	private SalesStatisticsDto sumSalesStatisticsDto;
	private String strProductid;

	/**
	 * 显示产品销售统计页面
	 * @return
	 */
	public String showSalesStatisticsAction() {
		try {
			this.clearMessages();
			//默认最近一个月
			Date date = new Date();
			strEnddate = DateUtil.dateToShortStr(date);
			strStartdate = DateUtil.dateToShortStr(DateUtil.addMonths(date, -1));
			if(StringUtil.isNotBlank(strStartdate)) {
				yearOnline = strStartdate.substring(0, 4);
			}
			monthArray = new ArrayList<String>();
			yearArray = new ArrayList<String>();
			sumSalesStatisticsDto = new SalesStatisticsDto();
			listSalesStatistics = new ArrayList<SalesStatisticsDto>();
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
			log.error("showSalesStatisticsAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询产品销售统计
	 * @return
	 */
	public String querySalesStatisticsAction() {
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
			log.error("querySalesStatisticsAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询产品销售统计（按日期）
	 * @return
	 */
	public String querySalesStatisticsByDateAction() {
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
			log.error("querySalesStatisticsByDateAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 翻页产品销售统计数据
	 * @return
	 */
	public String turnSalesStatisticsAction() {
		try {
			this.clearMessages();
			queryData();
		} catch(Exception e) {
			log.error("turnSalesStatisticsAction error:" + e);
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
		
		//取得年份
		yearArray = new ArrayList<String>();
		yearArray = get_Y_Data(strStartdate, strEnddate);
		Collections.sort(yearArray);
		
		if(StringUtil.isBlank(yearOnline)) {
			yearOnline = strStartdate.substring(0, 4);
		}
		String startdate = strStartdate;
		String enddate = strEnddate;
		//重新取得日期区间
		if(!strEnddate.substring(0, 4).equals(strStartdate.substring(0, 4))) {
			//开始
			if(Integer.parseInt(yearOnline) > Integer.parseInt(strStartdate.substring(0, 4))) {
				startdate = yearOnline + "-01-01";
			}
			
			//结束
			if(Integer.parseInt(yearOnline) < Integer.parseInt(strEnddate.substring(0, 4))) {
				enddate = yearOnline + "-12-31";
			}
		}
//		System.out.println("startdate:" + startdate);
//		System.out.println("enddate:" + enddate);
//		System.out.println("yearOnline:" + yearOnline);
		
		//取得月份
		monthArray = new ArrayList<String>();
		monthArray = get_X_Data(startdate, enddate);
		Collections.sort(monthArray);
		
		//查询总数量
		sumSalesStatisticsDto = warehouseService.querySumSalesStatistics(startdate,
				enddate, strTheme1, strTradename, strItem10, strKeyword, strProductid);
		
		//翻页查询产品销售统计数据
		this.page.setStartIndex(startIndex);
		page = warehouseService.querySalesStatisticsByPage(startdate,
				enddate, strTheme1, strTradename, strItem10, strKeyword, page);
		listSalesStatistics = (List<SalesStatisticsDto>) page.getItems();
		this.setStartIndex(page.getStartIndex());
	}
	
	private static List<String> get_X_Data(String d1, String d2) {
   		List<String> result = new ArrayList<String>();
   		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月
   		Calendar min = Calendar.getInstance();
   		Calendar max = Calendar.getInstance();

   	 	try {
			min.setTime(sdf.parse(d1));
	   		max.setTime(sdf.parse(d2));
	   		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

   		Calendar curr = min;
   		while (curr.before(max)) {
   			result.add(sdf.format(curr.getTime()));
   			curr.add(Calendar.MONTH, 1);
   		}
   		return result;
	}
	
	private static List<String> get_Y_Data(String d1, String d2) {
   		List<String> result = new ArrayList<String>();
   		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");//格式化为年
   		Calendar min = Calendar.getInstance();
   		Calendar max = Calendar.getInstance();

   	 	try {
			min.setTime(sdf.parse(d1));
	   		max.setTime(sdf.parse(d2));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

   		Calendar curr = min;
   		while (curr.before(max)) {
   			result.add(sdf.format(curr.getTime()));
   			curr.add(Calendar.YEAR, 1);
   		}
   		result.add(sdf.format(max.getTime()));
   		return result;
	}
	public static void main(String args[]) {
   		List<String> result = new ArrayList<String>();
   		result = get_Y_Data("1999-10-10","2016-8-10");
		Collections.sort(result);
   		for(int i=0;i<result.size();i++) {
   			System.out.println("year:" + result.get(i));
   		}
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

	public String getStrDateType() {
		return strDateType;
	}

	public void setStrDateType(String strDateType) {
		this.strDateType = strDateType;
	}

	public String getStrProductid() {
		return strProductid;
	}

	public void setStrProductid(String strProductid) {
		this.strProductid = strProductid;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public List<SalesStatisticsDto> getListSalesStatistics() {
		return listSalesStatistics;
	}

	public void setListSalesStatistics(List<SalesStatisticsDto> listSalesStatistics) {
		this.listSalesStatistics = listSalesStatistics;
	}

	public SalesStatisticsDto getSumSalesStatisticsDto() {
		return sumSalesStatisticsDto;
	}

	public void setSumSalesStatisticsDto(SalesStatisticsDto sumSalesStatisticsDto) {
		this.sumSalesStatisticsDto = sumSalesStatisticsDto;
	}

	public List<String> getMonthArray() {
		return monthArray;
	}

	public void setMonthArray(List<String> monthArray) {
		this.monthArray = monthArray;
	}

	public List<String> getYearArray() {
		return yearArray;
	}

	public void setYearArray(List<String> yearArray) {
		this.yearArray = yearArray;
	}

	public String getYearOnline() {
		return yearOnline;
	}

	public void setYearOnline(String yearOnline) {
		this.yearOnline = yearOnline;
	}
}
