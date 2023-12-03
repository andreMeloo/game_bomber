package com.game.model.controls;

import com.badlogic.gdx.Input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControlsConfig {
    public final static int UP = Input.Keys.UP;
    public final static int DOWN = Input.Keys.DOWN;
    public final static int LEFT = Input.Keys.LEFT;
    public final static int RIGHT = Input.Keys.RIGHT;
    public final static int ACTION_A = Input.Keys.D;
    public final static int ACTION_Y = Input.Keys.A;
    public final static int START = Input.Keys.ENTER;
    public final static String INPUT_KEY_UP = "1";
    public final static String INPUT_KEY_DOWN = "2";

    public static boolean isPressUP(int keyCode) {
        return keyCode == UP;
    }

    public static boolean isPressDOWN(int keyCode) {
        return keyCode == DOWN;
    }

    public static boolean isPressLEFT(int keyCode) {
        return keyCode == LEFT;
    }

    public static boolean isPressRIGHT(int keyCode) {
        return keyCode == RIGHT;
    }

    public static boolean isPressACTIONA(int keyCode) {
        return keyCode == ACTION_A;
    }

    public static boolean isPressACTIONY(int keyCode) {
        return keyCode == ACTION_Y;
    }

    public static boolean isPressSTART(int keyCode) {
        return keyCode == START;
    }


    public static List<Integer> getAllButtons() {
        return Arrays.asList(
                UP, DOWN, LEFT, RIGHT, ACTION_A, ACTION_Y, START
        );
    }

    public static List<Integer> getButtonsMove() {
        return Arrays.asList(
                UP, DOWN, LEFT, RIGHT
        );
    }

    public static List<Integer> getButtonsActions() {
        return Arrays.asList(
                ACTION_A, ACTION_Y, START
        );
    }

    public String toString(int controlConfig) {
        return String.valueOf(controlConfig);
    }
}
