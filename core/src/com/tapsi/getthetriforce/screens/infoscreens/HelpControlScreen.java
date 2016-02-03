package com.tapsi.getthetriforce.screens.infoscreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tapsi.getthetriforce.mainGameClass.GetTheTriforce;

/**
 * how to navigate in the game
 */
public class HelpControlScreen implements Screen {

    private Viewport viewport;
    private Stage stage;
    private GetTheTriforce game;
    private SpriteBatch sb;
    private Texture texture;

    private Label upLabel, leftLabel, rightLabel, xLabel, headingLabel;
    private Image upImg, leftImg, rightImg, xImg;
    private Table table;

    private Music music;

    public HelpControlScreen(final GetTheTriforce game){
        this.game = game;
        sb = game.batch;

        texture= new Texture("textures/back.jpg");

        viewport = new FitViewport(GetTheTriforce.V_WIDTH, GetTheTriforce.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        //creating the styles of the Labels and TextButtons
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        //initaizing the Labels and Images
        headingLabel = new Label("How to Play:", font);
        leftLabel = new Label("Run Left", font);
        rightLabel = new Label("Run right", font);
        upLabel = new Label("Jump", font);
        xLabel = new Label("Exit the Level", font);

        upImg = new Image(new Texture("controls/up.png"));
        upImg.setSize(16,16);

        leftImg = new Image(new Texture("controls/left.png"));
        leftImg.setSize(16,16);

        rightImg = new Image(new Texture("controls/right.png"));
        rightImg.setSize(16, 16);

        xImg = new Image(new Texture("controls/x.png"));
        xImg.setSize(16, 16);

        //creating and filling the Table
        table = new Table();
        table.left().center();
        table.setFillParent(true);


        table.add(headingLabel);
        table.row().padTop(10f);
        table.add(leftLabel);
        table.add(leftImg).size(20, 20).padLeft(10f);
        table.row();
        table.add(rightLabel);
        table.add(rightImg).size(20, 20).padLeft(10f);
        table.row();
        table.add(upLabel);
        table.add(upImg).size(20, 20).padLeft(10f);
        table.row();
        table.add(xLabel);
        table.add(xImg).size(20, 20).padLeft(10f);


        //adding the table to the stage
        stage.addActor(table);

        music = GetTheTriforce.manager.get("audio/music/zelda.ogg", Music.class);
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if(Gdx.input.justTouched()) {
            game.setScreen(new StartNavigationScreen(game));
            dispose();
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.begin();
        sb.draw(texture, 0, 0);
        sb.end();
        stage.draw();



    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
        texture.dispose();
    }
}
