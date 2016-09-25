package com.cn.dsyg.dao;

import java.util.List;

import com.cn.dsyg.dto.MailAuthDto;

/**
 * MailAuthDao
 * @author Frank
 * @version 1.0
 * @create 2016-8-5下午4:05:17
 */
public interface MailAuthDao {
	
	/**
	 * 翻页查询数据
	 * @param userid
	 * @param start
	 * @param end
	 * @return
	 */
	public List<MailAuthDto> queryMailAuthByPage(String userid, int start, int end);
	
	/**
	 * 查询总记录数
	 * @param userid
	 * @return
	 */
	public int queryMailAuthCountByPage(String userid);

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
