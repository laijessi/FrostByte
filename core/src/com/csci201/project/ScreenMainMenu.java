package com.csci201.project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class ScreenMainMenu implements Screen{
	//class variables
	Game game;
	
	TextureRegion titleMenu;
	SpriteBatch batch;
	
	private Stage stage;
	private Skin skin;
	private TextButton buttonPlay, buttonExit;
	
	//constructor
	public ScreenMainMenu(Game g){
		game = g;
		
		setUpButtons();
	}
	
	public void setUpButtons(){
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		skin = new Skin();
		Pixmap pixmap = new Pixmap(100, 100, Format.RGBA8888);
		pixmap.setColor(Color.LIGHT_GRAY);
		pixmap.fill();
		
		skin.add("black", new Texture(pixmap));
		
		BitmapFont bfont = new BitmapFont();
		bfont.scale(1);
		skin.add("default", bfont);
		
		TextButtonStyle tbs = new TextButtonStyle();
		tbs.up = skin.newDrawable("black", Color.DARK_GRAY);
		tbs.down = skin.newDrawable("black", Color.DARK_GRAY);
		tbs.checked = skin.newDrawable("black", Color.BLACK);
		tbs.over = skin.newDrawable("black", Color.LIGHT_GRAY);
		
		tbs.font = skin.getFont("default");
		
		buttonPlay = new TextButton("Play", tbs);
		buttonPlay.setPosition(100, 100);
		stage.addActor(buttonPlay);
		
		buttonPlay.addListener(new ChangeListener(){
			public void changed(ChangeEvent event, Actor actor){
				//set a new game screen here
				//g.setScreen(new whatevs());
			}
		});
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(titleMenu, 0, 0);
		batch.end();
		
		stage.act(1000);
		stage.draw();

		/*
		if (time > 1) {
			if (Gdx.input.isKeyPressed(Keys.ANY_KEY) || Gdx.input.justTouched()) {
				game.setScreen(new IntroScreen(game));
			}
		}*/
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		titleMenu = new TextureRegion(new Texture(Gdx.files.internal("mainMenu.jpg")), 0, 0, 480, 320);
		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, 480, 320);
		
		
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
		batch.dispose();
		titleMenu.getTexture().dispose();
		stage.dispose();
		skin.dispose();
	}

}
