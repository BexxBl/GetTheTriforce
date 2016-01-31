package com.tapsi.getthetriforce.sprites.tileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.tapsi.getthetriforce.mainGameClass.GetTheTriforce;
import com.tapsi.getthetriforce.scenes.Hud;
import com.tapsi.getthetriforce.screens.others.PlayScreen;
import com.tapsi.getthetriforce.sprites.items.ItemDef;
import com.tapsi.getthetriforce.sprites.items.Mushroom;
import com.tapsi.getthetriforce.sprites.link.Link;

/**
 * Creates the Stones that the user will get points for collecting them
 *  handels what happens after collision
 */
public class QuestionBlock extends InteractiveTileObject{
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;

    public QuestionBlock(PlayScreen screen, MapObject object){
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(GetTheTriforce.STONE_BIT);
    }

    @Override
    public void onHeadHit(Link link) {
       if(getCell().getTile().getId() == BLANK_COIN)
            GetTheTriforce.manager.get("audio/sounds/bump.wav", Sound.class).play();
        else {
            if(object.getProperties().containsKey("mushroom")) {
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / GetTheTriforce.PPM),
                        Mushroom.class));
                GetTheTriforce.manager.get("audio/sounds/powerup_spawn.wav", Sound.class).play();
            }
            else
                GetTheTriforce.manager.get("audio/sounds/coin.wav", Sound.class).play();

            getCell().setTile(tileSet.getTile(BLANK_COIN));
            Hud.addScore(150);
        }

    }
}
