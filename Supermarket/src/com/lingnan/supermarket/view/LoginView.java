package com.lingnan.supermarket.view;

import java.awt.Container;
import java.awt.Event;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.lingnan.supermarket.dao.UserService;
import com.lingnan.supermarket.dao.impl.UserServiceImpl;
import com.lingnan.supermarket.dto.Production;
import com.lingnan.supermarket.dto.User;
import com.lingnan.supermarket.componet.BGPanel;
import com.lingnan.supermarket.view.base.BaseView;

public class  LoginView extends BaseView implements ActionListener{
	
    //setLayout(null);
    //setBounds(x,y,width,height)
	private JPanel containerPanel,namePanel,passwordPanel;
    
    private JLabel nameLabel,pwdLabel;
    private JTextField nameTF;
    private JPasswordField pwdTF;
    
    private JButton loginBtn;
    
    private User user=null;
    
	
    /*创建窗口*/
    public LoginView() {
		super(350, 250, "新民超市");
		ImageIcon icon=new ImageIcon("static\\icon\\新.png");  //xxx代表图片存放路径，2.png图片名称及格式
		this.setIconImage(icon.getImage());
		
	}

    /*添加组件*/
	@Override/*界面*/
	protected void initView() {

		Image bgImage = null;
		try {
			bgImage = ImageIO.read(new File("static\\bg\\bg1.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        containerPanel = new BGPanel(bgImage);
		
		
		
		//用户名
        namePanel = new JPanel();
		nameLabel = new JLabel(new ImageIcon("static\\icon\\loginName.png"));
		nameTF = new JTextField("z001",22);
		namePanel.add(nameLabel);
		namePanel.add(nameTF);

		
		//密码
		passwordPanel = new JPanel();
		pwdLabel = new JLabel(new ImageIcon("static\\icon\\pwd.png"));
		pwdTF = new JPasswordField("0.00.0",22);
		passwordPanel.add(pwdLabel);
		passwordPanel.add(pwdTF);
		
		//登录
		loginBtn = new JButton("登录");
		loginBtn.addActionListener(this);
		



		
		/*添加组件*/
		containerPanel.add(namePanel);
		containerPanel.add(passwordPanel);
		containerPanel.add(loginBtn);

		

		
		Container container = getContentPane();
		container.add(containerPanel);
	}
	
	/*事件处理*/
	@Override
	public void actionPerformed(ActionEvent e) {
		/*如果点击登录*/
		if(e.getSource()==loginBtn){
		String loginName = nameTF.getText();
		String password = new String(pwdTF.getPassword());
		System.out.println("点击登录后");
		System.out.println("用户名为"+loginName);
		System.out.println("密码为"+password);
		//TODO 参数校验
		UserServiceImpl userService = new UserServiceImpl();
		user = userService.login(loginName, password);
		
		if(user==null) {
			JOptionPane.showMessageDialog(this,"账号或密码错误");
		}else {
			//去到主界面
			Random random=new Random();
			int skin=random.nextInt(10);
			System.out.println("skin="+skin);
			String iconSkin = "static\\icon\\新.png";/*默认icon*/
			new MainView(user,skin,iconSkin);
			this.dispose();
		}
		}
		
	}
	


	public static void main(String[] args) {
		Nimbus.Nimbus();
		LoginView loginView = new LoginView();
		}

	@Override
	protected void initView(User user,int skin) {
		// TODO Auto-generated method stub
		
	}








}


	
