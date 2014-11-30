package com.csci201.project;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.io.*;

import com.csci201.CharacterFiles.CharacterData;

public class Server {
	public static ArrayList<Socket> sockets = new ArrayList<Socket>();
	public static ArrayList<String> charFiles = new ArrayList<String>();
	public Server(int port) {
		try {
			ServerSocket ss= new ServerSocket( port );
			
			while(true) {
				Socket s = ss.accept();
				sockets.add(s);
				ServerThread st= new ServerThread(s);
				st.start();
			}
			
		} catch (IOException ioe) {
			System.out.println("IOExceptionin Server constructor: " + ioe.getMessage());
		}
	}
	
	public static void main(String [] args) {
		charFiles = new ArrayList<String>();
		for (int j = 0; j < 2; j++){
			Random rn = new Random();
			int i = rn.nextInt(4);
			String charFile = "";
			
			if (i == 0){
				charFile = "data/reindeer.png";
			}
			else if (i == 1){
				charFile = "data/penguin.png";
			}
			else if (i == 2){
				charFile = "data/santa.png";
			}
			else if (i == 3){
				charFile = "data/mrsclause.png";
			}
			if( charFiles.size() == 1 && charFiles.get(0).equals(charFile)){
				j--;
			}
			else{
				charFiles.add(charFile);
			}
		}
		
		Server server= new Server(12345);
	}

	class ServerThread extends Thread {
		private Socket s;
		private ObjectOutputStream oos;
		private ObjectInputStream ois;
		private boolean firstTime;
		private CharacterData charFile; 
		public ServerThread(Socket s ) {
			this.s = s;
			firstTime = true;
		}
		public void run() {
			try {
				
				while(true){
					if(sockets.size() == 2 && firstTime){
						for(Socket socket : sockets){
							//keeping opponent and char files in sync
							if(s.equals(sockets.get(0))){ }
							else if (s.equals(sockets.get(1))){
								Collections.swap(charFiles,0,1);
							}
							oos = new ObjectOutputStream(s.getOutputStream());
							oos.writeObject(charFiles);
							oos.flush();
							firstTime = false;
						}
					}
			
					System.out.print(sockets.size());

					if(sockets.size() == 2){
						for (Socket socket : sockets){
							//s is me, socket is other guy 
							if(!socket.equals(s)){
	
								ois = new ObjectInputStream(socket.getInputStream());
								
								Object obj = ois.readObject();
								
								if(obj instanceof CharacterData){
									CharacterData cd = (CharacterData) obj;
									//System.out.println(cd.toString());
									charFile = cd; 
									oos = new ObjectOutputStream(s.getOutputStream());
									oos.writeObject(obj);
									oos.flush();
								}
								else if(obj instanceof String){
									if (obj.toString().equals("Game Over")){
										System.out.println("Got the game over String");
										Connection conn; 
										
										try {
											Class.forName("com.mysql.jdbc.Driver");
											conn = DriverManager.getConnection("jdbc:mysql://localhost/FrostByte", "root", "");
											String userName = charFile.getName(); 
											int win, lose;
											int shotsFired = charFile.getProjectiles().size(); 
											charFile.setEnd(System.currentTimeMillis());
											long timePlayedLong = (charFile.getEnd() - charFile.getStart())/1000; 
											int timePlayed = (int) (timePlayedLong + 0); 
											boolean exists = false; 
											
											if(charFile.getHealth() == 0) {
												win = 0; 
												lose = 1;
											}
											
											else {
												win = 1; 
												lose = 0; 
											}
											
											Statement select = conn.createStatement();
											ResultSet results = select.executeQuery("SELECT * FROM PlayerInfo"); 
											String wins = null, losses = null, timeSpent = null; 
											
											while(results.next()) {
												if(results.getString("Username").equals(userName)) {
													exists = true; 
													wins = results.getString("Wins");
													losses = results.getString("Losses");
													timeSpent = results.getString("Time_Played");
												}
											}
											
											//if user exists in database already
											if(exists) {
												win += Integer.parseInt(wins);
												lose += Integer.parseInt(losses);
												timePlayed += Integer.parseInt(timeSpent);
												
												PreparedStatement insert = conn.prepareStatement("UPDATE PlayerInfo SET Shots_Fired=?, Wins=?, Losses=?, Time_Played=? WHERE UserName=?");
												insert.setString(1, Integer.toString(shotsFired));
												insert.setString(2, Integer.toString(win));
												insert.setString(3, Integer.toString(lose));
												insert.setString(4, Integer.toString(timePlayed));
												insert.setString(5, userName);
												insert.executeUpdate();
											}
											
											//if user is not in database already
											else {
												PreparedStatement insert = conn.prepareStatement("INSERT INTO PlayerInfo (UserName, Wins, Losses, Time_Played, Shots_Fired) VALUES (?, ?, ?, ?, ?)");
												insert.setString(1, userName);
												insert.setString(2, Integer.toString(win));
												insert.setString(3, Integer.toString(lose));
												insert.setString(4, Float.toString(timePlayed));
												insert.setString(5, Integer.toString(shotsFired));
												insert.executeUpdate();
											}
											
										} 
										
										catch (SQLException e) {
											e.printStackTrace();
										}
									}
								}
							}
						}
					}
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}