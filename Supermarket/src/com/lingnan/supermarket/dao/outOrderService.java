package com.lingnan.supermarket.dao;

import java.util.Vector;

import com.lingnan.supermarket.dto.InOrder;
import com.lingnan.supermarket.dto.OutOrder;

public interface outOrderService {
	//查询全部进货订单
	Vector<OutOrder> findAllOutOrder ();
	//查询某个进货订单
	OutOrder findByIdOutOrder (String oNumber );
	
	//默认princial为a1. 创建时new一个时间插入,总价随机模拟
	int addOutOrder(String oNumber ,float allOutPrice  );
	int deleteOutOrder(String oNumber );

}
