package com.pumi.pumi_web.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.pumi.pumi_web.Main;
import com.pumi.pumi_web.domain.CompanyProfileDto;
import com.pumi.pumi_web.domain.ImgDto;
import com.pumi.pumi_web.domain.LeaveWordDto;
import com.pumi.pumi_web.domain.ManageUserDto;
import com.pumi.pumi_web.domain.PruductTypeDto;
import com.pumi.pumi_web.repository.mysql.CompanyProfileDAO;
import com.pumi.pumi_web.repository.mysql.ImgDAO;
import com.pumi.pumi_web.repository.mysql.LeaveWordDAO;
import com.pumi.pumi_web.repository.mysql.ManageUserDAO;
import com.pumi.pumi_web.repository.mysql.PruductTypeDAO;
import com.pumi.pumi_web.service.PruductGroupsServiceImpl;
import com.pumi.pumi_web.utls.DateUtils;

@Controller
@RequestMapping("/management")
public class ManageController {

	@Autowired
	private ImgDAO imgDAO;
	
	@Autowired
	private PruductTypeDAO pruductTypeDAO;
	
	@Autowired
	private PruductGroupsServiceImpl pruductGroupsService;
	
	@Autowired
	private CompanyProfileDAO companyProfileDAO;
	
	@Autowired
	private LeaveWordDAO leaveWordDAO;
	
	
	@Autowired
	private ManageUserDAO manageUserDAO;
	
	
	
	@RequestMapping("/loginPage")
	public String login(){
		return "manage/login";
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public String login(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			HttpServletRequest request){
		Map<String,Object> map = new HashMap<>(4);
		map.put("status", 500);
		
		ManageUserDto manageUserDto = new ManageUserDto();
		manageUserDto.setUsername(username);
		
		ManageUserDto user = manageUserDAO.selectOneByManageUser(manageUserDto);
		if(user == null){
			map.put("msg", "账户不存在");
			return JSON.toJSONString(map);
		}
		if(!user.getPassword().equals(password)){
			map.put("msg", "密码错误");
			return JSON.toJSONString(map);
		}
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		
		map.put("status", 200);
		return JSON.toJSONString(map);
	}
	
	@RequestMapping("/user/edit")
	@ResponseBody
	public String login(
			@RequestParam("password_old") String password_old,
			@RequestParam("password_new_0") String password_new_0,
			@RequestParam("password_new_1") String password_new_1,
			HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<>(4);
		map.put("status", 500);
		
		HttpSession session = request.getSession();
		ManageUserDto user = (ManageUserDto)session.getAttribute("user");
		if(user==null){
			map.put("msg", "您已经太长时间没有使用页面，请重新刷新页面");
			return JSON.toJSONString(map);
		}
		
		
		if(!user.getPassword().equals(password_old)){
			map.put("msg", "原密码错误");
			return JSON.toJSONString(map);
		}
		if(password_new_0==null || !password_new_0.equals(password_new_1)){
			map.put("msg", "新密码不相等");
			return JSON.toJSONString(map);
		}
		
		ManageUserDto updateUser = new ManageUserDto();
		updateUser.setId(user.getId());
		updateUser.setPassword(password_new_0);
		manageUserDAO.updateById(updateUser);
		
		//修改session
		user.setPassword(password_new_0);
		
		map.put("status", 200);
		return JSON.toJSONString(map);
	}
	
	
	@RequestMapping("/user/edit/page")
	public String login( HttpServletRequest request){
		
		HttpSession session = request.getSession();
		ManageUserDto user = (ManageUserDto)session.getAttribute("user");
		if(user==null){
			return "redirect:/management/loginPage";
		}
		return "manage/userInfoEdit";
	}
	
	
	
	@RequestMapping("/products")
	public String addPruductInfo(ModelMap map,HttpServletRequest request){
		
		ManageUserDto user = (ManageUserDto)request.getSession().getAttribute("user");
		if(user==null){
			return "redirect:/management/loginPage";
		}
		
		map.put("pruductGroup", pruductGroupsService.selectImgMapListByType(1,2,3,4));
		map.put("pruductTypes", pruductTypeDAO.selectList());
		
		return "manage/blank_product_info";
	}
	

	@ResponseBody
	@RequestMapping("add/img")
	public String addPruductInfo(
			@RequestParam(value = "img") MultipartFile img,
			@RequestParam("imgType") String imgType,
			@RequestParam(value="name",required=false) String name,HttpServletRequest request){
		
		
		Map<String,Object> map = new HashMap<>(2);
		if(img.isEmpty()){
			map.put("status", 500);
			map.put("msg", "not fount img");
			return JSON.toJSONString(map);
		}
		
		ManageUserDto user = (ManageUserDto)request.getSession().getAttribute("user");
		if(user==null){
			map.put("status", 500);
			map.put("msg", "您已经太长时间没有使用页面，请重新刷新页面");
			return JSON.toJSONString(map);
		}
		
		String url ;
        try {
        	String suf = img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf("."));
        	String fileName =  UUID.randomUUID().toString()+suf;
			Files.copy(img.getInputStream(), Paths.get(Main.imgDir,fileName));
			url = "/img/"+fileName;
		} catch (IOException e) {
			e.printStackTrace();
			map.put("status", 500);
			map.put("msg", "save img error ");
			return JSON.toJSONString(map);
		}
		
		ImgDto imgDto = new ImgDto();
		imgDto.setName(name);
		imgDto.setUrl(url);
		imgDto.setImgType(imgType);
		imgDto.setCreateId("1");//TODO 
		imgDto.setCreateTime(DateUtils.getCurrentData());
        imgDAO.addImg(imgDto);
		
        map.put("status", 200);
		return JSON.toJSONString(map);
	}
	
	
	@ResponseBody
	@RequestMapping("remove/img")
	public String addPruductInfo(
			@RequestParam("imgId") String imgId,HttpServletRequest request){
		
		Map map = new HashMap();
		
		ManageUserDto user = (ManageUserDto)request.getSession().getAttribute("user");
		if(user==null){
			map.put("status", 500);
			map.put("msg", "您已经太长时间没有使用页面，请重新刷新页面");
			return JSON.toJSONString(map);
		}
		
		try {
			ImgDto imgDto = new ImgDto();
			imgDto.setImgId(imgId);
			imgDAO.removeById(imgDto);
		} catch (Exception e) {
			map.put("status", 500);
			map.put("msg", "imgId is error");
			return JSON.toJSONString(map);
		}
		
		map.put("status", 200);
		return JSON.toJSONString(map);
	}
	
	@RequestMapping("/companyProfile/Info")
	public String companyProfileEditInfo(ModelMap map,HttpServletRequest request){
		ManageUserDto user = (ManageUserDto)request.getSession().getAttribute("user");
		if(user==null){
			return "redirect:/management/loginPage";
		}
		
		map.put("companyProfile", companyProfileDAO.selectCompanyProfileOne());
		return "manage/companyProfileEdit";
	}
	
	@ResponseBody
	@RequestMapping("/companyProfile/edit")
	public String companyProfileEdit(@ModelAttribute CompanyProfileDto cDto,HttpServletRequest request){
		Map mapStatus = new HashMap();
		
		ManageUserDto user = (ManageUserDto)request.getSession().getAttribute("user");
		if(user==null){
			mapStatus.put("status", 500);
			mapStatus.put("msg", "您已经太长时间没有使用页面，请重新刷新页面");
			return JSON.toJSONString(mapStatus);
		}
		
		try {
			companyProfileDAO.updateOne(cDto);
		} catch (Exception e) {
			mapStatus.put("status", 500);
			mapStatus.put("msg", "companyProfile is error");
			return JSON.toJSONString(mapStatus);
		}
		
		mapStatus.put("status", 200);
		return JSON.toJSONString(mapStatus);
	}
	
	
	@RequestMapping("/leaveWord/show")
	public String LeaveWord(ModelMap map,HttpServletRequest request){
		ManageUserDto user = (ManageUserDto)request.getSession().getAttribute("user");
		if(user==null){
			return "redirect:/management/loginPage";
		}
		
		map.put("leaveWords", leaveWordDAO.selectLeaveWordList());
		return "manage/leaveWord";
	}
	
	@ResponseBody
	@RequestMapping("/leaveWord/del")
	public String LeaveWordDel(@RequestParam("id") String id,HttpServletRequest request){
		Map mapStatus = new HashMap();
		
		ManageUserDto user = (ManageUserDto)request.getSession().getAttribute("user");
		if(user==null){
			mapStatus.put("status", 500);
			mapStatus.put("msg", "您已经太长时间没有使用页面，请重新刷新页面");
			return JSON.toJSONString(mapStatus);
		}
		
		try {
			LeaveWordDto lwd = new LeaveWordDto();
			lwd.setId(id);
			leaveWordDAO.removeById(lwd);
		} catch (Exception e) {
			mapStatus.put("status", 500);
			mapStatus.put("msg", "leaveWord is error");
			return JSON.toJSONString(mapStatus);
		}
		
		mapStatus.put("status", 200);
		return JSON.toJSONString(mapStatus);
	}
	
	@ResponseBody
	@RequestMapping("/leaveWord/add")
	public String LeaveWordDel(@ModelAttribute LeaveWordDto lDto,HttpServletRequest request){
		
		Map mapStatus = new HashMap();
		
		ManageUserDto user = (ManageUserDto)request.getSession().getAttribute("user");
		if(user==null){
			mapStatus.put("status", 500);
			mapStatus.put("msg", "您已经太长时间没有使用页面，请重新刷新页面");
			return JSON.toJSONString(mapStatus);
		}
		
		try {
			lDto.setCreateTime(DateUtils.getCurrentData());
			leaveWordDAO.addLeaveWord(lDto);
			
		} catch (Exception e) {
			mapStatus.put("status", 500);
			mapStatus.put("msg", "您的填写错误");
			return JSON.toJSONString(mapStatus);
		}
		
		mapStatus.put("status", 200);
		return JSON.toJSONString(mapStatus);
	}
	
	
	

}
