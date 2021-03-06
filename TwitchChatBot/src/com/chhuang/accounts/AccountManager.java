package com.chhuang.accounts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class AccountManager{
	
	public static final String ACCOUNT_FILE_NAME = "accounts";
	
	private ArrayList<Account> accounts;
	private AccountManageUI ui;
	
	public AccountManager() {
		initializeAccounts();
	}

	public void initializeAccounts(){
		if(!loadAccounts()){
			accounts = new ArrayList<Account>();
			accounts.add(new Account(Account.CRAPPY_BOT, "oauth:iidx3w1bqh3q4daupgar3t4fmuw41s6"));
		}
		Collections.sort(accounts);
	}
	
	public void showUI(){
		if (ui == null){
			ui = new AccountManageUI(this);
		}
		ui.setVisible(true);
	}
	
	public void removeAccount(int index) {
		accounts.remove(index);
		Collections.sort(accounts);
	}
	
	public void editAccount(int index, String nick, String oauth){
		if(index >= 0){
			Account acc = accounts.get(index);
			acc.setNick(nick);
			acc.setPass(oauth);
			Collections.sort(accounts);
		}
	}

	public void addAccount(String nick, String oauth) {
		if (nick != null && !nick.trim().equals("")){
			nick = nick.trim();
			oauth = oauth.trim();
			Account newAccount = new Account(nick,oauth);
			accounts.add(newAccount);
			Collections.sort(accounts);
		}
	}
	
	public boolean saveAccounts(){
		try{
			File f = new File(ACCOUNT_FILE_NAME);
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(accounts);
			oos.close();
			fos.close();
			return true;
		} catch (IOException e){
			System.out.println("IOException");
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean loadAccounts(){
		try{
			File file = new File(ACCOUNT_FILE_NAME);
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			accounts = (ArrayList<Account>) ois.readObject();

			ois.close();
			fis.close();
			return true;
			
		} catch(FileNotFoundException e){
			System.out.println("File Not Found Exception");	
			e.printStackTrace();
			return false;
		} catch(ClassNotFoundException e){
			System.out.println("Class Not Found Exception");
			e.printStackTrace();
			return false;
		} catch(IOException e){
			System.out.println("IOException");
			e.printStackTrace();
			return false;
		}
	}
	
	public Account getSelectedAccount(int index){
		return accounts.get(index);
	}
	
	public ArrayList<Account> getAccounts(){
		return accounts;
	}
}
