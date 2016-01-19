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
import com.tapsi.getthetriforce.GetTheTriforce;


/**
 * Creates the onScreen Controls when using on mobile devices
 */

public class Controls implements Disposable{

    private GetTheTriforce game;
    public Stage stage;
    private Viewport viewport;
    private OrthographicCamera cam;

    private Image upImg, leftImg, rightImg;
    boolean upPressed, leftPressed, rightPressed;

    public Controls (GetTheTriforce game){
        this.game =game;
        cam = new OrthographicCamera();
        viewport = new FitViewport(GetTheTriforce.V_WIDTH, GetTheTriforce.V_HEIGHT, cam);
        stage = new Stage(viewport, game.batch);

        //listening to keyboard input
        stage.addListener(new InputListener(){

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch(keycode){
                    case Input.Keys.UP:
                        upPressed = true;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = true;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = true;
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch(keycode){
                    case Input.Keys.UP:
                        upPressed = false;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = false;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = false;
                        break;
                }
                return true;
            }
        });

        Gdx.input.setInputProcessor(stage);

        //define a table used to organize control elements
        Table tablel = new Table();
        //Top-Align table
        tablel.left().bottom();
        tablel.setSize(GetTheTriforce.V_WIDTH, GetTheTriforce.V_HEIGHT);

        //setup images + listener
        upImg = new Image(new Texture("controls/up1.png"));
        upImg.setSize(50, 50);
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


        leftImg = new Image(new Texture("controls/left1.png"));
        leftImg.setSize(50, 50);
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

        rightImg = new Image(new Texture("controls/right1.png"));
        rightImg.setSize(50, 50);
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

        //fill table
        tablel.add(leftImg).size(upImg.getWidth(), upImg.getHeight()).bottom();
        tablel.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight()).bottom();
        tablel.add(upImg).size(upImg.getWidth(),upImg.getHeight()).expand().bottom().right();

        //add table to stage
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

    public void draw(){
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
