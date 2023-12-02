package com.game.model.controls;

import com.game.model.state.ChoiceCharacterState;
import com.game.model.state.GameState;

public class ChoiceCaracterControl extends ControlAdapter{
    @Override
    public boolean processKeyDown(int keycode, GameState currentState) {
        ChoiceCharacterState state = (ChoiceCharacterState) currentState;
       if (ControlsConfig.isPressUP(keycode)) {
           popularState(state,4,4,3,0, .8f);
       } else if (ControlsConfig.isPressDOWN(keycode)) {
           popularState(state,1,4,3,0, -.8f);
       } else if (ControlsConfig.isPressLEFT(keycode)) {
           popularState(state,2,4, 3, -.8f, 0);
       } else if (ControlsConfig.isPressRIGHT(keycode)) {
           popularState(state, 3,4, 3, .8f, 0);
       }

        return true;
    }

    @Override
    public boolean processKeyUp(int keycode, GameState currentState) {
        ChoiceCharacterState state = (ChoiceCharacterState) currentState;
        if (ControlsConfig.isPressUP(keycode)) {
            popularState(state,4,1,0,0, 0);
        } else if (ControlsConfig.isPressDOWN(keycode)) {
            popularState(state,1,1,0,0, 0);
        } else if (ControlsConfig.isPressLEFT(keycode)) {
            popularState(state,2,1, 0, 0, 0);
        } else if (ControlsConfig.isPressRIGHT(keycode)) {
            popularState(state, 3,1, 0, 0, 0);
        }

        return true;
    }

    public void popularState(ChoiceCharacterState currentState, int row, int coluns, int frameInit, float modifPositionX, float modifPositionY) {
        currentState.setRow(row);
        currentState.setColuns(coluns);
        currentState.setFrameInit(frameInit);
        currentState.setModifPositionX(modifPositionX);
        currentState.setModifPositionY(modifPositionY);
    }
}
