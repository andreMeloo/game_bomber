package com.game.model.controls;

import com.game.model.state.GameState;

public abstract class ControlAdapter {
    public abstract boolean processKeyDown(int keycode, GameState currentState);
    public abstract boolean processKeyUp(int keycode, GameState currentState);
}
