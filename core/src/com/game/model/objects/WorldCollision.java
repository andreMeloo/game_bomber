package com.game.model.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class WorldCollision {
    public static Texture colisionBlockUniversal;
    public static float WIDTH_COLLISION_UNIVERSAL = 0f;
    public static float HEIGHT_COLLISION_UNIVERSAL = 0f;
    public static final String BLOCK = "block-colision.png";

    public static void init() {
        colisionBlockUniversal = new Texture(Gdx.files.internal(BLOCK));
        WIDTH_COLLISION_UNIVERSAL = colisionBlockUniversal.getWidth();
        HEIGHT_COLLISION_UNIVERSAL = colisionBlockUniversal.getHeight();
        colisionBlockUniversal.dispose();
    }


}
