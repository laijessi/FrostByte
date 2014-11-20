package com.csci201.project;
import javax.swing.plaf.synth.SynthSeparatorUI;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.InputProcessor;

public class Game implements ApplicationListener {
	private SpriteBatch batch;
	private Texture characterTexture;
	private Sprite character;
	private int characterX;
	private int characterY;
	float characterSpeed = 150f;
	
	/*Boolean data for movement state tracking*/	
	private MoveState down;
	private MoveState right;
	private MoveState left;
	private MoveState up;
	
	private int width;
	private int height;
	
	private Projectile p;
	private Texture projectileTexture;
	
	private int projX;
	private int projY;
	
	@Override
	public void create() {
	    batch = new SpriteBatch();
	    FileHandle characterFileHandle = Gdx.files.internal("reindeer.png"); 
	    characterTexture = new Texture(characterFileHandle);
	    width = characterTexture.getWidth()/3;
	    height = characterTexture.getHeight()/4;
	    character = new Sprite(characterTexture, width, height*2, width, height);
	    characterX = 0;
	    characterY = 0;
	    
	    //MoveState data
	    down = new MoveState();
	    right = new MoveState();
	    left = new MoveState();
	    up = new MoveState();
	    
	    
	    //Projectile data
	    FileHandle projectileFileHandle = Gdx.files.internal("projectile.png"); 
		projectileTexture = new Texture(projectileFileHandle);
		   
		p = new Projectile(projectileTexture, projectileTexture.getWidth(), projectileTexture.getHeight());
		   
		projX = 0;
		projY = 0;
	    
	}
	
	@Override
	public void render() {
		   if(Gdx.input.isKeyPressed(Keys.A)) {
			   
			   if(left.getRightFoot()){
				   character.setRegion(0, height*1, width, height);
			   }
			   
			   else if (left.getLeftFoot()){
				   character.setRegion(width*2, height*1, width, height);
				   
			   }
			   
			  left.changeFoot();
			  
		      characterX -= Gdx.graphics.getDeltaTime() * characterSpeed - 1;
		   }
		   
		   if(Gdx.input.isKeyPressed(Keys.D)) {
		      
			   if(right.getRightFoot()){
				   character.setRegion(0, height*2, width, height);
			   }
			   
			   else if (right.getLeftFoot()){
				   character.setRegion(width*2, height*2, width, height);
				   
			   }
			   
			   right.changeFoot();
			   
			   characterX += Gdx.graphics.getDeltaTime() * characterSpeed;
		   
		   }
		   
		   if(Gdx.input.isKeyPressed(Keys.W)) {
		      
			   if(up.getRightFoot()){
				   character.setRegion(0, height*3, width, height);
			   }
			   
			   else if (up.getLeftFoot()){
				   character.setRegion(width*2, height*3, width, height);
				   
			   }
			   
			   up.changeFoot();
			   
			   characterY += Gdx.graphics.getDeltaTime() * characterSpeed;
			   
			   
		   }
		   
		   if(Gdx.input.isKeyPressed(Keys.S)) {
		      
			   if(down.getRightFoot()){
				   character.setRegion(0, height*0, width, height);
			   }
			   
			   else if (down.getLeftFoot()){
				   character.setRegion(width*2, height*0, width, height);
				   
			   }

				   
			   down.changeFoot();
			   
			   characterY -= Gdx.graphics.getDeltaTime() * characterSpeed - 1;
		   }
		   
		  
		   
		   if(Gdx.input.justTouched()){
			   
			   
			   projX = Gdx.input.getX();
			   projY = Gdx.input.getY();
			   
			   
			   projY = Math.abs(Gdx.graphics.getHeight()-projY);
			   
			   p.setPosition(projX, projY);
			   
			   
			  // System.out.println("X: " + x + " Y: " + y);
			   
		   }
		   
		   Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		   batch.begin();
		   batch.draw(character, (int)characterX, (int)characterY);
		   batch.draw(p, projX, projY);
		   
		  
		   batch.end();
		}
	
	
	@Override
	public void resume() {
	}
	
	@Override
	public void resize(int width, int height) {
	}
	
	@Override
	public void pause() {
	}
	
	@Override
	public void dispose() {
	}

}