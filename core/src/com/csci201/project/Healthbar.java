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
	
	private Thread t;
	
	public Healthbar(){
		
		//health = 100;
		health = 50; //temporary default
		healthUpTo = health;
		healthDownTo = health;
		
		t = new Thread(this);
		t.start();

	}
	public void run(){
	
		while(true){
			try{

				Thread.sleep(100);
				if(health < healthUpTo){
					health++;
					if(health >= 100){
						health = 100;
					}
				}

			}
			catch(Exception e){
			}		
		}
	}

	public int getHealth(){
		return health;

	}

	public void setHealth(int i){
		if(i > 0){
			healthUpTo = health + i;
		}
		
		/*else if(i < 0){
			if(healthLock.tryLock()){
				for(int j = 0; j > i; j--){
					health--;
					healthLock.unlock();
				}
			}
			else{
				for(int j = 0; j > i; j--){
					health--;
				}
			}
		}*/
	}


}
