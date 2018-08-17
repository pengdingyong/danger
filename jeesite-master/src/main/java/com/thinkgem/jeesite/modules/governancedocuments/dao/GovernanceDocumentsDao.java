/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.governancedocuments.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.governancedocuments.entity.GovernanceDocuments;

/**
 * 隐患库文档DAO接口
 * @author Bill
 * @version 2018-06-23
 */
@MyBatisDao
public interface GovernanceDocumentsDao extends CrudDao<GovernanceDocuments> {
	
	public GovernanceDocuments getContentById(@Param("id")String id);
	
	public int deleteByDangerBankId(@Param("dangerBankId")String dangerBankId);
	
}