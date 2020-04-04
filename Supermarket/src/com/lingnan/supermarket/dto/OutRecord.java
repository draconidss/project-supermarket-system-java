package com.lingnan.supermarket.dto;

import com.lingnan.supermarket.dto.base.BsDomain;

public class OutRecord extends BsDomain{
     private String oNumber;
     private int sum;
     private Float outPrice;
	public String getoNumber() {
		return oNumber;
	}
	public void setoNumber(String oNumber) {
		this.oNumber = oNumber;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public Float getOutPrice() {
		return outPrice;
	}
	public void setOutPrice(Float outPrice) {
		this.outPrice = outPrice;
	}
}
