package com.adventure.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import static com.adventure.game.GlobalVariable.ASSETMANAGER;
import static com.adventure.game.GlobalVariable.WHITESQUARE;

public class StageActor {

	// Instruction: To manager all actor in this class

	// Initial
	public List<Stage> stages;
	final Stage dialogBlackScreen;

	private Texture WhiteSquare;

	// Event Boolean:
	private boolean dlbsboolean = false;

	public StageActor() {

		stages = new ArrayList<Stage>();
		WhiteSquare = ASSETMANAGER.get(WHITESQUARE);

		int globalHeight = Gdx.graphics.getHeight();
		int globalWidth = Gdx.graphics.getWidth();
		
		
		// dialogBlackScreen class initial:
		dialogBlackScreen = new Stage();
		Image ubImage = new Image(WhiteSquare);
		Image dbImage = new Image(WhiteSquare);
		ubImage.setBounds(0, globalHeight - (globalHeight / 6), globalWidth, globalHeight / 6);
		dbImage.setBounds(0, 0, globalWidth, globalHeight / 6);
		ubImage.setColor(Color.BLACK);
		dbImage.setColor(Color.BLACK);
		dialogBlackScreen.addActor(ubImage);
		dialogBlackScreen.addActor(dbImage);
		dialogBlackScreen.addAction(Actions.alpha(0));

		// List Add event:
		stages.add(dialogBlackScreen);
	}

	public void dialogBlackScreen() {
		// Switcher
		dlbsboolean = (dlbsboolean) ? false : true;
		// Action
		if (dlbsboolean) {
			for (Stage stage : stages) {
				if (stage == dialogBlackScreen) {
					stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));
				}
			}
		} else {
			for (Stage stage : stages) {
				if (stage == dialogBlackScreen) {
					stage.addAction(Actions.sequence(Actions.alpha(1), Actions.fadeOut(0.5f)));
				}
			}
		}
	}

	public void reset() {
		// to clean and reset all event

	}

	public void update(float dt) {
		for (Stage stage : stages) {
			stage.act(dt);
			stage.draw();
		}
	}

	public void dispose() {
		if (!stages.isEmpty()) {
			for (Stage stage : stages) {
				stage.dispose();
			}
		}
	}

	
}
