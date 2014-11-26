package com.csci201.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;

public class Healthbar implements Runnable{
	private NinePatch loadingHealthGreen;
	private int health;
	private int healthUpTo;
	
	private Thread t;
	
	public Healthbar(){
		loadingHealthGreen = new NinePatch(new Texture(Gdx.files.internal("data/health.png")), 9, 9, 9, 9);
		
		//health = 100;
		health = 50; //temporary default
		healthUpTo = health;
		t = new Thread(this);
		t.start();

	}
	
	public NinePatch getBar(){
		return loadingHealthGreen;
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

		healthUpTo = health + i;
	}


}
