package com.lingnan.supermarket.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.lingnan.supermarket.dao.BufferService;
import com.lingnan.supermarket.dto.Buffer;
import com.lingnan.supermarket.dto.Production;
import com.lingnan.supermarket.utils.JDBCUtil;

public class BufferImpl implements BufferService  {
	
	
	
	

	/*------------------------收银出货模块-----------------------*/
	/*返回出货缓冲表所有delmark=1的记录并排序*/
	public Vector<Buffer> allOutBuffer(){
		Connection conn = JDBCUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet resultSet=null;
		Vector<Buffer> v = new Vector<Buffer>();
		Buffer Buffer;
		try {
			pstmt = conn.prepareStatement("select * from OutBuffer  ");
			resultSet = pstmt.executeQuery();		
			while(resultSet.next()) {
				Buffer = new Buffer();
				Buffer.setId(resultSet.getString("id"));
				Buffer.setName(resultSet.getString("name"));
				Buffer.setInPrice(resultSet.getFloat("inPrice"));
				Buffer.setOutPrice(resultSet.getFloat("OutPrice"));
				Buffer.setLife(resultSet.getInt("life"));
				Buffer.setSum(resultSet.getInt("sum"));
				Buffer.setSupplyId(resultSet.getInt("supplyId"));
				Buffer.setId2(resultSet.getString("id2"));
				Buffer.setName2(resultSet.getString("name2"));		
				Buffer.setPrice(resultSet.getFloat("price"));
				Buffer.setDelmark(resultSet.getInt("delmark"));
				v.add(Buffer);
			}

			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(resultSet, pstmt, conn);
		}
		return v;
	}

	/*通过id查找返回boolean*/
	public Buffer findOutBufferbyId(String id) 
	{
	boolean flag = false;
	Connection conn = JDBCUtil.getConn();
	PreparedStatement pstmt = null;
	ResultSet resultSet=null;
	Buffer buffer = new Buffer();
	try {
		pstmt = conn.prepareStatement("select * from OutBuffer where id=? ");
		pstmt.setString(1, id);
		resultSet = pstmt.executeQuery();
		
		if(resultSet.next()) {
			buffer.setId(resultSet.getString("id"));
			buffer.setName(resultSet.getString("name"));
		    buffer.setInPrice(resultSet.getFloat("inPrice"));
		    buffer.setOutPrice(resultSet.getFloat("OutPrice"));
		    buffer.setLife(resultSet.getInt("life"));
		    buffer.setSum(resultSet.getInt("sum"));
		    buffer.setSupplyId(resultSet.getInt("supplyId"));
		    buffer.setId2(resultSet.getString("id2"));
		    buffer.setName2(resultSet.getString("name2"));
		    buffer.setPrice(resultSet.getFloat("price"));
		    buffer.setDelmark(resultSet.getInt("delmark"));
			flag = true;
		}else {
			return null;
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		JDBCUtil.close(resultSet, pstmt, conn);
	}
	return buffer;
}

	/*如果缓冲区没有那就把整条记录插入插入操作*/
	public boolean addOutBufferNewProd(String id,int sum) {
		boolean flag = false;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet resultSet=null;
		try {
			pstmt = conn.prepareStatement("call InsertOutBuffer(?,?) ");
			pstmt.setString(1, id);
			pstmt.setInt(2, sum);
			
					
			if( pstmt.executeUpdate()==1) {
				flag = true;
			}

			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(resultSet, pstmt, conn);
		}
		return flag;
	}
	
	
	/*根据商品id获取sum和outprice
	public Buffer findOutBufferSumAndOutPrice(String id) {
		Buffer Buffer = new Buffer();
		Connection conn = JDBCUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet resultSet=null;
		try {
			pstmt = conn.prepareStatement("select * from OutBuffer where id=? ");
			pstmt.setString(1, id);
			resultSet = pstmt.executeQuery();
					
			if(resultSet.next()) {
				Buffer.setSum(resultSet.getInt("sum"));
				Buffer.setOutPrice(resultSet.getFloat("OutPrice"));
			}

			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(resultSet, pstmt, conn);
		}
		return Buffer;
	}
	*/

	
	/*如果delmark是1，即是已经添加的商品*/
	public boolean addOutBufferExeistProd(String id,int sum,Buffer buffer){
		boolean flag = false;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;;
		try {
			pstmt = conn.prepareStatement("update  OutBuffer set sum=?,price=? where id=? ");
			pstmt.setInt(1, sum+buffer.getSum());
			pstmt.setFloat(2,( sum+buffer.getSum()*buffer.getOutPrice()));
			pstmt.setString(3, id);
			if(pstmt.executeUpdate()==1) {
				flag = true;
			}

			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {

			JDBCUtil.close(resultSet, pstmt, conn);
		}
		return flag;
	}

	/*获得购物车总价*/
	public Float OutBufferAllPrice(){
		boolean flag = false;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;;
		try {
			pstmt = conn.prepareStatement("select sum(price) from OutBuffer ");
			 resultSet=pstmt.executeQuery();
			if(resultSet.next()) {
				return resultSet.getFloat("sum(price)");
			}

			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {

			JDBCUtil.close(resultSet, pstmt, conn);
		}
		return null;
	}
	
	
	/*结账后对5个表进行操作*/
	public boolean Account(String number,String time,String id,int sum,Float price){
		boolean flag = false;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;;
		try {
			pstmt = conn.prepareStatement("call Account(?,?,?,?,?)");
			pstmt.setString(1,number);
			pstmt.setString(2,time );
			pstmt.setString(3,id );
			pstmt.setInt(4,sum );
			pstmt.setFloat(5, price);
			if(pstmt.execute())
				flag = true;


			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {

			JDBCUtil.close(resultSet, pstmt, conn);
		}
		return flag;
	}
	
	/*删除所有购物车*/
	public boolean DelAllOutBuffer(){
		boolean flag = false;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;;
		try {
			pstmt = conn.prepareStatement("delete from OutBuffer");
			if(pstmt.execute())
				flag = true;


			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {

			JDBCUtil.close(resultSet, pstmt, conn);
		}
		return flag;
	}

	/*往订单表插入一条记录*/
	public boolean InsertOutOrder(String number,Float allPrice,String time,String username){
		boolean flag = false;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;;
		try {
			pstmt = conn.prepareStatement("insert into OutOrder values(?,?,?,?,?)");
			pstmt.setString(1, number);
			pstmt.setFloat(2, allPrice);
			pstmt.setString(3, time);
			pstmt.setString(4, username);
			pstmt.setInt(5, 1);
			if(pstmt.execute())
					flag = true;


			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {

			JDBCUtil.close(resultSet, pstmt, conn);
		}
		return flag;
	}

	/*删除缓冲区OutBuffer一条记录通过id*/
	public boolean DelOutBufferById(String id) {
		boolean flag = false;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		try {
			pstmt = conn.prepareStatement("delete from OutBuffer where id=?");
			pstmt.setString(1, id);
			if(pstmt.executeUpdate()==1)
					flag = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {

			JDBCUtil.close(resultSet, pstmt, conn);
		}
		return flag;
	}
	
	/*更改记录通过id*/
	public boolean UpdateOutBufferById(String id,int sum) {
		boolean flag = false;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		try {
			pstmt = conn.prepareStatement("upadte OutBuffer set sum=? where  id=?");
			pstmt.setInt(1, sum);
			pstmt.setString(2, id);
			if(pstmt.execute())
					flag = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {

			JDBCUtil.close(resultSet, pstmt, conn);
		}
		return flag;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/*---------------------------进货模块----------------------------*/
	/*返回出货缓冲表所有delmark=1的记录并排序*/
	public Vector<Production> allInBuffer(){
		Connection conn = JDBCUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet resultSet=null;
		Vector<Production> v = new Vector<Production>();
		Production production;
		try {
			pstmt = conn.prepareStatement("select * from InBuffer  ");
			resultSet = pstmt.executeQuery();		
			while(resultSet.next()) {
				production = new Production();
				production.setId(resultSet.getString("id"));
				production.setName(resultSet.getString("name"));
				production.setInPrice(resultSet.getFloat("inPrice"));
				production.setOutPrice(resultSet.getFloat("OutPrice"));
				production.setLife(resultSet.getInt("life"));
				production.setSum(resultSet.getInt("sum"));
				production.setSupplyId(resultSet.getInt("supplyId"));
				production.setId2(resultSet.getString("id2"));
				production.setName2(resultSet.getString("name2"));		
				production.setPrice(resultSet.getFloat("price"));
				production.setDelmark(resultSet.getInt("delmark"));
				v.add(production);
			}

			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(resultSet, pstmt, conn);
		}
		return v;
	}

	/*通过id查找返回boolean*/
	public Buffer findInBufferbyId(String id) 
	{
	Connection conn = JDBCUtil.getConn();
	PreparedStatement pstmt = null;
	ResultSet resultSet=null;
	Buffer buffer = new Buffer();
	try {
		pstmt = conn.prepareStatement("select * from InBuffer where id=? ");
		pstmt.setString(1, id);
		resultSet = pstmt.executeQuery();
		
		if(resultSet.next()) {
			buffer.setId(resultSet.getString("id"));
			buffer.setName(resultSet.getString("name"));
		    buffer.setInPrice(resultSet.getFloat("inPrice"));
		    buffer.setOutPrice(resultSet.getFloat("OutPrice"));
		    buffer.setLife(resultSet.getInt("life"));
		    buffer.setSum(resultSet.getInt("sum"));
		    buffer.setSupplyId(resultSet.getInt("supplyId"));
		    buffer.setId2(resultSet.getString("id2"));
		    buffer.setName2(resultSet.getString("name2"));
		    buffer.setPrice(resultSet.getFloat("price"));
		    buffer.setDelmark(resultSet.getInt("delmark"));
		}
		else {
			return null;
		}

		
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		JDBCUtil.close(resultSet, pstmt, conn);
	}
	return buffer;
}

	/*如果缓冲区没有那就把整条记录插入插入操作*/
	public boolean addInBufferNewProd(String id,int sum) {
		boolean flag = false;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet resultSet=null;
		try {
			pstmt = conn.prepareStatement("call InsertInBuffer(?,?) ");
			pstmt.setString(1, id);
			pstmt.setInt(2, sum);
			
					
			if( pstmt.executeUpdate()==1) {
				flag = true;
			}

			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(resultSet, pstmt, conn);
		}
		return flag;
	}
	
	public boolean insertInBuffer(Production p) {
	boolean flag = false;
	Connection conn = JDBCUtil.getConn();
	PreparedStatement pstmt = null;
	ResultSet resultSet=null;
	try {
		pstmt = conn.prepareStatement("insert into InBuffer values(?,?,?,?,?,?,?,?,?,?,?)");
		pstmt.setString(1, p.getId());
		pstmt.setString(2, p.getName());
		pstmt.setFloat(3, p.getInPrice());
		pstmt.setFloat(4, p.getOutPrice());
		pstmt.setInt(5, p.getLife());
		pstmt.setInt(6, p.getSum());
		pstmt.setInt(7, p.getSupplyId());
		pstmt.setString(8, p.getId2());
		pstmt.setString(9, p.getName2());
		pstmt.setFloat(10,p.getPrice());
		pstmt.setInt(11, 1);
		pstmt.executeUpdate();
		
				
		if( pstmt.execute()) {
			flag = true;
		}

		
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		JDBCUtil.close(resultSet, pstmt, conn);
	}
	return flag;
}

	
/*	根据商品id获取sum和outprice
	public Buffer findInBufferSumAndInPrice(String id) {
		Buffer Buffer = new Buffer();
		Connection conn = JDBCUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet resultSet=null;
		try {
			pstmt = conn.prepareStatement("select * from InBuffer where id=? ");
			pstmt.setString(1, id);
			resultSet = pstmt.executeQuery();
					
			if(resultSet.next()) {
				Buffer.setSum(resultSet.getInt("sum"));
				Buffer.setOutPrice(resultSet.getFloat("InPrice"));
			}

			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(resultSet, pstmt, conn);
		}
		return Buffer;
	}*/
	


	/*如果delmark是1，即是已经添加的商品*/
	public boolean addInBufferExeistProd(String id,int sum,Buffer buffer){
		boolean flag = false;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;;
		try {
			pstmt = conn.prepareStatement("update  InBuffer set sum=?,price=? where id=? ");
			pstmt.setInt(1, sum+buffer.getSum());
			pstmt.setFloat(2,( sum+buffer.getSum()*buffer.getInPrice()));
			pstmt.setString(3, id);
			if(pstmt.executeUpdate()==1) {
				flag = true;
			}

			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {

			JDBCUtil.close(resultSet, pstmt, conn);
		}
		return flag;
	}

	/*获得购物车总价*/
	public Float InBufferAllPrice(){
		boolean flag = false;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;;
		try {
			pstmt = conn.prepareStatement("select sum(price) from InBuffer ");
			 resultSet=pstmt.executeQuery();
			if(resultSet.next()) {
				return resultSet.getFloat("sum(price)");
			}

			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {

			JDBCUtil.close(resultSet, pstmt, conn);
		}
		return null;
	}
	
	
	/*结账后对5个表进行操作*/
	public boolean Stock(String number,String time,String id,int sum,Float price){
		boolean flag = false;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;;
		try {
			pstmt = conn.prepareStatement("call Stock(?,?,?,?,?)");
			pstmt.setString(1,number);
			pstmt.setString(2,time );
			pstmt.setString(3,id );
			pstmt.setInt(4,sum );
			pstmt.setFloat(5, price);
			if(pstmt.executeUpdate()==1)
				flag = true;


			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {

			JDBCUtil.close(resultSet, pstmt, conn);
		}
		return flag;
	}
	
	/*删除所有购物车*/
	public boolean DelAllInBuffer(){
		boolean flag = false;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;;
		try {
			pstmt = conn.prepareStatement("delete from InBuffer");
			if(pstmt.execute())
				flag = true;


			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {

			JDBCUtil.close(resultSet, pstmt, conn);
		}
		return flag;
	}


	
	/*删除缓冲区InBuffer一条记录通过id*/
	public boolean DelInBufferById(String id) {
		boolean flag = false;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;;
		try {
			pstmt = conn.prepareStatement("delete from InBuffer where id=?");
			pstmt.setString(1, id);
			if(pstmt.executeUpdate()==1)
					flag = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {

			JDBCUtil.close(resultSet, pstmt, conn);
		}
		return flag;
	}
	
	
	
	/*更改记录通过id*/
	public boolean UpdateInBufferById(String id,int sum) {
		boolean flag = false;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		try {
			pstmt = conn.prepareStatement("upadte InBuffer set sum=? where  id=?");
			pstmt.setInt(1, sum);
			pstmt.setString(2, id);
			if(pstmt.execute())
					flag = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {

			JDBCUtil.close(resultSet, pstmt, conn);
		}
		return flag;
	}

	@Override
	public boolean InsertInOrder(String number, Float allPrice, String time, String username) {
		// TODO Auto-generated method stub
		return false;
	}






}


