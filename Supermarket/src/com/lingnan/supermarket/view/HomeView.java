package com.lingnan.supermarket.view;
import java.awt. *;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.imageio.ImageIO;
import javax.swing. *;

import com.lingnan.supermarket.componet.BGPanel;
import com.lingnan.supermarket.dao.impl.inOrderServiceImpl;
import com.lingnan.supermarket.dao.impl.outOrderServiceImpl;
import com.lingnan.supermarket.dto.User;
import com.lingnan.supermarket.table.UserTableModel;
import com.lingnan.supermarket.utils.FontUtil;
import com.lingnan.supermarket.utils.TimeAndOrder;
import com.lingnan.supermarket.view.base.BaseView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.awt.event.*;
public  class HomeView extends JPanel implements ActionListener
{

   private JLabel pan;

private static JLabel pan1;

private static JLabel pan2;

private static JLabel pan3;
   
   private JFrame jFrame;
   
   private JPanel tplbPanel;
   
   private Image bgImage = null;
   
   private ImageIcon[] imgs;
   
   private static Float allInPrice;

private static Float allOutPrice;
   
   private JButton refreshBtn;
   
   private JPanel priceJPanel;
   
   private static String date;
   
   private static  inOrderServiceImpl inOrderImpl ;
   private static outOrderServiceImpl outOrderImpl;
   

   
   public HomeView(JFrame jFrame) {
  this.setLayout(null);
  this.jFrame = jFrame;
  initView();

 }

 
 private void initView() {
  

	  
	  pan = new JLabel();
	  pan.setBounds(0,0, 1280,351);
	  
	  /*tplbPanel.add(pan);*/
	  
	  imgs =new  ImageIcon[7];
	   for(int i =0;i<7;i++) {
	      imgs[i]=new ImageIcon("static\\轮播\\0"+i+".jpg");
	     }
  	   pan.setIcon(imgs[6]);	  
	  Timer timer = new Timer(2500,L);
	  timer.start();	 
	  this.add(pan);
	  
		 date= TimeAndOrder.yMdTime();/*获取今天时间*/
		 inOrderImpl = new inOrderServiceImpl();
		 outOrderImpl = new outOrderServiceImpl();
		 
		 allInPrice=inOrderImpl.TodayInPrice(date);
		 allOutPrice=outOrderImpl.TodayOutPrice(date);
		 System.out.println("今日allInprice="+allInPrice);
		 System.out.println("今日allOutprice="+allOutPrice);
		 
		  pan1 = new JLabel("今日进货总金额:"+allInPrice+"元",new ImageIcon("static\\icon\\money.png"),JLabel.LEFT);
		  pan2 = new JLabel("今日收银总金额:"+allOutPrice+"元",new ImageIcon("static\\icon\\income.png"),JLabel.LEFT);
		  pan3 = new JLabel("今日被投诉次数:0次",new ImageIcon("static\\icon\\complaints.png"),JLabel.LEFT);
		  
		  pan1.setFont(FontUtil.homeFont);
		  pan2.setFont(FontUtil.homeFont);
		  pan3.setFont(FontUtil.homeFont);
		  
		  pan1.setBounds(280,300, 600,200);
		  pan2.setBounds(280,400,  600,200);
		  pan3.setBounds(280,500,  600,200);
		  
	/*	  priceJPanel = new JPanel();
		  priceJPanel.setBounds(100,200,700,500);*/
		  this.add(pan1);
		  this.add(pan2);
		  this.add(pan3);
	  
	
	  
	  refreshBtn = new JButton(new ImageIcon("static\\icon\\refresh.png"));
	  refreshBtn.addActionListener(this);
	  refreshBtn.setBounds(1050,700, 40,40);
	  this.add(refreshBtn);
	  
	  
  

 }
	 ActionListener L=new ActionListener() 
	 {
			int index;
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				pan.setIcon(imgs[index]);
				index++;
				if(index==7)
					index=0;
			}
		};
		
	public static void refreshHome() {

		 date= TimeAndOrder.yMdTime();/*获取今天时间*/
		 
		 allInPrice=inOrderImpl.TodayInPrice(date);
		 allOutPrice=outOrderImpl.TodayOutPrice(date);
		 System.out.println("今日allInprice="+allInPrice);
		 System.out.println("今日allOutprice="+allOutPrice);
		 
		  pan1.setText("今日进货总金额:"+allInPrice+"元");
		  pan2.setText("今日收银总金额:"+allOutPrice+"元");
		  pan3.setText("今日被投诉次数:0次");
		  
		  
	/*	  priceJPanel = new JPanel();
		  priceJPanel.setBounds(100,200,700,500);*/
/*		  this.add(pan1);
		  this.add(pan2);
		  this.add(pan3);*/
	}
 
 
 
 
 
 
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if(source==refreshBtn) {
			new HomeView(jFrame);
			refreshHome();
		}
		
	}
 
 
 
}