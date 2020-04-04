package com.lingnan.supermarket.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
 
public class JDBCUtil {
	
	private static Properties properties;
	private static String url;
	private static String user;
	private static String password;
	static {
		InputStream inputStream = JDBCUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
		 
		properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		//注册驱动
		try {
			//类加载会触发static里面代码
			Class.forName(properties.getProperty("driver"));
			
			url= properties.getProperty("url");
			user= properties.getProperty("user");
			password= properties.getProperty("password");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
 
	/**
	 * 获取连接
	 * @return
	 */
	public static Connection getConn() {
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 释放资源
	 * @param resultSet
	 * @param statement
	 * @param connection
	 */
	public static void close(ResultSet resultSet,Statement statement,Connection connection) {
		try {
		    if(resultSet!=null) {
		    		resultSet.close();
		    }
		    if(statement!=null) {
		    	statement.close();
		    }
		    if(connection!=null) {
		    	connection.close();
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	
}
