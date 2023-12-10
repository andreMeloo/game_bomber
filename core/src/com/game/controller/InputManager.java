package com.game.controller;

import com.badlogic.gdx.InputProcessor;
import com.game.model.controls.ControlAdapter;
import com.game.View.screen.ScreenAdapter;
import com.game.model.objects.GameObject;

public class InputManager implements InputProcessor {

    private ControlAdapter controler;
    private ScreenAdapter currentScreen;
    private GameObject gameObject;

    public InputManager(ControlAdapter controler, ScreenAdapter currentScreen) {
        this.controler = controler;
        this.currentScreen = currentScreen;
    }

    public InputManager(ControlAdapter controler, GameObject gameObject) {
        this.controler = controler;
        this.gameObject = gameObject;
    }

    @Override
    public boolean keyDown(int keycode) {
        return currentScreen != null ? controler.processKeyDown(keycode, currentScreen)
                : gameObject != null && controler.processKeyDown(keycode, gameObject);
    }

    @Override
    public boolean keyUp(int keycode) {
        return currentScreen != null ? controler.processKeyUp(keycode, currentScreen)
                : gameObject != null && controler.processKeyUp(keycode, gameObject);
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

    public ScreenAdapter getCurrentScreen() {
        return currentScreen;
    }

    public GameObject getGameObject() {
        return gameObject;
    }
}
