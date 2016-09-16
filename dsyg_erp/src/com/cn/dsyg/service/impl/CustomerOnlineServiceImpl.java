package com.cn.dsyg.service.impl;

import java.util.List;

import com.cn.common.util.Constants;
import com.cn.common.util.MD5Util;
import com.cn.common.util.Page;
import com.cn.common.util.PropertiesConfig;
import com.cn.common.util.StringUtil;
import com.cn.dsyg.dao.CustomerOnlineDao;
import com.cn.dsyg.dao.Dict01Dao;
import com.cn.dsyg.dto.CustomerOnlineDto;
import com.cn.dsyg.dto.Dict01Dto;
import com.cn.dsyg.service.CustomerOnlineService;

/**
 * @name 
 * @author 
 * @time 
 * @version 1.0
 */
public class CustomerOnlineServiceImpl implements CustomerOnlineService {
	
	private CustomerOnlineDao customerOnlineDao;
	private Dict01Dao dict01Dao;

	@Override
	public CustomerOnlineDto queryAllCustomerOnlineByID(String ID) {
		return customerOnlineDao.queryAllCustomerOnlineByID(ID);
	}

	@Override
	public CustomerOnlineDto queryAllCustomerOnlineByEmail(String customerEmail){
			return customerOnlineDao.queryAllCustomerOnlineByEmail(customerEmail);
	}

	@Override
	public Page queryCustomerOnlineByPage(Page page, String customerIdLow,
			String customerIdHigh, String customerEmail, String customerName) {
		customerIdLow = StringUtil.replaceDatabaseKeyword_mysql(customerIdLow);
		//查询总记录数
		int totalCount = customerOnlineDao.queryCustomerOnlineCountByPage(customerIdLow, customerIdHigh, customerEmail, customerName);
		page.setTotalCount(totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		//翻页查询记录
		List<CustomerOnlineDto> list = customerOnlineDao.queryCustomerOnlineByPage(customerIdLow, customerIdHigh,
				customerEmail, customerName, page.getStartIndex() * page.getPageSize(), page.getPageSize());
		page.setItems(list);
		return page;
	}

	@Override
	public CustomerOnlineDto queryCustomerOnlineByID(String customerId) {
		return customerOnlineDao.queryCustomerOnlineByID(customerId);
	}

	@Override
	public List<CustomerOnlineDto> queryAllCustomerOnline() {
		return customerOnlineDao.queryAllCustomerOnline();
	}

	@Override
	public void insertCustomerOnline(CustomerOnlineDto customer) {
		//客户番号
		/*String code = "";
		List<Dict01Dto> listDict = dict01Dao.queryDict01ByFieldcode(Constants.DICT_CUSTOMER_ONLINE_ORDER, PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
		if(listDict != null && listDict.size() > 0) {
			Dict01Dto dict = listDict.get(0);
			code = dict.getCode();
			//番号+1
			dict.setCode("" + (Integer.valueOf(dict.getCode()) + 1));
			dict01Dao.updateDict01(dict);
			code = "" + (Integer.valueOf(dict.getCode()) + 1);
		} else {
			//插入数据
			Dict01Dto dict = new Dict01Dto();
			dict.setFieldcode(Constants.DICT_CUSTOMER_ONLINE_ORDER);
			dict.setFieldname("客户番号");
			//番号默认从1开始
			dict.setCode("100001");
			code = "100001";
			dict.setLang(PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_LANGUAGE));
			dict.setMean("客户番号");
			dict.setNote("客户番号");
			dict.setStatus(Constants.STATUS_NORMAL);
			dict.setCreateuid("admin");
			dict.setUpdateuid("admin");
			dict01Dao.insertDict01(dict);
		}
		customer.setCustomerid(Integer.valueOf(code));*/
		//MD5加密密码
		customer.setPassword(MD5Util.md5(customer.getPassword()));
		customerOnlineDao.insertCustomerOnline(customer);
	}

	@Override
	public void updateCustomerOnline(CustomerOnlineDto customer) {
		customerOnlineDao.updateCustomerOnline(customer);
	}

	@Override
	public void deleteCustomerOnline(String customerId, String username) {
		CustomerOnlineDto customer = customerOnlineDao.queryCustomerOnlineByID(customerId);
		if(customer != null) {
			//状态=已删除
			customer.setStatus(Constants.STATUS_DEL);
			customer.setUpdateuid(username);
			customerOnlineDao.updateCustomerOnline(customer);
		}
	}

	@Override
	public List<CustomerOnlineDto> queryAllCustomerOnlineExport(String customerIdLow,
			String customerIdHigh) {
		return customerOnlineDao.queryAllCustomerOnlineExport(customerIdLow, customerIdHigh);
	}

	public CustomerOnlineDao getCustomerOnlineDao() {
		return customerOnlineDao;
	}

	public void setCustomerOnlineDao(CustomerOnlineDao customerOnlineDao) {
		this.customerOnlineDao = customerOnlineDao;
	}

	public Dict01Dao getDict01Dao() {
		return dict01Dao;
	}

	public void setDict01Dao(Dict01Dao dict01Dao) {
		this.dict01Dao = dict01Dao;
	}
}
