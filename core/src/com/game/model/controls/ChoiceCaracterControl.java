package com.game.model.controls;

import com.game.model.state.ChoiceCharacterScreen;
import com.game.model.state.ScreenAdapter;

import java.util.*;

public class ChoiceCaracterControl extends ControlAdapter {
    Stack<Integer> keys = new Stack<>();

    @Override
    public boolean processKeyDown(int keycode, ScreenAdapter currentScreen) {
        ChoiceCharacterScreen state = (ChoiceCharacterScreen) currentScreen;
        if (!keys.contains(keycode))
            keys.push(keycode);

        // Lógica para processar apenas a última tecla pressionada
        if (ControlsConfig.isPressUP(keycode)) {
            popularState(state, 4, 4, 0, .7f);
        } else if (ControlsConfig.isPressDOWN(keycode)) {
            popularState(state, 1, 4, 0, -.7f);
        } else if (ControlsConfig.isPressLEFT(keycode)) {
            popularState(state, 2, 4, -.7f, 0);
        } else if (ControlsConfig.isPressRIGHT(keycode)) {
            popularState(state, 3, 4, .7f, 0);
        }


        return true;
    }

    @Override
    public boolean processKeyUp(int keycode, ScreenAdapter currentScreen) {
        keys.removeElement(keycode);

        if (!keys.isEmpty()) {
            return processKeyDown(keys.peek(), currentScreen);
        }

        ChoiceCharacterScreen state = (ChoiceCharacterScreen) currentScreen;
        if (ControlsConfig.isPressUP(keycode)) {
            popularState(state, 4, 1, 0, 0);
        } else if (ControlsConfig.isPressDOWN(keycode)) {
            popularState(state, 1, 1, 0, 0);
        } else if (ControlsConfig.isPressLEFT(keycode)) {
            popularState(state, 2, 1, 0, 0);
        } else if (ControlsConfig.isPressRIGHT(keycode)) {
            popularState(state, 3, 1, 0, 0);
        }

        return true;
    }

    public void popularState(ChoiceCharacterScreen currentState, int row, int columns, float modifPositionX, float modifPositionY) {
        currentState.setRow(row);
        currentState.setColuns(columns);
        currentState.setModifPositionX(modifPositionX);
        currentState.setModifPositionY(modifPositionY);
    }
}
