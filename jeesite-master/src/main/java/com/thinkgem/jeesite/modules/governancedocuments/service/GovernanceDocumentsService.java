/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.governancedocuments.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.governancedocuments.entity.GovernanceDocuments;
import com.thinkgem.jeesite.modules.governancedocuments.dao.GovernanceDocumentsDao;

/**
 * 隐患库文档Service
 * @author Bill
 * @version 2018-06-23
 */
@Service
@Transactional(readOnly = true)
public class GovernanceDocumentsService extends CrudService<GovernanceDocumentsDao, GovernanceDocuments> {

	public GovernanceDocuments getContentById(String id) {
		return dao.getContentById(id);
	}
	
	public GovernanceDocuments get(String id) {
		return super.get(id);
	}
	
	public List<GovernanceDocuments> findList(GovernanceDocuments governanceDocuments) {
		return super.findList(governanceDocuments);
	}
	
	public Page<GovernanceDocuments> findPage(Page<GovernanceDocuments> page, GovernanceDocuments governanceDocuments) {
		return super.findPage(page, governanceDocuments);
	}
	
	@Transactional(readOnly = false)
	public void save(GovernanceDocuments governanceDocuments) {
		super.save(governanceDocuments);
	}
	
	@Transactional(readOnly = false)
	public void delete(GovernanceDocuments governanceDocuments) {
		super.delete(governanceDocuments);
	}
	
}