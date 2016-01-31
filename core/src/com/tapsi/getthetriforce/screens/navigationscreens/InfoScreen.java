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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tapsi.getthetriforce.GetTheTriforce;

import static com.badlogic.gdx.graphics.Color.WHITE;

/**
 * Screen the loads at the start of opening the app
 */
public class InfoScreen implements Screen {

    private Viewport viewport;
    private Stage stage;
    private GetTheTriforce game;
    private SpriteBatch sb;
    private Texture texture;
    private Music music;

    private Label headingLabel;
    private TextButton scoreTB, backTB, controllTB ;
    private Table table;

    public InfoScreen(final GetTheTriforce game){
        this.game = game;
        sb = game.batch;

        texture= new Texture("textures/triforce.png");


        viewport = new FitViewport(GetTheTriforce.V_WIDTH, GetTheTriforce.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);


        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.RED);
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = new BitmapFont();
        buttonStyle.fontColor = WHITE;

        table = new Table();
        table.center();
        table.setFillParent(true);

        headingLabel = new Label("What Informations do you need?  ", font);

        scoreTB = new TextButton("- What the Scores are",buttonStyle);
        controllTB = new TextButton("- How to control the Game", buttonStyle);

        scoreTB.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //go back to the StartNavigationScreen
                game.setScreen(new ScoreListScreen(game));
                dispose();
            }
        });

        controllTB.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //game.setScreen(new ..........(game));
                //dispose();
            }
        });



        backTB.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new StartNavigationScreen(game));
                dispose();
            }
        });



        table.add(headingLabel).expandX();
        table.row();
        table.add(scoreTB).padTop(20f);
        table.row();
        table.add(controllTB).padTop(20f);
        table.row();
        table.add(backTB).padTop(30f);


        stage.addActor(table);

        music = GetTheTriforce.manager.get("audio/music/zelda.ogg", Music.class);
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();
    }

    @Override
    public void show() { Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {

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
