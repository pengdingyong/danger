/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dangerbank.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.dangerbank.entity.DangerBank;

/**
 * 隐患库管理DAO接口
 * @author 隐患库管理
 * @version 2018-06-23
 */
@MyBatisDao
public interface DangerBankDao extends CrudDao<DangerBank> {
	
	public List<DangerBank> getDangerChartByCompanyId();
	
	public List<DangerBank> getChartByHiddenDangerParentId();

	public List<DangerBank> getChartByHiddenDangerId();
	
	public List<DangerBank> getDangerChartByHiddenDanger(DangerBank dangerBank);
	
	public List<DangerBank> getDangerChartByFindTime(DangerBank dangerBank);
	
	public List<DangerBank> getDangerChartByRating();
}