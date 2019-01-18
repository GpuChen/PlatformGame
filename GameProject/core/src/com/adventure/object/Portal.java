package com.adventure.object;

import com.adventure.game.GlobalVariable;
import com.adventure.stage.PlayableStage;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;

public class Portal extends InteractiveTileObject {

	public boolean portalable;
	public String nextMap;
	public Vector2 teleportTo;
	public Boolean playerFacingRight;
	public PlayableStage stage;

	public Portal(PlayableStage stage, MapObject obj) {
		super(stage, obj);
		this.stage = stage;
		fixture.setUserData(this);
		setCategoryFilter(GlobalVariable.PORTAL_BIT);

		try {
			if (obj.getProperties().containsKey("player_x")) {
				nextMap = obj.getProperties().get("mapTo", String.class);
				teleportTo = new Vector2(obj.getProperties().get("player_x", float.class),
						obj.getProperties().get("player_y", float.class));
				playerFacingRight = obj.getProperties().get("facingRight", boolean.class);
			}
		} catch (Error err) {

		}

	}

	public String toString() {
		return nextMap.substring(nextMap.lastIndexOf("/") + 1, nextMap.lastIndexOf("."));
	}

	public void TpEnabled() {
		if (!nextMap.equals("")) {
			stage.portalabled = true;
			stage.mapTo = nextMap;
			stage.teleportTo = teleportTo;
			stage.playerFacingRight = playerFacingRight;
		}
	}

	public void TpDisabled() {
		stage.portalabled = false;
	}

}
