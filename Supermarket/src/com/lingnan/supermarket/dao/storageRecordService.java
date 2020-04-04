package com.lingnan.supermarket.dao;

import java.util.Vector;

import com.lingnan.supermarket.dto.StorageRecord;

public interface storageRecordService {
	/*库存日志,在进货和顾客购买时进行增加或删除操作*/
	//查询全部进货订单
	Vector<StorageRecord> findAllStorageRecord ();
	//查询某个进货订单
	StorageRecord findByIdStorageRecord (String theNumber  );
}
