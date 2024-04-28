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
import com.game.model.controls.ControlsConfig;
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
    Rectangle initCollisionMap;
    Vector2 positionMap;
    ShapeRenderer view;

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
        initCollisionMap = new Rectangle(positionMap.x + 47f, positionMap.y + 41f, 626,  530);
        playerTeste = new Player(new Vector2(positionMap.x + 200f, positionMap.y + 200f), this);
    }

    @Override
    public void render(float delta) {
        view = new ShapeRenderer();
        view.begin(ShapeRenderer.ShapeType.Line);
        view.setColor(Color.RED);
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
        Vector2 velocity = new Vector2(playerTeste.getVelocity());
        Vector2 position = new Vector2();
        playerTeste.getCollisionRectangle().getPosition(position);

        // Trata colisões verticais
        handleVerticalCollisions(position, velocity);

        // Trata colisões horizontais
        handleHorizontalCollisions(position, velocity);

        playerTeste.setVelocity(velocity);
    }

    private void handleVerticalCollisions(Vector2 position, Vector2 velocity) {
        for (int i = 0; i < Math.abs(velocity.y); i++) {
            float oldPosition = position.y;
            position.y += Math.signum(velocity.y);
            Rectangle playerMovementRectangle = new Rectangle(position.x, position.y, playerTeste.getCollisionRectangle().getWidth(), playerTeste.getCollisionRectangle().getHeight());
            Rectangle rectangleIntersection = new Rectangle(playerMovementRectangle);
            if (isCollision(playerMovementRectangle, rectangleIntersection)) {
                if (rectangleIntersection.getWidth() <= (playerTeste.getCollisionRectangle().getWidth() * .5f) && rectangleIntersection.getWidth() > (playerTeste.getCollisionRectangle().getWidth() * .22f)) {
                    if ((rectangleIntersection.y > playerMovementRectangle.y && rectangleIntersection.x == playerMovementRectangle.x) || (rectangleIntersection.y == playerMovementRectangle.y && rectangleIntersection.x == playerMovementRectangle.x)) {
                        playerTeste.getCollisionRectangle().x += Player.ACCELERATE_PLAYER;
                        playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_3);
                    } else {
                        playerTeste.getCollisionRectangle().x -= Player.ACCELERATE_PLAYER;
                        playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_2);
                    }
                    playerTeste.getCollisionRectangle().y = oldPosition - velocity.y;
                    break;
                } else if (rectangleIntersection.getWidth() <= (playerTeste.getCollisionRectangle().getWidth() * .22f)) {
                    if ((rectangleIntersection.y > playerMovementRectangle.y && rectangleIntersection.x == playerMovementRectangle.x) || (rectangleIntersection.y == playerMovementRectangle.y && rectangleIntersection.x == playerMovementRectangle.x)) {
                        playerTeste.getCollisionRectangle().x += Player.ACCELERATE_PLAYER / 2f;
                    } else {
                        playerTeste.getCollisionRectangle().x -= Player.ACCELERATE_PLAYER / 2f;
                    }
                    if (velocity.y > 0)
                        playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_4);
                    else
                        playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_1);
                    break;
                }
                playerTeste.getCollisionRectangle().y = oldPosition;
                velocity.y = 0;
                break;
            }
        }
    }

    private void handleHorizontalCollisions(Vector2 position, Vector2 velocity) {
        for (int i = 0; i < Math.abs(velocity.x); i++) {
            float oldPosition = position.x;
            position.x += Math.signum(velocity.x);
            Rectangle playerMovementRectangle = new Rectangle(position.x, position.y, playerTeste.getCollisionRectangle().getWidth(), playerTeste.getCollisionRectangle().getHeight());
            Rectangle rectangleIntersection = new Rectangle(playerMovementRectangle);
            if (isCollision(playerMovementRectangle, rectangleIntersection)) {
                if (rectangleIntersection.getHeight() <= (playerTeste.getCollisionRectangle().getHeight() * .5f) && rectangleIntersection.getHeight() > (playerTeste.getCollisionRectangle().getHeight() * .22f)) {
                    if ((rectangleIntersection.x > playerMovementRectangle.x && rectangleIntersection.y == playerMovementRectangle.y) || (rectangleIntersection.x == playerMovementRectangle.x && rectangleIntersection.y == playerMovementRectangle.y)) {
                        playerTeste.getCollisionRectangle().y += Player.ACCELERATE_PLAYER;
                        playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_4);
                    } else {
                        playerTeste.getCollisionRectangle().y -= Player.ACCELERATE_PLAYER;
                        playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_1);
                    }
                    playerTeste.getCollisionRectangle().x = oldPosition - velocity.x;
                    break;
                } else if (rectangleIntersection.getHeight() <= (playerTeste.getCollisionRectangle().getHeight() * .22f)) {
                    if ((rectangleIntersection.x > playerMovementRectangle.x && rectangleIntersection.y == playerMovementRectangle.y) || (rectangleIntersection.x == playerMovementRectangle.x && rectangleIntersection.y == playerMovementRectangle.y)) {
                        playerTeste.getCollisionRectangle().y += Player.ACCELERATE_PLAYER / 2f;
                    } else {
                        playerTeste.getCollisionRectangle().y -= Player.ACCELERATE_PLAYER / 2f;
                    }
                    if (velocity.x > 0)
                        playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_3);
                    else
                        playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_2);
                    break;
                }
                playerTeste.getCollisionRectangle().x = oldPosition;
                velocity.x = 0;
                break;
            }
        }
    }

    private boolean isCollision(Rectangle playerMovementRectangle, Rectangle rectangleIntersection) {
        return !initCollisionMap.contains(playerMovementRectangle) || isCollisionWithBombs(playerMovementRectangle, rectangleIntersection);
    }

    private boolean isCollisionWithBombs(Rectangle playerMovementRectangle, Rectangle rectangleIntersection) {
        for (Bomb bomb : activeBombsList) {
            if (bomb.isCollisionActive()) {
                if (Intersector.intersectRectangles(playerMovementRectangle, bomb.getCollisionRectangle(), rectangleIntersection)) {
                    return true;
                }
            }
        }
        return false;
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
        view.dispose();
        for (Bomb bomb : activeBombsList) {
            bomb.dispose();
        }
    }

    @Override
    public void pressUp(boolean isTypeKeyPressDOWN) {
        if (!isTypeKeyPressDOWN) {
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
        playerTeste.setPlantBombActive(isTypeKeyPressDOWN);
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
        playerTeste.update(playerTeste.getRowFrame(), playerTeste.getCollunsFrames());
        view.rect(playerTeste.getCollisionRectangle().x, playerTeste.getCollisionRectangle().y, playerTeste.getCollisionRectangle().getWidth(), playerTeste.getCollisionRectangle().getHeight());
        view.end();
        getGameManager().batch.begin();
        getGameManager().batch.draw(playerTeste.getCurrentFrame(incrementStateTime), playerTeste.getPosition().x, playerTeste.getPosition().y);
        getGameManager().batch.end();
    }

    private void renderObjectsFrames(float delta) {
        if (playerTeste.isPlantBombActive()) {
            boolean overlaps = false;
            if (!activeBombsList.isEmpty()) {
                for (Bomb bomb : activeBombsList) {
                    if (playerTeste.getCollisionRectangle().overlaps(bomb.getCollisionRectangle())) {
                        overlaps = true;
                        break;
                    }
                }
            }

            if (!overlaps) {
                Bomb bomb = new Bomb(new Vector2(playerTeste.getCollisionRectangle().x, playerTeste.getCollisionRectangle().y), this);
                activeBombsList.add(bomb);
            }
        }

        if (!activeBombsList.isEmpty()) {
            getGameManager().batch.begin();
            for (Bomb bomb : activeBombsList) {
                bomb.update();
                if (!playerTeste.getCollisionRectangle().overlaps(bomb.getCollisionRectangle())) {
                    bomb.setCollisionActive(true);
                }
                getGameManager().batch.draw(bomb.getCurrentFrame(delta), bomb.getPosition().x, bomb.getPosition().y);
                view.rect(bomb.getCollisionRectangle().x, bomb.getCollisionRectangle().y, bomb.getCollisionRectangle().getWidth(), bomb.getCollisionRectangle().getHeight());
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
