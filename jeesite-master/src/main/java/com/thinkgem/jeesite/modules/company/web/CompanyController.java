/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.company.web;

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
import com.thinkgem.jeesite.modules.company.entity.Company;
import com.thinkgem.jeesite.modules.company.service.CompanyService;

/**
 * 单位Controller
 * @author Bill
 * @version 2018-06-10
 */
@Controller
@RequestMapping(value = "${adminPath}/company/company")
public class CompanyController extends BaseController {

	@Autowired
	private CompanyService companyService;
	
	@ModelAttribute
	public Company get(@RequestParam(required=false) String id) {
		Company entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = companyService.get(id);
		}
		if (entity == null){
			entity = new Company();
		}
		return entity;
	}
	
	@RequiresPermissions("company:company:view")
	@RequestMapping(value = {"list", ""})
	public String list(Company company, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Company> page = companyService.findPage(new Page<Company>(request, response), company); 
		model.addAttribute("page", page);
		return "modules/company/companyList";
	}

	@RequiresPermissions("company:company:view")
	@RequestMapping(value = "form")
	public String form(Company company, Model model) {
		model.addAttribute("company", company);
		return "modules/company/companyForm";
	}

	@RequiresPermissions("company:company:edit")
	@RequestMapping(value = "save")
	public String save(Company company, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, company)){
			return form(company, model);
		}
		companyService.save(company);
		addMessage(redirectAttributes, "保存单位成功");
		return "redirect:"+Global.getAdminPath()+"/company/company/?repage";
	}
	
	@RequiresPermissions("company:company:edit")
	@RequestMapping(value = "delete")
	public String delete(Company company, RedirectAttributes redirectAttributes) {
		companyService.delete(company);
		addMessage(redirectAttributes, "删除单位成功");
		return "redirect:"+Global.getAdminPath()+"/company/company/?repage";
	}

}