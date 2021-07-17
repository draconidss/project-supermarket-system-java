package com.lingnan.supermarket.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.mail.MessagingException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.html.HTMLDocument.Iterator;

import com.lingnan.supermarket.componet.BGPanel;
import com.lingnan.supermarket.dao.impl.BufferImpl;
import com.lingnan.supermarket.dao.impl.UserServiceImpl;
import com.lingnan.supermarket.dao.impl.inOrderServiceImpl;
import com.lingnan.supermarket.dao.impl.inRecordServiceImpl;
import com.lingnan.supermarket.dao.impl.prodCatalogImpl;
import com.lingnan.supermarket.dialog.ChangeStatusDialog;
import com.lingnan.supermarket.dialog.ChangeSumDialog;
import com.lingnan.supermarket.dialog.InDialog;
import com.lingnan.supermarket.dialog.UserDialog;
import com.lingnan.supermarket.dto.Buffer;
import com.lingnan.supermarket.dto.InRecord;
import com.lingnan.supermarket.dto.Production;
import com.lingnan.supermarket.dto.User;
import com.lingnan.supermarket.table.*;
import com.lingnan.supermarket.utils.CreateOrder;
import com.lingnan.supermarket.utils.FontUtil;
import com.lingnan.supermarket.utils.SendQQMailUtil;
import com.lingnan.supermarket.utils.TimeAndOrder;
import com.lingnan.supermarket.view.ProdCatalogView.MyItemListener;

public class InView extends JPanel implements ActionListener{
	
	
	//上面
	private JPanel toolBarPanel;
	
	private JPanel searchPanel;
	private JLabel nameLabel,locationLabel;
	private JTextField nameSearchTF;
	private JButton searchBtn,StockBtn,exitBtn;
	
	private JPanel opePanel;
	private JButton addBtn,updateBtn,deleteBtn,historyBtn,backBtn,detailBtn;
	
	//中间
	private JScrollPane tableScrollPane;
	private JTable inTable;
	
	//下面
	private JPanel bottomPanel,bottomPanelLeft,bottomPanelRight;
	private JLabel countInfoLabel,countInfoLabel2;
	
	private Buffer Buffer;
	private BufferImpl BufferImpl;
	
	private static Vector<Production> v = new  Vector<Production>();
	
	private JComboBox<String> combo;
	private String[] status ={"全部","已入库","待入库","已取消"};
	private int catalog;
	
	private JFrame jFrame;
	private User user;
	
	private InTableModel inTableModel ;
	
	private BufferImpl bufferImpl = new BufferImpl();
	
	private int mark;/*标记从提醒那里来1是进货表，0是提醒过来的表*/
	
	private inOrderServiceImpl inOrderImpl;
	
	private Float allPrice;
	private int row;
	private String uname;
	
	public InView(JFrame jFrame,User user,Vector<Production> v,int mark) {
		this.setLayout(new BorderLayout());		
		this.jFrame = jFrame;
		this.user = user;
		//获得进货缓冲区的保存的货物并删除缓冲区
		this.v =bufferImpl.allInBuffer();
		bufferImpl.DelAllInBuffer();
		
		this.mark=mark;
		System.out.println("mark="+mark);
		uname = user.getUsername();
		initView();
		
		
	}
	
	private void initView() {
		

		toolBarPanel = new JPanel(new BorderLayout()); 
		
		searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
		nameLabel = new JLabel("订单号");
		nameSearchTF = new JTextField(20);
		searchBtn = new JButton(new ImageIcon("static\\icon\\search.png"));
		searchBtn.addActionListener(this);
		locationLabel=new JLabel("当前位置>进货系统");
		locationLabel.setFont(new FontUtil().userFont);
		locationLabel.setForeground(new Color(18, 150, 219));
		
		
		
		combo = new JComboBox<String>(status);
		combo.addItemListener(new MyItemListener());
		
		
		
		opePanel =  new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		addBtn =new JButton(new ImageIcon("static\\icon\\add.png"));
		updateBtn =new JButton(new ImageIcon("static\\icon\\change.png"));
		deleteBtn =new JButton(new ImageIcon("static\\icon\\delete.png"));
		historyBtn =new JButton(new ImageIcon("static\\icon\\history.png"));
		backBtn =new JButton(new ImageIcon("static\\icon\\back.png"));
		detailBtn = new JButton(new ImageIcon("static\\icon\\detail.png"));
		
		addBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		historyBtn.addActionListener(this);
		backBtn.addActionListener(this);
		detailBtn.addActionListener(this);
		
		backBtn.setVisible(false);/*在记录页面显示出来*/
		detailBtn.setVisible(false);/*在订单详情页显示出来*/
		
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
		searchPanel.add(combo);
		
		
		
		toolBarPanel.add(searchPanel,"West");
		toolBarPanel.add(opePanel,"East");
		
		
		//中间表
		inTableModel = new InTableModel(v);

		inTable = new JTable(inTableModel);
		inTable.setFont(FontUtil.tableFont);
		inTable.setRowHeight(50);
		tableScrollPane = new JScrollPane(inTable);
		
		allPrice = inTableModel.getAllPrice();
		row = inTableModel.getRowCount();

		
		//下面
		bottomPanelLeft = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		countInfoLabel = new JLabel("商品种类:"+row+",总价:"+allPrice);	
		bottomPanelLeft.add(countInfoLabel,"Left");
		
		bottomPanelRight = new JPanel(new FlowLayout(FlowLayout.LEFT));
		StockBtn =new JButton(new ImageIcon("static\\icon\\stock.png"));/*结账按钮*/
		exitBtn =new JButton(new ImageIcon("static\\icon\\exit.png"));/*退出按钮*/
		StockBtn.addActionListener(this);
		exitBtn.addActionListener(this);
		bottomPanelRight.add(StockBtn);
		bottomPanelRight.add(exitBtn);
		
		bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(bottomPanelRight,"East");
		bottomPanel.add(bottomPanelLeft,"West");
		
		
		this.add(toolBarPanel,"North");
		this.add(tableScrollPane,"Center");/*将表格放到中间*/
		this.add(bottomPanel,"South");
		
		if(mark==1) /*判断是不是从提醒那里过来的*/{
			refreshBuffer(v);
		}
			
		else if(mark==0) {
			InOrderRecord();
		}
		

		

		
		setVisible(true);
		
		

	
		
		
	}
	
	public static Vector<Production> getVector(){
		return v;
	}
	
	
	public class MyItemListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			JComboBox cb = (JComboBox) e.getSource();
			String catalog1 = (String) cb.getSelectedItem();
			if(catalog1.equals("全部"))
				catalog=0;
			else if(catalog1.equals("已入库"))
				catalog=1;
			else if(catalog1.equals("待入库"))
				catalog=2;
			else if(catalog1.equals("已取消"))
				catalog=3;
			
			resultOfFindStatus(catalog);

			}

		}


	
	
	//按钮组件隐藏
	public void OrderView() {
		backBtn.setVisible(true);
		detailBtn.setVisible(true);
		updateBtn.setVisible(true);
		deleteBtn.setVisible(true);
		addBtn.setVisible(false);
		historyBtn.setVisible(false);
	}
	
	
	public void resultOfNumber(String iNumber) {
		this.mark=0;
		InOrderTM inOrderTM = new InOrderTM();
		inOrderTM.resultOfNumber(iNumber);
		inTable.setModel(inOrderTM);
		bottomPanelLeft.removeAll();
		countInfoLabel = new JLabel("共"+inOrderTM.getRowCount()+"条记录");
		bottomPanelLeft.add(countInfoLabel);
		OrderView();
		}
	
	public void resultOfFindStatus(int catalog) {
		this.mark=0;
		InOrderTM inOrderTM = new InOrderTM();
		inOrderTM.resultOfFind(catalog);
		inTable.setModel(inOrderTM);
		bottomPanelLeft.removeAll();
		countInfoLabel = new JLabel("共"+inOrderTM.getRowCount()+"条记录");
		bottomPanelLeft.add(countInfoLabel);
		OrderView();
	}

	
	/*刷新*/
 	public void refreshBuffer(Vector<Production> v) {
 		this.mark=1;

		InTableModel inTableModel = new InTableModel(v);
		inTable.setModel(inTableModel);
		bottomPanelLeft.removeAll();
		countInfoLabel = new JLabel("商品种类:"+inTableModel.getRowCount()+",总价:"+inTableModel.getAllPrice());
		bottomPanelLeft.add(countInfoLabel);
		backBtn.setVisible(false);
		detailBtn.setVisible(false);
		historyBtn.setVisible(true);
		updateBtn.setVisible(true);
		addBtn.setVisible(true);
		deleteBtn.setVisible(true);
		
 		allPrice = inTableModel.getAllPrice();
		row = inTableModel.getRowCount();
	}
	
	/*调出进货订单表*/
	public void InOrderRecord() {
		this.mark=0;
		InOrderTM inOrderTM = new InOrderTM();
		inOrderTM.allInOrderRecord();
		inTable.setModel(inOrderTM);
		bottomPanelLeft.removeAll();
		countInfoLabel = new JLabel("共"+inOrderTM.getRowCount()+"条记录");
		bottomPanelLeft.add(countInfoLabel);
		OrderView();
		
	}
	
	/*调出进货订单表*/
	public void InRecord(String iNumber) {
		this.mark=2;
		InRecordTM inRecordTM = new InRecordTM(iNumber);
		inRecordTM.findInRecordByINumber();
		inTable.setModel(inRecordTM);
		bottomPanelLeft.removeAll();
		countInfoLabel = new JLabel("订单号@"+iNumber+"共有"+inRecordTM.getRowCount()+"条记录");
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
			System.out.println("搜索后的订单："+number);
			resultOfNumber(number);
		}

		else if(addBtn==source) {/*添加*/
			InDialog outDialog = new InDialog(jFrame,v,user);
			outDialog.setVisible(true);
			v=outDialog.getVector();
			refreshBuffer(v);
			
		}
		
		
		else if(updateBtn==source) {/*更新*/
			System.out.println("mark="+mark);
			int rowIndex = inTable.getSelectedRow();
			if(rowIndex==-1) {
						JOptionPane.showMessageDialog(this,"请选中一条进行更改数量");
						return;
					}
			//进货表修改
			if(mark==1) {
					String id =(String) inTable.getValueAt(rowIndex,0);
					ChangeSumDialog changesumDialog = new ChangeSumDialog(jFrame,id,"In",v);
					changesumDialog.setVisible(true);
					v = changesumDialog.getVector();
					System.out.println("更改状态后v.size="+v.size());
					refreshBuffer(v);
			}
			
			//inOrder修改,修改状态
			else if(mark==0) {								
				String iNumber =(String) inTable.getValueAt(rowIndex,0);
				String status =(String) inTable.getValueAt(rowIndex,4);
				if(status.equals("已入库")) {
					JOptionPane.showMessageDialog(this,"订单上的货物已入库无法修改状态","提示",JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				ChangeStatusDialog changeStatusDialog = new ChangeStatusDialog(jFrame,iNumber,status);
				changeStatusDialog.setVisible(true);
				MainView.refreshRemind();
				HomeView.refreshHome();
				InOrderRecord();
			}
			
		}
		
			
		else if(deleteBtn==source) {
			int rowIndex = inTable.getSelectedRow();
			if(rowIndex==-1) {
						JOptionPane.showMessageDialog(this,"请选中一条");
						return;
					}
			
			/*删除进货表的*/
			if(mark==1) {
				System.out.println("删除进货表");
					String id =(String) inTable.getValueAt(rowIndex,0);
					int select = JOptionPane.showConfirmDialog(this,"是否删除id为"+id+"的记录","提示",JOptionPane.YES_NO_OPTION);
					if(select==JOptionPane.YES_OPTION) {/*选择是*/
						for(int i =0;i<v.size();i++) {
							System.out.println("开始删除");
							if(v.elementAt(i).getId().equals(id))
							{
								v.remove(i);
								
								JOptionPane.showMessageDialog(this,"删除成功","提示",JOptionPane.INFORMATION_MESSAGE);
								break;
							}							

						}
						refreshBuffer(v);
					}
					
				}
					//删除进货订单*/
				else if(mark==0) {
					System.out.println("删除订单表");
						String iNumber =(String) inTable.getValueAt(rowIndex,0);
						int select = JOptionPane.showConfirmDialog(this,"是否删除订单为"+iNumber+"的记录","提示",JOptionPane.YES_NO_OPTION);
						if(select==JOptionPane.YES_OPTION) {/*选择是*/
							System.out.println("iNumber="+iNumber);
							inOrderImpl=new inOrderServiceImpl();
							inOrderImpl.deleteInOrder(iNumber);
							JOptionPane.showMessageDialog(this,"删除成功","提示",JOptionPane.INFORMATION_MESSAGE);
							InOrderRecord();
					}
			}
			
			
		}else if(historyBtn==source) {/*查看历史全部记录*/
			InOrderRecord();
		}else if(backBtn==source) {/*历史记录中的返回按钮*/
			if(mark==0)
				refreshBuffer(v);
			else if(mark==2)
				InOrderRecord();
		}else if(detailBtn==source) {/*查看订单详细*/
			int rowIndex = inTable.getSelectedRow();
			if(rowIndex==-1) {
				JOptionPane.showMessageDialog(this,"请选中一条查看订单详细信息");
				return;
			}
			String iNumber =(String) inTable.getValueAt(rowIndex,0);
			System.out.println("详情订单号为="+iNumber);
			InRecord(iNumber);
		}
				
		
		else if(StockBtn==source) {/*结账*/
					refreshBuffer(v);

					
			
					if(v.size()==0)/*购物车为空*/{
						JOptionPane.showMessageDialog(null,"您的进货页面为空", "提示", JOptionPane.YES_OPTION);	
					}
					else {/*购物车不为空*/
						int res = JOptionPane.showConfirmDialog(null,"进价总金额:"+allPrice+"元\r\n负责人:"+uname+"\r\n发送邮件至1138312802@qq.com", "提交订单", JOptionPane.YES_NO_OPTION);
						if(res==JOptionPane.YES_OPTION)/*如果已经结账*/{
							
							/*获得时间和订单号,s[0]为订单号,s[1]为时间*/
							String[] s =TimeAndOrder.TimeAndOrder(uname);
							
							/*往订单表插入一条记录*/
							inOrderServiceImpl	inOrderImpl	= new inOrderServiceImpl();		
							inOrderImpl.InsertInOrder(s[0], allPrice, s[1],uname ,2);
							
							/*往inRecord表添加数据*/
							inRecordServiceImpl	inRecordImpl	= new inRecordServiceImpl();		
							for(Production p:v) {/*往inRecord表添加数据*/
								inRecordImpl.insertInRecord(s[0], p);
							}
							
							
							/*生成订单文本*/
							CreateOrder createOrder = new CreateOrder();
							String OrderText = createOrder.CreateOrder(v, s[0], s[1], allPrice,uname);
							try {/*发送邮件*/
								SendQQMailUtil QQEmail = new SendQQMailUtil("1138312802@qq.com","自行获取QQ邮箱SMTP密码","1138312802@qq.com","@新民超市进货需求申请",OrderText);
							} catch (MessagingException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							v=new Vector<Production>();
							refreshBuffer(v);
							MainView.refreshRemind();
							JOptionPane.showConfirmDialog(null,"发送邮件成功\r\n订单号:"+s[0]+"\r\n负责人:"+uname, "提示", JOptionPane.YES_OPTION);
							
					}
						
						
				}
			
		}else if(exitBtn==source) {
			int res = JOptionPane.showConfirmDialog(null,"确定退出并清空购物车吗", "结账", JOptionPane.YES_NO_OPTION);
			if(res==JOptionPane.YES_OPTION)/*如果退出*/{
				v=new Vector<Production>();/*将数组置空*/
				refreshBuffer(v);
				JOptionPane.showConfirmDialog(null,"退出成功", "提示", JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
	}
