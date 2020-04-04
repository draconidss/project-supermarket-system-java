package com.lingnan.supermarket.dao;

import java.util.ArrayList;
import java.util.Vector;

import com.lingnan.supermarket.dto.ProdCatalog;

public interface prodCatalogService {
	//根据商品类production的id查询并输出类别
	Vector<ProdCatalog> findProdCatalogAndProd();
	String findProdCatalog(String id1 );
	int addProdCatalog(String id1,String id2);
	int deleteProdCatalog(String id2);
	
	//根据商品类production的name查询并输出类别id
	String findProdCatalogByname(String name );
	
	//查找所有商品类的类名并以数组输出
	ArrayList<String> findNameProdCatalog();

	
}
