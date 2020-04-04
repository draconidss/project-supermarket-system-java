package com.lingnan.supermarket.dto;

import com.lingnan.supermarket.dto.base.BsDomain;

public class InRecord extends BsDomain{
	private String iNumber;
	private int sum;
    private Float inPrice;
	public String getiNumber() {
		return iNumber;
	}
	public void setiNumber(String iNumber) {
		this.iNumber = iNumber;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public Float getInPrice() {
		return inPrice;
	}
	public void setInPrice(Float inPrice) {
		this.inPrice = inPrice;
	}
}
