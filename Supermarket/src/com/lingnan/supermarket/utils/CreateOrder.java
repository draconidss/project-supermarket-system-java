package com.lingnan.supermarket.utils;

import java.util.Vector;

import com.lingnan.supermarket.dto.Buffer;
import com.lingnan.supermarket.dto.Production;

public class CreateOrder {

	
	public String CreateOrder(Vector<Production> v,String oNumber,String time,Float allPrice,String username) {
		String xx="----------------------------------------------------------------------------\r\n";
		String InRequireText=time+"\r\n"+xx;
		InRequireText += "#名称               #单价          #数量          #金额\r\n";/*生成订单小票*/
		for(Production p:v) {
			InRequireText+=p.getName()+"          "+p.getInPrice()+"  "+p.getSum()+"  "+p.getPrice()+"\r\n";
		}
		InRequireText+="\r\n"+xx;
		InRequireText+="#总进货金额:"+allPrice+"元";
		InRequireText+="\r\n#负责人:"+username;
		InRequireText+="\r\n#订单编号:"+oNumber;
		InRequireText+="\r\n#地址:新民超市";
		InRequireText+="\r\n#联系电话:xxx";
		
		return InRequireText;
	}
	
}
