package com.tapsi.getthetriforce.tools;

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
import com.tapsi.getthetriforce.mainGameClass.GetTheTriforce;
import com.tapsi.getthetriforce.screens.others.PlayScreen;
import com.tapsi.getthetriforce.sprites.enemies.Chicken;
import com.tapsi.getthetriforce.sprites.enemies.Kid;
import com.tapsi.getthetriforce.sprites.tileObjects.Brick;
import com.tapsi.getthetriforce.sprites.enemies.Enemy;
import com.tapsi.getthetriforce.sprites.tileObjects.Door;
import com.tapsi.getthetriforce.sprites.tileObjects.Hole;
import com.tapsi.getthetriforce.sprites.tileObjects.QuestionBlock;

/**
 * Helps to create the gameworld
 */
public class B2WorldCreator {
    private Array<Chicken> chickens;
    private Array<Kid> kids;

    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        //create body and fixture variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground bodies/fixtures
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / GetTheTriforce.PPM, (rect.getY() + rect.getHeight() / 2) / GetTheTriforce.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / GetTheTriforce.PPM, rect.getHeight() / 2 / GetTheTriforce.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //creates the holes
        for(MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / GetTheTriforce.PPM, (rect.getY() + rect.getHeight() / 2) / GetTheTriforce.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / GetTheTriforce.PPM, rect.getHeight() / 2 / GetTheTriforce.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create pipe bodies/fixtures
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / GetTheTriforce.PPM, (rect.getY() + rect.getHeight() / 2) / GetTheTriforce.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / GetTheTriforce.PPM, rect.getHeight() / 2 / GetTheTriforce.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = GetTheTriforce.OBJECT_BIT;
            body.createFixture(fdef);
        }

        //create brick bodies/fixtures
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            new Brick(screen, object);
        }

        //create coin bodies/fixtures
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            new QuestionBlock(screen, object);
        }

        //create the Door at the end
        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            new Door(screen,object);
        }

        //create the Holes in the Ground
        for(MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
            new Hole(screen,object);
        }

        //create all chickens
        chickens = new Array<Chicken>();
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            chickens.add(new Chicken(screen, rect.getX() / GetTheTriforce.PPM, rect.getY() / GetTheTriforce.PPM));
        }

        //create all the kids
        kids = new Array<Kid>();
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            kids.add(new Kid(screen, rect.getX() / GetTheTriforce.PPM, rect.getY() / GetTheTriforce.PPM));
        }


    }


    public Array<Chicken> getChickens() {
        return chickens;
    }
    public Array<Enemy> getEnemies(){
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(chickens);
        enemies.addAll(kids);
        return enemies;
    }

}
