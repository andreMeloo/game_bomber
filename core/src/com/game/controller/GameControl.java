package com.game.controller;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.game.model.state.ChoiceCaracterState;


public class GameControl extends ApplicationAdapter {
    private GameStateManager gameStateManager;
    private InputManager inputManager;

    @Override
    public void create() {
        gameStateManager = new GameStateManager();
        gameStateManager.setState(new ChoiceCaracterState(gameStateManager));
        inputManager = new InputManager(gameStateManager.getCurrentState().getControler(), gameStateManager.getCurrentState());
        setInput();
    }

    @Override
    public void render() {
         gameStateManager.update();
         gameStateManager.render();
         if (!inputManager.getCurrentState().equals(gameStateManager.getCurrentState())) {
             inputManager = new InputManager(gameStateManager.getCurrentState().getControler(), gameStateManager.getCurrentState());
             setInput();
         }
	}
	
	@Override
	public void dispose () {
        gameStateManager.getCurrentState().dispose();
    }

    private void setInput() {
        Gdx.input.setInputProcessor(inputManager);
    }
}
