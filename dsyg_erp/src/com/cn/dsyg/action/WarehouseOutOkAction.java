package com.cn.dsyg.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cn.common.action.BaseAction;
import com.cn.common.util.Constants;
import com.cn.common.util.Page;
import com.cn.common.util.PropertiesConfig;
import com.cn.dsyg.dto.AjaxResultDto;
import com.cn.dsyg.dto.Dict01Dto;
import com.cn.dsyg.dto.WarehouseInOutOkDto;
import com.cn.dsyg.dto.WarehouserptDto;
import com.cn.dsyg.service.Dict01Service;
import com.cn.dsyg.service.WarehouseService;
import com.cn.dsyg.service.WarehouserptService;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONArray;

/**
 * @name 预出库确认Action
 * @author Frank
 * @time 2015-6-4下午10:09:02
 * @version 1.0
 */
public class WarehouseOutOkAction extends BaseAction {

	private static final long serialVersionUID = 4049661437562429432L;

	private static final Logger log = LogManager.getLogger(WarehouseOutOkAction.class);
	
	private WarehouseService warehouseService;
	private WarehouserptService warehouserptService;
	private Dict01Service dict01Service;
	
	//页码
	private int startIndex;
	//翻页page
	private Page page;
	//一页显示数据条数
	private Integer intPageSize;
	//库存未确认数据集集列表
	private List<WarehouseInOutOkDto> warehouseOutOkList;
	
	//采购主题
	private List<Dict01Dto> goodsList;
	//颜色
	private List<Dict01Dto> colorList;
	//单位
	private List<Dict01Dto> unitList;
	//产地
	private List<Dict01Dto> makeareaList;
	
	//出库确认
	private String strOkIds;
	private String strSuppliername;
	
	//查询条件：网上、内部
	private String strFrom;
	
	//条形码出库单ID
	private String barcodeOutId;
	//条形码扫码入库
	private String strScanBarcodeInfo;
	
	//仓库编号  上海深圳基本仓库不标注， 特殊才标注 如 SZ：深圳SZ   B：深圳B
	private String strWarehouseNo;
	
	/**
	 * 显示刷新出库单利润率页面
	 * @return
	 */
	public String showRefreshRptPrimeRateAction() {
		try {
			this.clearMessages();
		} catch(Exception e) {
			log.error("showRefreshRptPrimeRateAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 刷新出库单利润率
	 * @return
	 */
	public String refreshRptPrimeRateAction() {
		try {
			this.clearMessages();
			warehouserptService.calcRptPrimeRate();
		} catch(Exception e) {
			log.error("refreshRptPrimeRateAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 条形码出库前验证
	 * @return
	 */
	public String barcodeWarehouseOutCheckAction() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		AjaxResultDto ajaxResult = new AjaxResultDto();
		try {
			this.clearMessages();
			WarehouserptDto rpt = warehouserptService.queryWarehouserptByID(barcodeOutId);
			if(rpt != null) {
				if("1".equals(rpt.getRes02())) {
					ajaxResult.setCode(99);
					ajaxResult.setMsg("单据已入库！");
				} else {
					//当前操作用户ID
					String username = (String) ActionContext.getContext().getSession().get(Constants.SESSION_USER_ID);
					ajaxResult = warehouseService.barcodeWarehouseInOutCheck(barcodeOutId, strScanBarcodeInfo, Constants.WAREHOUSERPT_TYPE_OUT, username, true);
				}
			} else {
				ajaxResult.setCode(1);
				ajaxResult.setMsg("单据数据不存在！");
			}
		} catch(Exception e) {
			ajaxResult.setCode(-1);
			ajaxResult.setMsg("系统异常，请联系管理员！");
			log.error("barcodeWarehouseOutCheckAction error:" + e);
		}
		out = response.getWriter();
		String result = JSONArray.fromObject(ajaxResult).toString();
		result = result.substring(1, result.length() - 1);
		log.info(result);
		out.write(result);
		out.flush();
		return null;
	}
	
	/**
	 * 条形码出库
	 * @return
	 * @throws Exception 
	 */
	public String barcodeWarehouseOutAction() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		AjaxResultDto ajaxResult = new AjaxResultDto();
		try {
			this.clearMessages();
			WarehouserptDto rpt = warehouserptService.queryWarehouserptByID(barcodeOutId);
			if(rpt != null) {
				if("1".equals(rpt.getRes02())) {
					ajaxResult.setCode(99);
					ajaxResult.setMsg("单据已出库！");
				} else {
					//当前操作用户ID
					String username = (String) ActionContext.getContext().getSession().get(Constants.SESSION_USER_ID);
					ajaxResult = warehouseService.barcodeWarehouseInOut(barcodeOutId, strScanBarcodeInfo, Constants.WAREHOUSERPT_TYPE_OUT, username);
				}
			} else {
				ajaxResult.setCode(1);
				ajaxResult.setMsg("单据数据不存在！");
			}
		} catch(Exception e) {
			ajaxResult.setCode(-1);
			ajaxResult.setMsg("系统异常，请联系管理员！");
			log.error("barcodeWarehouseOutAction error:" + e);
		}
		out = response.getWriter();
		String result = JSONArray.fromObject(ajaxResult).toString();
		result = result.substring(1, result.length() - 1);
		log.info(result);
		out.write(result);
		out.flush();
		return null;
	}
	
	/**
	 * 显示预出库确认页面
	 * @return
	 */
	public String showWarehouseOutOkAction() {
		try {
			this.clearMessages();
			//页面数据初期化
			strSuppliername = "";
			startIndex = 0;
			//默认10条
			intPageSize = 10;
			strFrom = "";
			page = new Page(intPageSize);
			warehouseOutOkList = new ArrayList<WarehouseInOutOkDto>();
			strOkIds = "";
			
			queryData();
		} catch(Exception e) {
			log.error("showWarehouseOutOkAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询出库数据
	 * @return
	 */
	public String queryWarehouseOutOkAction() {
		try {
			this.clearMessages();
			//页面数据初期化
			startIndex = 0;
			//默认10条
			if(intPageSize == null) {
				intPageSize = 10;
			}
			page = new Page(intPageSize);
			queryData();
		} catch(Exception e) {
			log.error("queryWarehouseOutOkAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 翻页
	 * @return
	 */
	public String turnWarehouseOutOkAction() {
		try {
			this.clearMessages();
			//页面数据初期化
			queryData();
		} catch(Exception e) {
			log.error("turnWarehouseOutOkAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 出库确认
	 * @return
	 */
	public String warehouseOutOkAction() {
		try {
			this.clearMessages();
			//当前操作用户ID
			String username = (String) ActionContext.getContext().getSession().get(Constants.SESSION_USER_ID);
			warehouseService.warehouseOutOk(strOkIds, username, strWarehouseNo);
			
			this.addActionMessage("出库单生成成功！");
			//刷新页面数据
			queryData();
		} catch(RuntimeException ex) {
			this.addActionMessage(ex.getMessage());
		} catch(Exception e) {
			log.error("warehouseOutOkAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 数据查询
	 */
	@SuppressWarnings("unchecked")
	private void queryData() {
		if(page == null) {
			page = new Page(intPageSize);
		}
		initDictList();
		//翻页查询所有预出库待确认数据
		this.page.setStartIndex(startIndex);
		if("1".equals(strFrom)) {
			//内部ERP
			page = warehouseService.queryWarehouseOutOk1ByPage(strSuppliername, "", "", "", "", "", "" + Constants.WAREHOUSE_STATUS_NEW, page);
		} else if("2".equals(strFrom)) {
			//online
			page = warehouseService.queryWarehouseOutOk2ByPage(strSuppliername, "", "", "", "", "", "" + Constants.WAREHOUSE_STATUS_NEW, page);
		} else {
			//all
			page = warehouseService.queryWarehouseOutOkByPage(strSuppliername, "", "", "", "", "", "" + Constants.WAREHOUSE_STATUS_NEW, page);
		}
		warehouseOutOkList = (List<WarehouseInOutOkDto>) page.getItems();
		this.setStartIndex(page.getStartIndex());
	}
	
	/**
	 * 初期化字典数据
	 */
	private void initDictList() {
		barcodeOutId = "";
		strScanBarcodeInfo = "";
		//采购主题
		goodsList = dict01Service.queryDict01ByFieldcode(Constants.DICT_GOODS_TYPE, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
		//单位
		unitList = dict01Service.queryDict01ByFieldcode(Constants.DICT_UNIT_TYPE, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
		//产地
		makeareaList = dict01Service.queryDict01ByFieldcode(Constants.DICT_MAKEAREA, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
		//颜色
		colorList = dict01Service.queryDict01ByFieldcode(Constants.DICT_COLOR_TYPE, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
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

	public WarehouseService getWarehouseService() {
		return warehouseService;
	}

	public void setWarehouseService(WarehouseService warehouseService) {
		this.warehouseService = warehouseService;
	}

	public String getStrOkIds() {
		return strOkIds;
	}

	public void setStrOkIds(String strOkIds) {
		this.strOkIds = strOkIds;
	}

	public List<WarehouseInOutOkDto> getWarehouseOutOkList() {
		return warehouseOutOkList;
	}

	public void setWarehouseOutOkList(List<WarehouseInOutOkDto> warehouseOutOkList) {
		this.warehouseOutOkList = warehouseOutOkList;
	}

	public String getStrSuppliername() {
		return strSuppliername;
	}

	public void setStrSuppliername(String strSuppliername) {
		this.strSuppliername = strSuppliername;
	}

	public String getStrFrom() {
		return strFrom;
	}

	public void setStrFrom(String strFrom) {
		this.strFrom = strFrom;
	}

	public String getBarcodeOutId() {
		return barcodeOutId;
	}

	public void setBarcodeOutId(String barcodeOutId) {
		this.barcodeOutId = barcodeOutId;
	}

	public String getStrScanBarcodeInfo() {
		return strScanBarcodeInfo;
	}

	public void setStrScanBarcodeInfo(String strScanBarcodeInfo) {
		this.strScanBarcodeInfo = strScanBarcodeInfo;
	}

	public WarehouserptService getWarehouserptService() {
		return warehouserptService;
	}

	public void setWarehouserptService(WarehouserptService warehouserptService) {
		this.warehouserptService = warehouserptService;
	}
	
	public String getStrWarehouseNo() {
		return strWarehouseNo;
	}

	public void setStrWarehouseNo(String strWarehouseNo) {
		this.strWarehouseNo = strWarehouseNo;
	}

}
