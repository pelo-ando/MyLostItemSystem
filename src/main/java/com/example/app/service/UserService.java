package com.example.app.service;

import java.util.List;

import com.example.app.domain.User;

public interface UserService {
	
		User getUserByLoginId(String loginId) throws Exception;
		
		// 25-10-13 added
		List<User> getUserList() throws Exception;
		User getUserById(Integer id) throws Exception;
		void deleteUserById(Integer id) throws Exception;
		void addUser(User user) throws Exception;
		void editUser(User user) throws Exception;
		boolean isExsitingUser(String loginId) throws Exception;
		List<User> getUserListPerPage(int page, int numPerPage) throws Exception;
	    int getTotalPages(int numPerPage) throws Exception;
}
