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
import com.lingnan.supermarket.view.LoginView;

public class UserInfDialog extends JDialog implements ActionListener {

	private JPanel namePanel, loginNamePanel, phonePanel, opePanel,
			passwordPanel, sSuperPanel;

	private JLabel nameLabel, loginNameLabel, phoneLabel, passwordLabel,
			sSuperLabel;
	private JTextField nameTF, loginNameTF, phoneTF, passwordTF, sSuperTF;

	private JButton saveBtn, cancelBtn;

	private UserService userService = new UserServiceImpl();

	private User user;
	// 下拉权限框
	private int sSuper = -1;
	private JComboBox<String> combo;
	private String[] identity = { "当前权限", "收银员", "管理员", "超级管理员" };
	
	private JFrame JFrame;

	public UserInfDialog(JFrame parent, User user) {
		super(parent, "添加");
		this.user = user;
		this.sSuper = user.getUsuper();

		setSize(350, 300);

		setLocationRelativeTo(null);

		setModal(true);
		setResizable(false);

		this.setLayout(new FlowLayout());
		
		this.JFrame=parent;

		initView();
	}

	private void initView() {

		namePanel = new JPanel();
		nameLabel = new JLabel("姓名");
		nameTF = new JTextField(user.getRname(), 15);
		namePanel.add(nameLabel);
		namePanel.add(nameTF);

		loginNamePanel = new JPanel();
		loginNameLabel = new JLabel("账号");
		loginNameTF = new JTextField(user.getUsername(), 15);
		loginNamePanel.add(loginNameLabel);
		loginNamePanel.add(loginNameTF);

		phonePanel = new JPanel();
		phoneLabel = new JLabel("联系");
		phoneTF = new JTextField(user.getPhone(), 15);
		phonePanel.add(phoneLabel);
		phonePanel.add(phoneTF);

		passwordPanel = new JPanel();
		passwordLabel = new JLabel("密码");
		passwordTF = new JTextField(user.getPassword(), 15);
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordTF);

/*		sSuperPanel = new JPanel();
		sSuperLabel = new JLabel("权限");
		// sSuperTF = new JTextField(15);
		combo = new JComboBox<String>(identity);
		combo.addItemListener(new MyItemListener());
		sSuperPanel.add(sSuperLabel);
		sSuperPanel.add(combo);*/

		// sSuperPanel.add(sSuperTF);

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
		/*container.add(sSuperPanel);*/
		container.add(opePanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == saveBtn) {
			// 思路获取数据
			// 保存到数据库
			// 关闭对话框
			// 刷新table

			String name = nameTF.getText();
			String loginName = loginNameTF.getText();
			String phone = phoneTF.getText();
			String password = passwordTF.getText();

			// TODO 参数校验
			User user1 = new User();
			user1.setId(user.getId());
			user1.setRname(name);
			user1.setUsername(loginName);
			user1.setPhone(phone);
			user1.setPassword(password);
			user1.setImg(user.getImg());
			user1.setUsuper(sSuper);

			int result = userService.updateByIdUser(user1);
			if (result == 1) {
				this.dispose();
				JOptionPane.showMessageDialog(this, "修改成功，请重新登陆", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				this.JFrame.dispose();
				new LoginView();
			} else {
				JOptionPane.showMessageDialog(this, "修改失败", "提示",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (source == cancelBtn) {

			this.dispose();
		}
	}

	public class MyItemListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			JComboBox cb = (JComboBox) e.getSource();
			String sSuper1 = (String) cb.getSelectedItem();
			if (sSuper1.equals("当前权限"))
				sSuper = user.getUsuper();
			else if (sSuper1.equals("收银员"))
				sSuper = 2;
			else if (sSuper1.equals("管理员"))
				sSuper = 1;
			else
				sSuper = 0;
		}

	}
}
