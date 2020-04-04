package com.lingnan.supermarket.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.html.HTMLDocument.Iterator;

import com.lingnan.supermarket.dao.impl.BufferImpl;
import com.lingnan.supermarket.dialog.ChangeSumDialog;
import com.lingnan.supermarket.dialog.OutDialog;
import com.lingnan.supermarket.dialog.UserDialog;
import com.lingnan.supermarket.dto.Buffer;
import com.lingnan.supermarket.dto.User;
import com.lingnan.supermarket.table.*;
import com.lingnan.supermarket.utils.FontUtil;
import com.lingnan.supermarket.utils.TimeAndOrder;

public class OutView extends JPanel implements ActionListener{
	
	
	//上面
	private JPanel toolBarPanel;
	
	private JPanel searchPanel;
	private JLabel nameLabel,locationLabel;
	private JTextField nameSearchTF;
	private JButton searchBtn,AccountBtn,exitBtn;
	
	private JPanel opePanel;
	private JButton addBtn,updateBtn,deleteBtn,historyBtn,backBtn,detailBtn;
	
	//中间
	private JScrollPane tableScrollPane;
	private JTable outTable;
	
	//下面
	private JPanel bottomPanel,bottomPanelLeft,bottomPanelRight;
	private JLabel countInfoLabel,countInfoLabel2;
	
	private Buffer Buffer;
	private BufferImpl BufferImpl;
	
	private Vector<Buffer> v;
	
	
	private JFrame jFrame;
	private User user;
	
	private OutTableModel outTableModel = new OutTableModel();
	
	private int mark;/*标记订单表和订单详情表*/
	
	public OutView(JFrame jFrame,User user) {
		this.setLayout(new BorderLayout());
		initView();
		this.jFrame = jFrame;
		this.user = user;
	}
	
	private void initView() {
		 
		toolBarPanel = new JPanel(new BorderLayout()); 
		
		searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
		nameLabel = new JLabel("订单号");
		nameSearchTF = new JTextField(20);
		
		searchBtn = new JButton(new ImageIcon("static\\icon\\search.png"));
		searchBtn.addActionListener(this);
		locationLabel=new JLabel("当前位置>收银系统");
		locationLabel.setFont(new FontUtil().userFont);
		locationLabel.setForeground(new Color(18, 150, 219));
		
		opePanel =  new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		addBtn =new JButton(new ImageIcon("static\\icon\\add.png"));
		updateBtn =new JButton(new ImageIcon("static\\icon\\change.png"));
		deleteBtn =new JButton(new ImageIcon("static\\icon\\delete.png"));
		historyBtn =new JButton(new ImageIcon("static\\icon\\history.png"));
		backBtn =new JButton(new ImageIcon("static\\icon\\back.png"));
		detailBtn = new JButton(new ImageIcon("static\\icon\\detail.png"));
		backBtn.setVisible(false);/*在记录页面显示出来*/
		detailBtn.setVisible(false);/*在订单详情页显示出来*/
		
		
		addBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		historyBtn.addActionListener(this);
		backBtn.addActionListener(this);
		detailBtn.addActionListener(this);
	
		opePanel.add(addBtn);		
		opePanel.add(backBtn);
		opePanel.add(detailBtn);
		opePanel.add(updateBtn);
		opePanel.add(deleteBtn);
		opePanel.add(historyBtn);
		 
		searchPanel.add(locationLabel);
		searchPanel.add(nameLabel);
		searchPanel.add(nameSearchTF);
		searchPanel.add(searchBtn);
		
		
		toolBarPanel.add(searchPanel,"West");
		toolBarPanel.add(opePanel,"East");
		
		
		//中间表格
		outTableModel = new OutTableModel();
		outTableModel.allOutBuffer();/*查找所有购物车*/
		outTable = new JTable(outTableModel);
		outTable.setFont(FontUtil.tableFont);
		outTable.setRowHeight(50);
		tableScrollPane = new JScrollPane(outTable);
		
		//下面
		bottomPanelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
		countInfoLabel = new JLabel("商品种类:"+outTableModel.getRowCount()+",总价:"+outTableModel.getAllPrice());	
		bottomPanelLeft.add(countInfoLabel);
		
		bottomPanelRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		AccountBtn =new JButton(new ImageIcon("static\\icon\\Account.png"));/*结账按钮*/
		exitBtn =new JButton(new ImageIcon("static\\icon\\exit.png"));/*退出按钮*/
		AccountBtn.addActionListener(this);
		exitBtn.addActionListener(this);
		bottomPanelRight.add(AccountBtn);
		bottomPanelRight.add(exitBtn);
		
		bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(bottomPanelRight,"East");
		bottomPanel.add(bottomPanelLeft,"West");
		
		
		this.add(toolBarPanel,"North");
		this.add(tableScrollPane,"Center");/*将表格放到中间*/
/*		this.add(bottomPanel,"South");*/
		this.add(bottomPanel,"South");
		
		setVisible(true);
	}
	
	
	//按钮组件隐藏
	public void OrderView() {
		backBtn.setVisible(true);
		detailBtn.setVisible(true);
		updateBtn.setVisible(false);
		deleteBtn.setVisible(true);
		addBtn.setVisible(false);
		historyBtn.setVisible(false);
	}
	
	
	
	
	
	//通过订单查找
	public void resultOfNumber(String oNumber) {
		this.mark=1;
		OutOrderTM outOrderTM = new OutOrderTM();
		outOrderTM.resultOfNumber(oNumber);
		outTable.setModel(outOrderTM);
		bottomPanelLeft.removeAll();
		countInfoLabel = new JLabel("共"+outOrderTM.getRowCount()+"条记录");
		bottomPanelLeft.add(countInfoLabel);
		OrderView();
	}
	
	
	/*刷新*/
	public void refreshOutBuffer() {
		OutTableModel outTableModel = new OutTableModel();
		outTableModel.allOutBuffer();
		outTable.setModel(outTableModel);
		bottomPanelLeft.removeAll();
		countInfoLabel = new JLabel("商品种类:"+outTableModel.getRowCount()+",总价:"+outTableModel.getAllPrice());
		bottomPanelLeft.add(countInfoLabel);
		backBtn.setVisible(false);
		detailBtn.setVisible(false);
		historyBtn.setVisible(true);
		updateBtn.setVisible(true);
		addBtn.setVisible(true);
		deleteBtn.setVisible(true);
	}
	
	/*调出收银出货订单表*/
	public void OutOrderRecord() {
		this.mark=1;
		OutOrderTM outOrderTM = new OutOrderTM();
		outOrderTM.allOutOrderRecord();
		outTable.setModel(outOrderTM);
		bottomPanelLeft.removeAll();
		countInfoLabel = new JLabel("共"+outOrderTM.getRowCount()+"条记录");
		bottomPanelLeft.add(countInfoLabel);
		OrderView();
	}
	
	public void OutRecord(String oNumber) {
		this.mark=0;
		OutRecordTM outRecordTM = new OutRecordTM(oNumber);
		outRecordTM.findOutRecordByINumber();
		outTable.setModel(outRecordTM);
		bottomPanelLeft.removeAll();
		countInfoLabel = new JLabel("订单号@"+oNumber+"共有"+outRecordTM.getRowCount()+"条记录");
		bottomPanelLeft.add(countInfoLabel);
		backBtn.setVisible(true);
		detailBtn.setVisible(false);
		updateBtn.setVisible(false);
		addBtn.setVisible(false);
		historyBtn.setVisible(false);
		deleteBtn.setVisible(false);
	}
	
	

	/*按钮监听时间*/
	@Override
	public void actionPerformed(ActionEvent e) {
		BufferImpl = new BufferImpl();/*获得购物车*/
		Object source = e.getSource();
		
		if(searchBtn==source) {
			String number = nameSearchTF.getText();
			resultOfNumber(number);
		}
		
		if(addBtn==source) {
			OutDialog outDialog = new OutDialog(jFrame);
			outDialog.setVisible(true);	
			refreshOutBuffer();
		}
		
		else if(updateBtn==source) {
			int rowIndex = outTable.getSelectedRow();
			if(rowIndex==-1) {
						JOptionPane.showMessageDialog(this,"请选中一条进行更改数量");
						return;
					}
					String id =(String) outTable.getValueAt(rowIndex,0);
					ChangeSumDialog changesumDialog = new ChangeSumDialog(jFrame,id,"Out");
					changesumDialog.setVisible(true);
					refreshOutBuffer();
		}
		
		else if(deleteBtn==source) {
			int rowIndex = outTable.getSelectedRow();
			if(rowIndex==-1) {
						JOptionPane.showMessageDialog(this,"请选中一条");
						return;
					}
					String id =(String) outTable.getValueAt(rowIndex,0);
					int select = JOptionPane.showConfirmDialog(this,"是否删除id为"+id+"的记录","提示",JOptionPane.YES_NO_OPTION);
					if(select==JOptionPane.YES_OPTION) {/*选择是*/
						if(BufferImpl.DelOutBufferById(id)==true) {
							JOptionPane.showMessageDialog(this,"删除成功","提示",JOptionPane.INFORMATION_MESSAGE);				    
						}else {
							JOptionPane.showMessageDialog(this,"删除失败","提示",JOptionPane.ERROR_MESSAGE);
						}
						refreshOutBuffer();
					}
					
			
		}else if(historyBtn==source) {/*查看历史全部记录*/
			OutOrderRecord();
		}else if(backBtn==source) {/*历史记录中的返回按钮*/
			System.out.println("outView中的mark="+mark);
			if(mark==1) {
				refreshOutBuffer();
			}else if(mark==0) {
				OutOrderRecord();
			}
			
		}else if(detailBtn==source) {/*查看订单详细*/
			
			int rowIndex = outTable.getSelectedRow();
			if(rowIndex==-1) {
				JOptionPane.showMessageDialog(this,"请选中一条查看订单详细信息");
				return;
			}
			
			String oNumber =(String) outTable.getValueAt(rowIndex,0);
			System.out.println("详情订单号为="+oNumber);
			OutRecord(oNumber);
		}
		
		
		
		else if(AccountBtn==source) {/*结账*/	
					refreshOutBuffer();
					v = BufferImpl.allOutBuffer();			
					if(v.size()==0)/*购物车为空*/{
						JOptionPane.showMessageDialog(null,"您的购物车为空", "提示", JOptionPane.YES_OPTION);	
					}
					else {/*购物车不为空*/
						int res = JOptionPane.showConfirmDialog(null,"总金额:"+outTableModel.getAllPrice()+"元\r\n是否已经结账", "结账", JOptionPane.YES_NO_OPTION);
						if(res==JOptionPane.YES_OPTION)/*如果已经结账*/{
							
							String[] s =TimeAndOrder.TimeAndOrder(user.getUsername());/*获得时间和订单号*/
							
							BufferImpl.InsertOutOrder(s[0],outTableModel.getAllPrice(), s[1], user.getUsername());/*往订单表插入一条记录*/	
							for(int i=0;i<v.size();i++) {
								Buffer = v.elementAt(i);
								BufferImpl.Account(s[0],s[1],Buffer.getId(),Buffer.getSum(),Buffer.getPrice());/*调用结账存储过程*/
							}
							refreshOutBuffer();/*刷新所有购物车*/
							HomeView.refreshHome();/*刷新首页*/
							JOptionPane.showConfirmDialog(null,"支付成功\r\n订单号:"+s[0]+"\r\n负责人:"+user.getUsername(), "提示", JOptionPane.YES_OPTION);
							
					}
						
						
					}
			
		}else if(exitBtn==source) {
			refreshOutBuffer();
			int res = JOptionPane.showConfirmDialog(null,"确定退出并清空购物车吗", "结账", JOptionPane.YES_NO_OPTION);
			if(res==JOptionPane.YES_OPTION)/*如果已经结账*/{
				BufferImpl.DelAllOutBuffer();
				refreshOutBuffer();/*刷新所有购物车*/
				JOptionPane.showConfirmDialog(null,"退出成功", "提示", JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
	}
