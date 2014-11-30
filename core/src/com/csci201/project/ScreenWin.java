package com.csci201.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ScreenWin implements Screen{
	private Label winnerLabel;
	private Skin skin;
	private Stage stage;
	private Table table;
	
	public ScreenWin(){
		initGUIElem();
		Gdx.input.setInputProcessor(stage);
		addGUIElem();
		
	}
	
	public void initGUIElem(){
		stage = new Stage();
		table = new Table();
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		winnerLabel = new Label("You Win!", skin);
	}
	
	public void addGUIElem(){
		table.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		table.add(winnerLabel);
		
		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
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
		
	}

}
