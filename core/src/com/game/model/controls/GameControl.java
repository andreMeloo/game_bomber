package com.game.model.controls;

import com.game.View.screen.PlayGameScreen;
import com.game.View.screen.ScreenAdapter;
import com.game.model.objects.GameObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class GameControl extends ControlAdapter {
    ScreenAdapter screen;
    private final Map<String, Runnable> keyOperations = new HashMap<>();
    Stack<Integer> keys = new Stack<>();

    public GameControl() {
        constructKeyOperation();
    }

    @Override
    public boolean processKeyDown(int keycode, ScreenAdapter currentScreen) {
        setScreen(currentScreen);

        if (currentScreen instanceof PlayGameScreen) {
            if (!keys.contains(keycode) && ControlsConfig.getButtonsMove().contains(keycode))
                keys.push(keycode);
        }

        Runnable operation = keyOperations.get(ControlsConfig.INPUT_KEY_DOWN + keycode);
        if (operation != null)
            operation.run();

        return true;
    }

    @Override
    public boolean processKeyUp(int keycode, ScreenAdapter currentScreen) {
        setScreen(currentScreen);

        if (currentScreen instanceof PlayGameScreen && ControlsConfig.getButtonsMove().contains(keycode)) {
            keys.removeElement(keycode);

            if (!keys.isEmpty()) {
                if (ControlsConfig.getButtonsMove().contains(keys.peek()))
                    return processKeyDown(keys.peek(), currentScreen);
            }
        }


        Runnable operation = keyOperations.get(ControlsConfig.INPUT_KEY_UP + keycode);
        if (operation != null)
            operation.run();

        return true;
    }

    public void setScreen(ScreenAdapter screen) {
        this.screen = screen;
    }

    private void constructKeyOperation() {
        keyOperations.put(ControlsConfig.INPUT_KEY_UP + ControlsConfig.DOWN, () -> screen.pressDown(false));
        keyOperations.put(ControlsConfig.INPUT_KEY_UP + ControlsConfig.LEFT, () -> screen.pressLeft(false));
        keyOperations.put(ControlsConfig.INPUT_KEY_UP + ControlsConfig.RIGHT, () -> screen.pressRight(false));
        keyOperations.put(ControlsConfig.INPUT_KEY_UP + ControlsConfig.UP, () -> screen.pressUp(false));
        keyOperations.put(ControlsConfig.INPUT_KEY_UP + ControlsConfig.ACTION_A, () -> screen.pressActionA(false));

        keyOperations.put(ControlsConfig.INPUT_KEY_DOWN + ControlsConfig.DOWN, () -> screen.pressDown(true));
        keyOperations.put(ControlsConfig.INPUT_KEY_DOWN + ControlsConfig.UP, () -> screen.pressUp(true));
        keyOperations.put(ControlsConfig.INPUT_KEY_DOWN + ControlsConfig.LEFT, () -> screen.pressLeft(true));
        keyOperations.put(ControlsConfig.INPUT_KEY_DOWN + ControlsConfig.RIGHT, () -> screen.pressRight(true));
        keyOperations.put(ControlsConfig.INPUT_KEY_DOWN + ControlsConfig.START, () -> screen.pressStart(true));
        keyOperations.put(ControlsConfig.INPUT_KEY_DOWN + ControlsConfig.ACTION_A, () -> screen.pressActionA(true));
        keyOperations.put(ControlsConfig.INPUT_KEY_DOWN + ControlsConfig.ACTION_Y, () -> screen.pressActionY(true));
    }

    public ScreenAdapter getScreen() {
        return screen;
    }

    public Map<String, Runnable> getKeyOperations() {
        return keyOperations;
    }

    public Stack<Integer> getKeys() {
        return keys;
    }

    public void setKeys(Stack<Integer> keys) {
        this.keys = keys;
    }
}
