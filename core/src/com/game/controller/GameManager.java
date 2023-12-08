package com.game.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.View.screen.ChoiceCharacterScreen;
import com.game.View.screen.MenuScreen;
import com.game.model.controls.GameControl;


public class GameManager extends Game {
    public SpriteBatch batch;
    private InputManager inputManager;

    @Override
    public void create() {
//        Gdx.graphics.setUndecorated(true);
        batch = new SpriteBatch();
        this.setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        this.getScreen().dispose();
    }

    public void setInput() {
        Gdx.input.setInputProcessor(inputManager);
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }
}
