package com.csci201.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;

public class Healthbar implements Runnable{
	private NinePatch startingBackground, loadingHealthGreen;
	private int health;
	
	public Healthbar(){
		startingBackground = new NinePatch(new Texture(Gdx.files.internal("data/bar.png")), 9, 9, 9, 9);
		loadingHealthGreen = new NinePatch(new Texture(Gdx.files.internal("data/health.png")), 9, 9, 9, 9);
		
		health = 100;
		Thread t = new Thread(this);
		t.start();

	}
	
	public void drawBar(){
		startingBackground.draw(GameplayScreen.getBatch(), Character.getCharacterX()-250, Character.getCharacterY()-165, 220, 25);
		loadingHealthGreen.draw(GameplayScreen.getBatch(), Character.getCharacterX()-248, Character.getCharacterY()-163, health*2 + 16, 21);
	}

	
	public void run(){
		while(true){
			try {
				Thread.sleep(100);
				if(health < 100){	
					health += 1;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}

	public int getEnergy(){
		return health;

	}

	public void setEnergy(int i){
		health += i;
	}


}
