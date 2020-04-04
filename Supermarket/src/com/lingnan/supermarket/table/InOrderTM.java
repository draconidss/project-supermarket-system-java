package com.lingnan.supermarket.table;

import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.table.AbstractTableModel;

import com.lingnan.supermarket.dto.Buffer;
import com.lingnan.supermarket.dto.InOrder;
import com.lingnan.supermarket.dto.Buffer;
import com.lingnan.supermarket.dto.Production;
import com.lingnan.supermarket.dto.User;
import com.lingnan.supermarket.dao.UserService;
import com.lingnan.supermarket.dao.impl.*;
import com.lingnan.supermarket.dialog.InDialog;


public class InOrderTM extends AbstractTableModel{
	
	private String [] columnName = {"订单号","总价","时间","负责人","状态"};

	private productionImpl prodDao = new productionImpl();
	
	private  Vector<InOrder> InOrders;
	private inOrderServiceImpl inOrderImpl= new inOrderServiceImpl();
	private InOrder inOrder ;
	
	String iNumber ;/*订单号*/
	
	
	public void allInOrderRecord() {
		//将添加的商品加入到静态变量Vector数组中
		/*prod = InDialog.getProduction();*/
		InOrders = inOrderImpl.findAllInOrder();
	}
	
	//查找分类结果
	public void resultOfFind(int catalog) {
		if(catalog==0)
			InOrders = inOrderImpl.findAllInOrder();
		else
			InOrders = inOrderImpl.FindStatus(catalog);
	}
	
	//根据订单查找
	public void resultOfNumber(String Number) {
		InOrders=new Vector<InOrder>();
		inOrder = inOrderImpl.findByIdinOrder(Number);
		InOrders.add(inOrder);
	}
	
	
	
	
	
	
	@Override
	public int getRowCount() {
		return InOrders.size();
	}
	
/*	public Float getAllPrice() {
		return BufferImpl.InBufferAllPrice();
	}
*/
	@Override
	public int getColumnCount() {  
		return columnName.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		inOrder = InOrders.get(rowIndex);
/*		System.out.println( "id="+users.get(rowIndex).getId());
		System.out.println("rowIndex"+rowIndex);
		System.out.println("columnIndex"+columnIndex);*/
		iNumber=inOrder.getiNumber();
		if(columnIndex==0) {
			return inOrder.getiNumber();
		}else if(columnIndex==1) {
			return inOrder.getAllInPrice();
		}else if(columnIndex==2) {
			return inOrder.getInDate();	
		}else if(columnIndex==3) {
			return  inOrder.getPrincipal();
		}else if(columnIndex==4) {
			String status = null;
			if(inOrder.getStatus()==1)
				status= "已入库";
			else if(inOrder.getStatus()==2)
				status= "待入库";
			else if(inOrder.getStatus()==3)
				status= "已取消";
			return status;
		}else {
			return null;
		}
	}
	 
	public String getINumber() {  /*返回要修改或删除的记录*/
		return iNumber;
	}
	
	
	@Override
	public String getColumnName(int column) {
		return columnName[column];
	}
	
	
}
