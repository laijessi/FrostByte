
package com.csci201.project;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.InputProcessor;


public class GameplayScreen extends ApplicationAdapter implements InputProcessor{

	private SpriteBatch batch;

	private Character character;

	private int projX;
	private int projY;
	
	public GameplayScreen(){}

	@Override
	public void create() {

		batch = new SpriteBatch();
		character = new Character();

		//Projectile data
	/*	FileHandle projectileFileHandle = Gdx.files.internal("data/projectile.png"); 
		projectileTexture = new Texture(projectileFileHandle);

		//p = new Projectile(projectileTexture, projectileTexture.getWidth(), projectileTexture.getHeight());

		projX = 0;
		projY = 0;
*/
	}

	@Override
	public void render() {
		if(Gdx.input.isKeyPressed(Keys.A)) {
			character.moveChar("A");
		}
		if(Gdx.input.isKeyPressed(Keys.D)) {
			character.moveChar("D");
		}
		if(Gdx.input.isKeyPressed(Keys.W)) {
			character.moveChar("W");
		}

		if(Gdx.input.isKeyPressed(Keys.S)) {
			character.moveChar("S");
		}
		batch.begin();

		character.drawChar(batch);
		//batch.draw(character, (int)characterX, (int)characterY);
		
		/*if(Gdx.input.justTouched()){
			projX = Gdx.input.getX();
			projY = Gdx.input.getY();
			p = new Projectile(characterX, characterY, projX, projY);
			p.drawShot(batch);
			batch.draw(p,projX,projY);
		/*	projX = Gdx.input.getX();
			projY = Gdx.input.getY();


			projY = Math.abs(Gdx.graphics.getHeight()-projY);

			p.setPosition(projX, projY);


			// System.out.println("X: " + x + " Y: " + y);
			//batch.draw(p, projX, projY);
		}
		*/

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