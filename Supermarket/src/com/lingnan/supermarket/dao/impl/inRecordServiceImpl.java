package com.lingnan.supermarket.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import com.lingnan.supermarket.dao.inRecordService;
import com.lingnan.supermarket.dto.InOrder;
import com.lingnan.supermarket.dto.InRecord;
import com.lingnan.supermarket.dto.Production;
import com.lingnan.supermarket.dto.User;
import com.lingnan.supermarket.utils.JDBCUtil;

public class inRecordServiceImpl implements inRecordService{

	@Override
	public Vector<InRecord> findAllinRecord() {
		Vector<InRecord> inRecords = new Vector<InRecord>();
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet=null;
		
		String SQL = "select * from inRecord";
		
		try {
			preparedStatement = conn.prepareStatement(SQL);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				InRecord inRecord = new InRecord();
				inRecord.setiNumber(resultSet.getString("iNumber"));
				inRecord.setSum(resultSet.getInt("sum"));
				inRecord.setInPrice(resultSet.getFloat("inPrice"));
				inRecords.add(inRecord);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, conn);
		}

		return inRecords;
	}

	@Override
	public Vector<InRecord> findByIdinRecord(String iNumber) {
		InRecord inRecord;
		Vector<InRecord> v = new Vector<InRecord>();
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = conn.prepareStatement("select * from inRecord where iNumber = ?");
			preparedStatement.setString(1, iNumber);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				inRecord = new InRecord();
				inRecord.setiNumber(resultSet.getString("iNumber"));
				inRecord.setId(resultSet.getString("id"));
				inRecord.setSum(resultSet.getInt("sum"));
				inRecord.setInPrice(resultSet.getFloat("Price"));
				v.add(inRecord);
			}  
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, conn);
		}
		return v;
	}

	@Override
	public int addinRecord(InRecord ir) {
		int flag = 0;
		
		String iNumber = ir.getiNumber();
		int sum = ir.getSum();
	    Float inPrice = ir.getInPrice();
		
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement
					("insert into inRecord values (?,?,?)");
			preparedStatement.setString(1, iNumber);
			preparedStatement.setInt(2, sum);
			preparedStatement.setFloat(3, inPrice);
			preparedStatement.executeUpdate();
			flag = 1;
		} catch (SQLException e) {
			flag = -1;
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null,preparedStatement, conn);
		}
		
		return flag;
	}

	@Override
	public int deleteinRecord(String iNumber) {
		int flag = 0;
		
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement
					("delete from inRecord where iNumber = ?");
			preparedStatement.setString(1, iNumber);
			preparedStatement.executeUpdate();
			flag = 1;
		} catch (SQLException e) {
			flag = -1;
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null,preparedStatement, conn);
		}
		return flag;
	}

	public boolean insertInRecord(String iNumber,Production p) {
		boolean flag = false;		
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement("insert into inRecord values(?,?,?,?)");
			preparedStatement.setString(1, iNumber);
			preparedStatement.setString(2, p.getId());
			preparedStatement.setInt(3,p.getSum());
			preparedStatement.setFloat(4, p.getPrice());
			
			if(preparedStatement.executeUpdate()==1)
				flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null,preparedStatement, conn);
		}
		return flag;
	}

	

}
