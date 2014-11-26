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
		public ServerThread(Socket s ) {
			this.s = s;
		}
		public void run() {
			try {
				while(true){
					for (int i = 0; i < sockets.size(); i++){
						s = sockets.get(i);
						System.out.println("In server");
						ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
						Character c = (Character)ois.readObject();
						System.out.println(c.toString());
						
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