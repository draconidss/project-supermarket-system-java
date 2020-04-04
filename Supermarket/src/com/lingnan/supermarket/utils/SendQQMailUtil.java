package com.lingnan.supermarket.utils;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.lingnan.supermarket.dto.*;

public class SendQQMailUtil {
    
    private String FromEamil;
    private String Stmt;
    private String ToEmail;
    private String Title;
    private String txtFile;

    
    
    public  SendQQMailUtil  (String setFromEamil,String Stmt,String ToEmail,String Title,String txtFile) throws AddressException, MessagingException{
    	this.FromEamil=setFromEamil;
    	this.Stmt=Stmt;
    	this.ToEmail=ToEmail;
    	this.Title=Title;
    	this.txtFile=txtFile;
    	
    	System.out.println("Stmt="+Stmt);

        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");// 连接协议
        properties.put("mail.smtp.host", "smtp.qq.com");// 主机名
        properties.put("mail.smtp.port", 465);// 端口号
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接 ---一般都使用
        properties.put("mail.debug", "false");// 设置是否显示debug信息 true 会在控制台显示相关信息
        // 得到回话对象
        Session session = Session.getInstance(properties);
        // 获取邮件对象
        Message message = new MimeMessage(session);
        // 设置发件人邮箱地址
        message.setFrom(new InternetAddress(FromEamil));
        // 设置收件人邮箱地址 
        message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(ToEmail)});
        //message.setRecipient(Message.RecipientType.TO, new InternetAddress("xxx@qq.com"));//一个收件人
        // 设置邮件标题
        message.setSubject(Title);
        // 设置邮件内容
        message.setText(txtFile);
        // 得到邮差对象
        Transport transport = session.getTransport();
        // 连接自己的邮箱账户
        transport.connect(FromEamil, Stmt);// 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
        // 发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}