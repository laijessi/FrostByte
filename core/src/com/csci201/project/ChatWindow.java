package com.csci201.project;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class ChatWindow extends JFrame{
	//GUI variables
	JPanel mainWindow;
	JPanel northPanel;
	JPanel southPanel;
	
	JTable chatArea;
	DefaultTableModel dtm = new DefaultTableModel(0,0);
	JTextArea typeArea;
	
	JLabel chatTitle;
	JLabel username;
	
	JScrollPane chatScroll;
	
	JButton submit;
	
	//class variables
	
	//constructor
	public ChatWindow(){
		super("Chat Window");
		
		createWindow();
		
		initGuiElem();
		
		styleGuiElem();
		
		addGuiElem();
		
		addListeners();
		
		setVisible(true);
	}
	
	private void createWindow() {
		setSize(800, 600);
		setLocation(200, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initGuiElem() {
		// TODO Auto-generated method stub
		
		mainWindow = new JPanel();
		northPanel = new JPanel();
		southPanel = new JPanel();
		mainWindow.setLayout(new BorderLayout());
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
		southPanel.setLayout(new BorderLayout());
		
		chatArea = new JTable();
		dtm.setColumnIdentifiers(new String[]{""});
		chatArea.setModel(dtm);
		typeArea = new JTextArea();
		
		chatTitle = new JLabel("FrostByte Chat Window");
		username = new JLabel("SAMPLE USERNAME HERE");
		
		chatScroll = new JScrollPane(chatArea);
		
		submit = new JButton("Submit");
	}

	private void styleGuiElem() {
		// TODO Auto-generated method stub
		chatTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		username.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		chatArea.setCellSelectionEnabled(false);

	}
	
	private void addGuiElem() {
		// TODO Auto-generated method stub
		northPanel.add(chatTitle);
		northPanel.add(username);
		
		southPanel.add(typeArea, BorderLayout.CENTER);
		southPanel.add(submit, BorderLayout.EAST);
		
		mainWindow.add(chatScroll, BorderLayout.CENTER);
		mainWindow.add(southPanel, BorderLayout.SOUTH);
		mainWindow.add(northPanel, BorderLayout.NORTH);
		
		add(mainWindow);
	}
	
	private void addListeners() {
		// TODO Auto-generated method stub
		submit.addActionListener(new buttonListener());
		
		chatScroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
		    public void adjustmentValueChanged(AdjustmentEvent e) {  
		        e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
		    }
		});
	}
	
	public static void main(String[] args){
		new ChatWindow();
	}
	
	class buttonListener implements ActionListener{
		//class variables
		
		//constructor
		public buttonListener(){
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			dtm.addRow(new Object[]{typeArea.getText()});
			typeArea.setText("");
		}
	}

}
