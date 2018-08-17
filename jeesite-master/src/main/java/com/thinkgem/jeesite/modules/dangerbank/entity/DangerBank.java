/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dangerbank.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.basis.entity.Basis;
import com.thinkgem.jeesite.modules.governancedocuments.entity.GovernanceDocuments;
import com.thinkgem.jeesite.modules.hiddendanger.entity.HiddenDanger;
import com.thinkgem.jeesite.modules.professional.entity.Professional;
import com.thinkgem.jeesite.modules.rating.entity.Rating;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 隐患库管理Entity
 * @author 隐患库管理
 * @version 2018-06-23
 */
public class DangerBank extends DataEntity<DangerBank> {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final long serialVersionUID = 1L;
	private Office company;		// 单位
	private String code;		// 编号
	private String name;		
	private String source;		// 隐患来源
	private Professional professional;		// 专业分类
	private Date findTime;		// 发现时间
	private Date findTimeExl;		// 发现时间(Excel)
	private HiddenDanger hiddenDangerParent;		// 隐患分类
	private HiddenDanger hiddenDanger;		// 隐患子类
	private String dangerPosition;		// 隐患位置
	private String dangerResume;		// 隐患简述
	private Rating rating;		// 评估定级
	private Basis basis;		// 评估依据
	private String corrective;		// 整改措施
	private String personLiable;		// 责任人
	private Office office;		// 归属部门
	private Date planDate;		// 计划时间
	private Date planDateExl;		// 计划时间(Excel)
	private Date completeDate;		// 完成时间
	private Date completeDateExl;		// 完成时间(Excel)
	private String completeSituation;		// 完成情况
	private String unClearDanger;		// 未消除的隐患
	private String rectificationProgressMonth;		// 当月整改进展情况
	private String supervisor;		// 督办人
	private String remark;		// 备注
	private Date beginFindTime;		// 开始 发现时间
	private Date endFindTime;		// 结束 发现时间
	private List<GovernanceDocuments> documentsList;	//隐患库文档
	private String delFileIds;
	private String chartType;    //图表类型
	private int count;    //隐患数
	private String hiddenDangerType;    //1：隐患父类2：隐患子类
	private String year;
	
	public DangerBank() {
		super();
	}

	public DangerBank(String id){
		super(id);
	}

	@ExcelField(title="隐患名称", align=2, sort=30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="评估定级", align=2, sort=110)
	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	@ExcelField(title="评估依据", align=2, sort=120)
	public Basis getBasis() {
		return basis;
	}

	public void setBasis(Basis basis) {
		this.basis = basis;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getHiddenDangerType() {
		return hiddenDangerType;
	}

	public void setHiddenDangerType(String hiddenDangerType) {
		this.hiddenDangerType = hiddenDangerType;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public String getDelFileIds() {
		return delFileIds;
	}

	public void setDelFileIds(String delFileIds) {
		this.delFileIds = delFileIds;
	}

	@ExcelField(title="单位", align=2, sort=10)
	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	@Length(min=0, max=64, message="编号长度必须介于 0 和 64 之间")
	@ExcelField(title="隐患编号", align=2, sort=20)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=64, message="隐患来源长度必须介于 0 和 64 之间")
	@ExcelField(title="隐患（问题）来源", align=2, sort=40)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	@ExcelField(title="专业分类", align=2, sort=50)
	public Professional getProfessional() {
		return professional;
	}

	public void setProfessional(Professional professional) {
		this.professional = professional;
	}

	@ExcelField(title="发现时间", align=2, sort=60)
	public String getFindTimeExl() {
		if (findTime != null){
			return sdf.format(findTime);
		}
		return "";
	}

	public void setFindTimeExl(String findTime) {
		if (StringUtils.isNotBlank(findTime)){
			try {
				this.findTime = sdf.parse(findTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFindTime() {
		return findTime;
	}
	
	public void setFindTime(Date findTime) {
		this.findTime = findTime;
	}

	@Length(min=0, max=200, message="隐患位置长度必须介于 0 和 200 之间")
	@ExcelField(title="隐患位置", align=2, sort=90)
	public String getDangerPosition() {
		return dangerPosition;
	}

	@ExcelField(title="隐患（问题）分类", align=2, sort=70)
	public HiddenDanger getHiddenDangerParent() {
		return hiddenDangerParent;
	}

	public void setHiddenDangerParent(HiddenDanger hiddenDangerParent) {
		this.hiddenDangerParent = hiddenDangerParent;
	}

	@ExcelField(title="隐患（问题）子类", align=2, sort=80)
	public HiddenDanger getHiddenDanger() {
		return hiddenDanger;
	}

	public void setHiddenDanger(HiddenDanger hiddenDanger) {
		this.hiddenDanger = hiddenDanger;
	}

	public void setDangerPosition(String dangerPosition) {
		this.dangerPosition = dangerPosition;
	}
	
	@Length(min=0, max=64, message="隐患简述长度必须介于 0 和 64 之间")
	@ExcelField(title="隐患简述", align=2, sort=100)
	public String getDangerResume() {
		return dangerResume;
	}

	public void setDangerResume(String dangerResume) {
		this.dangerResume = dangerResume;
	}
	
	@Length(min=0, max=200, message="整改措施长度必须介于 0 和 200 之间")
	@ExcelField(title="整改措施", align=2, sort=130)
	public String getCorrective() {
		return corrective;
	}

	public void setCorrective(String corrective) {
		this.corrective = corrective;
	}
	
	@Length(min=0, max=64, message="责任人长度必须介于 0 和 64 之间")
	@ExcelField(title="责任人", align=2, sort=140)
	public String getPersonLiable() {
		return personLiable;
	}

	public void setPersonLiable(String personLiable) {
		this.personLiable = personLiable;
	}

//	@ExcelField(title="归属部门", align=2, sort=150)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	@ExcelField(title="计划时间", align=2, sort=160)
	public String getPlanDateExl() {
		if (planDate != null){
			return sdf.format(planDate);
		}
		return "";
	}

	public void setPlanDateExl(String planDate) {
		if (StringUtils.isNotBlank(planDate)){
			try {
				this.planDate = sdf.parse(planDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPlanDate() {
		return planDate;
	}
	
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="完成时间", align=2, sort=170)
	public String getCompleteDateExl() {
		if (completeDate != null){
			return sdf.format(completeDate);
		}
		return "";
	}
	
	public void setCompleteDateExl(String completeDate) {
		if (StringUtils.isNotBlank(completeDate)){
			try {
				this.completeDate = sdf.parse(completeDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}
	
	@Length(min=0, max=64, message="完成情况长度必须介于 0 和 64 之间")
	@ExcelField(title="完成情况", align=2, sort=180)
	public String getCompleteSituation() {
		return completeSituation;
	}

	public void setCompleteSituation(String completeSituation) {
		this.completeSituation = completeSituation;
	}
	
	@Length(min=0, max=64, message="未消除的隐患长度必须介于 0 和 64 之间")
	@ExcelField(title="未消除的隐患", align=2, sort=190)
	public String getUnClearDanger() {
		return unClearDanger;
	}

	public void setUnClearDanger(String unClearDanger) {
		this.unClearDanger = unClearDanger;
	}
	
	@Length(min=0, max=64, message="当月整改进展情况长度必须介于 0 和 64 之间")
	@ExcelField(title="当月整改进展情况", align=2, sort=200)
	public String getRectificationProgressMonth() {
		return rectificationProgressMonth;
	}

	public void setRectificationProgressMonth(String rectificationProgressMonth) {
		this.rectificationProgressMonth = rectificationProgressMonth;
	}
	
	@Length(min=0, max=64, message="督办人长度必须介于 0 和 64 之间")
	@ExcelField(title="督办人", align=2, sort=200)
	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}
	
	@Length(min=0, max=400, message="备注长度必须介于 0 和 400 之间")
	@ExcelField(title="备注", align=2, sort=200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Date getBeginFindTime() {
		return beginFindTime;
	}

	public void setBeginFindTime(Date beginFindTime) {
		this.beginFindTime = beginFindTime;
	}
	
	public Date getEndFindTime() {
		return endFindTime;
	}

	public void setEndFindTime(Date endFindTime) {
		this.endFindTime = endFindTime;
	}

	public List<GovernanceDocuments> getDocumentsList() {
		return documentsList;
	}

	public void setDocumentsList(List<GovernanceDocuments> documentsList) {
		this.documentsList = documentsList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
		
}