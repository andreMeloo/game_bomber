package com.game.model.controls;

import com.game.model.state.GameState;

public class MenuControl extends ControlAdapter{
    @Override
    public boolean processKeyDown(int keycode, GameState currentState) {
        return false;
    }

    @Override
    public boolean processKeyUp(int keycode, GameState currentState) {
        return false;
    }
}
