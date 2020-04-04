package com.lingnan.supermarket.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import com.lingnan.supermarket.dao.inOrderService;
import com.lingnan.supermarket.dto.InOrder;
import com.lingnan.supermarket.utils.DateUtil;
import com.lingnan.supermarket.utils.JDBCUtil;

public class inOrderServiceImpl implements inOrderService{
	
	@Override
	public Vector<InOrder> findAllInOrder (){
		Vector<InOrder> inOrders = new Vector<InOrder>();
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet=null;
		
		String SQL = "select * from inOrder where delmark=1 order by inDate desc";
		
		try {
			preparedStatement = conn.prepareStatement(SQL);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				InOrder inOrder = new InOrder();
				inOrder.setiNumber(resultSet.getString("iNumber"));
				inOrder.setAllInPrice(resultSet.getFloat("allInPrice"));
				inOrder.setInDate(resultSet.getString("inDate"));
				inOrder.setPrincipal(resultSet.getString("principal"));
				inOrder.setStatus(resultSet.getInt("status"));
				inOrder.setDelmark(resultSet.getInt("Delmark"));
				inOrders.add(inOrder);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, conn);
		}

		return inOrders;
		
	}
	
	
	@Override
	public InOrder findByIdinOrder(String iNumber) {

		InOrder inOrder = new InOrder();
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = conn.prepareStatement("select * from inOrder where iNumber = ?");
			preparedStatement.setString(1, iNumber);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				inOrder.setiNumber(resultSet.getString("iNumber"));
				inOrder.setAllInPrice(resultSet.getFloat("allInPrice"));
				inOrder.setInDate(resultSet.getString("inDate"));
				inOrder.setPrincipal(resultSet.getString("principal"));
				inOrder.setStatus(resultSet.getInt("status"));
			} else {
				return null; // 没有找到该订单或订单不存在，返回null
			} 

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, conn);
		}
		return inOrder;
	}
	
	
	@Override
	public int addInOrder(String iNumber, float allInPrice) {
		int flag = 0;
		Timestamp inDate = new Timestamp(System.currentTimeMillis()); 
		//System.out.println(inDate);
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement
					("insert into inOrder values (?,?,?,?)");
			preparedStatement.setString(1, iNumber);
			preparedStatement.setFloat(2, allInPrice);
			preparedStatement.setTimestamp(3, inDate);
			preparedStatement.setString(4, "a1");
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
	public int deleteInOrder(String iNumber) {
		int flag = 0;
		
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement
					("update inOrder set delmark=0 where iNumber = ?");
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

	/*往订单表插入一条记录*/
	public boolean InsertInOrder(String number,Float allPrice,String time,String username,int c){
		boolean flag = false;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;;
		try {
			pstmt = conn.prepareStatement("insert into InOrder values(?,?,?,?,?,?)");
			pstmt.setString(1, number);
			pstmt.setFloat(2, allPrice);
			pstmt.setString(3, time);
			pstmt.setString(4, username);
			pstmt.setInt(5, 2);
			pstmt.setInt(6, 1);
			
			if(pstmt.execute())
					flag = true;


			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {

			JDBCUtil.close(resultSet, pstmt, conn);
		}
		return flag;
	}

	/*更新状态*/
	public boolean updateInOrderStatus(String iNumber,int status) {
		boolean flag = false;		
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;		
		try {
			preparedStatement = conn.prepareStatement("update inOrder set status=? where iNumber=?");
			preparedStatement.setInt(1, status);
			preparedStatement.setString(2,iNumber);		
			if(preparedStatement.executeUpdate()==1)
				flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null,preparedStatement, conn);
		}
		return flag;
	}
	
	//查找所有待入库订单*/待确认
	public Vector<InOrder> findUnconfirmInOrder(){
		Vector<InOrder> inOrders = new Vector<InOrder>();
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet=null;
		
		String SQL = "select * from inOrder where status=2 and delmark=1";
		
		try {
			preparedStatement = conn.prepareStatement(SQL);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				InOrder inOrder = new InOrder();
				inOrder.setiNumber(resultSet.getString("iNumber"));
				inOrder.setAllInPrice(resultSet.getFloat("allInPrice"));
				inOrder.setInDate(resultSet.getString("inDate"));
				inOrder.setPrincipal(resultSet.getString("principal"));
				inOrder.setStatus(resultSet.getInt("status"));
				inOrder.setDelmark(resultSet.getInt("Delmark"));
				inOrders.add(inOrder);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, conn);
		}

		return inOrders;
		
	}
	
	//获取今日进货金额
	public Float TodayInPrice(String date) {

		InOrder inOrder = new InOrder();
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Float allInPrice=(float) 0;

		try {
			preparedStatement = conn.prepareStatement("select sum(allInPrice) from inOrder where inDate>=? and inDate<=date_add(?,interval 1 day)");
			preparedStatement.setString(1, date);
			preparedStatement.setString(2, date);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				allInPrice=resultSet.getFloat("sum(allInPrice)");
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, conn);
		}
		return allInPrice;
	}

	//查找指定状态订单
	public Vector<InOrder> FindStatus(int status){
		Vector<InOrder> inOrders = new Vector<InOrder>();
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet=null;
		
	
		
		try {		
			preparedStatement = conn.prepareStatement("select * from inOrder where status=? and delmark=1 order by inDate desc");
			preparedStatement.setInt(1, status);
			resultSet = preparedStatement.executeQuery();		
			while(resultSet.next()) {
				InOrder inOrder = new InOrder();
				inOrder.setiNumber(resultSet.getString("iNumber"));
				inOrder.setAllInPrice(resultSet.getFloat("allInPrice"));
				inOrder.setInDate(resultSet.getString("inDate"));
				inOrder.setPrincipal(resultSet.getString("principal"));
				inOrder.setStatus(resultSet.getInt("status"));
				inOrder.setDelmark(resultSet.getInt("Delmark"));
				inOrders.add(inOrder);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, conn);
		}

		return inOrders;
		
	}

}
