package com.lingnan.supermarket.dto;

import com.lingnan.supermarket.dto.base.BaseDomain;


public class User extends BaseDomain {

	private String username;
	private String rname ;
	private String password;
	private String phone;
	private String img;
	private int usuper;
	private int delmark ;
	
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public int getDelmark() {
		return delmark;
	}
	public void setDelmark(int delmark) {
		this.delmark = delmark;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getUsuper() {
		return usuper;
	}
	public void setUsuper(int usuper) {
		this.usuper = usuper;
	}
	
}
