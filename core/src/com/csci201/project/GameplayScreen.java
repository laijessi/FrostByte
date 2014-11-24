
package com.csci201.project;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


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
		
		character.setChar();
		
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

		//System.out.println("Rendering");
		
		character.drawChar(batch);
		//batch.draw(character, (int)characterX, (int)characterY);
		
		if(Gdx.input.justTouched()){
			projX = Gdx.input.getX();
			projY = Gdx.input.getY();
		
			
			//TODO: 320 and 240 are hard-coded to allow the projectile to 
		    //interpret the current map's origin as (0,0), find the actual
		    //dimensions of the height and width of the screen and divide by 2
		    
			//System.out.println(Gdx.graphics.getWidth());
		    
			if(character.getEnergybar().getEnergy() >= 10){
				character.addProjectile( new Projectile(projX - Gdx.graphics.getWidth()/2,
										projY-Gdx.graphics.getHeight()/2,
										character.getCharacterX(),
										character.getCharacterY()));
			}
		}
	
		for(Projectile p : character.getProjectiles()){
			
			if(p.exists()){
				p.drawShot(batch);
			}
			if(p.distanceUp() > 100){
				p.setExists(false);
			}
		}
			
			
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