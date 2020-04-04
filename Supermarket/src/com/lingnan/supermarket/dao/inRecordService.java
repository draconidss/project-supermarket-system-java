package com.lingnan.supermarket.dao;

import java.util.Vector;

import com.lingnan.supermarket.dto.InRecord;
import com.lingnan.supermarket.dto.User;

public interface inRecordService {
	//查询全部进货详细订单
	Vector<InRecord> findAllinRecord();
	//查询某个进货详细订单
	public Vector<InRecord> findByIdinRecord(String iNumber);
	
	//默认princial为a1. 创建时new一个时间插入,总价随机模拟
	int addinRecord(InRecord ir );
	int deleteinRecord(String iNumber);
}
