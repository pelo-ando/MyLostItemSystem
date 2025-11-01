package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Strage;

@Mapper
public interface StrageMapper {

	List<Strage> findAll();
	void insert(Strage strage);
	long countAll();
}
