package com.tapsi.getthetriforce.sprites.tileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.tapsi.getthetriforce.GetTheTriforce;
import com.tapsi.getthetriforce.scenes.Hud;
import com.tapsi.getthetriforce.screens.PlayScreen;
import com.tapsi.getthetriforce.sprites.link.Link;

/**
 * Creates the Bricks & handels what happens after collision
 */
public class Brick extends InteractiveTileObject {
    public Brick(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(GetTheTriforce.BRICK_BIT);
    }



    public void onHeadHit(Link link) {
        if(link.isBig()){
            setCategoryFilter(GetTheTriforce.DESTROYED_BIT);
            getCell().setTile(null);
            GetTheTriforce.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
            Hud.addScore(200);
        }
        GetTheTriforce.manager.get("audio/sounds/bump.wav", Sound.class).play();

    }
}
