package com.game.model.controls;

import com.game.model.state.ScreenAdapter;

public class MenuControl extends ControlAdapter{
    @Override
    public boolean processKeyDown(int keycode, ScreenAdapter currentState) {
        return false;
    }

    @Override
    public boolean processKeyUp(int keycode, ScreenAdapter currentState) {
        return false;
    }
}
