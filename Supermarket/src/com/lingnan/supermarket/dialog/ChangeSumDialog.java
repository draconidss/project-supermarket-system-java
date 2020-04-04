package com.lingnan.supermarket.dialog;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

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


public class ChangeSumDialog extends JDialog implements ActionListener{
	
	
	private JPanel prodIdPanel,sumPanel,phonePanel,opePanel,titlePanel;
	
	private JLabel prodIdLabel,sumLabel,titleLabel;
	private JTextField prodIdTF,sumTF;
	
	private JButton UpdateBtn,cancelBtn;
	
	
	private OutTableModel outTableModel = new OutTableModel();
	
	private Buffer buffer;
	
	private String prodId,mark;/*mark用来标记是进货还是出货系统*/
	
	private Vector<Production> v;
	
	public ChangeSumDialog(JFrame parent,String prodId,String mark,Vector<Production> v) {
		super(parent,"更改商品数量");
		
		setSize(350,200);	
		setLocationRelativeTo(null);		
		setModal(true);
		setResizable(false);		 
		this.setLayout(new FlowLayout());
		
		this.prodId=prodId;
		this.mark=mark;
		this.v = v;
		initView();
	}
	
	public ChangeSumDialog(JFrame parent,String prodId,String mark) {
		super(parent,"更改商品数量");
		
		setSize(350,200);	
		setLocationRelativeTo(null);		
		setModal(true);
		setResizable(false);		 
		this.setLayout(new FlowLayout());
		
		this.prodId=prodId;
		this.mark=mark;
		initView();
	}
	
	
	
	
	
	private void initView() {

		titlePanel = new JPanel();
		titleLabel = new JLabel("修改商品id为"+prodId+"的数量");
		titlePanel.add(titleLabel);

		
		
		sumPanel = new JPanel();
		sumLabel = new JLabel("数量");
		sumTF = new JTextField(15);
		sumPanel.add(sumLabel);
		sumPanel.add(sumTF);
		
		
		opePanel = new JPanel();
		UpdateBtn = new JButton("更改");
		cancelBtn = new JButton("取消");
		UpdateBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		opePanel.add(UpdateBtn);
		opePanel.add(cancelBtn);
		
		Container container = getContentPane();
		container.add(titlePanel);
		container.add(sumPanel);
		container.add(opePanel);
	}	
	
	public Vector<Production> getVector(){
		return v;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source==UpdateBtn){
			
			//TODO 参数校验
			/*/返回这个记录的信息*/
			
			int sum = Integer.parseInt(sumTF.getText());/*获得数量*/
			System.out.println("所要修改的数量sum="+sum);
			
			if(sumTF.getText().equals("")) {
				JOptionPane.showMessageDialog(this,"请输入完整","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}

			if(sum<0) {/*判断输入大于0*/
				JOptionPane.showMessageDialog(this,"请输入大于0的数量","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			
			
			
			BufferImpl bufferImpl = new BufferImpl();
			
			productionImpl productionImpl = new productionImpl();
			Production production = new Production();
			production = productionImpl.findByIdProduction(prodId);
			
			Buffer buffer = new Buffer();
			boolean flag = false;
			
			if(mark=="In") {/*进货界面*/
				for(Production p:v) {
					if(p.getId().equals(prodId))
						p.setSum(sum);
				}
					
			}
			
			else if(mark=="Out") {/*出货界面*/
				buffer = bufferImpl.findInBufferbyId(prodId);
				if(buffer!=null) {/*记录有这条数据*/
					if(sum>production.getSum())/*修改数量超过库存*/
						JOptionPane.showMessageDialog(this,"库存数量为:"+production.getSum()+",修改数量请勿超过库存","提示",JOptionPane.ERROR_MESSAGE);
					else
						flag = bufferImpl.UpdateInBufferById(prodId, sum);
				
				}
	
			}
			

			if(flag = true) {/*如果修改成功*/
									
				JOptionPane.showMessageDialog(this,"修改成功","提示",JOptionPane.INFORMATION_MESSAGE);
				dispose();
				
			}else {
				JOptionPane.showMessageDialog(this,"修改失败","提示",JOptionPane.ERROR_MESSAGE);
				dispose();
			}
		}
		
		else if(source == cancelBtn) {
			
			this.dispose();
		}
	}
}
