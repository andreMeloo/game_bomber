package com.game.model.controls;

import com.game.model.state.GameState;

public class MenuControl extends ControlAdapter{
    @Override
    public boolean processarDown(int keycode, GameState currentState) {
        return false;
    }

    @Override
    public boolean processarUp(int keycode, GameState currentState) {
        return false;
    }
}
