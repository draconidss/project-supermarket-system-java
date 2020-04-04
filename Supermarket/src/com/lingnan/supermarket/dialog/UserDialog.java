package com.lingnan.supermarket.dialog;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.lingnan.supermarket.*;
import com.lingnan.supermarket.dao.UserService;
import com.lingnan.supermarket.dao.impl.UserServiceImpl;
import com.lingnan.supermarket.dto.User;


public class UserDialog extends JDialog implements ActionListener{

	private JPanel namePanel,loginNamePanel,phonePanel,opePanel,passwordPanel,sSuperPanel;
	
	private JLabel nameLabel,loginNameLabel,phoneLabel,passwordLabel,sSuperLabel;
	private JTextField nameTF,loginNameTF,phoneTF,passwordTF,sSuperTF;
	
	private JButton saveBtn,cancelBtn;
	
	private UserService userService = new UserServiceImpl();
	
	//下拉权限框
	private  static int sSuper=-1;
	private JComboBox<String> combo;
	private String [] identity={"请选择身份","收银员","管理员","超级管理员"};
	
	public UserDialog(JFrame parent) {
		super(parent,"添加");
		
		setSize(350,300);
		
		setLocationRelativeTo(null);
		
		setModal(true);
		setResizable(false);
		 
		this.setLayout(new FlowLayout());
		
		initView();
	}
	
	private void initView() {
		namePanel = new JPanel();
		nameLabel = new JLabel("姓名");
		nameTF = new JTextField(15);
		namePanel.add(nameLabel);
		namePanel.add(nameTF);
		
		loginNamePanel = new JPanel();
		loginNameLabel = new JLabel("账号");
		loginNameTF = new JTextField(15);
		loginNamePanel.add(loginNameLabel);
		loginNamePanel.add(loginNameTF);
		
		phonePanel = new JPanel();
		phoneLabel = new JLabel("手机");
		phoneTF = new JTextField(15);
		phonePanel.add(phoneLabel);
		phonePanel.add(phoneTF);
		
		passwordPanel = new JPanel();
		passwordLabel = new JLabel("密码");
		passwordTF = new JTextField(15);
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordTF);
		
		sSuperPanel = new JPanel();
		sSuperLabel = new JLabel("权限");
//		sSuperTF = new JTextField(15);
		combo= new JComboBox<String>(identity);
		combo.addItemListener(new MyItemListener());
		sSuperPanel.add(sSuperLabel);
		sSuperPanel.add(combo);
		
//		sSuperPanel.add(sSuperTF);
		
		opePanel = new JPanel();
		saveBtn = new JButton("保存");
		cancelBtn = new JButton("取消");
		saveBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		opePanel.add(saveBtn);
		opePanel.add(cancelBtn);
		
		Container container = getContentPane();
		container.add(namePanel);
		container.add(loginNamePanel);
		container.add(passwordPanel);
		container.add(phonePanel);
		container.add(sSuperPanel);
		container.add(opePanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source==saveBtn){
			//思路获取数据
			//保存到数据库
			//关闭对话框
			//刷新table
			
			String name = nameTF.getText();
			String loginName = loginNameTF.getText();
			String phone = phoneTF.getText();
			String password=passwordTF.getText();
			
			//TODO 参数校验
			User user = new User();
			user.setRname(name);
			user.setUsername(loginName);
			user.setPhone(phone);
			user.setPassword(password);
			user.setUsuper(sSuper);
			
			
			
			int result = userService.addUser(user);
			if(result==1) {
				this.dispose();
				JOptionPane.showMessageDialog(this,"添加成功","提示",JOptionPane.INFORMATION_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(this,"添加失败","提示",JOptionPane.ERROR_MESSAGE);
			}
		}else if(source == cancelBtn) {
			
			this.dispose();
		}
	}
	static class MyItemListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			JComboBox cb=(JComboBox)e.getSource();
			String sSuper1=(String) cb.getSelectedItem();
			if(sSuper1.equals("请选择身份"))
				sSuper=-1;
			else if(sSuper1.equals("收银员"))
				sSuper=2;
			else if (sSuper1.equals("管理员"))
				sSuper=1;
			else
				sSuper=0;
		}
		
	}
}
