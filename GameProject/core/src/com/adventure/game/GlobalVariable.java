package com.adventure.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class GlobalVariable{

	////////////////////////////////////////////////////////////////////////////////
	// this sector global variable is for object class or use for Saving events . //
	////////////////////////////////////////////////////////////////////////////////
	
	public static final AssetManager ASSETMANAGER = new AssetManager();

	public static final float PPM = 100;
	//public static Texture WHITESQUARE = new Texture("img/system/white.png");
	public static String GAME_TITLE = "New Game";
	public static int SCALE = 2;
	public static int WIDTH = 320;
	public static int HEIGHT = 200;

	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short OBJECT_BIT = 4;
	public static final short PORTAL_BIT = 8;
	// 3 : 0000 0011
	
	// 6 : 0000 0110
	
	// TODO: setting default, if none of setting before then using default

	public enum InputType {
		Traditional, // right hand to control movement panel, follow by keyboard.
		Arcade, // left hand to control movement panel, follow by arcade.
		Contoller // setting by using controller, for now only xbox360 controller.
	};
	
	public static InputType ControlInputType = InputType.Traditional;
	public static int buttonUp = Keys.UP;
	public static int buttonLeft = Keys.LEFT;
	public static int buttonRight = Keys.RIGHT;
	public static int buttonDown = Keys.DOWN;
	public static int button1; // A
	public static int button2; // B
	public static int button3; // X
	public static int button4; // Y
	public static int button5; // L Button
	public static int button6; // R Button
	public static int button7; // Select Button
	public static int button8; // Start Button

	public static int RTbutton; // default maybe not use this.
	public static int LTbutton; // default maybe not use this.
	
	// Image Name
	public static String WHITESQUARE = "img/system/white.png";
	
}
