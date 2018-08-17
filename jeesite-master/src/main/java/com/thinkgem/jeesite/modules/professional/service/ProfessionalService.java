/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.professional.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.professional.entity.Professional;
import com.thinkgem.jeesite.modules.professional.dao.ProfessionalDao;

/**
 * 专业分类Service
 * @author Bill
 * @version 2018-06-10
 */
@Service
@Transactional(readOnly = true)
public class ProfessionalService extends CrudService<ProfessionalDao, Professional> {

	public Professional get(String id) {
		return super.get(id);
	}
	
	public List<Professional> findList(Professional professional) {
		return super.findList(professional);
	}
	
	public Page<Professional> findPage(Page<Professional> page, Professional professional) {
		return super.findPage(page, professional);
	}
	
	@Transactional(readOnly = false)
	public void save(Professional professional) {
		super.save(professional);
	}
	
	@Transactional(readOnly = false)
	public void delete(Professional professional) {
		super.delete(professional);
	}
	
}