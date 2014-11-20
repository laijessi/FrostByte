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
	private Texture marioTexture;
	private Sprite mario;
	private int marioX;
	private int marioY;
	float marioSpeed = 150f;
	
	/*Boolean data for movement state tracking*/	
	private MoveState down;
	private MoveState right;
	private MoveState left;
	private MoveState up;
	
	private int width;
	private int height;
	
	@Override
	public void create() {
	    batch = new SpriteBatch();
	    FileHandle marioFileHandle = Gdx.files.internal("santa.png"); 
	    marioTexture = new Texture(marioFileHandle);
	    width = marioTexture.getWidth()/3;
	    height = marioTexture.getHeight()/4;
	    mario = new Sprite(marioTexture, width, height*2, width, height);
	    marioX = 0;
	    marioY = 0;
	    
	    //MoveState data
	    down = new MoveState();
	    right = new MoveState();
	    left = new MoveState();
	    up = new MoveState();
	    
	}
	
	@Override
	public void render() {
		   if(Gdx.input.isKeyPressed(Keys.A)) {
			   
			  
			  
		      marioX -= Gdx.graphics.getDeltaTime() * marioSpeed;
		   }
		   
		   if(Gdx.input.isKeyPressed(Keys.D)) {
		      
			   
			   marioX += Gdx.graphics.getDeltaTime() * marioSpeed;
		   
		   }
		   
		   if(Gdx.input.isKeyPressed(Keys.W)) {
		      
			   
			   marioY += Gdx.graphics.getDeltaTime() * marioSpeed;
		   }
		   
		   if(Gdx.input.isKeyPressed(Keys.S)) {
		      
			   if(down.getRightFoot()){
				   mario.setRegion(0, height*2, width, height);
			   }
			   
			   else if (down.getLeftFoot()){
				   mario.setRegion(width*2, height*2, width, height);
				   
			   }

				   
			   down.changeFoot();
			   
			   marioY -= Gdx.graphics.getDeltaTime() * marioSpeed;
		   }
		   
		   Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		   batch.begin();
		   batch.draw(mario, (int)marioX, (int)marioY);
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