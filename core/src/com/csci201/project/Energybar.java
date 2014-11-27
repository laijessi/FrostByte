package com.csci201.project;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;

public class Energybar implements Runnable, Serializable{
	private int energy;

	public Energybar(){
		energy = 100;
		Thread t = new Thread(this);
		t.start();

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

