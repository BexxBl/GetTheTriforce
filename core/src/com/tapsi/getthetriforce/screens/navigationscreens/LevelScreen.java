package com.tapsi.getthetriforce.screens.navigationscreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.tapsi.getthetriforce.screens.PlayScreen;

import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;

/**
 * Creates a screen to select a level
 */
public class LevelScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    private GetTheTriforce game;
    private SpriteBatch sb;
    private Texture texture;
    private Table table;

    private Label.LabelStyle fontHeading, font;
    private Label selectionLabel, clickLabel;
    private TextButton level1TB, level2TB, level3TB, exitTB;

    public LevelScreen (final GetTheTriforce game){
        this.game = game;
        sb = game.batch;

        viewport = new FitViewport(GetTheTriforce.V_WIDTH, GetTheTriforce.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        //setting up the backgroundImage
        texture = new Texture("test/back.jpg");

        //setting up the style of the label and textbutton
        fontHeading = new Label.LabelStyle(new BitmapFont(), RED);
        font= new Label.LabelStyle(new BitmapFont(), WHITE);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = new BitmapFont();
        buttonStyle.fontColor = WHITE;

        //creating the Labels & Buttons
        selectionLabel = new Label("Select your level of choice now",fontHeading);
        clickLabel = new Label("Just click the levelname to do so", font);

        level1TB = new TextButton("Level 1-1",buttonStyle);
        level2TB = new TextButton("Level 1-2",buttonStyle);
        level3TB = new TextButton("Level 1-3",buttonStyle);

        exitTB = new TextButton("Exit Game",buttonStyle);

        //creating listener for the textbuttons
        level1TB.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //set Game to Level 1-1
                game.setScreen(new PlayScreen(game));
                dispose();
            }
        });

        level2TB.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //set Game to Level 1-2
            }
        });

        level3TB.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //set Game to Level 1-3
            }
        });


        exitTB.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.exit(0);
            }
        });


        //creating and filling table
        table = new Table();
        table.center();
        table.setFillParent(true);

        table.add(selectionLabel).expand();
        table.row();
        table.add(clickLabel).expand().padTop(10f);
        table.row();
        table.row().padTop(20f);
        table.add(level1TB).expand().padTop(10f);
        table.row();
        table.add(level2TB).expand().padTop(10f);
        table.row();
        table.add(level3TB).expand().padTop(10f);
        table.row();
        table.add(exitTB).expand().padTop(30f);

        stage.addActor(table);

    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
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
