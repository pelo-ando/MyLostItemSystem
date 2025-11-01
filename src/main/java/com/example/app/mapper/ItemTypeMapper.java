package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.ItemType;

@Mapper
public interface ItemTypeMapper {

	List<ItemType> findAll();
	ItemType findById(Integer id);
	void insert(ItemType itemType);
	long countAll();
	
}
