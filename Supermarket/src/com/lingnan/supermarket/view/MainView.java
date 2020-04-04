package com.lingnan.supermarket.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.Timer;

import com.lingnan.supermarket.componet.BGPanel;
import com.lingnan.supermarket.dao.impl.BufferImpl;
import com.lingnan.supermarket.dao.impl.inOrderServiceImpl;
import com.lingnan.supermarket.dialog.CloseDialog;
import com.lingnan.supermarket.dialog.InDialog;
import com.lingnan.supermarket.dialog.UserDialog;
import com.lingnan.supermarket.dialog.UserInfDialog;
import com.lingnan.supermarket.dto.InOrder;
import com.lingnan.supermarket.dto.Production;
import com.lingnan.supermarket.dto.User;
import com.lingnan.supermarket.utils.DateUtil;
import com.lingnan.supermarket.utils.FontUtil;
import com.lingnan.supermarket.view.base.BaseView;

import java.awt.*;

public class MainView extends BaseView implements ActionListener, MouseListener,WindowListener{
	
	
	JMenuBar menuBar;
	JMenu settingMenu,helpMenu;
	
	JMenuItem skinMenuItem,configMenuItem;
	 
	
	JSplitPane containerPanel;
	
	CardLayout rightPanelLayout;
	JPanel leftPanel,rightPanel;
	
	/*菜单栏*/
	JLabel logoLabel,userMenuLabel1,homeMenuLabel,userMenuLabel,inMenuLabel,
	outMenuLabel,storageMenuLabel,supplierMenuLabel,catalogMenuLabel;
	
	static JLabel  remindMenuLabel;/*全局调用刷新*/
	
	JPanel bottomPanel;
	
	JLabel timeLabel;
	
	JPanel purposePanel,timePanel;
	JLabel purposeLabel;
	
	
	JButton saveBtn,unSaveBtn,cancleBtn;/*退出时按钮*/
	
	//
	Timer timer;
	
	private  User user ;/*从登录界面传过来的用户信息*/
	
	private BufferImpl bufferImpl;
	
	private Image bgImage ;
	private String iconSkin;
	private int skin;
	
	private Vector<Production> vP=new Vector<Production>() ;/*用于进货缓存*/

	
	private int location;

	private  int sSuper=-1;//界面权限
	
	private static inOrderServiceImpl inOrderImpl = new inOrderServiceImpl();
	private static int unConfirmmark;/*未确认订单*/
	
	public MainView(User user,int skin,String iconSkin) {
		super(1300,850,"新民超市管理系统欢迎您",user,skin);
		timer = new Timer(1000,this);
		timer.start();
		this.user = user;
		
		this.sSuper=user.getUsuper();//界面权限
		System.out.println("userid="+user.getId());
		this.addWindowListener(this);
		
		this.skin = skin;
		this.iconSkin = iconSkin;
		ImageIcon icon=new ImageIcon(iconSkin);  //xxx代表图片存放路径，2.png图片名称及格式
		this.setIconImage(icon.getImage());
		
		//获得未进货的信息
		Vector<InOrder> vInOrder;
		vInOrder = inOrderImpl.findUnconfirmInOrder();
		unConfirmmark=vInOrder.size();

	
		initView(user,skin);
	}

	public MainView(User user) {
		super(1300,850,"新民超市管理系统欢迎您");
		timer = new Timer(1000,this);
		timer.start();
		this.user = user;
		
		this.sSuper=user.getUsuper();//界面权限
		System.out.println("userid="+user.getId());
		this.addWindowListener(this);
		

		
		//获得未进货的信息
		Vector<InOrder> vInOrder;
		vInOrder = inOrderImpl.findUnconfirmInOrder();
		unConfirmmark=vInOrder.size();

	
		initView(user,skin);
	}
	
	public static void refreshRemind() {
		Vector<InOrder> vInOrder;
		vInOrder = inOrderImpl.findUnconfirmInOrder();
		unConfirmmark=vInOrder.size();
		remindMenuLabel.setText("待确认进货:"+unConfirmmark);
	}
	

	
	
	
/*	public static User getUserInf() {
		return user;
	}*/

	@Override
	protected void initView(User user,int skin) {
		
		
		
		
		/*菜单栏*/
		menuBar = new JMenuBar();
		
		settingMenu = new JMenu("设置");
		
		helpMenu = new JMenu("帮助");
		
		skinMenuItem = new JMenuItem("随机切换皮肤",new ImageIcon("static\\icon\\skin.png"));

/*		for(int i = 3;i<9;i++) {
			
		}*/
		configMenuItem = new JMenuItem("参数设置",new ImageIcon("static\\icon\\setting.png"));
		skinMenuItem.addActionListener(this);
		
		settingMenu.add(configMenuItem);
		settingMenu.add(skinMenuItem);
		menuBar.add(settingMenu);
		menuBar.add(helpMenu);
		
		setJMenuBar(menuBar);
		 

		/*左边菜单栏设置*/
		
		
		try {
			bgImage = ImageIO.read(new File("static\\bg\\bg"+skin+".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		leftPanel = new BGPanel(bgImage);/*皮肤*/
		leftPanel.setLayout(null);

		
		/*菜单栏:用户登录信息*/
		System.out.println("用户头像地址=="+user.getImg());
		JLabel logoLabel = new JLabel(new ImageIcon(user.getImg()),JLabel.LEFT);
		System.out.println(user.getImg());
		leftPanel.add(logoLabel);
		logoLabel.setBounds(25, 30,150,150);
		
		/*账号名字*/
		String x = UsuperIcon(user.getUsuper());/*判断权限返回icon地址*/
		System.out.println("身份地址："+x);
		userMenuLabel1 = new JLabel("|"+user.getUsername()+"|"+user.getRname(),new ImageIcon(x),JLabel.LEFT);
		userMenuLabel1.setFont(FontUtil.userFont);
		userMenuLabel1.addMouseListener(this);
		userMenuLabel1.setBounds(20, 170,180,32);
		userMenuLabel1.setForeground(Color.white);
		leftPanel.add(userMenuLabel1);/*添加用户选项到菜单栏*/
		
		
		
		/*菜单栏:首页*/
		homeMenuLabel = new JLabel("新民首页",new ImageIcon("static\\icon\\home1.png"),JLabel.LEFT);
		homeMenuLabel.setFont(FontUtil.menuFont);
		homeMenuLabel.addMouseListener(this);
		homeMenuLabel.setBounds(20, 250,150,32);
		homeMenuLabel.setForeground(Color.white);
		leftPanel.add(homeMenuLabel);/*添加用户选项到菜单栏*/
		
		
		
		/*菜单栏:人员管理*/
		userMenuLabel = new JLabel("人员管理",new ImageIcon("static\\icon\\user1.png"),JLabel.LEFT);
		userMenuLabel.setFont(FontUtil.menuFont);
		userMenuLabel.addMouseListener(this);
		userMenuLabel.setBounds(20, 300,150,32);
		userMenuLabel.setForeground(Color.white);
		leftPanel.add(userMenuLabel);/*添加用户选项到菜单栏*/
		
		/*菜单栏:进货系统*/
		inMenuLabel = new JLabel("进货系统",new ImageIcon("static\\icon\\in1.png") ,JLabel.LEFT);
		inMenuLabel.setFont(FontUtil.menuFont);
		inMenuLabel.addMouseListener(this);
		inMenuLabel.setBounds(20, 350,150,32);
		inMenuLabel.setForeground(Color.white);
		leftPanel.add(inMenuLabel);
		
		/*菜单栏:收银系统*/
		outMenuLabel = new JLabel("收银系统",new ImageIcon("static\\icon\\out1.png") ,JLabel.LEFT);
		outMenuLabel.setFont(FontUtil.menuFont);
		outMenuLabel.addMouseListener(this);
		outMenuLabel.setBounds(20, 400,150,32);
		outMenuLabel.setForeground(Color.white);
		leftPanel.add(outMenuLabel);
		
		/*菜单栏:库存*/
		storageMenuLabel = new JLabel("商品库存",new ImageIcon("static\\icon\\storage1.png") ,JLabel.LEFT);
		storageMenuLabel.setFont(FontUtil.menuFont);
		storageMenuLabel.addMouseListener(this);
		storageMenuLabel.setBounds(20, 450,150,32);
		storageMenuLabel.setForeground(Color.white);
		leftPanel.add(storageMenuLabel);
		
		/*菜单栏:供应商*/
		supplierMenuLabel = new JLabel("供应商",new ImageIcon("static\\icon\\supplier1.png") ,JLabel.LEFT);
		supplierMenuLabel.setFont(FontUtil.menuFont);
		supplierMenuLabel.addMouseListener(this);
		supplierMenuLabel.setBounds(20, 500,150,32);
		supplierMenuLabel.setForeground(Color.white);
		leftPanel.add(supplierMenuLabel);
		
		/*菜单栏:商品目录*/
		catalogMenuLabel = new JLabel("商品目录",new ImageIcon("static\\icon\\catalog1.png") ,JLabel.LEFT);
		catalogMenuLabel.setFont(FontUtil.menuFont);
		catalogMenuLabel.addMouseListener(this);
		catalogMenuLabel.setBounds(20,550,150,32);
		catalogMenuLabel.setForeground(Color.white);
		leftPanel.add(catalogMenuLabel);
		
		/*提醒进货确认模块*/
		remindMenuLabel = new JLabel("待确认进货:"+unConfirmmark,new ImageIcon("static\\icon\\remind1.png") ,JLabel.LEFT);
		remindMenuLabel.setFont(FontUtil.remindFont);
		remindMenuLabel.addMouseListener(this);
		remindMenuLabel.setBounds(0,650,200,32);
		remindMenuLabel.setForeground(Color.white);
		leftPanel.add(remindMenuLabel);
		
		
		rightPanelLayout = new CardLayout();
		
		//0.超市首页展示
		JPanel homePanel = new HomeView(this);
		
		//1.用户管理界面:用户的列表
		JPanel userPanel = new UserView(this);
		
		//2.进货系统界面
		JPanel inPanel = new InView(this,user,vP,1);
		
		//3收银系统界面
		JPanel outPanel = new OutView(this,user);
		
		//4.库存系统界面
		JPanel storagePanel = new StorageView(this);
		
		//5.供应商界面
		JPanel supplierPanel = new SupplierView(this);
		
		//6商品目录界面
		JPanel ProdCatalogPanel = new ProdCatalogView(this);

		//7商品目录界面
		JPanel superPanel = new SuperView(this);
		
		//8进货信息提示
		JPanel   inPanel2 = new InView(this,user,vP,0);
		
		/*添加界面并给索引*/
		rightPanel = new JPanel(rightPanelLayout);
		rightPanel.add(homePanel, "0");
		rightPanel.add(userPanel, "1");
		rightPanel.add(inPanel, "2");
		rightPanel.add(outPanel, "3");
		rightPanel.add(storagePanel, "4");
		rightPanel.add(supplierPanel, "5");
		rightPanel.add(ProdCatalogPanel, "6");
		rightPanel.add(superPanel, "7");
		rightPanel.add(inPanel2, "8");
		 
		
		containerPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftPanel,rightPanel);
		containerPanel.setDividerLocation(180);
		containerPanel.setDividerSize(0);
		
		bottomPanel = new JPanel();//默认的布局是流式布局
		 
		bottomPanel.setBackground(Color.WHITE);
		bottomPanel.setLayout(new BorderLayout());
		
		purposePanel = new JPanel();
		purposePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		purposePanel.setBackground(Color.WHITE);
		purposeLabel = new JLabel("当前位置是:超市首页");
		purposePanel.add(purposeLabel);
		
		timePanel=new JPanel();
		timePanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		timePanel.setBackground(Color.WHITE);
		timeLabel = new JLabel(DateUtil.dateToString(new Date(),null));
		timePanel.add(timeLabel);
		
		bottomPanel.add(purposePanel,"West");
		bottomPanel.add(timePanel,"East");
		Container container = getContentPane();
		container.add(containerPanel,"Center");
		container.add(bottomPanel,"South");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		timeLabel.setText(DateUtil.dateToString(new Date(),null));
		if(source==skinMenuItem)/*换肤*/{
			System.out.println("切换皮肤");
			Random random=new Random();
			skin=random.nextInt(10);
			this.dispose();
			new MainView(user,skin,iconSkin);
		}
		
	}

	@Override/*左侧菜单栏点击事件*/
	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		if(source==homeMenuLabel) {
			rightPanelLayout.show(rightPanel,"0");
			location=0;
		}else if(source==userMenuLabel) {
			if(sSuper==0)
			rightPanelLayout.show(rightPanel,"1");
			else{
				rightPanelLayout.show(rightPanel,"7");
			}
			location=1;
		}
		else if(source==inMenuLabel) {
			if(sSuper==2)
				rightPanelLayout.show(rightPanel,"7");
			else{
				rightPanelLayout.show(rightPanel,"2");
				
			}
			location=2;
		}
		else if(source==outMenuLabel) {
			rightPanelLayout.show(rightPanel,"3");
			location=3;
		}
		else if(source==storageMenuLabel) {
			rightPanelLayout.show(rightPanel,"4");
			location=4;
		}
		else if(source==supplierMenuLabel) {
			rightPanelLayout.show(rightPanel,"5");

			location=5;
			
		}
		else if(source==catalogMenuLabel) {	
				rightPanelLayout.show(rightPanel,"6");
				location=6;

		}else if(source==remindMenuLabel) {
			if(sSuper==2)
				rightPanelLayout.show(rightPanel,"7");
			else{
				rightPanelLayout.show(rightPanel,"8");
			}
			location=7;
		}else if(source==userMenuLabel1){
			
			UserInfDialog userInfDialog = new UserInfDialog(this,user);
			userInfDialog.setVisible(true);
			location=8;
		}
		
		refreshRemove();
	}
	//获取当前位置
		public void refreshRemove(){

			purposePanel.removeAll();
			if(location==0){
				purposeLabel = new JLabel("当前位置是:"+homeMenuLabel.getText());
			
			}else if(location==1){
				purposeLabel = new JLabel("当前位置是:"+userMenuLabel.getText());
				
			}else if(location==2){
				purposeLabel = new JLabel("当前位置是:"+inMenuLabel.getText());
			
			}else if(location==3){
				purposeLabel = new JLabel("当前位置是:"+outMenuLabel.getText());
				
			}else if(location==4){
				purposeLabel = new JLabel("当前位置是:"+storageMenuLabel.getText());
				
			}else if(location==5){
				purposeLabel = new JLabel("当前位置是:"+supplierMenuLabel.getText());
			
			}else{
				purposeLabel = new JLabel("当前位置是:"+catalogMenuLabel.getText());
			}
			
			purposePanel.add(purposeLabel);
			
		}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override/*鼠标焦点时*/
	public void mouseEntered(MouseEvent e) {
		Object source = e.getSource();
		if(source==homeMenuLabel) {
			homeMenuLabel.setForeground(new Color(18, 150, 219));
			homeMenuLabel.setIcon(new ImageIcon("static\\icon\\home2.png"));
		}
		if(source==userMenuLabel) {
			userMenuLabel.setForeground(new Color(18, 150, 219));
			userMenuLabel.setIcon(new ImageIcon("static\\icon\\user2.png"));
		}else if(source==inMenuLabel) {
			inMenuLabel.setForeground(new Color(18, 150, 219));
			inMenuLabel.setIcon(new ImageIcon("static\\icon\\in2.png"));
		}else if(source==outMenuLabel) {
			outMenuLabel.setForeground(new Color(18, 150, 219));
			outMenuLabel.setIcon(new ImageIcon("static\\icon\\out2.png"));
		}else if(source==storageMenuLabel) {
			storageMenuLabel.setForeground(new Color(18, 150, 219));
			storageMenuLabel.setIcon(new ImageIcon("static\\icon\\storage2.png"));
		}else if(source==supplierMenuLabel) {
			supplierMenuLabel.setForeground(new Color(18, 150, 219));
			supplierMenuLabel.setIcon(new ImageIcon("static\\icon\\supplier2.png"));
		}else if(source==catalogMenuLabel) {
			catalogMenuLabel.setForeground(new Color(18, 150, 219));
			catalogMenuLabel.setIcon(new ImageIcon("static\\icon\\catalog2.png"));
	}
		else if(source==userMenuLabel1) {
			userMenuLabel1.setForeground(new Color(18, 150, 219));
			
	}
}

	@Override
	public void mouseExited(MouseEvent e) {
		Object source = e.getSource(); 
		if(source==homeMenuLabel) {
			homeMenuLabel.setForeground(Color.white);
			homeMenuLabel.setIcon(new ImageIcon("static\\icon\\home1.png"));
		}
		else if(source==userMenuLabel) {
			userMenuLabel.setForeground(Color.white);
			userMenuLabel.setIcon(new ImageIcon("static\\icon\\user1.png"));
		}else if(source==inMenuLabel) {
			inMenuLabel.setForeground(Color.white);
			inMenuLabel.setIcon(new ImageIcon("static\\icon\\in1.png"));
		}else if(source==outMenuLabel) {
			outMenuLabel.setForeground(Color.white);
			outMenuLabel.setIcon(new ImageIcon("static\\icon\\out1.png"));
		}else if(source==storageMenuLabel) {
			storageMenuLabel.setForeground(Color.white);
			storageMenuLabel.setIcon(new ImageIcon("static\\icon\\storage1.png"));
		}else if(source==supplierMenuLabel) {
			supplierMenuLabel.setForeground(Color.white);
			supplierMenuLabel.setIcon(new ImageIcon("static\\icon\\supplier1.png"));
		}else if(source==catalogMenuLabel) {
			catalogMenuLabel.setForeground(Color.white);
			catalogMenuLabel.setIcon(new ImageIcon("static\\icon\\catalog1.png"));
	}
		else  {
			userMenuLabel1.setForeground(Color.white);
		
	}
		
	}


	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();  
		if(source==this) {/*关闭窗口时检查进货系统和出货系统是否还有记录*/
			this.vP =InView.getVector();
			System.out.println("v的size="+vP.size());
			bufferImpl = new BufferImpl();
			if(vP.size()!=0||bufferImpl.allOutBuffer().size()!=0) {/*如果购物车还有记录*/
				CloseDialog closeDialog = new CloseDialog(this,vP);
				closeDialog.setVisible(true);
			}else
				System.exit(0);
			

		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}







}
