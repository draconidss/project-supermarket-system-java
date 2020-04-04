package com.lingnan.supermarket.dao;

import java.util.Vector;

import com.lingnan.supermarket.dto.OutRecord;

public interface outRecordService {
	//查询全部进货订单
	Vector<OutRecord> findAllOutRecord ();
	//查询某个进货订单
	Vector<OutRecord> findByIdOutRecordr (String oNumber );
	
	
	int addoutRecord(OutRecord or);
	 int deleteOutOrder(String oNumber);
}
