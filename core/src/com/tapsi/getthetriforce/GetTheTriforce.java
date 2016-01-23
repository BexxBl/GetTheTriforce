package com.tapsi.getthetriforce;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tapsi.getthetriforce.screens.navigationscreens.EndScreen;
import com.tapsi.getthetriforce.screens.navigationscreens.ReallyWantToLeaveScreen;
import com.tapsi.getthetriforce.screens.navigationscreens.StartScreen;

/*
 * Main Game Class
 */
public class GetTheTriforce extends Game implements ApplicationListener {

	//Virtual ScreenSize and Box2DScale
	public static final float PPM = 100 ;
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208 ;


	//Box2D Collision Bits
	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short LINK_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short STONE_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short ITEM_BIT = 256;
	public static final short LINK_HEAD_BIT = 512;
	public static final short DOOR_BIT = 1024;
	public static final short HOLE_BIT = 2048;

	//SpriteBatch
	public SpriteBatch batch;

	//AssetManager to load our music
	public static AssetManager manager = new AssetManager();



	@Override
	public void create () {
		batch = new SpriteBatch();
		manager.load("audio/music/zelda.ogg", Music.class);
		manager.load("audio/sounds/coin.wav", Sound.class);
		manager.load("audio/sounds/bump.wav", Sound.class);
		manager.load("audio/sounds/breakblock.wav", Sound.class);
		manager.load("audio/sounds/powerup_spawn.wav", Sound.class);
		manager.load("audio/sounds/powerup.wav", Sound.class);
		manager.load("audio/sounds/powerdown.wav", Sound.class);
		manager.load("audio/sounds/stomp.wav", Sound.class);
		manager.load("audio/sounds/linkdie.wav", Sound.class);

		manager.finishLoading();
		setScreen(new StartScreen(this));

	}

	@Override
	public void render () {
		super.render();
	}


}
