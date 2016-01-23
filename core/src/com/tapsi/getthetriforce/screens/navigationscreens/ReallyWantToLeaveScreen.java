package com.tapsi.getthetriforce.screens.navigationscreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
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
 * Creates the screen that questions the user if they really want to exit the game
 */
public class ReallyWantToLeaveScreen implements Screen{
    private Viewport viewport;
    private Stage stage;

    private GetTheTriforce game;
    private SpriteBatch sb;
    private Texture texture;
    private Label headingLabel;
    private TextButton yesTB, noTB;

    public ReallyWantToLeaveScreen (final GetTheTriforce game){
        this.game = game;
        sb= game.batch;

        //initialize viewport and stage
        viewport = new FitViewport(GetTheTriforce.V_WIDTH, GetTheTriforce.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        //setting up the backgroundImage
        texture = new Texture("textures/back.jpg");

        //setting up the style of the label and textbutton
        Label.LabelStyle fontEnd = new Label.LabelStyle(new BitmapFont(), RED);
        Label.LabelStyle font= new Label.LabelStyle(new BitmapFont(), WHITE);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = new BitmapFont();
        buttonStyle.fontColor = WHITE;

        //Setting up the Label
        headingLabel = new Label("Do you really want to leave?", fontEnd);

        //setting up the Buttons
        yesTB = new TextButton("NO", buttonStyle);
        yesTB.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new StartScreen(game));
            }
        });





        noTB = new TextButton("YES", buttonStyle);
        noTB.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.exit(0);
            }
        });

        //creating & filling the table
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        table.add(headingLabel).expandX();
        table.row();
        table.add(noTB).expandX().padTop(10f);
        table.row();
        table.add(yesTB).expandX().padTop(20f);

        //adding table to stage
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
