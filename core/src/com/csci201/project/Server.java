package com.csci201.project;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Server {
	public static ArrayList<Socket> sockets = new ArrayList<Socket>();
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
		Server server= new Server(12345);
	}

	class ServerThread extends Thread {
		private Socket s;
		private Scanner sc;
		private PrintWriter pw;
		private ObjectOutputStream oos;
		private ObjectInputStream ois;
		private boolean done;
		
		public ServerThread(Socket s ) {
			try {
				this.s = s;
				ois = new ObjectInputStream(s.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			done = false;
			try {
				ois = new ObjectInputStream(s.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void run() {
			try {
				
				while(true){
					if(sockets.size() == 2){
						for(Socket socket : sockets){
							if(!socket.equals(s)){
								oos = new ObjectOutputStream(socket.getOutputStream());
								oos.writeObject(new String("begin"));
								oos.flush();
								done = true;
							}
						}
						if(done) break;
					}
				}
		
				while(true){
					for (int i = 0; i < sockets.size(); i++){
						
						System.out.println("In server");
						
						
						String line = ois.readObject().toString();

						if(!sockets.get(i).equals(s)){
							oos.writeObject(line);
							oos.flush();
						}
					}
				}
			} catch (IOException ioe) {
				System.out.println("IOExceptionin ServerThreadconstructor: " + ioe.getMessage());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}