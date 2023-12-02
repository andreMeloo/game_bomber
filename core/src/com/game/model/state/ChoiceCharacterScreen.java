package com.game.model.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.game.controller.GameControl;
import com.game.controller.InputManager;
import com.game.model.controls.ChoiceCaracterControl;
import com.game.model.controls.ControlAdapter;

public class ChoiceCharacterScreen extends ScreenAdapter implements Screen {
    final GameControl gameControl;

    /**
     * Statics Values
     */
    private static final String PLAYER = "cowboy.png";
    private static final float FRAME_DURATION = .25f; // Defina a duração de cada frame da animação
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

    public ChoiceCharacterScreen(final GameControl gameControl) {
        this.gameControl = gameControl;
        this.gameControl.setInputManager(new InputManager(getControler(), this));
        batch = new SpriteBatch();
        stateTime = 0f;
        positionX = 300f;
        positionY = 300f;
        modifPositionX = 0;
        modifPositionY = 0;
        row = 1;
        coluns = 1;
        loadAnimationSheet(PLAYER, TILESET_WIDTH, TILESET_HEIGHT);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1,1,1,1);
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
        batch.dispose();
    }

    @Override
    public ControlAdapter getControler() {
        return new ChoiceCaracterControl();
    }

    private void renderAnimationFrames(int totalFrames, float incrementStateTime, boolean restartAnimation) {
        stateTime += incrementStateTime;
        int nextFrame = (int) (stateTime / FRAME_DURATION) % totalFrames;
        TextureRegion currentFrame = animationFrames.get(nextFrame);

        batch.begin();
        batch.draw(currentFrame, positionX, positionY);
        batch.end();
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
