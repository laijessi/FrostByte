package com.csci201.project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class ScreenMainMenu implements Screen{
	//class variables
	Game game;
	Stage stage;
	
	private TextButton buttonPlay;
	private TextField fieldHostName;
	
	private int xCen;
	private int yCen;
	
	//constructor
	public ScreenMainMenu(Game g){
		game = g;
		stage = new Stage();
		
		xCen = Gdx.graphics.getWidth()/2;
		yCen = Gdx.graphics.getHeight()/2;
		
		Gdx.input.setInputProcessor(stage);
		
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		buttonPlay = new TextButton("Play", skin);
		buttonPlay.setPosition(xCen-150, yCen-30);
		buttonPlay.setSize(300, 60);
		
		fieldHostName = new TextField("", skin);
		fieldHostName.setPosition(xCen-150, yCen-60);
		fieldHostName.setSize(300, 30);
		
		stage.addActor(buttonPlay);
		stage.addActor(fieldHostName);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 1, 1, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
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
		Gdx.app.debug("FrostByte", "dispose main menu");
	}

}
