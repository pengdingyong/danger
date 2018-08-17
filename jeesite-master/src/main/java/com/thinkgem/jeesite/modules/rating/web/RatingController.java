/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rating.web;

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
import com.thinkgem.jeesite.modules.rating.entity.Rating;
import com.thinkgem.jeesite.modules.rating.service.RatingService;

/**
 * 评估定级Controller
 * @author Bill
 * @version 2018-06-10
 */
@Controller
@RequestMapping(value = "${adminPath}/rating/rating")
public class RatingController extends BaseController {

	@Autowired
	private RatingService ratingService;
	
	@ModelAttribute
	public Rating get(@RequestParam(required=false) String id) {
		Rating entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ratingService.get(id);
		}
		if (entity == null){
			entity = new Rating();
		}
		return entity;
	}
	
	@RequiresPermissions("rating:rating:view")
	@RequestMapping(value = {"list", ""})
	public String list(Rating rating, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Rating> page = ratingService.findPage(new Page<Rating>(request, response), rating); 
		model.addAttribute("page", page);
		return "modules/rating/ratingList";
	}

	@RequiresPermissions("rating:rating:view")
	@RequestMapping(value = "form")
	public String form(Rating rating, Model model) {
		model.addAttribute("rating", rating);
		return "modules/rating/ratingForm";
	}

	@RequiresPermissions("rating:rating:edit")
	@RequestMapping(value = "save")
	public String save(Rating rating, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, rating)){
			return form(rating, model);
		}
		ratingService.save(rating);
		addMessage(redirectAttributes, "保存评估定级成功");
		return "redirect:"+Global.getAdminPath()+"/rating/rating/?repage";
	}
	
	@RequiresPermissions("rating:rating:edit")
	@RequestMapping(value = "delete")
	public String delete(Rating rating, RedirectAttributes redirectAttributes) {
		ratingService.delete(rating);
		addMessage(redirectAttributes, "删除评估定级成功");
		return "redirect:"+Global.getAdminPath()+"/rating/rating/?repage";
	}

}