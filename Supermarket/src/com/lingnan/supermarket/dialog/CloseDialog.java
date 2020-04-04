package com.lingnan.supermarket.dialog;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.lingnan.supermarket.*;
import com.lingnan.supermarket.dao.UserService;
import com.lingnan.supermarket.dao.impl.BufferImpl;
import com.lingnan.supermarket.dao.impl.UserServiceImpl;
import com.lingnan.supermarket.dao.impl.productionImpl;
import com.lingnan.supermarket.dto.Buffer;
import com.lingnan.supermarket.dto.Production;
import com.lingnan.supermarket.dto.User;
import com.lingnan.supermarket.table.OutTableModel;
import com.lingnan.supermarket.view.MainView;
import com.lingnan.supermarket.view.OutView;


public class CloseDialog extends JDialog implements ActionListener{
	
	
	/**
	 * 
	 */
	private JPanel prodIdPanel,sumPanel,phonePanel,opePanel;
	
	private JLabel prodIdLabel,sumLabel;
	private JTextField prodIdTF,sumTF;
	
	private JButton saveBtn,unSaveBtn,cancleBtn;
	
	
	private OutTableModel outTableModel = new OutTableModel();
	
	private BufferImpl bufferImpl;
	
	private User user;
	private Vector<Production> v;
	
	public CloseDialog(JFrame parent,Vector<Production> v) {
		super(parent,"提示");
		setSize(350,130);	
		setLocationRelativeTo(null);		
		setModal(true);
		setResizable(false);		 
		this.setLayout(new FlowLayout());		
		
		this.v = v;
		
		initView();
	}
	
	
	
	
	
	private void initView() {
		prodIdPanel = new JPanel();
		prodIdLabel = new JLabel("您还未结账，是否保存购物车");
		prodIdPanel.add(prodIdLabel);

		
		
		opePanel = new JPanel();
		saveBtn = new JButton("保存");
		unSaveBtn = new JButton("不保存");
		cancleBtn = new JButton("取消");
		saveBtn.addActionListener(this);
		unSaveBtn.addActionListener(this);
		cancleBtn.addActionListener(this);
		opePanel.add(saveBtn);
		opePanel.add(unSaveBtn);
		opePanel.add(cancleBtn);
		
		Container container = getContentPane();
		container.add(prodIdPanel);
		container.add(opePanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		bufferImpl = new BufferImpl();
		if(source==saveBtn){/*吧vector数组加入到缓冲区中*/
			for(Production p:v) {
				System.out.println("保存数据");
				bufferImpl.insertInBuffer(p);
			}
			
			System.exit(0);
		}else if(source==unSaveBtn) {
			bufferImpl = new BufferImpl();
			bufferImpl.DelAllOutBuffer();
			System.exit(0);
		}else if(source==cancleBtn) {
			setVisible(false);

		}/*else if(source==WindowEvent)*/
	}





}
