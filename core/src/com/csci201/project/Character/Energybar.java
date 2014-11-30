package com.csci201.project.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;

public class Energybar implements Runnable {
	private NinePatch loadingEnergyBlue;
	private int energy;

	public Energybar(){
		loadingEnergyBlue = new NinePatch(new Texture(Gdx.files.internal("data/energy.png")), 9, 9, 9, 9);
		
		energy = 100;
		Thread t = new Thread(this);
		t.start();

	}
	public NinePatch getBar(){
		return loadingEnergyBlue;
	}

	public void run(){
		while(true){
			try {
				Thread.sleep(100);
				if(energy < 100){	
					energy++;
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