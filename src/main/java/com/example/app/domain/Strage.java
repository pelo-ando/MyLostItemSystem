package com.example.app.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Strage {

	private int id;
	private String name;
	private LocalDateTime registerAt;
	private LocalDateTime updateAt;
}
