package com.lingnan.supermarket.dao;

import java.util.Vector;

import com.lingnan.supermarket.dto.Buffer;
import com.lingnan.supermarket.dto.Production;
public interface BufferService {

	/*主要用于更新操作*/
	
	/*根据商品id判断delmark是0还是1,即是否是新添加的商品返回2，还是已经添加的商品返回1，还是不存在的商品返回0*/
	public Vector<Buffer> allOutBuffer();
	public Buffer findOutBufferbyId(String id)  ;	
	public boolean addOutBufferNewProd(String id,int sum);
	public boolean addOutBufferExeistProd(String id,int sum,Buffer buffer);
	/*public Buffer findOutBufferSumAndOutPrice(String id);*/
	public Float OutBufferAllPrice();
	public boolean Account(String number,String time,String id,int sum,Float price);
	public boolean InsertOutOrder(String number,Float allPrice,String time,String username);
	public boolean DelAllOutBuffer();
	public boolean DelOutBufferById(String id);
	public boolean UpdateOutBufferById(String id,int sum);
	
	
	/*进货模块*/
	public Vector<Production> allInBuffer();
	public Buffer findInBufferbyId(String id)  ;	
	public boolean addInBufferNewProd(String id,int sum);
	public boolean addInBufferExeistProd(String id,int sum,Buffer buffer);
	/*public Buffer findInBufferSumAndInPrice(String id);*/
	public Float InBufferAllPrice();
	public boolean Stock(String number,String time,String id,int sum,Float price);
	public boolean InsertInOrder(String number,Float allPrice,String time,String username);
	public boolean DelAllInBuffer();
	public boolean DelInBufferById(String id);
	public boolean UpdateInBufferById(String id,int sum);
}
