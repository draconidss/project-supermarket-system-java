package com.lingnan.supermarket.dialog;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.lingnan.supermarket.*;
import com.lingnan.supermarket.dao.SupplierInfService;
import com.lingnan.supermarket.dao.impl.SupplierInfImpl;
import com.lingnan.supermarket.dto.SupplierInf;
import com.lingnan.supermarket.view.SupplierView;

public class SupplierInfDialog extends JDialog implements ActionListener {

	private JPanel namePanel, addressPanel, contactPanel,emailPanel, opePanel;

	private JLabel nameLabel, addressLabel, contactLabel,emailLabel;
	private JTextField nameTF, addressTF, contactTF,emailTF;

	private JButton saveBtn, cancelBtn;

	private SupplierInfService supplierInfService = new SupplierInfImpl();

	private SupplierView supplierView;

	private SupplierInf supplierInf;

	public SupplierInfDialog(JFrame parent, SupplierView supplierView) {
		super(parent, "添加");
		this.supplierView = supplierView;

		setSize(350, 300);

		setLocationRelativeTo(null);

		setModal(true);
		setResizable(false);

		this.setLayout(new FlowLayout());

		initView();
	}

	private void initView() {
		namePanel = new JPanel();
		nameLabel = new JLabel("名称");
		nameTF = new JTextField(15);
		namePanel.add(nameLabel);
		namePanel.add(nameTF);

		addressPanel = new JPanel();
		addressLabel = new JLabel("地址");
		addressTF = new JTextField(15);
		addressPanel.add(addressLabel);
		addressPanel.add(addressTF);

		contactPanel = new JPanel();
		contactLabel = new JLabel("电话");
		contactTF = new JTextField(15);
		contactPanel.add(contactLabel);
		contactPanel.add(contactTF);
		
		emailPanel = new JPanel();
		emailLabel = new JLabel("邮箱");
		emailTF = new JTextField(15);
		emailPanel.add(emailLabel);
		emailPanel.add(emailTF);

		opePanel = new JPanel();
		saveBtn = new JButton("保存");
		cancelBtn = new JButton("取消");
		saveBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		opePanel.add(saveBtn);
		opePanel.add(cancelBtn);

		Container container = getContentPane();
		container.add(namePanel);
		container.add(addressPanel);
		container.add(contactPanel);
		container.add(emailPanel);
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
			String address = addressTF.getText();
			String contact = contactTF.getText();
			String email = emailTF.getText();

			// TODO 参数校验
			if (this.supplierInf == null) {
				SupplierInf supplierInf = new SupplierInf();
				supplierInf.setName(name);
				supplierInf.setAddress(address);
				supplierInf.setContact(contact);
				supplierInf.setEmail(email);
				int result = supplierInfService.addSupplierInf(supplierInf);
				// int result = 1;
				if (result == 1) {

					JOptionPane.showMessageDialog(this, "添加成功", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					this.dispose();
				} else {
					JOptionPane.showMessageDialog(this, "添加失败", "提示",
							JOptionPane.ERROR_MESSAGE);
				}
			}/*else{
				//更新
				SupplierInf supplierInf= new SupplierInf();
				supplierInf.setName(name);
				supplierInf.setAddress(address);
				supplierInf.setContact(contact);
				supplierInf.setId(this.supplierInf.getId());
				
				int result = supplierInfService.updateSupplierInf(supplierInf);
				if(result==1){
					JOptionPane.showMessageDialog(this, "更新成功", "提示",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}*/

		} else if (source == cancelBtn) {

			this.dispose();
		}
	}
}
