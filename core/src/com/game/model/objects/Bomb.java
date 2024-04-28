package com.game.model.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Bomb extends GameObject {

    private static final String BOMBER_SPRITE = "bomber.png";
    private static final int TILESET_WIDTH = 4;
    private static final int TILESET_HEIGHT = 3;
    private static final float FRAME_DURATION_BOMB = .3f;
    private boolean collisionActive = false;

    public Bomb(Vector2 position, Screen screen) {
        super(position, screen);
        setAnimationSheet(new Texture(Gdx.files.internal(BOMBER_SPRITE)));
        loadAnimationSheet();
        updatePositionAnimation();
    }

    private void loadAnimationSheet() {
        setFrames(TextureRegion.split(getAnimationSheet(), getAnimationSheet().getWidth() / TILESET_WIDTH, getAnimationSheet().getHeight() / TILESET_HEIGHT));
        setWidth(getCollisionRectangle().getWidth() - ((float) getAnimationSheet().getWidth() / TILESET_WIDTH));
    }

    public TextureRegion getCurrentFrame(float delta) {
        return super.getCurrentFrame(FRAME_DURATION_BOMB, delta);
    }

    public void dispose() {
        super.dispose();
    }

    public boolean isCollisionActive() {
        return collisionActive;
    }

    public void setCollisionActive(boolean collisionActive) {
        this.collisionActive = collisionActive;
    }
}
