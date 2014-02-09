package com.chhuang.display;

import java.util.LinkedList;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

public abstract class TextDisplay {
	
	public static final int DEFAULT_MAX_LINES = 300;
	protected LinkedList<String> messageQueue;
	protected int currentNumOfLines = 0;
	protected JTextPane display;
	protected StyledDocument doc;
	protected int max_lines;
	
	public TextDisplay(JTextPane jtpDisplay){
		display = jtpDisplay;
		doc = display.getStyledDocument();
		max_lines = DEFAULT_MAX_LINES;
	}
	
	public void maximumLineFormat() throws BadLocationException{
		if(currentNumOfLines > max_lines){
			doc.remove(0, display.getText().indexOf("\n"));
		}
	}
	
	public void reset(){
		try {
			doc.remove(0, doc.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		};
	}
	
	public boolean isEmptyString(String str){
		return (str == null || str.equals(""));
	}
	
	public void setDisplayPane(JTextPane jtp){
		display = jtp;
	}
	
	public JTextPane getDisplayPane(){
		return display;
	}
	
	
	public abstract void output(String line);
	
}