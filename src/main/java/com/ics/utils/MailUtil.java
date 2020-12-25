package com.ics.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtil {
	/**
	 * 
	 * @Title: sendMail
	 * @Description: 实现发送邮件
	 * @param email：接收邮件的地址
	 * @param emailMsg：发送邮件的内容
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public static void sendMail(String email, String emailMsg, String aaa) throws AddressException, MessagingException {
		// 1.创建一个程序与邮件服务器会话对象 Session

		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "SMTP");

		// 163邮箱设置
//		 props.setProperty("mail.host", "smtp.163.com");

		// qq邮箱设置
		props.setProperty("mail.host", "smtp.qq.com");

		props.setProperty("mail.smtp.auth", "true");// 指定验证为true

		// 创建验证器
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				// 自己的邮箱账号和密码（发送者）{qq邮箱开启登录授权码时会给你一个授权码此授权码就是你登 入邮箱的密码}

//				return new PasswordAuthentication("jsl**", "bfferznbnprdbbii");// 授权码
//				return new PasswordAuthentication("1144250601@qq.com", "nzlpcmuetcmijeec");// 授权码
//				return new PasswordAuthentication("18250803949@163.com", "jjz5571751");// 授权码
				return new PasswordAuthentication("631473520@qq.com", "bfferznbnprdbbii");// 授权码
//				return new PasswordAuthentication("kun.zhang@docomo-china.com.cn", "Password123");// 授权码
			}
		};

		Session session = Session.getInstance(props, auth);

		// 2.创建一个Message，它相当于是邮件内容
		Message message = new MimeMessage(session);

//		message.setFrom(new InternetAddress("kun.zhang@docomo-china.com.cn")); // 设置发送者（自己的邮箱账号）
		message.setFrom(new InternetAddress("631473520@qq.com")); // 设置发送者（自己的邮箱账号）
//		message.setFrom(new InternetAddress("1144250601@qq.com")); // 设置发送者（自己的邮箱账号）
//		message.setFrom(new InternetAddress("18250803949@163.com")); // 设置发送者（自己的邮箱账号）

		String[] emailArr = email.split(";");
		for(int i=0;i<emailArr.length;i++) {
			if(i==0) {
				message.setRecipient(RecipientType.TO, new InternetAddress(emailArr[i])); // 设置发送方式与接收者
			}else {
				message.setRecipient(RecipientType.CC, new InternetAddress(emailArr[i]));
			}
		}


		
		message.setSubject(aaa);
		// message.setText("这是一封激活邮件，请<a href='#'>点击</a>");

		message.setContent(emailMsg, "text/html;charset=utf-8");

		// 3.创建 Transport用于将邮件发送

		Transport.send(message);
	}
	
	public static void main(String[] args) {
		try {
//			sendMail("18250803949@163.com", "8989", "11111");
//			MailUtil.sendMail("1144250601@qq.com", "8989", "测试66");
			MailUtil.sendMail("18250803949@163.com", "4777", "测试36");
//			sendMail("631473520@qq.com", "555566", "报警通知");
			System.out.println("发送成功");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
