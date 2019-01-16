package com.adventure.game;

import com.adventure.object.Player;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {

		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();

		int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
		// x(b) collision with y (a)
//		System.out.println("a:"+ fixA.getUserData());
//		System.out.println("b:"+ fixB.getUserData());
//		System.out.println(cDef);

		// Player movement stop by wall and other object, and setting to not to stick
		// in.
		switch (cDef) {
		case GlobalVariable.PLAYER_BIT | GlobalVariable.OBJECT_BIT:
			break;
		}

	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

}
