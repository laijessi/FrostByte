package com.csci201.project.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.csci201.project.GameplayScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "FrostByte";
		config.useGL30 = false;
		config.resizable = false;
		
		new LwjglApplication(new GameplayScreen(), config);
	}
}

