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

import com.lingnan.supermarket.dao.UserService;
import com.lingnan.supermarket.dao.impl.UserServiceImpl;
import com.lingnan.supermarket.dialog.UserDialog;
import com.lingnan.supermarket.dto.User;
import com.lingnan.supermarket.table.UserTableModel;
import com.lingnan.supermarket.utils.FontUtil;

public class UserView extends JPanel implements ActionListener{
	
	
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
	private JTable userTable;
	
	//删除时选中赋值给id
	private int id;
	
	//下面
	private JPanel bottomPanel;
	private JLabel countInfoLabel;
	
	private JFrame jFrame;
	
	
	private UserTableModel userTableModel ;
	
	
	private UserService userService=new UserServiceImpl();
	public UserView(JFrame jFrame) {
		this.setLayout(new BorderLayout());
		initView();
		this.jFrame = jFrame;
	}
	
	private void initView() {
		 
		toolBarPanel = new JPanel(new BorderLayout()); 
		
		searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		locationLabel=new JLabel("当前位置>人员管理");
		locationLabel.setFont(new FontUtil().userFont);
		locationLabel.setForeground(new Color(18, 150, 219));
		nameLabel = new JLabel("姓名");
		nameSearchTF = new JTextField(10);
		
		searchBtn = new JButton(new ImageIcon("static\\icon\\search.png"));
		
		/*右边功能模块*/
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
	    userTableModel = new UserTableModel();
		userTableModel.all();
	
		userTable = new JTable(userTableModel);
		userTable.setFont(FontUtil.tableFont);
		userTable.setRowHeight(50);
		tableScrollPane = new JScrollPane(userTable);
		
		//下面
		bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		countInfoLabel = new JLabel("总共"+userTableModel.getRowCount()+"条");
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
			UserDialog userDialog = new UserDialog(jFrame);
			userDialog.setVisible(true);
			refreshUser();
		}else if(updateBtn==source) {
		
			refreshUser();
		}else if(deleteBtn==source) {
			//获取选中记录
			int rowIndex = userTable.getSelectedRow();
			if(rowIndex==-1) {
				JOptionPane.showMessageDialog(this,"请选中一条");
				return;
			}
			int id = (Integer)userTable.getValueAt(rowIndex,0);
			int select=JOptionPane.showConfirmDialog(this,"是否删除id="+id,"提示",JOptionPane.YES_NO_OPTION);
			if(select==JOptionPane.YES_OPTION){
				
				if(userService.deleteUser(id)==1) {
					JOptionPane.showMessageDialog(this,"删除成功","提示",JOptionPane.INFORMATION_MESSAGE);
					
				}else {
					JOptionPane.showMessageDialog(this,"删除失败","提示",JOptionPane.ERROR_MESSAGE);
				}
			}
			refreshUser();
			
		}else{
			System.out.println("搜索");
			refreshFindRname();
		}
		
	}
	public void refreshFindRname() {
		String rname = nameSearchTF.getText();
		User user =new User();
		user.setRname(rname);
		userTableModel = new UserTableModel();
		userTableModel.Byrname(user);
		userTable.setModel(userTableModel);
		//同时更新下面的记录数
				refreshCount();
	}
	public void refreshUser() {
		userTableModel = new UserTableModel();
		userTableModel.all();
		userTable.setModel(userTableModel);
		//同时更新下面的记录数
		refreshCount();
		
	}
	public void refreshCount(){
		bottomPanel.removeAll();
		countInfoLabel = new JLabel("总共"+userTableModel.getRowCount()+"条");
		bottomPanel.add(countInfoLabel);
	}
}

