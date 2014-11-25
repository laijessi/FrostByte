
package com.csci201.project;


import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


public class GameplayScreen implements Screen{
	private MainMap mainMap; 
	private static SpriteBatch batch;

	private Character character;

	private ArrayList<Character> characters;
	
	
	private int projX;
	private int projY;
	
	
	//private Item item;
	private ArrayList<Item> itemList = new ArrayList<Item>();
	
	Game game;
	
	public GameplayScreen(Game g){
		Sound sound = Gdx.audio.newSound(Gdx.files.internal("soundtrack.mp3"));
		long id = sound.play(.5f);
		sound.setLooping(id,true);
		game = g;
		characters = new ArrayList<Character>();
		create();
	}

	
	public void create() {

		batch = new SpriteBatch();
		mainMap = new MainMap("map3.tmx");
		character = new Character(mainMap);
		for(int i = 0; i < 5; i++){
			Item temp = new Item(i);
			itemList.add(temp);
		}
		mainMap.takeItemList(itemList);
		//Give map my locations
		Array<Rectangle> itemRect = new Array<Rectangle>();
		for(int i = 0; i < itemList.size(); i++){
			itemRect.add(itemList.get(i).getRect());
		}
		mainMap.takeItemRects(itemRect);
		
		
		//Projectile data
	/*	FileHandle projectileFileHandle = Gdx.files.internal("data/projectile.png"); 
		projectileTexture = new Texture(projectileFileHandle);
		//p = new Projectile(projectileTexture, projectileTexture.getWidth(), projectileTexture.getHeight());
		projX = 0;
		projY = 0;
	
*/
		
	}

	@Override
	public void render(float delta) {
		
		
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
		for(int i = 0; i < itemList.size(); i++){
			if(itemList.get(i).isActive()){
				itemList.get(i).drawItem(batch);
			}
		}
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
										character.getCharacterY() - 5));
			}
		}
	
		for(Projectile p : character.getProjectiles()){
			
			if(p.exists()){
				p.drawShot(batch);
			}
			if(p.distanceUp() > 100 || p.detectCollision(mainMap)){
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
		
		character.listen();
		
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public static SpriteBatch getBatch(){
		return batch;
	}
}
