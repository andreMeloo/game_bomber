package com.game.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.View.screen.MenuScreen;


public class GameManager extends Game {
    public SpriteBatch batch;
    private InputMultiplexer inputMultiplexer;

    @Override
    public void create() {
        inputMultiplexer = new InputMultiplexer();
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

    public void addInput(InputManager input) {
        getInputMultiplexer().addProcessor(input);
        Gdx.input.setInputProcessor(getInputMultiplexer());
    }

    public void removeInput(InputManager input) {
        getInputMultiplexer().removeProcessor(input);
        Gdx.input.setInputProcessor(getInputMultiplexer());
    }

    public InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }
}
