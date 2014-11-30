package com.csci201.project;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.math.Rectangle;

public class CharacterData implements Serializable{
	/*static FileHandle characterFileHandle = Gdx.files.internal("data/reindeer.png"); 
	private static Texture characterTexture = new Texture(characterFileHandle);
	
	private static int width = characterTexture.getWidth()/3;
	private static int height = characterTexture.getHeight()/4;*/
	
	private float characterX;
	private float characterY;
	float characterSpeed = 200f;
	float amountMoved; 

	/*Boolean data for movement state tracking*/	
	private MoveState down;
	private MoveState right;
	private MoveState left;
	private MoveState up;
	
	private int regionX;
	private int regionY;
	private int width;
	private int height;
	
	private long startTime; //for item 
	private boolean itemActive;
	private Item currItem;
	
	
	private int health;
	private int energy;

	private Queue<Projectile> projectiles;
	
	//collision variables
	private Rectangle characterCollisionBox; 
	private Rectangle characterHitBox; 
	
	public CharacterData(int w, int h, int startX, int startY){
		characterX = startX;
	    characterY = startY;
	    
	    projectiles = new LinkedList<Projectile>();
	    
	    itemActive = false;

		//MoveState data
		down = new MoveState();
		right = new MoveState();
		left = new MoveState();
		up = new MoveState();
		
		
		width = w;
		height = h;
		regionX = w;
		regionY = h+h;
		
		//energy data
		health = 100;
		energy = 100;
		
		//collision initialization
		characterCollisionBox = new Rectangle(characterX, characterY + 2*h/3, w - 10, h/3);	
		characterHitBox = new Rectangle(characterX, characterY, w, h);
	}
	public float getCharacterSpeed(){
		return characterSpeed;
	}
	public void setCharacterSpeed(float num){
		characterSpeed = num;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void setRegionX(int x){
		regionX = x;
	}
	
	public void setRegionY(int y){
		regionY = y;	
	}
	
	public int getRegionX(){
		return regionX;
	}
	
	public int getRegionY(){
		return regionY;
	}
	
	public float getX(){
		return characterX;
	}
	public float getY(){
		return characterY;
	}
	public void addX(float num){
		characterX += num;
	}
	public void addY(float num){
		characterY += num;
	}
	public MoveState getLeft(){
		return left;
	}
	public MoveState getRight(){
		return right;
	}
	public MoveState getUp(){
		return up;
	}
	public MoveState getDown(){
		return down;
	}
	public boolean isItemActive(){
		return itemActive;
	}
	public void setItemActive(boolean b){
		itemActive = b;
	}
	public long getStartTime(){
		return startTime;
	}
	public void setStartTime(long time){
		startTime = time;
	}
	public Item getCurrItem(){
		return currItem;
	}
	public void setCurrItem(Item i){
		currItem = i;
	}
	public int getHealth(){
		return health;
	}
	public void addHealth(int i){
		if(health + i <= 100) {
			health += i;
		}
		
		else if(health + i > 100) {
			health = 100; 
		}
	}
	public int getEnergy(){
		return energy;
	}
	public void setEnergy(int i){
		energy += i;
	}
	public void resetEnergy(int i ){
		energy = i;
	}
	public void addProjectile(Projectile p){
		projectiles.add(p);
		setEnergy(-10);	
	}
	public Queue<Projectile> getProjectiles(){
		return projectiles;
	}

	public String toString(){
		return "X: " + Float.toString(characterX) + "Y: " + Float.toString(characterY);
	}
	public Rectangle getCharacterCollisionBox(){
		return characterCollisionBox;
	}
	public Rectangle getCharacterHitBox() {
		return characterHitBox; 
	}
}
