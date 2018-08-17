/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.basis.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.modules.basis.dao.BasisDao;
import com.thinkgem.jeesite.modules.basis.entity.Basis;

/**
 * 评估依据Service
 * @author Bill
 * @version 2018-06-10
 */
@Service
@Transactional(readOnly = true)
public class BasisService extends TreeService<BasisDao, Basis> {

	public Basis get(String id) {
		return super.get(id);
	}
	
	public List<Basis> findList(Basis basis) {
		return super.findList(basis);
	}
	
	public Page<Basis> findPage(Page<Basis> page, Basis basis) {
		return super.findPage(page, basis);
	}
	
	@Transactional(readOnly = false)
	public void save(Basis basis) {
		String parentId = basis.getParent().getId();
		if (StringUtils.isBlank(parentId)){
			basis.setGrade("1");
		} else {
			String parentGrade = get(parentId).getGrade();
			int grade = Integer.parseInt(parentGrade) + 1;
			basis.setGrade(String.valueOf(grade));
		}
		super.save(basis);
	}
	
	@Transactional(readOnly = false)
	public void delete(Basis basis) {
		super.delete(basis);
	}
	
}