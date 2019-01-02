package com.adventure.stage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.adventure.game.GlobalVariable;
import com.adventure.game.SystemVariable;

public class PlayStage implements Screen{

	
	// ******** Main Object ********
	private Game game;	
	
	private World world;
	private Box2DDebugRenderer b2dr;
	// private Creator creator;
	
	private Camera camera;
	private Viewport cameraViewPort;
	// ********** Texture **********
	
	// ********** TileMap **********
	
	
	
	// ********** Function **********
	public PlayStage(Game game) {
	////// [ Main Object initialized ] //////
		this.game = game;
		camera = new OrthographicCamera();
		cameraViewPort = new FitViewport(
				SystemVariable.WIDTH / GlobalVariable.PPM,
				SystemVariable.HEIGHT / GlobalVariable.PPM,camera);
		
		
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		
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
		
		//world.dispose();
		//b2dr.dispose();
		
	}

	
	
	
}
