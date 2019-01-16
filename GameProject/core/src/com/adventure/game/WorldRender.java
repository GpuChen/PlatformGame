package com.adventure.game;

import com.adventure.stage.PlayableStage;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import static com.adventure.game.GlobalVariable.PPM;
import static com.adventure.game.GlobalVariable.WIDTH;
import static com.adventure.game.GlobalVariable.HEIGHT;

public class WorldRender {

	public World world;
	public TiledMap tileMap;

	BodyDef bdef;
	PolygonShape shape;
	FixtureDef fdef;
	Body body;

	public WorldRender(PlayableStage stage) {
		world = stage.getWorld();
		tileMap = stage.getMap();

//		String MapName = tileMap.getLayers().get("MapInfo").getProperties().get("Name", String.class);

		bdef = new BodyDef();
		shape = new PolygonShape();
		fdef = new FixtureDef();

		for (MapObject obj : getLayerByGroup("ObjectLayer", "Wall").getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) obj).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / PPM, (rect.getY() + rect.getHeight() / 2) / PPM);
			body = world.createBody(bdef);
			shape.setAsBox((rect.getWidth() / 2) / PPM, (rect.getHeight() / 2) / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = GlobalVariable.OBJECT_BIT;
			fdef.friction = 0;
			body.createFixture(fdef).setUserData("wall");

		}

		for (MapObject obj : getLayerByGroup("ObjectLayer", "Platform").getObjects()
				.getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) obj).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / PPM, (rect.getY() + rect.getHeight() / 2) / PPM);
			body = world.createBody(bdef);

			shape.setAsBox((rect.getWidth() / 2) / PPM, (rect.getHeight() / 2) / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = GlobalVariable.GROUND_BIT;
			fdef.friction = 0;
			body.createFixture(fdef).setUserData("platform");

		}

	}

	private MapLayer getLayerByGroup(String group, String layer) {
		try {
			MapGroupLayer groupLayer = ((MapGroupLayer) tileMap.getLayers().get(group));
			return (MapLayer) groupLayer.getLayers().get(layer);
		} catch (Error err) {
			System.out.println("Error by WorldRender \"getLayer\" function.");
			System.out.println("Error message: " + err.toString());
			return null;
		}
	}

	private void DrawRectStaticBodyObject(MapLayer layer, short filter) {
		for (MapObject obj : layer.getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) obj).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / PPM, (rect.getY() + rect.getHeight() / 2) / PPM);
			body = world.createBody(bdef);

			shape.setAsBox((rect.getWidth() / 2) / PPM, (rect.getHeight() / 2) / PPM);
			fdef.shape = shape;
			if (filter != -1)
				fdef.filter.categoryBits = filter;
			body.createFixture(fdef);

		}
//		Original:
//		for (MapObject obj : getLayerByGroup("ObjectLayer", "Wall").getObjects().getByType(RectangleMapObject.class)) {
//			Rectangle rect = ((RectangleMapObject) obj).getRectangle();
//			bdef.type = BodyDef.BodyType.StaticBody;
//			bdef.position.set((rect.getX() + rect.getWidth() / 2) / PPM, (rect.getY() + rect.getHeight() / 2) / PPM);
//			body = world.createBody(bdef);
//			shape.setAsBox((rect.getWidth() / 2) / PPM, (rect.getHeight() / 2) / PPM);
//			fdef.shape = shape;
//			body.createFixture(fdef);
//		}
	}

}
