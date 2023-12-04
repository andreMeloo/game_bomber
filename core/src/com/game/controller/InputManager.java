package com.game.controller;

import com.badlogic.gdx.InputProcessor;
import com.game.model.controls.ControlAdapter;
import com.game.View.screen.ScreenAdapter;

public class InputManager implements InputProcessor {

    private ControlAdapter controler;
    private ScreenAdapter currentScreen;

    public InputManager(ControlAdapter controler, ScreenAdapter currentState) {
        this.controler = controler;
        this.currentScreen = currentState;
    }

    @Override
    public boolean keyDown(int keycode) {
        return controler.processKeyDown(keycode, currentScreen);
    }

    @Override
    public boolean keyUp(int keycode) {
        return controler.processKeyUp(keycode, currentScreen);
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public ControlAdapter getControler() {
        return controler;
    }

    public void setControler(ControlAdapter controler) {
        this.controler = controler;
    }

    public ScreenAdapter getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(ScreenAdapter currentScreen) {
        this.currentScreen = currentScreen;
    }
}
