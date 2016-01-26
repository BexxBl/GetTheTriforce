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
import com.tapsi.getthetriforce.screens.playscreen.PlayScreen;

import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;

/**
 * Creates a screen to select a level
 */
public class StartNavigationScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    private GetTheTriforce game;
    private SpriteBatch sb;
    private Texture texture;
    private Table table;

    private Label.LabelStyle fontHeading;
    private Label selectionLabel;
    private TextButton startTB, levelSelectTB, level3TB, exitTB;

    public StartNavigationScreen(final GetTheTriforce game){
        this.game = game;
        sb = game.batch;

        viewport = new FitViewport(GetTheTriforce.V_WIDTH, GetTheTriforce.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        //setting up the backgroundImage
        texture = new Texture("textures/back.jpg");

        //setting up the style of the label and textbutton
        fontHeading = new Label.LabelStyle(new BitmapFont(), RED);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = new BitmapFont();
        buttonStyle.fontColor = WHITE;

        //creating the Labels & Buttons
        selectionLabel = new Label("Choose from these levels",fontHeading);

        startTB = new TextButton("- Start a new Game",buttonStyle);
        levelSelectTB = new TextButton("- Select a level",buttonStyle);

        exitTB = new TextButton("- Exit the Game",buttonStyle);

        //creating listener for the textButtons
        startTB.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //set Game to Level 1-1
                game.setScreen(new PlayScreen(game, "level/level1.tmx"));
                dispose();
            }
        });

        levelSelectTB.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //set Game to Level 1-2
                game.setScreen(new LevelSelectionScreen(game));
                dispose();

            }
        });



        exitTB.addListener(new InputListener() {
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


        //creating and filling table
        table = new Table();
        table.center();
        table.setFillParent(true);

        table.add(selectionLabel).expandX().padTop(30f);
        table.row();
        table.add(startTB).padTop(10f);
        table.row();
        table.add(levelSelectTB).padTop(10f);
        table.row();
        table.add(exitTB).expand().padTop(10f);

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
