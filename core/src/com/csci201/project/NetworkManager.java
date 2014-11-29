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
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
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
			//ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			while(true){

				ois = new ObjectInputStream(s.getInputStream());
				
				System.out.println("getting data from server");
				if (ois.readObject().toString().equals("begin")){
					System.out.println("work");
					break;
				}
			
			}
			
			//s.close();
		
			
		}
		catch(Exception e){
			System.out.println("Exception in client: " + e);
			e.printStackTrace();
		}
	}
	
	public CharacterData pingReceive(){
		
		CharacterData cd = null;
		
		try {
			ois = new ObjectInputStream(s.getInputStream());
			
			Object obj = ois.readObject();
			
			if(obj instanceof CharacterData){
				cd = (CharacterData) obj;
				//System.out.println(cd.toString());
				//CharacterData cd = (CharacterData)ois.readObject();
				//System.out.println(cd.toString());
				//CharacterData opponent = (CharacterData)ois.readObject();
				//System.out.println("Opponent: " + opponent.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return cd;
	}
	
	//Happens in "GameplayScreen.render()" which is a while loop in practice
	public void pingSend(CharacterData cd){
		try {
			oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(cd);
			oos.flush();
			
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		

	}
	
	
}