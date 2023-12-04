package com.game.View.screen;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.game.model.controls.ControlAdapter;

public abstract class ScreenAdapter {



    public abstract ControlAdapter getControler();
    public abstract boolean pressUp();
    public abstract boolean pressDown();
    public abstract boolean pressLeft();
    public abstract boolean pressRight();
    public abstract boolean pressActionA();
    public abstract boolean pressActionY();
    public abstract boolean pressStart();
}
