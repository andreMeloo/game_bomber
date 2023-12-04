package com.game.model.controls;

import com.game.View.screen.MenuScreen;
import com.game.View.screen.ScreenAdapter;

import java.util.HashMap;
import java.util.Map;

public class MenuControl extends ControlAdapter{
    MenuScreen screen;
    private final Map<String, Runnable> keyOperations = new HashMap<>();

    public MenuControl() {
        constructKeyOperation();
    }

    @Override
    public boolean processKeyDown(int keycode, ScreenAdapter currentScreen) {
        setScreen((MenuScreen) currentScreen);
        Runnable operation = keyOperations.get(ControlsConfig.INPUT_KEY_DOWN + keycode);
        if (operation != null)
            operation.run();

        return true;
    }

    @Override
    public boolean processKeyUp(int keycode, ScreenAdapter currentScreen) {
        return false;
    }

    public void setScreen(MenuScreen screen) {
        this.screen = screen;
    }

    public void constructKeyOperation() {
        keyOperations.put(ControlsConfig.INPUT_KEY_DOWN + ControlsConfig.DOWN, () -> screen.pressDown());
        keyOperations.put(ControlsConfig.INPUT_KEY_DOWN + ControlsConfig.UP, () -> screen.pressUp());
        keyOperations.put(ControlsConfig.INPUT_KEY_DOWN + ControlsConfig.START, () -> screen.pressStart());
        keyOperations.put(ControlsConfig.INPUT_KEY_DOWN + ControlsConfig.ACTION_A, () -> screen.pressActionA());
    }
}
