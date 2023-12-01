package com.game.model.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.game.controller.GameStateManager;
import com.game.model.controls.ControlAdapter;
import com.game.model.controls.MenuControl;

public class MenuState extends GameState {
    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void initializedState() {
        if (!initialized) {

        }
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        initializedState();
        getBGColor();
    }

    @Override
    public void dispose() {
    }

    private void getBGColor() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public ControlAdapter getControler() {
        return new MenuControl();
    }
}
