package com.pumi.pumi_web.repository.mysql;

import org.apache.ibatis.annotations.Mapper;

import com.pumi.pumi_web.domain.ImgDto;

@Mapper
public interface ImgDAO {

	void addImg(ImgDto imgDto);
	
	void updateById(ImgDto imgDto);
	
	void removeById(ImgDto imgDto);
}
