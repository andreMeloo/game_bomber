package com.game.model.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Bomb extends GameObject {

    private static final String BOMBER_SPRITE = "bomber.png";
    private static final int TILESET_WIDTH = 4;
    private static final int TILESET_HEIGHT = 5;

    public Bomb(Vector2 position, ScreenAdapter screen) {
        super(position, screen);
        setAnimationSheet(new Texture(Gdx.files.internal(BOMBER_SPRITE)));
        loadAnimationSheet();
    }

    private void loadAnimationSheet() {
        setFrames(TextureRegion.split(getAnimationSheet(), getAnimationSheet().getWidth() / TILESET_WIDTH, getAnimationSheet().getHeight() / TILESET_HEIGHT));
    }

    public void dispose() {
        super.dispose();
    }
}
