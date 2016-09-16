package com.cn.dsyg.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.cn.common.action.BaseAction;
import com.cn.common.util.Constants;
import com.cn.common.util.Page;
import com.cn.common.util.StringUtil;
import com.cn.dsyg.dto.CustomerOnlineDto;
import com.cn.dsyg.service.CustomerOnlineService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 线上客户Action
 * @author 
 * @time 
 * @version 1.0
 */
public class CustomerOnlineAction extends BaseAction {

	private static final long serialVersionUID = 8339856646743223524L;

	private static final Logger log = LogManager.getLogger(CustomerOnlineAction.class);
	
	private CustomerOnlineService customerOnlineService;
	
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
	 * 客户列表
	 */
	private List<CustomerOnlineDto> listCustomer;
	
	/**
	 * 客户编号（起）
	 */
	private String strCustomerIdLow;
	
	/**
	 * 客户编号（终）
	 */
	private String strCustomerIdHigh;
	
	/**
	 * 客户名称
	 */
	private String strCustomerName;
	
	/**
	 * 客户邮编地址
	 */
	private String strCustomerEmail;
	
	/**
	 * 新增客户对象
	 */
	private CustomerOnlineDto addCustomerOnlineDto;
	
	/**
	 * 修改的客户编号
	 */
	private String updateCustomerId;
	
	/**
	 * 修改客户对象
	 */
	private CustomerOnlineDto updateCustomerOnlineDto;
	
	/**
	 * 删除的客户编号
	 */
	private String delCustomerId;
	
	/**
	 * ajax查询条件-客户编号
	 */
	private String queryCustomerId;
	
	//客户查询页面（共通）
	/**
	 * 客户信息页码
	 */
	private int startIndexCustomer;
	
	/**
	 * 客户信息翻页
	 */
	private Page pageCustomer;
	
	private List<CustomerOnlineDto> customerList;
	
	private String customerIdLow;
	
	private String customerIdHigh;
	
	/**
	 * 控件ID
	 */
	private String strKey;

	/**
	 * 显示客户页面
	 * @return
	 */
	public String showCustomerOnlineAction() {
		try {
			this.clearMessages();
			strCustomerIdLow = "";
			strCustomerIdHigh = "";
			strCustomerName = "";
			strCustomerEmail = "";
			addCustomerOnlineDto = new CustomerOnlineDto();
			updateCustomerOnlineDto = new CustomerOnlineDto();
			updateCustomerId = "";
			delCustomerId = "";
			
			//默认10条
			intPageSize = 10;
			page = new Page(intPageSize);
			
			startIndex = 0;
			listCustomer = new ArrayList<CustomerOnlineDto>();
			
			queryCustomerOnline();
		} catch(Exception e) {
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询客户列表
	 * @return
	 */
	public String queryCustomerOnlineList() {
		try {
			this.clearMessages();
			startIndex = 0;
			//默认10条
			if(intPageSize == null) {
				intPageSize = 10;
			}
			page = new Page(intPageSize);
			queryCustomerOnline();
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
	public String turnCustomerOnlinePage() {
		try {
			this.clearMessages();
			queryCustomerOnline();
		} catch(Exception e) {
			log.error(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 翻页查询所有客户列表
	 */
	@SuppressWarnings("unchecked")
	private void queryCustomerOnline() {
		listCustomer = new ArrayList<CustomerOnlineDto>();
		if(page == null) {
			page = new Page(intPageSize);
		}
		//翻页查询所有客户
		this.page.setStartIndex(startIndex);
		page = customerOnlineService.queryCustomerOnlineByPage(page, strCustomerIdLow, strCustomerIdHigh, strCustomerEmail, strCustomerName);
		listCustomer = (List<CustomerOnlineDto>) page.getItems();
		
		this.setStartIndex(page.getStartIndex());
	}
	
	/**
	 * 显示添加客户页面
	 * @return
	 */
	public String showAddCustomerOnlineAction() {
		try {
			this.clearMessages();
			addCustomerOnlineDto = new CustomerOnlineDto();
		} catch(Exception e) {
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 添加客户
	 * @return
	 */
	public String addCustomerOnlineAction() {
		try {
			this.clearMessages();
			//数据校验
			if(!checkData(addCustomerOnlineDto)) {
				return "checkerror";
			}
			log.info("addCustomerOnlineDto.getCustomerid()=" + addCustomerOnlineDto.getCustomerid());
			log.info("addCustomerOnlineDto.getCustomeremail()=" + addCustomerOnlineDto.getCustomeremail());
			//校验客户代码是否存在
			CustomerOnlineDto customer = customerOnlineService.queryAllCustomerOnlineByID(addCustomerOnlineDto.getCustomerid()+"");
			if(customer != null) {
				this.addActionMessage("客户Id已经存在！");
				return "checkerror";
			}
			//校验客户邮件地址是否存在
			CustomerOnlineDto customer1 = customerOnlineService.queryAllCustomerOnlineByEmail(addCustomerOnlineDto.getCustomeremail()+"");
			if(customer1 != null) {
				this.addActionMessage("相同客户邮件地址已经存在！");
				return "checkerror";
			}
			//保存数据
			addCustomerOnlineDto.setStatus(2);
			String username = (String) ActionContext.getContext().getSession().get(Constants.SESSION_USER_NAME);
			addCustomerOnlineDto.setCreateuid(username);
			addCustomerOnlineDto.setBelongto((String)ActionContext.getContext().getSession().get(Constants.SESSION_BELONGTO));
			customerOnlineService.insertCustomerOnline(addCustomerOnlineDto);
			this.addActionMessage("添加客户成功！");
			addCustomerOnlineDto = new CustomerOnlineDto();
		} catch(Exception e) {
			this.addActionMessage("系统异常，添加客户失败！");
			log.error("addCustomerOnlineAction error:" + e);
			return "checkerror";
		}
		return SUCCESS;
	}
	
	/**
	 * 显示修改客户页面
	 * @return
	 */
	public String showUpdCustomerOnlineAction() {
		try {
			this.clearMessages();
			System.out.println("id is: " + updateCustomerId);
			updateCustomerOnlineDto = customerOnlineService.queryCustomerOnlineByID(updateCustomerId);
			if(updateCustomerOnlineDto == null) {
				this.addActionMessage("该数据不存在！");
				return "checkerror";
			}
		} catch(Exception e) {
			this.addActionMessage("系统错误，查询客户异常！");
			log.error("showUpdCustomerOnlineAction error:" + e);
			return "checkerror";
		}
		return SUCCESS;
	}
	
	/**
	 * 修改客户
	 * @return
	 */
	public String updCustomerOnlineAction() {
		try {
			this.clearMessages();
			//数据校验
			if(!checkData(updateCustomerOnlineDto)) {
				return "checkerror";
			}
			System.out.println("id is: " + updateCustomerOnlineDto.getCustomerid());
			//修改数据
			String username = (String) ActionContext.getContext().getSession().get(Constants.SESSION_USER_NAME);
			updateCustomerOnlineDto.setUpdateuid(username);
			customerOnlineService.updateCustomerOnline(updateCustomerOnlineDto);
			this.addActionMessage("修改客户成功！");
		} catch(Exception e) {
			this.addActionMessage("系统异常，修改客户失败！");
			log.error("updCustomerOnlineAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 删除客户
	 * @return
	 */
	public String delCustomerOnlineAction() {
		try {
			this.clearMessages();
			if(StringUtil.isBlank(delCustomerId)) {
				this.addActionMessage("客户代码为空！");
				return "checkerror";
			}
			String username = (String) ActionContext.getContext().getSession().get(Constants.SESSION_USER_NAME);
			//删除
			customerOnlineService.deleteCustomerOnline(delCustomerId, username);
			this.addActionMessage("删除客户成功！");
			delCustomerId = "";
			//刷新页面
			startIndex = 0;
			queryCustomerOnline();
		} catch(Exception e) {
			log.error("delCustomerOnlineAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 验证数据格式
	 * @param customer
	 * @return
	 */
	private boolean checkData(CustomerOnlineDto customer) {
		if(customer == null) {
			this.addActionMessage("客户ID不能为空！");
			return false;
		}
		if(StringUtil.isBlank(customer.getCustomerid()+"")) {
			this.addActionMessage("客户ID不能为空！");
			return false;
		}
		if(StringUtil.isBlank(customer.getCustomeremail())) {
			this.addActionMessage("客户邮件地址不能为空！");
			return false;
		}
		/*if(customer.getCustomername().length() > 40) {
			this.addActionMessage("客户名称不能超过40个字符！");
			return false;
		}
		if(StringUtil.isNotBlank(customer.getNote()) && customer.getNote().length() > 100) {
			this.addActionMessage("备考不能超过100个字符！");
			return false;
		}*/
		return true;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<CustomerOnlineDto> getListCustomer() {
		return listCustomer;
	}

	public void setListCustomer(List<CustomerOnlineDto> listCustomer) {
		this.listCustomer = listCustomer;
	}

	public CustomerOnlineService getCustomerOnlineService() {
		return customerOnlineService;
	}

	public void setCustomerOnlineService(CustomerOnlineService customerOnlineService) {
		this.customerOnlineService = customerOnlineService;
	}

	public Integer getIntPageSize() {
		return intPageSize;
	}

	public void setIntPageSize(Integer intPageSize) {
		this.intPageSize = intPageSize;
	}

	public String getStrCustomerIdLow() {
		return strCustomerIdLow;
	}

	public void setStrCustomerIdLow(String strCustomerIdLow) {
		this.strCustomerIdLow = strCustomerIdLow;
	}

	public String getStrCustomerIdHigh() {
		return strCustomerIdHigh;
	}

	public void setStrCustomerIdHigh(String strCustomerIdHigh) {
		this.strCustomerIdHigh = strCustomerIdHigh;
	}

	public String getStrCustomerEmail() {
		return strCustomerEmail;
	}

	public void setStrCustomerEmail(String strCustomerEmail) {
		this.strCustomerEmail = strCustomerEmail;
	}

	public CustomerOnlineDto getAddCustomerOnlineDto() {
		return addCustomerOnlineDto;
	}

	public void setAddCustomerOnlineDto(CustomerOnlineDto addCustomerOnlineDto) {
		this.addCustomerOnlineDto = addCustomerOnlineDto;
	}

	public String getUpdateCustomerId() {
		return updateCustomerId;
	}

	public void setUpdateCustomerId(String updateCustomerId) {
		this.updateCustomerId = updateCustomerId;
	}

	public CustomerOnlineDto getUpdateCustomerOnlineDto() {
		return updateCustomerOnlineDto;
	}

	public void setUpdateCustomerOnlineDto(CustomerOnlineDto updateCustomerOnlineDto) {
		this.updateCustomerOnlineDto = updateCustomerOnlineDto;
	}

	public String getDelCustomerId() {
		return delCustomerId;
	}

	public void setDelCustomerId(String delCustomerId) {
		this.delCustomerId = delCustomerId;
	}

	public String getQueryCustomerId() {
		return queryCustomerId;
	}

	public void setQueryCustomerId(String queryCustomerId) {
		this.queryCustomerId = queryCustomerId;
	}

	public int getStartIndexCustomer() {
		return startIndexCustomer;
	}

	public void setStartIndexCustomer(int startIndexCustomer) {
		this.startIndexCustomer = startIndexCustomer;
	}

	public Page getPageCustomer() {
		return pageCustomer;
	}

	public void setPageCustomer(Page pageCustomer) {
		this.pageCustomer = pageCustomer;
	}

	public List<CustomerOnlineDto> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<CustomerOnlineDto> customerList) {
		this.customerList = customerList;
	}

	public String getCustomerIdLow() {
		return customerIdLow;
	}

	public void setCustomerIdLow(String customerIdLow) {
		this.customerIdLow = customerIdLow;
	}

	public String getCustomerIdHigh() {
		return customerIdHigh;
	}

	public void setCustomerIdHigh(String customerIdHigh) {
		this.customerIdHigh = customerIdHigh;
	}

	public String getStrKey() {
		return strKey;
	}

	public void setStrKey(String strKey) {
		this.strKey = strKey;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public String getStrCustomerName() {
		return strCustomerName;
	}

	public void setStrCustomerName(String strCustomerName) {
		this.strCustomerName = strCustomerName;
	}
}
