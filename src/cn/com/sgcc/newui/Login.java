package cn.com.sgcc.newui;

import java.awt.EventQueue;
import org.apache.commons.logging.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import cn.com.sgcc.dao.DaoException;
import cn.com.sgcc.db.DatabaseLayer;
import cn.com.sgcc.ui.LoginPanel;
import cn.com.sgcc.ui.MainFrame;
import cn.com.sgcc.vo.User;

import com.mysql.jdbc.log.Log;
import com.mysql.jdbc.log.LogFactory;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame{

	private JFrame loginframe;
	private JTextField usernameTextField;
	private JTextField passwordTextField;




	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.loginframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		loginframe = new JFrame();
		
		loginframe.setBounds(100, 100, 528, 339);
		loginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		loginframe.setIconImage(new ImageIcon("./icon/main.png").getImage());
		loginframe.setTitle("国家电网安全电子文档加密客户端");
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 512, 303);
		panel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		
		JLabel usernameLabel = new JLabel("用户名");
		usernameLabel.setBounds(154, 73, 42, 18);
		usernameLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
		
		JLabel passwordLabel = new JLabel("密    码");
		passwordLabel.setBounds(154, 148, 42, 18);
		passwordLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
		
		usernameTextField = new JTextField();
		usernameTextField.setBounds(230, 71, 132, 23);
		usernameTextField.setColumns(10);
		
		passwordTextField = new JTextField();
		passwordTextField.setBounds(230, 143, 132, 23);
		passwordTextField.setColumns(10);
		
		JButton loginButton = new JButton("登    录");
		loginButton.setBounds(213, 223, 73, 22);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
			}
		});
		loginframe.getContentPane().setLayout(null);
		loginframe.getContentPane().add(panel);
		panel.setLayout(null);
		panel.add(passwordLabel);
		panel.add(usernameLabel);
		panel.add(passwordTextField);
		panel.add(usernameTextField);
		panel.add(loginButton);
	}
}
