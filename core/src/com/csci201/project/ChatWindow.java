package com.csci201.project;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	String hostIP;
	
	//class variables
	Scanner input;
	PrintWriter output;
	
	//constructor
	public ChatWindow(String username, String hostIP){
		super("Chat Window");
		
		this.username = username;
		this.hostIP = hostIP;
		
		createWindow();
		
		initGuiElem();
		
		styleGuiElem();
		
		addGuiElem();
		
		addListeners();
		
		setVisible(true);
		
		try{
			Socket s = new Socket(hostIP, 6789);
			input = new Scanner(s.getInputStream());
			output = new PrintWriter(s.getOutputStream());
			
			//output.println("/connect");
			//output.flush();
			
			clientThread ct = new clientThread(s);
			ct.start();
		}catch (IOException ioe){
			System.out.println("IOException in client constructor");
		}
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
		
		chatArea.setEnabled(false);

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
		submit.addActionListener(new buttonListener());
		
		/*chatScroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
		    public void adjustmentValueChanged(AdjustmentEvent e) {  
		        e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
		    }
		});*/
		
		typeArea.addKeyListener(new typeAreaListener());
	}
	
	public void updateScroll(){
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				chatScroll.getVerticalScrollBar().setValue(chatScroll.getVerticalScrollBar().getMaximum()+16);
			}
		});
	}
	
	/*public static void main(String[] args){
		new ChatWindow("Amos");
	}*/
	
	class buttonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			chatScroll.revalidate();
			if(!typeArea.getText().equals("")){
				Object[] obj = new Object[1];
				obj[0] = username + ": " + typeArea.getText();
				
				output.println(username + ": " + typeArea.getText());
				output.flush();
				
				dtm.addRow(obj);
				typeArea.setText("");
				
				updateScroll();
			}
			
			/*int rownum = dtm.getRowCount();
			DefaultTableCellRenderer rightRender = new DefaultTableCellRenderer();
			rightRender.setHorizontalAlignment(SwingConstants.RIGHT);
			chatArea.getColumnModel().getColumn(rownum-1).setCellRenderer(rightRender);*/
		}
	}
	
	class typeAreaListener implements KeyListener{
		@Override
		public void keyPressed(KeyEvent e) {
			chatScroll.revalidate();
			switch(e.getKeyCode()){
				case KeyEvent.VK_ENTER:
					if(!typeArea.getText().equals("")){
						Object[] obj = new Object[1];
						obj[0] = username + ": " + typeArea.getText();
						
						output.println(username + ": " + typeArea.getText());
						output.flush();
						
						dtm.addRow(obj);
						typeArea.setText("");
						
						updateScroll();
					}else{
						typeArea.setText("");
					}
					break;
				default:
					break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {}
		@Override
		public void keyTyped(KeyEvent e) {}
	}
	
	class clientThread extends Thread{
		//class variables
		Socket s;
		Scanner threadInput;
		PrintWriter threadOutput;
		String message = "";
		
		//constructor
		public clientThread(Socket s){
			this.s = s;
		}
		
		public void run(){
			//TODO: impl thread things
			try{
				try{
					threadInput = new Scanner(s.getInputStream());
					threadOutput = new PrintWriter(s.getOutputStream());
					threadOutput.flush();
					
					while(true){
						if(threadInput.hasNext()){
							message = threadInput.nextLine();
							
							//might not need this if statement since server will handle all
							//special cases where you only want to chat to a specific person
							//if(!message.startsWith("/")){
								Object[] obj = new Object[1];
								obj[0] = message;
								dtm.addRow(obj);
								updateScroll();
							//}else{
							//}
						}
					}
				}finally{
					s.close();
				}
			}catch(IOException ioe){
				System.out.println("IOException in client thread");
			}
		}
	}

}
