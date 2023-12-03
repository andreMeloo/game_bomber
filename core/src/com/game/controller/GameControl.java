package com.game.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.model.state.ChoiceCharacterScreen;
import com.game.model.state.MenuScreen;


public class GameControl extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    private InputManager inputManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // use libGDX's default Arial font
        this.setScreen(new MenuScreen(this));
        setInput();
    }

    @Override
    public void render() {
        super.render();
        if (!inputManager.getCurrentScreen().equals(this.getScreen())) {
            setInput();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        this.getScreen().dispose();
    }

    private void setInput() {
        Gdx.input.setInputProcessor(inputManager);
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }
}
