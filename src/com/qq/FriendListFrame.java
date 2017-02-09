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

	// ����
	private JFrame frame = new JFrame("QQ");
	// �󲼾�
	private JPanel panel = new JPanel();

	// �����ϲ�
	private JPanel panel2 = new JPanel();

	// �ϲ��м�
	private JPanel panel3 = new JPanel();

	// �����²�
	private JPanel panel4 = new JPanel();

	// �����б������
	private JPanel panel5 = new JPanel();

	// �����б�
	private JPanel panel6 = new JPanel();

	// ��Ӻ���
	private JPanel panel7 = new JPanel();

	// ͷ��
	private JLabel imagelabel = new JLabel(new ImageIcon("src/images/qq2.jpg"));

	// �û�����ʾ��
	private JLabel userLabel = new JLabel();
	private JLabel friendLabel = new JLabel();

	// ����ǩ��
	private JTextField userTextField = new JTextField(15);
	// ������
	private JTextField serachtJTextField = new JTextField(20);

	// ���������б�
	private JList list = new JList();

	// ��ť
	JButton exitButton = new JButton("����");
	JButton moreButton = new JButton("Ⱥ��");

	private final int F_WIDTH = 280;
	private final int F_HEIGHT = 600;

	private Socket socket;

	private ChatFramePrivate client; // ˽��
	private ChatFrameMore c; // Ⱥ��
	
	private Map<Integer, ChatFramePrivate> friendMap = new HashMap<Integer, ChatFramePrivate>();
	
	private String name;
	//private Integer port;
	
	
	public FriendListFrame(String name) {

		this.name = name;
			
		init();
		isempty();
		//˽��
		listactionlistener();
		//Ⱥ��
		morelistener();
		//����
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
	 * ��ʼ������
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
		userTextField.setText("�����Լ���Ŭ����֮������");

		panel4.setBackground(new Color(32, 143, 255));
		panel4.setLayout(new BorderLayout());
		serachtJTextField.setText("��������ϵ�ˡ�Ⱥ�������飬��ҵ");
		panel4.add(serachtJTextField, BorderLayout.NORTH);
		panel4.add(panel5);

		// panel5.setBackground(new Color(32,143,255));
		panel5.setLayout(new BorderLayout());
		friendLabel.setText("�����б�");
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
	 * �ж�
	 */
	public void isempty() {

		serachtJTextField.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (serachtJTextField.getText().equals("��������ϵ�ˡ�Ⱥ�������飬��ҵ")) {
					serachtJTextField.setText("");
				}
			}

		});
	}

	/**
	 * list˽�ļ�����
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
					
					//���˿ںźͶ�Ӧ�Ĵ��ڴ���map��
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
	 * Ⱥ�ļ�����
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
	 * ���߼�����
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
	 * ������Ϣ�߳�
	 */
	class ReceiveMsg extends Thread {

		public void run() {

			try {

				while (true) {
					// ��ȡ����
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
					UserBean user = (UserBean) ois.readObject();
					
					
					System.out.println("99999999999999999");
					System.out.println(user.getMessage());
					System.out.println(user.getPort());

					//���ܷ�������������Ϣ flag
					int flag = user.getFlag();

					/**
					 * ���ܺ����б� if(flag == 1)�����б�
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
						 * Ⱥ�� if(flag == 2)Ⱥ��
						 */

						System.out.println("----------------");
						if(c == null){
							
							c = new ChatFrameMore(socket);
						}
						
						// Ⱥ�ķ���
						System.out.println(c);
						//System.out.println(user.getPort());
						c.chatmore(user.getMessage(),user.getPort());
						
						
					} else if (flag == 3) {

						/**
						 * ˽�� if(flag == 3) �˿ں�:��Ϣ
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
							
							//���洰��
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
