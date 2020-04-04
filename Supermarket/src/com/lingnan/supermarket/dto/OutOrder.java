package com.lingnan.supermarket.dto;

import java.util.Date;

public class OutOrder {
       private String oNumber;
       private Float allOutPrice;
       private Date oDate;
       private String principal;
       private int delmark;
	public String getoNumber() {
		return oNumber;
	}
	public void setoNumber(String oNumber) {
		this.oNumber = oNumber;
	}
	public Float getAllOutPrice() {
		return allOutPrice;
	}
	public void setAllOutPrice(Float allOutPrice) {
		this.allOutPrice = allOutPrice;
	}
	public Date getoDate() {
		return oDate;
	}
	public void setoDate(Date oDate) {
		this.oDate = oDate;
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
}
