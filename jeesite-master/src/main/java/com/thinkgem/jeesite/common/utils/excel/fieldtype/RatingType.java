/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils.excel.fieldtype;

import java.util.List;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.rating.entity.Rating;
import com.thinkgem.jeesite.modules.rating.service.RatingService;

/**
 * 字段类型转换
 * @author Bill
 * @version 2018-07-08
 */
public class RatingType {
	
	private static RatingService ratingService = SpringContextHolder.getBean(RatingService.class);

	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		List<Rating> list = ratingService.findList(new Rating());
		if (list == null || list.isEmpty()){
			return null;
		}
		for (Rating e : list){
			if (StringUtils.trimToEmpty(val).equals(e.getName())){
				return e;
			}
		}
		return null;
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((Rating)val).getName() != null){
			return ((Rating)val).getName();
		}
		return "";
	}
}
