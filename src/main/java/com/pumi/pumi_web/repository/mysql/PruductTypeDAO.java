package com.pumi.pumi_web.repository.mysql;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pumi.pumi_web.domain.PruductTypeDto;

@Mapper
public interface PruductTypeDAO {

	List<PruductTypeDto> selectList();
	
	List<PruductTypeDto> selectPruductImgList(List<Integer> list);
}
