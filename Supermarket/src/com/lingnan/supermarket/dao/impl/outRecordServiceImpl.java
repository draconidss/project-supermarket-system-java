package com.lingnan.supermarket.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.lingnan.supermarket.dao.outRecordService;
import com.lingnan.supermarket.dto.InOrder;
import com.lingnan.supermarket.dto.InRecord;
import com.lingnan.supermarket.dto.OutRecord;
import com.lingnan.supermarket.utils.JDBCUtil;

public class outRecordServiceImpl implements outRecordService{

	@Override
	public Vector<OutRecord> findAllOutRecord() {
		Vector<OutRecord> outRecords = new Vector<OutRecord>();
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet=null;
		
		String SQL = "select * from outRecord";
		
		try {
			preparedStatement = conn.prepareStatement(SQL);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				OutRecord outRecord = new OutRecord();
				outRecord.setoNumber(resultSet.getString("oNumber"));
				outRecord.setSum(resultSet.getInt("sum"));
				outRecord.setOutPrice(resultSet.getFloat("outPrice"));
				outRecords.add(outRecord);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, conn);
		}

		return outRecords;
	}

	@Override
	public Vector<OutRecord> findByIdOutRecordr(String oNumber) {
		OutRecord outRecord ;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Vector<OutRecord> v = new Vector<OutRecord>();

		try {
			preparedStatement = conn.prepareStatement("select * from outRecord where oNumber = ?");
			preparedStatement.setString(1, oNumber);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				outRecord = new OutRecord();
				outRecord.setoNumber(resultSet.getString("oNumber"));
				outRecord.setId(resultSet.getString("id"));
				outRecord.setSum(resultSet.getInt("sum"));
				outRecord.setOutPrice(resultSet.getFloat("Price"));
				v.add(outRecord);
			} 

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, conn);
		}
		return v;
	}

	@Override
	public int addoutRecord(OutRecord or) {
		
		int flag = 0;
		
		String oNumber = or.getoNumber();
		int sum = or.getSum();
		Float outPrice = or.getOutPrice();
		
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement
					("insert into outRecord values (?,?,?)");
			preparedStatement.setString(1, oNumber);
			preparedStatement.setInt(2, sum);
			preparedStatement.setFloat(3, outPrice);
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
	public int deleteOutOrder(String oNumber) {
		int flag = 0;
		
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement
					("delete from outRecord where oNumber = ?");
			preparedStatement.setString(1, oNumber);
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

	

}
