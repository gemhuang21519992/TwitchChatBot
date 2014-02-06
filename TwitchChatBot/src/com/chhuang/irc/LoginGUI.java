package com.chhuang.irc;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.chhuang.accounts.Account;


public class LoginGUI{
	
	public static final int LOGIN_CLICKED = 0;

	private ArrayList<Account> accounts;
	private ArrayList<String> channels;
	
	private boolean valid = false;
	private int choice;
	private String nick;
	private String pass;
	private String channel;
	
	private JLabel lblNick;
	private JLabel lblCh;
	private JComboBox<String> txtNicks;
	private JComboBox<String> jcbChannel;

	

	public LoginGUI(JFrame frame, ArrayList<Account> accounts, ArrayList<String> channels) {

		this.accounts =  accounts;
		this.channels = channels;
		choice = JOptionPane.showOptionDialog(frame, getPane(), "Login", JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE, null,new String[] {"Login", "Cancel"}, "Login");
		if(choice == LOGIN_CLICKED) login();
		
	}
	
	public void login(){
		
		int index = txtNicks.getSelectedIndex();
		nick = accounts.get(index).getNick();
		pass = accounts.get(index).getPass();
		channel = (String)jcbChannel.getSelectedItem();;
		
		if (nick != null && !nick.trim().equals("") && channel != null && !channel.trim().equals("")){ 
			nick = nick.trim();
			channel = channel.trim();
			valid = true;
		}
	}
	
	public JPanel getPane(){
		
		JPanel mainPane = new JPanel();
		JPanel pLabels = new JPanel();
		JPanel pInputs = new JPanel();
		
		pLabels.setLayout(new GridLayout(2,1, 2, 10));
		pInputs.setLayout(new GridLayout(2,1,1, 2));
		
		lblNick = new JLabel("Nickname: ", JLabel.RIGHT);
		lblCh = new JLabel("Channel: ", JLabel.RIGHT);
		
		String[] strNicks = new String[accounts.size()];
		for(int i = 0; i < accounts.size(); i++){
			strNicks[i] = accounts.get(i).getNick();
		}
		

		String[] strChannels = new String[channels.size()];
		strChannels = channels.toArray(strChannels);
		
		txtNicks = new JComboBox<String>(strNicks);
		jcbChannel = new JComboBox<String>(strChannels);
		

		pLabels.add(lblNick);
		pLabels.add(lblCh);
		pInputs.add(txtNicks);
		pInputs.add(jcbChannel);
		
		mainPane.add(pLabels);
		mainPane.add(pInputs);
		
		return mainPane;
	}
	
	
	public String getNick(){
		return nick;
	}
	
	public String getPass(){
		return pass;
	}
	
	public String getChannel(){
		return channel;
	}
	
	public boolean isValid(){
		return valid;
	}

}
