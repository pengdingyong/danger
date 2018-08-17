/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hiddendanger.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.hiddendanger.entity.HiddenDanger;
import com.thinkgem.jeesite.modules.hiddendanger.service.HiddenDangerService;

/**
 * 隐患管理Controller
 * @author Bill
 * @version 2018-06-10
 */
@Controller
@RequestMapping(value = "${adminPath}/hiddendanger/hiddenDanger")
public class HiddenDangerController extends BaseController {

	@Autowired
	private HiddenDangerService hiddenDangerService;
	
	@ModelAttribute
	public HiddenDanger get(@RequestParam(required=false) String id) {
		HiddenDanger entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hiddenDangerService.get(id);
		}
		if (entity == null){
			entity = new HiddenDanger();
		}
		return entity;
	}
	
	@RequiresPermissions("hiddendanger:hiddenDanger:view")
	@RequestMapping(value = {"list", ""})
	public String list(HiddenDanger hiddenDanger, HttpServletRequest request, HttpServletResponse response, Model model) {
		hiddenDanger.setParentId("0");
		Page<HiddenDanger> page = hiddenDangerService.findPage(new Page<HiddenDanger>(request, response), hiddenDanger);
		model.addAttribute("page", page); 
		List<HiddenDanger> hiddenDangerList = hiddenDangerService.findChildList();
		page.getList().addAll(hiddenDangerList);
		return "modules/hiddendanger/hiddenDangerList";
	}

	@RequestMapping(value = "getHiddengDangerByParentId")
	@ResponseBody
	public List<HiddenDanger> getHiddengDangerByParentId(HiddenDanger hiddenDanger) {
		return hiddenDangerService.findList(hiddenDanger);
	}
	
	@RequiresPermissions("hiddendanger:hiddenDanger:view")
	@RequestMapping(value = "form")
	public String form(HiddenDanger hiddenDanger, Model model) {
		String parentId = hiddenDanger.getParentId();
		if (StringUtils.isNotBlank(parentId) && !"0".equals(parentId)){
			String parentName = hiddenDangerService.get(parentId).getName();
			hiddenDanger.setParentName(parentName);
		}
		model.addAttribute("hiddenDanger", hiddenDanger);
		return "modules/hiddendanger/hiddenDangerForm";
	}

	@RequiresPermissions("hiddendanger:hiddenDanger:edit")
	@RequestMapping(value = "save")
	public String save(HiddenDanger hiddenDanger, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hiddenDanger)){
			return form(hiddenDanger, model);
		}
		hiddenDangerService.save(hiddenDanger);
		addMessage(redirectAttributes, "保存隐患成功");
		return "redirect:"+Global.getAdminPath()+"/hiddendanger/hiddenDanger/?repage";
	}
	
	@RequiresPermissions("hiddendanger:hiddenDanger:edit")
	@RequestMapping(value = "delete")
	public String delete(HiddenDanger hiddenDanger, RedirectAttributes redirectAttributes) {
		hiddenDangerService.delete(hiddenDanger);
		addMessage(redirectAttributes, "删除隐患成功");
		return "redirect:"+Global.getAdminPath()+"/hiddendanger/hiddenDanger/?repage";
	}

}