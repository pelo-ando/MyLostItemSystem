package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Area;

@Mapper
public interface AreaMapper {

	List<Area> findAll();
	void insert(Area area);
	long countAll();
	
}
