package com.lingnan.supermarket.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.lingnan.supermarket.dao.impl.prodCatalogImpl;
import com.lingnan.supermarket.dialog.UserDialog;
import com.lingnan.supermarket.dto.ProdCatalog;
import com.lingnan.supermarket.dto.Production;
import com.lingnan.supermarket.dto.User;
import com.lingnan.supermarket.table.ProdCatalogTM;
import com.lingnan.supermarket.table.StorageTableModel;
import com.lingnan.supermarket.table.UserTableModel;
import com.lingnan.supermarket.utils.FontUtil;

public class ProdCatalogView extends JPanel  {

	// 上面
	private JPanel toolBarPanel;

	private JPanel searchPanel;
	private JLabel logLabel, locationLabel;
	private JTextField nameSearchTF;
	private JButton searchBtn;

	private JPanel opePanel;
	private JButton addBtn, updateBtn, deleteBtn;

	private String catalog = "0";
	private JComboBox<String> combo;
	private String log[]=null;
	private ArrayList<String>alog=null;
	private ProdCatalogTM prodCatalogTM;
	private ProdCatalog pc;
	private prodCatalogImpl pci;

	// 中间
	private JScrollPane tableScrollPane;
	private JTable prodCatalogTable;

	// 下面
	private JPanel bottomPanel;
	private JLabel countInfoLabel;

	private JFrame jFrame;

	public ProdCatalogView(JFrame jFrame) {
		this.setLayout(new BorderLayout());
		initView();
		this.jFrame = jFrame;
	}
	

	private void initView() {

		toolBarPanel = new JPanel(new BorderLayout());

		searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		locationLabel = new JLabel("商品目录");
		locationLabel.setFont(new FontUtil().userFont);
		locationLabel.setForeground(new Color(18, 150, 219));
		logLabel = new JLabel("分类");
		nameSearchTF = new JTextField(10);

		searchBtn = new JButton("搜索", new ImageIcon("static\\icon\\search.png"));

//		opePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//		addBtn = new JButton(new ImageIcon("static\\icon\\add.png"));
//		updateBtn = new JButton(new ImageIcon("static\\icon\\update.png"));
//		deleteBtn = new JButton(new ImageIcon("static\\icon\\delete.png"));

//		addBtn.addActionListener(this);
//		updateBtn.addActionListener(this);
//		deleteBtn.addActionListener(this);

//		opePanel.add(addBtn);
//		opePanel.add(updateBtn);
//		opePanel.add(deleteBtn);
		
		pci=new prodCatalogImpl();
		this.alog=pci.findNameProdCatalog();
		this.log=new String[alog.size()];
		for(int i=0;i<alog.size();i++)
		log[i]=alog.get(i);
		
		for(int i=0;i<log.length;i++)
		{
		System.out.println(log[i]);
		}
		
		
			
		

		combo = new JComboBox<String>(log);
		combo.addItemListener(new MyItemListener());

		searchPanel.add(locationLabel);
		searchPanel.add(logLabel);
		searchPanel.add(combo);
		/*
		 * searchPanel.add(nameSearchTF); searchPanel.add(searchBtn);
		 */

		toolBarPanel.add(searchPanel, "West");
//		toolBarPanel.add(opePanel, "East");

		// 中间表格
		prodCatalogTM = new ProdCatalogTM();
		prodCatalogTM.all();
		prodCatalogTable = new JTable(prodCatalogTM);
		prodCatalogTable.setFont(FontUtil.tableFont);
		prodCatalogTable.setRowHeight(50);
		tableScrollPane = new JScrollPane(prodCatalogTable);

		// 下面
		bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		countInfoLabel = new JLabel("总共" + prodCatalogTable.getRowCount() + "条");
		bottomPanel.add(countInfoLabel);

		this.add(toolBarPanel, "North");
		this.add(tableScrollPane, "Center");/* 将表格放到中间 */
		this.add(bottomPanel, "South");

		setVisible(true);
	}

//	@Override
//	public void actionPerformed(ActionEvent e) {
//		Object source = e.getSource();
//		if (addBtn == source) {
//			UserDialog userDialog = new UserDialog(jFrame);
//			userDialog.setVisible(true);
//		} else if (updateBtn == source) {
//
//		} else if (deleteBtn == source) {
//
//		}
//	}

	public class MyItemListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			JComboBox cb = (JComboBox) e.getSource();
			String catalog1 = (String) cb.getSelectedItem();
			pci =new prodCatalogImpl();
			for(int i=0;i<log.length;i++){
				if(catalog1.equals(log[i]))
						
						
					catalog=pci.findProdCatalogByname(catalog1);
				System.out.println(catalog);
			}
			refreshFindId2();
		}

	}

	// 刷新当前界面
	public void refreshFindId2() {
		Production p = new Production();

		p.setId2(catalog);
		prodCatalogTM = new ProdCatalogTM();
		prodCatalogTM.ById2(p);
		prodCatalogTable.setModel(prodCatalogTM);
		// 同时更新下面的记录数
		refreshCount();
	}

	public void refreshCount() {
		bottomPanel.removeAll();
		countInfoLabel = new JLabel("总共" + prodCatalogTM.getRowCount() + "条");
		bottomPanel.add(countInfoLabel);
	}

}