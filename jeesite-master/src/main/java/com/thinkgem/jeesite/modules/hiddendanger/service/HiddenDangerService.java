/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hiddendanger.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hiddendanger.entity.HiddenDanger;
import com.thinkgem.jeesite.modules.hiddendanger.dao.HiddenDangerDao;

/**
 * 隐患管理Service
 * @author Bill
 * @version 2018-06-10
 */
@Service
@Transactional(readOnly = true)
public class HiddenDangerService extends CrudService<HiddenDangerDao, HiddenDanger> {

	@Autowired
	private HiddenDangerDao hiddenDangerDao;
	
	public HiddenDanger get(String id) {
		return super.get(id);
	}
	
	public List<HiddenDanger> findList(HiddenDanger hiddenDanger) {
		return super.findList(hiddenDanger);
	}
	
	public List<HiddenDanger> findChildList() {
		return hiddenDangerDao.findChildList();
	}
	
	public Page<HiddenDanger> findPage(Page<HiddenDanger> page, HiddenDanger hiddenDanger) {
		page.setPageSize(5);
		return super.findPage(page, hiddenDanger);
	}
	
	@Transactional(readOnly = false)
	public void save(HiddenDanger hiddenDanger) {
		if (StringUtils.isBlank(hiddenDanger.getParentId())){
			hiddenDanger.setParentId("0");
		}
		super.save(hiddenDanger);
	}
	
	@Transactional(readOnly = false)
	public void delete(HiddenDanger hiddenDanger) {
		super.delete(hiddenDanger);
	}
	
}