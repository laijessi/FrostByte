package com.csci201.project;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Item extends Sprite{
	static FileHandle itemFileHandle = Gdx.files.internal("data/item.png"); //whatever shit it is  
	private static Texture itemTexture = new Texture(itemFileHandle);
	private float startX; 
	private float startY; 
	private int increaseFactor;
	private String type; 

	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;
	public Item(){

		super(itemTexture);
		tiledMap = new TmxMapLoader().load("map1.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		randomItem();
		randomLocation();
		System.out.println("Was drawn here " + startX + " " + startY);
		this.increaseFactor = 2;
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
	public void drawItem(SpriteBatch batch){
		batch.draw(this, (int)startX, (int)startY);
	}
}
