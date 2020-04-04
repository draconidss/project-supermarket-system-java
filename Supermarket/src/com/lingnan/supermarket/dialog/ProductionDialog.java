package com.lingnan.supermarket.dialog;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.lingnan.supermarket.*;
import com.lingnan.supermarket.dao.SupplierInfService;
import com.lingnan.supermarket.dao.productionService;
import com.lingnan.supermarket.dao.impl.SupplierInfImpl;
import com.lingnan.supermarket.dao.impl.prodCatalogImpl;
import com.lingnan.supermarket.dao.impl.productionImpl;
import com.lingnan.supermarket.dto.ProdCatalog;
import com.lingnan.supermarket.dto.Production;
import com.lingnan.supermarket.dto.SupplierInf;
import com.lingnan.supermarket.table.ProdCatalogTM;
import com.lingnan.supermarket.view.StorageView;
import com.lingnan.supermarket.view.SupplierView;
import com.lingnan.supermarket.view.ProdCatalogView.MyItemListener;

public class ProductionDialog extends JDialog implements ActionListener {

	private JPanel namePanel, addressPanel, contactPanel,
	opePanel,idPanel,inpricePanel,outpricePanel,lifePanel,
	sumPanel,supplyidPanel,id2Panel,name2Panel;

	private JLabel nameLabel, addressLabel, contactLabel,
	idLabel,inpriceLabel,outpriceLabel,lifeLabel,sumLabel,
	supplyidLabel,id2Label,name2Label;
	private JTextField nameTF, addressTF, contactTF,
	idTF,inpriceTF,outpriceTF,lifeTF,sumTF,
	supplyidTF,id2TF,name2TF;

	private JButton saveBtn, cancelBtn;

	private productionService productionService = new productionImpl();
	
	//下拉类别
	private String log[]=null;
	private ArrayList<String>alog=null;
	private ProdCatalogTM prodCatalogTM;
	private ProdCatalog pc;
	private prodCatalogImpl pci;
	private JComboBox<String> combo;
	private String id2;
	private String name2;
	
	//下拉供应商类别
	private String superlier[]=null;
	private ArrayList<String>asuperlier=null;
	private SupplierInf si;
	private SupplierInfImpl sii;
	private JComboBox<String> combo1;
	private int supplyid;

	private StorageView storageView;

	private Production production;

	public ProductionDialog(JFrame parent) {
		super(parent, "添加");
		

		setSize(350, 500);

		setLocationRelativeTo(null);

		setModal(true);
		setResizable(false);

		this.setLayout(new FlowLayout());

		initView();
	}

	private void initView() {
		idPanel = new JPanel();
		idLabel = new JLabel("商品编号");
		idTF = new JTextField(15);
		idPanel.add(idLabel);
		idPanel.add(idTF);

		
		namePanel = new JPanel();
		nameLabel = new JLabel("名称");
		nameTF = new JTextField(15);
		namePanel.add(nameLabel);
		namePanel.add(nameTF);
		
		
		inpricePanel = new JPanel();
		inpriceLabel = new JLabel("进货单价");
		inpriceTF = new JTextField(15);
		inpricePanel.add(inpriceLabel);
		inpricePanel.add(inpriceTF);
		
		outpricePanel = new JPanel();
		outpriceLabel = new JLabel("购买单价");
		outpriceTF = new JTextField(15);
		outpricePanel.add(outpriceLabel);
		outpricePanel.add(outpriceTF);
		
		
		lifePanel = new JPanel();
		lifeLabel = new JLabel("保质期(月份数)");
		lifeTF = new JTextField(15);
		lifePanel.add(lifeLabel);
		lifePanel.add(lifeTF);
		
		
		sumPanel = new JPanel();
		sumLabel = new JLabel("库存");
		sumTF = new JTextField(15);
		sumPanel.add(sumLabel);
		sumPanel.add(sumTF);
		
		//供应商名下拉框 传递supplyid
		supplyidPanel = new JPanel();
		supplyidLabel = new JLabel("供应商");
//		supplyidTF = new JTextField(15);
		
		sii=new SupplierInfImpl();
		this.asuperlier=sii.findNameSupplier();
		this.superlier=new String[asuperlier.size()];
		for(int i=0;i<asuperlier.size();i++)
			superlier[i]=asuperlier.get(i);
		
		for(int i=0;i<superlier.length;i++)
		{
		System.out.println(superlier[i]);
		}
		
		combo1 = new JComboBox<String>(superlier);
		combo1.addItemListener(new MyItemListener1());
		
		supplyidPanel.add(supplyidLabel);
		supplyidPanel.add(combo1);
		
		
		
		
		
	/*	id2Panel = new JPanel();
		id2Label = new JLabel("分类id");
		id2TF = new JTextField(id2,15);
		id2Panel.add(id2Label);
		id2Panel.add(id2TF);*/
		//类名下拉框
		name2Panel = new JPanel();
		name2Label = new JLabel("类名");
		pci=new prodCatalogImpl();
		this.alog=pci.findNameProdCatalog();
		this.log=new String[alog.size()];
		for(int i=0;i<alog.size();i++)
		log[i]=alog.get(i);
	
		
		combo = new JComboBox<String>(log);
		combo.addItemListener(new MyItemListener());
		name2Panel.add(name2Label);
		name2Panel.add(combo);

		
		addressPanel = new JPanel();
		addressLabel = new JLabel("地址");
		addressTF = new JTextField(15);
		addressPanel.add(addressLabel);
		addressPanel.add(addressTF);

		contactPanel = new JPanel();
		contactLabel = new JLabel("电话");
		contactTF = new JTextField(15);
		contactPanel.add(contactLabel);
		contactPanel.add(contactTF);

		opePanel = new JPanel();
		saveBtn = new JButton("保存");
		cancelBtn = new JButton("取消");
		saveBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		opePanel.add(saveBtn);
		opePanel.add(cancelBtn);

		Container container = getContentPane();
		container.add(idPanel);
		container.add(namePanel);
		container.add(inpricePanel);
		container.add(outpricePanel);
		container.add(lifePanel);
		container.add(sumPanel);
		container.add(supplyidPanel);
//		container.add(id2Panel);
		container.add(name2Panel);
		container.add(opePanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == saveBtn) {
			// 思路获取数据
			// 保存到数据库
			// 关闭对话框
			// 刷新table

			String name = nameTF.getText();
			String id = idTF.getText();
			float inprice = Float.parseFloat((inpriceTF.getText()));
			float outprice = Float.parseFloat(outpriceTF.getText());
			int life = Integer.parseInt(lifeTF.getText());
			int sum = Integer.parseInt(sumTF.getText());
			
			

			// TODO 参数校验
			if (this.production == null) {
				if(supplyid==-1){
					JOptionPane.showMessageDialog(this, "商品检索出错", "提示",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(supplyid==0){
					JOptionPane.showMessageDialog(this, "请选择商品名", "提示",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(id2.equals("0")){
					JOptionPane.showMessageDialog(this, "请选择商品类", "提示",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Production production = new Production();
				production.setId(id);
				production.setName(name);
				production.setInPrice(inprice);
				production.setOutPrice(outprice);
				production.setLife(life);
				production.setSum(sum);
				production.setSupplyId(supplyid);
				production.setId2(id2);
				production.setName2(name2);				
				int result = productionService.addProduction(production);
				// int result = 1;
				if (result == 1) {

					JOptionPane.showMessageDialog(this, "添加成功", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					this.dispose();
				} else if(result == 2){
					JOptionPane.showMessageDialog(this, "已存在该商品", "提示",
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(this, "出错！添加失败", "提示",
							JOptionPane.ERROR_MESSAGE);
				}
			}/*else{
				//更新
				SupplierInf supplierInf= new SupplierInf();
				supplierInf.setName(name);
				supplierInf.setAddress(address);
				supplierInf.setContact(contact);
				supplierInf.setId(this.supplierInf.getId());
				
				int result = supplierInfService.updateSupplierInf(supplierInf);
				if(result==1){
					JOptionPane.showMessageDialog(this, "更新成功", "提示",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}*/

		} else if (source == cancelBtn) {

			this.dispose();
		}
	}
	public class MyItemListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			JComboBox cb = (JComboBox) e.getSource();
			name2 = (String) cb.getSelectedItem();
			pci =new prodCatalogImpl();
			for(int i=0;i<log.length;i++){
				if(name2.equals(log[i]))
				id2=pci.findProdCatalogByname(name2);
			}
		
		}

	}
	
	public class MyItemListener1 implements ItemListener {
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			JComboBox cb = (JComboBox) e.getSource();
			String suppliername = (String) cb.getSelectedItem();
			sii =new SupplierInfImpl();
			for(int i=0;i<superlier.length;i++){
				if(suppliername.equals(superlier[i]))
					supplyid=sii.findIdSupplierByName(suppliername);
			}
		}
		
	}
}
