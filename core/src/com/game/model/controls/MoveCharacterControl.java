package com.game.model.controls;

import com.game.View.screen.MoveCharacterScreen;
import com.game.View.screen.ScreenAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class MoveCharacterControl extends ControlAdapter {

    private final Map<String, Runnable> keyOperations = new HashMap<>();
    Stack<Integer> keys = new Stack<>();
    private MoveCharacterScreen screen;

    @Override
    public boolean processKeyDown(int keycode, ScreenAdapter currentScreen) {
        setScreen((MoveCharacterScreen) currentScreen);
        constructKeyOperation();

        if (!keys.contains(keycode))
            keys.push(keycode);

        Runnable operation = keyOperations.get(ControlsConfig.INPUT_KEY_DOWN + keycode);
        if (operation != null)
            operation.run();


        return true;
    }

    @Override
    public boolean processKeyUp(int keycode, ScreenAdapter currentScreen) {
        keys.removeElement(keycode);
        constructKeyOperation();

        if (!keys.isEmpty()) {
            if (ControlsConfig.getButtonsMove().contains(keys.peek()))
                return processKeyDown(keys.peek(), currentScreen);
        }

        if (ControlsConfig.getButtonsMove().contains(keycode)) {
            Runnable operation = keyOperations.get(ControlsConfig.INPUT_KEY_UP + keycode);
            if (operation != null)
                operation.run();
        }

        return true;
    }

    public void popularState(int row, int columns, float modifPositionX, float modifPositionY) {
        getScreen().setRow(row);
        getScreen().setColuns(columns);
        getScreen().setModifPositionX(modifPositionX);
        getScreen().setModifPositionY(modifPositionY);
    }

    private void constructKeyOperation() {
        keyOperations.put(ControlsConfig.INPUT_KEY_UP + ControlsConfig.DOWN, () -> popularState(1, 1, 0, 0));
        keyOperations.put(ControlsConfig.INPUT_KEY_UP + ControlsConfig.LEFT, () -> popularState(2, 1, 0, 0));
        keyOperations.put(ControlsConfig.INPUT_KEY_UP + ControlsConfig.RIGHT, () -> popularState(3, 1, 0, 0));
        keyOperations.put(ControlsConfig.INPUT_KEY_UP + ControlsConfig.UP, () -> popularState(4, 1, 0, 0));
        keyOperations.put(ControlsConfig.INPUT_KEY_DOWN + ControlsConfig.DOWN, () -> popularState(1, 4, 0, -2f));
        keyOperations.put(ControlsConfig.INPUT_KEY_DOWN + ControlsConfig.LEFT, () -> popularState(2, 4, -2f, 0));
        keyOperations.put(ControlsConfig.INPUT_KEY_DOWN + ControlsConfig.RIGHT, () -> popularState(3, 4, 2f, 0));
        keyOperations.put(ControlsConfig.INPUT_KEY_DOWN + ControlsConfig.UP, () -> popularState(4, 4, 0, 2f));
        keyOperations.put(ControlsConfig.INPUT_KEY_DOWN + ControlsConfig.START, () -> screen.pressStart(true));
    }

    public MoveCharacterScreen getScreen() {
        return screen;
    }

    public void setScreen(MoveCharacterScreen screen) {
        this.screen = screen;
    }

}
