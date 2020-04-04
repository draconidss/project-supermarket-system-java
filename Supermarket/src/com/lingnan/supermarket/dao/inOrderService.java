package com.lingnan.supermarket.dao;

import java.util.Vector;

import com.lingnan.supermarket.dto.InOrder;

public interface inOrderService {
	//查询全部进货订单
	Vector<InOrder> findAllInOrder ();
	//查询某个进货订单
	InOrder findByIdinOrder (String iNumber);
	
	//默认princial为a1. 创建时new一个时间插入,总价随机模拟
	int addInOrder(String iNumber,float allInPrice );
	int deleteInOrder(String iNumber);


}
