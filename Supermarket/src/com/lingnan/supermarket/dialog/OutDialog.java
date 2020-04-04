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
import com.lingnan.supermarket.view.OutView;


public class OutDialog extends JDialog implements ActionListener{
	
	
	private JPanel prodIdPanel,sumPanel,phonePanel,opePanel;
	
	private JLabel prodIdLabel,sumLabel;
	private JTextField prodIdTF,sumTF;
	
	private JButton addBtn,cancelBtn;
	
	
	private OutTableModel outTableModel = new OutTableModel();
	
	private Buffer buffer;
	
	public OutDialog(JFrame parent) {
		super(parent,"添加商品");
		
		setSize(350,300);	
		setLocationRelativeTo(null);		
		setModal(true);
		setResizable(false);		 
		this.setLayout(new FlowLayout());
		
		initView();
	}
	
	
	
	
	
	private void initView() {
		prodIdPanel = new JPanel();
		prodIdLabel = new JLabel("编号");
		prodIdTF = new JTextField(15);
		prodIdPanel.add(prodIdLabel);
		prodIdPanel.add(prodIdTF);
		
		
		sumPanel = new JPanel();
		sumLabel = new JLabel("数量");
		sumTF = new JTextField(15);
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
			
			String prodId =prodIdTF.getText();
			System.out.println("proId="+prodId);
			if(prodIdTF.getText().equals("")||sumTF.getText().equals("")) {
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
			Production production = new Production();
			production = productionImpl.findByIdProduction(prodId);


			if(production!=null) {/*商品库有这个商品存在*/
				
				buffer =  new Buffer();
				BufferImpl BufferImpl = new BufferImpl();			
				buffer = BufferImpl.findOutBufferbyId(prodId);/*判断购物车是否有这个商品了,获得已添加的sum和价格*/
				
				int allSum = production.getSum();
				
				if(sum<0) {
					JOptionPane.showMessageDialog(this,"请输入大于0的数量","提示",JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(buffer!=null)/*购物缓冲区已经有添加的商品*/{
					int exeistSum = buffer.getSum();
					if(sum+exeistSum>allSum)/*库存不够*/{
						JOptionPane.showMessageDialog(this,"库存数量不够，库存数:"+allSum,"提示",JOptionPane.ERROR_MESSAGE);
						return;
					}else
						BufferImpl.addOutBufferExeistProd(prodId, sum, buffer);
					
				}
				else if(buffer==null){/*添加新的商品*/
					if(sum>allSum)/*库存不够*/{
						JOptionPane.showMessageDialog(this,"库存数量不够，库存数:"+allSum,"提示",JOptionPane.ERROR_MESSAGE);
						return;
					}else
					BufferImpl.addOutBufferNewProd(prodId, sum);
				}

				this.dispose();
				JOptionPane.showMessageDialog(this,"添加成功","提示",JOptionPane.ERROR_MESSAGE);

			}
			
			else {
				JOptionPane.showMessageDialog(this,"商品不存在","提示",JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(source == cancelBtn) {
			
			this.dispose();
		}
	}
}
