package com.adventure.object;

import com.adventure.stage.PlayableStage;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import static com.adventure.game.GlobalVariable.PPM;

public abstract class InteractiveTileObject {

	
	protected World world;
	protected TiledMap map;
	protected TiledMapTile tile;
	protected Rectangle bounds;
	protected MapObject obj;
	protected Body body;
	protected PlayableStage stage;
	
	protected Fixture fixture;
	
	
	public InteractiveTileObject(PlayableStage stage, MapObject obj) {
		this.stage = stage;
		this.world = stage.getWorld();
		this.map = stage.getMap();
		this.bounds = ((RectangleMapObject) obj).getRectangle();
		this.obj = obj;
		
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		
		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / PPM,
						(bounds.getY() + bounds.getHeight() / 2) / PPM);
		body = world.createBody(bdef);
		shape.setAsBox((bounds.getWidth()/2) / PPM, (bounds.getHeight()/2) / PPM);
		fdef.shape = shape;
		//body.createFixture(fdef);
		fixture = body.createFixture(fdef);
		
	}
	
	public void setCategoryFilter(short filter_bit) {
		Filter filter = new Filter();
		filter.categoryBits = filter_bit;
		fixture.setFilterData(filter);
	}
	
	public TiledMapTileLayer.Cell getCell(){
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
		
		return layer.getCell((int) (body.getPosition().x * PPM / 16),
							 (int) (body.getPosition().y * PPM / 16));
	}
	
}
