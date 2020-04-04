package com.lingnan.supermarket.table;

import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.table.AbstractTableModel;


import com.lingnan.supermarket.dto.InOrder;

import com.lingnan.supermarket.dto.Production;
import com.lingnan.supermarket.dto.User;
import com.lingnan.supermarket.dao.UserService;
import com.lingnan.supermarket.dao.impl.*;
import com.lingnan.supermarket.dialog.InDialog;



public class InTableModel extends AbstractTableModel{
	
	
	private String [] columnName = {"id","名称","数量","单价","价格","保质期","类别","供应商id"};

	private productionImpl prodDao = new productionImpl();
	
	private  Vector<Production> v;

	
	String id ;
	
	
	public InTableModel(Vector<Production> v) {
		System.out.println("调用imtablemodel里面的构造函数");
		this.v=v;
	}
	
	
	
	public int getRowCount() {
		return v.size();
	}
	
	public Float getAllPrice() {
		Float allPrice=(float) 0;
		for(Production p:v) {
			allPrice+=p.getPrice();
		}
		return allPrice;
	}

	@Override
	public int getColumnCount() {  
		return columnName.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Production p = v.get(rowIndex);
/*		System.out.println( "id="+users.get(rowIndex).getId());
		System.out.println("rowIndex"+rowIndex);
		System.out.println("columnIndex"+columnIndex);*/
		id=p.getId();
		if(columnIndex==0) {
			return p.getId();
		}else if(columnIndex==1) {
			return p.getName();			
		}else if(columnIndex==2) {
			return p.getSum();	
		}else if(columnIndex==3) {
			return  p.getInPrice() ; 
		}else if(columnIndex==4) {
			return  p.getPrice() ;
		}else if(columnIndex==5) {
			return p.getLife();
		}else if(columnIndex==6) {
			return p.getName2()+p.getId2();
		}else if(columnIndex==7) {
			return p.getSupplyId();
		}else {
			return null;
		}
	}
	 
	public String getId() {  /*返回要修改或删除的记录*/
		return id;
	}
	
	
	@Override
	public String getColumnName(int column) {
		return columnName[column];
	}



	
	
}
