package com.adventure.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import static com.adventure.game.GlobalVariable.PPM;
import static com.adventure.game.GlobalVariable.WIDTH;
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
	public HUD hud;
	// public Array<Sprite> Objects; // Manager all object render and update method

	// ********** TileMap **********

	// private TmxMapLoader mapLoader;
	private TiledMap tileMap;
	private OrthogonalTiledMapRenderer mapRenderer;

	// ********** Map Variable **********
	public boolean portalabled;
	public String mapTo;
	public Vector2 teleportTo;
	public boolean playerFacingRight;
	public String mapName;
	// ********** Function **********

	public PlayableStage(Main game, String map, Vector2 playerPosition, boolean playerFacingRight) {

		// Main Object initialized
		this.game = game;

		camera = new OrthographicCamera();
		cameraViewPort = new FitViewport(WIDTH / PPM, HEIGHT / PPM, camera);

		world = new World(new Vector2(0, -9.81f), true);
		world.setContactListener(new WorldContactListener());
		b2dr = new Box2DDebugRenderer();

		// Map Loader and image Loading
		// mapLoader = new TmxMapLoader();
		tileMap = new TmxMapLoader().load("map/" + map);
		mapRenderer = new OrthogonalTiledMapRenderer(tileMap, 1 / PPM);
		camera.setToOrtho(false, cameraViewPort.getWorldWidth(), cameraViewPort.getWorldHeight());
		// camera.position.set(cameraViewPort.getWorldWidth() / 2,
		// cameraViewPort.getWorldHeight() / 2, 0);

		// GameObject Initialized
		new WorldRender(this, map);

		try {
			player = new Player(this, playerPosition, playerFacingRight);
		} catch (Error err) {

		}
		portalabled = false;
		mapTo = "";
		teleportTo = null;
		
		mapName = map.substring(map.lastIndexOf("/") + 1, map.lastIndexOf("."));
		
		
		hud = new HUD(game.batch, this);
		camera.position.set(playerPosition.x / PPM, playerPosition.y / PPM, 0);
	}

	public void update(float dt) {
		// System.out.println(portalabled + ", " + teleportTo + ", " + mapTo);

		player.update(dt);
		world.step(1 / 60f, 4, 2);
		// camera.position.x = (float) Math.round(player.b2Body.getPosition().x * 100f)
		// / 100f;

		float camPosition_x = (player.b2Body.getPosition().x - camera.position.x) * 0.1f;
		float camPosition_y = (player.b2Body.getPosition().y - camera.position.y + 0.2f);
		camera.position.x += (float) Math.round(camPosition_x * 99f) / 99f;
		camera.position.y += ((float) Math.round(camPosition_y * 99f) / 99f);

		hud.update(dt);

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

		game.batch.setProjectionMatrix(hud.actorStage.getCamera().combined);
		hud.actorStage.draw();

		if (portalabled) {
			if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
				game.setScreen(new PlayableStage(game, mapTo, teleportTo, playerFacingRight));
				dispose();
			}
		}
	}

	@Override
	public void show() {

	}

	@Override
	public void resize(int width, int height) {
		cameraViewPort.update(width, height);
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
	}

	@Override
	public void dispose() {
		hud.dispose();
		tileMap.dispose();
		world.dispose();
		mapRenderer.dispose();
		b2dr.dispose();
	}

	public World getWorld() {
		return world;
	}

	public TiledMap getMap() {
		return tileMap;
	}

}
