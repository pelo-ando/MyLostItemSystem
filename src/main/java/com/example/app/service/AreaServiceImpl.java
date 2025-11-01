package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.domain.Area;
import com.example.app.mapper.AreaMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {

	private final AreaMapper areaMapper;
	
	@Override
	public List<Area> getAreaList() {
		return areaMapper.findAll();
	}

}
