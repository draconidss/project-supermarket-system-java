package com.lingnan.supermarket.dto;

import java.util.Date;

public class InOrder {
    private String iNumber;
    private Float allInPrice;
    private 	String inDate;
    private String principal;
    private int status;
    private int delmark;
	public String getiNumber() {
		return iNumber;
	}
	public void setiNumber(String iNumber) {
		this.iNumber = iNumber;
	}
	public Float getAllInPrice() {
		return allInPrice;
	}
	public void setAllInPrice(Float allInPrice) {
		this.allInPrice = allInPrice;
	}
	public String getInDate() {
		return inDate;
	}
	public void setInDate(String inDate) {
		this.inDate = inDate;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public int getDelmark() {
		return delmark;
	}
	public void setDelmark(int delmark) {
		this.delmark = delmark;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
