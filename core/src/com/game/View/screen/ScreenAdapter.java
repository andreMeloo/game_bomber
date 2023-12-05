package com.game.View.screen;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.game.model.controls.ControlAdapter;

public abstract class ScreenAdapter {



    public abstract ControlAdapter getControler();
    public abstract boolean pressUp(boolean isTypeKeyPressDOWN);
    public abstract boolean pressDown(boolean isTypeKeyPressDOWN);
    public abstract boolean pressLeft(boolean isTypeKeyPressDOWN);
    public abstract boolean pressRight(boolean isTypeKeyPressDOWN);
    public abstract boolean pressActionA(boolean isTypeKeyPressDOWN);
    public abstract boolean pressActionY(boolean isTypeKeyPressDOWN);
    public abstract boolean pressStart(boolean isTypeKeyPressDOWN);
}
