package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.domain.ItemType;
import com.example.app.mapper.ItemTypeMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemTypeServiceImpl implements ItemTypeService {

	private final ItemTypeMapper itemTypeMapper;
	
	@Override
	public List<ItemType> getItemTypeList() {
		return itemTypeMapper.findAll();
	}
}
