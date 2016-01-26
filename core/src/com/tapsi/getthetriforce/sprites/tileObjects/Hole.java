package com.tapsi.getthetriforce.sprites.tileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.tapsi.getthetriforce.GetTheTriforce;
import com.tapsi.getthetriforce.screens.playscreen.PlayScreen;

/**
 * Creates the holes in the grounds
 */
public class Hole {

    protected GetTheTriforce game;
    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected PlayScreen screen;
    protected MapObject object;

    protected Fixture fixture;
    protected BodyDef bdef;
    protected FixtureDef fdef;
    protected PolygonShape shape;

    public Hole(PlayScreen screen, MapObject object) {
        this.object = object;
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = ((RectangleMapObject) object).getRectangle();

        bdef = new BodyDef();
        fdef = new FixtureDef();
        shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / GetTheTriforce.PPM, (bounds.getY() + bounds.getHeight() / 2) / GetTheTriforce.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / GetTheTriforce.PPM, bounds.getHeight() / 2 / GetTheTriforce.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);

        fixture.setUserData(this);
        setCategoryFilter(GetTheTriforce.HOLE_BIT);

    }
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }


}
