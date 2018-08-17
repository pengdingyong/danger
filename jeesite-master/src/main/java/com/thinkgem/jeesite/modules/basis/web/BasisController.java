/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.basis.web;

import java.util.List;
import java.util.Map;

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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.basis.entity.Basis;
import com.thinkgem.jeesite.modules.basis.service.BasisService;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 评估依据Controller
 * @author Bill
 * @version 2018-06-10
 */
@Controller
@RequestMapping(value = "${adminPath}/basis/basis")
public class BasisController extends BaseController {

	@Autowired
	private BasisService basisService;
	
	@ModelAttribute
	public Basis get(@RequestParam(required=false) String id) {
		Basis entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = basisService.get(id);
		}
		if (entity == null){
			entity = new Basis();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {""})
	public String index(Office office, Model model) {
		return "modules/basis/basisIndex";
	}
	
	@RequiresPermissions("basis:basis:view")
	@RequestMapping(value = {"list"})
	public String list(Basis basis, Model model) {
		model.addAttribute("list", basisService.findList(basis));
		return "modules/basis/basisList";
	}

	@RequiresPermissions("basis:basis:view")
	@RequestMapping(value = "form")
	public String form(Basis basis, Model model) {
		if (basis.getParent()!= null && basis.getParent().getId() != null){
			basis.setParent(basisService.get(basis.getParent().getId()));
		}
		model.addAttribute("basis", basis);
		return "modules/basis/basisForm";
	}

	@RequiresPermissions("basis:basis:edit")
	@RequestMapping(value = "save")
	public String save(Basis basis, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, basis)){
			return form(basis, model);
		}
		String urlId = basis.getUrlId();
		String urlParentIds = basis.getUrlParentIds();
		basisService.save(basis);
		addMessage(redirectAttributes, "保存评估依据成功");
		return "redirect:"+Global.getAdminPath()+"/basis/basis/list?id="+urlId+"&parentIds="+urlParentIds;
	}
	
	@RequiresPermissions("basis:basis:edit")
	@RequestMapping(value = "delete")
	public String delete(Basis basis, RedirectAttributes redirectAttributes) {
		basisService.delete(basis);
		addMessage(redirectAttributes, "删除评估依据成功");
		return "redirect:"+Global.getAdminPath()+"/basis/basis/list?id="+basis.getUrlId()+"&parentIds="+basis.getUrlParentIds();
	}

	/**
	 * 获取评估依据JSON数据。
	 * @param grade 显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("basis:basis:view")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Basis> list = basisService.findList(new Basis());
		for (int i=0; i<list.size(); i++){
			Basis e = list.get(i);
//			if ("3".equals(e.getGrade())){
//				continue;
//			}
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentId());
			map.put("pIds", e.getParentIds());
			map.put("name", e.getName());
			mapList.add(map);
		}
		return mapList;
	}
	@RequestMapping(value = "allRreeData")
	@ResponseBody
	public List<Map<String, Object>> allRreeData(HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Basis> list = basisService.findList(new Basis());
		for (int i=0; i<list.size(); i++){
			Basis e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentId());
			map.put("pIds", e.getParentIds());
			map.put("name", e.getName());
			mapList.add(map);
		}
		return mapList;
	}
}