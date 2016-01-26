package com.tapsi.getthetriforce.screens.playscreen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tapsi.getthetriforce.GetTheTriforce;
import com.tapsi.getthetriforce.scenes.Controls;
import com.tapsi.getthetriforce.scenes.Hud;
import com.tapsi.getthetriforce.screens.navigationscreens.ExitInGameScreen;
import com.tapsi.getthetriforce.screens.navigationscreens.GameOverScreen;
import com.tapsi.getthetriforce.screens.navigationscreens.TimeUpScreen;
import com.tapsi.getthetriforce.sprites.enemies.Enemy;
import com.tapsi.getthetriforce.sprites.items.Item;
import com.tapsi.getthetriforce.sprites.items.ItemDef;
import com.tapsi.getthetriforce.sprites.items.Mushroom;
import com.tapsi.getthetriforce.sprites.link.Link;
import com.tapsi.getthetriforce.tools.B2WorldCreator;
import com.tapsi.getthetriforce.tools.WorldContactListener;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Class where we setup our Screen to show the world
 * not using the Assetmanager that libgdx provides because of the graphics used
 * used graphics can be loaded quickly without using the assetmanager
 */

public class PlayScreen implements Screen{
    //Reference to Game, used to set Screens
    private GetTheTriforce game;
    private TextureAtlas atlas;
    public static boolean alreadyDestroyed = false;

    //basic playscreen variables
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    //Controller variables
    private Controls controls;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //sprites
    private Link player;

    private Music music;

    private Array<Item> items;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;


    public PlayScreen(GetTheTriforce game, String level){
        atlas = new TextureAtlas("linkandenemies.pack");

        this.game = game;
        //create cam used to follow link through cam world
        gameCam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(GetTheTriforce.V_WIDTH / GetTheTriforce.PPM, GetTheTriforce.V_HEIGHT / GetTheTriforce.PPM, gameCam);

        //create game HUD for scores/timers/level info
        hud = new Hud(game.batch);

        //create the controls
        controls = new Controls(game);

        //Load map and setup map renderer
        maploader = new TmxMapLoader();
        map = maploader.load(level);
        renderer = new OrthogonalTiledMapRenderer(map, 1  / GetTheTriforce.PPM);

        //initially set gamcam to be centered correctly at the start of of map
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //create Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
        world = new World(new Vector2(0, -10), true);
        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();

        //creates the bodies and fixtures
        creator = new B2WorldCreator(this);

        //create link in our game world
        player = new Link(this, game);


        //setting the world contactlistener
        world.setContactListener(new WorldContactListener());

        //to play the music
        music = GetTheTriforce.manager.get("audio/music/zelda.ogg", Music.class);
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();
    }

    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }


    public void handleSpawningItems(){
        if(!itemsToSpawn.isEmpty()){
            ItemDef idef = itemsToSpawn.poll();
            if(idef.type == Mushroom.class){
                items.add(new Mushroom(this, idef.position.x, idef.position.y));
            }
        }
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }


    @Override
    public void show() {
    }


    public void handleInput(float dt){
        //control player using immediate impulses
        if(player.currentState != Link.State.DEAD) {
            if (controls.isUpPressed())
                player.jump();
            if (controls.isRightPressed() && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            if (controls.isLeftPressed() && player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
            if (controls.isExitPressed()){
                game.setScreen(new ExitInGameScreen(game));
            }
        }
    }


    public void update(float dt){
        //handle user input first
        handleInput(dt);

        handleSpawningItems();

        //takes 1 step in the physics simulation(60 times per second)
        world.step(1 / 60f, 6, 2);

        player.update(dt);


        for(Enemy enemy : creator.getEnemies()) {
            enemy.update(dt);
            if(enemy.getX() < player.getX() + 224 / GetTheTriforce.PPM) {
                enemy.b2body.setActive(true);
            }
        }


        for(Item item : items)
            item.update(dt);

        hud.update(dt);

        //attach gameCam to players.x coordinate
        if(player.currentState != Link.State.DEAD) {
            gameCam.position.x = player.b2body.getPosition().x;
        }

        //update gameCam with correct coordinates after changes
        gameCam.update();
        //renderer to draws only what camera can see in game world.
        renderer.setView(gameCam);

    }


    @Override
    public void render(float delta) {
        //separate update logic from render
        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render game map
        renderer.render();

        //renderer our Box2DDebugLines--> to see if every element that interacts with player has a hitbox
        //b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);

        for (Enemy enemy : creator.getEnemies())
            enemy.draw(game.batch);
        for (Item item : items)
            item.draw(game.batch);

        game.batch.end();

        //Set batch to now draw what the Hud camera sees.
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        //draw controls when using android
        if(Gdx.app.getType() == Application.ApplicationType.Android){
            controls.stage.draw();
        }

        //draw controls when using ios
        if(Gdx.app.getType() == Application.ApplicationType.iOS){
            controls.stage.draw();
        }

        //if link is dead--> set Screen to the GameOverScreen
        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }

        //if time is up --> set Screen to GameOverScreen
        if (hud.isTimeUp()){
            game.setScreen(new TimeUpScreen(game));
            dispose();
        }
    }



    public boolean gameOver(){
        if(player.currentState == Link.State.DEAD && player.getStateTimer() > 3){
            return true;
        }

        return false;
    }

    @Override
    public void resize(int width, int height) {
        //updated game viewport
        gamePort.update(width,height);

    }

    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //dispose of all our opened resources
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        controls.dispose();
    }

    public Hud getHud(){ return hud; }


}
