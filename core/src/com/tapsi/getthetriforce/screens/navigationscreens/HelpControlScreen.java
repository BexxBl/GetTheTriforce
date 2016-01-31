package com.tapsi.getthetriforce.screens.navigationscreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tapsi.getthetriforce.GetTheTriforce;

import static com.badlogic.gdx.graphics.Color.WHITE;

/**
 * how to navigate in the game
 */
public class HelpControlScreen implements Screen {

    private Viewport viewport;
    private Stage stage;
    private GetTheTriforce game;
    private SpriteBatch sb;
    private Texture texture;

    private Label headingLabel, upLabel, leftLabel, rightLabel, xLabel;
    private Image upImg, leftImg, rightImg, xImg;
    private Table table;

    public HelpControlScreen(final GetTheTriforce game){
        this.game = game;
        sb = game.batch;

        texture= new Texture("textures/back.jpg");


        viewport = new FitViewport(GetTheTriforce.V_WIDTH, GetTheTriforce.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);


        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.RED);
        Label.LabelStyle font2 = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = new BitmapFont();
        buttonStyle.fontColor = WHITE;

        headingLabel = new Label("How to control the Game: ", font);
        leftLabel = new Label("Run Left", font2);
        rightLabel = new Label("Run right", font2);
        upLabel = new Label("Jump", font2);
        xLabel = new Label("Exit the Game while playing", font2);

        upImg = new Image(new Texture("controls/up.png"));
        upImg.setSize(16,16);

        leftImg = new Image(new Texture("controls/left.png"));
        leftImg.setSize(16,16);

        rightImg = new Image(new Texture("controls/right.png"));
        rightImg.setSize(16, 16);

        xImg = new Image(new Texture("controls/x.png"));
        xImg.setSize(16, 16);


        table = new Table();
        table.center();
        table.setFillParent(true);

        table.add(headingLabel);
        table.row();
        table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        table.add(leftLabel);
        table.row();
        table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
        table.add(rightLabel);
        table.row();
        table.add(upImg).size(upImg.getWidth(), upImg.getHeight());
        table.add(upLabel);
        table.row();
        table.add(xImg).size(xImg.getWidth(), xImg.getHeight());
        table.add(xLabel);

        stage.addActor(table);
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
