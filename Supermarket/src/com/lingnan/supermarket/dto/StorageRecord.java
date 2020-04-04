package com.lingnan.supermarket.dto;

import java.util.Date;

import com.lingnan.supermarket.dto.base.BsDomain;

public class StorageRecord extends BsDomain{
         private String theNumber;
         private String cDate;
         private int num;
         private String execute;
		public String getTheNumber() {
			return theNumber;
		}
		public void setTheNumber(String theNumber) {
			this.theNumber = theNumber;
		}
		public String getcDate() {
			return cDate;
		}
		public void setcDate(String cDate) {
			this.cDate = cDate;
		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
		public String getExecute() {
			return execute;
		}
		public void setExecute(String execute) {
			this.execute = execute;
		}
}
