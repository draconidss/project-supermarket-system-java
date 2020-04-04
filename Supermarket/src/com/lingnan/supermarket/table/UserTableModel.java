package com.lingnan.supermarket.table;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.lingnan.supermarket.dto.User;
import com.lingnan.supermarket.dao.UserService;
import com.lingnan.supermarket.dao.impl.*;


public class UserTableModel extends AbstractTableModel{
	
	private String [] columnName = {"id","工号","密码","姓名","权限","电话"};

	private UserService userService = new UserServiceImpl();
	private int id=0;
	private Vector<User> users;
	public void all() {
		//查找全部数据
		users = userService.allUser();
	}
	public void Byrname(User user) {
		users = userService.findByrnameUser(user);
	}
	
	
	@Override
	public int getRowCount() {
		return users.size();
	}

	@Override
	public int getColumnCount() {  
		return columnName.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		User user = users.get(rowIndex);
		id=user.getId();
	/*	System.out.println( "id="+users.get(rowIndex).getId());
		System.out.println("rowIndex"+rowIndex);
		System.out.println("columnIndex"+columnIndex);*/
		if(columnIndex==0) {
			return user.getId();
		}else if(columnIndex==1) {
			return user.getUsername();			
		}else if(columnIndex==2) {
			return user.getPassword();	
		}else if(columnIndex==3) {
			return user.getRname();
		}else if(columnIndex==4) {
			return user.getUsuper();
		}else if(columnIndex==5) {
			return user.getPhone();
		}else {
			return null;
		}
	
		
	}
	public int getid(){
		return id;
	}
	public int getValueAt(int rowIndex) {
		User user = users.get(rowIndex);
		id=users.get(rowIndex).getId();
/*		System.out.println( "id="+users.get(rowIndex).getId());
		System.out.println("rowIndex"+rowIndex);
		System.out.println("columnIndex"+columnIndex);*/
			return user.getId();
		
	
		
	}
	
	
	
	
	 
	@Override
	public String getColumnName(int column) {
		return columnName[column];
	}
	
	
}
