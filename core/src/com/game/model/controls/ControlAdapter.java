package com.game.model.controls;

import com.game.model.state.GameState;

public abstract class ControlAdapter {
    public abstract boolean processarDown(int keycode, GameState currentState);
    public abstract boolean processarUp(int keycode, GameState currentState);
}
