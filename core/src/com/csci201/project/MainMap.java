package com.csci201.project;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class MainMap {
	private TiledMap mainMap; 
	private TiledMapRenderer  tiledMapRenderer;
	private Array<Rectangle> collisionRects; 
	
	public MainMap (String mapName) {
		mainMap = new TmxMapLoader().load("map1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(mainMap);
        
        setUpCollisionRectangles(); 
	}
	
	private void setUpCollisionRectangles() {
		MapObjects collisionObjects = mainMap.getLayers().get("CollisionLayer").getObjects();
	    collisionRects = new Array<Rectangle>();
	    for (int i = 0; i < collisionObjects.getCount(); i++) {
	                RectangleMapObject obj = (RectangleMapObject) collisionObjects.get(i);
	                Rectangle rect = obj.getRectangle();
	                collisionRects.add(new Rectangle(rect.x, rect.y, rect.width, rect.height));
	                System.out.println("Rectangle " + i);
	                System.out.println("X: " + rect.x + " Y: " + rect.y + " width: " + rect.width + "rect.height: " + rect.height);
	                System.out.println();
	    }
	}
	
	public TiledMap getMap() {
		return mainMap; 
	}
	
	public TiledMapRenderer getMapRenderer() {
		return tiledMapRenderer; 
	}
	
	public Array<Rectangle> getCollisionRects() {
		return collisionRects;
	}
}
