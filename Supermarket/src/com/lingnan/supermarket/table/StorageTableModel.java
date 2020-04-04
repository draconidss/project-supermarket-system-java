package com.lingnan.supermarket.table;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.lingnan.supermarket.dto.Production;
import com.lingnan.supermarket.dto.User;
import com.lingnan.supermarket.dao.UserService;
import com.lingnan.supermarket.dao.impl.*;


public class StorageTableModel extends AbstractTableModel{
	
	private String [] columnName = {"id","名称","保质期","数量","类别","供应商编号"};

	private productionImpl prodDao = new productionImpl();
	
	private Vector<Production> prods;
	public void all() {
		//查找全部数据
		prods = prodDao.findAllproduction();
	}
	public void Byname(Production p) {
		//查找全部数据
		prods = prodDao.findproduction(p.getName());
	}
	
	@Override
	public int getRowCount() {
		return prods.size();
	}

	@Override
	public int getColumnCount() {  
		return columnName.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Production prod = prods.get(rowIndex);
/*		System.out.println( "id="+users.get(rowIndex).getId());
		System.out.println("rowIndex"+rowIndex);
		System.out.println("columnIndex"+columnIndex);*/
		if(columnIndex==0) {
			return prod.getId();
		}else if(columnIndex==1) {
			return prod.getName();			
		}else if(columnIndex==2) {
			return prod.getLife();	
		}else if(columnIndex==3) {
			return prod.getSum();
		}else if(columnIndex==4) {
			return prod.getName2()+prod.getId2();
		}else if(columnIndex==5) {
			return prod.getSupplyId();
		}else {
			return null;
		}
	}
	 
	@Override
	public String getColumnName(int column) {
		return columnName[column];
	}
	
	
}
