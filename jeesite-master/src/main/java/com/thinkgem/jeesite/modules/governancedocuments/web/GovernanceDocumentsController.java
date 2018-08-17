/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.governancedocuments.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.governancedocuments.entity.GovernanceDocuments;
import com.thinkgem.jeesite.modules.governancedocuments.service.GovernanceDocumentsService;

/**
 * 隐患库文档Controller
 * @author Bill
 * @version 2018-06-23
 */
@Controller
@RequestMapping(value = "${adminPath}/governancedocuments/governanceDocuments")
public class GovernanceDocumentsController extends BaseController {

	@Autowired
	private GovernanceDocumentsService governanceDocumentsService;
	
	@ModelAttribute
	public GovernanceDocuments get(@RequestParam(required=false) String id) {
		GovernanceDocuments entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = governanceDocumentsService.get(id);
		}
		if (entity == null){
			entity = new GovernanceDocuments();
		}
		return entity;
	}
	
	@RequiresPermissions("governancedocuments:governanceDocuments:view")
	@RequestMapping(value = {"list", ""})
	public String list(GovernanceDocuments governanceDocuments, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GovernanceDocuments> page = governanceDocumentsService.findPage(new Page<GovernanceDocuments>(request, response), governanceDocuments); 
		model.addAttribute("page", page);
		return "modules/governancedocuments/governanceDocumentsList";
	}

	@RequiresPermissions("governancedocuments:governanceDocuments:view")
	@RequestMapping(value = "form")
	public String form(GovernanceDocuments governanceDocuments, Model model) {
		model.addAttribute("governanceDocuments", governanceDocuments);
		return "modules/governancedocuments/governanceDocumentsForm";
	}

	@RequiresPermissions("governancedocuments:governanceDocuments:edit")
	@RequestMapping(value = "save")
	public String save(GovernanceDocuments governanceDocuments, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, governanceDocuments)){
			return form(governanceDocuments, model);
		}
		governanceDocumentsService.save(governanceDocuments);
		addMessage(redirectAttributes, "保存隐患库文档成功");
		return "redirect:"+Global.getAdminPath()+"/governancedocuments/governanceDocuments/?repage";
	}
	
	@RequiresPermissions("governancedocuments:governanceDocuments:edit")
	@RequestMapping(value = "delete")
	public String delete(GovernanceDocuments governanceDocuments, RedirectAttributes redirectAttributes) {
		governanceDocumentsService.delete(governanceDocuments);
		addMessage(redirectAttributes, "删除隐患库文档成功");
		return "redirect:"+Global.getAdminPath()+"/governancedocuments/governanceDocuments/?repage";
	}

	@RequestMapping(value = "downFile")
	public void downFile(HttpServletRequest requset,HttpServletResponse response) {
		InputStream in = null;
		OutputStream outputSream = null;
		try {
			String id = requset.getParameter("fileid");
			GovernanceDocuments doc = governanceDocumentsService.get(id);
			String filename = doc.getName();
			String name = filename.substring(0,filename.lastIndexOf(".")-1);
			name = URLEncoder.encode(name,"utf-8");
			name = name.replace("+", "%20");
			String type = filename.substring(filename.lastIndexOf("."));
			response.setHeader("Content-Disposition", "attachment; filename="+name+type);
//			response.setContentType("xlsx/xls");  
		    response.setCharacterEncoding("UTF-8");  
		    outputSream = response.getOutputStream();
		    byte[] content = governanceDocumentsService.getContentById(id).getContent();
		    in = new ByteArrayInputStream(content);
		    int len = 0;  
		    byte[] buf = new byte[1024];  
		    while ((len = in.read(buf, 0, 1024)) != -1) {  
		        outputSream.write(buf, 0, len);  
		    }  
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			 try {
				outputSream.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}
}