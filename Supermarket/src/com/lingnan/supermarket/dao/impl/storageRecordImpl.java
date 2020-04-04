package com.lingnan.supermarket.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;







import com.lingnan.supermarket.dao.storageRecordService;
import com.lingnan.supermarket.dto.StorageRecord;
import com.lingnan.supermarket.utils.DateUtil;
import com.lingnan.supermarket.utils.JDBCUtil;

public class storageRecordImpl implements storageRecordService {

	@Override
	public Vector<StorageRecord> findAllStorageRecord() {
		Vector<StorageRecord> v=new Vector<StorageRecord>();
		
		
		Connection conn=JDBCUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet resultSet=null;
		try {

			String sql="select * from storageRecord  order by cDate desc";
			pstmt=conn.prepareStatement(sql);
			 
			resultSet=pstmt.executeQuery();
			 while(resultSet.next()) {
				 StorageRecord sr=new StorageRecord();
				sr.setId(resultSet.getString("id"));
				sr.setTheNumber(resultSet.getString("theNumber"));
				sr.setNum(resultSet.getInt("num"));
				sr.setExecute(resultSet.getString("execute"));
				sr.setcDate(DateUtil.dateToString(resultSet.getTimestamp("cDate"), null));
				v.add(sr);
			 }
		
			
		}

		catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(resultSet, pstmt, conn);
			
		}

		
		
		return v;	
	}


	@Override
	public StorageRecord findByIdStorageRecord(String theNumber) {
		
		StorageRecord sr=null;
		Connection conn=JDBCUtil.getConn();	
		PreparedStatement pstmt = null;
		ResultSet resultSet=null;

		try {
			sr=new StorageRecord();
			pstmt = conn.prepareStatement("select *from storageRecord where theNumber=?");
			pstmt.setString(1,theNumber);
			resultSet = pstmt.executeQuery();
			if(resultSet.next()){
				sr.setTheNumber((resultSet.getString("theNumber")));
				sr.setId((resultSet.getString("id")));
				sr.setNum(resultSet.getInt("num"));
				sr.setExecute(resultSet.getString("execute"));
				sr.setcDate(DateUtil.dateToString(resultSet.getTimestamp("cDate"), null));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.close(resultSet, pstmt, conn);
		}
		
		
		return sr;
	}

	public boolean insertStorageRecord(String iNumber,String time,String prodId,String execute,int sum) {
		boolean flag = false;
		Connection conn=JDBCUtil.getConn();	
		PreparedStatement pstmt = null;
		ResultSet resultSet=null;

		try {
			pstmt = conn.prepareStatement("insert into storageRecord values(?,?,?,?,?) ");
			pstmt.setString(1,iNumber);
			pstmt.setString(2,time);
			pstmt.setString(3,prodId);
			pstmt.setString(4,execute);
			pstmt.setInt(5,sum);

			if(pstmt.executeUpdate()==1){
					flag = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.close(resultSet, pstmt, conn);
		}
		
		
		return flag;
	}
}
