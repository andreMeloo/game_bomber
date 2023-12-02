package com.game.model.controls;

import com.game.model.state.ChoiceCharacterScreen;
import com.game.model.state.ScreenAdapter;

import java.util.*;

public class ChoiceCaracterControl extends ControlAdapter {

    private final Map<String, Runnable> keyOperations = new HashMap<>();
    private final String INPUT_KEY_DOWN = "1";
    private final String INPUT_KEY_UP = "2";

    Stack<Integer> keys = new Stack<>();
    private ChoiceCharacterScreen screen;

    @Override
    public boolean processKeyDown(int keycode, ScreenAdapter currentScreen) {
        setScreen((ChoiceCharacterScreen) currentScreen);
        constructKeyOperation();

        if (!keys.contains(keycode))
            keys.push(keycode);

        Runnable operation = keyOperations.get(INPUT_KEY_DOWN + keycode);
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
            Runnable operation = keyOperations.get(INPUT_KEY_UP + keycode);
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

    public void constructKeyOperation() {
        keyOperations.put(INPUT_KEY_UP + ControlsConfig.DOWN, () -> popularState(1, 1, 0, 0));
        keyOperations.put(INPUT_KEY_UP + ControlsConfig.LEFT, () -> popularState(2, 1, 0, 0));
        keyOperations.put(INPUT_KEY_UP + ControlsConfig.RIGHT, () -> popularState(3, 1, 0, 0));
        keyOperations.put(INPUT_KEY_UP + ControlsConfig.UP, () -> popularState(4, 1, 0, 0));
        keyOperations.put(INPUT_KEY_DOWN + ControlsConfig.DOWN, () -> popularState(1, 4, 0, -1f));
        keyOperations.put(INPUT_KEY_DOWN + ControlsConfig.LEFT, () -> popularState(2, 4, -1f, 0));
        keyOperations.put(INPUT_KEY_DOWN + ControlsConfig.RIGHT, () -> popularState(3, 4, 1f, 0));
        keyOperations.put(INPUT_KEY_DOWN + ControlsConfig.UP, () -> popularState(4, 4, 0, 1f));
    }

    public ChoiceCharacterScreen getScreen() {
        return screen;
    }

    public void setScreen(ChoiceCharacterScreen screen) {
        this.screen = screen;
    }
}
