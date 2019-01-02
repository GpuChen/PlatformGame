package com.adventure.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.adventure.game.Main;
import com.adventure.game.SystemVariable;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	
		config.resizable =false;
		
		//Base Setting
		config.title = SystemVariable.GAME_TITLE;
		config.width = SystemVariable.WIDTH * SystemVariable.SCALE;
		config.height = SystemVariable.HEIGHT * SystemVariable.SCALE;		
		
		new LwjglApplication(new Main(), config);
		
	}
}
