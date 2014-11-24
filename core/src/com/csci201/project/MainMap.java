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
	private Array<Rectangle> projectileCollisionRects; 
	
	public MainMap (String mapName) {
		mainMap = new TmxMapLoader().load(mapName);
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
	    }
	    
	    collisionObjects = mainMap.getLayers().get("RiverLayer").getObjects();
	    projectileCollisionRects = new Array<Rectangle>();
	    for (int i = 0; i < collisionObjects.getCount(); i++) {
	                RectangleMapObject obj = (RectangleMapObject) collisionObjects.get(i);
	                Rectangle rect = obj.getRectangle();
	                projectileCollisionRects.add(new Rectangle(rect.x, rect.y, rect.width, rect.height));
	    }
	}
	
	public Array<Rectangle> getProjectileCollisionRects() {
		return projectileCollisionRects;
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
