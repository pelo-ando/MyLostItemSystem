package com.example.app.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.app.validation.ItemGroup;

import lombok.Data;

@Data
public class LostItem {

	
	private int id;
	@NotNull(groups = {ItemGroup.class})
	@PastOrPresent(groups = {ItemGroup.class})
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate findDate;
	@NotBlank(groups = {ItemGroup.class})
	@Size(max = 30, groups = {ItemGroup.class})
	private String content;
	@NotBlank(groups = {ItemGroup.class})
	@Size(max =10, groups = {ItemGroup.class})
	private String findPersonName;
	private String status;
	private LocalDateTime registerAt;
	
	//	拾得場所
	private int  areaId;
	private String areaName;
	
	//	保管場所
	private int strageId;			// 場所
	private String strageName;
	
	//登録者
	private int userId;
	private String userName;
}

