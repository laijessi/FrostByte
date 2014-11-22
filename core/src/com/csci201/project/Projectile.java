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
	private float vecX, vecY;
	
	
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
	
	public Projectile(float shipX, float shipY, float mouseX, float mouseY) {  
		super(projectileTexture, 0, 0, 30, 30);
		
		this.shotTime = TimeUtils.millis();

		this.posX = shipX;
		this.posY = shipY;

		//I used aVector = aVector.nor() here before but for some reason didn't work
		float tmp = (float) (Math.pow(mouseX-shipX, 2) + Math.pow(mouseY-shipY, 2));
		tmp = (float) Math.sqrt(Math.abs(tmp));

		this.vecX = (mouseX-shipX)/tmp;
		this.vecY = (mouseY-shipY)/tmp;
	}
	
	public void drawShot(SpriteBatch batch) {
		
		//position = positionBefore + v*t
		this.posX = this.posX + 5;
		this.posY = this.posY + 5;
		batch.draw(this, posX, posY);
	}
	
}
