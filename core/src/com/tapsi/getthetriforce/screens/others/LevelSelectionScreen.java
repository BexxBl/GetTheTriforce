package com.tapsi.getthetriforce.screens.others;

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


import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;

/**
 * Creates a screen to select a level
 */
public class LevelSelectionScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    private GetTheTriforce game;
    private SpriteBatch sb;
    private Texture texture;
    private Table table;

    private Label.LabelStyle font;
    private Label selectionLabel;
    private TextButton level1TB, level2TB, level3TB, gobackTB;
    private com.badlogic.gdx.scenes.scene2d.ui.Skin skin;
    private TextureAtlas buttonatlas;

    private Music music;

    public LevelSelectionScreen(final GetTheTriforce game){
        this.game = game;
        sb = game.batch;

        viewport = new FitViewport(GetTheTriforce.V_WIDTH, GetTheTriforce.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        //setting up the backgroundImage
        texture = new Texture("textures/back.jpg");

        //setting up the style of the label and textbutton
        font = new Label.LabelStyle(new BitmapFont(), WHITE);

        skin = new Skin();
        buttonatlas = new TextureAtlas(Gdx.files.internal("buttons/buttons.txt"));
        skin.addRegions(buttonatlas);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = new BitmapFont();
        buttonStyle.fontColor = WHITE;
        buttonStyle.up = skin.getDrawable("up");
        buttonStyle.down = skin.getDrawable("down");

        //creating the Labels & Buttons
        selectionLabel = new Label("Please choose from these levels:",font);

        level1TB = new TextButton("Level 1",buttonStyle);
        level2TB = new TextButton("Level 2",buttonStyle);
        level3TB = new TextButton("Level 3",buttonStyle);
        gobackTB = new TextButton("Go back to the Menu",buttonStyle);


        //creating listener for the textButtons
        level1TB.addListener(new InputListener(){
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

        level2TB.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //set Game to Level 1-2
                game.setScreen(new PlayScreen(game, "level/level2.tmx"));
                dispose();

            }
        });

        level3TB.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //set Game to Level 1-3
                game.setScreen(new PlayScreen(game, "level/level3.tmx"));
                dispose();
            }
        });

        gobackTB.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //go back to the StartNavigationScreen
                game.setScreen(new com.tapsi.getthetriforce.screens.infoscreens.StartNavigationScreen(game));
                dispose();
            }
        });



        //creating and filling table
        table = new Table();
        table.center();
        table.setFillParent(true);

        table.add(selectionLabel).expandX();
        table.row();
        table.add(level1TB).padTop(6f);
        table.row();
        table.add(level2TB).padTop(3f);
        table.row();
        table.add(level3TB).padTop(3f);
        table.row();
        table.add(gobackTB).padTop(10f);


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
