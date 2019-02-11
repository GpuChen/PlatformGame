package com.adventure.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.adventure.menu.TitleScreen;
import com.adventure.stage.PlayableStage;

import static com.adventure.game.GlobalVariable.*;

public class Main extends Game {

	public SpriteBatch batch;

	public boolean paused;
	public boolean debug;

	TitleScreen titleScreen;
	PlayableStage playStage;

	@Override
	public void create() {

		// img = new Texture("badlogic.jpg");

		// Global Part
		paused = false;
		debug = true;
		batch = new SpriteBatch();

		loadImage();
		ASSETMANAGER.finishLoading(); // to load all image which need to use. (without this will cause Exception)

		playStage = new PlayableStage(this, "testWorld/map01.tmx", new Vector2(200, 200), true);
		setScreen(playStage);
		// titleScreen = new TitleScreen(this);
		// setScreen(titleScreen);

		// setScreen(new MenuScreen(this));
		// setScreen(new testScreen(this));
	}

	@Override
	public void pause() {
		// super.pause();
		paused = (paused) ? false : true;

	}

	@Override
	public void render() {

		if (debug) {
			Gdx.graphics.setTitle(GlobalVariable.GAME_TITLE + " FPS:" + Gdx.graphics.getFramesPerSecond());
		}
		super.render();
	}

	public void loadImage() {
		ASSETMANAGER.load(WHITESQUARE, Texture.class);
	}

	public void dispose() {
		ASSETMANAGER.dispose();
		super.dispose();
		batch.dispose();
	}

}
