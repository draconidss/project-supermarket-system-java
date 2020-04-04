package com.lingnan.supermarket.dao.impl;

import java.io.StreamCorruptedException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.lingnan.supermarket.dao.prodCatalogService;
import com.lingnan.supermarket.dto.ProdCatalog;
import com.lingnan.supermarket.utils.JDBCUtil;

public class prodCatalogImpl implements prodCatalogService{

	/*将商品目录表和商品表联合查询*/
	@Override
	public Vector<ProdCatalog> findProdCatalogAndProd() {
		Connection conn=JDBCUtil.getConn();
		Vector<ProdCatalog> v = new Vector<ProdCatalog>();
		ProdCatalog prodCatalog;
		PreparedStatement pstmt = null;
		ResultSet resultSet=null;
		try {
			pstmt = conn.prepareStatement("call allProdCatalog()");
			resultSet = pstmt.executeQuery();
			while(resultSet.next()){
				prodCatalog = new ProdCatalog();
				prodCatalog.setId(resultSet.getString("id"));
				prodCatalog.setName(resultSet.getString("name"));
				v.add(prodCatalog);
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
	public String findProdCatalog(String id1 ) {
		Connection conn=JDBCUtil.getConn();
	
		PreparedStatement pstmt = null;
		ResultSet resultSet=null;
		String catalog=null;
		try {
			pstmt = conn.prepareStatement("select *from prodCatalog where id2 =?");
			pstmt.setString(1,id1);
			

			resultSet = pstmt.executeQuery();
			if(resultSet.next()){
				catalog=resultSet.getString("name1");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.close(resultSet, pstmt, conn);
		}
		
		
		return catalog;
		
	
	}

	@Override
	public int addProdCatalog(String id1, String id2) {
		int flag=0;
		String name1=null;
		//01 食品 02 电器 03 生活用品 04 其他 
		if(id1.equals("01"))
			name1="食品";
		else if(id1.equals("02"))
				name1="电器";
		else if(id1.equals("03"))
				name1="生活用品";
		else 
			name1="其他";
	
		//连接jdbc
		Connection conn=JDBCUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet resultSet=null;
		
		try {
			pstmt = conn.prepareStatement("insert into prodCatalog values(?,?,?)");
			pstmt.setString(1,id1);
			pstmt.setString(2,name1);
			pstmt.setString(3,id2);
			

			pstmt.executeUpdate();
			flag=1;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCUtil.close(resultSet, pstmt, conn);
		}
		
		return flag;
	}
	

	@Override
	public int deleteProdCatalog(String id2) {
		int flag=0;
		//连接jdbc
		Connection conn=JDBCUtil.getConn();
		PreparedStatement pstmt = null;
		ResultSet resultSet=null;
		
		try {
			pstmt = conn.prepareStatement("delete from prodCatalog where id2=?");
			pstmt.setString(1,id2);
			pstmt.executeUpdate();
			flag=1;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.close(resultSet, pstmt, conn);
		}
		
		return flag;
	}


	@Override
	//根据商品类production的name查询并输出类别id
	public String findProdCatalogByname(String name) {
		Connection conn=JDBCUtil.getConn();
		
		PreparedStatement pstmt = null;
		ResultSet resultSet=null;
		
		String catalogid="0";
		try {
			if(name.equals("全部")){
				return catalogid;
			}
			pstmt = conn.prepareStatement("select * from prodCatalog where name =?");
			pstmt.setString(1,name);
			

			resultSet = pstmt.executeQuery();
			if(resultSet.next()){
				catalogid=resultSet.getString("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.close(resultSet, pstmt, conn);
		}
		
		
		return catalogid;
	}
/*	public static void main(String[] args) {
		ProdCatalog p=new ProdCatalog();
		prodCatalogImpl pi=new prodCatalogImpl();
		p.setName("全部");
		System.out.println(pi.findProdCatalogByname(p.getName()));
	}*/


	@Override
	public ArrayList<String> findNameProdCatalog() {
		Connection conn=JDBCUtil.getConn();
		ArrayList<String> v = new ArrayList<String>();
		v.add("全部");
		ProdCatalog prodCatalog;
		PreparedStatement pstmt = null;
		ResultSet resultSet=null;
		try {
			pstmt = conn.prepareStatement("select * from prodCatalog");
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
	/*public static void main(String[] args) {
		prodCatalogImpl pi=new prodCatalogImpl();
		ArrayList<String>log=null;
		
		
		log=pi.findNameProdCatalog();
		String []s=new String[log.size()];
		for(int i=0;i<log.size();i++)
		{s[i]=log.get(i);
		System.out.println(log.get(i));
		}
		for(int i=0;i<s.length;i++)
		{
		System.out.println(s[i]);
		}
		
		
	}*/


}
