/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils.excel.fieldtype;

import java.util.List;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.basis.entity.Basis;
import com.thinkgem.jeesite.modules.basis.service.BasisService;

/**
 * 字段类型转换
 * @author Bill
 * @version 2018-07-08
 */
public class BasisType {
	
	private static BasisService basisService = SpringContextHolder.getBean(BasisService.class);

	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		List<Basis> list = basisService.findList(new Basis());
		if (list == null || list.isEmpty()){
			return null;
		}
		for (Basis e : list){
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
		if (val != null && ((Basis)val).getName() != null){
			return ((Basis)val).getName();
		}
		return "";
	}
}
