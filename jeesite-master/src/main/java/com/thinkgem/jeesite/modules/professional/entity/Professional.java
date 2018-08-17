/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.professional.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 专业分类Entity
 * @author Bill
 * @version 2018-06-10
 */
public class Professional extends DataEntity<Professional> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	
	public Professional() {
		super();
	}

	public Professional(String id){
		super(id);
	}

	@Length(min=0, max=64, message="名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}