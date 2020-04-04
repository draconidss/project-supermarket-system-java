package com.lingnan.supermarket.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import com.lingnan.supermarket.dao.UserService;
import com.lingnan.supermarket.dto.User;
import com.lingnan.supermarket.utils.JDBCUtil;

public class UserServiceImpl implements UserService {

	
	public User login(String Name, String password) {
		Connection conn=JDBCUtil.getConn();
		User user=null;
		PreparedStatement pstmt = null;
		ResultSet resultSet=null;
		try {
			pstmt = conn.prepareStatement("select *from User where name=? and password=? and delmark = 1");
			pstmt.setString(1,Name);
			pstmt.setString(2,password);

			resultSet = pstmt.executeQuery();
			if(resultSet.next()){
				user=new User();
				System.out.println(resultSet.getInt("id")+" "+resultSet.getString("name")+" "+resultSet.getString("password")+" "+resultSet.getInt("sSuper"));
				user.setId(resultSet.getInt("id"));
				user.setUsername(resultSet.getString("name"));
				user.setRname(resultSet.getString("rname"));
				user.setPassword(resultSet.getString("password"));
				user.setPhone(resultSet.getString("phone"));
				user.setUsuper(resultSet.getInt("sSuper"));		
				user.setImg(resultSet.getString("img"));
				user.setDelmark(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.close(resultSet, pstmt, conn);
		}
		
		
		return user;
	}

	
//添加用户基本信息
	public int addUser(User user) {
		int flag=0;
		Connection conn=JDBCUtil.getConn();
		String s="static\\userimg\\u1.png";
		String SQL="insert into User values(null,?,?,?,?,?,?,?)";
		
		System.out.println(SQL);
		
	
		PreparedStatement preparedStatement = null;
		ResultSet resultSet=null;
		try {
			preparedStatement = conn.prepareStatement(SQL);
			preparedStatement.setString(1,user.getUsername());
			preparedStatement.setString(2,user.getRname());
			preparedStatement.setString(3,user.getPassword());
			preparedStatement.setString(4,user.getPhone());
			preparedStatement.setInt(5,user.getUsuper());
			preparedStatement.setString(6,s);
			preparedStatement.setInt(7,1);
			preparedStatement.executeUpdate();
			flag=1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.close(resultSet, preparedStatement, conn);
		}
		
		
		return flag;
	}


	/*查找所有用户，返回vector数组*/
	 public Vector<User> allUser(){
	  Connection conn=JDBCUtil.getConn();
	  Vector<User> v = new Vector<>();
	  User user ;
	  PreparedStatement pstmt = null;
	  ResultSet resultSet=null;
	  try {
	   pstmt = conn.prepareStatement("select *from User where delmark = 1");

	   resultSet = pstmt.executeQuery();
	   while(resultSet.next()){
	    user= new User();
	    user.setId(resultSet.getInt("id"));
	    user.setUsername(resultSet.getString("name"));
	    user.setRname(resultSet.getString("rname"));
	    user.setPassword(resultSet.getString("password"));
	    user.setPhone(resultSet.getString("phone"));
	    user.setUsuper(resultSet.getInt("sSuper"));  
	    user.setImg(resultSet.getString("img"));
	    user.setDelmark(1);
	    v.add(user);
	   }
	  } catch (SQLException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }finally{
	   JDBCUtil.close(resultSet, pstmt, conn);
	  }
	  
	  
	  return v;
	 }
	 
	 //通过真实姓名
	 public Vector<User> findByrnameUser(User user) {
		 Connection conn=JDBCUtil.getConn();
		  Vector<User> v = new Vector<>();
		  System.out.println(user.getRname());
		  PreparedStatement pstmt = null;
		  ResultSet resultSet=null;
		  try {
			  if(!user.getRname().equals("")){
				
				 String s='%'+user.getRname()+'%';
				  pstmt = conn.prepareStatement("select * from user where rname like ? and delmark = 1");
			  pstmt.setString(1,s);
			  }
			  else
				  pstmt = conn.prepareStatement("select * from user where delmark = 1");
			  
		  
		   resultSet = pstmt.executeQuery();
		   while(resultSet.next()){
		    user= new User();
		    user.setId(resultSet.getInt("id"));
		    user.setUsername(resultSet.getString("name"));
		    user.setRname(resultSet.getString("rname"));
		    user.setPassword(resultSet.getString("password"));
		    user.setPhone(resultSet.getString("phone"));
		    user.setUsuper(resultSet.getInt("sSuper"));  
		    user.setImg(resultSet.getString("img"));
		    user.setDelmark(1);
		    v.add(user);
		    
		   }
		   
		  
		   Iterator<User> it=v.iterator();
		   while(it.hasNext()){
		    user=it.next();
		    System.out.println(user.getId()+" "+user.getUsername()+" "+user.getRname()+" "+user.getPassword()+" "+user.getPhone()+" "+user.getUsuper()+" "+user.getImg()+" "+user.getDelmark()+" ");
		   }
		  } catch (SQLException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }finally{
		   JDBCUtil.close(resultSet, pstmt, conn);
		  }
		  
		  
		  return v;
		
	}

	@Override
	//按id编号删除某用户记录
	public int deleteUser(int id) {
		int flag=0;
		Connection conn=JDBCUtil.getConn();
		
		String SQL="delete from user where id=?";
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet=null;
		try {
			preparedStatement = conn.prepareStatement(SQL);
			preparedStatement.setInt(1,id);
			
			preparedStatement.executeUpdate();
			flag=1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.close(resultSet, preparedStatement, conn);
		}
		
		
		return flag;
	
	}







	@Override
	public User login(String loginName, String password, int usuper) {
		// TODO Auto-generated method stub
		return null;
	}
	/*public static void main(String[] args) {
		User user=new User();
		user.setUsername("whf");
		user.setRname("fafafa");
		user.setPassword("1111");
		user.setPhone("66666");
		user.setUsuper(1);
		UserServiceImpl usi=new UserServiceImpl();
		usi.addUser(user);
	}*/


	@Override
	public int updateByIdUser(User user) {
		int flag=0;
		int id=user.getId();
		System.out.println(id);
		Connection conn=JDBCUtil.getConn();
		String SQL="update User set name=?,rname=?,password=?,phone=?,sSuper=?,img=?,delmark=? where id=? ";
		
		System.out.println(SQL);
		
	
		PreparedStatement preparedStatement = null;
		ResultSet resultSet=null;
		try {
			preparedStatement = conn.prepareStatement(SQL);
			preparedStatement.setString(1,user.getUsername());
			preparedStatement.setString(2,user.getRname());
			preparedStatement.setString(3,user.getPassword());
			preparedStatement.setString(4,user.getPhone());
			preparedStatement.setInt(5,user.getUsuper());
			preparedStatement.setString(6,user.getImg());
			preparedStatement.setInt(7,1);
			preparedStatement.setInt(8,id);
			preparedStatement.executeUpdate();
			flag=1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.close(resultSet, preparedStatement, conn);
		}
		
		
		return flag;
	}
/*	public static void main(String[] args) {
		User user=new User();
		user.setId(1);
		user.setUsername("z001");
		user.setRname("陈龙");
		user.setPassword("0.00.0");
		user.setPhone("6666776");
		user.setUsuper(0);
		user.setImg("static\\userimg\\u1.png");
		user.setDelmark(1);
		UserServiceImpl usi=new UserServiceImpl();
		int flag=usi.updateByIdUser(user);
		if(flag==1){
			System.out.println("ok");
		}
		 System.out.println(user.getId()+" "+user.getUsername()+" "+user.getRname()+" "+user.getPassword()+" "+user.getPhone()+" "+user.getUsuper()+" "+user.getImg()+" "+user.getDelmark()+" ");
	}*/
	
}
