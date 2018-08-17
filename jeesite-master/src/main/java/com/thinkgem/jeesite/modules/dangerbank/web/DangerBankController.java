/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dangerbank.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.dangerbank.entity.DangerBank;
import com.thinkgem.jeesite.modules.dangerbank.service.DangerBankService;
import com.thinkgem.jeesite.modules.governancedocuments.entity.GovernanceDocuments;
import com.thinkgem.jeesite.modules.governancedocuments.service.GovernanceDocumentsService;
import com.thinkgem.jeesite.modules.hiddendanger.entity.HiddenDanger;
import com.thinkgem.jeesite.modules.hiddendanger.service.HiddenDangerService;
import com.thinkgem.jeesite.modules.professional.entity.Professional;
import com.thinkgem.jeesite.modules.professional.service.ProfessionalService;
import com.thinkgem.jeesite.modules.rating.entity.Rating;
import com.thinkgem.jeesite.modules.rating.service.RatingService;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 隐患库管理Controller
 * @author 隐患库管理
 * @version 2018-06-23
 */
@Controller
@RequestMapping(value = "${adminPath}/dangerbank/dangerBank")
public class DangerBankController extends BaseController {

	@Autowired
	private DangerBankService dangerBankService;
	@Autowired
	private ProfessionalService professionalService;
	@Autowired
	private HiddenDangerService hiddenDangerService;
	@Autowired
	private RatingService ratingService;
	@Autowired
	private GovernanceDocumentsService governanceDocumentsService;
	@Autowired 
	private OfficeService officeService;
	
	@ModelAttribute
	public DangerBank get(@RequestParam(required=false) String id) {
		DangerBank entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dangerBankService.get(id);
		}
		if (entity == null){
			entity = new DangerBank();
		}
		return entity;
	}
	
	@RequiresPermissions("dangerbank:dangerBank:view")
	@RequestMapping(value = {"list", ""})
	public String list(DangerBank dangerBank, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DangerBank> page = dangerBankService.findPage(new Page<DangerBank>(request, response), dangerBank); 
		model.addAttribute("page", page);
		model.addAttribute("professionalList", professionalService.findList(new Professional()));
		HiddenDanger hiddenDangerQuery = new HiddenDanger();
		hiddenDangerQuery.setParentId("0");
		model.addAttribute("hiddenDangerList", hiddenDangerService.findList(hiddenDangerQuery));
		User user = UserUtils.getUser();
		model.addAttribute("user", user);
		return "modules/dangerbank/dangerBankList";
	}
	
	@RequiresPermissions("dangerbank:dangerBank:view")
	@RequestMapping(value = "chart")
	public String chart(DangerBank dangerBank, HttpServletRequest request, HttpServletResponse response, Model model) {
		HiddenDanger hiddenDangerQuery = new HiddenDanger();
		hiddenDangerQuery.setParentId("0");
		model.addAttribute("hiddenDangerList", hiddenDangerService.findList(hiddenDangerQuery));
		String year = DateUtils.getYear();
		List<DangerBank> yearList = new ArrayList<DangerBank>();
		DangerBank temp = new DangerBank();
		temp.setYear(year);
		yearList.add(temp);
		for(int i = 1;i<=10;i++){
			DangerBank temp2 = new DangerBank();
			temp2.setYear(String.valueOf(Integer.parseInt(year)-i));
			yearList.add(temp2);
		}
		model.addAttribute("yearList", yearList);
		return "modules/dangerbank/dangerBankChart";
	}
	
	@RequestMapping(value = "getDangerChart")
	@ResponseBody
	public Map<String,Object> getDangerChart(DangerBank dangerBank) {
		String chartType = dangerBank.getChartType();
		Map<String,Object> map = new HashMap<String,Object>();
		List<String> xAxisList = new ArrayList<String>();
		List<Integer> yAxisList = new ArrayList<Integer>();
		if("1".equals(chartType)){
			String hiddenDangerType = dangerBank.getHiddenDangerType();
			if("1".equals(hiddenDangerType)){
				List<DangerBank> list = dangerBankService.getChartByHiddenDangerParentId();
				for(DangerBank temp : list){
					String hiddenDangerParentName = temp.getHiddenDangerParent().getName();
					xAxisList.add(hiddenDangerParentName);
					int count = temp.getCount();
					yAxisList.add(count);
				}
			}
			if("2".equals(hiddenDangerType)){
				List<DangerBank> list = dangerBankService.getChartByHiddenDangerId();
				for(DangerBank temp : list){
					xAxisList.add(temp.getHiddenDanger().getName());
					int count = temp.getCount();
					yAxisList.add(count);
				}
			}
		} else if ("2".equals(chartType)){
			List<DangerBank> list = dangerBankService.getDangerChartByCompanyId();
			for(DangerBank temp : list){
				xAxisList.add(temp.getCompany().getName());
				int count = temp.getCount();
				yAxisList.add(count);
			}
		} else if ("3".equals(chartType)){
			List<DangerBank> list = dangerBankService.getDangerChartByHiddenDanger(dangerBank);
			for(DangerBank temp : list){
				xAxisList.add(temp.getCompany().getName());
				int count = temp.getCount();
				yAxisList.add(count);
			}
		} else if ("4".equals(chartType)){
			List<DangerBank> list = dangerBankService.getDangerChartByFindTime(dangerBank);
			for(DangerBank temp : list){
				xAxisList.add(temp.getYear());
				int count = temp.getCount();
				yAxisList.add(count);
			}
		} else if ("5".equals(chartType)){
			List<DangerBank> list = dangerBankService.getDangerChartByRating();
			List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
			for(DangerBank temp : list){
				xAxisList.add(temp.getRating().getName());
				int count = temp.getCount();
				yAxisList.add(count);
				Map<String,Object> tempMap = new HashMap<String,Object>();
				tempMap.put("value", count);
				tempMap.put("name", temp.getRating().getName());
				mapList.add(tempMap);
				map.put("ratingList", mapList);
			}
		}
		map.put("xAxisList", xAxisList);
		map.put("yAxisList", yAxisList);
		return map;
	}

	@RequiresPermissions("dangerbank:dangerBank:view")
	@RequestMapping(value = "form")
	public String form(DangerBank dangerBank, Model model) {
		model.addAttribute("dangerBank", dangerBank);
		model.addAttribute("professionalList", professionalService.findList(new Professional()));
		HiddenDanger hiddenDangerQuery = new HiddenDanger();
		hiddenDangerQuery.setParentId("0");
		model.addAttribute("hiddenDangerList", hiddenDangerService.findList(hiddenDangerQuery));
		model.addAttribute("ratingList", ratingService.findList(new Rating()));
		GovernanceDocuments docQuery = new GovernanceDocuments();
		if (StringUtils.isNotBlank(dangerBank.getId())){
			docQuery.setDangerBankId(dangerBank.getId());
			model.addAttribute("docList", governanceDocumentsService.findList(docQuery));
		}
		return "modules/dangerbank/dangerBankForm";
	}
	
	@RequiresPermissions("dangerbank:dangerBank:view")
	@RequestMapping(value = "view")
	public String view(DangerBank dangerBank, Model model) {
		model.addAttribute("dangerBank", dangerBank);
		model.addAttribute("professionalList", professionalService.findList(new Professional()));
		HiddenDanger hiddenDangerQuery = new HiddenDanger();
		hiddenDangerQuery.setParentId("0");
		model.addAttribute("hiddenDangerList", hiddenDangerService.findList(hiddenDangerQuery));
		model.addAttribute("ratingList", ratingService.findList(new Rating()));
		GovernanceDocuments docQuery = new GovernanceDocuments();
		if (StringUtils.isNotBlank(dangerBank.getId())){
			docQuery.setDangerBankId(dangerBank.getId());
			model.addAttribute("docList", governanceDocumentsService.findList(docQuery));
		}
		return "modules/dangerbank/dangerBankView";
	}

	@RequiresPermissions("dangerbank:dangerBank:edit")
	@RequestMapping(value = "save")
	public String save(DangerBank dangerBank, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, dangerBank)){
			return form(dangerBank, model);
		}
		//转型为MultipartHttpRequest
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
		dangerBankService.save(dangerBank,multipartRequest,request);
		addMessage(redirectAttributes, "保存隐患成功");
		return "redirect:"+Global.getAdminPath()+"/dangerbank/dangerBank/?repage";
	}
	
	@RequiresPermissions("dangerbank:dangerBank:edit")
	@RequestMapping(value = "delete")
	public String delete(DangerBank dangerBank, RedirectAttributes redirectAttributes) {
		dangerBankService.delete(dangerBank);
		addMessage(redirectAttributes, "删除隐患成功");
		return "redirect:"+Global.getAdminPath()+"/dangerbank/dangerBank/?repage";
	}
	
	/**
	 * 下载导入隐患库数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("dangerbank:dangerBank:edit")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "隐患库数据导入模板.xlsx";
    		List<User> list = Lists.newArrayList();
    		new ExportExcel("隐患库数据", DangerBank.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/dangerbank/dangerBank/?repage";
    }

	/**
	 * 导出隐患库数据
	 * @param dangerBank
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("dangerbank:dangerBank:edit")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DangerBank dangerBank, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "隐患库数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DangerBank> page = dangerBankService.findPage(new Page<DangerBank>(request, response), dangerBank);
            List<DangerBank> list = page.getList();
    		new ExportExcel("隐患库数据", DangerBank.class).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出隐患库数据失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/dangerbank/dangerBank/?repage";
    }
	
	/**
	 * 导入隐患库数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("dangerbank:dangerBank:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/dangerbank/dangerBank/?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DangerBank> list = ei.getDataList(DangerBank.class);
			for (DangerBank dangerBank : list){
				try{
					BeanValidators.validateWithException(validator, dangerBank);
					dangerBank.setOffice(UserUtils.getUser().getOffice());
					String code = dangerBank.getCode();
					if(StringUtils.isNotBlank(code)){
						DangerBank dangerBankQuery = new DangerBank();
						dangerBankQuery.setCode(code);
						List<DangerBank> banklist = dangerBankService.findList(dangerBankQuery);
						if (banklist != null && !banklist.isEmpty()){
							dangerBankService.delete(banklist.get(0));
						}
					}
					dangerBankService.save(dangerBank);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>隐患名称 "+dangerBank.getName()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>隐患名称 "+dangerBank.getName()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条数据，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条数据"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入数据失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/dangerbank/dangerBank/?repage";
    }
}