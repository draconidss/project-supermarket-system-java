package com.lingnan.supermarket.dao;


import java.util.Vector;

import com.lingnan.supermarket.dto.User;

public interface UserService  {
	
	 Vector<User> allUser ();
	 Vector<User> findByrnameUser(User user);
	User login(String loginName,String password,int usuper);
	int addUser(User user);
	int deleteUser(int id);
	
	//根据所给的id改
	int updateByIdUser(User user);
	
}
