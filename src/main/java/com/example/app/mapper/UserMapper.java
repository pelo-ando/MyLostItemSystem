package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.User;

@Mapper
public interface UserMapper {

	User selectByLoginId(String loginId) throws Exception;
	
	//25-10-13 Added
	List<User> selectAll() throws Exception;
	User selectById(Integer id) throws Exception;
	void setDeleteById(Integer id) throws Exception;
	void insert(User student) throws Exception;
	void update(User student) throws Exception;
	List<User> selectLimited(@Param("offset") int offset, @Param("num") int num) throws Exception;
    long countActive() throws Exception;

	
}
