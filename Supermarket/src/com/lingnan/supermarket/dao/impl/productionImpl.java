package com.lingnan.supermarket.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.lingnan.supermarket.dao.productionService;
import com.lingnan.supermarket.dto.Production;
import com.lingnan.supermarket.utils.JDBCUtil;

public class productionImpl implements productionService {

	@Override
	/**
	 * 查找全部商品信息
	 */
	public Vector<Production> findAllproduction() {
		Vector<Production> productions = new Vector<Production>();

		Connection conn = JDBCUtil.getConn();

		String SQL = "select * from production where delmark = 1";

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = conn.prepareStatement(SQL);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Production production = new Production();
				production.setId(resultSet.getString("id"));
				production.setName(resultSet.getString("name"));
				production.setInPrice(resultSet.getFloat("inPrice"));
				production.setOutPrice(resultSet.getFloat("OutPrice"));
				production.setLife(resultSet.getInt("life"));
				production.setSum(resultSet.getInt("sum"));
				production.setSupplyId(resultSet.getInt("supplyId"));
				production.setId2(resultSet.getString("id2"));
				production.setName2(resultSet.getString("name2"));
				production.setDelmark(resultSet.getInt("delmark"));
				productions.add(production);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, conn);
		}
		return productions;
	}

	/* 用于收银系统，通过商品id返回所有信息 */
	public Production findByIdProduction(String id) {
		Production production = new Production();
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = conn
					.prepareStatement("select * from production where id=? and delmark = 1");
			preparedStatement.setString(1, id);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				production.setId(resultSet.getString("id"));
				production.setName(resultSet.getString("name"));
				production.setInPrice(resultSet.getFloat("inPrice"));
				production.setOutPrice(resultSet.getFloat("OutPrice"));
				production.setLife(resultSet.getInt("life"));
				production.setSum(resultSet.getInt("sum"));
				production.setSupplyId(resultSet.getInt("supplyId"));
				production.setId2(resultSet.getString("id2"));
				production.setName2(resultSet.getString("name2"));
				production.setDelmark(resultSet.getInt("delmark"));
			} else {
				System.out.println("未找到");
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, conn);
		}
		return production;
	}

	@Override
	public Vector<Production> findproduction(String name) {
		/**
		 * 由一个商品名查找并输出全部商品信息
		 */
		Vector<Production> productions = new Vector<Production>();
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = conn.prepareStatement("select * from production where name like ? and  delmark = 1");
			String s='%'+name+'%';
			preparedStatement.setString(1, s);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Production production = new Production();
				production.setId(resultSet.getString("id"));
				production.setName(resultSet.getString("name"));
				production.setInPrice(resultSet.getFloat("inPrice"));
				production.setOutPrice(resultSet.getFloat("OutPrice"));
				production.setLife(resultSet.getInt("life"));
				production.setSum(resultSet.getInt("sum"));
				production.setSupplyId(resultSet.getInt("supplyId"));
				production.setId2(resultSet.getString("id2"));
				production.setName2(resultSet.getString("name2"));
				production.setDelmark(resultSet.getInt("delmark"));
				productions.add(production);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, conn);
		}
		return productions;
	}

	@Override
	/**
	 * 通过商品名字来查找输出单个商品信息
	 */
	public Production findByNameProduction(String name) {
		Production production = new Production();
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = conn
					.prepareStatement("select * from production where name=?");
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				production.setId(resultSet.getString("id"));
				production.setName(resultSet.getString("name"));
				production.setInPrice(resultSet.getFloat("inPrice"));
				production.setOutPrice(resultSet.getFloat("OutPrice"));
				production.setLife(resultSet.getInt("life"));
				production.setSum(resultSet.getInt("sum"));
				production.setSupplyId(resultSet.getInt("supplyId"));
				production.setDelmark(resultSet.getInt("delmark"));
			} else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, conn);
		}
		return production;
	}

	@Override
	/**
	 * 假设商品不存在，增加商品的信息
	 */
	public int addProduction(Production p) {
		int flag = 0;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement1 = null;
		ResultSet resultSet1=null;
		// 假设商品不存在
		try {
			preparedStatement1 = conn.prepareStatement("select * from production where id=?");
			preparedStatement1.setString(1, p.getId());
			resultSet1=preparedStatement1.executeQuery();
			if(resultSet1.next()){
				return flag=2;
			}
			preparedStatement = conn.prepareStatement("insert into production values(?,?,?,?,?,?,?,?,?,?,?)");
			preparedStatement.setString(1, p.getId());
			preparedStatement.setString(2, p.getName());
			preparedStatement.setFloat(3, p.getInPrice());
			preparedStatement.setFloat(4, p.getOutPrice());
			preparedStatement.setInt(5, p.getLife());
			preparedStatement.setInt(6, p.getSum());
			preparedStatement.setInt(7, p.getSupplyId());
			preparedStatement.setString(8, p.getId2());
			preparedStatement.setString(9, p.getName2());
			preparedStatement.setFloat(10, 0);
			preparedStatement.setInt(11, 1);
			preparedStatement.executeUpdate();
			flag=1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, preparedStatement, conn);
		}

		return flag;
	}

	@Override
	/**
	 * 删除商品信息
	 */
	public int deleteProduction(String id) {
		int flag = 0;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = conn
					.prepareStatement("delete from production where id = ?");
			preparedStatement.setString(1, id);
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
	/**
	 * 更新商品信息
	 */
	public int updateProduction(Production p) {
		int flag = 0;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = conn
					.prepareStatement("update production set name=?,inPrice=?,OutPrice=?,life=?,sum=?,delmark=? where id = ? and supplyId=?");

			preparedStatement.setString(1, p.getName());
			preparedStatement.setFloat(2, p.getInPrice());
			preparedStatement.setFloat(3, p.getOutPrice());
			preparedStatement.setInt(4, p.getLife());
			preparedStatement.setInt(5, p.getSum());
			preparedStatement.setInt(6, p.getDelmark());
			preparedStatement.setString(7, p.getId());
			preparedStatement.setInt(8, p.getSupplyId());

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
	public Vector<Production> findProductionById2(String id) {
		/**
		 * 由一个商品类别id2查找并输出全部商品		
		 */

		Vector<Production> productions = new Vector<Production>();
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			if(id.equals("0"))
				preparedStatement = conn.prepareStatement("select * from production where delmark = 1");
			else
				{preparedStatement = conn.prepareStatement("select * from production where id2= ? and  delmark = 1");
			preparedStatement.setString(1, id);}
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Production production = new Production();
				production.setId(resultSet.getString("id"));
				production.setName(resultSet.getString("name"));
				production.setInPrice(resultSet.getFloat("inPrice"));
				production.setOutPrice(resultSet.getFloat("OutPrice"));
				production.setLife(resultSet.getInt("life"));
				production.setSum(resultSet.getInt("sum"));
				production.setSupplyId(resultSet.getInt("supplyId"));
				production.setId2(resultSet.getString("id2"));
				production.setName2(resultSet.getString("name2"));
				production.setDelmark(resultSet.getInt("delmark"));
				productions.add(production);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(resultSet, preparedStatement, conn);
		}
		return productions;
	}
	@Override
	/**
	 * 更新商品数量和价格
	 */
	public boolean updateProductionSum(String prodId,int sum) {
		boolean flag = false;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = conn
					.prepareStatement("update production set sum=?+(select sum from(select sum from production where id = ? and delmark=1 ) p) where id = ? and delmark=1;");

			preparedStatement.setInt(1, sum);
			preparedStatement.setString(2, prodId);
			preparedStatement.setString(3, prodId);

			if(preparedStatement.executeUpdate()==1);
				flag = true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, preparedStatement, conn);
		}
		return flag;
	}

}
