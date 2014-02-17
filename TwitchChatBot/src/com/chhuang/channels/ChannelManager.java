package com.chhuang.channels;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ChannelManager implements Comparator<Channel>{

	public static final String CHANNEL_FILE_NAME = "channels";
	
	private ArrayList<Channel> channels;
	private ChannelManageUI ui;
	
	public ChannelManager() {
		initializeChannels();
	}
	
	public void showUI(){
		if(ui == null)
			ui = new ChannelManageUI(this);
		
		ui.checkOnline();
		ui.setVisible(true);
	}
	
	private void initializeChannels(){
		if(!loadChannels()){
			channels = new ArrayList<Channel>();
			channels.add(new Channel("#gemhuang2151992"));
		}
		
		for(Channel ch : channels){
			ch.checkOnline();
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
				Channel newCh = new Channel(channel);
				newCh.checkOnline();
				channels.add(newCh);
				Collections.sort(channels, this);
				return 0;
			}
			return 1;
		}
		return -1;
	}

	public void removeChannel(int index){
		if(index >= 0){
			channels.remove(index);
			Collections.sort(channels, this);
		}
	}
	
	public void editChannel(int index, String channel){
		if(index >=0){
			channel = channel.trim().toLowerCase();
			while(channel.startsWith("#")){
				channel = channel.substring(1);
			}
			channels.get(index).setChannel("#" + channel);
			channels.get(index).checkOnline();
			Collections.sort(channels, this);
		}
	}

	public Channel getSelectedChannel(int index){
		return channels.get(index);
	}
	
	public Channel getSelectedChannel(String ch){	
		return getSelectedChannel(channels.indexOf(ch));
	}
		
	public ArrayList<Channel> getChannels(){
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
			channels = (ArrayList<Channel>) ois.readObject();
			
			ois.close();
			fis.close();
			return true;
			
		} catch (IOException | ClassNotFoundException e){
			System.out.println("NO FILE FOUND, NEW FILE [channels] CREATED!");
		 	return false;
		}

	}

	@Override
	public int compare(Channel c1, Channel c2) {
		return c1.getChannel().compareToIgnoreCase(c2.getChannel());
	}
}
