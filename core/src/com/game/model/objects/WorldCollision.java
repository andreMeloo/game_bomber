package com.game.model.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class WorldCollision {
    public static Texture colisionBlockUniversal;
    public static float WIDTH_COLLISION_UNIVERSAL = 0f;
    public static float HEIGHT_COLLISION_UNIVERSAL = 0f;
    public static final String BLOCK = "fixed-block-colision.png";

    private Rectangle rectangleCollision;

    public static void init() {
        colisionBlockUniversal = new Texture(Gdx.files.internal(BLOCK));
        WIDTH_COLLISION_UNIVERSAL = colisionBlockUniversal.getWidth();
        HEIGHT_COLLISION_UNIVERSAL = colisionBlockUniversal.getHeight();
        colisionBlockUniversal.dispose();
    }

    public WorldCollision(Vector2 position) {
        rectangleCollision = new Rectangle(position.x, position.y, WIDTH_COLLISION_UNIVERSAL, HEIGHT_COLLISION_UNIVERSAL);
    }

    public Rectangle getCollision() {
        return rectangleCollision;
    }

    public void setCollision(Rectangle collision) {
        this.rectangleCollision = collision;
    }
}
