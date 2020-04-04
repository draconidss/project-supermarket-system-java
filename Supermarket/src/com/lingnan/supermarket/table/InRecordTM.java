package com.lingnan.supermarket.table;

import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.table.AbstractTableModel;

import com.lingnan.supermarket.dto.Buffer;
import com.lingnan.supermarket.dto.InOrder;
import com.lingnan.supermarket.dto.InRecord;
import com.lingnan.supermarket.dto.Buffer;
import com.lingnan.supermarket.dto.Production;
import com.lingnan.supermarket.dto.User;
import com.lingnan.supermarket.dao.UserService;
import com.lingnan.supermarket.dao.impl.*;
import com.lingnan.supermarket.dialog.InDialog;


public class InRecordTM extends AbstractTableModel{
	
	private String [] columnName = {"订单号","id","数量","金额"};

	private productionImpl prodDao = new productionImpl();
	
	private  Vector<InRecord> InRecords;
	
	private inRecordServiceImpl inRecordImpl = new inRecordServiceImpl();
	private InRecord inRecord= new InRecord();

	
	private String iNumber ;/*订单号*/
	
	
	public InRecordTM(String iNumber) {
		this.iNumber=iNumber;
	}
	
	public void findInRecordByINumber() {
		//将添加的商品加入到静态变量Vector数组中
		/*prod = InDialog.getProduction();*/
		InRecords = inRecordImpl.findByIdinRecord(iNumber);
	}
	
	
	@Override
	public int getRowCount() {
		return InRecords.size();
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
		inRecord = InRecords.get(rowIndex);
/*		System.out.println( "id="+users.get(rowIndex).getId());
		System.out.println("rowIndex"+rowIndex);
		System.out.println("columnIndex"+columnIndex);*/
		iNumber=inRecord.getiNumber();
		if(columnIndex==0) {
			return inRecord.getiNumber();
		}else if(columnIndex==1) {
			return inRecord.getId();
		}else if(columnIndex==2) {
			return inRecord.getSum();	
		}else if(columnIndex==3) {
			return  inRecord.getInPrice();
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
