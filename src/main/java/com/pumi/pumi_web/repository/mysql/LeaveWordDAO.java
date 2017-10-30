package com.pumi.pumi_web.repository.mysql;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pumi.pumi_web.domain.LeaveWordDto;

@Mapper
public interface LeaveWordDAO {
	
	List<LeaveWordDto> selectLeaveWordList();
	
	void addLeaveWord(LeaveWordDto leaveWordDto);
	
	void removeById(LeaveWordDto leaveWordDto);
	
}
