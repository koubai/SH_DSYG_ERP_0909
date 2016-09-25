package com.cn.dsyg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.common.dao.BaseDao;
import com.cn.dsyg.dao.MailAuthDao;
import com.cn.dsyg.dto.MailAuthDto;

/**
 * MailAuthDaoImpl
 * @author Frank
 * @version 1.0
 * @create 2016-8-5下午4:08:11
 */
public class MailAuthDaoImpl extends BaseDao implements MailAuthDao {

	@Override
	public List<MailAuthDto> queryMailAuthByPage(String userid, int start,
			int end) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userid", userid);
		paramMap.put("start", start);
		paramMap.put("end", end);
		@SuppressWarnings("unchecked")
		List<MailAuthDto> list = getSqlMapClientTemplate().queryForList("queryMailAuthByPage", paramMap);
		return list;
	}

	@Override
	public int queryMailAuthCountByPage(String userid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userid", userid);
		return (Integer) getSqlMapClientTemplate().queryForObject("queryMailAuthCountByPage", paramMap);
	}

	@Override
	public MailAuthDto queryMailAuthByID(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		@SuppressWarnings("unchecked")
		List<MailAuthDto> list = getSqlMapClientTemplate().queryForList("queryMailAuthByID", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public MailAuthDto queryMailAuthByLogicId(String userid, String mailauthcode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userid", userid);
		paramMap.put("mailauthcode", mailauthcode);
		@SuppressWarnings("unchecked")
		List<MailAuthDto> list = getSqlMapClientTemplate().queryForList("queryMailAuthByLogicId", paramMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertMailAuth(MailAuthDto mailAuth) {
		getSqlMapClientTemplate().insert("insertMailAuth", mailAuth);
	}

	@Override
	public void updateMailAuth(MailAuthDto mailAuth) {
		getSqlMapClientTemplate().update("updateMailAuth", mailAuth);
	}
}
