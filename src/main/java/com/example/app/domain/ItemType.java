package com.example.app.domain;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.example.app.validation.UpdateGroup;

import lombok.Data;

@Data
public class ItemType {

	private int id;
	@NotBlank(groups = {UpdateGroup.class})
	@Size(max=20, groups = {UpdateGroup.class})
	private String name;
	private int regiPersonId;
	private LocalDateTime registerAt;
	
}
