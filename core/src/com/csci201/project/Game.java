
package com.csci201.project;

import javax.swing.plaf.synth.SynthSeparatorUI;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.InputProcessor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


public class Game extends ApplicationAdapter implements InputProcessor {
	Texture img;
	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;
	private SpriteBatch batch;
	private Texture characterTexture;
	private Sprite character;
	private int characterX;
	private int characterY;
	float characterSpeed = 150f;
	float amountMoved; 

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
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
		camera.update();
		tiledMap = new TmxMapLoader().load("map1.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		Gdx.input.setInputProcessor(this);

		batch = new SpriteBatch();
		FileHandle characterFileHandle = Gdx.files.internal("data/reindeer.png"); 
		characterTexture = new Texture(characterFileHandle);
		width = characterTexture.getWidth()/3;
		height = characterTexture.getHeight()/4;
		character = new Sprite(characterTexture, width, height*2, width, height);
		characterX = 280;
	    characterY = 220;

		//MoveState data
		down = new MoveState();
		right = new MoveState();
		left = new MoveState();
		up = new MoveState();


		//Projectile data
		FileHandle projectileFileHandle = Gdx.files.internal("data/projectile.png"); 
		projectileTexture = new Texture(projectileFileHandle);

		p = new Projectile(projectileTexture, projectileTexture.getWidth(), projectileTexture.getHeight());

		projX = 0;
		projY = 0;

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		if(Gdx.input.isKeyPressed(Keys.A)) {

			if(left.getRightFoot()){
				character.setRegion(0, height*1, width, height);
			}

			else if (left.getLeftFoot()){
				character.setRegion(width*2, height*1, width, height);

			}

			left.changeFoot();

			amountMoved = Gdx.graphics.getDeltaTime() * characterSpeed;
			characterX -= amountMoved - 1;
			camera.translate((int)-amountMoved,0);
		}

		if(Gdx.input.isKeyPressed(Keys.D)) {

			if(right.getRightFoot()){
				character.setRegion(0, height*2, width, height);
			}

			else if (right.getLeftFoot()){
				character.setRegion(width*2, height*2, width, height);

			}

			right.changeFoot();

			amountMoved = Gdx.graphics.getDeltaTime() * characterSpeed;
			characterX += amountMoved;
			camera.translate((int)amountMoved,0);
		}

		if(Gdx.input.isKeyPressed(Keys.W)) {

			if(up.getRightFoot()){
				character.setRegion(0, height*3, width, height);
			}

			else if (up.getLeftFoot()){
				character.setRegion(width*2, height*3, width, height);

			}

			up.changeFoot();

			amountMoved = Gdx.graphics.getDeltaTime() * characterSpeed;
			characterY += amountMoved;
			camera.translate(0, (int)amountMoved);
		}

		if(Gdx.input.isKeyPressed(Keys.S)) {

			if(down.getRightFoot()){
				character.setRegion(0, height*0, width, height);
			}

			else if (down.getLeftFoot()){
				character.setRegion(width*2, height*0, width, height);

			}


			down.changeFoot();

			amountMoved = Gdx.graphics.getDeltaTime() * characterSpeed;
			characterY -= amountMoved - 1;
			camera.translate(0, (int)-amountMoved);  
		}



		if(Gdx.input.justTouched()){


			projX = Gdx.input.getX();
			projY = Gdx.input.getY();


			projY = Math.abs(Gdx.graphics.getHeight()-projY);

			p.setPosition(projX, projY);


			// System.out.println("X: " + x + " Y: " + y);

		}

		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		camera.update();  
		batch.setProjectionMatrix(camera.combined);
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

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

}