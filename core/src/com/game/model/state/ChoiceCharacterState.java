package com.game.model.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.game.controller.GameStateManager;
import com.game.model.controls.ChoiceCaracterControl;
import com.game.model.controls.ControlAdapter;

public class ChoiceCharacterState extends GameState {
    /**
     * Statics Values
     */
    private static final String PLAYER = "cowboy.png";
    private static final float FRAME_DURATION = .2f; // Defina a duração de cada frame da animação
    private static final int TILESET_WIDTH = 4;
    private static final int TILESET_HEIGHT = 4;


    SpriteBatch batch;
    private Texture animationSheet;
    private TextureRegion[][] frames;
    private Array<TextureRegion> animationFrames;


    /**
     * Config State
     */
    private float stateTime;
    private float positionX = 0f;
    private float positionY = 0f;
    private int row;
    private int coluns;
    private float modifPositionX;
    private float modifPositionY;

    public ChoiceCharacterState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void initializedState() {
        if (!initialized) {
            batch = new SpriteBatch();
            stateTime = 0f;
            positionX = 300f;
            positionY = 300f;
            modifPositionX = 0;
            modifPositionY = 0;
            row = 1;
            coluns = 1;
            loadAnimationSheet(PLAYER, TILESET_WIDTH, TILESET_HEIGHT);
            loadAnimationFrame(row,coluns,modifPositionX,modifPositionY);
            initialized = true;
        }
    }

    @Override
    public void update() {
        if (initialized)
            loadAnimationFrame(row, coluns, modifPositionX, modifPositionY);
    }

    @Override
    public void render() {
        initializedState();
        setBGColor();
        renderAnimationFrames(animationFrames.size, Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {
        animationSheet.dispose();
    }

    @Override
    public ControlAdapter getControler() {
        return new ChoiceCaracterControl();
    }

    private void renderAnimationFrames(int totalFrames, float incrementStateTime) {
        stateTime += incrementStateTime;
        int nextFrame = (int) (stateTime / FRAME_DURATION) % totalFrames;
        TextureRegion currentFrame = animationFrames.get(nextFrame);

        batch.begin();
        batch.draw(currentFrame, positionX, positionY);
        batch.end();
    }

    private void setBGColor() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void loadAnimationFrame(int row, int coluns, float modifPositionX, float modifPositionY) {
        this.row = row;
        animationFrames = new Array<>();
        for (int i = this.row - 1; i < this.row; i++) {
            for (int j = coluns - 1 ; j >= 0; j--) {
                animationFrames.add(frames[i][j]);
            }
        }
        positionX += modifPositionX;
        positionY += modifPositionY;
    }

    private void loadAnimationSheet(String sheet, int tileRows, int tileColuns) {
        animationSheet = new Texture(Gdx.files.internal(sheet));
        frames = TextureRegion.split(animationSheet, animationSheet.getWidth() / tileRows, animationSheet.getHeight() / tileColuns);
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColuns() {
        return coluns;
    }

    public void setColuns(int coluns) {
        this.coluns = coluns;
    }

    public float getModifPositionX() {
        return modifPositionX;
    }

    public void setModifPositionX(float modifPositionX) {
        this.modifPositionX = modifPositionX;
    }

    public float getModifPositionY() {
        return modifPositionY;
    }

    public void setModifPositionY(float modifPositionY) {
        this.modifPositionY = modifPositionY;
    }
}
