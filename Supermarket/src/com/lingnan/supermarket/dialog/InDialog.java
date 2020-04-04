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
import com.lingnan.supermarket.dao.impl.productionImpl;
import com.lingnan.supermarket.dto.Buffer;
import com.lingnan.supermarket.dto.Production;
import com.lingnan.supermarket.dto.User;
import com.lingnan.supermarket.table.OutTableModel;
import com.lingnan.supermarket.view.InView;
import com.lingnan.supermarket.view.OutView;
import com.lingnan.supermarket.view.ProdCatalogView.MyItemListener;


public class InDialog extends JDialog implements ActionListener{
	
	
	private JPanel prodIdPanel,sumPanel,phonePanel,opePanel;
	
	private JLabel prodIdLabel,sumLabel;
	private JTextField prodIdTF,sumTF;
	
	private JButton addBtn,cancelBtn;
	
	
	private OutTableModel outTableModel = new OutTableModel();
	
	private Production production;
	private productionImpl productionImpl;
	
	private Vector<Production> v;
	
	private User user;
	
	private JFrame JFramparent;
	
	private JComboBox<String> combo;
	private String allProdId[] = null;
	private Vector<Production> vAll;
	private static String catalog;
	
	public InDialog(JFrame parent,Vector<Production> v,User user) {
		super(parent,"添加商品");
		
		setSize(250,200);	
		setLocationRelativeTo(null);		
		setModal(true);
		setResizable(false);		 
		this.setLayout(new FlowLayout());
		JFramparent=parent;
		this.v=v;
		this.user = user;
		initView();
		
		
	}
	
	
	
	
	
	private void initView() {
		prodIdPanel = new JPanel();
		prodIdLabel = new JLabel("编号");
		

		productionImpl= new productionImpl();
		vAll=productionImpl.findAllproduction();
		allProdId = new String[vAll.size()];
		for(int i=0;i<vAll.size();i++) {
			allProdId[i]=vAll.elementAt(i).getId();
		}
		catalog = allProdId[0];
		System.out.println(allProdId[0]);
		combo = new JComboBox<String>(allProdId);
		combo.addItemListener(new MyItemListener());
		
		prodIdPanel.add(prodIdLabel);
		prodIdPanel.add(combo);
		
		
		
		sumPanel = new JPanel();
		sumLabel = new JLabel("数量");
		sumTF = new JTextField(10);
		sumPanel.add(sumLabel);
		sumPanel.add(sumTF);
		
		
		opePanel = new JPanel();
		addBtn = new JButton("添加");
		cancelBtn = new JButton("取消");
		addBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		opePanel.add(addBtn);
		opePanel.add(cancelBtn);
		
		Container container = getContentPane();
		container.add(prodIdPanel);
		container.add(sumPanel);
		container.add(opePanel);
	}
	
	/*将数组传到inview的刷新方法里面再刷新*/
	public Vector<Production> getVector(){
		return v;
	}
	
	
	//下拉框监听
	static class MyItemListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			JComboBox cb=(JComboBox)e.getSource();
			String select=(String) cb.getSelectedItem();
			catalog=select;
		}
		
	}
	
	
	
	
	
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source==addBtn){
			//1.判断是否存在这个商品
			//2.如果存在就获取这条商品记录为一个对象
			//3.判断购物缓冲区是否有这个记录
				//3.1如果有update数量和price
				//3.2如果没有就insert这条记录，把sum更新
			//保存到数据库
			//关闭对话框
			//刷新table
			

			String prodId =catalog;
			System.out.println("proId="+prodId);
			System.out.println("vatalog="+catalog);
			
			if(sumTF.getText().equals("")) {
				JOptionPane.showMessageDialog(this,"请输入完整","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			int sum =  Integer.parseInt(sumTF.getText()) ;

			if(sum<0) {/*判断输入大于0*/
				JOptionPane.showMessageDialog(this,"请输入大于0的数量","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//TODO 参数校验
			/*/判断是已添加,未添加还是不存在*/
			productionImpl productionImpl = new productionImpl();
			production = new Production();
			production = productionImpl.findByIdProduction(prodId);


			if(production!=null) {/*商品库有这个商品存在*/
				int mark = 0;
				for(Production p:v) {
					
					if(p.getId().equals(prodId)){/*如果数组中存在相同商品就更新数量和价格*/
						sum=p.getSum()+sum;/*数量*/
						p.setSum(sum);
						p.setPrice(sum*p.getInPrice());/*进货价格*/
						mark = 1;
						break;
					}
					
				}
				if(mark==0) {/*插入新的*/
					System.out.println("缓存区不存在，插入新的数据");
					production.setSum(sum);/*更新价格和数量后插入心的*/
					production.setPrice(sum*production.getInPrice());
					v.add(production);

					}
				
				System.out.println("插入后v的大小"+v.size());
				this.dispose();
				JOptionPane.showMessageDialog(this,"添加成功","提示",JOptionPane.ERROR_MESSAGE);
		
			
		}
				

			
				else {/*商品库没有这个商品*/
					JOptionPane.showMessageDialog(this,"商品不存在","提示",JOptionPane.ERROR_MESSAGE);
				}
		}
		else if(source == cancelBtn) {
			
			this.dispose();
		}
	}



}
