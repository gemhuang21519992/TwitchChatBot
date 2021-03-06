package com.chhuang.accounts;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.chhuang.twitchirc.ManageUI;

@SuppressWarnings("serial")
public class AccountManageUI extends ManageUI{
	
	public static final String ACCOUNT_MANAGE_UI_TITLE = "Accounts";
	
	private AccountManager accountManager;

	private JTextField lblPass;
	private JPanel panelAccountInfo;
	
	public AccountManageUI(AccountManager accManage) {
		super(ACCOUNT_MANAGE_UI_TITLE);
		this.accountManager = accManage;
		initializeUI();
	}
	
	private void initializeUI(){
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				accountManager.saveAccounts();
			}
		});
		
		initializeMainList();
		
		lblPass = new JTextField(" ");
		lblPass.setFont(DEFAULT_FONT);
		lblPass.setHorizontalAlignment(SwingUtilities.CENTER);
		lblPass.setBorder(null);
		lblPass.setOpaque(false);
		lblPass.setEditable(false);
		
		lstMain.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if(!arg0.getValueIsAdjusting()){
					int index = lstMain.getSelectedIndex();
					if(index >= 0){
						Account acc = accountManager.getSelectedAccount(index);
						lblPass.setText(acc.getPass());
						btnRemove.setEnabled(true);
						if(acc.getNick().equalsIgnoreCase(Account.CRAPPY_BOT)){
							btnRemove.setEnabled(false);
						}
						
					}else{
						lblPass.setText("");
					}
				}
			}
		});
		
		panelAccountInfo = new JPanel(new BorderLayout(5,5));
		panelAccountInfo.add(scrollPaneMainLst, BorderLayout.CENTER);
		panelAccountInfo.add(lblPass, BorderLayout.SOUTH);
	
		this.getContentPane().add(panelAccountInfo, BorderLayout.CENTER);
		this.getContentPane().add(panelButtons, BorderLayout.SOUTH);
	}

	protected void resetList(){
		dlmMainList.clear();
		if(!accountManager.getAccounts().isEmpty()){
			for(Account acc : accountManager.getAccounts()){
				dlmMainList.addElement(acc.getNick());
			}
			lstMain.clearSelection();
		}
	}

	@Override
	public void add() {
		JPanel p = new JPanel();
		JPanel pLabels = new JPanel();
		JPanel pInputs = new JPanel();
		
		pLabels.setLayout(new GridLayout(2,1));
		pInputs.setLayout(new GridLayout(2,1));
		
		JLabel lblNick = new JLabel("Nickname: ", JLabel.RIGHT);
		JLabel lblOath = new JLabel("Pass: ", JLabel.RIGHT);
		JTextField txtNick = new JTextField(20);
		JTextField txtOauth = new JTextField(26);
		pLabels.add(lblNick);
		pLabels.add(lblOath);
		pInputs.add(txtNick);
		pInputs.add(txtOauth);
		
		p.add(pLabels);
		p.add(pInputs);

		int choice = JOptionPane.showOptionDialog(this, p, "Add account", JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE, null,new String[] {"Add"}, "Add");
		
		if(choice == 0){
			String nick = txtNick.getText();
			String oauth = txtOauth.getText(); 
			
			if (!nick.trim().isEmpty()){
				nick = nick.trim();
				oauth = oauth.trim();
				accountManager.addAccount(nick, oauth);
				resetList();
			}
		}
	}

	@Override
	public void remove() {
		int index = lstMain.getSelectedIndex();
		
		if(index >= 0){
			int choice = JOptionPane.showOptionDialog(this, "Remove " + accountManager.getSelectedAccount(index).getNick() + "?", "Remove account", JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE, null,new String[] {"Yes","Cancel"}, "Cancel");
			if(choice == 0){
				accountManager.removeAccount(index);
				resetList();
			}
		}
	}

	@Override
	public void edit() {
		int index = -1;
		if((index = lstMain.getSelectedIndex()) >= 0){
			Account acc = accountManager.getSelectedAccount(index);
			
			JPanel p = new JPanel();
			JPanel pLabels = new JPanel();
			JPanel pInputs = new JPanel();
			
			pLabels.setLayout(new GridLayout(2,1));
			pInputs.setLayout(new GridLayout(2,1));
			
			JLabel lblNick = new JLabel("Nickname: ", JLabel.RIGHT);
			JLabel lblOath = new JLabel("Pass: ", JLabel.RIGHT);
			JTextField txtNick = new JTextField(acc.getNick(),20);
			if(acc.getNick().equalsIgnoreCase(Account.CRAPPY_BOT)){
				txtNick.setEditable(false);
			}
			JTextField txtOauth = new JTextField(acc.getPass(),26);
			pLabels.add(lblNick);
			pLabels.add(lblOath);
			pInputs.add(txtNick);
			pInputs.add(txtOauth);
			
			p.add(pLabels);
			p.add(pInputs);
			
			int choice = JOptionPane.showOptionDialog(this, p, "Editing - " + acc.getNick(), JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE, null,new String[] {"Edit", "Cancel"}, "Edit");
			
			String nick = txtNick.getText().trim().toLowerCase();
			
			if(choice == 0 && nick != null && nick.equals("")){
				
				String oauth = txtOauth.getText(); 

				nick = nick.trim();
				oauth = oauth.trim();
				accountManager.editAccount(index, nick, oauth);
				resetList();

			}
		}
	}
}
