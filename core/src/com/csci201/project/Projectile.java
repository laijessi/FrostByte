package com.csci201.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Projectile extends Sprite {

	private SpriteBatch batch;
	private Texture projectileTexture;
	private int projectileX;
	private int projectileY;
	private float projectileSpeed = 200f;
	
	private int width;
	private int height;
	
	public Projectile(Texture p, int width, int height){
		
		super(p, 0, 0, width, height);
		
		projectileTexture = p;
		
		batch = new SpriteBatch();
	  
	    width = projectileTexture.getWidth();
	    height = projectileTexture.getHeight();
	    
	    projectileX = 0;
	    projectileY = 0;
		
		
	}
	
}
