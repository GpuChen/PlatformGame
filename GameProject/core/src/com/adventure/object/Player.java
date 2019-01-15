package com.adventure.object;

import com.adventure.game.GlobalVariable;
import com.adventure.stage.PlayableStage;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.kotcrab.vis.ui.widget.ListView.UpdatePolicy;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import static com.adventure.game.GlobalVariable.PPM;
public class Player extends Sprite {
	
	
	public enum State {
		IDEL, 
		RUNNING,
		JUMPING
	}

	public State currState;
	public State prevState;

	public World world;
	public Body b2Body;
	public float width = 40;
	public float height = 50;
	
	public float hitbox_width = 10;
	public float hitbox_height = 24;
	
	public TextureAtlas playerAtlas;

	private float stateTimer;
	private boolean runningRight;
	private TextureRegion idel;
	private Animation<TextureRegion> runBegin;
	private Animation<TextureRegion> runing;
	private Animation<TextureRegion> runStop;

	
	public Player(PlayableStage stage) {
		
		// Animation Setting and Default
		//super(stage.playerAtlas.findRegion("idel"));
		playerAtlas = new TextureAtlas("img/player/Player.atlas");
		
		currState = State.IDEL;
		prevState = State.IDEL;
		stateTimer = 0;
		runningRight = true;
		
		idel = new TextureRegion(playerAtlas.findRegion("idel").getTexture(), 2, 2, 40, 50);
		
		setBounds(0, 0, width / PPM, height / PPM);
		setRegion(idel);
		
		world = stage.getWorld();
		DefindPlayer(world);
	}

	public void update(float dt) {
		setPosition((b2Body.getPosition().x - getWidth() / 2)+0.05f, (b2Body.getPosition().y - getHeight() / 2));
	}
	
	public void DefindPlayer(World world) {
		// abstract part
		BodyDef bdef = new BodyDef();
		bdef.position.set(new Vector2(200/PPM , 200/PPM));
		bdef.type = BodyType.DynamicBody;
		//bdef.type = BodyType.StaticBody;
		b2Body = world.createBody(bdef);
		
		//CircleShape shape = new CircleShape();
		//shape.setRadius(6 / PPM);
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(hitbox_width / PPM, hitbox_height / PPM);
		fdef.shape = shape;
		//fdef.filter.categoryBits = GlobalVariable.OBJECT_BIT;
		//fdef.filter.maskBits = GlobalVariable.GROUND_BIT | GlobalVariable.OBJECT_BIT;

		b2Body.createFixture(fdef);
	}

}
