package com.cn.dsyg.service;

import com.cn.common.util.Page;
import com.cn.dsyg.dto.MailAuthDto;

/**
 * MailAuthService
 * @author Frank
 * @version 1.0
 * @create 2016-8-5下午4:10:09
 */
public interface MailAuthService {

	/**
	 * 翻页查询数据
	 * @param userid
	 * @param page
	 * @return
	 */
	public Page queryMailAuthByPage(String userid, Page page);
	
	/**
	 * 根据ID查询数据
	 * @param id
	 * @return
	 */
	public MailAuthDto queryMailAuthByID(String id);
	
	/**
	 * 根据逻辑主键查询数据
	 * @paramd
	 * @param userid
	 * @param mailauthcode
	 * @return
	 */
	public MailAuthDto queryMailAuthByLogicId(String userid, String mailauthcode);
	
	/**
	 * 逻辑删除认证数据
	 * @param id
	 * @param ip 更新IP
	 * @param operator
	 */
	public void deleteMailAuth(String id, String ip, String operator);
	
	/**
	 * 新增数据
	 * @param mailAuth
	 */
	public void insertMailAuth(MailAuthDto mailAuth);
	
	/**
	 * 修改数据
	 * @param mailAuth
	 */
	public void updateMailAuth(MailAuthDto mailAuth);
}
