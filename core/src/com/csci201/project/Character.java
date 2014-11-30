
package com.csci201.project;

import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Character extends Sprite implements InputProcessor {
	//static FileHandle characterFileHandle = Gdx.files.internal("data/reindeer.png"); 
	//private static Texture characterTexture = new Texture(characterFileHandle);

	private int width;
	private int height;
	
	private CharacterData charData;
	
	private Energybar energybar;
	
	//main variables
	MainMap mainMap; 
	
	String name;
	
	public String toString(){
		return charData.toString();
	}
	
	public Character(MainMap mainMap, Texture characterTexture, int startX, int startY, String name){
		super(characterTexture, characterTexture.getWidth()/3, characterTexture.getHeight()/2, characterTexture.getWidth()/3, characterTexture.getHeight()/4);
		System.out.println("Character constructor was called");
		
		this.name = name;

		width = characterTexture.getWidth()/3;
		height = characterTexture.getHeight()/4;
		charData = new CharacterData(width, height, startX, startY, name);
		energybar = new Energybar();
		
		// camera data
		Gdx.input.setInputProcessor(this);
		
		//map initialization
		this.mainMap = mainMap; 
	}

	public float moveChar(String dir) {
		float amountMoved = Gdx.graphics.getDeltaTime() * charData.getCharacterSpeed();
		
		if(dir.equals("A")) {
			if(charData.getLeft().getRightFoot()){
				this.setRegion(0, height*1, width, height);
			}
			
			else if (charData.getLeft().getLeftFoot()){
				this.setRegion(width*2, height*1, width, height);
			}
			
			charData.getLeft().changeFoot();
			if(detectCollision(-amountMoved, 0)) {
				amountMoved = 0;			
			}
			charData.addX(-amountMoved);
		}
		
		else if(dir.equals("D")){
			if(charData.getRight().getRightFoot()){
				this.setRegion(0, height*2, width, height);
			}

			else if (charData.getRight().getLeftFoot()){
				this.setRegion(width*2, height*2, width, height);
			}
			
			charData.getRight().changeFoot();
			if(detectCollision(amountMoved, 0)) {
				amountMoved = 0;
			}
			charData.addX(amountMoved);
		}
		else if(dir.equals("W")){
			if(charData.getUp().getRightFoot()){
				this.setRegion(0, height*3, width, height);
			}
			
			else if (charData.getUp().getLeftFoot()){
				this.setRegion(width*2, height*3, width, height);
			}
			
			charData.getUp().changeFoot();
			if(detectCollision(0, amountMoved)) {
				amountMoved = 0;			
			}
			charData.addY(amountMoved);
		}
		else if(dir.equals("S")){
			if(charData.getDown().getRightFoot()){
				this.setRegion(0, height*0, width, height);
			}
			
			else if (charData.getDown().getLeftFoot()){
				this.setRegion(width*2, 0, width, height);
			}
			
			charData.getDown().changeFoot();
			if(detectCollision(0, -amountMoved)) {
				amountMoved = 0;
			}
			charData.addY(-amountMoved);
		}
		charData.getCharacterCollisionBox().setPosition(charData.getX(), charData.getY()); 
		charData.getCharacterHitBox().setPosition(charData.getX(), charData.getY());
		
		charData.setRegionX(this.getRegionX());
		charData.setRegionY(this.getRegionY());
		
		//Item detect 		
		detectItem();
		
		return amountMoved;
	}
	
	private void detectItem(){
		if(charData.isItemActive()){
			if(System.currentTimeMillis() >= charData.getStartTime() + 5000){
					charData.setItemActive(false);
				if(charData.getCurrItem().getType().equals("Health")){
					charData.getCurrItem().setAvailable();
				}
				if(charData.getCurrItem().getType().equals("Strength")){
					charData.getCurrItem().setAvailable();
				}
				if(charData.getCurrItem().getType().equals("Speed")){
					charData.setCharacterSpeed(charData.getCharacterSpeed()/2);
					charData.getCurrItem().setAvailable();
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
					charData.setStartTime(System.currentTimeMillis());
					if(powerType.equals("Strength")){
						//Sound strength = Gdx.audio.newSound(Gdx.files.internal("strength.mp3"));
						//strength.play(1f);
						charData.setCurrItem(mainMap.getItemList().get(val));
						charData.setItemActive(true);
						
					}
					if(powerType.equals("Speed")){
						//Sound speed = Gdx.audio.newSound(Gdx.files.internal("speed.mp3"));
						//speed.play(1f);
						charData.setCurrItem(mainMap.getItemList().get(val));
						charData.setItemActive(true);
						charData.setCharacterSpeed(charData.getCharacterSpeed()*2);
					}
					if(powerType.equals("Health")){
						//Sound health = Gdx.audio.newSound(Gdx.files.internal("health.mp3"));
						//health.play(1f);
						charData.addHealth(10); //add 10 
						charData.setCurrItem(mainMap.getItemList().get(val));
						charData.setItemActive(true);
					}
				}	
			}
		}	
	}
	
	public boolean detectCollision(float x,float y) {
		//sees if character is touching any collision rectangles
		Rectangle nextBox = charData.getCharacterCollisionBox(); 
		nextBox.setPosition(charData.getCharacterCollisionBox().getX() + x, charData.getCharacterCollisionBox().getY() + y);
		for(int i = 0; i < mainMap.getCollisionRects().size(); i++) {
			Rectangle mapCollisionBox = mainMap.getCollisionRects().get(i);
			if (Intersector.overlaps(nextBox, mapCollisionBox)) {
				return true; 
			}
		}
		
		return false; 
	}
	
	private int gotItem(){
		//sees if character is touching any items 
		for(int i = 0; i < mainMap.getItemRects().size(); i++){
			Rectangle mapItemBox = mainMap.getItemRects().get(i);
			if(Intersector.overlaps(charData.getCharacterCollisionBox(), mapItemBox)){
				return i;
			}
		}
		return -1;
	}
	
	public boolean checkDamage(Projectile p){
		if(Intersector.overlaps(charData.getCharacterHitBox(), p.getColBox())){
			if(charData.getHealth() - 5 > 0){
				charData.addHealth(-5);
				System.out.println("My health got decreased. New health: " + charData.getHealth() );
				//return true;
			}
			else if(charData.getHealth() - 5 <= 0){
				charData.addHealth(-charData.getHealth());
				System.out.println("My health got decreased to 0. New health: " + charData.getHealth() );
			}
			if(charData.getHealth() == 0){
				System.out.println("My health is 0, game over");
			}
			return true;
		}
		else{
			return false;
			//System.out.print("I was not hit. ");
			//System.out.println(" This was projectiles x and y: " + p.getX() + p.getY());
		}
	}
	
	public float getCharacterX(){
		return charData.getX();
	}
	
	public float getCharacterY(){
		return charData.getY();
	}
	
	public Queue<Projectile> getProjectiles(){
		return charData.getProjectiles();
	}
	
	public void addProjectile(Projectile p){
		charData.addProjectile(p);
	}
	
	public int getEnergy(){
		return charData.getEnergy();
	}
	
	public int getHealth(){
		return charData.getHealth();
	}
	
	public Energybar getEnergybar(){
		return energybar;
	}
	
	public CharacterData getCharData(){
		return charData;
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

	public void setCharacterData(CharacterData cd) {
		
		if(!(cd == null)){
			
			charData = cd;
			
			//System.out.println(charData.toString());
	
		}
		
		
		
	}

	
}
