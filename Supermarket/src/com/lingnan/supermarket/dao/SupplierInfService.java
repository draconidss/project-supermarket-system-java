package com.lingnan.supermarket.dao;

import java.util.ArrayList;
import java.util.Vector;

import com.lingnan.supermarket.dto.SupplierInf;
import com.lingnan.supermarket.dto.User;

public interface SupplierInfService {
	//查询操作 加where default==1
	 Vector<SupplierInf> findAllSupplierInf();
	 
	 Vector<SupplierInf> findByNameSupplierInf(SupplierInf supplierInf);
	// SupplierInf findByNameSupplierInf(String name);
	
	//根据id增加
	int addSupplierInf(SupplierInf s);
	
	//根据id删除
	//int deleteSupplierInf(String id);
	
	//根据id更新
	int updateSupplierInf(SupplierInf supplierInf);

	int deleteSupplierInf(int id);
	
	//输出所有供应商名字
	ArrayList<String> findNameSupplier();
	
	//通过供应商名查找并输出id
	int findIdSupplierByName(String name);
	}
