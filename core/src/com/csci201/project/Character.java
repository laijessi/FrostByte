
package com.csci201.project;

import java.util.LinkedList;
import java.util.Queue;

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
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Character extends Sprite implements InputProcessor{

	static FileHandle characterFileHandle = Gdx.files.internal("data/reindeer.png"); 
	private static Texture characterTexture = new Texture(characterFileHandle);
	private float characterX;
	private float characterY;
	float characterSpeed = 200f;
	float amountMoved; 

	/*Boolean data for movement state tracking*/	
	private MoveState down;
	private MoveState right;
	private MoveState left;
	private MoveState up;
	
	private long startTime; //for item 
	private boolean itemActive;
	private Item currItem; 
	
	private static int width = characterTexture.getWidth()/3;
	private static int height = characterTexture.getHeight()/4;
	
	private Energybar energybar;
	
	//main variables
	MainMap mainMap; 
	OrthographicCamera camera;
	
	//collision variables
	private boolean wentLeft = false, wentRight = false, wentUp = false, wentDown = false;
	private Array<Rectangle> collisionRects; 
	private Rectangle characterCollisionBox; 
	private Array<Rectangle> itemRects;
	private Queue<Projectile> projectiles;
	

	public Character(MainMap mainMap){
		super(characterTexture, width, height*2, width, height);
		characterX = 280;
	    characterY = 220;
	    
	    projectiles = new LinkedList<Projectile>();
	    
	    itemActive = false;

		//MoveState data
		down = new MoveState();
		right = new MoveState();
		left = new MoveState();
		up = new MoveState();
		
		//energy data
		energybar = new Energybar();
		
		// camera data
		Gdx.input.setInputProcessor(this);
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
		camera.update();
		
		//collision initialization
		characterCollisionBox = new Rectangle(characterX, characterY + 2*this.getHeight()/3, this.getWidth(), this.getHeight()/3);
		
		//map initialization
		this.mainMap = mainMap; 
	}
	
	public Rectangle getCollisionRectangle() {
		return characterCollisionBox; 
	}
	
	public void addProjectile(Projectile p){
		projectiles.add(p);
		energybar.setEnergy(-10);
		
	}
	
	public Queue<Projectile> getProjectiles(){
		return projectiles;
	}
	
	public Energybar getEnergybar(){
		return energybar;
	}
	
	
	
	public void moveChar(String dir) {
		amountMoved = Gdx.graphics.getDeltaTime() * characterSpeed;
		
		if(dir.equals("A")) {
			wentLeft = true; 
			
			if(left.getRightFoot()){
				this.setRegion(0, height*1, width, height);
			}
			
			else if (left.getLeftFoot()){
				this.setRegion(width*2, height*1, width, height);
			}
			
			left.changeFoot();
			characterX -= amountMoved;
			camera.translate((float)-amountMoved,0);
		}
		
		else if(dir.equals("D")){
			wentRight = true; 
			
			if(right.getRightFoot()){
				this.setRegion(0, height*2, width, height);
			}

			else if (right.getLeftFoot()){
				this.setRegion(width*2, height*2, width, height);
			}
			
			right.changeFoot();
			characterX += amountMoved;
			camera.translate((float)amountMoved,0);
		}
		else if(dir.equals("W")){
			wentUp = true; 
			
			if(up.getRightFoot()){
				this.setRegion(0, height*3, width, height);
			}
			
			else if (up.getLeftFoot()){
				this.setRegion(width*2, height*3, width, height);
			}
			
			up.changeFoot();
			characterY += amountMoved;
			camera.translate(0,(float)amountMoved);
		}
		else if(dir.equals("S")){
			wentDown = true; 
			
			if(down.getRightFoot()){
				this.setRegion(0, height*0, width, height);
			}
			
			else if (down.getLeftFoot()){
				this.setRegion(width*2, height*0, width, height);
			}
			
			down.changeFoot();
			characterY -= amountMoved;
			camera.translate(0, (float)-amountMoved);  
		}
		
		
		
		characterCollisionBox.setPosition(characterX, characterY); 
		
		//Item detect 
		
		if(itemActive){
			System.out.println("This is start time " +  startTime/1000 + " My time " + System.currentTimeMillis()/1000 );
			if(System.currentTimeMillis() >= startTime + 5000){
				itemActive = false;
				if(currItem.getType().equals("Strength")){

				}
				if(currItem.getType().equals("Speed")){
					characterSpeed /= 2;
					System.out.println("Character speed " + characterSpeed);
					currItem.setAvailable();
				}
			}
			
		}
		int val = gotItem();
		if(val != -1){
			String powerType = mainMap.getItemList().get(val).getType();
			if(mainMap.getItemList().get(val).isActive()){
				mainMap.getItemList().get(val).deactivate();
				startTime = System.currentTimeMillis();
				if(powerType.equals("Strength")){
					
				}
				if(powerType.equals("Speed")){
					currItem =  mainMap.getItemList().get(val);
					itemActive = true;
					characterSpeed *= 2;
					System.out.println("Character speed " + characterSpeed);
				}
				if(powerType.equals("Health")){

				}
			}
			else{
				
			}
			
		}

		if(detectCollision()) {
			if(wentUp) {
				System.out.println("Went Up");
				characterY -= amountMoved;
				camera.translate(0, (float)(-amountMoved)); 
			}

			if(wentDown) {
				System.out.println("Went Down");
				characterY += amountMoved;
				camera.translate(0, (float)(amountMoved)); 
			}

			if(wentRight) {
				System.out.println("Went Right");
				characterX -= amountMoved;
				camera.translate((float)(-amountMoved), 0); 
			}

			if(wentLeft) {
				System.out.println("Went Left");
				characterX += amountMoved;
				camera.translate((float)(amountMoved), 0); 
			}

			wentUp = false; 
			wentDown = false;
			wentLeft = false;
			wentRight = false;
		}
		
	}
	
	private boolean detectCollision() {
		//sees if character is touching any collision rectangles
		for(int i = 0; i < mainMap.getCollisionRects().size; i++) {
			Rectangle mapCollisionBox = mainMap.getCollisionRects().get(i);
			if (Intersector.overlaps(characterCollisionBox, mapCollisionBox)) {
				return true; 
			}
		}
		
		return false; 
	}
	private int gotItem(){
		//sees if character is touching any items 
		for(int i = 0; i < mainMap.getItemRects().size; i++){
			Rectangle mapItemBox = mainMap.getItemRects().get(i);
			if(Intersector.overlaps(characterCollisionBox, mapItemBox)){
				return i;
			}
		}
		return -1;
	}
	
	public void setChar(){
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		mainMap.getMapRenderer().setView(camera);
		mainMap.getMapRenderer().render();
	}
	
	public void drawChar(SpriteBatch batch){
		camera.update();  
		batch.setProjectionMatrix(camera.combined);
		batch.draw(this, characterX, characterY);
	}
	
	public float getCharacterX(){
		return characterX;
	}
	
	public float getCharacterY(){
		return characterY;
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
