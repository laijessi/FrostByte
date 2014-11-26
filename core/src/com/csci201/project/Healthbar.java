package com.csci201.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;

public class Healthbar implements Runnable{
	private NinePatch loadingHealthGreen;
	private int health;
	private int healthTo;
	
	private Thread t;
	
	public Healthbar(){
		loadingHealthGreen = new NinePatch(new Texture(Gdx.files.internal("data/health.png")), 9, 9, 9, 9);
		
		//health = 100;
		health = 50; //temporary default
		t = new Thread(this);
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
		try{
			
			while(health < healthTo){
				Thread.sleep(100);
				health++;
				System.out.println(health);
				
			}
			
		}
		catch(Exception e){
			
		}
	}

	public int getHealth(){
		return health;

	}

	public void setHealth(int i){
		System.out.println("getting health");
		healthTo = health + i;
		t.run();
		
	}


}
