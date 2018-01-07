package com.cn.dsyg.action;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.cn.dsyg.dto.BarcodeLogDto;
import com.cn.dsyg.dto.Dict01Dto;
import com.cn.dsyg.dto.ProductDto;
import com.cn.dsyg.service.BarcodeLogService;
import com.cn.dsyg.service.Dict01Service;
import com.cn.dsyg.service.ProductBarcodeService;
import com.cn.dsyg.service.ProductService;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONArray;

/**
 * @name 产品条形码管理Action
 * @author Frank
 * @time 2017-12-31下午10:09:02
 * @version 1.0
 */
public class ProductBarcodeAction extends BaseAction {

	private static final long serialVersionUID = 7014372041934804484L;
	private static final Logger log = LogManager.getLogger(ProductBarcodeAction.class);
	
	private ProductService productService;
	private Dict01Service dict01Service;
	private ProductBarcodeService productBarcodeService;
	private BarcodeLogService barcodeLogService;
	
	//页码
	private int startIndex;
	//翻页page
	private Page page;
	//一页显示数据条数
	private Integer intPageSize;
	//页面显示的产品列表
	private List<ProductDto> productList;
	
	private String strFieldno;
	private String strItem10;
	private String strKeyword;
	private String strPackaging;
	
	//采购主题
	private List<Dict01Dto> goodsList;
	//颜色
	private List<Dict01Dto> colorList;
	//单位
	private List<Dict01Dto> unitList;
	//产地
	private List<Dict01Dto> makeareaList;
	
	//产品ID用于条形码生成，多个用逗号分割
	private String strBarcodeProductIds;
	//起始编号
	private String strBarcodeSeq;
	//生成数量
	private String strBarcodeQuantity;
	//单位长度（product表Item14为空时，需要用户手输入）
	private String strProductItem14;
	
	/**
	 * 生成条形码
	 * @return
	 * @throws IOException 
	 */
	public String createProductBarcodeAction() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		AjaxResultDto ajaxResult = new AjaxResultDto();
		try {
			this.clearMessages();
			String username = (String) ActionContext.getContext().getSession().get(Constants.SESSION_USER_ID);
			//生成条形码
			List<BarcodeLogDto> barcodeInfoList = barcodeLogService.createBarcodeBatch(strBarcodeProductIds, strBarcodeSeq, strBarcodeQuantity, strProductItem14, username);
			ajaxResult.setCode(0);
			ajaxResult.setMsg("succ");
			ajaxResult.setData(barcodeInfoList);
		} catch(Exception e) {
			ajaxResult.setCode(-1);
			ajaxResult.setMsg("系统异常，请联系管理员！");
			log.error("createProductBarcodeAction error:" + e);
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
	 * 显示产品条形码管理页面
	 * @return
	 */
	public String showProductBarcodeAction() {
		try {
			this.clearMessages();
			strFieldno = "";
			strItem10 = "";
			strKeyword = "";
			strPackaging = "";
			
			startIndex = 0;
			//默认10条
			intPageSize = 10;
			page = new Page(intPageSize);
			queryData();
		} catch(Exception e) {
			log.error("showBarcodeAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询产品
	 * @return
	 */
	public String queryProductBarcodeAction() {
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
			log.error("queryProductBarcodeAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 翻页
	 * @return
	 */
	public String turnProductBarcodeAction() {
		try {
			this.clearMessages();
			queryData();
		} catch(Exception e) {
			log.error("turnProductBarcodeAction error:" + e);
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
		//初期化字典数据
		initDictList();
		//翻页查询所有委托公司
		this.page.setStartIndex(startIndex);
		page = productService.queryProductByPage(strFieldno, strItem10, strKeyword, strPackaging, "", "", "",
				"", "" + Constants.STATUS_NORMAL, page);
		productList = (List<ProductDto>) page.getItems();
		this.setStartIndex(page.getStartIndex());
	}
	
	/**
	 * 初期化字典数据
	 */
	private void initDictList() {
		strBarcodeProductIds = "";
		strBarcodeSeq = "";
		strBarcodeQuantity = "";
		strProductItem14 = "";
		//采购主题
		goodsList = dict01Service.queryDict01ByFieldcode(Constants.DICT_GOODS_TYPE, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
		//单位
		unitList = dict01Service.queryDict01ByFieldcode(Constants.DICT_UNIT_TYPE, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
		//产地
		makeareaList = dict01Service.queryDict01ByFieldcode(Constants.DICT_MAKEAREA, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
		//颜色
		colorList = dict01Service.queryDict01ByFieldcode(Constants.DICT_COLOR_TYPE, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
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

	public List<ProductDto> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductDto> productList) {
		this.productList = productList;
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

	public String getStrFieldno() {
		return strFieldno;
	}

	public void setStrFieldno(String strFieldno) {
		this.strFieldno = strFieldno;
	}

	public String getStrItem10() {
		return strItem10;
	}

	public void setStrItem10(String strItem10) {
		this.strItem10 = strItem10;
	}

	public String getStrKeyword() {
		return strKeyword;
	}

	public void setStrKeyword(String strKeyword) {
		this.strKeyword = strKeyword;
	}

	public ProductBarcodeService getProductBarcodeService() {
		return productBarcodeService;
	}

	public void setProductBarcodeService(ProductBarcodeService productBarcodeService) {
		this.productBarcodeService = productBarcodeService;
	}

	public String getStrPackaging() {
		return strPackaging;
	}

	public void setStrPackaging(String strPackaging) {
		this.strPackaging = strPackaging;
	}

	public String getStrBarcodeProductIds() {
		return strBarcodeProductIds;
	}

	public void setStrBarcodeProductIds(String strBarcodeProductIds) {
		this.strBarcodeProductIds = strBarcodeProductIds;
	}

	public String getStrBarcodeSeq() {
		return strBarcodeSeq;
	}

	public void setStrBarcodeSeq(String strBarcodeSeq) {
		this.strBarcodeSeq = strBarcodeSeq;
	}

	public String getStrBarcodeQuantity() {
		return strBarcodeQuantity;
	}

	public void setStrBarcodeQuantity(String strBarcodeQuantity) {
		this.strBarcodeQuantity = strBarcodeQuantity;
	}

	public String getStrProductItem14() {
		return strProductItem14;
	}

	public void setStrProductItem14(String strProductItem14) {
		this.strProductItem14 = strProductItem14;
	}

	public BarcodeLogService getBarcodeLogService() {
		return barcodeLogService;
	}

	public void setBarcodeLogService(BarcodeLogService barcodeLogService) {
		this.barcodeLogService = barcodeLogService;
	}
}
