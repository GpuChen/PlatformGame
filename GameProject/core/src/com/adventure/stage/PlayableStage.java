package com.adventure.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import static com.adventure.game.GlobalVariable.PPM;
import static com.adventure.game.GlobalVariable.WIDTH;

import java.util.Vector;

import static com.adventure.game.GlobalVariable.HEIGHT;
import com.adventure.game.*;
import com.adventure.object.Player;

public class PlayableStage implements Screen {

	// ******** System Object ********
	public Main game;
	public World world;
	public Box2DDebugRenderer b2dr;

	public OrthographicCamera camera;
	public Viewport cameraViewPort;

	// ********** Texture **********
	
	// ********** GameObject **********
	public Player player;

	public BodyDef bdef;
	public Body b2body;
	// public Array<Sprite> Objects; // Manager all object render and update method

	// ********** TileMap **********

	// private TmxMapLoader mapLoader;
	private TiledMap tileMap;
	private OrthogonalTiledMapRenderer mapRenderer;

	// ********** Function **********

	public PlayableStage(Main game) {

		// Main Object initialized
		this.game = game;

		camera = new OrthographicCamera();
		cameraViewPort = new FitViewport(WIDTH / PPM, HEIGHT / PPM, camera);

		world = new World(new Vector2(0, -9.8f), true);
		world.setContactListener(new WorldContactListener());
		b2dr = new Box2DDebugRenderer();

		// Map Loader and image Loading
		// mapLoader = new TmxMapLoader();
		tileMap = new TmxMapLoader().load("map/testWorld/map01.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(tileMap, 1 / PPM);
		camera.setToOrtho(false, cameraViewPort.getWorldWidth(), cameraViewPort.getWorldHeight());
		// camera.position.set(cameraViewPort.getWorldWidth() / 2,
		// cameraViewPort.getWorldHeight() / 2, 0);


		// GameObject Initialized
//		bdef = new BodyDef();
//		bdef.position.set(16 / PPM, 32 / PPM);
//		bdef.type = BodyType.StaticBody;
//		b2body = world.createBody(bdef);
		WorldRender worldRender = new WorldRender(this);
		player = new Player(this);

	}

	public void update(float dt) {
		world.step(1 / 60f, 4, 2);
		inputController(dt);
		player.update(dt);
		//camera.position.x = (float) Math.round(player.b2Body.getPosition().x * 100f) / 100f;

		float camPosition_x = (player.b2Body.getPosition().x - camera.position.x) * 0.1f;
		float camPosition_y = (player.b2Body.getPosition().y - camera.position.y + 0.2f) * 0.1f;
		camera.position.x += (float) Math.round(camPosition_x * 99f) / 99f;
		camera.position.y += ((float) Math.round(camPosition_y * 99f) / 99f);

//		if (Gdx.input.isKeyPressed(Keys.LEFT))
//			camera.position.x -= (float) Math.round(1.5 * dt * 100f) / 100f;
//		if (Gdx.input.isKeyPressed(Keys.RIGHT))
//			camera.position.x += (float) Math.round(1.5 * dt * 100f) / 100f;
//		if (Gdx.input.isKeyPressed(Keys.UP))
//			camera.position.y += (float) Math.round(1.5 * dt * 100f) / 100f;
//		if (Gdx.input.isKeyPressed(Keys.DOWN))
//			camera.position.y -= (float) Math.round(1.5 * dt * 100f) / 100f;

		camera.update();
		mapRenderer.setView(camera);

	}

	@Override
	public void render(float delta) {
		update(delta);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		mapRenderer.render();

		if (game.debug)
			b2dr.render(world, camera.combined);

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		player.draw(game.batch);
		game.batch.end();
		
	}

	@Override
	public void show() {

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {
		game.pause();
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

		world.dispose();
		b2dr.dispose();

		tileMap.dispose();
		mapRenderer.dispose();
		
	}

	public World getWorld() {
		return world;
	}

	public TiledMap getMap() {
		return tileMap;
	}

	public void inputController(float dt) {

		if (Gdx.input.isKeyJustPressed(Keys.UP)) {
			player.b2Body.applyLinearImpulse(new Vector2(0, 3.5f), player.b2Body.getWorldCenter(), true);
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT) && player.b2Body.getLinearVelocity().x >= -1f) {
			//player.b2Body.applyLinearImpulse(new Vector2(1f, 0), player.b2Body.getWorldCenter(), true);
			player.b2Body.setLinearVelocity(1.25f, player.b2Body.getLinearVelocity().y);
		} else if (Gdx.input.isKeyPressed(Keys.LEFT) && player.b2Body.getLinearVelocity().x >= -1f) {
			//player.b2Body.applyLinearImpulse(new Vector2(-1f, 0), player.b2Body.getWorldCenter(), true);
			player.b2Body.setLinearVelocity(-1.25f, player.b2Body.getLinearVelocity().y);
		} else if (!Gdx.input.isKeyPressed(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.LEFT) ){
			player.b2Body.setLinearVelocity(0, player.b2Body.getLinearVelocity().y);
		}
		
		
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}
}
