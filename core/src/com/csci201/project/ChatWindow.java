package com.csci201.project;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ChatWindow extends JFrame{
	//GUI variables
	JPanel mainWindow;
	JPanel northPanel;
	JPanel southPanel;
	
	JTable chatArea;
	DefaultTableModel dtm = new DefaultTableModel(0,0);
	JTextField typeArea;
	
	JLabel chatTitle;
	JLabel usernameLabel;
	
	JScrollPane chatScroll;
	
	JButton submit;
	
	String username;
	
	//class variables
	
	//constructor
	public ChatWindow(String username){
		super("Chat Window");
		
		this.username = username;
		
		createWindow();
		
		initGuiElem();
		
		styleGuiElem();
		
		addGuiElem();
		
		addListeners();
		
		setVisible(true);
	}
	
	private void createWindow() {
		setSize(400, 300);
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
		typeArea = new JTextField();
		
		chatTitle = new JLabel("FrostByte Chat Window");
		usernameLabel = new JLabel(username);
		
		chatScroll = new JScrollPane(chatArea);
		
		submit = new JButton("Submit");
	}

	private void styleGuiElem() {
		// TODO Auto-generated method stub
		chatTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		chatArea.setCellSelectionEnabled(false);

	}
	
	private void addGuiElem() {
		// TODO Auto-generated method stub
		northPanel.add(chatTitle);
		northPanel.add(usernameLabel);
		
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
		
		typeArea.addKeyListener(new typeAreaListener());
	}
	
	public static void main(String[] args){
		new ChatWindow("Amos");
	}
	
	class buttonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!typeArea.getText().equals("")){
				Object[] obj = new Object[1];
				obj[0] = username + ": " + typeArea.getText();
				
				dtm.addRow(obj);
				typeArea.setText("");
			}
			
			/*int rownum = dtm.getRowCount();
			DefaultTableCellRenderer rightRender = new DefaultTableCellRenderer();
			rightRender.setHorizontalAlignment(SwingConstants.RIGHT);
			chatArea.getColumnModel().getColumn(rownum-1).setCellRenderer(rightRender);*/
		}
	}
	
	class typeAreaListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			//if(e.equals(obj))
			switch(e.getKeyCode()){
				case KeyEvent.VK_ENTER:
					if(!typeArea.getText().equals("")){
						Object[] obj = new Object[1];
						obj[0] = username + ": " + typeArea.getText();
						
						dtm.addRow(obj);
						typeArea.setText("");
					}else{
						typeArea.setText("");
					}
					break;
				default:
					break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
