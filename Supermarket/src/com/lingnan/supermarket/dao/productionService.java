package com.lingnan.supermarket.dao;

import java.util.Vector;

import com.lingnan.supermarket.dto.Production;
import com.lingnan.supermarket.dto.User;

public interface productionService {
	//查找并输出全部商品
	Vector<Production> findAllproduction ();
	//由一个商品名查找并输出全部商品
		Vector<Production> findproduction (String name);
	Production findByNameProduction(String name);
	
	//由一个商品类别id2查找并输出全部商品
			Vector<Production> findProductionById2 (String id);

	/*通过id查找商品*/
	public Production findByIdProduction(String id);
	//根据id增加
	int addProduction(Production p);
	
	//根据id删除
	public int deleteProduction(String id) ;
	
	//根据id更新
	 int updateProduction(Production p) ;
	 
	 boolean updateProductionSum(String prodId,int sum);

}
