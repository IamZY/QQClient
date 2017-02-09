package com.qq;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class QQServer {
	private JFrame frame = new JFrame("server");
	private JPanel panel = new JPanel();

	private JTextArea receiver = new JTextArea(12, 16);


	private JScrollPane jPane = new JScrollPane(receiver);

	private ServerSocket server;
	private Socket socket;
	private UserBean userBean;

	private ArrayList<Integer> arrayList = new ArrayList<Integer>();
	private Map<Integer, Socket> socketMap = new HashMap<Integer, Socket>();

	public QQServer() {

		try {
			server = new ServerSocket(9999);

			AcceptConnectionThread act = new AcceptConnectionThread();
			act.start();

			// 初始化界面
			init();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void init() {

		panel.add(jPane);
		frame.add(panel);

		frame.setSize(200, 260);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}

	/**
	 * 收消息的线程
	 * 
	 * @author IamZY
	 * 
	 */

	class ReceiveThread extends Thread {

		private Socket socket;

		public ReceiveThread(Socket socket) {
			this.socket = socket;
		}

		public void run() {

			while (true) {
				
				try {
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

					userBean = (UserBean) ois.readObject();
					
					
					// 获得每个客户端的端口号
					InetSocketAddress address = (InetSocketAddress) socket
							.getRemoteSocketAddress();
					int port2 = address.getPort();
					
//					userBean.setMessage(port2 + " " + y + "-" + m + "-" + d + " " + h + ":" + mm + ":" + s + "\n" + userBean.getMessage());

					System.out.println("flag->" + userBean.getFlag());
					
					receiver.append(userBean.getMessage() + "\n");

					int flag = userBean.getFlag();

					if (flag == 2) {

						// 群聊
						//通过port遍历socket  
						Set set = socketMap.keySet();
						Iterator ite = set.iterator();

						while (ite.hasNext()) {
							Integer port = (Integer) ite.next();
							//找到所有的socket
							Socket socket = socketMap.get(port);

							UserBean userBean2 = new UserBean();
							
							userBean2.setMessage(userBean.getMessage());
							userBean2.setPort(port2);
							
							//userBean2.setMessage(userBean.getMessage());
							
							System.out.println("55555555555555555");
							System.out.println(userBean2.getMessage());
							
							userBean2.setFlag(2);

							ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

							oos.writeObject(userBean2);
							oos.flush();

							System.out.println("aaaaaaaaaaaaa");

						}

					} else if (flag == 3) {

						// 私聊

						System.out.println(userBean.getMessage());
						
						//String[] ss = userBean.getMessage().split(":");

						System.out.println(userBean.getPort());
						int port1 = userBean.getPort();
						
						System.out.println("\\\\\\\\\\\\\\\\\\\\");
						
						System.out.println(port1);
						System.out.println("ssssssssssssss");

						System.out.println("00000000000000000");
						
						// 找对应的端口号 socket
							
						Socket socket = socketMap.get(port1);
						UserBean userBean3 = new UserBean();

						System.out.println(userBean.getMessage());
						
						System.out.println("333333333333333");
						System.out.println(userBean.getPort());
						userBean3.setPort(userBean.getPort());
						userBean3.setMessage(userBean.getMessage());
						System.out.println("aaaaaaaaaaaaaaaaaaaaa");
						//本机的端口号
						userBean3.setPort(port2);
						
						System.out.println(userBean3.getPort());
						userBean3.setFlag(3);
					
						ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
						
						oos.writeObject(userBean3);
						oos.flush();

					}else if(flag == 4){
						System.out.println("666666666666666666");
						port2 = address.getPort();
						
						//Integer.toString(port2);
						
						System.out.println(port2);
						
						arrayList.remove((Integer)port2);
						
						System.out.println(arrayList.toString());
						
						//遍历好友列表
						Set set = socketMap.keySet();
						Iterator ite = set.iterator();

						while (ite.hasNext()) {

							Integer ports = (Integer) ite.next();
							Socket socket = socketMap.get(ports);

							ObjectOutputStream oos = new ObjectOutputStream(
									socket.getOutputStream());
							UserBean userBean4 = new UserBean();

							userBean4.setFlag(1);
							userBean4.setList(arrayList);
							oos.writeObject(userBean4);
							oos.flush();

						}
						
						
					}

					//WriteThread w = new WriteThread();
					//w.start();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}

	
	/**
	 * 接受连接的线程
	 * 
	 * @author Administrator
	 * 
	 */

	class AcceptConnectionThread extends Thread {

		public void run() {

			while (true) {

				try {

					socket = server.accept();

					// 获得每个客户端的端口号
					InetSocketAddress address = (InetSocketAddress) socket
							.getRemoteSocketAddress();
					int port = address.getPort();

					arrayList.add(port);
					System.out.println(arrayList.toString());

					// 将每个客户端的端口号和socket存如map中
					socketMap.put(port, socket);

					// 刷新好友列表

					// 遍历socket 将好友列表发给每个客户端
					Set set = socketMap.keySet();
					Iterator ite = set.iterator();

					while (ite.hasNext()) {

						Integer ports = (Integer) ite.next();
						Socket socket = socketMap.get(ports);

						ObjectOutputStream oos = new ObjectOutputStream(
								socket.getOutputStream());
						UserBean userBean1 = new UserBean();

						userBean1.setFlag(1);
						userBean1.setList(arrayList);
						oos.writeObject(userBean1);
						oos.flush();

					}

					ReceiveThread r = new ReceiveThread(socket);
					r.start();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String args[]) {
		QQServer qqServer = new QQServer();
	}

}
