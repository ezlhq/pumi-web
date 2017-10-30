package com.pumi.pumi_web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pumi.pumi_web.repository.mysql.CompanyProfileDAO;
import com.pumi.pumi_web.service.PruductGroupsServiceImpl;

@Controller
public class WebController {

	@Autowired
	private CompanyProfileDAO companyProfileDAO;

	@Autowired
	private PruductGroupsServiceImpl pruductGroupsService;
	
	@RequestMapping("/")
	public String index(ModelMap map) {
		map.put("pageEnum", "index");
		map.put("pruductGroup", pruductGroupsService.selectPruductImgList());
		map.put("companyProfile", companyProfileDAO.selectCompanyProfileOne());
		return "index";
	}
	
	@RequestMapping("showDevice")
	public String showDevice(ModelMap map) {
		map.put("pageEnum", "showDevice");
		map.put("pruductGroup", pruductGroupsService.selectPruductImgList());
		map.put("deviceGroup", pruductGroupsService.selectDeviceImgList());
		map.put("companyProfile", companyProfileDAO.selectCompanyProfileOne());
		return "showDevice";
	}
	
	@RequestMapping("/company/profile") //请求路径，和Spring一样
    public String companyProfile(ModelMap map){
		map.put("pageEnum", "companyProfile");
		map.put("pruductGroup", pruductGroupsService.selectPruductImgList());
		map.put("companyProfile", companyProfileDAO.selectCompanyProfileOne());
        return "companyProfile";
    }
	
	@RequestMapping("/jobInfo") //请求路径，和Spring一样
	public String jobInfo(ModelMap map){
		map.put("pageEnum", "jobInfo");
		map.put("pruductGroup", pruductGroupsService.selectPruductImgList());
		map.put("companyProfile", companyProfileDAO.selectCompanyProfileOne());
		return "jobInfo";
	}
	
	@RequestMapping("/productCenter") //请求路径，和Spring一样
	public String productCenter(@RequestParam(value="type",required=false) String type,ModelMap map){
		map.put("pageEnum", "productCenter");
		map.put("pruductGroup", pruductGroupsService.selectPruductImgList());
		map.put("companyProfile", companyProfileDAO.selectCompanyProfileOne());
		map.put("type", type);
		return "productCenter";
	}
	
	@RequestMapping("/contact") //请求路径，和Spring一样
	public String contact(ModelMap map){
		map.put("pageEnum", "contact");
		map.put("companyProfile", companyProfileDAO.selectCompanyProfileOne());
		map.put("pruductGroup", pruductGroupsService.selectPruductImgList());
		return "contact";
	}
	
	
}
