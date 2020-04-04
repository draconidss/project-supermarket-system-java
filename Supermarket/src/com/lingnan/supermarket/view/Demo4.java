package com.lingnan.supermarket.view;
import java.awt. *;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing. *;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.awt.event.*;
public  class Demo4 extends JFrame implements ActionListener
{
   static Demo4 tplb=new Demo4();
   static JLabel pan=new JLabel();
    
    static ImageIcon[] imgs = {
            new ImageIcon("s"),
            new ImageIcon("static\\bg\\bg1.jpg"),
            new ImageIcon("static\\bg\\bg2.jpg"),
            new ImageIcon("static\\bg\\bg3.jpg"),
            new ImageIcon("static\\bg\\bg4.jpg"),
            new ImageIcon("static\\bg\\bg5.jpg"),
            new ImageIcon("static\\bg\\bg6.jpg"),
            new ImageIcon("static\\bg\\bg7.jpg"),
        };
    
	public static void settplb()/*�ܿ��*/
	{
		tplb.setTitle("ͼƬ�ֲ�����");
		tplb.setLayout(null);
		tplb.setSize(700,800);
		tplb.setResizable(false);
		tplb.setLocationRelativeTo(null);/*���þ���*/
		tplb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);/*�رճ���*/
		tplb.setVisible(true);
	}


    public static void setpan() 
    {
    	pan.setBounds(50, 50, 500, 500);
    	tplb.add(pan);
        Timer timer = new Timer(1000,L);
        timer.start();
    }
    
    static ActionListener L=new ActionListener() 
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
	
	
	
	
    public static void main(String[] args) {
        settplb();
        setpan();
    }


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		
	}


	
}
