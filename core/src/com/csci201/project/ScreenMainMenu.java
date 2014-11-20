package com.csci201.project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class ScreenMainMenu implements Screen{
	//class variables
	Game game;
	
	TextureRegion titleMenu;
	SpriteBatch batch;
	float time = 0;
	
	//constructor
	public ScreenMainMenu(Game g){
		game = g;
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(titleMenu, 0, 0);
		batch.end();

		/*time += delta;
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
	}

}
