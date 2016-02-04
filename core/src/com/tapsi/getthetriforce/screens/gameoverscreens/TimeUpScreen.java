package com.tapsi.getthetriforce.screens.gameoverscreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tapsi.getthetriforce.mainGameClass.GetTheTriforce;
import com.tapsi.getthetriforce.scenes.Hud;
import com.tapsi.getthetriforce.screens.infoscreens.StartNavigationScreen;
import com.tapsi.getthetriforce.screens.others.LevelSelectionScreen;

import static com.badlogic.gdx.graphics.Color.GOLDENROD;
import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;

/**
 * Creates a navigationscreen that shows up when the timer is 0
 */
public class TimeUpScreen implements Screen {

    private Viewport viewport;
    private Stage stage;

    private GetTheTriforce game;
    private SpriteBatch sb;
    private Texture texture;
    private Label gameOverLabel, sorryLabel, pointsLabel;
    private TextButton changeLevelTB,exitTB;
    private Table table;
    private Music music;
    private Skin skin;
    private TextureAtlas buttonatlas;

    public TimeUpScreen(final GetTheTriforce game){
        this.game = game;
        sb= game.batch;

        //initialize viewport and stage
        viewport = new FitViewport(GetTheTriforce.V_WIDTH, GetTheTriforce.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        //setting up the backgroundImage
        texture = new Texture("textures/back.jpg");

        //setting up the style of the label and textbutton
        Label.LabelStyle font= new Label.LabelStyle(new BitmapFont(), WHITE);

        skin = new Skin();
        buttonatlas = new TextureAtlas(Gdx.files.internal("buttons/buttons.txt"));
        skin.addRegions(buttonatlas);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = new BitmapFont();
        buttonStyle.fontColor = WHITE;
        buttonStyle.up = skin.getDrawable("up");
        buttonStyle.down = skin.getDrawable("down");


        //creating the textlabels & buttons incl. listener
        gameOverLabel = new Label("TIME IS UP", font);
        sorryLabel = new Label("Please: ", font);

        pointsLabel = new Label("You have gained " + Hud.getScore()+ " Points but reached the time limit.", font);


        changeLevelTB= new TextButton("Change the Level", buttonStyle);
        changeLevelTB.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new LevelSelectionScreen(game));
            }
        });


        exitTB = new TextButton("Go back to the Menu", buttonStyle);
        exitTB.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new StartNavigationScreen(game));
                dispose();
            }
        });



        //creating & filling the table
        table = new Table();
        table.center();
        table.setFillParent(true);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(pointsLabel).expandX().padTop(10f);
        table.row();
        table.add(sorryLabel).expandX().padTop(10f);
        table.row();
        table.add(changeLevelTB).expandX().padTop(5f);
        table.row();
        table.add(exitTB).expandX().padTop(5f);

        //adding table to stage
        stage.addActor(table);

        music = GetTheTriforce.manager.get("audio/music/zelda.ogg", Music.class);
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();
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
