package com.csci201.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Character extends Sprite implements InputProcessor{

	static FileHandle characterFileHandle = Gdx.files.internal("data/reindeer.png"); 
	private static Texture characterTexture = new Texture(characterFileHandle);
	private int characterX;
	private int characterY;
	float characterSpeed = 150f;
	float amountMoved; 

	/*Boolean data for movement state tracking*/	
	private MoveState down;
	private MoveState right;
	private MoveState left;
	private MoveState up;

	private static int width = characterTexture.getWidth()/3;
	private static int height = characterTexture.getHeight()/4;
	
	Texture img;
	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;

	public Character(){
		super(characterTexture, width, height*2, width, height);
		characterX = 280;
	    characterY = 220;

		//MoveState data
		down = new MoveState();
		right = new MoveState();
		left = new MoveState();
		up = new MoveState();
		
		// camera data
		Gdx.input.setInputProcessor(this);
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
		camera.update();
		tiledMap = new TmxMapLoader().load("map1.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
	}
	
	public void moveChar(String dir){
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		amountMoved = Gdx.graphics.getDeltaTime() * characterSpeed;
		if(dir.equals("A")) {
			if(left.getRightFoot()){
				this.setRegion(0, height*1, width, height);
			}
			else if (left.getLeftFoot()){
				this.setRegion(width*2, height*1, width, height);
			}
			left.changeFoot();
			characterX -= amountMoved - 1;
			camera.translate((int)-amountMoved,0);
		}
		else if(dir.equals("D")){
			if(right.getRightFoot()){
				this.setRegion(0, height*2, width, height);
			}

			else if (right.getLeftFoot()){
				this.setRegion(width*2, height*2, width, height);
			}
			right.changeFoot();
			characterX += amountMoved;
			camera.translate((int)amountMoved,0);
		}
		else if(dir.equals("W")){
			if(up.getRightFoot()){
				this.setRegion(0, height*3, width, height);
			}
			else if (up.getLeftFoot()){
				this.setRegion(width*2, height*3, width, height);
			}
			up.changeFoot();
			characterY += amountMoved;
			camera.translate(0,(int)amountMoved);
		}
		else if(dir.equals("S")){
			if(down.getRightFoot()){
				this.setRegion(0, height*0, width, height);
			}
			else if (down.getLeftFoot()){
				this.setRegion(width*2, height*0, width, height);
			}
			down.changeFoot();
			characterY -= amountMoved - 1;
			camera.translate(0, (int)-amountMoved);  
		}
	}
	
	public void drawChar(SpriteBatch batch){
		camera.update();  
		batch.setProjectionMatrix(camera.combined);
		batch.draw(this, (int)characterX, (int)characterY);
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}