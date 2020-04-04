package com.lingnan.supermarket.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import com.lingnan.supermarket.dao.outOrderService;
import com.lingnan.supermarket.dto.InOrder;
import com.lingnan.supermarket.dto.OutOrder;
import com.lingnan.supermarket.utils.JDBCUtil;

public class outOrderServiceImpl implements outOrderService{

	@Override
	public Vector<OutOrder> findAllOutOrder() {
		Vector<OutOrder> outOrders = new Vector<OutOrder>();
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet=null;
		
		String SQL = "select * from OutOrder  order by oDate desc";
		
		try {
			preparedStatement = conn.prepareStatement(SQL);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				OutOrder outOrder = new OutOrder();
				outOrder.setoNumber(resultSet.getString("oNumber"));
				outOrder.setAllOutPrice(resultSet.getFloat("allOutPrice"));
				outOrder.setoDate(resultSet.getDate("oDate"));
				outOrder.setPrincipal(resultSet.getString("principal"));
				outOrders.add(outOrder);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, conn);
		}

		return outOrders;
	}

	@Override
	public OutOrder findByIdOutOrder(String oNumber) {
		OutOrder outOrder = new OutOrder();
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = conn.prepareStatement("select * from outOrder where oNumber = ?");
			preparedStatement.setString(1, oNumber);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				outOrder.setoNumber(resultSet.getString("oNumber"));
				outOrder.setAllOutPrice(resultSet.getFloat("allOutPrice"));
				outOrder.setoDate(resultSet.getDate("oDate"));
				outOrder.setPrincipal(resultSet.getString("principal"));
			} else {
				return null; // 没有找到该订单或订单不存在，返回null
			} 

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, conn);
		}
		return outOrder;
	
	}

	@Override
	public int addOutOrder(String oNumber, float allOutPrice) {
		int flag = 0;
		Timestamp oDate = new Timestamp(System.currentTimeMillis()); 
		
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement
					("insert into outOrder values (?,?,?,?)");
			preparedStatement.setString(1, oNumber);
			preparedStatement.setFloat(2, allOutPrice);
			preparedStatement.setTimestamp(3, oDate);
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
	public int deleteOutOrder(String oNumber) {
		int flag = 0;
		
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement
					("delete from outOrder where oNumber = ?");
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

	
	//获取今日进货金额
		public Float TodayOutPrice(String date) {

			InOrder inOrder = new InOrder();
			Connection conn = JDBCUtil.getConn();
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			Float allInPrice=(float) 0;

			try {
				preparedStatement = conn.prepareStatement("select sum(allOutPrice) from outOrder where oDate>=? and oDate<=date_add(?,interval 1 day)");
				preparedStatement.setString(1, date);
				preparedStatement.setString(2, date);
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					allInPrice=resultSet.getFloat("sum(allOutPrice)");
				} 
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBCUtil.close(resultSet, preparedStatement, conn);
			}
			return allInPrice;
		}
}
