package com.csci201.project.Character;

import java.io.Serializable;

public class MoveState implements Serializable{
	
	
	private boolean isLeftFoot;
	private boolean isRightFoot;
	
	private int frameCount;
	
	public MoveState(){

		frameCount = 0;
		isLeftFoot = false;
		isRightFoot = true;
		
	}
	
	
	public void setLeftFoot(boolean b){
		isLeftFoot = b;
	}
	
	public boolean getLeftFoot(){
		return isLeftFoot;
	}
	
	public void setRightFoot(boolean b){
		isRightFoot = b;
	}
	
	public boolean getRightFoot(){
		return isRightFoot;
	}
	
	public void changeFoot(){
		frameCount++;
			
		if(frameCount == 15){
			frameCount = 0;
			isLeftFoot = !isLeftFoot;
			isRightFoot = !isLeftFoot;
		}
	}
}
