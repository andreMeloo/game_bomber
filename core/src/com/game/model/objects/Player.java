package com.game.model.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player extends GameObject{

    private static final String PLAYER_SPRITE = "cowboy.png";
    private static final float FRAME_DURATION_PLAYER = .15f;
    private static final int TILESET_WIDTH = 4;
    private static final int TILESET_HEIGHT = 4;
    public static final float ACCELERATE_PLAYER = 2f;

    // Atributos para controle dos frames do player
    private int rowFrame;
    private int collunsFrames;
    private boolean plantBombActive = false;
    public static final int NUMBER_ROW_FRAMES_1 = 1;
    public static final int NUMBER_ROW_FRAMES_2 = 2;
    public static final int NUMBER_ROW_FRAMES_3 = 3;
    public static final int NUMBER_ROW_FRAMES_4 = 4;
    public static final int COLLUNS_FRAMES_STOP = 1;
    public static final int COLLUNS_FRAMES_MOVE = 4;


    public Player(Vector2 position, Screen screen) {
        super(position, screen);
        rowFrame = 1;
        collunsFrames = 1;
        setAnimationSheet(new Texture(Gdx.files.internal(PLAYER_SPRITE)));
        loadAnimationSheet();
        updatePositionAnimation();
    }

    private void loadAnimationSheet() {
        setFrames(TextureRegion.split(getAnimationSheet(), getAnimationSheet().getWidth() / TILESET_WIDTH, getAnimationSheet().getHeight() / TILESET_HEIGHT));
        setWidth(((float) getAnimationSheet().getWidth() / TILESET_WIDTH) - getCollisionRectangle().getWidth());
    }

    public TextureRegion getCurrentFrame(float delta) {
        return super.getCurrentFrame(FRAME_DURATION_PLAYER, delta);
    }

    public void dispose() { super.dispose(); }

    public int getRowFrame() {
        return rowFrame;
    }

    public void setRowFrame(int rowFrame) {
        this.rowFrame = rowFrame;
    }

    public int getCollunsFrames() {
        return collunsFrames;
    }

    public void setCollunsFrames(int collunsFrames) {
        this.collunsFrames = collunsFrames;
    }

    public boolean isPlantBombActive() {
        return plantBombActive;
    }

    public void setPlantBombActive(boolean plantBombActive) {
        this.plantBombActive = plantBombActive;
    }
}
