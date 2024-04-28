package com.game.View.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
    private static final float LINES_GRID_POSITION = 11;
    private static final float COLLUNS_GRID_POSITION = 13;

    /**
     * Mapa
     */
    private Texture map;
    private Rectangle[][] gridPositionMap;

    /**
     * Objects
     */
    private List<Bomb> activeBombsList;
    private List<Rectangle> fixedWallColision;

    /**
     * Config State
     */
    private float stateTime;
    Player playerTeste;
    Rectangle collisionMap;
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
        gridPositionMap = new Rectangle[(int) COLLUNS_GRID_POSITION][(int) LINES_GRID_POSITION];
        fixedWallColision = new ArrayList<>();
        WorldCollision.init();

        /**
         * testes colisão
         */
        map = new Texture(Gdx.files.internal(MAP));

        positionMap = new Vector2(Gdx.graphics.getWidth() / 2f - (map.getWidth() / 2f), (Gdx.graphics.getHeight() / 2f) - (map.getHeight() / 2f));
        collisionMap = new Rectangle(positionMap.x + 47f, positionMap.y + 41f, (WorldCollision.WIDTH_COLLISION_UNIVERSAL * COLLUNS_GRID_POSITION) + 2f,  (WorldCollision.HEIGHT_COLLISION_UNIVERSAL * LINES_GRID_POSITION) + 2f);
        playerTeste = new Player(new Vector2((collisionMap.x + (collisionMap.getWidth() / 2f)) - (WorldCollision.WIDTH_COLLISION_UNIVERSAL / 2f), (collisionMap.y + (collisionMap.getHeight() / 2f)) - (WorldCollision.HEIGHT_COLLISION_UNIVERSAL / 2f)), this);
        constructorMapGridPosition();
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
            boolean collisionBomb = false;
            float oldPosition = position.y;

            position.y += Math.signum(velocity.y);
            Rectangle playerMovementRectangle = new Rectangle(position.x, position.y, playerTeste.getCollisionRectangle().getWidth(), playerTeste.getCollisionRectangle().getHeight());
            Rectangle rectangleIntersection = new Rectangle(playerMovementRectangle);
            collisionBomb = isCollisionWithBombs(playerMovementRectangle);
            if (isCollision(playerMovementRectangle, rectangleIntersection) || collisionBomb) {
                if (!collisionBomb) {
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
                }
                playerTeste.getCollisionRectangle().y = oldPosition;
                velocity.y = 0;
                break;
            }
        }
    }

    private void handleHorizontalCollisions(Vector2 position, Vector2 velocity) {
        for (int i = 0; i < Math.abs(velocity.x); i++) {
            boolean collisionBomb = false;
            float oldPosition = position.x;

            position.x += Math.signum(velocity.x);
            Rectangle playerMovementRectangle = new Rectangle(position.x, position.y, playerTeste.getCollisionRectangle().getWidth(), playerTeste.getCollisionRectangle().getHeight());
            Rectangle rectangleIntersection = new Rectangle(playerMovementRectangle);
            collisionBomb = isCollisionWithBombs(playerMovementRectangle);
            if (isCollision(playerMovementRectangle, rectangleIntersection) || collisionBomb) {
                if (!collisionBomb) {
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
                }
                playerTeste.getCollisionRectangle().x = oldPosition;
                velocity.x = 0;
                break;
            }
        }
    }

    private boolean isCollision(Rectangle playerMovementRectangle, Rectangle rectangleIntersection) {
        return !collisionMap.contains(playerMovementRectangle) || isCollisionWithWalls(playerMovementRectangle, rectangleIntersection);
    }

    private boolean isCollisionWithWalls(Rectangle playerMovementRectangle, Rectangle rectangleIntersection) {
        for (Rectangle wall : fixedWallColision) {
            if (Intersector.intersectRectangles(playerMovementRectangle, wall, rectangleIntersection)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCollisionWithBombs(Rectangle playerMovementRectangle) {
        for (Bomb bomb : activeBombsList) {
            if (bomb.isCollisionActive()) {
                if (Intersector.intersectRectangles(playerMovementRectangle, bomb.getCollisionRectangle(), new Rectangle())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void constructorMapGridPosition() {
        Vector2 positionInitial = new Vector2(collisionMap.x, collisionMap.y);
        for (int i = 0; i < COLLUNS_GRID_POSITION; i++) {
            for (int j = 0; j < LINES_GRID_POSITION; j++) {
                gridPositionMap[i][j] = new Rectangle(positionInitial.x + 1f + (WorldCollision.WIDTH_COLLISION_UNIVERSAL * i), positionInitial.y + 1f + (WorldCollision.HEIGHT_COLLISION_UNIVERSAL * j), WorldCollision.WIDTH_COLLISION_UNIVERSAL, WorldCollision.HEIGHT_COLLISION_UNIVERSAL);

                if (i % 2 != 0 && j % 2 != 0) {
                    fixedWallColision.add(gridPositionMap[i][j]);
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
