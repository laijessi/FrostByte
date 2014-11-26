
package com.csci201.project;

import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Character extends Sprite implements InputProcessor, Serializable{

	static FileHandle characterFileHandle = Gdx.files.internal("data/reindeer.png"); 
	private static Texture characterTexture = new Texture(characterFileHandle);
	private static float characterX;
	private static float characterY;
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
	private Healthbar healthbar;
	
	//main variables
	MainMap mainMap; 
	
	
	//collision variables
	private boolean wentLeft = false, wentRight = false, wentUp = false, wentDown = false;
	private Array<Rectangle> collisionRects; 
	private Rectangle characterCollisionBox; 
	private Array<Rectangle> itemRects;
	private Queue<Projectile> projectiles;
	
	//server variables
	private PrintWriter pw;
	private Scanner sc;
	public Socket s;
	

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
		healthbar = new Healthbar();
		
		// camera data
		Gdx.input.setInputProcessor(this);
		
		
		
		//collision initialization
		characterCollisionBox = new Rectangle(characterX, characterY + 2*this.getHeight()/3, this.getWidth() - 10, this.getHeight()/3);
		
		//map initialization
		this.mainMap = mainMap; 
		
		//connect();
	}
	
	public void listen(){
		
		String line = sc.nextLine();
		System.out.println("In client: ");
		System.out.println(line);
		
		try{
			pw = new PrintWriter(s.getOutputStream());
			pw.println("nothing");
			pw.flush();
		}
		catch(Exception e){}
	}
	
	public void connect(){
		try{
			int port = 12345;
			String host = "127.0.0.1";
			s = new Socket(host, port);

			sc = new Scanner(s.getInputStream());
			String line = sc.nextLine();
			if(line.equals("Begin")){
				pw = new PrintWriter(s.getOutputStream());
				pw.println("sending to server");
				pw.flush();
			}

			
			//close 
			/*s.close();
			sc.close();
			pw.close();*/
			
		}
		catch(Exception e){
			System.out.println("Exception in client: " + e);
		}
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
	
	public Healthbar getHealthbar(){
		return healthbar;
	}
	
	
	public float moveChar(String dir) {
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
			if(detectCollision(-amountMoved, 0)) {
				amountMoved = 0;			
			}
			
			
			characterX -= amountMoved;
				
			
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
			if(detectCollision(amountMoved, 0)) {
				amountMoved = 0;
			}
			
	
			characterX += amountMoved;
			
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
			if(detectCollision(0, amountMoved)) {
				amountMoved = 0;			
			}
			
			characterY += amountMoved;
			
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
			if(detectCollision(0, -amountMoved)) {
				amountMoved = 0;
			}
		
			characterY -= amountMoved;
			
		}
		
		
		
		characterCollisionBox.setPosition(characterX, characterY); 
		//Item detect 		
		detectItem();
		
		return amountMoved;
	}
	
	private void detectItem(){
		if(itemActive){
			if(System.currentTimeMillis() >= startTime + 5000){
					itemActive = false;
				if(currItem.getType().equals("Health")){
					currItem.setAvailable();
				}
				if(currItem.getType().equals("Strength")){
					currItem.setAvailable();
				}
				if(currItem.getType().equals("Speed")){
					characterSpeed /= 2;
					currItem.setAvailable();
				}
			}
		}
		//only get items when you don't have another one 
		else{ 
			int val = gotItem();
			if(val != -1){
				String powerType = mainMap.getItemList().get(val).getType();
				if(mainMap.getItemList().get(val).isActive()){
					mainMap.getItemList().get(val).deactivate();
					startTime = System.currentTimeMillis();
					if(powerType.equals("Strength")){
						//Sound strength = Gdx.audio.newSound(Gdx.files.internal("strength.mp3"));
						//strength.play(1f);
						currItem =  mainMap.getItemList().get(val);
						itemActive = true;
						
					}
					if(powerType.equals("Speed")){
						//Sound speed = Gdx.audio.newSound(Gdx.files.internal("speed.mp3"));
						//speed.play(1f);
						currItem =  mainMap.getItemList().get(val);
						itemActive = true;
						characterSpeed *= 2;
					}
					if(powerType.equals("Health")){
						//Sound health = Gdx.audio.newSound(Gdx.files.internal("health.mp3"));
						//health.play(1f);
						healthbar.setHealth(10); //add 10 
						currItem =  mainMap.getItemList().get(val);
						itemActive = true;
					}
				}	
			}
		}		
	}
	
	public boolean detectCollision(float x,float y) {
		//sees if character is touching any collision rectangles
		Rectangle nextBox = characterCollisionBox; 
		nextBox.setPosition(characterCollisionBox.getX() + x, characterCollisionBox.getY() + y);
		for(int i = 0; i < mainMap.getCollisionRects().size; i++) {
			Rectangle mapCollisionBox = mainMap.getCollisionRects().get(i);
			if (Intersector.overlaps(nextBox, mapCollisionBox)) {
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
