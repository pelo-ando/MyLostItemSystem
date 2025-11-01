package com.example.app.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Area {

	private int id;
	private String name;
	private int regiPersonId;
	private LocalDateTime registerAt;
	
}
