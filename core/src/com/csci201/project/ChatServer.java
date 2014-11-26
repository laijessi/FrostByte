package com.csci201.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class ChatServer {
	//class variables
	ArrayList<Socket> connections = new ArrayList<Socket>();
	int players = 0;
	boolean started = false;
	public static final int NUM_CONNECTIONS = 2;
	
	//constructor
	public ChatServer(int port){
		try{
			ServerSocket ss = new ServerSocket(port, NUM_CONNECTIONS);
			
			while(true){
				Socket s = ss.accept();
				connections.add(s);
				
				ServerThread st = new ServerThread(s);
				st.start();
			}
		}catch (IOException ioe){
			System.out.println("IOException Server Constructor");
			ioe.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		new ChatServer(6789);
	}
	
	class ServerThread extends Thread{
		//class variables
		private Socket s;
		private Scanner input;
		private PrintWriter output;
		private String message = "";
		
		public ServerThread(Socket s){
			this.s = s;
		}
		
		public void run(){
			try{
				try{
					input = new Scanner(s.getInputStream());
					output = new PrintWriter(s.getOutputStream());
					
					while(true){
						if(!input.hasNextLine()){
							return;
						}
						
						message = input.nextLine();
						//System.out.println(message);
						
						if(!message.startsWith("/")){
							for(int i = 0; i < connections.size(); i++){
								if(connections.get(i) != s){
									Socket tempSock = connections.get(i);
									PrintWriter tempOut = new PrintWriter(tempSock.getOutputStream());
									tempOut.println(message);
									tempOut.flush();
								}
							}
						}else{
							//special case where only sending to one person
						}
					}
				}finally{
					s.close();
				}
			}catch(IOException ioe){
				System.out.println("IOException in server thread");
				ioe.printStackTrace();
			}
		}
	}
}
