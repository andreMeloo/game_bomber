package com.game.View.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.game.controller.GameManager;
import com.game.controller.InputManager;
import com.game.model.controls.ControlAdapter;
import com.game.model.controls.MoveCharacterControl;

public class MoveCharacterScreen extends ScreenAdapter implements Screen {
    final GameManager gameManager;

    /**
     * Statics Values
     */
    private static final String PLAYER = "cowboy.png";
    private static final float FRAME_DURATION = .25f; // Defina a duração de cada frame da animação
    private static final int TILESET_WIDTH = 4;
    private static final int TILESET_HEIGHT = 4;


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


    public MoveCharacterScreen(final GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameManager.setInputManager(new InputManager(getControler(), this));
        this.gameManager.setInput();
    }

    @Override
    public void show() {
        stateTime = 0f;
        positionX = 300f;
        positionY = 300f;
        modifPositionX = 0;
        modifPositionY = 0;
        row = 1;
        coluns = 1;
        loadAnimationSheet();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);
        loadAnimationFrame(row, coluns, modifPositionX, modifPositionY);
        renderAnimationFrames(animationFrames.size, Gdx.graphics.getDeltaTime(), true);
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
        animationSheet.dispose();
    }

    @Override
    public ControlAdapter getControler() {
        return new MoveCharacterControl();
    }

    @Override
    public boolean pressUp(boolean isTypeKeyPressDOWN) {
        return false;
    }

    @Override
    public boolean pressDown(boolean isTypeKeyPressDOWN) {
        return false;
    }

    @Override
    public boolean pressLeft(boolean isTypeKeyPressDOWN) {
        return false;
    }

    @Override
    public boolean pressRight(boolean isTypeKeyPressDOWN) {
        return false;
    }

    @Override
    public boolean pressActionA(boolean isTypeKeyPressDOWN) {
        return false;
    }

    @Override
    public boolean pressActionY(boolean isTypeKeyPressDOWN) {
        return false;
    }

    @Override
    public boolean pressStart(boolean isTypeKeyPressDOWN) {
        dispose();
        gameManager.setScreen(new MenuScreen(gameManager));
        return true;
    }


    private void renderAnimationFrames(int totalFrames, float incrementStateTime, boolean restartAnimation) {
        stateTime += incrementStateTime;
        int nextFrame = (int) (stateTime / FRAME_DURATION) % totalFrames;
        TextureRegion currentFrame = animationFrames.get(nextFrame);

        gameManager.batch.begin();
        gameManager.batch.draw(currentFrame, positionX, positionY);
        gameManager.batch.end();
    }

    private void loadAnimationFrame(int row, int coluns, float modifPositionX, float modifPositionY) {
        this.row = row;
        animationFrames = new Array<>();
        for (int i = this.row - 1; i < this.row; i++) {
            for (int j = 0; j < coluns; j++) {
                animationFrames.add(frames[i][j]);
            }
        }
        positionX += modifPositionX;
        positionY += modifPositionY;
    }

    private void loadAnimationSheet() {
        animationSheet = new Texture(Gdx.files.internal(PLAYER));
        frames = TextureRegion.split(animationSheet, animationSheet.getWidth() / TILESET_WIDTH, animationSheet.getHeight() / TILESET_HEIGHT);
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

    public Texture getAnimationSheet() {
        return animationSheet;
    }

    public void setAnimationSheet(Texture animationSheet) {
        this.animationSheet = animationSheet;
    }

    public TextureRegion[][] getFrames() {
        return frames;
    }

    public void setFrames(TextureRegion[][] frames) {
        this.frames = frames;
    }

    public Array<TextureRegion> getAnimationFrames() {
        return animationFrames;
    }

    public void setAnimationFrames(Array<TextureRegion> animationFrames) {
        this.animationFrames = animationFrames;
    }
}
