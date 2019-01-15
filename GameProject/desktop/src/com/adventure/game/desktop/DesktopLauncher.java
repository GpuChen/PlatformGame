package com.adventure.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import net.dermetfan.gdx.physics.box2d.Box2DUtils.Settings;

import com.adventure.game.Main;
import com.adventure.game.GlobalVariable;;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		
		
		//config.resizable =false;
		
		//Base Setting
		config.title = GlobalVariable.GAME_TITLE;
		config.width = GlobalVariable.WIDTH * GlobalVariable.SCALE;
		config.height = GlobalVariable.HEIGHT * GlobalVariable.SCALE;		
		
		new LwjglApplication(new Main(), config);
		
	}
}
