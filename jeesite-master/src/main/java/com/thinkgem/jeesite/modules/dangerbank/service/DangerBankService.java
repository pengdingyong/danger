/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dangerbank.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.dangerbank.dao.DangerBankDao;
import com.thinkgem.jeesite.modules.dangerbank.entity.DangerBank;
import com.thinkgem.jeesite.modules.governancedocuments.dao.GovernanceDocumentsDao;
import com.thinkgem.jeesite.modules.governancedocuments.entity.GovernanceDocuments;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 隐患库管理Service
 * @author 隐患库管理
 * @version 2018-06-23
 */
@Service
@Transactional(readOnly = true)
public class DangerBankService extends CrudService<DangerBankDao, DangerBank> {
	
	@Autowired
	private GovernanceDocumentsDao governanceDocumentsDao;
	@Autowired
	private OfficeService officeService;
	

	public DangerBank get(String id) {
		return super.get(id);
	}
	
	public List<DangerBank> findList(DangerBank dangerBank) {
		return super.findList(dangerBank);
	}
	
	public Page<DangerBank> findPage(Page<DangerBank> page, DangerBank dangerBank) {
		return super.findPage(page, dangerBank);
	}
	
	@Transactional(readOnly = false)
	public void save(DangerBank dangerBank, MultipartHttpServletRequest multipartRequest,HttpServletRequest request) {
		dangerBank.setOffice(UserUtils.getUser().getOffice());
		if (dangerBank.getIsNewRecord()){
			Office company = officeService.get(dangerBank.getCompany().getId());
			if (company != null){
				String time = DateUtils.getDate()+" "+DateUtils.getTime();
				time = time.replace("-", "").replace(" ", "").replace(":", "");
				String code = company.getRemarks()+"-"+company.getCode()+time;
				dangerBank.setCode(code);
			}
		}
		super.save(dangerBank);
		String delFileIds = dangerBank.getDelFileIds();
		if(StringUtils.isNotBlank(delFileIds)){
			String[] filIds = delFileIds.split(",");
			for(String filId : filIds){
				governanceDocumentsDao.delete(new GovernanceDocuments(filId));
			}
		}
		List<MultipartFile> fileList  =  multipartRequest.getFiles("excel0");
		if(fileList != null){
        	for(MultipartFile file: fileList){
        		try {
        			this.uploadFile(file,dangerBank.getId(),request);
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}
        }
	}
	/**
	 * 
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	private String uploadFile(MultipartFile file,String dangerBankId,HttpServletRequest request) throws Exception {
		if(file == null || file.isEmpty()){
			return null;
		}
		String filename = file.getOriginalFilename();
		if(StringUtils.isBlank(filename)){  
        	return "上传的文件不存在!";
        }
		byte[] b = file.getBytes();
		int a = b.length;
		GovernanceDocuments doc = new GovernanceDocuments();
		doc.setId(IdGen.uuid());
		doc.setDangerBankId(dangerBankId);
		doc.setName(filename);
        String type = filename.substring(filename.lastIndexOf(".")+1,filename.length());
		doc.setType(type);
		doc.setContent(b);
		governanceDocumentsDao.insert(doc);
		return null;
	}
	@Transactional(readOnly = false)
	public void delete(DangerBank dangerBank) {
		super.delete(dangerBank);
		governanceDocumentsDao.deleteByDangerBankId(dangerBank.getId());
	}
	
	public List<DangerBank> getDangerChartByCompanyId(){
		return dao.getDangerChartByCompanyId();
	}
	
	public List<DangerBank> getChartByHiddenDangerParentId(){
		return dao.getChartByHiddenDangerParentId();
	}

	public List<DangerBank> getChartByHiddenDangerId() {
		return dao.getChartByHiddenDangerId();
	}
	
	public List<DangerBank> getDangerChartByHiddenDanger(DangerBank dangerBank) {
		return dao.getDangerChartByHiddenDanger(dangerBank);
	}
	
	public List<DangerBank> getDangerChartByFindTime(DangerBank dangerBank){
		return dao.getDangerChartByFindTime(dangerBank);
	}
	
	public List<DangerBank> getDangerChartByRating(){
		return dao.getDangerChartByRating();
	}
}