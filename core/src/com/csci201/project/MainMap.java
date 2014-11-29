package com.csci201.project;

import java.io.Serializable;
import java.util.ArrayList;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class MainMap implements Serializable{
	private TiledMap mainMap; 
	private TiledMapRenderer  tiledMapRenderer;
	private ArrayList<Rectangle> collisionRects; 
	private ArrayList<Rectangle> itemRects;
	private ArrayList<Item> itemList;
	private ArrayList<Rectangle> projectileCollisionRects; 
	private int [] backgroundLayers = {0,1,2,3,4,5,6,7,8,9,10,11};
	private int [] foregroundLayers = {12};
	
	public MainMap (String mapName) {
		mainMap = new TmxMapLoader().load(mapName);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(mainMap);
        
        setUpCollisionRectangles(); 
	}
	
	public void renderForegroundLayers() {
		tiledMapRenderer.render(foregroundLayers);
	}
	
	public void renderBackgroundLayers() {
		tiledMapRenderer.render(backgroundLayers);
	}
	
	private void setUpCollisionRectangles() {
		MapObjects collisionObjects = mainMap.getLayers().get("CollisionLayer").getObjects();
	    collisionRects = new ArrayList<Rectangle>();
	    for (int i = 0; i < collisionObjects.getCount(); i++) {
	                RectangleMapObject obj = (RectangleMapObject) collisionObjects.get(i);
	                Rectangle rect = obj.getRectangle();
	                collisionRects.add(new Rectangle(rect.x, rect.y, rect.width, rect.height));
	    }
	    
	    collisionObjects = mainMap.getLayers().get("RiverLayer").getObjects();
	    projectileCollisionRects = new ArrayList<Rectangle>();
	    for (int i = 0; i < collisionObjects.getCount(); i++) {
	                RectangleMapObject obj = (RectangleMapObject) collisionObjects.get(i);
	                Rectangle rect = obj.getRectangle();
	                projectileCollisionRects.add(new Rectangle(rect.x, rect.y, rect.width, rect.height));
	    }
	}
	
	public ArrayList<Rectangle> getProjectileCollisionRects() {
		return projectileCollisionRects;
	}
	
	public TiledMap getMap() {
		return mainMap; 
	}
	
	public TiledMapRenderer getMapRenderer() {
		return tiledMapRenderer; 
	}
	
	public ArrayList<Rectangle> getCollisionRects() {
		return collisionRects;
	}
	public void takeItemRects(ArrayList<Rectangle> itemRect){
		this.itemRects = itemRect;
	}
	public void takeItemList(ArrayList<Item> itemList){
		this.itemList = itemList;
	}
	public ArrayList<Rectangle> getItemRects() {
		return itemRects;
	}
	public ArrayList<Item> getItemList(){
		return itemList;
	}
}
