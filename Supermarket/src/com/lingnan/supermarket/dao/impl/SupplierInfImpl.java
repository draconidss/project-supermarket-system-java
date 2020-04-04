package com.lingnan.supermarket.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import com.lingnan.supermarket.dao.SupplierInfService;
import com.lingnan.supermarket.dto.ProdCatalog;
import com.lingnan.supermarket.dto.SupplierInf;
import com.lingnan.supermarket.dto.User;
import com.lingnan.supermarket.utils.JDBCUtil;

public class SupplierInfImpl implements SupplierInfService{

	@Override
	public Vector<SupplierInf> findAllSupplierInf() {
		Vector<SupplierInf> supplierInfs = new Vector<SupplierInf>();
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet=null;
		
		String SQL = "select * from supplierInf where delmark = 1";
		
		try {
			preparedStatement = conn.prepareStatement(SQL);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				SupplierInf supplierInf = new SupplierInf();
				supplierInf.setId(resultSet.getInt("id"));
				supplierInf.setName(resultSet.getString("name"));
				supplierInf.setContact(resultSet.getString("contact"));
				supplierInf.setEmail(resultSet.getString("email"));
				supplierInf.setAddress(resultSet.getString("address"));
				supplierInf.setDelmark(resultSet.getInt("delmark"));
				supplierInfs.add(supplierInf);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, conn);
		}

		return supplierInfs;
	}

	@Override
	public Vector<SupplierInf> findByNameSupplierInf(SupplierInf supplierInf) {
		//SupplierInf supplierInf = new SupplierInf();
		Connection conn = JDBCUtil.getConn();
		Vector<SupplierInf> v = new Vector<>();
		System.out.println(supplierInf.getName());
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			if(!supplierInf.getName().equals("")){
				String s='%'+supplierInf.getName()+'%';
				preparedStatement = conn.prepareStatement("select * from supplierInf where name like ? and delmark = 1");
				preparedStatement.setString(1, s);
			}else
				preparedStatement = conn.prepareStatement("select * from supplierInf where delmark = 1");			
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				supplierInf = new SupplierInf();
				supplierInf.setId(resultSet.getInt("id"));
				supplierInf.setName(resultSet.getString("name"));
				supplierInf.setAddress(resultSet.getString("address"));
				supplierInf.setContact(resultSet.getString("contact"));
				supplierInf.setEmail(resultSet.getString("email"));
				supplierInf.setDelmark(1);
				v.add(supplierInf);
			}
			  Iterator<SupplierInf> it=v.iterator();
			   while(it.hasNext()){
				   supplierInf=it.next();
			    System.out.println(supplierInf.getId()+" "+supplierInf.getName()+" "+supplierInf.getAddress()+" "+supplierInf.getContact()+" "+supplierInf.getEmail()+" "+supplierInf.getDelmark()+" ");
			   }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, conn);
		}
		return v;
	}

	@Override
	public int addSupplierInf(SupplierInf supplierInf) {
		int flag = 0;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement
					("insert into supplierInf values (null,?,?,?,?,?)");
			//preparedStatement.setInt(1, supplierInf.getId());
			preparedStatement.setString(1, supplierInf.getName());
			preparedStatement.setString(2, supplierInf.getAddress());
			preparedStatement.setString(3, supplierInf.getContact());
			preparedStatement.setString(4, supplierInf.getEmail());
			preparedStatement.setInt(5, 1);
			preparedStatement.executeUpdate();
			flag = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null,preparedStatement, conn);
		}
		
		return flag;
	}

	@Override
	public int deleteSupplierInf(int id) {
		int flag = 0;
		
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement
					("delete from supplierInf where id = ?");
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			flag = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null,preparedStatement, conn);
		}
		return flag;
	}

	@Override
	public int updateSupplierInf(SupplierInf supplierInf) {
		int flag=0;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = conn.prepareStatement("update supplierInf set name=?,address=?,contact=?,email=?,delmark=? where id = ?");
		    preparedStatement.setString(1,supplierInf.getName());
		    preparedStatement.setString(2,supplierInf.getAddress());
		    preparedStatement.setString(3,supplierInf.getContact());
		    preparedStatement.setString(4, supplierInf.getEmail());
		    preparedStatement.setInt(5, supplierInf.getDelmark());
		    preparedStatement.setInt(6,supplierInf.getId());
		    preparedStatement.executeUpdate();
			flag = 1;
	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, preparedStatement, conn);
		}
		return flag;
	}
	
	@Override
	public ArrayList<String> findNameSupplier() {
		Connection conn=JDBCUtil.getConn();
		ArrayList<String> v = new ArrayList<String>();
		SupplierInf supplierInf;
		PreparedStatement pstmt = null;
		ResultSet resultSet=null;
		v.add("全部");
		try {
			pstmt = conn.prepareStatement("select * from supplierInf");
			resultSet = pstmt.executeQuery();
			while(resultSet.next()){
				
				v.add(resultSet.getString("name"));
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
	public int findIdSupplierByName(String name) {
		int flag = -1;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet= null;
		int id=0;
		try {
			if(name.equals("全部"))
				return id;
			preparedStatement = conn.prepareStatement
					("select * from supplierInf where name=?");
			preparedStatement.setString(1, name);
			resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				return resultSet.getInt("id");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null,preparedStatement, conn);
		}
		
		return flag;
	}

	

}
