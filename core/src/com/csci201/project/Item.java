package com.csci201.project;

import java.io.Serializable;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Item extends Sprite implements Serializable{
	private float startX; 
	private float startY; 
	private String type; 
	private Rectangle itemRect;
	private boolean available;
	
	public Item(int val){
		super(new Texture(Gdx.files.internal("data/item.png")));
		randomItem();
		available = true;
		
		if(val == 0){
			this.startX = 416;
			this.startY = 1344;
		}
		else if(val == 1){
			this.startX = 1216;
			this.startY = 608;
		}
		else if(val == 2){
			this.startX = 384;
			this.startY = 256;
		}
		else if(val == 3){
			this.startX = 1472;
			this.startY = 1840; 
		}
		else if(val == 4){
			this.startX = 800; 
			this.startY = 864;
		}
		else{
			System.out.println("Idek");
		}
		itemRect = new Rectangle(startX, startY, 32, 64);
	}
	public Rectangle getRect(){
		return itemRect;
	}
	public void randomItem(){
		Random ran = new Random(); 
		int val = ran.nextInt(3);
		if(val == 0){
			this.type = "Speed";
		}
		if(val == 1){
			this.type = "Strength";
		}
		if(val == 2){
			this.type = "Health";
		}
		
	}
	public void randomLocation(){
		Random ran = new Random(); 
		int val = ran.nextInt(5);
		if(val == 0){
			this.startX = 416;
			this.startY = 1344;
		}
		if(val == 1){
			this.startX = 1216;
			this.startY = 608;
		}
		if(val == 2){
			this.startX = 384;
			this.startY = 256;
		}
		if(val == 3){
			this.startX = 1472;
			this.startY = 1888; 
		}
		if(val == 4){
			this.startX = 800; 
			this.startY = 864;
		}
		else{
			System.out.println("Idek");
		}
	}
	public String getType(){
		return this.type;
	}
	public float getStartX(){
		return this.startX; 
	}
	public float getStartY(){
		return this.startY;
	}
	public void deactivate(){
	
		available = false;

		
	}
	public boolean isActive(){
		return available;
	}
	public void setAvailable(){
		available = true;
		randomItem();
	}
}
