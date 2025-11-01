package com.example.app.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.User;
import com.example.app.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	@Autowired
	 UserMapper userMapper;
	
	@Override
	public User getUserByLoginId(String loginId) throws Exception {
		return userMapper.selectByLoginId(loginId);
	}
	
//	25-10-13 added
	@Override
	public List<User> getUserList() throws Exception {
		return userMapper.selectAll();
	}

	@Override
	public User getUserById(Integer id) throws Exception {
		return userMapper.selectById(id);
	}

	@Override
	public void deleteUserById(Integer id) throws Exception {
		userMapper.setDeleteById(id);
	}

	@Override
	public void addUser(User user) throws Exception {
		String password = user.getLoginPass();
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		user.setLoginPass(hashedPassword);
		userMapper.insert(user);
	}

	@Override
	public void editUser(User user) throws Exception {
		String password = user.getLoginPass();
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		user.setLoginPass(hashedPassword);
		userMapper.update(user);
	}

	@Override
	public boolean isExsitingUser(String loginId) throws Exception {
		User user = userMapper.selectByLoginId(loginId);
		if(user != null) {
			return true;
		}

		return false;
	}

	@Override
	public List<User> getUserListPerPage(int page, int numPerPage) throws Exception {
		int offset = numPerPage * (page - 1);
		return userMapper.selectLimited(offset, numPerPage);
	}

	@Override
	public int getTotalPages(int numPerPage) throws Exception {
		long count = userMapper.countActive();
		return (int) Math.ceil((double) count / numPerPage);
	}

	
	
}
