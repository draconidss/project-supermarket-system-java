package com.lingnan.supermarket.dto.base;

public class BaseProduction {
	private String id;
	private String name;
    private float inPrice;
    private float OutPrice;
    private int life;
    private int sum;
    private int supplyId;
    private String id2;
    private String name2;
    private Float price;
    private int delmark;
    
    
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getDelmark() {
		return delmark;
	}
	public void setDelmark(int delmark) {
		this.delmark = delmark;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getInPrice() {
		return inPrice;
	}
	public void setInPrice(float inPrice) {
		this.inPrice = inPrice;
	}
	public float getOutPrice() {
		return OutPrice;
	}
	public void setOutPrice(float outPrice) {
		OutPrice = outPrice;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	
	public String getId2() {
		return id2;
	}
	public void setId2(String id2) {
		this.id2 = id2;
	}
	
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	
	public int getSupplyId() {
		return supplyId;
	}
	public void setSupplyId(int supplyId) {
		this.supplyId = supplyId;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
}
