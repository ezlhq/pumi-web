package com.pumi.pumi_web.repository.mysql;

import org.apache.ibatis.annotations.Mapper;

import com.pumi.pumi_web.domain.CompanyProfileDto;

@Mapper
public interface CompanyProfileDAO {

	CompanyProfileDto selectCompanyProfileOne();
	
	void updateOne(CompanyProfileDto companyProfileDto);
}
