package com.adventure.game;

import com.adventure.stage.PlayableStage;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import static com.adventure.game.GlobalVariable.WIDTH;

import com.adventure.object.Player;

import static com.adventure.game.GlobalVariable.HEIGHT;

public class HUD implements Disposable {

	public PlayableStage stage;
	public Stage actorStage;
	private Viewport viewPort;

	private Player player;
	private float playerHealPoint;
	private Vector2 playerVelocity;
	private Vector2 playerPosition;
	private String playerState;
	private String mapName;

	private Label playerVelocityLabel;
	private Label playerPositionLabel;
	private Label playerStateLabel;
	
	private Label var_MovementLabel;
	private Label var_AnimatSpeedLabel;
	
	public HUD(SpriteBatch batch, PlayableStage stage) {

		// Initiation
		this.stage = stage;
		player = stage.player;

		viewPort = new FitViewport(WIDTH, HEIGHT, new OrthographicCamera());
		actorStage = new Stage(viewPort, batch);

		try {
			playerVelocity = player.getVelocity();
			playerPosition = player.getPosition(); 
			playerState = player.getState().toString();
		} catch (Error err) {

		}

		// Object Create

		if (stage.game.debug) {
			Table debugTable = new Table();
			debugTable.setFillParent(true);
			playerVelocityLabel = new Label(String.format("VelX: %.3f, VelY: %.3f", playerVelocity.x, playerVelocity.y),
					new Label.LabelStyle(new BitmapFont(), Color.WHITE));
			playerVelocityLabel.setFontScale(0.5f);
			playerPositionLabel = new Label(String.format("VelX: %.3f, VelY: %.3f", playerPosition.x, playerPosition.y),
					new Label.LabelStyle(new BitmapFont(), Color.WHITE));
			playerPositionLabel.setFontScale(0.5f);

			debugTable.add(playerVelocityLabel);
			debugTable.row();
			debugTable.add(playerPositionLabel);
			debugTable.right().bottom();
			actorStage.addActor(debugTable);
			
			
			Table playerVarTable = new Table();
			playerVarTable.setFillParent(true);
			var_MovementLabel = new Label(String.format("Movement: %.3f", player.movementSpeed),
					new Label.LabelStyle(new BitmapFont(), Color.WHITE));
			var_MovementLabel.setFontScale(0.5f);
			var_AnimatSpeedLabel = new Label(String.format("AnimatedSpeed: %.3f", player.animationSpeed),
					new Label.LabelStyle(new BitmapFont(), Color.WHITE));
			var_AnimatSpeedLabel.setFontScale(0.5f);
			playerStateLabel = new Label(String.format("State: %s", playerState),
					new Label.LabelStyle(new BitmapFont(), Color.WHITE));
			playerStateLabel.setFontScale(0.5f);
			
			playerVarTable.add(playerStateLabel);
			playerVarTable.row();
			playerVarTable.add(var_MovementLabel);
			playerVarTable.row();
			playerVarTable.add(var_AnimatSpeedLabel);
			playerVarTable.left().bottom();
			actorStage.addActor(playerVarTable);
			
		}

	}

	public void update(float dt) {

		playerVelocity = player.getVelocity();
		playerVelocityLabel.setText(String.format("VelX: %.3f, VelY: %.3f", playerVelocity.x, playerVelocity.y));
		playerPosition = player.getPosition();
		playerPositionLabel.setText(String.format("PosX: %.3f, PosY: %.3f", playerPosition.x, playerPosition.y));
		
		playerState = player.getState().toString();
		playerStateLabel.setText(String.format("State: %s", playerState));
		var_MovementLabel.setText(String.format("MovSped: %.2f", player.movementSpeed));
		var_AnimatSpeedLabel.setText(String.format("AniSped: %.2f", player.animationSpeed));
		
	}

	@Override
	public void dispose() {
		actorStage.dispose();
	}

}
