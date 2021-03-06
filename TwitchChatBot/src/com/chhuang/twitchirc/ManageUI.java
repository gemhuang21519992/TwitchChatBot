package com.chhuang.twitchirc;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

@SuppressWarnings("serial")
public abstract class ManageUI extends JFrame{
	public static final Font DEFAULT_FONT = new Font("Arial",Font.PLAIN,15);
	protected DefaultListModel<String> dlmMainList;
	protected JList<String> lstMain;
	protected JScrollPane scrollPaneMainLst;
	protected JButton btnAdd;
	protected JButton btnRemove;
	protected JButton btnEdit;
	protected JPanel panelButtons;
	
	public ManageUI(String title){
		setTitle(title);
		setSize(350,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()-50);
		setLayout(new BorderLayout(0,5));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		initializeButtons();
		
		getContentPane().add(panelButtons, BorderLayout.SOUTH);
	}
	
	protected void initializeButtons(){
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				add();
			}
		});
		
		btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remove();
			}
		});
		
		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edit();
			}
		});
		
		panelButtons = new JPanel(new GridLayout(1,0,2,2));
		panelButtons.add(btnAdd);
		panelButtons.add(btnRemove);
		panelButtons.add(btnEdit);
	}
	
	protected void initializeMainList(){
		dlmMainList = new DefaultListModel<String>();
		lstMain = new JList<String>(dlmMainList);
		lstMain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstMain.setFont(DEFAULT_FONT);
		
		resetList();
		
		lstMain.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2){
					edit();
				}
			}
		});
		
		scrollPaneMainLst = new JScrollPane(lstMain);
	}
	
	protected abstract void resetList();
	
	public abstract void add();
	
	public abstract void remove();
	
	public abstract void edit();
}
