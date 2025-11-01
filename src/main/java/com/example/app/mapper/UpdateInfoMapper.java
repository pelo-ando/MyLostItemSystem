package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.UpdateInfo;

@Mapper
public interface UpdateInfoMapper {

	List<UpdateInfo> selectAll() throws Exception;
	List<UpdateInfo> selectById(Integer id) throws Exception;
	void insert(UpdateInfo updateInfo) throws Exception;
	
}
