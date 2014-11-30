package com.csci201.project;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkManager {

	private String host;
	private int port;
	private Socket s;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public NetworkManager(Character c){
		port = 12345;
		host = "localhost";		
	}
	
	public ArrayList<String> connect(){
		try{
			
			System.out.println("Connecting");
			s = new Socket(host, port);
			System.out.println("Getting oos");
			
			while(true){

				ois = new ObjectInputStream(s.getInputStream());
				
				System.out.println("getting data from server");
				Object obj = ois.readObject();
				if (obj instanceof ArrayList<?>){
					@SuppressWarnings("unchecked")
					ArrayList<String> files = (ArrayList<String>) obj;
					return files;
				}
			
			}
		
			
		}
		catch(Exception e){
			System.out.println("Exception in client: " + e);
			e.printStackTrace();
		}
		return null;
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
	
	public void sendGameOver(){
		try {
			oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(new String("Game Over"));
			oos.flush();
			
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
}