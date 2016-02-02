package com.tapsi.getthetriforce.sprites.link;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.tapsi.getthetriforce.mainGameClass.GetTheTriforce;
import com.tapsi.getthetriforce.screens.completescreens.LevelCompleteScreen;
import com.tapsi.getthetriforce.screens.others.PlayScreen;
import com.tapsi.getthetriforce.sprites.enemies.Enemy;


/**
 * Class where our Main Character is defined
 */

public class Link extends Sprite {




    public enum State { FALLING, JUMPING, STANDING, RUNNING, GROWING, DEAD, COLLIDING };
    public State currentState;
    public State previousState;

    public World world;
    public Body b2body;

    private TextureRegion linkStand;
    private Animation linkRun;
    private TextureRegion linkJump;
    private TextureRegion linkDead;
    private TextureRegion biglinkStand;
    private TextureRegion biglinkJump;
    private Animation biglinkRun;
    private Animation growlink;

    private float stateTimer;
    private boolean runningRight;
    private boolean linkIsBig;
    private boolean runGrowAnimation;
    private boolean timeToDefineBiglink;
    private boolean timeToRedefinelink;
    private boolean linkIsDead;

    private PlayScreen screen;
    private GetTheTriforce game;

    public Link(PlayScreen screen, GetTheTriforce game){
        //initialize default values
        this.game = game;
        this.screen = screen;
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        //get run animation frames and add them to linkRun Animation
        for(int i = 1; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("little_link"), i * 16, 0, 16, 16));
        linkRun = new Animation(0.1f, frames);

        frames.clear();

        //get run animation frames + adding them to the bigLinkRun Animation
        for(int i = 3; i < 6; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("big_link"), i * 32, 0, 32, 32));
        biglinkRun = new Animation(0.1f, frames);

        frames.clear();

        //get set animation frames from growing link
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_link"), 288, 0, 32, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_link"), 0, 0, 32, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_link"), 288, 0, 32, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_link"), 0, 0, 32, 32));
        growlink = new Animation(0.2f, frames);


        //get jump animation frames and add them to linkJump Animation
        linkJump = new TextureRegion(screen.getAtlas().findRegion("little_link"), 64, 0, 16, 16);
        biglinkJump = new TextureRegion(screen.getAtlas().findRegion("big_link"), 64, 0, 32, 32);

        //create texture region for link standing
        linkStand = new TextureRegion(screen.getAtlas().findRegion("little_link"), 0, 0, 16, 16);
        biglinkStand = new TextureRegion(screen.getAtlas().findRegion("big_link"), 0, 0, 32, 32);

        //create dead link texture region
        linkDead = new TextureRegion(screen.getAtlas().findRegion("little_link"), 96, 0, 16, 16);

        //define link in Box2d
        definelink();

        //set initial values for links location, width and height. And initial frame as linkStand.
        setBounds(0, 0, 16 / GetTheTriforce.PPM, 16 / GetTheTriforce.PPM);
        setRegion(linkStand);


    }

    public void update(float dt){
        //update our sprite to correspond with the position of our Box2D body
        if(linkIsBig)
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 - 6 / GetTheTriforce.PPM);
        else
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        //update sprite with the correct frame depending on links current action
        setRegion(getFrame(dt));
        if(timeToDefineBiglink)
            defineBiglink();
        if(timeToRedefinelink)
            redefineLink();
    }

    //method to finding out wich keyframes should be used while moving around
    public TextureRegion getFrame(float dt){
        //get links current state. ie. jumping, running, standing...
        currentState = getState();

        TextureRegion region;

        //depending on the state, get corresponding animation keyFrame.
        switch(currentState){
            case DEAD:
                region = linkDead;
                break;
            case GROWING:
                region = growlink.getKeyFrame(stateTimer);
                if(growlink.isAnimationFinished(stateTimer)) {
                    runGrowAnimation = false;
                }
                break;
            case JUMPING:
                region = linkIsBig ? biglinkJump : linkJump;
                break;
            case RUNNING:
                region = linkIsBig ? biglinkRun.getKeyFrame(stateTimer, true) : linkRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = linkIsBig ? biglinkStand : linkStand;
                break;
        }

        //if link is running left and the texture isn't facing left... flip it.
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        //if link is running right and the texture isn't facing right... flip it.
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;

    }

    //get the State Link is in
    public State getState(){
        //Test to Box2D for velocity on the X and Y-Axis
        //if link is going positive in Y-Axis he is jumping... or if he just jumped and is falling remain in jump state
        if(linkIsDead)
            return State.DEAD;

        else if(runGrowAnimation)
            return State.GROWING;
        else if((b2body.getLinearVelocity().y > 0 && currentState == State.JUMPING) || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
            //if negative in Y-Axis link is falling
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
            //if link is positive or negative in the X axis he is running
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
            //if none of these return then he must be standing
        else
            return State.STANDING;
    }

    //defining little Link
    public void definelink(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / GetTheTriforce.PPM, 48 / GetTheTriforce.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / GetTheTriforce.PPM);
        fdef.filter.categoryBits = GetTheTriforce.LINK_BIT;
        fdef.filter.maskBits = GetTheTriforce.GROUND_BIT |
                GetTheTriforce.STONE_BIT |
                GetTheTriforce.BRICK_BIT |
                GetTheTriforce.ENEMY_BIT |
                GetTheTriforce.OBJECT_BIT |
                GetTheTriforce.ENEMY_HEAD_BIT |
                GetTheTriforce.ITEM_BIT|
                GetTheTriforce.END_BIT|
                GetTheTriforce.HOLE_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / GetTheTriforce.PPM, 6 / GetTheTriforce.PPM), new Vector2(2 / GetTheTriforce.PPM, 6 / GetTheTriforce.PPM));
        fdef.filter.categoryBits = GetTheTriforce.LINK_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
    }

    //defining big Link
    public void defineBiglink(){
        Vector2 currentPosition = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(currentPosition.add(0, 10 / GetTheTriforce.PPM));
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / GetTheTriforce.PPM);
        fdef.filter.categoryBits = GetTheTriforce.LINK_BIT;
        fdef.filter.maskBits = GetTheTriforce.GROUND_BIT |
                GetTheTriforce.STONE_BIT |
                GetTheTriforce.BRICK_BIT |
                GetTheTriforce.ENEMY_BIT |
                GetTheTriforce.OBJECT_BIT |
                GetTheTriforce.ENEMY_HEAD_BIT |
                GetTheTriforce.ITEM_BIT|
                GetTheTriforce.END_BIT|
                GetTheTriforce.HOLE_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        shape.setPosition(new Vector2(0, -14 / GetTheTriforce.PPM));
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / GetTheTriforce.PPM, 6 / GetTheTriforce.PPM), new Vector2(2 / GetTheTriforce.PPM, 6 / GetTheTriforce.PPM));
        fdef.filter.categoryBits = GetTheTriforce.LINK_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
        timeToDefineBiglink = false;
    }

    //define Big Link to little Link when big Link is hit by an enemy
    public void redefineLink(){
        Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / GetTheTriforce.PPM);
        fdef.filter.categoryBits = GetTheTriforce.LINK_BIT;
        fdef.filter.maskBits = GetTheTriforce.GROUND_BIT |
                GetTheTriforce.STONE_BIT |
                GetTheTriforce.BRICK_BIT |
                GetTheTriforce.ENEMY_BIT |
                GetTheTriforce.OBJECT_BIT |
                GetTheTriforce.ENEMY_HEAD_BIT |
                GetTheTriforce.ITEM_BIT|
                GetTheTriforce.END_BIT|
        GetTheTriforce.HOLE_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / GetTheTriforce.PPM, 6 / GetTheTriforce.PPM), new Vector2(2 / GetTheTriforce.PPM, 6 / GetTheTriforce.PPM));
        fdef.filter.categoryBits = GetTheTriforce.LINK_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);

        timeToRedefinelink = false;

    }

    //what happens if Link is colliding with an enemy
    public void hit(Enemy enemy){

            if (linkIsBig) {
                linkIsBig = false;
                timeToRedefinelink = true;
                setBounds(getX(), getY(), getWidth(), getHeight() / 2);
                GetTheTriforce.manager.get("audio/sounds/powerdown.wav", Sound.class).play();
            } else {
                die();
            }

    }

    //method that lets little link grow into big Link after picking up a mushroom
    public void grow(){
        if( !isBig() ) {
            runGrowAnimation = true;
            linkIsBig = true;
            timeToDefineBiglink = true;
            setBounds(getX(), getY(), getWidth(), getHeight() * 2);
        }
    }

    //method that defines what happens if Link dies
    public void die() {

        if (!isDead()) {
            GetTheTriforce.manager.get("audio/music/zelda.ogg", Music.class).stop();
            GetTheTriforce.manager.get("audio/sounds/linkdie.wav", Sound.class).play();
            linkIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = GetTheTriforce.NOTHING_BIT;

            for (Fixture fixture : b2body.getFixtureList()) {
                fixture.setFilterData(filter);
            }

            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        }
    }





    public boolean isDead(){
        return linkIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public boolean isBig(){
        return linkIsBig;
    }

    public void jump(){
        if ( currentState != State.JUMPING ) {
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            currentState = State.JUMPING;
        }
    }

    public void endMethod() {
        game.setScreen(new LevelCompleteScreen(game));
    }






}




