package com.tapsi.getthetriforce.screens.navigationscreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tapsi.getthetriforce.GetTheTriforce;
import com.tapsi.getthetriforce.screens.playscreen.PlayScreen;

import static com.badlogic.gdx.graphics.Color.WHITE;

/**
 * Creates the screen between the levels
 */
public class ChangeScreen implements Screen{
    private Viewport viewport;
    private Stage stage;
    private GetTheTriforce game;
    private SpriteBatch sb;
    private Texture texture;

    public ChangeScreen(final GetTheTriforce game) {


        this.game = game;
        sb = game.batch;

        texture= new Texture("textures/back.jpg");


        viewport = new FitViewport(GetTheTriforce.V_WIDTH, GetTheTriforce.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.RED);
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = new BitmapFont();
        buttonStyle.fontColor = WHITE;

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label completeLabel = new Label("You completed the Level", font);
        Label descionLabel = new Label("Do you want to", font);
        TextButton nextLevelTB = new TextButton("Go to the next Level",buttonStyle);
        TextButton exitGameTB = new TextButton("Exit the Game", buttonStyle);

        //setting up the listener

        nextLevelTB.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new PlayScreen(game,"level/level2.tmx"));
                dispose();
            }
        });

        exitGameTB.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new ReallyWantToLeaveScreen(game));
                dispose();
            }
        });

        table.add(completeLabel).expandX();
        table.row();
        table.add(descionLabel);
        table.row();
        table.add(nextLevelTB).expandX().padTop(10f);
        table.row();
        table.add(exitGameTB).expandX().padTop(10f);

        stage.addActor(table);
    }

    @Override
    public void show() { Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        sb.draw(texture,0,0);
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
