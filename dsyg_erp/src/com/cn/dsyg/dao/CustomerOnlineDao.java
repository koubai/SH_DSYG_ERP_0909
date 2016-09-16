package com.cn.dsyg.dao;

import java.util.List;
import com.cn.dsyg.dto.CustomerOnlineDto;

/**
 * @name 
 * @author 
 * @time 
 * @version 1.0
 */
public interface CustomerOnlineDao {
	
	/**
	 * 根据ID查询客户（查询所有记录）
	 * @param customerId
	 * @return
	 */
	public CustomerOnlineDto queryAllCustomerOnlineByID(String customerId);

	/**
	 * 根据客户邮件查询客户（查询所有记录）
	 * @param customerEmail
	 * @return
	 */
	public CustomerOnlineDto queryAllCustomerOnlineByEmail(String customerEmail);

	/**
	 * 翻页查询客户
	 * @param customerIdLow
	 * @param customerIdHigh
	 * @param customerEmail
	 * @param customerName
	 * @param start
	 * @param end
	 * @return
	 */
	public List<CustomerOnlineDto> queryCustomerOnlineByPage(String customerIdLow,
				String customerIdHigh, String customerEmail, String customerName, int start, int end);
	
	/**
	 * 查询总记录数
	 * @param customerIdLow
	 * @param customerIdHigh
	 * @param customerEmail
	 * @param customerName
	 * @return
	 */
	public int queryCustomerOnlineCountByPage(String customerIdLow, String customerIdHigh, String customerEmail, String customerName);
	
	/**
	 * 根据ID查询客户（查询未删除的记录）
	 * @param customerId
	 * @return
	 */
	public CustomerOnlineDto queryCustomerOnlineByID(String customerId);
	
	/**
	 * 查询所有的客户
	 * @return
	 */
	public List<CustomerOnlineDto> queryAllCustomerOnline();
	
	/**
	 * 新增客户
	 * @param customer
	 */
	public void insertCustomerOnline(CustomerOnlineDto customer);
	
	/**
	 * 修改客户
	 * @param customer
	 */
	public void updateCustomerOnline(CustomerOnlineDto customer);
	
	/**
	 * 查询客户（Excel导出用）
	 * @param customerIdLow
	 * @param customerIdHigh
	 * @return
	 */
	public List<CustomerOnlineDto> queryAllCustomerOnlineExport(String customerIdLow, String customerIdHigh);
}
