package com.lingnan.supermarket.dialog;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
import com.lingnan.supermarket.dao.impl.inOrderServiceImpl;
import com.lingnan.supermarket.dao.impl.inRecordServiceImpl;
import com.lingnan.supermarket.dao.impl.productionImpl;
import com.lingnan.supermarket.dao.impl.storageRecordImpl;
import com.lingnan.supermarket.dto.Buffer;
import com.lingnan.supermarket.dto.InOrder;
import com.lingnan.supermarket.dto.InRecord;
import com.lingnan.supermarket.dto.Production;
import com.lingnan.supermarket.dto.User;
import com.lingnan.supermarket.table.OutTableModel;
import com.lingnan.supermarket.utils.TimeAndOrder;
import com.lingnan.supermarket.view.HomeView;
import com.lingnan.supermarket.view.OutView;
import com.lingnan.supermarket.view.ProdCatalogView.MyItemListener;


public class ChangeStatusDialog extends JDialog implements ActionListener{
	
	
	private JPanel statusPanel,opePanel,titlePanel,comboPanel;
	


	private JLabel titleLabel,statusLabel;

	
	private JButton UpdateBtn,cancelBtn;
	
	
	
	private String iNumber;
	private String status;
	
	private Vector<InRecord> InRecords;
	
	private JComboBox<String> combo;

	private String[] log = { "待入库", "已入库", "取消订单" };
	
	private inRecordServiceImpl inRecordImpl;
	
	private int catalog;
	

	private productionImpl productionImpl;
	
	
	
	
	public ChangeStatusDialog(JFrame parent,String iNumber,String status) {
		super(parent,"修改进货订单状态");
		
		setSize(400,200);	
		setLocationRelativeTo(null);		
		setModal(true);
		setResizable(false);		 
		this.setLayout(new FlowLayout());
		
		this.iNumber=iNumber;
		this.status=status;
		initView();
	}
	
	
	
	
	
	
	private void initView() {

		titlePanel = new JPanel();
		titleLabel = new JLabel("修改订单为"+iNumber+"的状态");
		titlePanel.add(titleLabel);

	
		statusPanel = new JPanel();
		statusLabel = new JLabel("当前状态:"+status);
		statusPanel.add(statusLabel);
				

		comboPanel = new JPanel();
		combo = new JComboBox<String>(log);/*下拉表*/
		combo.addItemListener(new MyItemListener());
		comboPanel.add(combo);
		
		
		opePanel = new JPanel();
		UpdateBtn = new JButton("更改");
		cancelBtn = new JButton("取消");
		UpdateBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		opePanel.add(UpdateBtn);
		opePanel.add(cancelBtn);
		
		Container container = getContentPane();
		container.add(titlePanel,"North");
		container.add(statusPanel,"Center");
		container.add(comboPanel,"South");
		container.add(opePanel);
		

	}	
	
	
	public class MyItemListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			JComboBox cb = (JComboBox) e.getSource();
			String catalog1 = (String) cb.getSelectedItem();
			if (catalog1.equals("已入库")) {
				catalog = 1;
			}
			else if (catalog1.equals("待入库")) {
				catalog = 2;
			} else if (catalog1.equals("取消订单")) {
				catalog = 3;
			} 

		}

	}
	
	
	
	public Vector<InRecord> getVector(){
		return InRecords;
	}

	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source==UpdateBtn){
			System.out.println("此时此刻的catalog为"+catalog);
			//这里修改进货订单表的状态*/
			inOrderServiceImpl inOrderImpl = new inOrderServiceImpl();
			inRecordServiceImpl inRecordImpl = new inRecordServiceImpl();
			storageRecordImpl storageRecordImpl = new storageRecordImpl();
			productionImpl = new productionImpl();
			Production p ;
			//获得订单信息
			
			//修改状态
			inOrderImpl.updateInOrderStatus(iNumber,catalog);
			//确认进货，修改状态并对库存和库存日志修改
			if(catalog==1) {
				//获得订单详细信息
				this.InRecords = inRecordImpl.findByIdinRecord(iNumber);
				//遍历添加库存
				String s[]=TimeAndOrder.TimeAndOrder("");/*生成时间*/
				for(InRecord i:InRecords) {
					//查找到原来的价格
					//更新库存表
					productionImpl.updateProductionSum(i.getId(),i.getSum());	
					//增加库存日志表
					storageRecordImpl.insertStorageRecord(iNumber,s[1], i.getId(),"+", i.getSum());
				}
				
				JOptionPane.showMessageDialog(this,"订单已确认,库存更新成功","提示",JOptionPane.INFORMATION_MESSAGE);
			}		
			/*刷新首页*/
			this.dispose();

		}
		else if(source == cancelBtn) {
			
			this.dispose();
		}
	}
}
