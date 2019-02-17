package com.cn.dsyg.action;

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
import com.cn.dsyg.dto.CuPriceDto;
import com.cn.dsyg.dto.Dict01Dto;
import com.cn.dsyg.service.CuPriceService;
import com.cn.dsyg.service.Dict01Service;
import com.opensymphony.xwork2.ActionContext;

public class CuPriceAction extends BaseAction {

	private static final long serialVersionUID = -1187962397537413059L;
	private static final Logger log = LogManager.getLogger(CuPriceAction.class);
	
	private CuPriceService cuPriceService;
	private Dict01Service dict01Service;
	
	//页码
	private int startIndex;
	//翻页page
	private Page page;
	//一页显示数据条数
	private Integer intPageSize;
	
	private List<CuPriceDto> cuPriceList;
	
	private List<Dict01Dto> cuPriceDict01List;
	
	//查询条件
	//设置日期起
	private String strSetdateLow;
	//设置日期终
	private String strSetdateHigh;
	//铜价code
	private String strPriceCode;
	
	//新增
	private CuPriceDto addCuPriceDto;
	
	//修改
	private String updPriceCodeId;
	private CuPriceDto updCuPriceDto;
	
	//删除
	private String delPriceCodeId;
	
	/**
	 * 显示修改铜价设置记录页面
	 * @return
	 */
	public String showUpdCuPriceAction() {
		try {
			this.clearMessages();
			updCuPriceDto = cuPriceService.queryCuPriceByID(updPriceCodeId);
			//铜价区间代码表数据
			cuPriceDict01List = dict01Service.queryDict01ByFieldcode(Constants.DICT_CU_PRICE_AREA, Constants.SYSTEM_LANGUAGE_ENGLISH);
		} catch(Exception e) {
			log.error("showUpdCuPriceAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 修改铜价设置记录
	 * @return
	 */
	public String updCuPriceAction() {
		try {
			this.clearMessages();
			//铜价区间代码表数据
			cuPriceDict01List = dict01Service.queryDict01ByFieldcode(Constants.DICT_CU_PRICE_AREA, Constants.SYSTEM_LANGUAGE_ENGLISH);
			//当前操作用户ID
			String username = (String) ActionContext.getContext().getSession().get(Constants.SESSION_USER_ID);
			updCuPriceDto.setUpdateuid(username);
			cuPriceService.updateCuPrice(updCuPriceDto);
			
			this.addActionMessage("修改成功！");
		} catch(Exception e) {
			log.error("updCuPriceAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 显示新增铜价设置记录页面
	 * @return
	 */
	public String showAddCuPriceAction() {
		try {
			this.clearMessages();
			//铜价区间代码表数据
			cuPriceDict01List = dict01Service.queryDict01ByFieldcode(Constants.DICT_CU_PRICE_AREA, Constants.SYSTEM_LANGUAGE_ENGLISH);
			addCuPriceDto = new CuPriceDto();
			addCuPriceDto.setSetdate(new Date());
		} catch(Exception e) {
			log.error("showAddCuPriceAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 新增铜价设置记录
	 * @return
	 */
	public String addCuPriceAction() {
		try {
			this.clearMessages();
			//铜价区间代码表数据
			cuPriceDict01List = dict01Service.queryDict01ByFieldcode(Constants.DICT_CU_PRICE_AREA, Constants.SYSTEM_LANGUAGE_ENGLISH);
			//查询设置日期是否有数据
			CuPriceDto cuPrice = cuPriceService.queryCuPriceByLogicId(DateUtil.dateToShortStr(addCuPriceDto.getSetdate()));
			if(cuPrice != null) {
				this.addActionMessage("设置日期=" + DateUtil.dateToShortStr(addCuPriceDto.getSetdate()) + "数据已存在！");
				return "checkerror";
			}
			//当前操作用户ID
			String username = (String) ActionContext.getContext().getSession().get(Constants.SESSION_USER_ID);
			addCuPriceDto.setCreateuid(username);
			addCuPriceDto.setUpdateuid(username);
			addCuPriceDto.setStatus(Constants.STATUS_NORMAL);
			addCuPriceDto.setBelongto(PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_BELONG));
			cuPriceService.insertCuPrice(addCuPriceDto);
			
			this.addActionMessage("添加成功！");
			addCuPriceDto = new CuPriceDto();
			addCuPriceDto.setSetdate(new Date());
		} catch(Exception e) {
			log.error("addCuPriceAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 铜价设置一览
	 * @return
	 */
	public String showCuPriceAction() {
		try {
			this.clearMessages();
			//页面数据初期化
			startIndex = 0;
			//默认10条
			intPageSize = 10;
			page = new Page(intPageSize);
			cuPriceList = new ArrayList<CuPriceDto>();
			
			addCuPriceDto = new CuPriceDto();
			updCuPriceDto = new CuPriceDto();
			
			queryData();
		} catch(Exception e) {
			log.error("showCuPriceAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询铜价设置列表
	 * @return
	 */
	public String queryCuPriceAction() {
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
			log.error("queryCuPriceAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 翻页
	 * @return
	 */
	public String turnCuPriceAction() {
		try {
			this.clearMessages();
			//页面数据初期化
			queryData();
		} catch(Exception e) {
			log.error("turnCuPriceAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String delCuPriceAction() {
		try {
			this.clearMessages();
			CuPriceDto cuPrice = cuPriceService.queryCuPriceByID(delPriceCodeId);
			if(cuPrice != null) {
				//当前操作用户ID
				String userid = (String) ActionContext.getContext().getSession().get(Constants.SESSION_USER_ID);
				cuPriceService.deleteCuPriceLogic(delPriceCodeId, userid);
				this.addActionMessage("删除成功！");
			} else {
				this.addActionMessage("该数据不存在！");
			}
			//刷新页面
			queryData();
		} catch(Exception e) {
			log.error("delCuPriceAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 数据查询
	 */
	@SuppressWarnings("unchecked")
	private void queryData() {
		//铜价区间代码表数据
		cuPriceDict01List = dict01Service.queryDict01ByFieldcode(Constants.DICT_CU_PRICE_AREA, Constants.SYSTEM_LANGUAGE_ENGLISH);
		
		updPriceCodeId = "";
		delPriceCodeId = "";
		
		if(page == null) {
			page = new Page(intPageSize);
		}
		//翻页查询所有委托公司
		this.page.setStartIndex(startIndex);
		page = cuPriceService.queryCuPriceByPage(strSetdateLow, strSetdateHigh, strPriceCode, page);
		cuPriceList = (List<CuPriceDto>) page.getItems();
		this.setStartIndex(page.getStartIndex());
	}

	public CuPriceService getCuPriceService() {
		return cuPriceService;
	}

	public void setCuPriceService(CuPriceService cuPriceService) {
		this.cuPriceService = cuPriceService;
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

	public String getStrPriceCode() {
		return strPriceCode;
	}

	public void setStrPriceCode(String strPriceCode) {
		this.strPriceCode = strPriceCode;
	}

	public List<CuPriceDto> getCuPriceList() {
		return cuPriceList;
	}

	public void setCuPriceList(List<CuPriceDto> cuPriceList) {
		this.cuPriceList = cuPriceList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CuPriceDto getAddCuPriceDto() {
		return addCuPriceDto;
	}

	public void setAddCuPriceDto(CuPriceDto addCuPriceDto) {
		this.addCuPriceDto = addCuPriceDto;
	}

	public String getUpdPriceCodeId() {
		return updPriceCodeId;
	}

	public void setUpdPriceCodeId(String updPriceCodeId) {
		this.updPriceCodeId = updPriceCodeId;
	}

	public CuPriceDto getUpdCuPriceDto() {
		return updCuPriceDto;
	}

	public void setUpdCuPriceDto(CuPriceDto updCuPriceDto) {
		this.updCuPriceDto = updCuPriceDto;
	}

	public String getDelPriceCodeId() {
		return delPriceCodeId;
	}

	public void setDelPriceCodeId(String delPriceCodeId) {
		this.delPriceCodeId = delPriceCodeId;
	}

	public String getStrSetdateLow() {
		return strSetdateLow;
	}

	public void setStrSetdateLow(String strSetdateLow) {
		this.strSetdateLow = strSetdateLow;
	}

	public String getStrSetdateHigh() {
		return strSetdateHigh;
	}

	public void setStrSetdateHigh(String strSetdateHigh) {
		this.strSetdateHigh = strSetdateHigh;
	}

	public Dict01Service getDict01Service() {
		return dict01Service;
	}

	public void setDict01Service(Dict01Service dict01Service) {
		this.dict01Service = dict01Service;
	}

	public List<Dict01Dto> getCuPriceDict01List() {
		return cuPriceDict01List;
	}

	public void setCuPriceDict01List(List<Dict01Dto> cuPriceDict01List) {
		this.cuPriceDict01List = cuPriceDict01List;
	}
}
