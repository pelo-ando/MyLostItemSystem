package com.example.app.domain;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.example.app.validation.LoginGroup;

import lombok.Data;

@Data
public class User {
	
	private Integer id;
	@NotBlank(groups = {LoginGroup.class})
	@Size(max = 30, groups = {LoginGroup.class})
	private String loginId;
	@NotBlank(groups = {LoginGroup.class})
	@Size(max = 60, groups = {LoginGroup.class})
	private String loginPass;
	@NotBlank(groups = {LoginGroup.class})
	@Size(max = 30, groups = {LoginGroup.class})
	private String name;
	@NotBlank(groups = {LoginGroup.class})
	@Size(max = 10, groups = {LoginGroup.class})
	private String userType;
	
	private LocalDateTime registerAt;
	private LocalDateTime updateAt;
	
}
