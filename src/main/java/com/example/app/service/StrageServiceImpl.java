package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.domain.Strage;
import com.example.app.mapper.StrageMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StrageServiceImpl implements StrageService {

	private final StrageMapper strageMapper;
	
	@Override
	public List<Strage> getStrageList() {
		return strageMapper.findAll();
	}
}
