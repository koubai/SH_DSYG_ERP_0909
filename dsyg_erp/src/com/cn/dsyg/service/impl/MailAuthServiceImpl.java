package com.cn.dsyg.service.impl;

import java.util.List;

import com.cn.common.util.Constants;
import com.cn.common.util.Page;
import com.cn.common.util.StringUtil;
import com.cn.dsyg.dao.MailAuthDao;
import com.cn.dsyg.dto.MailAuthDto;
import com.cn.dsyg.service.MailAuthService;

/**
 * MailAuthServiceImpl
 * @author Frank
 * @version 1.0
 * @create 2016-8-5下午4:12:55
 */
public class MailAuthServiceImpl implements MailAuthService {
	
	private MailAuthDao mailAuthDao;

	@Override
	public Page queryMailAuthByPage(String userid, Page page) {
		userid = StringUtil.replaceDatabaseKeyword_mysql(userid);
		//查询总记录数
		int totalCount = mailAuthDao.queryMailAuthCountByPage(userid);
		page.setTotalCount(totalCount);
		if(totalCount % page.getPageSize() > 0) {
			page.setTotalPage(totalCount / page.getPageSize() + 1);
		} else {
			page.setTotalPage(totalCount / page.getPageSize());
		}
		//翻页查询记录
		List<MailAuthDto> list = mailAuthDao.queryMailAuthByPage(userid,
				page.getStartIndex() * page.getPageSize(), page.getPageSize());
		page.setItems(list);
		return page;
	}

	@Override
	public MailAuthDto queryMailAuthByID(String id) {
		return mailAuthDao.queryMailAuthByID(id);
	}

	@Override
	public MailAuthDto queryMailAuthByLogicId(String userid, String mailauthcode) {
		return mailAuthDao.queryMailAuthByLogicId(userid, mailauthcode);
	}

	@Override
	public void deleteMailAuth(String id, String ip, String operator) {
		MailAuthDto mailAuth = mailAuthDao.queryMailAuthByID(id);
		if(mailAuth != null) {
			//状态=无效
			mailAuth.setStatus(Constants.STATUS_DEL);
			//操作IP
			mailAuth.setUpdateip(ip);
			//操作者
			mailAuth.setUpdateuid(operator);
			mailAuthDao.updateMailAuth(mailAuth);
		}
	}

	@Override
	public void insertMailAuth(MailAuthDto mailAuth) {
		mailAuthDao.insertMailAuth(mailAuth);
	}

	@Override
	public void updateMailAuth(MailAuthDto mailAuth) {
		mailAuthDao.updateMailAuth(mailAuth);
	}

	public MailAuthDao getMailAuthDao() {
		return mailAuthDao;
	}

	public void setMailAuthDao(MailAuthDao mailAuthDao) {
		this.mailAuthDao = mailAuthDao;
	}

}
