package com.tapsi.getthetriforce.mainGameClass;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.tapsi.getthetriforce.screens.exitscreens.ReallyWantToLeaveScreen;
import com.tapsi.getthetriforce.screens.infoscreens.IntroScreen;


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
	public static final short END_BIT = 1024;
	public static final short HOLE_BIT = 2048;

	//SpriteBatch
	public SpriteBatch batch;

	//AssetManager to load our music
	public static AssetManager manager;

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();

		//loading all the Audiofiles
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

		//setting the first screen we see to the startscreen
		setScreen(new IntroScreen(this));

		//disabling the back key
		Gdx.input.setCatchBackKey(true);

		if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
			setScreen(new ReallyWantToLeaveScreen(this));
		}


	}

	@Override
	public void render () {
		super.render();
	}




}
