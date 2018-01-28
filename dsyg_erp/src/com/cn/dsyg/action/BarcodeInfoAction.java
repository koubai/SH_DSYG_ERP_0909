package com.cn.dsyg.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.cn.common.action.BaseAction;
import com.cn.common.util.Page;
import com.cn.dsyg.dto.BarcodeInfoDto;
import com.cn.dsyg.service.BarcodeInfoService;

public class BarcodeInfoAction extends BaseAction {

	private static final long serialVersionUID = 6920441930743938446L;
	private static final Logger log = LogManager.getLogger(BarcodeInfoAction.class);
	
	private BarcodeInfoService barcodeInfoService;

	//页码
	private int startIndex;
	//翻页page
	private Page page;
	//一页显示数据条数
	private Integer intPageSize;
	
	private String strProductid;
	private String strTradename;
	private String strBarcode;
	private String strOperatetype;
	
	private List<BarcodeInfoDto> barcodeInfoList;
	
	/**
	 * 显示条形码管理页面
	 * @return
	 */
	public String showBarcodeInfoAction() {
		try {
			this.clearMessages();
			//页面数据初期化
			startIndex = 0;
			//默认10条
			intPageSize = 10;
			page = new Page(intPageSize);
			strProductid = "";
			strTradename = "";
			strBarcode = "";
			strOperatetype = "";
			queryData();
		} catch(Exception e) {
			log.error("showBarcodeInfoAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询数据
	 * @return
	 */
	public String queryBarcodeInfoAction() {
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
			log.error("queryBarcodeInfoAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 翻页
	 * @return
	 */
	public String turnBarcodeInfoAction() {
		try {
			this.clearMessages();
			//页面数据初期化
			queryData();
		} catch(Exception e) {
			log.error("turnBarcodeInfoAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询数据
	 */
	@SuppressWarnings("unchecked")
	private void queryData() {
		barcodeInfoList = new ArrayList<BarcodeInfoDto>();
		if(page == null) {
			page = new Page(intPageSize);
		}
		//翻页查询所有委托公司
		this.page.setStartIndex(startIndex);
		page = barcodeInfoService.queryBarcodeInfoByPage(strProductid, strTradename, "",
				strBarcode, "", strOperatetype, page);
		barcodeInfoList = (List<BarcodeInfoDto>) page.getItems();
		this.setStartIndex(page.getStartIndex());
	}

	public BarcodeInfoService getBarcodeInfoService() {
		return barcodeInfoService;
	}

	public void setBarcodeInfoService(BarcodeInfoService barcodeInfoService) {
		this.barcodeInfoService = barcodeInfoService;
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

	public List<BarcodeInfoDto> getBarcodeInfoList() {
		return barcodeInfoList;
	}

	public void setBarcodeInfoList(List<BarcodeInfoDto> barcodeInfoList) {
		this.barcodeInfoList = barcodeInfoList;
	}

	public String getStrProductid() {
		return strProductid;
	}

	public void setStrProductid(String strProductid) {
		this.strProductid = strProductid;
	}

	public String getStrBarcode() {
		return strBarcode;
	}

	public void setStrBarcode(String strBarcode) {
		this.strBarcode = strBarcode;
	}

	public String getStrOperatetype() {
		return strOperatetype;
	}

	public void setStrOperatetype(String strOperatetype) {
		this.strOperatetype = strOperatetype;
	}

	public String getStrTradename() {
		return strTradename;
	}

	public void setStrTradename(String strTradename) {
		this.strTradename = strTradename;
	}
}
