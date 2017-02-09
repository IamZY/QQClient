package com.qq;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.sun.net.ssl.SSLContext;

public class FriendListFrame {

	// 窗口
	private JFrame frame = new JFrame("QQ");
	// 大布局
	private JPanel panel = new JPanel();

	// 窗口上部
	private JPanel panel2 = new JPanel();

	// 上部中间
	private JPanel panel3 = new JPanel();

	// 窗口下部
	private JPanel panel4 = new JPanel();

	// 好友列表的名字
	private JPanel panel5 = new JPanel();

	// 好友列表
	private JPanel panel6 = new JPanel();

	// 添加好友
	private JPanel panel7 = new JPanel();

	// 头像
	private JLabel imagelabel = new JLabel(new ImageIcon("src/images/qq2.jpg"));

	// 用户名显示框
	private JLabel userLabel = new JLabel();
	private JLabel friendLabel = new JLabel();

	// 个性签名
	private JTextField userTextField = new JTextField(15);
	// 搜索框
	private JTextField serachtJTextField = new JTextField(20);

	// 好友下拉列表
	private JList list = new JList();

	// 按钮
	JButton exitButton = new JButton("离线");
	JButton moreButton = new JButton("群聊");

	private final int F_WIDTH = 280;
	private final int F_HEIGHT = 600;

	private Socket socket;

	private ChatFramePrivate client; // 私聊
	private ChatFrameMore c; // 群聊
	
	private Map<Integer, ChatFramePrivate> friendMap = new HashMap<Integer, ChatFramePrivate>();
	
	private String name;
	//private Integer port;
	
	
	public FriendListFrame(String name) {

		this.name = name;
			
		init();
		isempty();
		//私聊
		listactionlistener();
		//群聊
		morelistener();
		//离线
		exitlistener();

		try {
			socket = new Socket("127.0.0.1", 9999);

			ReceiveMsg r = new ReceiveMsg();
			r.start();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 初始化窗口
	 */
	public void init() {
		panel.setLayout(new BorderLayout());
		panel.add(panel2, BorderLayout.NORTH);
		panel.add(panel4, BorderLayout.CENTER);

		panel2.setBackground(new Color(32, 143, 255));
		panel2.add(imagelabel, BorderLayout.WEST);
		panel2.add(panel3, BorderLayout.CENTER);

		panel3.setBackground(new Color(32, 143, 255));
		panel3.setLayout(new GridLayout(3, 1, 2, 2));
		panel3.add(userLabel);
		userLabel.setText(name);
		panel3.add(userTextField);
		userTextField.setText("不让自己的努力付之东流。");

		panel4.setBackground(new Color(32, 143, 255));
		panel4.setLayout(new BorderLayout());
		serachtJTextField.setText("搜索：联系人、群、讨论组，企业");
		panel4.add(serachtJTextField, BorderLayout.NORTH);
		panel4.add(panel5);

		// panel5.setBackground(new Color(32,143,255));
		panel5.setLayout(new BorderLayout());
		friendLabel.setText("好友列表");
		panel5.add(friendLabel, BorderLayout.NORTH);
		panel5.add(panel6);

		panel6.setBackground(new Color(32, 143, 255));
		panel6.setLayout(new BorderLayout());
		panel6.add(list, BorderLayout.NORTH);

		panel7.setBackground(new Color(32, 143, 255));
		panel6.add(panel7, BorderLayout.SOUTH);
		panel7.setLayout(new GridLayout(1, 3, 2, 2));
		panel7.add(exitButton);
		panel7.add(new JLabel());
		panel7.add(moreButton);

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		final int newWidth = (int) ((screen.getWidth() - F_WIDTH) / 2);
		final int newHeight = (int) ((screen.getHeight() - F_HEIGHT) / 2);

		frame.add(panel);
		frame.setLocation(newWidth, newHeight);
		frame.setResizable(false);
		frame.setSize(280, 600);
		frame.setVisible(true);
	}

	/*
	 * 判断
	 */
	public void isempty() {

		serachtJTextField.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (serachtJTextField.getText().equals("搜索：联系人、群、讨论组，企业")) {
					serachtJTextField.setText("");
				}
			}

		});
	}

	/**
	 * list私聊监听器
	 */
	public void listactionlistener() {
		list.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
		
					Integer port = (Integer) list.getSelectedValue();
					client = new ChatFramePrivate(socket,port);
					
					System.out.println(",,,,,,,,,,,,,,,,");
					System.out.println(port);
					System.out.println("11111111111111111");
					
					//将端口号和对应的窗口存入map中
					friendMap.put(port, client);
																				
					Set set = friendMap.keySet();
					Iterator ite = set.iterator();
					while(ite.hasNext()){
						
						Integer port3 = (Integer) ite.next();
						System.out.println(port3);
						ChatFramePrivate chatFramePrivate = friendMap.get(port3);
						System.out.println(chatFramePrivate);
					}
					
					System.out.println("777777777777777777");
					
					System.out.println("ssssssssssssssssssss");
					
				}
			}

		});
	}

	/**
	 * 群聊监听器
	 */
	public void morelistener() {
		moreButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				c = new ChatFrameMore(socket);

			}

		});
	}

	
	/**
	 * 离线监听器
	 */
	public void exitlistener(){
		exitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				
					UserBean userBean = new UserBean();
					userBean.setFlag(4);
					
					oos.writeObject(userBean);
					
					frame.dispose();	
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
	}
	
	
	
	
	/**
	 * 接受消息线程
	 */
	class ReceiveMsg extends Thread {

		public void run() {

			try {

				while (true) {
					// 读取对象
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
					UserBean user = (UserBean) ois.readObject();
					
					
					System.out.println("99999999999999999");
					System.out.println(user.getMessage());
					System.out.println(user.getPort());

					//接受服务器发来的消息 flag
					int flag = user.getFlag();

					/**
					 * 接受好友列表 if(flag == 1)接受列表
					 */

					System.out.println("...............");
					System.out.println("flag->" + flag);

					if(flag == 1){
						DefaultListModel listModel = new DefaultListModel();

						for (int i = 0; i < user.getList().size(); i++) {

							System.out.println(user.getList().get(i));

							listModel.addElement(user.getList().get(i));
						}

						list.setModel(listModel);

					}else if (flag == 2) {

						/**
						 * 群聊 if(flag == 2)群聊
						 */

						System.out.println("----------------");
						if(c == null){
							
							c = new ChatFrameMore(socket);
						}
						
						// 群聊方法
						System.out.println(c);
						//System.out.println(user.getPort());
						c.chatmore(user.getMessage(),user.getPort());
						
						
					} else if (flag == 3) {

						/**
						 * 私聊 if(flag == 3) 端口号:消息
						 */
						
						System.out.println("================");
												
						Set set = friendMap.keySet();
						Iterator ite = set.iterator();
						
						Integer port2 = user.getPort();
						
						System.out.println("333333333333333333");
						
						System.out.println(port2);
						
						ChatFramePrivate client = friendMap.get(port2);
						System.out.println("jjjjjjjjjjjjjjjjjj");
						System.out.println(client);
						
						System.out.println(user.getMessage());
						System.out.println(user.getPort());
						
						if(client == null){
							System.out.println("kkkkkkkkkkkkkkkkkkkk");
							client = new ChatFramePrivate(socket, port2);
							
							//保存窗口
							friendMap.put(port2, client);
														
							System.out.println("------------------");
							//String[] ss = user.getMessage().split(":");
							client.chatmore(user.getMessage(),user.getPort());

						}else{
							//String[] ss = user.getMessage().split(":");							
							client.chatmore(user.getMessage(),user.getPort());
						}
						
					
//						System.out.println(client);
//						client.chatmore(user.getMessage());

					}

				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * main
	 */
	public static void main(String args[]) {
		FriendListFrame flf = new FriendListFrame("8");
	}

}
