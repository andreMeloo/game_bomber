package com.game.controller;

import com.game.model.state.GameState;

public class GameStateManager {
    private GameState currentState;

    public void setState(GameState newState) {
        if (currentState != null) {
            currentState.dispose();
        }
        currentState = newState;
    }

    public void update() {
        if (currentState != null) {
            currentState.update();
        }
    }

    public void render() {
        if (currentState != null) {
            currentState.render();
        }
    }

    public GameState getCurrentState() {
        return currentState;
    }
}
