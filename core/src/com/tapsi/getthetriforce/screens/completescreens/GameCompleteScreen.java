package com.tapsi.getthetriforce.screens.completescreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
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
import com.tapsi.getthetriforce.screens.others.PlayScreen;

import static com.badlogic.gdx.graphics.Color.GOLDENROD;
import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;

/**
 * Creates the Screen that will pop up when the player completed all 3 levels
 * contatins a particle system
 * is not used because currently game logic does not remember which level is played.
 * will be used in a future version of the game
 */
public class GameCompleteScreen implements Screen{
    private Viewport viewport;
    private Stage stage;

    private GetTheTriforce game;
    private SpriteBatch sb;
    private Texture texture;
    private Label headingLabel, messageLabel, congratsLabel,descionLabel;
    private Table table;
    private Music music;
    private Skin skin;
    private TextureAtlas buttonatlas;

    private ParticleEffect particleEffect;

    public GameCompleteScreen(final GetTheTriforce game){
        this.game = game;
        sb= game.batch;

        //initialize viewport and stage
        viewport = new FitViewport(GetTheTriforce.V_WIDTH, GetTheTriforce.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        //setting up the backgroundImage
        texture = new Texture("textures/back.jpg");

        //setting up the ParticleEffect
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("particle/v2white.party"), Gdx.files.internal(""));
        particleEffect.getEmitters().first().setPosition(GetTheTriforce.V_WIDTH / 2, GetTheTriforce.V_HEIGHT/2);
        particleEffect.start();

        //setting up the style of the label and textbutton
        Label.LabelStyle font= new Label.LabelStyle(new BitmapFont(), WHITE);

        skin = new Skin();
        buttonatlas = new TextureAtlas(Gdx.files.internal("buttons/buttons.txt"));
        skin.addRegions(buttonatlas);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = new BitmapFont();
        buttonStyle.fontColor = WHITE;
        buttonStyle.downFontColor = RED;
        buttonStyle.up = skin.getDrawable("up");
        buttonStyle.down = skin.getDrawable("down");
        buttonStyle.down = skin.getDrawable("down");

        //creating the textlabels & buttons incl. listener
        headingLabel = new Label("The End", font);
        messageLabel = new Label("You have completed all 3 Levels!", font);
        congratsLabel = new Label("Congratulations!", font);
        descionLabel = new Label("Do you want to: ",font);


        TextButton playAgainTB = new TextButton("Start a new Game", buttonStyle);
        playAgainTB.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new PlayScreen(game, "level/level1.tmx"));
                dispose();
            }
        });


        TextButton exitTB = new TextButton("Exit the Game", buttonStyle);
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

        //creating & filling the table
        table = new Table();
        table.center();
        table.setFillParent(true);

        table.add(headingLabel).expandX();
        table.row();
        table.add(messageLabel).expandX().padTop(10f);
        table.row();
        table.add(congratsLabel).expandX().padTop(10f);
        table.row();
        table.add(descionLabel).expandX().padTop(10f);
        table.row();
        table.add(playAgainTB).expandX().padTop(10f);
        table.row();
        table.add(exitTB).expandX().padTop(20f);

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
        particleEffect.update(Gdx.graphics.getDeltaTime());

        sb.begin();
        sb.draw(texture, 0, 0);
        particleEffect.draw(sb);
        sb.end();
        stage.draw();

        //draw particleeffect all the time
        if (particleEffect.isComplete()){
            particleEffect.reset();
        }
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
        particleEffect.dispose();
    }
}
