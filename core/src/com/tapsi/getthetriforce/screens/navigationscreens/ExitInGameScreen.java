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

import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;

/**
 * Creates a screen to select a level
 */
public class ExitInGameScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    private GetTheTriforce game;
    private SpriteBatch sb;
    private Texture texture;
    private Table table;

    private Label.LabelStyle fontHeading;
    private Label selectionLabel;
    private TextButton changeLevelTB, exitTB, returnTB;

    public ExitInGameScreen(final GetTheTriforce game){
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
        selectionLabel = new Label("Do you want to: ",fontHeading);
        //returnTB = new TextButton("Return to the Level",buttonStyle);
        changeLevelTB = new TextButton("- Change the Level",buttonStyle);
        exitTB = new TextButton("- Exit the Game",buttonStyle);

        //creating listener for the textButtons

        //Return Button is a future feature
        /*returnTB.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //set Screen back to the current game

                dispose();
            }
        });*/

        changeLevelTB.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
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

        table.add(selectionLabel).expandX().padTop(10f);
        table.row();
        table.add(changeLevelTB).expandX().padTop(10f);
        table.row();
        table.add(exitTB).expandX().padTop(10f);

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
