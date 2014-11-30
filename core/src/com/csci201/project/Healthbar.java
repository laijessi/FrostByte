package com.csci201.project;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;

public class Healthbar implements Runnable, Serializable{
	private int health;
	private int healthUpTo;
	private int healthDownTo;
	private Lock healthLock;
	
	private Thread t;
	
	public Healthbar(){
		
		//health = 100;
		health = 100; //temporary default
		healthUpTo = health;
		healthDownTo = health;
		healthLock = new ReentrantLock();
		
		t = new Thread(this);
		t.start();

	}
	public void run(){
	/*
		while(true){
			try{

				Thread.sleep(100);
				while(health < healthUpTo){
					health++;
					if(health > healthDownTo){
						healthUpTo = health;
					}
					if(health >= 100){
						health = 100;
					}
				}
				while(health > healthDownTo){
					health--;
					if(health == 0){
						health = 0;
					}
				}

			}
			catch(Exception e){
			}		
		}*/
	}

	public int getHealth(){
		return health;

	}

	public void addHealth(int i){
		System.out.println("I'm adding health " + i);
		if(health + i <= 100) {
			health+=i;
		}
		
		else if(health + i > 100) {
			health = 100; 
		}
		/*healthLock.lock();
		
		if(i > 0){
			healthUpTo = health + i;
		}
		else{
			healthDownTo = health + i;
		}
		
		healthDownTo = health;
		healthUpTo = health;
		
		healthLock.unlock();*/
	}
}
