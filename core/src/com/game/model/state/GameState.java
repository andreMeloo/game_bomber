package com.game.model.state;

import com.game.controller.GameStateManager;
import com.game.model.controls.ControlAdapter;

public abstract class GameState {
    protected GameStateManager gameStateManager;
    protected boolean initialized = false;

    public GameState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    public abstract void initializedState();
    public abstract void update();
    public abstract void render();
    public abstract void dispose();
    public abstract ControlAdapter getControler();
}
