package com.adventure.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.adventure.game.GlobalVariable;
import com.adventure.game.Main;

public class MenuScreen implements Screen {

	Main game;

	private Stage stage;

	Label textLabel;

	private Table table;
	
	float secondCounter = 0;
	int counter = 0;
	
	
	public MenuScreen(Main game) {
		this.game = game;

		
	}

	public void labelControl() {
		table = new Table();
		table.top();
		table.setFillParent(true);

		//textLabel = new Label("This is test label!", new LabelStyle(new BitmapFont(), Color.WHITE));
		//textLabel = new Label(String.valueOf(counter), new LabelStyle(new BitmapFont(), Color.WHITE));
		textLabel = new Label(String.valueOf(counter), new LabelStyle(new BitmapFont(), Color.WHITE));
		table.add(textLabel).expandX().padTop(10);

		stage = new Stage();
		stage.addActor(table);
	}
	
	public void update(float dt) {
		
		labelControl();
		secondCounter += dt;
		if(secondCounter >= 1) {		
			counter++;
			secondCounter = 0;
		}
		
		
		inputManager(dt);
	}

	public void inputManager(float dt) {

		if (Gdx.input.isKeyPressed(GlobalVariable.buttonLeft)) {
			//System.out.println(GlobalVariable.buttonLeft);
		}
		if (Gdx.input.isKeyPressed(GlobalVariable.buttonRight)) {
			//System.out.println(GlobalVariable.buttonRight);
		}
	}
	
	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		if (!game.paused) {

			// Game Logic Control
			update(delta);

			// Graphic Display
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			game.batch.begin();
			stage.draw();
			game.batch.end();

		}
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
