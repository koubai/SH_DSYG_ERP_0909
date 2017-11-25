package com.cn.dsyg.action;

import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.cn.common.action.BaseAction;
import com.cn.common.util.Constants;
import com.cn.common.util.DateUtil;
import com.cn.common.util.Page;
import com.cn.common.util.StringUtil;
import com.cn.dsyg.dto.InvoiceDto;
import com.cn.dsyg.service.CustomerService;
import com.cn.dsyg.service.InvoiceService;
import com.cn.dsyg.service.SupplierService;
import com.opensymphony.xwork2.ActionContext;

public class InvoiceAction extends BaseAction {

	private static final long serialVersionUID = 9061045843079689235L;
	private static final Logger log = LogManager.getLogger(InvoiceAction.class);
	private InvoiceService invoiceService;
	private CustomerService customerService;
	private SupplierService supplierService;
	
	//页码
	private int okStartIndex;
	//翻页page
	private Page okPage;
	//一页显示数据条数
	private Integer okIntPageSize;
	//发票列表
	private List<InvoiceDto> listInvoiceOk;
	//发票号
	private String strInvoiceno;
	
	//页码
	private int newStartIndex;
	//翻页page
	private Page newPage;
	//一页显示数据条数
	private Integer newIntPageSize;
	//发票列表
	private List<InvoiceDto> listInvoiceNew;
	//查询条件
	//开票日期起
	private String strInvoiceDateLow;
	//开票日期终
	private String strInvoiceDateHigh;
	//客户名称
	private String strCustomerName;
	//客户ID
	private String strCustomerid;
	
	//预开票、删除用
	private String strIds;
	private String strInvoicenoOK;
	private String strNote;
	
	/**
	 * 废票
	 * @return
	 */
	public String cancelInvoiceAction() {
		try {
			this.clearMessages();
			//作废
			String username = (String) ActionContext.getContext().getSession().get(Constants.SESSION_USER_ID);
			if(StringUtil.isBlank(strIds)) {
				this.addActionMessage("请选择一条记录！");
				return "checkerror";
			}
			//废票处理
			invoiceService.cancelInvoice(strInvoicenoOK, strNote, strIds, username);
			this.addActionMessage("作废操作成功！");
			//刷新预开票页面数据
			strInvoicenoOK = "";
			strNote = "";
			queryInvoiceNewData();
		} catch(RuntimeException e) {
			this.addActionMessage(e.getMessage());
			//刷新预开票页面数据
			queryInvoiceNewData();
		} catch(Exception e) {
			log.error("cancelInvoiceAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 预开票记录确认开票
	 * @return
	 */
	public String invoiceOKAction() {
		try {
			this.clearMessages();
			//当前操作用户ID
			String username = (String) ActionContext.getContext().getSession().get(Constants.SESSION_USER_ID);
			//判断发票号码是否存在，若存在则提示发票已存在，开票失败
			List<InvoiceDto> invoiceList = invoiceService.queryInvoiceByInvoiceno(strInvoicenoOK, null);
			if(invoiceList != null && invoiceList.size() > 0) {
				this.addActionMessage("发票" + strInvoicenoOK + "已存在，开票失败");
				return "checkerror";
			}
			//开票处理
			invoiceService.invoiceOK(strInvoicenoOK, strNote, strIds, username);
			this.addActionMessage("开票成功！");
			//刷新预开票页面数据
			strInvoicenoOK = "";
			strNote = "";
			//刷新预开票页面数据
			queryInvoiceNewData();
		} catch(RuntimeException e) {
			this.addActionMessage(e.getMessage());
			//刷新预开票页面数据
			queryInvoiceNewData();
		} catch(Exception e) {
			log.error("invoiceOKAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 删除预出库记录
	 * @return
	 */
	public String delInvoiceAction() {
		try {
			this.clearMessages();
			invoiceService.deleteInvoiceBatch(strIds);
			//刷新预开票页面数据
			queryInvoiceNewData();
			this.addActionMessage("删除成功！");
		} catch(RuntimeException e) {
			this.addActionMessage(e.getMessage());
			//刷新预开票页面数据
			queryInvoiceNewData();
		} catch(Exception e) {
			log.error("delInvoiceAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	//预开票
	/**
	 * 预开票管理页面
	 * @return
	 */
	public String showInvoiceNewAction() {
		try {
			this.clearMessages();
			//页面数据初期化
			newStartIndex = 0;
			//默认10条
			newIntPageSize = 10;
			newPage = new Page(newIntPageSize);
			
			Date now = new Date();
			//默认查询日期=近30天
			strInvoiceDateLow = DateUtil.dateToShortStr(DateUtil.addDays(now, -30));
			strInvoiceDateHigh = DateUtil.dateToShortStr(now);
			strCustomerid = "";
			strCustomerName = "";
			//刷新预开票页面数据
			queryInvoiceNewData();
		} catch(Exception e) {
			log.error("showInvoiceNewAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询预开票信息
	 * @return
	 */
	public String queryInvoiceNewAction() {
		try {
			this.clearMessages();
			//页面数据初期化
			newStartIndex = 0;
			//默认10条
			if(newIntPageSize == null) {
				newIntPageSize = 10;
			}
			newPage = new Page(newIntPageSize);
			//刷新预开票页面数据
			queryInvoiceNewData();
		} catch(Exception e) {
			log.error("queryInvoiceNewAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 预开票翻页
	 * @return
	 */
	public String turnInvoiceNewAction() {
		try {
			this.clearMessages();
			//刷新预开票页面数据
			queryInvoiceNewData();
		} catch(Exception e) {
			log.error("turnInvoiceNewAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	//发票管理-发票一览
	/**
	 * 发票管理页面
	 * @return
	 */
	public String showInvoiceOKAction() {
		try {
			this.clearMessages();
			//页面数据初期化
			okStartIndex = 0;
			//默认10条
			okIntPageSize = 10;
			okPage = new Page(okIntPageSize);
			strInvoiceno = "";
			
			//刷新发票一览数据
			queryInvoiceOKData();
		} catch(Exception e) {
			log.error("showInvoiceOKAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询发票一览
	 * @return
	 */
	public String queryInvoiceOKAction() {
		try {
			this.clearMessages();
			//页面数据初期化
			okStartIndex = 0;
			//默认10条
			if(okIntPageSize == null) {
				okIntPageSize = 10;
			}
			okPage = new Page(okIntPageSize);
			//刷新发票一览数据
			queryInvoiceOKData();
		} catch(Exception e) {
			log.error("queryInvoiceOKAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 发票一览翻页
	 * @return
	 */
	public String turnInvoiceOKAction() {
		try {
			this.clearMessages();
			//刷新发票一览数据
			queryInvoiceOKData();
		} catch(Exception e) {
			log.error("turnInvoiceOKAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询预开票数据
	 */
	@SuppressWarnings("unchecked")
	private void queryInvoiceNewData() {
		strIds = "";
		if(newPage == null) {
			newPage = new Page(newIntPageSize);
		}
		//翻页查询所有委托公司
		this.newPage.setStartIndex(newStartIndex);
		newPage = invoiceService.queryInvoiceByPage("", "", strInvoiceDateHigh, strInvoiceDateLow,
				"" + Constants.INVOICE_STATUS_NEW, "", strCustomerid, strCustomerName, newPage);
		listInvoiceNew = (List<InvoiceDto>) newPage.getItems();
		this.setNewStartIndex(newPage.getStartIndex());
	}
	
	/**
	 * 查询发票一览
	 */
	@SuppressWarnings("unchecked")
	private void queryInvoiceOKData() {
		if(okPage == null) {
			okPage = new Page(okIntPageSize);
		}
		//翻页查询所有委托公司
		this.okPage.setStartIndex(okStartIndex);
		//不查询99的记录
		okPage = invoiceService.queryInvoiceOKByPage("", strInvoiceno, "", "",
				"" + Constants.INVOICE_STATUS_OK + "," + Constants.INVOICE_STATUS_RETURN + "," + Constants.INVOICE_STATUS_CANCEL, "", okPage);
//		okPage = invoiceService.queryInvoiceByPage("", strInvoiceno, "", "",
//				//"" + Constants.INVOICE_STATUS_OK + "," + Constants.INVOICE_STATUS_RETURN, "", "", "", okPage);
//				"" + Constants.INVOICE_STATUS_OK + "," + Constants.INVOICE_STATUS_RETURN + "," + Constants.INVOICE_STATUS_CANCEL, "", "", "", okPage);
		
		listInvoiceOk = (List<InvoiceDto>) okPage.getItems();
		this.setNewStartIndex(okPage.getStartIndex());
	}

	public InvoiceService getInvoiceService() {
		return invoiceService;
	}

	public void setInvoiceService(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	public String getStrInvoiceDateLow() {
		return strInvoiceDateLow;
	}

	public void setStrInvoiceDateLow(String strInvoiceDateLow) {
		this.strInvoiceDateLow = strInvoiceDateLow;
	}

	public String getStrInvoiceDateHigh() {
		return strInvoiceDateHigh;
	}

	public void setStrInvoiceDateHigh(String strInvoiceDateHigh) {
		this.strInvoiceDateHigh = strInvoiceDateHigh;
	}

	public String getStrCustomerid() {
		return strCustomerid;
	}

	public void setStrCustomerid(String strCustomerid) {
		this.strCustomerid = strCustomerid;
	}

	public String getStrInvoiceno() {
		return strInvoiceno;
	}

	public void setStrInvoiceno(String strInvoiceno) {
		this.strInvoiceno = strInvoiceno;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public SupplierService getSupplierService() {
		return supplierService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public List<InvoiceDto> getListInvoiceNew() {
		return listInvoiceNew;
	}

	public void setListInvoiceNew(List<InvoiceDto> listInvoiceNew) {
		this.listInvoiceNew = listInvoiceNew;
	}

	public int getNewStartIndex() {
		return newStartIndex;
	}

	public void setNewStartIndex(int newStartIndex) {
		this.newStartIndex = newStartIndex;
	}

	public Page getNewPage() {
		return newPage;
	}

	public void setNewPage(Page newPage) {
		this.newPage = newPage;
	}

	public Integer getNewIntPageSize() {
		return newIntPageSize;
	}

	public void setNewIntPageSize(Integer newIntPageSize) {
		this.newIntPageSize = newIntPageSize;
	}

	public int getOkStartIndex() {
		return okStartIndex;
	}

	public void setOkStartIndex(int okStartIndex) {
		this.okStartIndex = okStartIndex;
	}

	public Page getOkPage() {
		return okPage;
	}

	public void setOkPage(Page okPage) {
		this.okPage = okPage;
	}

	public Integer getOkIntPageSize() {
		return okIntPageSize;
	}

	public void setOkIntPageSize(Integer okIntPageSize) {
		this.okIntPageSize = okIntPageSize;
	}

	public List<InvoiceDto> getListInvoiceOk() {
		return listInvoiceOk;
	}

	public void setListInvoiceOk(List<InvoiceDto> listInvoiceOk) {
		this.listInvoiceOk = listInvoiceOk;
	}

	public String getStrIds() {
		return strIds;
	}

	public void setStrIds(String strIds) {
		this.strIds = strIds;
	}

	public String getStrInvoicenoOK() {
		return strInvoicenoOK;
	}

	public void setStrInvoicenoOK(String strInvoicenoOK) {
		this.strInvoicenoOK = strInvoicenoOK;
	}

	public String getStrNote() {
		return strNote;
	}

	public void setStrNote(String strNote) {
		this.strNote = strNote;
	}

	public String getStrCustomerName() {
		return strCustomerName;
	}

	public void setStrCustomerName(String strCustomerName) {
		this.strCustomerName = strCustomerName;
	}
}
