package com.lingnan.supermarket.table;

import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.table.AbstractTableModel;

import com.lingnan.supermarket.dto.Buffer;
import com.lingnan.supermarket.dto.InOrder;
import com.lingnan.supermarket.dto.OutOrder;
import com.lingnan.supermarket.dto.OutRecord;
import com.lingnan.supermarket.dto.Buffer;
import com.lingnan.supermarket.dto.Production;
import com.lingnan.supermarket.dto.User;
import com.lingnan.supermarket.dao.UserService;
import com.lingnan.supermarket.dao.impl.*;
import com.lingnan.supermarket.dialog.InDialog;


public class OutOrderTM extends AbstractTableModel{
	
	private String [] columnName = {"订单号","总价","时间","负责人"};

	private productionImpl prodDao = new productionImpl();
	
	private  Vector<OutOrder> outOrders;
	private outOrderServiceImpl outOrderImpl= new outOrderServiceImpl();
	private OutOrder outOrder ;
	
	String oNumber ;/*订单号*/
	
	
	public void allOutOrderRecord() {
		//将添加的商品加入到静态变量Vector数组中
		/*prod = InDialog.getProduction();*/
		outOrders = outOrderImpl.findAllOutOrder();
	}
	
	public void resultOfNumber(String oNumber) {
		outOrders = new Vector<OutOrder>();
		outOrder = outOrderImpl.findByIdOutOrder(oNumber);
		outOrders.add(outOrder);
	}
	
	
	
	@Override
	public int getRowCount() {
		return outOrders.size();
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
		outOrder = outOrders.get(rowIndex);
/*		System.out.println( "id="+users.get(rowIndex).getId());
		System.out.println("rowIndex"+rowIndex);
		System.out.println("columnIndex"+columnIndex);*/
		oNumber=outOrder.getoNumber();
		if(columnIndex==0) {
			return outOrder.getoNumber();
		}else if(columnIndex==1) {
			return outOrder.getAllOutPrice();
		}else if(columnIndex==2) {
			return outOrder.getoDate();
		}else if(columnIndex==3) {
			return  outOrder.getPrincipal();
		}else {
			return null;
		}
	}
	 
	public String getOutNumber() {  /*返回要修改或删除的记录*/
		return oNumber;
	}
	
	
	@Override
	public String getColumnName(int column) {
		return columnName[column];
	}
	
	
}
