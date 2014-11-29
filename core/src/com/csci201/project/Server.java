package com.csci201.project;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.io.*;

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
		public ServerThread(Socket s ) {
			this.s = s;
			firstTime = true;
		}
		public void run() {
			try {
				
				while(true){
					if(sockets.size() == 2 && firstTime){
						for(Socket socket : sockets){
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
									oos = new ObjectOutputStream(s.getOutputStream());
									oos.writeObject(obj);
									oos.flush();
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