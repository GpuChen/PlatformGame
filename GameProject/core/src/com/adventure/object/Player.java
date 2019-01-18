package com.adventure.object;

import com.adventure.game.GlobalVariable;
import com.adventure.stage.PlayableStage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.kotcrab.vis.ui.widget.ListView.UpdatePolicy;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import static com.adventure.game.GlobalVariable.PPM;

import javax.swing.text.Position;

public class Player extends Sprite {

	public enum State {
		IDEL, RUNNING, JUMPING
	}

	public State currState;
	public State prevState;

	public World world;
	public Body b2Body;
	public float width = 30;
	public float height = 50;

	public float hitbox_width = 10;
	public float hitbox_height = 22;
	public float feetBox_width = 6;
	public float feetBox_height = 2;

	public TextureAtlas playerAtlas;

	private float stateTimer;
	private boolean runningRight;
	private TextureRegion idel;
	private Animation<TextureRegion> runBegin;
	private Animation<TextureRegion> runing;
	private Animation<TextureRegion> runStop;

	public float movementSpeed;
	public float animationSpeed;

	public Player(PlayableStage stage, Vector2 position, boolean facingRight) {

		// Animation Setting and Default
		// super(stage.playerAtlas.findRegion("idel"));
		playerAtlas = new TextureAtlas("img/player/Player.atlas");

		currState = State.IDEL;
		prevState = State.IDEL;
		stateTimer = 0;
		runningRight = facingRight;
		animationSpeed = 1f;
		movementSpeed = 1.4f;

		// setting Animation and texture.
		Array<TextureRegion> frames = new Array<TextureRegion>();

		for (int i = 0; i < 8; i++) {
			frames.add(new TextureRegion(playerAtlas.findRegion("run").getTexture(), (i * 50) + 2, 68, 50, 50));
		}
		runing = new Animation<TextureRegion>(0.1f, frames);
		frames.clear();

		idel = new TextureRegion(playerAtlas.findRegion("idel").getTexture(), 132, 16, 30, 50);

		// ---------------------------------
		// setBounds(0, 0, width / PPM, height / PPM);
		setRegion(idel);

		world = stage.getWorld();
		DefindPlayer(world, position);
	}

	public void update(float dt) {
		movementSpeed = 1.0f;
		animationSpeed = 0.7f;
		
		setPosition((b2Body.getPosition().x - getWidth() / 2), (b2Body.getPosition().y - getHeight() / 2));
		setRegion(getFrame(dt * animationSpeed));
		playerMovement(dt);
		// System.out.println(b2Body.getLinearVelocity().x);
	}

	public void playerMovement(float dt) {

		if (Gdx.input.isKeyJustPressed(Keys.UP)) {
			b2Body.applyLinearImpulse(new Vector2(0, 4f), b2Body.getWorldCenter(), true);
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			// b2Body.applyLinearImpulse(new Vector2(0.2f, 0), b2Body.getWorldCenter(),
			// true);
			b2Body.setLinearVelocity(movementSpeed, b2Body.getLinearVelocity().y);
		} else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			// b2Body.applyLinearImpulse(new Vector2(-0.2f, 0), b2Body.getWorldCenter(),
			// true);
			b2Body.setLinearVelocity(-movementSpeed, b2Body.getLinearVelocity().y);
		} else if (!Gdx.input.isKeyPressed(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.LEFT)) {
			b2Body.setLinearVelocity(0, b2Body.getLinearVelocity().y);
		}

		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}

	public TextureRegion getFrame(float dt) {
		currState = getState();
		TextureRegion region;

		switch (currState) {
		case RUNNING:
			region = runing.getKeyFrame(stateTimer, true);
			setBounds(b2Body.getWorldCenter().x - (50 / PPM / 2), b2Body.getWorldCenter().y - (50 / PPM / 2), 50 / PPM,
					50 / PPM);
			break;

		default:
			region = idel;
			setBounds(b2Body.getWorldCenter().x - (30 / PPM / 2), b2Body.getWorldCenter().y - (50 / PPM / 2), 30 / PPM,
					50 / PPM);
			break;
		}

		if ((b2Body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
			region.flip(true, false);
			runningRight = false;
		} else if ((b2Body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
			region.flip(true, false);
			runningRight = true;
		}

		stateTimer = (currState == prevState) ? stateTimer + dt : 0;
		prevState = currState;
		return region;
	}

	public void DefindPlayer(World world, Vector2 position) {
		// hit box part
		BodyDef bdef = new BodyDef();
		bdef.position.set(new Vector2(position.x / PPM, position.y / PPM));
		bdef.type = BodyType.DynamicBody;
		// bdef.type = BodyType.StaticBody;
		b2Body = world.createBody(bdef);

		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(hitbox_width / PPM, hitbox_height / PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = GlobalVariable.PLAYER_BIT;
		fdef.filter.maskBits = GlobalVariable.GROUND_BIT | GlobalVariable.OBJECT_BIT;
		b2Body.createFixture(fdef).setUserData(this);

		// feet box part
		// TODO: seek for with userData by string still can force on this object
		shape.setAsBox(feetBox_width / PPM, feetBox_height / PPM, new Vector2(0, -24 / PPM), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = GlobalVariable.PLAYER_BIT;
		fdef.filter.maskBits = GlobalVariable.GROUND_BIT | GlobalVariable.OBJECT_BIT | GlobalVariable.PORTAL_BIT;
		fdef.isSensor = true;
		b2Body.createFixture(fdef).setUserData("playerFoot");

	}

	public Vector2 getVelocity() {
		return new Vector2(b2Body.getLinearVelocity().x, b2Body.getLinearVelocity().y);
	}

	public Vector2 getPosition() {
		return new Vector2(b2Body.getPosition().x, b2Body.getPosition().y);
	}

	public void facingRight() {
		runningRight = (runningRight) ? false : true;
	}

	public State getState() {

		if (b2Body.getLinearVelocity().x != 0)
			return State.RUNNING;
		else
			return State.IDEL;
	}

}
