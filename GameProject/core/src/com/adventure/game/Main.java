package com.adventure.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.adventure.menu.MenuScreen;
import com.adventure.menu.SplashScreen;
import com.adventure.menu.TitleScreen;
import com.adventure.stage.PlayableStage;
import com.adventure.stage.testScreen;

public class Main extends Game {
	
	public SpriteBatch batch;
	
	public boolean paused;
	public boolean debug;

	
	TitleScreen titleScreen;
	PlayableStage playStage;
	
	
	
	public Texture BLACK_TILE;
	
	@Override
	public void create () {
		
		// Global Part
		
		paused = false;
		debug = true;
		
		// Global Part
		BLACK_TILE = new Texture("img/system/black.png");
		batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
		
		//titleScreen = new TitleScreen(this);
		//setScreen(titleScreen);
		
		//setScreen(new MenuScreen(this));
		
		playStage = new PlayableStage(this, "testWorld/map01.tmx", new Vector2(200, 200), true);
		setScreen(playStage);

		//setScreen(new testScreen(this));
		
	}
	@Override
	public void pause() {
		//super.pause();
		paused = (paused) ? false : true;
		
	}
	
	@Override
	public void render() {

		if(debug) {
			Gdx.graphics.setTitle(GlobalVariable.GAME_TITLE + " FPS:" + Gdx.graphics.getFramesPerSecond());
		}
		super.render();
	}
	
	
	public void dispose() {
		super.dispose();
		batch.dispose();
	}
	

}


