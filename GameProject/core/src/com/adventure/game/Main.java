package com.adventure.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.adventure.menu.SplashScreen;
import com.adventure.menu.TitleScreen;
import com.adventure.stage.PlayStage;

public class Main extends Game {
	
	public SpriteBatch batch;

	TitleScreen titleScreen;
	PlayStage playStage;
	
	
	@Override
	public void create () {

		batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
		
		titleScreen = new TitleScreen(this);
		//setScreen(titleScreen);
		
		//playStage = new PlayStage(this);
		//setScreen(playStage);
	}

	
	@Override
	public void render() {
		super.render();
	}
	
	public void dispose() {
		super.dispose();
		batch.dispose();
	}
}


