package com.game.model.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class PowerAttribute extends GameObject {

    private ObjectAttributes principalAttribute;
    private static final String SPEED_ATTRIBUTE = "velocity-attribute.png";
    private static final int TILESET_WIDTH = 1;
    private static final int TILESET_HEIGHT = 5;
    private static final float FRAME_DURATION_SPEED_ATTRIBUTE = .1f;

    public PowerAttribute(Vector2 position, Screen screen, ObjectAttributes objAttribute) {
        super(position, screen);
        principalAttribute = objAttribute;
        setAnimationSheet(new Texture(Gdx.files.internal(SPEED_ATTRIBUTE)));
        loadAnimationSheet();
        updatePositionAnimation();
    }

    private void loadAnimationSheet() {
        setFrames(TextureRegion.split(getAnimationSheet(), getAnimationSheet().getWidth() / TILESET_WIDTH, getAnimationSheet().getHeight() / TILESET_HEIGHT));
        setWidth(((float) getAnimationSheet().getWidth() / TILESET_WIDTH) - getCollisionRectangle().getWidth());
    }

    public TextureRegion getCurrentFrame(float delta) {
        return super.getCurrentFrame(FRAME_DURATION_SPEED_ATTRIBUTE, delta);
    }

    public void dispose() {
        super.dispose();
    }

    public ObjectAttributes getPrincipalAttribute() {
        return principalAttribute;
    }

    public void setPrincipalAttribute(ObjectAttributes principalAttribute) {
        this.principalAttribute = principalAttribute;
    }
}
