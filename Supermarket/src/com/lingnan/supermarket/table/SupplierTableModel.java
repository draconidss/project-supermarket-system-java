package com.lingnan.supermarket.table;

import java.util.List;
import java.util.Vector;



import javax.swing.table.AbstractTableModel;

import com.lingnan.supermarket.dto.SupplierInf;
import com.lingnan.supermarket.dao.SupplierInfService;
import com.lingnan.supermarket.dao.impl.*;


public class SupplierTableModel extends AbstractTableModel{
	
	private String [] columnName = {"id","名称","地址","联系方式","邮箱"};

	//private SupplierInfImpl supplierDao = new SupplierInfImpl();
	private SupplierInfService supplierInfService = new SupplierInfImpl();
	
	private SupplierInf supplierInf = new SupplierInf();
	
	private Vector<SupplierInf> suppliers;
	
	private int id=0;
	
	public void all() {
		//查找全部数据
		suppliers = supplierInfService.findAllSupplierInf();
	}

	public void Byname(SupplierInf supplierInf) {
		suppliers = supplierInfService.findByNameSupplierInf(supplierInf);
		
	}

	
	@Override
	public int getRowCount() {
		return suppliers.size();
	}

	@Override
	public int getColumnCount() {  
		return columnName.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		SupplierInf prod = suppliers.get(rowIndex);
		//id = supplierInf.getId();
/*		System.out.println( "id="+users.get(rowIndex).getId());
		System.out.println("rowIndex"+rowIndex);
		System.out.println("columnIndex"+columnIndex);*/
		if(columnIndex==0) {
			return prod.getId();
		}else if(columnIndex==1) {
			return prod.getName();			
		}else if(columnIndex==2) {
			return prod.getAddress();
		}else if(columnIndex==3) {
			return prod.getContact();
		}else if(columnIndex==4){
			return prod.getEmail();
		}
		else {
			return null;
		}
	}
	 
	@Override
	public String getColumnName(int column) {
		return columnName[column];
	}
	
/*
	public int getId() {
		return id;
	}
    public int getValueAt(int rowIndex){
    	SupplierInf supplierInf = suppliers.get(rowIndex);
    	id=suppliers.get(rowIndex).getId();
    	//System.out.println("rowIndex"+rowIndex);
		//System.out.println("columnIndex"+columnIndex);
    	return supplierInf.getId();
    }

 */

	
	
}
