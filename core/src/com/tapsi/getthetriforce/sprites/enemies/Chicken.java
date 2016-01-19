package com.tapsi.getthetriforce.sprites.enemies;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.tapsi.getthetriforce.GetTheTriforce;
import com.tapsi.getthetriforce.screens.PlayScreen;
import com.tapsi.getthetriforce.sprites.link.Link;

/**
 * Creating one of the Enemies - the allmighty Chicken
 * Attention Please - don't mess with the chickens at all
 */


public class Chicken extends Enemy{

    //variables for the animation
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    float angle;


    public Chicken (PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i = 3; i < 5; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("chicken"), i * 16, 0, 16, 16));
        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / GetTheTriforce.PPM, 16 / GetTheTriforce.PPM);
        setToDestroy = false;
        destroyed = false;
        angle = 0;
    }

    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("chicken"), 32, 0, 16, 16));
            stateTime = 0;
        }
        else if(!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / GetTheTriforce.PPM);
        fdef.filter.categoryBits = GetTheTriforce.ENEMY_BIT;
        fdef.filter.maskBits = GetTheTriforce.GROUND_BIT |
                GetTheTriforce.STONE_BIT |
                GetTheTriforce.BRICK_BIT |
                GetTheTriforce.ENEMY_BIT |
                GetTheTriforce.OBJECT_BIT |
                GetTheTriforce.LINK_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 8).scl(1 / GetTheTriforce.PPM);
        vertice[1] = new Vector2(5, 8).scl(1 / GetTheTriforce.PPM);
        vertice[2] = new Vector2(-3, 3).scl(1 / GetTheTriforce.PPM);
        vertice[3] = new Vector2(3, 3).scl(1 / GetTheTriforce.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = GetTheTriforce.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);

    }

    public void draw(Batch batch){
        if(!destroyed || stateTime < 1)
            super.draw(batch);
    }

    @Override
    public void hitOnHead(Link link) {
        setToDestroy = true;
        GetTheTriforce.manager.get("audio/sounds/stomp.wav", Sound.class).play();

    }

    @Override
    public void hitByEnemy(Enemy enemy) {

            reverseVelocity(true, false);
    }


}
