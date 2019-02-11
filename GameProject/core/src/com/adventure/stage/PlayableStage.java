package com.adventure.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import static com.adventure.game.GlobalVariable.PPM;
import static com.adventure.game.GlobalVariable.WIDTH;

import java.util.ArrayList;
import java.util.List;

import static com.adventure.game.GlobalVariable.*; // Import all static which by using
import com.adventure.game.*;
import com.adventure.object.Player;

public class PlayableStage implements Screen {

	// ******** System Object ********
	public Main game;
	public World world;
	public Box2DDebugRenderer b2dr;

	public OrthographicCamera camera;
	public Viewport cameraViewPort;
	public boolean mapSwitching;
	// ********** Texture **********
	public Stage stage;
	private Image image;

	// ********** GameObject **********
	public Player player;
	public HUD hud;
	public StageActor stageActor;
	// public Array<Sprite> Objects; // Manager all object render and update method

	// ********** TileMap **********

	// private TmxMapLoader mapLoader;
	private TiledMap tileMap;
	private GulaOrthogonalTiledMapRenderer mapRenderer;

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
		mapRenderer = new GulaOrthogonalTiledMapRenderer(tileMap, 1 / PPM);

		camera.setToOrtho(false, cameraViewPort.getWorldWidth(), cameraViewPort.getWorldHeight());

		// camera.position.set(cameraViewPort.getWorldWidth() / 2,
		// cameraViewPort.getWorldHeight() / 2, 0);

		// GameObject Initialized
		mapSwitching = false;
		new WorldRender(this, map);

		try {
			player = new Player(this, playerPosition, playerFacingRight);
		} catch (Error err) {

		}
		portalabled = false;
		mapTo = "";
		teleportTo = null;

		mapName = map.substring(map.lastIndexOf("/") + 1, map.lastIndexOf("."));

		stageActor = new StageActor();
		hud = new HUD(game.batch, this);
		camera.position.set(playerPosition.x / PPM, playerPosition.y / PPM, 0);
	}

	public void update(float dt) {
		// System.out.println(portalabled + ", " + teleportTo + ", " + mapTo);

		world.step(1 / 60f, 4, 2);
		CameraPositionUpdate();

		player.update(dt);

		hud.update(dt);
		camera.update();
		mapRenderer.setView(camera);

	}

	float camPosition_x;
	float camPosition_y;

	public void CameraPositionUpdate() {
		// camera.position.x = (float) Math.round(player.b2Body.getPosition().x * 100f)
		// / 100f;

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && !mapSwitching) {
			camPosition_x = (player.b2Body.getPosition().x - 0.47f - camera.position.x) * 0.05f;
			camPosition_y = (player.b2Body.getPosition().y - camera.position.y + 0.2f) * 0.2f;
		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !mapSwitching) {
			camPosition_x = (player.b2Body.getPosition().x + 0.47f - camera.position.x) * 0.05f;
			camPosition_y = (player.b2Body.getPosition().y - camera.position.y + 0.2f) * 0.2f;
		}else if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1)){
			Gdx.app.log("Stage event", "dlbs");
			stageActor.dialogBlackScreen();
			// TODO: this is for testing, it will be remove.
		}else {
			camPosition_x = (player.b2Body.getPosition().x - camera.position.x) * 0.1f;
			camPosition_y = (player.b2Body.getPosition().y - camera.position.y + 0.2f) * 0.2f;
		}
		// camera.position.x += (float) Math.round(camPosition_x * 99f) / 99f;
		// camera.position.y += ((float) Math.round(camPosition_y * 99f) / 99f);
		camera.position.x += camPosition_x;
		camera.position.y += camPosition_y;

	}

	@Override
	public void render(float delta) {

		update(delta);

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		mapRenderer.render();

		if (game.debug)
			b2dr.render(world, camera.combined);

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		player.draw(game.batch);
		game.batch.end();

		game.batch.setProjectionMatrix(hud.actorStage.getCamera().combined);

		stage.act();
		stage.draw();
		stageActor.update(delta);
		
		/*
		 * for (Stage stage : stages) { stage.act(); stage.draw(); }
		 */

		hud.actorStage.draw();

		if (portalabled && !mapSwitching) {
			if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
				mapSwitch();
				stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f), Actions.delay(0.05f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								game.setScreen(new PlayableStage(game, mapTo, teleportTo, playerFacingRight));
								dispose();
								mapSwitch();
							}
						})));
			}
		}

	}

	@Override
	public void show() {

		// stageActor.dialogBlackScreen();

		// MapChange Stage
		stage = new Stage();
		image = new Image((Texture) ASSETMANAGER.get(WHITESQUARE));
		image.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		image.setColor(Color.BLACK);
		stage.addActor(image);
		mapSwitch();
		stage.addAction(Actions.sequence(Actions.alpha(1), Actions.fadeOut(0.5f), Actions.delay(0.05f),
				Actions.run(new Runnable() {
					@Override
					public void run() {
						mapSwitch();
					}
				})));

		// stage.getRoot().setColor(1, 1, 1, 0);
		// stage.getRoot().addAction(Actions.sequence(Actions.alpha(0),
		// Actions.fadeOut(1.0f), Actions.delay(1)));
	}

	public void mapSwitch() {
		mapSwitching = (mapSwitching) ? false : true;
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
		stage.dispose();
		stageActor.dispose();
		/*
		 * if (!stages.isEmpty()) { for (Stage stage : stages) { stage.dispose(); } }
		 */
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
