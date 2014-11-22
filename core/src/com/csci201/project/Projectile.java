package com.csci201.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class Projectile extends Sprite {

	//private SpriteBatch batch;
	//private Texture projectileTexture;
	private float projectileX;
	private float projectileY;
	private float speed = 200f;
	
	private float width;
	private float height;
	private float shotTime;
	private float lifeTime;
	private float posX, posY;
	private float goX, goY;
	private float xDistance, yDistance;
	
	private float radians;
	
	/*public Projectile(Texture p, int width, int height){
		
		super(p, 0, 0, width, height);
		
		projectileTexture = p;
		
		batch = new SpriteBatch();
	  
	    width = projectileTexture.getWidth();
	    height = projectileTexture.getHeight();
	    
	    projectileX = 0;
	    projectileY = 0;
		
		
	}*/

	static FileHandle projectileFileHandle = Gdx.files.internal("data/projectile.png"); 
	static Texture projectileTexture = new Texture(projectileFileHandle);
	
	
	public Projectile(int mouseX, int mouseY) {  
		super(projectileTexture, 0, 0, 30, 30);
		

		
		
		
		
		
	}
	
	public Projectile(float goX, float goY, float shipX, float shipY) {  
		super(projectileTexture, 0, 0, 30, 30);
		
		this.shotTime = TimeUtils.millis();

		this.posX = shipX;
		this.posY = shipY;

		this.goX = goX;
		this.goY = goY;
		
		this.xDistance = (goX - posX);
		this.yDistance = (goY - posY);
		
		
		//I used aVector = aVector.nor() here before but for some reason didn't work

		radians = (float) Math.atan(yDistance/xDistance);
		
	}
	
	public void drawShot(SpriteBatch batch) {
		
		//position = positionBefore + v*t
		int ACCELERATOR = 5;
		
		this.posX += Math.cos(radians) * ACCELERATOR;
		this.posY += Math.sin(radians) * ACCELERATOR;
		
		//System.out.println("Away from x by: " + Math.abs(goX-posX));
		//System.out.println("Away from y by: " + Math.abs(goY-posY));
		
		batch.draw(this, posX, posY);
	}
	
}
