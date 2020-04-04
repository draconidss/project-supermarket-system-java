package com.lingnan.supermarket.table;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.lingnan.supermarket.dao.impl.storageRecordImpl;
import com.lingnan.supermarket.dto.StorageRecord;


public class StorageRecordTM extends AbstractTableModel{
	
	private String [] columnName = {"订单号","操作时间","商品编号","进货+/出货-","数量"};

	private storageRecordImpl srDao = new storageRecordImpl();
	
	private  Vector<StorageRecord> storageRecords;
	private StorageRecord storageRecord ;
	
	String oNumber ;/*订单号*/
	
	
	public void allStoragrRecord() {
		//将添加的商品加入到静态变量Vector数组中
		/*prod = InDialog.getProduction();*/
		storageRecords = srDao.findAllStorageRecord();
	}
	
	
	@Override
	public int getRowCount() {
		return storageRecords.size();
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
		storageRecord = storageRecords.get(rowIndex);
/*		System.out.println( "id="+users.get(rowIndex).getId());
		System.out.println("rowIndex"+rowIndex);
		System.out.println("columnIndex"+columnIndex);*/
		oNumber=storageRecord.getTheNumber();
		if(columnIndex==0) {
			return storageRecord.getTheNumber();
		}else if(columnIndex==1) {
			return storageRecord.getcDate();
		}else if(columnIndex==2) {
			return storageRecord.getId();
		}else if(columnIndex==3) {
			return  storageRecord.getExecute();
		}else if(columnIndex==4) {
			return  storageRecord.getNum();
		}else {
			return null;
		}
	}
	 

	
	
	@Override
	public String getColumnName(int column) {
		return columnName[column];
	}
	
	
}
