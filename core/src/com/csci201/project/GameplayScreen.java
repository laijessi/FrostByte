package com.csci201.project;


import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


public class GameplayScreen implements Screen{
	private MainMap mainMap; 
	private SpriteBatch batch;

	private Character me;
	private Character opponent;
	private NinePatch startingBackground; 
	private NinePatch loadingHealthGreen;
	private NinePatch loadingEnergyBlue;
	
	private ArrayList<Character> characters;
	
	private int projX;
	private int projY;
	
	//Camera data
	private OrthographicCamera camera;
	private float w = Gdx.graphics.getWidth();
	private float h = Gdx.graphics.getHeight();
	
	//private Item item;
	private ArrayList<Item> itemList = new ArrayList<Item>();
	
	//networkmanager data
	private NetworkManager network;
	
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
		me = new Character(mainMap);
		startingBackground = new NinePatch(new Texture(Gdx.files.internal("data/bar.png")), 9, 9, 9, 9);
		loadingHealthGreen = new NinePatch(new Texture(Gdx.files.internal("data/health.png")), 9, 9, 9, 9);
		loadingEnergyBlue = new NinePatch(new Texture(Gdx.files.internal("data/energy.png")), 9, 9, 9, 9);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
		camera.update();
		
		for(int i = 0; i < 5; i++){
			Item temp = new Item(i);
			itemList.add(temp);
		}
		mainMap.takeItemList(itemList);
		//Give map my locations
		ArrayList<Rectangle> itemRect = new ArrayList<Rectangle>();
		for(int i = 0; i < itemList.size(); i++){
			itemRect.add(itemList.get(i).getRect());
		}
		mainMap.takeItemRects(itemRect);
		
		
		network = new NetworkManager(me);
		
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
		
		network.pingSend(me.getCharData());
		
		network.pingReceive();
		
		setCamera();
		
		moveChar();
		
		batch.begin();

		drawItems();
				
		drawChar(me);
		
		shootProjectile();	
		
		drawProjectiles();

		batch.end();
		
		
		
	}
	
	public void drawProjectiles(){
		for(Projectile p : me.getProjectiles()){
			
			if(p.exists()){
				double ACCELERATOR = 7.77;
				
				p.setX( Math.cos(p.getRadians()) * ACCELERATOR );
				p.setY( Math.sin(p.getRadians()) * ACCELERATOR );
				
				p.getColBox().setPosition(p.getX(), p.getY()); 

				batch.draw(p, p.getX(), p.getY());
			}
			if(p.distanceUp() > 100 || p.detectCollision(mainMap)){
				p.setExists(false);
			}
		}
	}
	
	public void shootProjectile(){
		if(Gdx.input.justTouched()){
			projX = Gdx.input.getX();
			projY = Gdx.input.getY();
		
			
			//TODO: 320 and 240 are hard-coded to allow the projectile to 
		    //interpret the current map's origin as (0,0), find the actual
		    //dimensions of the height and width of the screen and divide by 2
		    
			//System.out.println(Gdx.graphics.getWidth());
		    
			if(me.getEnergy() >= 10){
				me.addProjectile( new Projectile(projX - w/2,
										projY-h/2,
										me.getCharacterX(),
										me.getCharacterY() - 5));
			}
		}
	}

	public void drawItems(){
		
		for(Item i : itemList){			
			if(i.isActive()){
				batch.draw(i, (int)i.getStartX(), (int)i.getStartY());		
			}
		}	
	}
	
	public void moveChar(){
		float amountMoved;// = Gdx.graphics.getDeltaTime() * 200f;
		
		if(Gdx.input.isKeyPressed(Keys.A)) {
			amountMoved = me.moveChar("A");
			camera.translate((float)-amountMoved,0);							
		}
		if(Gdx.input.isKeyPressed(Keys.D)) {
			amountMoved = me.moveChar("D");
			camera.translate((float)amountMoved,0);
			
		}
		if(Gdx.input.isKeyPressed(Keys.W)) {
			amountMoved = me.moveChar("W");
			camera.translate(0,(float)amountMoved);
		}
		

		if(Gdx.input.isKeyPressed(Keys.S)) {
			amountMoved = me.moveChar("S");
			camera.translate(0, (float)-amountMoved); 		
		}
	}
	
	public void setCamera(){
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		mainMap.getMapRenderer().setView(camera);
		mainMap.getMapRenderer().render();
	}
	
	public void drawChar(Character c){
		camera.update();  
		batch.setProjectionMatrix(camera.combined);
		batch.draw(c, c.getCharacterX(), c.getCharacterY());
		drawEnergybar(c);
		drawHealthbar(c);
	}
	
	public void drawEnergybar(Character c){
		startingBackground.draw(batch, c.getCharacterX()-250, c.getCharacterY()-195, 220, 25);
		loadingEnergyBlue.draw(batch, c.getCharacterX()-248, c.getCharacterY()-193, c.getEnergy()*2 + 16, 21);
	}
	
	public void drawHealthbar(Character c){
		startingBackground.draw(batch, c.getCharacterX()-250, c.getCharacterY()-165, 220, 25);
		loadingHealthGreen.draw(batch, c.getCharacterX()-248, c.getCharacterY()-163, c.getHealth()*2 + 16, 21);
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
}