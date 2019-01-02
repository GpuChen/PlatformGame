package com.adventure.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.adventure.game.Main;

public class MenuScreen implements Screen{

	Main game;
	
	private Stage stage;
	
	Label textLabel;
	
	private Table table;
	
	public MenuScreen(Main game) {
		this.game = game;
		
		
		table = new Table();
		table.top();
		table.setFillParent(true);
		
		textLabel = new Label("This is test label!", new LabelStyle(new BitmapFont(),Color.WHITE));
		table.add(textLabel).expandX().padTop(10);
		
		stage = new Stage();
		stage.addActor(table);
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		
		stage.draw();
		game.batch.end();
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
		stage.dispose();
	}

}
