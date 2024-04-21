package com.game.View.screen;

import com.game.controller.GameManager;
import com.game.controller.InputManager;

public abstract class ScreenAdapter {

    private final GameManager gameManager;
    private InputManager inputManager;

    protected ScreenAdapter(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public abstract void pressUp(boolean isTypeKeyPressDOWN);
    public abstract void pressDown(boolean isTypeKeyPressDOWN);
    public abstract void pressLeft(boolean isTypeKeyPressDOWN);
    public abstract void pressRight(boolean isTypeKeyPressDOWN);
    public abstract void pressActionA(boolean isTypeKeyPressDOWN);
    public abstract void pressActionY(boolean isTypeKeyPressDOWN);
    public abstract void pressStart(boolean isTypeKeyPressDOWN);

    public InputManager getInputManager() {
        return inputManager;
    }

    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
