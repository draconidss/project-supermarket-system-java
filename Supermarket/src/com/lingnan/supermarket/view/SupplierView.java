package com.lingnan.supermarket.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.lingnan.supermarket.dao.SupplierInfService;
import com.lingnan.supermarket.dao.impl.SupplierInfImpl;
import com.lingnan.supermarket.dialog.SupplierInfDialog;
import com.lingnan.supermarket.dto.SupplierInf;
import com.lingnan.supermarket.table.SupplierTableModel;
import com.lingnan.supermarket.utils.FontUtil;

public class SupplierView extends JPanel implements ActionListener{
	
	
	//上面
	private JPanel toolBarPanel;
	
	private JPanel searchPanel;
	private JLabel nameLabel,locationLabel;
	private JTextField nameSearchTF;
	private JButton searchBtn;
	
	private JPanel opePanel;
	private JButton addBtn,updateBtn,deleteBtn;
	
	//中间
	private JScrollPane tableScrollPane;
	private JTable supplierTable;
	
	//下面
	private JPanel bottomPanel;
	private JLabel countInfoLabel;
	
	private SupplierTableModel supplierTableModel;
	
	private JFrame jFrame;
	
	private SupplierInfService supplierInfService = new SupplierInfImpl(); 
	
	public SupplierView(JFrame jFrame) {
		this.setLayout(new BorderLayout());
		initView();
		this.jFrame = jFrame;
	}
	
	private void initView() {
		 
		toolBarPanel = new JPanel(new BorderLayout()); 
		
		searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		locationLabel=new JLabel("当前位置>供应商");
		locationLabel.setFont(new FontUtil().userFont);
		locationLabel.setForeground(new Color(18, 150, 219));
		nameLabel = new JLabel("公司名称");
		nameSearchTF = new JTextField(10);
		
		searchBtn = new JButton(new ImageIcon("static\\icon\\search.png"));
		
		opePanel =  new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
		addBtn =new JButton(new ImageIcon("static\\icon\\add.png"));
		updateBtn =new JButton(new ImageIcon("static\\icon\\update.png"));
		deleteBtn =new JButton(new ImageIcon("static\\icon\\delete.png"));
		
		addBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		searchBtn.addActionListener(this);
		
		opePanel.add(addBtn);
		opePanel.add(updateBtn);
		opePanel.add(deleteBtn);
		 
		searchPanel.add(locationLabel);
		searchPanel.add(nameLabel);
		searchPanel.add(nameSearchTF);
		searchPanel.add(searchBtn);
		
		
		toolBarPanel.add(searchPanel,"West");
		toolBarPanel.add(opePanel,"East");
		
		
		//中间表格
		SupplierTableModel supplierTableModel = new SupplierTableModel();
		supplierTableModel.all();
		supplierTable = new JTable(supplierTableModel);
		supplierTable.setFont(FontUtil.tableFont);
		supplierTable.setRowHeight(50);
		
		tableScrollPane = new JScrollPane(supplierTable);/*滑动条*/
		
		//下面
		bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		countInfoLabel = new JLabel("总共"+supplierTableModel.getRowCount()+"条");
		bottomPanel.add(countInfoLabel);
		
		
		this.add(toolBarPanel,"North");
		this.add(tableScrollPane,"Center");/*将表格放到中间*/
		this.add(bottomPanel,"South");
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(addBtn==source) {
			SupplierInfDialog supplierInfDialog = new SupplierInfDialog(jFrame, null);
			supplierInfDialog.setVisible(true);
			refreshSupplier();
		}else if(updateBtn==source) {
			refreshSupplier();
		}else if(deleteBtn==source) {
			//获取选中记录
			int rowIndex = supplierTable.getSelectedRow();
			if(rowIndex==-1) {
				JOptionPane.showMessageDialog(this,"请选中一条");
				return;
			}
			int id = (Integer)supplierTable.getValueAt(rowIndex,0);
			int select=JOptionPane.showConfirmDialog(this,"是否删除id="+id,"提示",JOptionPane.YES_NO_OPTION);
			if(select==JOptionPane.YES_OPTION){
			if(supplierInfService.deleteSupplierInf(id)==1) {
				JOptionPane.showMessageDialog(this,"删除成功","提示",JOptionPane.INFORMATION_MESSAGE);
				refreshSupplier();
			}else {
				JOptionPane.showMessageDialog(this,"删除失败","提示",JOptionPane.ERROR_MESSAGE);
			}
			}
		}else{
			System.out.println("搜索");
			refreshFindRname();
		}
	}
	public void refreshFindRname() {
		String name = nameSearchTF.getText();
		SupplierInf supplierInf =new SupplierInf();
		supplierInf.setName(name);
		
		 
	  supplierTableModel = new SupplierTableModel();
		supplierTableModel.Byname(supplierInf);
		
		supplierTable.setModel(supplierTableModel);
		
		refreshCount();
		
	}
	public void refreshSupplier() {
		supplierTableModel = new SupplierTableModel();
		supplierTableModel.all();
		supplierTable.setModel(supplierTableModel);
		
		refreshCount();
	}
	public void refreshCount(){
		bottomPanel.removeAll();
		countInfoLabel = new JLabel("总共"+supplierTableModel.getRowCount()+"条");
		bottomPanel.add(countInfoLabel);
	}
	
}