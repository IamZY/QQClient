package com.qq;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


public class QQLoginFrame {
	private JFrame frame = new JFrame("QQ");
	// ���岼��
	private JPanel p1 = new JPanel();
	// �����²�
	private JPanel p2 = new JPanel();
	// �û���������
	private JPanel p3 = new JPanel();
	// �Ҳ�ע����һ�
	private JPanel p4 = new JPanel();
	// ��ס������Զ���½
	private JPanel p5 = new JPanel();

	private JLabel nullLabel = new JLabel();
	private JLabel nullLabel2 = new JLabel();
	private JLabel nullLabel3 = new JLabel();
	private JLabel nullLabel4 = new JLabel();
	private JLabel nullLabel5 = new JLabel();
	// private JLabel pwdlaJLabel = new JLabel("����");

	private JLabel imageLabel = new JLabel(new ImageIcon("src/images/qq.jpg"));
	private JLabel userimageJLabel = new JLabel(new ImageIcon("src/images/qq2.jpg"));
	private JLabel msglLabel = new JLabel();

	private JButton regButton = new JButton("ע���˺�");
	private JButton lookButton = new JButton("�һ�����");
	private JButton loginButton = new JButton("��ȫ��¼");
	private JButton nullButton = new JButton();

	private JTextField userField = new JTextField("�û���", 10);
	private JPasswordField pwdField = new JPasswordField("********", 10);

	private Checkbox pwdCheckbox = new Checkbox("��ס����", false);
	private Checkbox loginCheckbox = new Checkbox("�Զ���½", false);

	// �Ի���
	private JDialog dialog = new JDialog(frame, false);

	// ���ô��ڵĳ���
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private final int WIDTH = 430;
	private final int HEIGHT = 330;
	
	final int newWidth = (int) ((screen.getWidth() - WIDTH) / 2);
	final int newHeight = (int) ((screen.getHeight() - HEIGHT) / 2);
	

	public QQLoginFrame() {

		init();
		
		isempty();
		
		login();

	}
	
	/**
	 * ��ʼ������
	 */
	public void init(){
		

		p1.setLayout(new BorderLayout());
		p1.add(imageLabel, BorderLayout.NORTH);

		p2.setLayout(new BorderLayout());
		p2.add(userimageJLabel, BorderLayout.WEST);
		p2.add(p3);

		p3.setLayout(new GridLayout(5, 1, 2, 2));
		userField.setForeground(Color.gray); // �������ɫ
		pwdField.setForeground(Color.gray);
		p3.add(nullLabel);
		p3.add(userField);
		// p3.add(pwdlaJLabel);
		p3.add(pwdField);

		p4.setLayout(new GridLayout(5, 1, 2, 2));
		// regButton.setBounds(400, 200, 20, 10);
		p4.add(nullLabel2);
		regButton.setForeground(Color.BLUE);
		p4.add(regButton);
		// lookButton.setBounds(400, 200, 20, 10);
		lookButton.setForeground(Color.BLUE);
		p4.add(lookButton);
		p4.add(nullLabel3);
		p2.add(p4, BorderLayout.EAST);

		p5.setLayout(new GridLayout(1, 3, 2, 2));
		p5.add(pwdCheckbox);
		p5.add(nullLabel4);
		p5.add(loginCheckbox);
		p3.add(p5);
		loginButton.setBackground(Color.BLUE);
		loginButton.setForeground(Color.WHITE);
		p3.add(loginButton);

		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		p1.add(p2, BorderLayout.CENTER);

		frame.add(p1);
		// frame.setUndecorated(true); //�����ޱ�����

		frame.setLocation(newWidth, newHeight);
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	
	/**
	 * �ж�Textfiled�Ƿ�Ϊ��
	 */
	public void isempty(){
		userField.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (userField.getText().equals("�û���")) {
					userField.setText("");
				}
			}

		});

		pwdField.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (pwdField.getText().equals("********")) {
					pwdField.setText("");
				}

			}
		});
	}
	
	
	/**
	 * ��¼�¼�
	 */
	public void login(){
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				String id = userField.getText();
				String pwd = pwdField.getText();
					
				User user = new User(id, pwd);
				Login login = new Login(user);

				if (login.verify()) {
					new FriendListFrame(userField.getText());
					frame.setVisible(false);
				} else {
					msglLabel.setText("               �û�����������������µ�¼");
					dialog.add(msglLabel);
					dialog.setSize(250, 150);
					dialog.setLocation(((int) screen.getWidth() - 300) / 2,
							((int) screen.getHeight() - 200) / 2);
					dialog.setVisible(true);
					dialog.setResizable(false);
				}

			}
		});
	}
	
	
	/**
	 * main
	 */
	public static void main(String args[]) {
		QQLoginFrame qq = new QQLoginFrame();

	}

}
