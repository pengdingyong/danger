/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils.excel.fieldtype;

import java.util.List;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.hiddendanger.service.HiddenDangerService;
import com.thinkgem.jeesite.modules.professional.entity.Professional;
import com.thinkgem.jeesite.modules.professional.service.ProfessionalService;

/**
 * 字段类型转换
 * @author Bill
 * @version 2018-07-08
 */
public class ProfessionalType {
	
	private static ProfessionalService professionalService = SpringContextHolder.getBean(ProfessionalService.class);

	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		List<Professional> list = professionalService.findList(new Professional());
		if (list == null || list.isEmpty()){
			return null;
		}
		for (Professional e : list){
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
		if (val != null && ((Professional)val).getName() != null){
			return ((Professional)val).getName();
		}
		return "";
	}
}
