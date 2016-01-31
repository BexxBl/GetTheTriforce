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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tapsi.getthetriforce.mainGameClass.GetTheTriforce;

import static com.badlogic.gdx.graphics.Color.WHITE;

/**
 * What Points do i get when breaking bricks, jumping at enemies or jumping at ?-blocks
 */
public class ScoreListScreen implements Screen {

    private Viewport viewport;
    private Stage stage;
    private GetTheTriforce game;
    private SpriteBatch sb;
    private Texture texture;
    private Music music;

    private Label welcomeLabel, BricksLabel, kidLabel, chickenLabel, blockLabel, lineLabel;
    private Table table;


    public ScoreListScreen(final GetTheTriforce game){
        this.game = game;
        sb = game.batch;

        texture= new Texture("textures/back.jpg");


        viewport = new FitViewport(GetTheTriforce.V_WIDTH, GetTheTriforce.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);


        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.RED);
        Label.LabelStyle font2 = new Label.LabelStyle(new BitmapFont(),Color.WHITE);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = new BitmapFont();
        buttonStyle.fontColor = WHITE;


        welcomeLabel = new Label("List of Points you can get for: ", font);
        BricksLabel = new Label("# Breaking Bricks - 200 Points ", font2);
        blockLabel = new Label("# Hitting the ?- Blocks - 150 Points",font2);
        lineLabel =new Label("---------------------",font2);
        chickenLabel = new Label("# Killing the Chickens - 300 Points",font2);
        kidLabel = new Label("# Killing the Horrorkids - 350 Points",font2);

        table = new Table();
        table.center();
        table.setFillParent(true);

        table.add(welcomeLabel).expandX();
        table.row();
        table.add(BricksLabel).padTop(10f);
        table.row();
        table.add(blockLabel).padTop(10f);
        table.row();
        table.add(lineLabel);
        table.row();
        table.add(chickenLabel).padTop(10f);
        table.row();
        table.add(kidLabel).padTop(10f);

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
