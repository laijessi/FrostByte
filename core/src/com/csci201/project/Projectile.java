package com.csci201.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
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
	
	private float distance;
	
	private boolean exists;
	
	private Rectangle projectileCollisionBox; 
	
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

		projectileCollisionBox = new Rectangle(posX, posY, this.getWidth(), this.getHeight());
		
		this.posX = shipX;
		this.posY = shipY;

		this.goX = goX;
		this.goY = goY;
		
		this.xDistance = (goX);
		this.yDistance = -1*(goY);
		
		/*System.out.println("STARTX IS : " + shipX);
		System.out.println("STARTY IS : " + shipY);
		
		System.out.println("GO TO X IS : " + goX);
		System.out.println("GO TO Y IS : " + goY);
		
		System.out.println("X DISTANCE IS: " + xDistance);
		System.out.println("Y DISTANCE IS: " + yDistance);
		*/
		
		
		exists = true;
		distance = 0;
		//I used aVector = aVector.nor() here before but for some reason didn't work

		radians = (float) Math.atan(yDistance/xDistance);
		
		//Calculations receive proper snowball angle if projectile is in Quadrants 2 or 3
		
		if(xDistance < 0 && yDistance > 0){
			radians += Math.PI;
		}
		else if(xDistance < 0 && yDistance < 0){
			radians += Math.PI;
		}
	}
	
	public void drawShot(SpriteBatch batch) {
		
		//position = positionBefore + v*t
		
		//Lucky number
		double ACCELERATOR = 7.77;
		
		this.posX += Math.cos(radians) * ACCELERATOR;
		this.posY += Math.sin(radians) * ACCELERATOR;
		
		projectileCollisionBox.setPosition(posX, posY); 
		//System.out.println("X is " + posX  + "Y is " + posY);
		//System.out.println("Away from x by: " + Math.abs(goX-posX));
		//System.out.println("Away from y by: " + Math.abs(goY-posY));
		
		batch.draw(this, posX, posY);
	}

	public boolean detectCollision(MainMap mainMap) {
		//sees if character is touching any collision rectangles
		for(int i = 0; i < mainMap.getProjectileCollisionRects().size; i++) {
			Rectangle mapCollisionBox = mainMap.getProjectileCollisionRects().get(i);
			if (Intersector.overlaps(projectileCollisionBox, mapCollisionBox)) {
				return true; 
			}
		}
		
		return false; 
	}
	
	public float distanceUp() {
		return distance++;
		
	}
	
	public boolean exists(){
		return exists;
	}
	
	public void setExists(boolean b){
		exists = b;
	}
	
}
