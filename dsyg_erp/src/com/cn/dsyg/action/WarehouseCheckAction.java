package com.cn.dsyg.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.cn.common.action.BaseAction;
import com.cn.common.factory.Poi2007Base;
import com.cn.common.factory.PoiFactory;
import com.cn.common.factory.PoiWarehouseCheckIn;
import com.cn.common.util.Constants;
import com.cn.common.util.Page;
import com.cn.common.util.PropertiesConfig;
import com.cn.common.util.StringUtil;
import com.cn.dsyg.dto.Dict01Dto;
import com.cn.dsyg.dto.PositionCollectDto;
import com.cn.dsyg.dto.PositionDto;
import com.cn.dsyg.dto.WarehouseCheckDto;
import com.cn.dsyg.service.Dict01Service;
import com.cn.dsyg.service.PositionService;
import com.cn.dsyg.service.WarehouseService;
import com.opensymphony.xwork2.ActionContext;

/**
 * @name 库存盘点
 * @author Frank
 * @time 2015-7-26下午2:56:49
 * @version 1.0
 */
public class WarehouseCheckAction extends BaseAction {

	private static final long serialVersionUID = 842353236788134771L;
	private static final Logger log = LogManager.getLogger(WarehouseCheckAction.class);
	
	private Dict01Service dict01Service;
	private WarehouseService warehouseService;
	private PositionService positionService;
	
	//页码
	private int startIndex;
	//翻页page
	private Page page;
	//一页显示数据条数
	private Integer intPageSize;
	//盘点集集
	private List<PositionCollectDto> positionCollectList;
	//盘点日期
	private String strCheckday;
	
	//页码
	private int checkStartIndex;
	//翻页page
	private Page checkPage;
	//一页显示数据条数
	private Integer checkIntPageSize;
	private List<WarehouseCheckDto> warehouseCheckList;
	
	private String strTheme;

	//采购主题
	private List<Dict01Dto> goodsList;
	//颜色
	private List<Dict01Dto> colorList;
	//单位
	private List<Dict01Dto> unitList;
	//产地
	private List<Dict01Dto> makeareaList;
	//excel密码
	private String excelPass;
	
	//盘点的产品ID
	private String strCheckProductid;
	//盘点的库存值
	private String strCheckProductNum;
	//盘点库存位置
	private String strPosition;
	
	//盘点明细
	private String strDay;
	private String strUser;
	private List<PositionDto> positionDetailList;
	
	//上传盘点文件名及路径
	private String uploadfile;
		
	/**
	 * 盘点明细
	 * @return
	 */
	public String showCollectDetailAction() {
		try {
			this.clearMessages();
			//初期化字典数据
			initDictList();
			positionDetailList = new ArrayList<PositionDto>();
			positionDetailList = positionService.queryPositionListByLogicId(strUser, "", strDay);
			for(PositionDto pdt: positionDetailList) {
				if (pdt.getRes01()== null)
					pdt.setRes01("");
				else
					pdt.setRes01(decodeProdHist(pdt.getRes01()));
			}			
		} catch(Exception e) {
			log.error("showCollectDetailAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 盘点集集页面
	 * @return
	 */
	public String showPositionCollectAction() {
		try {
			this.clearMessages();
			//页面数据初期化
			startIndex = 0;
			strCheckday = "";
			//默认10条
			intPageSize = 10;
			page = new Page(intPageSize);
			positionCollectList = new ArrayList<PositionCollectDto>();
			warehouseCheckList = new ArrayList<WarehouseCheckDto>();
			
			queryData();
		} catch(Exception e) {
			log.error("showPositionCollectAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询盘点集集
	 * @return
	 */
	public String queryPositionCollectAction() {
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
			log.error("queryPositionCollectAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 翻页
	 * @return
	 */
	public String turnPositionCollectAction() {
		try {
			this.clearMessages();
			queryData();
		} catch(Exception e) {
			log.error("turnPositionCollectAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	//
	
	/**
	 * 库存盘点页面
	 * @return
	 */
	public String showWarehouseCheckAction() {
		try {
			this.clearMessages();
			//页面数据初期化
			checkStartIndex = 0;
			strTheme = "";
			//默认10条
			checkIntPageSize = 10;
			checkPage = new Page(checkIntPageSize);
			warehouseCheckList = new ArrayList<WarehouseCheckDto>();
			
			strDay = "";
			//初期化字典数据
			initDictList();
			queryData_check();
		} catch(Exception e) {
			log.error("showWarehouseCheckAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询仓库盘点
	 * @return
	 */
	public String queryWarehouseCheckAction() {
		try {
			this.clearMessages();
			//页面数据初期化
			checkStartIndex = 0;
			//默认10条
			if(checkIntPageSize == null) {
				checkIntPageSize = 10;
			}
			checkPage = new Page(checkIntPageSize);
			queryData_check();
		} catch(Exception e) {
			log.error("queryWarehouseCheckAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 翻页
	 * @return
	 */
	public String turnWarehouseCheckAction() {
		try {
			this.clearMessages();
			queryData_check();
		} catch(Exception e) {
			log.error("turnWarehouseCheckAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 盘点
	 * @return
	 */
	public String checkProductQuantity() {
		try {
			this.clearMessages();
			//当前操作用户ID
			String username = (String) ActionContext.getContext().getSession().get(Constants.SESSION_USER_ID);
			//查询原始库存
			boolean b = warehouseService.checkProductQuantity(strCheckProductid, strCheckProductNum, strPosition, username);
			if(b) {
				this.addActionMessage("盘点成功！");
			} else {
				//没有库存数据
				this.addActionMessage("没有该产品的库存数据！");
			}
			//刷新页面
			queryData_check();
		} catch(Exception e) {
			log.error("showWarehouseCheckAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	//导入盘点数据
	public String uploadWarehouserCheckAction() {
		try {
			this.clearMessages();
			//当前操作用户ID
			String username = (String) ActionContext.getContext().getSession().get(Constants.SESSION_USER_ID);
			initDictList();
			String filename = getUploadfile();
			if (filename.equals(""))
				return ERROR;
				
			PoiWarehouseCheckIn base = (PoiWarehouseCheckIn) PoiFactory.getPoi(Constants.EXCEL_TYPE_WAREHOUSCHECKIN);
			List<WarehouseCheckDto> list = base.upload(filename);
			int index = 0;
			//采购主题
			goodsList = dict01Service.queryDict01ByFieldcode(Constants.DICT_GOODS_TYPE, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
			//单位
			unitList = dict01Service.queryDict01ByFieldcode(Constants.DICT_UNIT_TYPE, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
			//产地
			makeareaList = dict01Service.queryDict01ByFieldcode(Constants.DICT_MAKEAREA, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
			//颜色
			colorList = dict01Service.queryDict01ByFieldcode(Constants.DICT_COLOR_TYPE, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));

			for(WarehouseCheckDto wdt: list) {
				for(Dict01Dto dict : goodsList) {
					if (dict.getFieldname().equals(wdt.getFieldno()))
						wdt.setFieldno(dict.getCode());
				}
				for(Dict01Dto dict : colorList) {
					if (dict.getFieldname().equals(wdt.getColor()))
						wdt.setColor(dict.getCode());
				}
				for(Dict01Dto dict : makeareaList) {
					if (dict.getFieldname().equals(wdt.getMakearea()))
						wdt.setMakearea(dict.getCode());
				}
				for(Dict01Dto dict : unitList) {
					if (dict.getFieldname().equals(wdt.getUnit()))
						wdt.setUnit(dict.getCode());
				}
				wdt.setCheckAmount(wdt.getWarehouseamount());
				if (wdt.getRes01()== null)
					wdt.setWarehouseamount(new BigDecimal(0));
				else
					wdt.setWarehouseamount(new BigDecimal(wdt.getRes01()));
				if (wdt.getRes03()== null)
					wdt.setRes04("");
				else
					wdt.setRes04(decodeProdHist(wdt.getRes03()));
//				warehouseCheckList.set(index, wdt);
//	用上载信息更新数据库库存信息				
				boolean b = warehouseService.checkProductQuantity(wdt, username);
				if(!b) {
					log.error(wdt.getProductid() + " position save error！");
					System.out.println(wdt.getProductid() + " position save error！");
				} 
				
				index++;
			}				
							
			queryData_check();
			//查询所有审价履历
			this.addActionMessage("盘点文件上传成功！");
//			String rtn = warehouseService.loadWarehouseCheck(list);
		} catch(Exception e) {
			log.error("uploadWarehouserCheckAction error:" + e);
			return ERROR;
		}	
		
		return SUCCESS;
	}
	
	/**保存文件
     * @param stream
     * @param path
     * @param filename
     * @throws IOException
     */
    public void SaveFileFromInputStream(InputStream stream,String path,String filename) throws IOException
    {      
        FileOutputStream fs=new FileOutputStream( path + "/"+ filename);
        byte[] buffer =new byte[1024*1024];
        int bytesum = 0;
        int byteread = 0; 
        while ((byteread=stream.read(buffer))!=-1)
        {
           bytesum+=byteread;
           fs.write(buffer,0,byteread);
           fs.flush();
        } 
        fs.close();
        stream.close();      
    }       

	
	//导出盘点数据
	public String exportWarehouserCheckAction() {
		try {
			this.clearMessages();
			initDictList();
			//字典数据组织个MAP
			Map<String, String> dictMap = new HashMap<String, String>();
			if(goodsList != null && goodsList.size() > 0) {
				for(Dict01Dto dict : goodsList) {
					dictMap.put(Constants.DICT_GOODS_TYPE + "_" + dict.getCode(), dict.getFieldname());
				}
			}
			if(unitList != null && unitList.size() > 0) {
				for(Dict01Dto dict : unitList) {
					dictMap.put(Constants.DICT_UNIT_TYPE + "_" + dict.getCode(), dict.getFieldname());
				}
			}
			if(makeareaList != null && makeareaList.size() > 0) {
				for(Dict01Dto dict : makeareaList) {
					dictMap.put(Constants.DICT_MAKEAREA + "_" + dict.getCode(), dict.getFieldname());
				}
			}
			if(colorList != null && colorList.size() > 0) {
				for(Dict01Dto dict : colorList) {
					dictMap.put(Constants.DICT_COLOR_TYPE + "_" + dict.getCode(), dict.getFieldname());
				}
			}
			dictMap.put(Constants.EXCEL_PASS, excelPass);
			
			//文件目录
			String name = StringUtil.createFileName(Constants.EXCEL_TYPE_WAREHOUSCHECK);
			response.setHeader("Content-Disposition","attachment;filename=" + name);//指定下载的文件名
			response.setContentType("application/vnd.ms-excel");
			Poi2007Base base = PoiFactory.getPoi(Constants.EXCEL_TYPE_WAREHOUSCHECK);
			
			//查询所有审价履历
			List<WarehouseCheckDto> list = warehouseService.queryWarehouseCheckToExcel("", "", "", 
					strTheme, "", "", "", "", "");
			
			base.setDatas(list);
			base.setSheetName(Constants.EXCEL_TYPE_WAREHOUSCHECK);
			base.setDictMap(dictMap);
			base.exportExcel(response.getOutputStream());
		} catch(Exception e) {
			log.error("exportWarehouserCheckAction error:" + e);
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
		//翻页查询所有入库汇总记录
		this.page.setStartIndex(startIndex);
		page = positionService.queryPositionCollectByPage("", strCheckday, page);
		positionCollectList = (List<PositionCollectDto>) page.getItems();
		this.setStartIndex(page.getStartIndex());
	}
	
	/**
	 * 数据查询
	 */
	@SuppressWarnings("unchecked")
	private void queryData_check() {
		if(checkPage == null) {
			checkPage = new Page(checkIntPageSize);
		}
		//初期化字典数据
		initDictList();
		//翻页查询所有入库汇总记录
		this.checkPage.setStartIndex(checkStartIndex);
		checkPage = warehouseService.queryWarehouseCheckByPage("", "",
				"", strTheme, "", "", "", "", "", checkPage);
		warehouseCheckList = (List<WarehouseCheckDto>) checkPage.getItems();
		for(WarehouseCheckDto wdt: warehouseCheckList) {
			if (wdt.getRes03() == null)
				wdt.setRes04("");
			else
				wdt.setRes04(decodeProdHist(wdt.getRes03()));
		}
		this.setStartIndex(checkPage.getStartIndex());
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
		//excel密码
		List<Dict01Dto> listPass = dict01Service.queryDict01ByFieldcode(Constants.EXCEL_PASS, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
		if(listPass != null && listPass.size() > 0) {
			excelPass = listPass.get(0).getCode();
		}
	}

	public Dict01Service getDict01Service() {
		return dict01Service;
	}

	public void setDict01Service(Dict01Service dict01Service) {
		this.dict01Service = dict01Service;
	}

	public WarehouseService getWarehouseService() {
		return warehouseService;
	}

	public void setWarehouseService(WarehouseService warehouseService) {
		this.warehouseService = warehouseService;
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

	public String getStrTheme() {
		return strTheme;
	}

	public void setStrTheme(String strTheme) {
		this.strTheme = strTheme;
	}

	public List<WarehouseCheckDto> getWarehouseCheckList() {
		return warehouseCheckList;
	}

	public void setWarehouseCheckList(List<WarehouseCheckDto> warehouseCheckList) {
		this.warehouseCheckList = warehouseCheckList;
	}
	
	public String getExcelPass() {
		return excelPass;
	}

	public void setExcelPass(String excelPass) {
		this.excelPass = excelPass;
	}

	public String getStrCheckProductid() {
		return strCheckProductid;
	}

	public void setStrCheckProductid(String strCheckProductid) {
		this.strCheckProductid = strCheckProductid;
	}

	public String getStrCheckProductNum() {
		return strCheckProductNum;
	}

	public void setStrCheckProductNum(String strCheckProductNum) {
		this.strCheckProductNum = strCheckProductNum;
	}

	public String getStrPosition() {
		return strPosition;
	}

	public void setStrPosition(String strPosition) {
		this.strPosition = strPosition;
	}

	public PositionService getPositionService() {
		return positionService;
	}

	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}

	public List<PositionCollectDto> getPositionCollectList() {
		return positionCollectList;
	}

	public void setPositionCollectList(List<PositionCollectDto> positionCollectList) {
		this.positionCollectList = positionCollectList;
	}

	public String getStrCheckday() {
		return strCheckday;
	}

	public void setStrCheckday(String strCheckday) {
		this.strCheckday = strCheckday;
	}

	public int getCheckStartIndex() {
		return checkStartIndex;
	}

	public void setCheckStartIndex(int checkStartIndex) {
		this.checkStartIndex = checkStartIndex;
	}

	public Page getCheckPage() {
		return checkPage;
	}

	public void setCheckPage(Page checkPage) {
		this.checkPage = checkPage;
	}

	public Integer getCheckIntPageSize() {
		return checkIntPageSize;
	}

	public void setCheckIntPageSize(Integer checkIntPageSize) {
		this.checkIntPageSize = checkIntPageSize;
	}

	public String getStrDay() {
		return strDay;
	}

	public void setStrDay(String strDay) {
		this.strDay = strDay;
	}

	public List<PositionDto> getPositionDetailList() {
		return positionDetailList;
	}

	public void setPositionDetailList(List<PositionDto> positionDetailList) {
		this.positionDetailList = positionDetailList;
	}

	public String getStrUser() {
		return strUser;
	}

	public void setStrUser(String strUser) {
		this.strUser = strUser;
	}
	
	public String getUploadfile() {
		return uploadfile;
	}

	public void setUploadfile(String uploadfile) {
		this.uploadfile = uploadfile;
	}

	public String decodeProdHist(String in_String){
		if ((in_String == null) || (in_String.trim().equals("")))
			return null;
		
		JSONArray mapArray = JSONArray.fromObject(in_String);
		String rtn = "";
		for (int m = 0 ; m <mapArray.size(); m++){
			JSONObject json = (JSONObject)mapArray.get(m);
			rtn += json.get("in_wdate").toString()+",";
			rtn += json.get("in_wquantity").toString()+";\n";	    		
		}			
		return rtn;
	}
	
	public JSONArray encodeProdHist(String in_String){
		if ((in_String == null) || (in_String.trim().equals("")))
			return null;
		List<Map<String, String>> lst = new ArrayList<Map<String, String>> ();
    	String[] w_data = in_String.toString().split("\n");
    	if (w_data.length > 0){
    		for (int m = 0 ; m <w_data.length ; m++){
        		com.cn.dsyg.dto.PositionHistDto pp = new com.cn.dsyg.dto.PositionHistDto();
        		System.out.println("w_data["+m+"]"+ w_data[m]);
        		int ix = w_data[m].toString().indexOf("，");
        		String[] w_dat;
        		if (ix > 0)
        			w_dat = w_data[m].toString().split("，");
        		else 
        			w_dat = w_data[m].toString().split(",");
        		if (w_dat.length > 0){
        			String in_wdate = w_dat[0].trim(); 
    				pp.setIn_wdate(in_wdate);
	        		System.out.println("in_wdate:"+ in_wdate);	        		
        			if (w_dat.length > 1){
        				int idx= w_dat[1].indexOf(";");
        				String in_quantity ="";
        				if (idx > 0)	
            				in_quantity = w_dat[1].substring(0, idx); 
        				else
        					in_quantity = w_dat[1].trim(); 
        				pp.setIn_quantity(new BigDecimal(in_quantity));
        				System.out.println("in_quantity:"+ in_quantity);
            			lst.add(pp.getIn_map());
        			}else{
        				System.out.println( m + "row in_quantity error.");	        				        				
        			}
        		}
    		}    		
    	}
    	if (lst.size() <= 0){
    		return null;    		
    	}
		JSONArray mapArray = JSONArray.fromObject(lst);
		System.out.println("mapArray" + mapArray.toString());
		return mapArray;
	}

}
