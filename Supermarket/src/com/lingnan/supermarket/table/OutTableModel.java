package com.lingnan.supermarket.table;

import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.table.AbstractTableModel;

import com.lingnan.supermarket.dto.Buffer;
import com.lingnan.supermarket.dto.Production;
import com.lingnan.supermarket.dto.User;
import com.lingnan.supermarket.dao.UserService;
import com.lingnan.supermarket.dao.impl.*;
import com.lingnan.supermarket.dialog.OutDialog;


public class OutTableModel extends AbstractTableModel{
	
	private String [] columnName = {"id","名称","数量","单价","价格","保质期","类别","供应商id"};

	private productionImpl prodDao = new productionImpl();
	
	private  Vector<Buffer> Buffers;
	private  BufferImpl BufferImpl  = new BufferImpl();
	
	
	public void allOutBuffer() {
		//将添加的商品加入到静态变量Vector数组中
		/*prod = OutDialog.getProduction();*/
		Buffers = BufferImpl.allOutBuffer();
	}
	
	
	@Override
	public int getRowCount() {
		return Buffers.size();
	}
	
	public Float getAllPrice() {
		return BufferImpl.OutBufferAllPrice();
	}

	@Override
	public int getColumnCount() {  
		return columnName.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Buffer Buffer = Buffers.get(rowIndex);
/*		System.out.println( "id="+users.get(rowIndex).getId());
		System.out.println("rowIndex"+rowIndex);
		System.out.println("columnIndex"+columnIndex);*/
		if(columnIndex==0) {
			return Buffer.getId();
		}else if(columnIndex==1) {
			return Buffer.getName();			
		}else if(columnIndex==2) {
			return Buffer.getSum();	
		}else if(columnIndex==3) {
			return  Buffer.getOutPrice() ; 
		}else if(columnIndex==4) {
			return  Buffer.getPrice() ;
		}else if(columnIndex==5) {
			return Buffer.getLife();
		}else if(columnIndex==6) {
			return Buffer.getName2()+Buffer.getId2();
		}else if(columnIndex==7) {
			return Buffer.getSupplyId();
		}else {
			return null;
		}
	}
	 
	@Override
	public String getColumnName(int column) {
		return columnName[column];
	}
	
	
}
