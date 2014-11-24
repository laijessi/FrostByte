package com.csci201.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;

public class Energybar implements Runnable {
	private NinePatch startingBackground, loadingEnergyBlue;
	private int energy;

	public Energybar(){
		startingBackground = new NinePatch(new Texture(Gdx.files.internal("data/bar.png")), 9, 9, 9, 9);
		loadingEnergyBlue = new NinePatch(new Texture(Gdx.files.internal("data/energy.png")), 9, 9, 9, 9);
		
		energy = 100;
		Thread t = new Thread(this);
		t.start();

	}

	public void drawBar(){
		startingBackground.draw(GameplayScreen.getBatch(), Character.getCharacterX()-250, Character.getCharacterY()-195, 220, 25);
		loadingEnergyBlue.draw(GameplayScreen.getBatch(), Character.getCharacterX()-248, Character.getCharacterY()-193, energy*2 + 16, 21);
	}

	public void run(){
		while(true){
			try {
				Thread.sleep(100);
				if(energy < 100){	
					energy += 1;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}

	public int getEnergy(){
		return energy;

	}

	public void setEnergy(int i){
		energy += i;
	}


}

