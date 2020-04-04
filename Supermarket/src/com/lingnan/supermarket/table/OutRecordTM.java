package com.lingnan.supermarket.table;

import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.table.AbstractTableModel;

import com.lingnan.supermarket.dto.Buffer;
import com.lingnan.supermarket.dto.InOrder;
import com.lingnan.supermarket.dto.InRecord;
import com.lingnan.supermarket.dto.OutRecord;
import com.lingnan.supermarket.dto.Production;
import com.lingnan.supermarket.dto.User;
import com.lingnan.supermarket.dao.UserService;
import com.lingnan.supermarket.dao.impl.*;
import com.lingnan.supermarket.dialog.InDialog;


public class OutRecordTM extends AbstractTableModel{
	
	/**
	 * 
	 */

	private String [] columnName = {"订单号","id","数量","金额"};

	private productionImpl prodDao = new productionImpl();
	
	private  Vector<OutRecord> OutRecords;
	
	private outRecordServiceImpl outRecordImpl = new outRecordServiceImpl();
	private OutRecord outRecord= new OutRecord();

	
	private String oNumber ;/*订单号*/
	
	
	public OutRecordTM(String oNumber) {
		this.oNumber=oNumber;
	}
	
	public void findOutRecordByINumber() {
		//将添加的商品加入到静态变量Vector数组中
		/*prod = InDialog.getProduction();*/
		OutRecords = outRecordImpl.findByIdOutRecordr(oNumber);
	}
	
	

	
	
	@Override
	public int getRowCount() {
		return OutRecords.size();
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
		outRecord = OutRecords.get(rowIndex);
/*		System.out.println( "id="+users.get(rowIndex).getId());
		System.out.println("rowIndex"+rowIndex);
		System.out.println("columnIndex"+columnIndex);*/
		oNumber=outRecord.getoNumber();
		if(columnIndex==0) {
			return outRecord.getoNumber();
		}else if(columnIndex==1) {
			return outRecord.getId();
		}else if(columnIndex==2) {
			return outRecord.getSum();	
		}else if(columnIndex==3) {
			return  outRecord.getOutPrice();
		}else {
			return null;
		}
	}
	 
	public String getONumber() {  /*返回要修改或删除的记录*/
		return oNumber;
	}
	
	
	@Override
	public String getColumnName(int column) {
		return columnName[column];
	}
	
	
}
