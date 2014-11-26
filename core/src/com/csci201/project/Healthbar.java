package com.csci201.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;

public class Healthbar implements Runnable{
	private NinePatch loadingHealthGreen;
	private int health;
	
	public Healthbar(){
		loadingHealthGreen = new NinePatch(new Texture(Gdx.files.internal("data/health.png")), 9, 9, 9, 9);
		
		//health = 100;
		health = 50; //temporary default
		Thread t = new Thread(this);
		t.start();

	}
	
	public NinePatch getBar(){
		return loadingHealthGreen;
	}
	
	public void addHealth(){
		if(health + 10 <= 100){
			health += 10;
		}
		else{
			health = 100;
		}
	}
	public void run(){
		while(true){
			try {
				Thread.sleep(100);
				if(health < 100){	
					//health += 1;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}

	public int getHealth(){
		return health;

	}

	public void setHealth(int i){
		health += i;
	}


}
