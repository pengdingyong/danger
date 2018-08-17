/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.governancedocuments.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 隐患库文档Entity
 * @author Bill
 * @version 2018-06-23
 */
public class GovernanceDocuments extends DataEntity<GovernanceDocuments> {
	
	private static final long serialVersionUID = 1L;
	private String dangerBankId;		// 隐患库ID
	private String name;		// 名字
	private String type;		// 后缀类型
	private byte[] content;		// 内容
	
	public GovernanceDocuments() {
		super();
	}

	public GovernanceDocuments(String id){
		super(id);
	}

	@Length(min=0, max=64, message="隐患库ID长度必须介于 0 和 64 之间")
	public String getDangerBankId() {
		return dangerBankId;
	}

	public void setDangerBankId(String dangerBankId) {
		this.dangerBankId = dangerBankId;
	}
	
	@Length(min=0, max=64, message="名字长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="后缀类型长度必须介于 0 和 64 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
	
}