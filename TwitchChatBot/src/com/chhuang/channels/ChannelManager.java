package com.chhuang.channels;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class ChannelManager {

	public static final String CHANNEL_FILE_NAME = "channels";
	
	private ArrayList<String> channels;
	//private ArrayList<Channel> channels;
	private ChannelManageUI ui;
	
	public ChannelManager() {
		initializeChannels();
	}
	
	public void showUI(){
		if(ui == null)
			ui = new ChannelManageUI(this);
		ui.setVisible(true);
	}
	
	private void initializeChannels(){
		if(!loadChannels()){
			channels = new ArrayList<String>();
			channels.add("#gemhuang2151992");
		}
	}	
	
	public int addChannel(String channel){
		if(!channel.trim().isEmpty()){
			channel = channel.trim().toLowerCase();
			
			while(channel.startsWith("#")){
				channel = channel.substring(1);
			}
			
			channel = "#" + channel;
			
			if(!channels.contains(channel)){
				channels.add(channel);
				Collections.sort(channels);
				return 0;
			}
			return 1;
		}
		return -1;
	}

	public void removeChannel(int index){
		if(index >= 0){
			channels.remove(index);
			Collections.sort(channels);
		}
	}
	
	public void editChannel(int index, String channel){
		if(index >=0){
			channel = channel.trim().toLowerCase();
			while(channel.startsWith("#")){
				channel = channel.substring(1);
			}
			channels.set(index, "#" + channel);
		}
	}

	public String getSelectedChannel(int index){
		return channels.get(index);
	}
	
	public String getSelectedChannel(String ch){		
		return getSelectedChannel(channels.indexOf(ch));
	}
	
	
	public ArrayList<String> getChannels(){
		return channels;
	}
	
	public boolean saveChannels(){
		try{
			File f = new File(CHANNEL_FILE_NAME);
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(channels);
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
	public boolean loadChannels(){
		try {
			File file = new File(CHANNEL_FILE_NAME);
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			channels = (ArrayList<String>) ois.readObject();
			
			ois.close();
			fis.close();
			return true;
			
		} catch (IOException | ClassNotFoundException e){
			System.out.println("NO FILE FOUND, NEW FILE [channels] CREATED!");
		 	return false;
		}

	}
}
