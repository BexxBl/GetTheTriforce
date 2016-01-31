package com.tapsi.getthetriforce.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tapsi.getthetriforce.mainGameClass.GetTheTriforce;


/**
 * Creates the Controls for the Game
 */

public class Controls implements Disposable{

    private GetTheTriforce game;
    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera cam;

    private Image upImg, leftImg, rightImg, exitImg;
    boolean upPressed, leftPressed, rightPressed, exitPressed;
    private Table tabler, tablel;

    public Controls (final GetTheTriforce game){
        this.game = game;
        cam = new OrthographicCamera();
        viewport = new FitViewport(GetTheTriforce.V_WIDTH, GetTheTriforce.V_HEIGHT, cam);
        stage = new Stage(viewport, game.batch);

        //listening to keyboard input
        stage.addListener(new InputListener() {

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.SPACE:
                        upPressed = true;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = true;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = true;
                        break;
                    case Input.Keys.E:
                        exitPressed = true;

                }
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.SPACE:
                        upPressed = false;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = false;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = false;
                        break;
                    case Input.Keys.E:
                        exitPressed = false;
                        break;


                }
                return true;
            }
        });

        //set the input processor to the stage
        Gdx.input.setInputProcessor(stage);

        //setup images + listener
        upImg = new Image(new Texture("controls/up.png"));
        upImg.setSize(40, 40);
        upImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });


        leftImg = new Image(new Texture("controls/left.png"));
        leftImg.setSize(40, 40);
        leftImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        rightImg = new Image(new Texture("controls/right.png"));
        rightImg.setSize(40, 40);
        rightImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        //define a table used to organize control elements
        tablel = new Table();
        tablel.left().bottom();
        tablel.setSize(GetTheTriforce.V_WIDTH, GetTheTriforce.V_HEIGHT);
        tablel.add(leftImg).size(upImg.getWidth(), upImg.getHeight()).bottom();
        tablel.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight()).bottom();
        tablel.add(upImg).size(upImg.getWidth(),upImg.getHeight()).expand().bottom().right();

        //setup exit Image in right top corner
        exitImg = new Image(new Texture("controls/x.png"));
        exitImg.setSize(40, 40);
        exitImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                exitPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                exitPressed = false;
            }
        });

        stage.addListener(new InputListener() {

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.E:
                        exitPressed = true;
                }
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.E:
                        exitPressed = false;
                }
                return true;
            }
        });

        // define Table for the In - Game - Exit
        tabler = new Table();
        tabler.right().top();
        tabler.setSize(GetTheTriforce.V_WIDTH, GetTheTriforce.V_HEIGHT);
        tabler.add(exitImg).size(exitImg.getWidth(), exitImg.getHeight()).bottom();


        //add table to stage
        stage.addActor(tabler);
        stage.addActor(tablel);

    }

    public boolean isUpPressed() {
        return upPressed;
    }
    public boolean isLeftPressed() {
        return leftPressed;
    }
    public boolean isRightPressed() {
        return rightPressed;
    }
    public boolean isExitPressed(){return exitPressed;}


    public void draw(){
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
