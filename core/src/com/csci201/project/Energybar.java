package com.csci201.project;

public class Energybar implements Runnable {

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

