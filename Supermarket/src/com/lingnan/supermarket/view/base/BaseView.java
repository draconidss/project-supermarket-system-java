package com.lingnan.supermarket.view.base;

import java.awt.Toolkit;
import java.util.Vector;

import javax.swing.JFrame;

import com.lingnan.supermarket.dto.Production;
import com.lingnan.supermarket.dto.User;

public abstract class BaseView extends JFrame {
	
	public BaseView(int initWidth,int initHeigth,String title) {
		this.setTitle(title);
		this.setSize(initWidth,initHeigth);
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation((width-getWidth())/2,(height-getHeight())/2);
				
		initView();

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/*带对象参数的构造方法*/
	public BaseView(int initWidth,int initHeigth,String title,User user,int skin) {
		this.setTitle(title);
		this.setSize(initWidth,initHeigth);
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation((width-getWidth())/2,(height-getHeight())/2);
				
		

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	

	
	protected abstract void initView();
	protected abstract void initView(User user,int skin);

	
	/*判断权限并返回字符*/
	public String Usuper(int usuper) {
		String x=null;
		if(usuper==0)
			x="管理员";
		else if(usuper==1)
			x="进货员";
		else if(usuper==2)
			x="收银员";
		return x;
	}
	
	/*判断权限并返回icon地址*/
	public String UsuperIcon(int usuper) {
		String x=null;
		if(usuper==0)
			x="static\\icon\\admin.png";
		else if(usuper==1)
			x="static\\icon\\carrier.png";
		else if(usuper==2)
			x="static\\icon\\cashier.png";
		return x;
	}


}
