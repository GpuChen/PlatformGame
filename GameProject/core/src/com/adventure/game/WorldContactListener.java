package com.adventure.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldContactListener implements ContactListener{

	@Override
	public void beginContact(Contact contact) {
		
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		int collisionDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits; 
		// x collision y
		
		
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
