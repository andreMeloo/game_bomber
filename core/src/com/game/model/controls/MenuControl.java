package com.game.model.controls;

import com.game.View.screen.MenuScreen;
import com.game.View.screen.ScreenAdapter;

import java.util.HashMap;
import java.util.Map;

public class MenuControl extends ControlAdapter {
    ScreenAdapter screen;
    private final Map<String, Runnable> keyOperations = new HashMap<>();

    public MenuControl() {
        constructKeyOperation();
    }

    @Override
    public boolean processKeyDown(int keycode, ScreenAdapter currentScreen) {
        setScreen(currentScreen);
        Runnable operation = keyOperations.get(ControlsConfig.INPUT_KEY_DOWN + keycode);
        if (operation != null)
            operation.run();

        return true;
    }

    @Override
    public boolean processKeyUp(int keycode, ScreenAdapter currentScreen) {
        return false;
    }

    public void setScreen(ScreenAdapter screen) {
        this.screen = screen;
    }

    public void constructKeyOperation() {
        keyOperations.put(ControlsConfig.INPUT_KEY_DOWN + ControlsConfig.DOWN, () -> screen.pressDown(true));
        keyOperations.put(ControlsConfig.INPUT_KEY_DOWN + ControlsConfig.UP, () -> screen.pressUp(true));
        keyOperations.put(ControlsConfig.INPUT_KEY_DOWN + ControlsConfig.LEFT, () -> screen.pressLeft(true));
        keyOperations.put(ControlsConfig.INPUT_KEY_DOWN + ControlsConfig.RIGHT, () -> screen.pressRight(true));
        keyOperations.put(ControlsConfig.INPUT_KEY_DOWN + ControlsConfig.START, () -> screen.pressStart(true));
        keyOperations.put(ControlsConfig.INPUT_KEY_DOWN + ControlsConfig.ACTION_A, () -> screen.pressActionA(true));
        keyOperations.put(ControlsConfig.INPUT_KEY_DOWN + ControlsConfig.ACTION_Y, () -> screen.pressActionY(true));
    }
}
