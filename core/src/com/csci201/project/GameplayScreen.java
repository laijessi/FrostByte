
package com.csci201.project;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class GameplayScreen extends ApplicationAdapter{

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
}