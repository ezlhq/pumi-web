package com.pumi.pumi_web.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pumi.pumi_web.domain.PruductTypeDto;
import com.pumi.pumi_web.repository.mysql.PruductTypeDAO;

@Service
public class PruductGroupsServiceImpl {
	
	@Autowired
	private PruductTypeDAO pruductTypeDAO;
	
	
	private static final List<Integer> default_pruduct_Types = new ArrayList<Integer>(){{add(1);add(2);add(3);}};
	
	private static final List<Integer> default_device_Types  = new ArrayList<Integer>(){{add(4);}};
	
	
	public Map<String, List<PruductTypeDto>> selectPruductImgList(){
		List<PruductTypeDto> selectPruductImgList = pruductTypeDAO.selectPruductImgList(default_pruduct_Types);
		Map<String,List<PruductTypeDto>> pruductGroup = new LinkedHashMap<>();
		for(PruductTypeDto product: selectPruductImgList){
			String typeName = product.getTypeName();
			List<PruductTypeDto> list = pruductGroup.get(typeName);
			if(list == null){
				list = new ArrayList<>();
				pruductGroup.put(typeName, list);
			}
			list.add(product);
		}
		return pruductGroup;
	}
	
	public Map<String, List<PruductTypeDto>> selectImgMapListByType(Integer... types){
		List<PruductTypeDto> selectPruductImgList = selectImgListByType(types);
		Map<String,List<PruductTypeDto>> pruductGroup = new LinkedHashMap<>();
		for(PruductTypeDto product: selectPruductImgList){
			String typeName = product.getTypeName();
			List<PruductTypeDto> list = pruductGroup.get(typeName);
			if(list == null){
				list = new ArrayList<>();
				pruductGroup.put(typeName, list);
			}
			list.add(product);
		}
		return pruductGroup;
	}
	
	public List<PruductTypeDto> selectImgListByType(Integer... types){
		List<PruductTypeDto> selectPruductImgList = pruductTypeDAO.selectPruductImgList(Arrays.asList(types));
		return selectPruductImgList;
	}
	
	public List<PruductTypeDto> selectDeviceImgList(){
		List<PruductTypeDto> selectPruductImgList = pruductTypeDAO.selectPruductImgList(default_device_Types);
		return selectPruductImgList;
	}
	
	

}
