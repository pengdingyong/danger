/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.professional.web;

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
import com.thinkgem.jeesite.modules.professional.entity.Professional;
import com.thinkgem.jeesite.modules.professional.service.ProfessionalService;

/**
 * 专业分类Controller
 * @author Bill
 * @version 2018-06-10
 */
@Controller
@RequestMapping(value = "${adminPath}/professional/professional")
public class ProfessionalController extends BaseController {

	@Autowired
	private ProfessionalService professionalService;
	
	@ModelAttribute
	public Professional get(@RequestParam(required=false) String id) {
		Professional entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = professionalService.get(id);
		}
		if (entity == null){
			entity = new Professional();
		}
		return entity;
	}
	
	@RequiresPermissions("professional:professional:view")
	@RequestMapping(value = {"list", ""})
	public String list(Professional professional, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Professional> page = professionalService.findPage(new Page<Professional>(request, response), professional); 
		model.addAttribute("page", page);
		return "modules/professional/professionalList";
	}

	@RequiresPermissions("professional:professional:view")
	@RequestMapping(value = "form")
	public String form(Professional professional, Model model) {
		model.addAttribute("professional", professional);
		return "modules/professional/professionalForm";
	}

	@RequiresPermissions("professional:professional:edit")
	@RequestMapping(value = "save")
	public String save(Professional professional, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, professional)){
			return form(professional, model);
		}
		professionalService.save(professional);
		addMessage(redirectAttributes, "保存专业分类成功");
		return "redirect:"+Global.getAdminPath()+"/professional/professional/?repage";
	}
	
	@RequiresPermissions("professional:professional:edit")
	@RequestMapping(value = "delete")
	public String delete(Professional professional, RedirectAttributes redirectAttributes) {
		professionalService.delete(professional);
		addMessage(redirectAttributes, "删除专业分类成功");
		return "redirect:"+Global.getAdminPath()+"/professional/professional/?repage";
	}

}