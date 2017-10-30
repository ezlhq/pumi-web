package com.pumi.pumi_web.repository.mysql;

import org.apache.ibatis.annotations.Mapper;

import com.pumi.pumi_web.domain.ManageUserDto;

@Mapper
public interface ManageUserDAO {

	ManageUserDto selectOneByManageUser(ManageUserDto ManageUser);
	
	void updateById(ManageUserDto ManageUser);
	
}
