package com.game.model.controls;

import com.game.View.screen.ScreenAdapter;
import com.game.model.objects.GameObject;

public abstract class ControlAdapter {
    public abstract boolean processKeyDown(int keycode, ScreenAdapter currentScreen);

    public abstract boolean processKeyUp(int keycode, ScreenAdapter currentScreen);

    public abstract boolean processKeyDown(int keycode, GameObject gameObject);

    public abstract boolean processKeyUp(int keycode, GameObject currentScreen);
}
