package com.cn.dsyg.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.cn.common.action.BaseAction;
import com.cn.common.util.Constants;
import com.cn.common.util.DateUtil;
import com.cn.common.util.FileUtil;
import com.cn.common.util.Page;
import com.cn.common.util.PropertiesConfig;
import com.cn.common.util.StringUtil;
import com.cn.dsyg.dto.WarehouseReportDto;
import com.cn.dsyg.service.WarehouseReportService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 库存盘点报告Action
 * @author 
 * @time 
 * @version 1.0
 */
public class WarehouseReportAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(WarehouseReportAction.class);
	
	private WarehouseReportService warehouseReportService;
	
	/**
	 * 页码
	 */
	private int startIndex;
	
	/**
	 * 翻页
	 */
	private Page page;
	
	//一页显示数据条数
	private Integer intPageSize;
	
	/**
	 * 库存盘点报告列表
	 */
	private List<WarehouseReportDto> listWarehouseReport;
	
	/**
	 * 库存盘点报告编号（起）
	 */
	private String strWarehouseReportNoLow;
	
	/**
	 * 库存盘点报告编号（终）
	 */
	private String strWarehouseReportNoHigh;
	
	/**
	 * 库存盘点报告名称
	 */
	private String strWarehouseReportName;
	
	/**
	 * 新增库存盘点报告对象
	 */
	private WarehouseReportDto addWarehouseReportDto;
	
	/**
	 * 修改的库存盘点报告编号
	 */
	private String updateWarehouseReportNo;
	
	/**
	 * 修改库存盘点报告对象
	 */
	private WarehouseReportDto updateWarehouseReportDto;
	
	/**
	 * 删除的库存盘点报告编号
	 */
	private String delWarehouseReportNo;
	
	/**
	 * ajax查询条件-库存盘点报告编号
	 */
	private String queryWarehouseReportNo;
	
	//库存盘点报告查询页面（共通）
	/**
	 * 库存盘点报告信息页码
	 */
	private int startIndexWarehouseReport;
	
	/**
	 * 库存盘点报告信息翻页
	 */
	private Page pageWarehouseReport;
	
	private List<WarehouseReportDto> warehouseReportList;
	
	private String warehouseReportNoLow;
	
	private String warehouseReportNoHigh;
	
	/**
	 * 控件ID
	 */
	private String strKey;
	
	//新增
	private File addReportFile01;
	private File addReportFile02;
	private File addReportFile03;
	//对应的文件名
	private String file01Name;
	private String file02Name;
	private String file03Name;
	
	//修改
	private File updReportFile01;
	private File updReportFile02;
	private File updReportFile03;

	//rank
	private Integer rank;
	
	/**
	 * 显示库存盘点报告页面
	 * @return
	 */
	public String showWarehouseReportAction() {
		try {
			this.clearMessages();
//			System.out.println("showSalesReport2Action start");
			strWarehouseReportNoLow = "";
			strWarehouseReportNoHigh = "";
			strWarehouseReportName = "";
			addWarehouseReportDto = new WarehouseReportDto();
			updateWarehouseReportDto = new WarehouseReportDto();
			updateWarehouseReportNo = "";
			delWarehouseReportNo = "";

			//默认10条
			intPageSize = 10;
			page = new Page(intPageSize);
			
			startIndex = 0;
			warehouseReportList = new ArrayList<WarehouseReportDto>();
			
			queryWarehouseReport();
			listWarehouseReport = warehouseReportList;
			String username = (String) ActionContext.getContext().getSession().get(Constants.SESSION_USER_NAME);
			//判断当前用户是否是经理级以上（rank为80以上）
			rank = (Integer) ActionContext.getContext().getSession().get(Constants.SESSION_ROLE_RANK);

			System.out.println("showWarehouseReportAction rank:"+rank );
		} catch(Exception e) {
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询库存盘点报告列表
	 * @return
	 */
	public String queryWarehouseReportList() {
		try {
			this.clearMessages();
			//默认10条
			if(intPageSize == null) {
				intPageSize = 10;
			}
			page = new Page(intPageSize);
			startIndex = 0;
			queryWarehouseReport();
			listWarehouseReport = warehouseReportList;
		} catch(Exception e) {
			log.error(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 翻页
	 * @return
	 */
	public String turnWarehouseReportPage() {
		try {
			this.clearMessages();
			queryWarehouseReport();
			listWarehouseReport = warehouseReportList;
		} catch(Exception e) {
			log.error(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 翻页查询所有库存盘点报告列表
	 */
	@SuppressWarnings("unchecked")
	private void queryWarehouseReport() {
		warehouseReportList = new ArrayList<WarehouseReportDto>();
		if(page == null) {
			page = new Page(intPageSize);
		}
		//翻页查询所有库存盘点报告
		this.page.setStartIndex(startIndex);
		System.out.println("queryWarehouseReport start");
		page = warehouseReportService.queryWarehouseReportByPage(page, strWarehouseReportNoLow, strWarehouseReportNoHigh, strWarehouseReportName);
		warehouseReportList = (List<WarehouseReportDto>) page.getItems();
		rank = (Integer) ActionContext.getContext().getSession().get(Constants.SESSION_ROLE_RANK);
		this.setStartIndex(page.getStartIndex());
	}
	
	/**
	 * 显示添加库存盘点报告页面
	 * @return
	 */
	public String showAddWarehouseReportAction() {
		try {
			this.clearMessages();
			addWarehouseReportDto = new WarehouseReportDto();
		} catch(Exception e) {
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 添加库存盘点报告
	 * @return
	 */
	public String addWarehouseReportAction() {
		try {
			this.clearMessages();
			//数据校验
			if(!checkData(addWarehouseReportDto)) {
				return "checkerror";
			}
			log.info("addWarehouseReportDto.getWarehouseReportno()=" + addWarehouseReportDto.getWarehousereportno());
			log.info("addWarehouseReportDto.getWarehouseReportname()=" + addWarehouseReportDto.getWarehousereportname());
			//校验库存盘点报告代码是否存在
			WarehouseReportDto warehouseReport = warehouseReportService.queryWarehouseReportById(addWarehouseReportDto.getWarehousereportno()+"");
			if(warehouseReport != null) {
				this.addActionMessage("库存盘点报告已经存在！");
				return "checkerror";
			}
			
			//文件目录
			String pdf_path = PropertiesConfig.getPropertiesValueByKey(Constants.PROPERTIES_PDF_PATH);
			
			//保存文件到指定目录
			if(addReportFile01 != null) {
				String newfile01 = FileUtil.uploadFile(addReportFile01, pdf_path, file01Name);
				addWarehouseReportDto.setReportpath01(newfile01);
			}
			if(addReportFile02 != null) {
				String newfile02 = FileUtil.uploadFile(addReportFile02, pdf_path, file02Name);
				addWarehouseReportDto.setReportpath02(newfile02);
			}
			if(addReportFile03 != null) {
				String newfile03 = FileUtil.uploadFile(addReportFile03, pdf_path, file03Name);
				addWarehouseReportDto.setReportpath03(newfile03);
			}
			
			//保存数据
			addWarehouseReportDto.setStatus(Constants.STATUS_NORMAL);
			String username = (String) ActionContext.getContext().getSession().get(Constants.SESSION_USER_NAME);
			addWarehouseReportDto.setCreateuid(username);
			String warehouseReportno = warehouseReportService.insertWarehouseReport(addWarehouseReportDto);
			this.addActionMessage("添加库存盘点报告成功！库存盘点报告编号为：" + warehouseReportno);
			addWarehouseReportDto = new WarehouseReportDto();
		} catch(Exception e) {
			this.addActionMessage("系统异常，添加库存盘点报告失败！");
			log.error("addWarehouseReportAction error:" + e);
			return "checkerror";
		}
		return SUCCESS;
	}
	
	/**
	 * 显示修改库存盘点报告页面
	 * @return
	 */
	public String showUpdWarehouseReportAction() {
		try {
			updReportFile01 = null;
			updReportFile02 = null;
			updReportFile03 = null;
			this.clearMessages();
//			System.out.println("salesreport2No is: "+updateSalesReportNo);
			updateWarehouseReportDto = warehouseReportService.queryWarehouseReportById(updateWarehouseReportNo);
			if(updateWarehouseReportDto == null) {
				this.addActionMessage("该数据不存在！");
				return "checkerror";
			}
		} catch(Exception e) {
			this.addActionMessage("系统错误，查询库存盘点报告异常！");
			log.error("showUpdWarehouseReportAction error:" + e);
			return "checkerror";
		}
		return SUCCESS;
	}
	
	/**
	 * 显示修改库存盘点报告详细页面
	 * @return
	 */
	public String showUpdWarehouseReportItemAction() {
		try {
			updReportFile01 = null;
			updReportFile02 = null;
			updReportFile03 = null;
			this.clearMessages();
//			System.out.println("salesreport2No is: "+updateSalesReportNo);
			updateWarehouseReportDto = warehouseReportService.queryWarehouseReportById(updateWarehouseReportNo);
			if(updateWarehouseReportDto == null) {
				this.addActionMessage("该数据不存在！");
				return "checkerror";
			}
		} catch(Exception e) {
			this.addActionMessage("系统错误，查询库存盘点报告异常！");
			log.error("showUpdWarehouseReportItemAction error:" + e);
			return "checkerror";
		}
		return SUCCESS;
	}
	
	/**
	 * 修改库存盘点报告
	 * @return
	 */
	public String updWarehouseReportAction() {
		try {
			this.clearMessages();
			//数据校验
			if(!checkData(updateWarehouseReportDto)) {
				return "checkerror";
			}
//			System.out.println("WarehouseReportNo is: "+updateWarehouseReportDto.getWarehouseReportno());
			
			//文件目录
			String pdf_path = PropertiesConfig.getPropertiesValueByKey(Constants.PROPERTIES_PDF_PATH);
			
			//保存文件到指定目录
			String oldfile1 = "";
			String oldfile2 = "";
			String oldfile3 = "";
			WarehouseReportDto oldReport = warehouseReportService.queryWarehouseReportById(updateWarehouseReportNo);
			
			if(updReportFile01 != null) {
				String newfile01 = FileUtil.uploadFile(updReportFile01, pdf_path, file01Name);
				updateWarehouseReportDto.setReportpath01(newfile01);
				oldfile1 = oldReport.getReportpath01();
			}
			if(updReportFile02 != null) {
				String newfile02 = FileUtil.uploadFile(updReportFile02, pdf_path, file02Name);
				updateWarehouseReportDto.setReportpath02(newfile02);
				oldfile2 = oldReport.getReportpath02();
			}
			if(updReportFile03 != null) {
				String newfile03 = FileUtil.uploadFile(updReportFile03, pdf_path, file03Name);
				updateWarehouseReportDto.setReportpath03(newfile03);
				oldfile3 = oldReport.getReportpath03();
			}
			//修改数据
			String username = (String) ActionContext.getContext().getSession().get(Constants.SESSION_USER_NAME);
			updateWarehouseReportDto.setUpdateuid(username);
			warehouseReportService.updateWarehouseReport(updateWarehouseReportDto);
			this.addActionMessage("修改库存盘点报告成功！");
			//清空数据
			updReportFile01 = null;
			updReportFile02 = null;
			updReportFile03 = null;
		} catch(Exception e) {
			this.addActionMessage("系统异常，修改库存盘点报告失败！");
			log.error("updWarehouseReportAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 删除库存盘点报告
	 * @return
	 */
	public String delWarehouseReportAction() {
		try {
			this.clearMessages();
//			System.out.println("delSalesReport2Action");
			if(StringUtil.isBlank(delWarehouseReportNo)) {
				this.addActionMessage("库存盘点报告代码为空！");
				return "checkerror";
			}
			String username = (String) ActionContext.getContext().getSession().get(Constants.SESSION_USER_NAME);
			updateWarehouseReportDto.setUpdateuid(username);
			//删除
			warehouseReportService.deleteWarehouseReport(delWarehouseReportNo,username );
			this.addActionMessage("删除库存盘点报告成功！");
			delWarehouseReportNo = "";
			//刷新页面
			startIndex = 0;
			queryWarehouseReport();
			listWarehouseReport = warehouseReportList;
		} catch(Exception e) {
			log.error("delWarehouseReportAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 验证数据格式
	 * @param WarehouseReport
	 * @return
	 */
	private boolean checkData(WarehouseReportDto warehouseReport) {
		if(warehouseReport == null) {
			this.addActionMessage("库存盘点报告名称不能为空！");
			return false;
		}
//		if(StringUtil.isBlank(salesreport.getSalesReportno()+"")) {
//			this.addActionMessage("库存盘点报告代码不能为空！");
//			return false;
//		}
		if(StringUtil.isBlank(warehouseReport.getWarehousereportname())) {
			this.addActionMessage("库存盘点报告名称不能为空！");
			return false;
		}
		if(warehouseReport.getWarehousereportname().length() > 128) {
			this.addActionMessage("库存盘点报告名称不能超过128个字符！");
			return false;
		}
		if(!DateUtil.isDate(warehouseReport.getRegisterdate().toString())) {
			this.addActionMessage("登记日期格式不正确！");
			return false;
		}
		if(StringUtil.isNotBlank(warehouseReport.getNote()) && warehouseReport.getNote().length() > 500) {
			this.addActionMessage("内容介绍不能超过500个字符！");
			return false;
		}
		return true;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public String getStrKey() {
		return strKey;
	}

	public void setStrKey(String strKey) {
		this.strKey = strKey;
	}

	public Integer getIntPageSize() {
		return intPageSize;
	}

	public void setIntPageSize(Integer intPageSize) {
		this.intPageSize = intPageSize;
	}

	public File getAddReportFile01() {
		return addReportFile01;
	}

	public void setAddReportFile01(File addReportFile01) {
		this.addReportFile01 = addReportFile01;
	}

	public File getAddReportFile02() {
		return addReportFile02;
	}

	public void setAddReportFile02(File addReportFile02) {
		this.addReportFile02 = addReportFile02;
	}

	public File getAddReportFile03() {
		return addReportFile03;
	}

	public void setAddReportFile03(File addReportFile03) {
		this.addReportFile03 = addReportFile03;
	}

	public String getFile01Name() {
		return file01Name;
	}

	public void setFile01Name(String file01Name) {
		this.file01Name = file01Name;
	}

	public String getFile02Name() {
		return file02Name;
	}

	public void setFile02Name(String file02Name) {
		this.file02Name = file02Name;
	}

	public String getFile03Name() {
		return file03Name;
	}

	public void setFile03Name(String file03Name) {
		this.file03Name = file03Name;
	}

	public File getUpdReportFile01() {
		return updReportFile01;
	}

	public void setUpdReportFile01(File updReportFile01) {
		this.updReportFile01 = updReportFile01;
	}

	public File getUpdReportFile02() {
		return updReportFile02;
	}

	public void setUpdReportFile02(File updReportFile02) {
		this.updReportFile02 = updReportFile02;
	}

	public File getUpdReportFile03() {
		return updReportFile03;
	}

	public void setUpdReportFile03(File updReportFile03) {
		this.updReportFile03 = updReportFile03;
	}

	public List<WarehouseReportDto> getListWarehouseReport() {
		return listWarehouseReport;
	}

	public void setListWarehouseReport(List<WarehouseReportDto> listWarehouseReport) {
		this.listWarehouseReport = listWarehouseReport;
	}

	public String getStrWarehouseReportNoLow() {
		return strWarehouseReportNoLow;
	}

	public void setStrWarehouseReportNoLow(String strWarehouseReportNoLow) {
		this.strWarehouseReportNoLow = strWarehouseReportNoLow;
	}

	public String getStrWarehouseReportNoHigh() {
		return strWarehouseReportNoHigh;
	}

	public void setStrWarehouseReportNoHigh(String strWarehouseReportNoHigh) {
		this.strWarehouseReportNoHigh = strWarehouseReportNoHigh;
	}

	public String getStrWarehouseReportName() {
		return strWarehouseReportName;
	}

	public void setStrWarehouseReportName(String strWarehouseReportName) {
		this.strWarehouseReportName = strWarehouseReportName;
	}

	public WarehouseReportDto getAddWarehouseReportDto() {
		return addWarehouseReportDto;
	}

	public void setAddWarehouseReportDto(WarehouseReportDto addWarehouseReportDto) {
		this.addWarehouseReportDto = addWarehouseReportDto;
	}

	public String getUpdateWarehouseReportNo() {
		return updateWarehouseReportNo;
	}

	public void setUpdateWarehouseReportNo(String updateWarehouseReportNo) {
		this.updateWarehouseReportNo = updateWarehouseReportNo;
	}

	public WarehouseReportDto getUpdateWarehouseReportDto() {
		return updateWarehouseReportDto;
	}

	public void setUpdateWarehouseReportDto(
			WarehouseReportDto updateWarehouseReportDto) {
		this.updateWarehouseReportDto = updateWarehouseReportDto;
	}

	public String getDelWarehouseReportNo() {
		return delWarehouseReportNo;
	}

	public void setDelWarehouseReportNo(String delWarehouseReportNo) {
		this.delWarehouseReportNo = delWarehouseReportNo;
	}

	public String getQueryWarehouseReportNo() {
		return queryWarehouseReportNo;
	}

	public void setQueryWarehouseReportNo(String queryWarehouseReportNo) {
		this.queryWarehouseReportNo = queryWarehouseReportNo;
	}

	public int getStartIndexWarehouseReport() {
		return startIndexWarehouseReport;
	}

	public void setStartIndexWarehouseReport(int startIndexWarehouseReport) {
		this.startIndexWarehouseReport = startIndexWarehouseReport;
	}

	public Page getPageWarehouseReport() {
		return pageWarehouseReport;
	}

	public void setPageWarehouseReport(Page pageWarehouseReport) {
		this.pageWarehouseReport = pageWarehouseReport;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public WarehouseReportService getWarehouseReportService() {
		return warehouseReportService;
	}

	public void setWarehouseReportService(
			WarehouseReportService warehouseReportService) {
		this.warehouseReportService = warehouseReportService;
	}

	public List<WarehouseReportDto> getWarehouseReportList() {
		return warehouseReportList;
	}

	public void setWarehouseReportList(List<WarehouseReportDto> warehouseReportList) {
		this.warehouseReportList = warehouseReportList;
	}

	public String getWarehouseReportNoLow() {
		return warehouseReportNoLow;
	}

	public void setWarehouseReportNoLow(String warehouseReportNoLow) {
		this.warehouseReportNoLow = warehouseReportNoLow;
	}

	public String getWarehouseReportNoHigh() {
		return warehouseReportNoHigh;
	}

	public void setWarehouseReportNoHigh(String warehouseReportNoHigh) {
		this.warehouseReportNoHigh = warehouseReportNoHigh;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

}
