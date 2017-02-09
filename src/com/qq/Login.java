package com.qq;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Login {
	private User user;
	private Properties loginProperties;
	private String id;
	private String pwd;
	
	
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Login(User user) {
		super();
		this.user = user;
	}

	
	
	// 验证
	public boolean verify() {
		boolean b = false;

		// 1.读取账号密码文件
		loginProperties = new Properties();

		try {
			FileInputStream fis = new FileInputStream("e:\\login.properties");
			loginProperties.load(fis);

			// 2. 验证
			if (user.getPwd().equals(loginProperties.getProperty(user.getUsername()))) {
				b = true;
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return b;
	} // verify

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Properties getLoginProperties() {
		return loginProperties;
	}

	public void setLoginProperties(Properties loginProperties) {
		this.loginProperties = loginProperties;
	}

} // Login
