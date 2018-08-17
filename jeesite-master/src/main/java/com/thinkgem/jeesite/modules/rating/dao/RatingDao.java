/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rating.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rating.entity.Rating;

/**
 * 评估定级DAO接口
 * @author Bill
 * @version 2018-06-10
 */
@MyBatisDao
public interface RatingDao extends CrudDao<Rating> {
	
}