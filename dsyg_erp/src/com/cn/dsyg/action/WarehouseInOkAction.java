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
 * @name 预入库确认Action
 * @author Frank
 * @time 2015-6-4下午10:09:02
 * @version 1.0
 */
public class WarehouseInOkAction extends BaseAction {

	private static final long serialVersionUID = 4049661437562429432L;

	private static final Logger log = LogManager.getLogger(WarehouseInOkAction.class);
	
	private WarehouseService warehouseService;
	private WarehouserptService warehouserptService;
	private Dict01Service dict01Service;
	
	//页码
	private int startIndex;
	//翻页page
	private Page page;
	//一页显示数据条数
	private Integer intPageSize;
	//预入库确认数据列表
	private List<WarehouseInOutOkDto> warehouseInOkList;
	
	//采购主题
	private List<Dict01Dto> goodsList;
	//颜色
	private List<Dict01Dto> colorList;
	//单位
	private List<Dict01Dto> unitList;
	//产地
	private List<Dict01Dto> makeareaList;
	
	//预入库确认ID
	private String strOkIds;
	private String strSuppliername;
	
	//条形码入库单ID
	private String barcodeInId;
	//条形码扫码入库
	private String strScanBarcodeInfo;
	
	//仓库编号  上海深圳基本仓库不标注， 特殊才标注 如 SZ：深圳SZ   B：深圳B
	private String strWarehouseNo;

	/**
	 * 条形码入库前验证
	 * @return
	 */
	public String barcodeWarehouseInCheckAction() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		AjaxResultDto ajaxResult = new AjaxResultDto();
		try {
			this.clearMessages();
			WarehouserptDto rpt = warehouserptService.queryWarehouserptByID(barcodeInId);
			if(rpt != null) {
				if("1".equals(rpt.getRes02())) {
					ajaxResult.setCode(99);
					ajaxResult.setMsg("单据已入库！");
				} else {
					//当前操作用户ID
					String username = (String) ActionContext.getContext().getSession().get(Constants.SESSION_USER_ID);
					ajaxResult = warehouseService.barcodeWarehouseInOutCheck(barcodeInId, strScanBarcodeInfo, Constants.WAREHOUSERPT_TYPE_IN, username, true);
				}
			} else {
				ajaxResult.setCode(1);
				ajaxResult.setMsg("单据数据不存在！");
			}
		} catch(Exception e) {
			ajaxResult.setCode(-1);
			ajaxResult.setMsg("系统异常，请联系管理员！");
			log.error("barcodeWarehouseInCheckAction error:" + e);
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
	 * 条形码入库
	 * @return
	 * @throws Exception 
	 */
	public String barcodeWarehouseInAction() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		AjaxResultDto ajaxResult = new AjaxResultDto();
		try {
			this.clearMessages();
			WarehouserptDto rpt = warehouserptService.queryWarehouserptByID(barcodeInId);
			if(rpt != null) {
				if("1".equals(rpt.getRes02())) {
					ajaxResult.setCode(99);
					ajaxResult.setMsg("单据已入库！");
				} else {
					//当前操作用户ID
					String username = (String) ActionContext.getContext().getSession().get(Constants.SESSION_USER_ID);
					ajaxResult = warehouseService.barcodeWarehouseInOut(barcodeInId, strScanBarcodeInfo, Constants.WAREHOUSERPT_TYPE_IN, username);
				}
			} else {
				ajaxResult.setCode(1);
				ajaxResult.setMsg("单据数据不存在！");
			}
		} catch(Exception e) {
			ajaxResult.setCode(-1);
			ajaxResult.setMsg("系统异常，请联系管理员！");
			log.error("barcodeWarehouseInAction error:" + e);
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
	 * 预入库确认页面
	 * @return
	 */
	public String showWarehouseInOkAction() {
		try {
			this.clearMessages();
			//页面数据初期化
			strSuppliername = "";
			startIndex = 0;
			//默认10条
			intPageSize = 10;
			page = new Page(intPageSize);
			warehouseInOkList = new ArrayList<WarehouseInOutOkDto>();
			strOkIds = "";
			
			queryData();
		} catch(Exception e) {
			log.error("showWarehouseInOkAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询预入库确认数据
	 * @return
	 */
	public String queryWarehouseInOkAction() {
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
			log.error("queryWarehouseInOkAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 翻页
	 * @return
	 */
	public String turnWarehouseInOkAction() {
		try {
			this.clearMessages();
			//页面数据初期化
			queryData();
		} catch(Exception e) {
			log.error("turnWarehouseInOkAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 预入库确认
	 * @return
	 */
	public String warehouseInOkAction() {
		try {
			this.clearMessages();
			//当前操作用户ID
			String username = (String) ActionContext.getContext().getSession().get(Constants.SESSION_USER_ID);
			warehouseService.warehouseInOk(strOkIds, username, strWarehouseNo);
			
			this.addActionMessage("入库单生成成功！");
			//刷新页面数据
			queryData();
		} catch(RuntimeException ex) {
			this.addActionMessage(ex.getMessage());
		} catch(Exception e) {
			log.error("warehouseInOkAction error:" + e);
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
		//翻页查询所有预入库确认数据
		this.page.setStartIndex(startIndex);
		page = warehouseService.queryWarehouseInOkByPage(strSuppliername, "", "", "", "", "", "" + Constants.WAREHOUSE_STATUS_NEW, page);
		warehouseInOkList = (List<WarehouseInOutOkDto>) page.getItems();
		this.setStartIndex(page.getStartIndex());
	}
	
	/**
	 * 初期化字典数据
	 */
	private void initDictList() {
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

	public List<WarehouseInOutOkDto> getWarehouseInOkList() {
		return warehouseInOkList;
	}

	public void setWarehouseInOkList(List<WarehouseInOutOkDto> warehouseInOkList) {
		this.warehouseInOkList = warehouseInOkList;
	}

	public String getStrSuppliername() {
		return strSuppliername;
	}

	public void setStrSuppliername(String strSuppliername) {
		this.strSuppliername = strSuppliername;
	}

	public String getBarcodeInId() {
		return barcodeInId;
	}

	public void setBarcodeInId(String barcodeInId) {
		this.barcodeInId = barcodeInId;
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
