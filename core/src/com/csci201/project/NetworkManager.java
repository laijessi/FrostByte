package com.csci201.project;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class NetworkManager {

	private String host;
	private int port;
	private Character c;
	private Socket s;
	
	public NetworkManager(Character c){
		port = 12345;
		host = "localhost";
		this.c = c;
		
		connect();
		
	}
	
	public void connect(){
		try{
			
			System.out.println("Connecting");
			s = new Socket(host, port);
			System.out.println("Getting oos");
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			System.out.println("Writing object c");
			oos.writeObject(c.getCharData());
			oos.flush();
			System.out.println("Done connecting");
			
			//s.close();
		
			
		}
		catch(Exception e){
			System.out.println("Exception in client: " + e);
		}
	}
	
	//Happens in "GameplayScreen.render()" which is a while loop in practice
	public void ping(){
	
		
		try {
			System.out.println("Pinging");
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			Character opponent = (Character)ois.readObject();
			System.out.println(opponent.toString());
			
			
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(c);
			oos.flush();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		

	}
	
	
}
