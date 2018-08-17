/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.basis.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.TreeEntity;

/**
 * 评估依据Entity
 * @author Bill
 * @version 2018-06-10
 */
public class Basis extends TreeEntity<Basis> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String parentIds;		// parent_ids
	private String grade;		// grade
	private String urlId;
	private String urlParentIds;
	private String content; //内容
	
	public Basis() {
		super();
	}

	public Basis(String id){
		super(id);
	}

	@Length(min=0, max=400, message="名称长度必须介于 0 和 400 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrlParentIds() {
		return urlParentIds;
	}

	public String getUrlId() {
		return urlId;
	}

	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}

	public void setUrlParentIds(String urlParentIds) {
		this.urlParentIds = urlParentIds;
	}

	@Length(min=0, max=192, message="parent_ids长度必须介于 0 和 192 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=0, max=64, message="grade长度必须介于 0 和 64 之间")
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

//	@Override
//	@JsonBackReference
//	@NotNull
	public Basis getParent() {
		return parent;
	}

	public void setParent(Basis parent) {
		this.parent = parent;
	}
	
}