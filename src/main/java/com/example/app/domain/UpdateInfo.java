package com.example.app.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UpdateInfo {

	private int id;
	//@Size(max = 100, groups = {UpdateGroup.class})
	private String memo;
	private LocalDateTime updateAt;
	
	// 忘れ物ID
	//@NotNull
	private int contentId;
	private String contentName;
	
	// UserID
	private int modifyPersonId;
	private String modifyPersonName;


}
