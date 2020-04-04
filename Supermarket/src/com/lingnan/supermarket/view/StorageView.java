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

import com.lingnan.supermarket.dao.productionService;
import com.lingnan.supermarket.dao.impl.productionImpl;
import com.lingnan.supermarket.dialog.ProductionDialog;
import com.lingnan.supermarket.dialog.UserDialog;
import com.lingnan.supermarket.dto.Production;
import com.lingnan.supermarket.table.StorageRecordTM;
import com.lingnan.supermarket.table.StorageTableModel;
import com.lingnan.supermarket.utils.FontUtil;

public class StorageView extends JPanel implements ActionListener{
	
	
	//上面
	private JPanel toolBarPanel;
	
	private JPanel searchPanel;
	private JLabel nameLabel,locationLabel;
	private JTextField nameSearchTF;
	private JButton searchBtn;
	
	private JPanel opePanel;
	private JButton addBtn,updateBtn,deleteBtn,historyBtn,backBtn;
	
	//中间
	private JScrollPane tableScrollPane;
	private JTable storageTable;
	
	//下面
	private JPanel bottomPanel;
	private JLabel countInfoLabel;
	
	private StorageTableModel storageTableModel ;
	private JFrame jFrame;
	
	private productionService productionService=new productionImpl();
	
	public StorageView(JFrame jFrame) {
		this.setLayout(new BorderLayout());
		initView();
		this.jFrame = jFrame;
	}
	
	private void initView() {
		 
		toolBarPanel = new JPanel(new BorderLayout()); 
		
		searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		locationLabel=new JLabel("当前位置>商品库存");
		locationLabel.setFont(new FontUtil().userFont);
		locationLabel.setForeground(new Color(18, 150, 219));
		nameLabel = new JLabel("商品名");
		nameSearchTF = new JTextField(10);
		
		searchBtn = new JButton(new ImageIcon("static\\icon\\search.png"));
		
		opePanel =  new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
		addBtn =new JButton(new ImageIcon("static\\icon\\add.png"));
		updateBtn =new JButton(new ImageIcon("static\\icon\\update.png"));
		deleteBtn =new JButton(new ImageIcon("static\\icon\\delete.png"));
		backBtn =new JButton(new ImageIcon("static\\icon\\back.png"));
		historyBtn =new JButton(new ImageIcon("static\\icon\\history.png"));
		backBtn.setVisible(false);
		
		addBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		searchBtn.addActionListener(this);
		backBtn.addActionListener(this);
		historyBtn.addActionListener(this);
		
		opePanel.add(addBtn);
		opePanel.add(updateBtn);
		opePanel.add(deleteBtn);
		opePanel.add(backBtn);
		opePanel.add(historyBtn);
		 
		searchPanel.add(locationLabel);
		searchPanel.add(nameLabel);
		searchPanel.add(nameSearchTF);
		searchPanel.add(searchBtn);
		
		
		toolBarPanel.add(searchPanel,"West");
		toolBarPanel.add(opePanel,"East");
		
		
		//中间表格
		storageTableModel = new StorageTableModel();
		storageTableModel.all();
		storageTable = new JTable(storageTableModel);
		storageTable.setFont(FontUtil.tableFont);
		storageTable.setRowHeight(50);
		
		tableScrollPane = new JScrollPane(storageTable);
		
		//下面
		bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		countInfoLabel = new JLabel("总共"+storageTableModel.getRowCount()+"条");
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
			//添加数据的对话框
			ProductionDialog productionDialog = new ProductionDialog(jFrame);
			productionDialog.setVisible(true);
			refreshProduction();
		}else if(updateBtn==source) {
			refreshProduction();
		}else if(historyBtn==source) {
			storageRecord();
		}else if(backBtn==source) {
			refreshProduction();
		}
		
		else if(deleteBtn==source) {
			//获取选中记录,删除
			int[] rowIndexs = storageTable.getSelectedRows();
			if(rowIndexs.length==0) {
				JOptionPane.showMessageDialog(this,"请至少选中一条");
				return;
			}
			int select=JOptionPane.showConfirmDialog(this,"是否删除选中的"+rowIndexs.length+"条记录","提示",JOptionPane.YES_NO_OPTION);
			if(select==JOptionPane.YES_OPTION){
				
			for(int i=0;i<rowIndexs.length;i++){
			String id = (String)storageTable.getValueAt(rowIndexs[i],0);
				if(productionService.deleteProduction(id)==1) {	
					
				}else {
					JOptionPane.showMessageDialog(this,"删除失败,id="+id,"提示",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			JOptionPane.showMessageDialog(this,"删除操作结束","提示",JOptionPane.INFORMATION_MESSAGE);
			}
			refreshProduction();

		}else if(source==searchBtn){
			refreshFindRname();
		}
	}
	
	
	
	
	public void refreshFindRname() {
		String name = nameSearchTF.getText();
		Production p=new Production();
		p.setName(name);
		storageTableModel = new StorageTableModel();
		storageTableModel.Byname(p);
		storageTable.setModel(storageTableModel);
		//同时更新下面的记录数
		refreshCount();
		hiddinBtn();
	}
	public void refreshProduction() {
		storageTableModel = new StorageTableModel();
		storageTableModel.all();
		storageTable.setModel(storageTableModel);
		//同时更新下面的记录数
		refreshCount();
		hiddinBtn();
	}
	public void refreshCount(){
		bottomPanel.removeAll();
		countInfoLabel = new JLabel("总共"+storageTableModel.getRowCount()+"条");
		bottomPanel.add(countInfoLabel);
		hiddinBtn();
	}
	
	public void storageRecord() {
		StorageRecordTM storageRecordTM= new StorageRecordTM();
		storageRecordTM.allStoragrRecord();
		storageTable.setModel(storageRecordTM);
		//同时更新下面的记录数
		bottomPanel.removeAll();
		countInfoLabel = new JLabel("总共"+storageRecordTM.getRowCount()+"条");
		bottomPanel.add(countInfoLabel);
		backBtn.setVisible(true);
		updateBtn.setVisible(false);
		addBtn.setVisible(false);
		historyBtn.setVisible(false);
		deleteBtn.setVisible(false);
		
	}
	
	public void hiddinBtn() {
		backBtn.setVisible(false);
		updateBtn.setVisible(true);
		addBtn.setVisible(true);
		historyBtn.setVisible(true);
		deleteBtn.setVisible(true);
	}
	
	
	
}