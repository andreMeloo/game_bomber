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
import com.game.model.objects.Player;
import com.game.model.objects.WorldCollision;

import java.util.ArrayList;
import java.util.List;

public class PlayGameScreen extends ScreenAdapter implements Screen {
    /**
     * Statics Values
     */
    private static final String MAP = "map-teste.png";

    /**
     * testes colisão
     */
    private Texture map;

    /**
     * Objects
     */
    private List<Bomb> activeBombsList;

    /**
     * Config State
     */
    private float stateTime;
    Player playerTeste;
    Vector2 initCollisionMap;
    Vector2 positionMap;

    public PlayGameScreen(final GameManager gameManager) {
        super(gameManager);
        setInputManager(new InputManager(new GameControl(), this));
        getGameManager().addInput(getInputManager());
    }

    @Override
    public void show() {
        stateTime = 0f;
        activeBombsList = new ArrayList<>();
        WorldCollision.init();

        /**
         * testes colisão
         */
        map = new Texture(Gdx.files.internal(MAP));

        positionMap = new Vector2(Gdx.graphics.getWidth() / 2f - (map.getWidth() / 2f), (Gdx.graphics.getHeight() / 2f) - (map.getHeight() / 2f));
        initCollisionMap = new Vector2(positionMap.x + 53f, positionMap.y + 46f);
        playerTeste = new Player(new Vector2(positionMap.x + 200f, positionMap.y + 200f), this);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getGameManager().batch.begin();
        getGameManager().batch.draw(map, positionMap.x, positionMap.y);
        getGameManager().batch.end();

        renderObjectsFrames(delta);
        treatPlayerCollision();
        renderAnimationFrames(delta);
    }

    private void treatPlayerCollision() {
        ShapeRenderer view = new ShapeRenderer();
        view.begin(ShapeRenderer.ShapeType.Line);
        view.setColor(Color.RED);
        view.rect(initCollisionMap.x, initCollisionMap.y, map.getWidth() - (53f * 2), map.getHeight() - (46f * 2));
        view.end();

        if ((playerTeste.getVelocity().x != 0 || playerTeste.getVelocity().y != 0)) {
            for (Bomb bomb : activeBombsList) {
                // Calcular coordenadas dos pontos relevantes para a linha superior do jogador
                Vector2 leftBottomVectorCollision = new Vector2(playerTeste.getCollisionRectangle().getX(), playerTeste.getCollisionRectangle().getY());
                Vector2 leftTopVectorCollision = new Vector2(playerTeste.getCollisionRectangle().getX(), playerTeste.getCollisionRectangle().getY() + playerTeste.getCollisionRectangle().getHeight());
                Vector2 rightBottomVectorCollision = new Vector2(playerTeste.getCollisionRectangle().getX() + playerTeste.getCollisionRectangle().getWidth(), playerTeste.getCollisionRectangle().getY());
                Vector2 rightTopVectorCollision = new Vector2(playerTeste.getCollisionRectangle().getX() + playerTeste.getCollisionRectangle().getWidth(), playerTeste.getCollisionRectangle().getY() + playerTeste.getCollisionRectangle().getHeight());

                if (playerTeste.getVelocity().y < 0) {
                    leftBottomVectorCollision.y += playerTeste.getVelocity().y;
                    rightBottomVectorCollision.y += playerTeste.getVelocity().y;
                    if ((Intersector.intersectSegments(leftBottomVectorCollision, leftTopVectorCollision, bomb.leftTopVectorCollision(), bomb.rightTopVectorCollision(), new Vector2())
                            || Intersector.intersectSegments(rightBottomVectorCollision, rightTopVectorCollision, bomb.leftTopVectorCollision(), bomb.rightTopVectorCollision(), new Vector2())))
                        playerTeste.getVelocity().y = 0;
                } else if (playerTeste.getVelocity().y > 0) {
                    leftTopVectorCollision.y += playerTeste.getVelocity().y;
                    rightTopVectorCollision.y += playerTeste.getVelocity().y;
                    if ((Intersector.intersectSegments(leftBottomVectorCollision, leftTopVectorCollision, bomb.leftBottomVectorCollision(), bomb.rightBottomVectorCollision(), new Vector2())
                            || Intersector.intersectSegments(rightBottomVectorCollision, rightTopVectorCollision, bomb.leftBottomVectorCollision(), bomb.rightBottomVectorCollision(), new Vector2())))
                        playerTeste.getVelocity().y = 0;
                } else if (playerTeste.getVelocity().x > 0) {
                    rightTopVectorCollision.x += playerTeste.getVelocity().x;
                    rightBottomVectorCollision.x += playerTeste.getVelocity().x;
                    if ((Intersector.intersectSegments(leftBottomVectorCollision, rightBottomVectorCollision, bomb.leftBottomVectorCollision(), bomb.leftTopVectorCollision(), new Vector2())
                            || Intersector.intersectSegments(leftTopVectorCollision, rightTopVectorCollision, bomb.leftBottomVectorCollision(), bomb.leftTopVectorCollision(), new Vector2())))
                        playerTeste.getVelocity().x = 0;
                } else if (playerTeste.getVelocity().x < 0) {
                    leftTopVectorCollision.x += playerTeste.getVelocity().x;
                    leftBottomVectorCollision.x += playerTeste.getVelocity().x;
                    if ((Intersector.intersectSegments(leftBottomVectorCollision, rightBottomVectorCollision, bomb.rightBottomVectorCollision(), bomb.rightTopVectorCollision(), new Vector2())
                            || Intersector.intersectSegments(leftTopVectorCollision, rightTopVectorCollision, bomb.rightBottomVectorCollision(), bomb.rightTopVectorCollision(), new Vector2())))
                        playerTeste.getVelocity().x = 0;
                }
            }

            Rectangle viewRectangle = new Rectangle(initCollisionMap.x, initCollisionMap.y, map.getWidth() - (53f * 2), map.getHeight() - (46f * 2));
            Rectangle retanglePlayerMoviment = new Rectangle(playerTeste.getCollisionRectangle().x + playerTeste.getVelocity().x, playerTeste.getCollisionRectangle().y + playerTeste.getVelocity().y, playerTeste.getCollisionRectangle().getWidth(), playerTeste.getCollisionRectangle().getHeight());
            if (!viewRectangle.contains(retanglePlayerMoviment)) {
                playerTeste.stop();
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
        map.dispose();
        playerTeste.dispose();
        for (Bomb bomb : activeBombsList) {
            bomb.dispose();
        }
    }

    @Override
    public void pressUp(boolean isTypeKeyPressDOWN) {
        if (!isTypeKeyPressDOWN) {
            playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_4);
            playerTeste.setCollunsFrames(Player.COLLUNS_FRAMES_STOP);
            playerTeste.stop();
        } else {
            playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_4);
            playerTeste.setCollunsFrames(Player.COLLUNS_FRAMES_MOVE);
            playerTeste.accelerate(0f, Player.ACCELERATE_PLAYER);
        }
    }

    @Override
    public void pressDown(boolean isTypeKeyPressDOWN) {
        if (!isTypeKeyPressDOWN) {
            playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_1);
            playerTeste.setCollunsFrames(Player.COLLUNS_FRAMES_STOP);
            playerTeste.stop();
        } else {
            playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_1);
            playerTeste.setCollunsFrames(Player.COLLUNS_FRAMES_MOVE);
            playerTeste.accelerate(0f, -(Player.ACCELERATE_PLAYER));
        }
    }

    @Override
    public void pressLeft(boolean isTypeKeyPressDOWN) {
        if (!isTypeKeyPressDOWN) {
            playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_2);
            playerTeste.setCollunsFrames(Player.COLLUNS_FRAMES_STOP);
            playerTeste.stop();
        } else {
            playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_2);
            playerTeste.setCollunsFrames(Player.COLLUNS_FRAMES_MOVE);
            playerTeste.accelerate(-(Player.ACCELERATE_PLAYER), 0f);
        }
    }

    @Override
    public void pressRight(boolean isTypeKeyPressDOWN) {
        if (!isTypeKeyPressDOWN) {
            playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_3);
            playerTeste.setCollunsFrames(Player.COLLUNS_FRAMES_STOP);
            playerTeste.stop();
        } else {
            playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_3);
            playerTeste.setCollunsFrames(Player.COLLUNS_FRAMES_MOVE);
            playerTeste.accelerate(Player.ACCELERATE_PLAYER, 0f);
        }
    }

    @Override
    public void pressActionA(boolean isTypeKeyPressDOWN) {
        if (isTypeKeyPressDOWN) {
            Bomb bomb = new Bomb(new Vector2(playerTeste.getFrames()[0][0].getRegionWidth() / 2f + playerTeste.getPosition().x, playerTeste.getPosition().y), this);
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


    private void renderAnimationFrames(float incrementStateTime) {
        playerTeste.update(incrementStateTime, playerTeste.getRowFrame(), playerTeste.getCollunsFrames());
        getGameManager().batch.begin();
        getGameManager().batch.draw(playerTeste.getCurrentFrame(incrementStateTime), playerTeste.getPosition().x, playerTeste.getPosition().y);
        getGameManager().batch.end();
    }

    private void renderObjectsFrames(float delta) {
        if (!activeBombsList.isEmpty()) {
            getGameManager().batch.begin();
            for (Bomb bomb : activeBombsList) {
                bomb.update(delta);
                getGameManager().batch.draw(bomb.getCurrentFrame(delta), bomb.getPosition().x, bomb.getPosition().y);
            }
            getGameManager().batch.end();
        }
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }
}
