package com.adventure.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.adventure.game.Main;

public class TitleScreen implements Screen{

	//TODO: TitleScreen: wait for title image insert
	

    private Texture texture = new Texture("badlogic.jpg");
	private Image image = new Image(texture);
	private Stage stage = new Stage();
	
	Main game;
	MenuScreen menuScreen;
	
	public TitleScreen(Main game) {
		menuScreen = new MenuScreen(game);
		this.game = game;
	}
		
	@Override
	public void show() {
		stage.addActor(image);
		image.addAction(Actions.sequence(
				Actions.alpha(0),
				Actions.fadeIn(1.0f),
				Actions.delay(1),
				Actions.run(new Runnable() {
					@Override
					public void run() {
					 game.setScreen(menuScreen);
					}
				})));
	}

	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		texture.dispose();
		stage.dispose();
	}
	
	
	
}
