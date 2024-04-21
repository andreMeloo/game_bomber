package com.game.View.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.game.controller.GameManager;
import com.game.controller.InputManager;
import com.game.model.controls.GameControl;
import com.game.model.objects.Bomb;

import java.util.ArrayList;
import java.util.List;

public class PlayGameScreen extends ScreenAdapter implements Screen {
    /**
     * Statics Values
     */
    private static final String PLAYER = "cowboy.png";
    private static final String MAP = "map-teste.png";
    private static final String BLOCK = "block-colision.png";
    private static final float FRAME_DURATION_PLAYER = .25f; // Defina a duração de cada frame da animação
    private static final float FRAME_DURATION_BOMB = .5f; // Defina a duração de cada frame da animação
    private static final int TILESET_WIDTH = 4;
    private static final int TILESET_HEIGHT = 4;


    /**
     * testes colisão
     */
    private Texture map;
    private Texture colision;
    private List<Rectangle> rectangleUniversalColision;

    private Texture animationSheet;
    private TextureRegion[][] frames;
    private Array<TextureRegion> animationFrames;
    private Rectangle rectanglePlayer;
    private Rectangle rectangleView;

    /**
     * Objects
     */
    private List<Bomb> activeBombsList;


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

    private ShapeRenderer shapeView;
    private ShapeRenderer shapePlayer;
    boolean isColision;


    public PlayGameScreen(final GameManager gameManager) {
        super(gameManager);
        setInputManager(new InputManager(new GameControl(), this));
        getGameManager().addInput(getInputManager());
    }

    @Override
    public void show() {
        stateTime = 0f;
        modifPositionX = 0;
        modifPositionY = 0;
        row = 1;
        coluns = 1;
        rectanglePlayer = new Rectangle();
        shapePlayer = new ShapeRenderer();
        activeBombsList = new ArrayList<>();
        shapeView = new ShapeRenderer();
        rectangleUniversalColision = new ArrayList<>();

        /**
         * testes colisão
         */
        map = new Texture(Gdx.files.internal(MAP));
        colision = new Texture(Gdx.files.internal(BLOCK));


        positionX = 300;
        positionY = 250;
        rectanglePlayer.setPosition(positionX, positionY);
        rectanglePlayer.setSize(colision.getWidth(), colision.getHeight());
        loadAnimationSheet();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getGameManager().batch.begin();
        getGameManager().batch.draw(map, Gdx.graphics.getWidth() / 2f - (map.getWidth() / 2f), (Gdx.graphics.getHeight() / 2f) - (map.getHeight() / 2f));
        getGameManager().batch.end();

        renderObjectsFrames(delta);
        treatPlayerCollision();
        loadAnimationFrame(row, coluns, modifPositionX, modifPositionY);
        renderAnimationFrames(animationFrames.size, delta);
    }

    private void treatPlayerCollision() {
        for (Bomb bomb : activeBombsList) {
            if ((modifPositionY != 0 || modifPositionX != 0)) {
                // Margem de segurança para a detecção de colisão
                float margemSeguranca = 2.0f; // Ajuste conforme necessário

                // Calcular coordenadas dos pontos relevantes para a linha superior do jogador
                Vector2 leftPlayerX = new Vector2(rectanglePlayer.x, rectanglePlayer.y - margemSeguranca);
                Vector2 leftPlayerY = new Vector2(rectanglePlayer.x, rectanglePlayer.y + rectanglePlayer.height - margemSeguranca);
                Vector2 rightPlayerX = new Vector2(rectanglePlayer.x + rectanglePlayer.width, rectanglePlayer.y - margemSeguranca);
                Vector2 rightPlayerY = new Vector2(rectanglePlayer.x + rectanglePlayer.width, rectanglePlayer.y + rectanglePlayer.height - margemSeguranca);

                if (modifPositionY < 0) {
                    if ((Intersector.intersectSegments(leftPlayerX, leftPlayerY, bomb.leftTopVectorCollision(), bomb.rightTopVectorCollision(), new Vector2())
                            || Intersector.intersectSegments(rightPlayerX, rightPlayerY, bomb.leftTopVectorCollision(), bomb.rightTopVectorCollision(), new Vector2())))
                        modifPositionY = 0;
                } else if (modifPositionY > 0) {
                    if ((Intersector.intersectSegments(leftPlayerX, leftPlayerY, bomb.leftBottomVectorCollision(), bomb.rightBottomVectorCollision(), new Vector2())
                            || Intersector.intersectSegments(rightPlayerX, rightPlayerY, bomb.leftBottomVectorCollision(), bomb.rightBottomVectorCollision(), new Vector2())))
                        modifPositionY = 0;
                } else if (modifPositionX > 0) {
                    if ((Intersector.intersectSegments(leftPlayerX, rightPlayerX, bomb.leftBottomVectorCollision(), bomb.leftTopVectorCollision(), new Vector2())
                            || Intersector.intersectSegments(leftPlayerY, rightPlayerY, bomb.leftBottomVectorCollision(), bomb.leftTopVectorCollision(), new Vector2())))
                        modifPositionX = 0;
                } else if (modifPositionX < 0) {
                    if ((Intersector.intersectSegments(leftPlayerX, rightPlayerX, bomb.rightBottomVectorCollision(), bomb.rightTopVectorCollision(), new Vector2())
                            || Intersector.intersectSegments(leftPlayerY, rightPlayerY, bomb.rightBottomVectorCollision(), bomb.rightTopVectorCollision(), new Vector2())))
                        modifPositionX = 0;
                }
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
        shapePlayer.dispose();
        map.dispose();
        colision.dispose();
        shapeView.dispose();
        for (Bomb bomb : activeBombsList) {
            bomb.dispose();
        }
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
        if (isTypeKeyPressDOWN) {
            Bomb bomb = new Bomb(new Vector2(frames[0][0].getRegionWidth() / 2f + positionX, positionY), this);
            activeBombsList.add(bomb);
        }
    }

    @Override
    public void pressActionY(boolean isTypeKeyPressDOWN) {
    }

    @Override
    public void pressStart(boolean isTypeKeyPressDOWN) {
        if (isTypeKeyPressDOWN) {
            dispose();
            getGameManager().removeInput(getInputManager());
            getGameManager().setScreen(new MenuScreen(getGameManager()));
        }
    }


    private void renderAnimationFrames(int totalFrames, float incrementStateTime) {
        stateTime += incrementStateTime;
        int nextFrame = (int) (stateTime / FRAME_DURATION_PLAYER) % totalFrames;
        TextureRegion currentFrame = animationFrames.get(nextFrame);
        rectanglePlayer.setPosition(positionX, positionY);
        shapePlayer.begin(ShapeRenderer.ShapeType.Line);
        shapePlayer.setColor(Color.RED);
        shapePlayer.rect(rectanglePlayer.x, rectanglePlayer.y, rectanglePlayer.width, rectanglePlayer.height);
        shapePlayer.end();
        getGameManager().batch.begin();
        getGameManager().batch.draw(currentFrame, positionX, positionY);
        getGameManager().batch.end();
    }

    private void renderObjectsFrames(float delta) {
        int timeFrame = 0;
        int nextFrame = 0;
        TextureRegion currentFrame = new TextureRegion();

        if (!activeBombsList.isEmpty()) {
            getGameManager().batch.begin();
            for (Bomb bomb : activeBombsList) {
                bomb.update(delta);
                timeFrame = (int) (bomb.getStateTime() / FRAME_DURATION_BOMB);
                nextFrame = timeFrame % bomb.getAnimationFrames().size;
                currentFrame = bomb.getAnimationFrames().get(nextFrame);
                bomb.getCollisionRectangle().setWidth(colision.getWidth());
                bomb.getCollisionRectangle().setHeight(colision.getHeight());
                getGameManager().batch.draw(currentFrame, bomb.getPosition().x, bomb.getPosition().y);
            }
            getGameManager().batch.end();

            shapeView.begin(ShapeRenderer.ShapeType.Line);
            shapeView.setColor(Color.RED);
            shapeView.rect(activeBombsList.get(0).getPosition().x, activeBombsList.get(0).getPosition().y, colision.getWidth(), colision.getHeight());
            shapeView.end();
        }
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
