/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hiddendanger.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.hiddendanger.entity.HiddenDanger;

/**
 * 隐患管理DAO接口
 * @author Bill
 * @version 2018-06-10
 */
@MyBatisDao
public interface HiddenDangerDao extends CrudDao<HiddenDanger> {
	public List<HiddenDanger> findChildList();
}