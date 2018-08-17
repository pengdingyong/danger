/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rating.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rating.entity.Rating;
import com.thinkgem.jeesite.modules.rating.dao.RatingDao;

/**
 * 评估定级Service
 * @author Bill
 * @version 2018-06-10
 */
@Service
@Transactional(readOnly = true)
public class RatingService extends CrudService<RatingDao, Rating> {

	public Rating get(String id) {
		return super.get(id);
	}
	
	public List<Rating> findList(Rating rating) {
		return super.findList(rating);
	}
	
	public Page<Rating> findPage(Page<Rating> page, Rating rating) {
		return super.findPage(page, rating);
	}
	
	@Transactional(readOnly = false)
	public void save(Rating rating) {
		super.save(rating);
	}
	
	@Transactional(readOnly = false)
	public void delete(Rating rating) {
		super.delete(rating);
	}
	
}