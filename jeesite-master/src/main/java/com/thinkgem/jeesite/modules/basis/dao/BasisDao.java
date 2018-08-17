/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.basis.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.basis.entity.Basis;

/**
 * 评估依据DAO接口
 * @author Bill
 * @version 2018-06-10
 */
@MyBatisDao
public interface BasisDao extends TreeDao<Basis> {
	
}