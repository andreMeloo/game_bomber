package com.game.model.controls;

import com.game.model.state.ScreenAdapter;

public abstract class ControlAdapter {
    public abstract boolean processKeyDown(int keycode, ScreenAdapter currentScreen);
    public abstract boolean processKeyUp(int keycode, ScreenAdapter currentScreen);
}
