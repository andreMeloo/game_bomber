package com.game.model.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.game.controller.GameControl;
import com.game.controller.InputManager;
import com.game.model.controls.ControlAdapter;
import com.game.model.controls.MenuControl;

public class MenuScreen extends ScreenAdapter implements Screen {
    final GameControl gameControl;

    public MenuScreen(final GameControl gameControl) {
        this.gameControl = gameControl;
        this.gameControl.setInputManager(new InputManager(getControler(), this));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }

    @Override
    public ControlAdapter getControler() {
        return new MenuControl();
    }
}
