package com.game.View.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.game.controller.GameManager;
import com.game.controller.InputManager;
import com.game.model.controls.GameControl;

public class PlayGameScreen extends ScreenAdapter implements Screen {
    final GameManager gameManager;
    private InputManager inputManager;


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
    private Rectangle rectanglePlayer;
    private Rectangle rectangleView;


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


    public PlayGameScreen(final GameManager gameManager) {
        this.gameManager = gameManager;
        inputManager = new InputManager(new GameControl(), this);
        this.gameManager.addInput(inputManager);
        rectangleView = new Rectangle(10,10, Gdx.graphics.getWidth() - 20, Gdx.graphics.getHeight() - 20);
    }

    @Override
    public void show() {
        stateTime = 0f;
        positionX = Gdx.graphics.getWidth() / 2f;
        positionY = Gdx.graphics.getHeight() / 2f;
        modifPositionX = 0;
        modifPositionY = 0;
        row = 1;
        coluns = 1;
        rectanglePlayer = new Rectangle();
        loadAnimationSheet();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        treatPlayerCollision();

        loadAnimationFrame(row, coluns, modifPositionX, modifPositionY);
        renderAnimationFrames(animationFrames.size, delta);
    }

    private void treatPlayerCollision() {
        if (!rectangleView.contains(rectanglePlayer)) {
            if (rectanglePlayer.y < rectangleView.y && modifPositionY < 0) {
                modifPositionY = 0;
            } else if (rectanglePlayer.y + rectanglePlayer.height > rectangleView.y + rectangleView.height && modifPositionY > 0) {
                modifPositionY = 0;
            } else if (rectanglePlayer.x < rectangleView.x && modifPositionX < 0) {
                modifPositionX = 0;
            } else if (rectanglePlayer.x + rectanglePlayer.width > rectangleView.x + rectangleView.width && modifPositionX > 0) {
                modifPositionX = 0;
            }
        }
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
    public void pressUp(boolean isTypeKeyPressDOWN) {
        if (!isTypeKeyPressDOWN) {
            row = 4;
            coluns = 1;
            modifPositionX = 0;
            modifPositionY = 0;
        } else {
            row = 4;
            coluns = 4;
            modifPositionX = 0;
            modifPositionY = 2f;
        }
    }

    @Override
    public void pressDown(boolean isTypeKeyPressDOWN) {
        if (!isTypeKeyPressDOWN) {
            row = 1;
            coluns = 1;
            modifPositionX = 0;
            modifPositionY = 0;
        } else {
            row = 1;
            coluns = 4;
            modifPositionX = 0;
            modifPositionY = -2f;
        }
    }

    @Override
    public void pressLeft(boolean isTypeKeyPressDOWN) {
        if (!isTypeKeyPressDOWN) {
            row = 2;
            coluns = 1;
            modifPositionX = 0;
            modifPositionY = 0;
        } else {
            row = 2;
            coluns = 4;
            modifPositionX = -2f;
            modifPositionY = 0;
        }
    }

    @Override
    public void pressRight(boolean isTypeKeyPressDOWN) {
        if (!isTypeKeyPressDOWN) {
            row = 3;
            coluns = 1;
            modifPositionX = 0;
            modifPositionY = 0;
        } else {
            row = 3;
            coluns = 4;
            modifPositionX = 2f;
            modifPositionY = 0;
        }
    }

    @Override
    public void pressActionA(boolean isTypeKeyPressDOWN) {
    }

    @Override
    public void pressActionY(boolean isTypeKeyPressDOWN) {
    }

    @Override
    public void pressStart(boolean isTypeKeyPressDOWN) {
        if (isTypeKeyPressDOWN) {
            dispose();
            gameManager.removeInput(inputManager);
            gameManager.setScreen(new MenuScreen(gameManager));
        }
    }


    private void renderAnimationFrames(int totalFrames, float incrementStateTime) {
        stateTime += incrementStateTime;
        int nextFrame = (int) (stateTime / FRAME_DURATION) % totalFrames;
        TextureRegion currentFrame = animationFrames.get(nextFrame);
        rectanglePlayer.setPosition(positionX, positionY);
        rectanglePlayer.setSize(currentFrame.getRegionWidth() - 2f, currentFrame.getRegionHeight());
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
