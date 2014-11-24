package com.csci201.project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FrostByte extends Game {
	SpriteBatch batch;
	Texture img;
	
	public static final int MAIN_MENU = 1;
	public static final int GAMEPLAY_SCREEN = 2;
	public static final int END_SCREEN = 3;
	
	@Override
	public void create () {
		setScreen(new ScreenMainMenu(this));
	}
}