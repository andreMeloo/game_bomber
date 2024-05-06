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
import com.badlogic.gdx.utils.ScreenUtils;
import com.game.controller.GameManager;
import com.game.controller.InputManager;
import com.game.model.controls.GameControl;
import com.game.model.objects.*;

import java.util.*;

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
    private LinkedList<Explosion> activeExplosionsList;
    private List<FixedWallCollsion> fixedWallColision;

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
        activeExplosionsList = new LinkedList<>();
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

        fixedWallColision.forEach(v -> {
            view.rect(v.getCollision().x, v.getCollision().y, v.getCollision().getWidth(), v.getCollision().getHeight());
        });

        renderObjectsFrames(delta);
        treatPlayerCollision();
        renderAnimationFrames(delta);
        getGameManager().batch.end();
        view.end();
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
        float remainingMovement = Math.abs(velocity.y);

        while (remainingMovement > 0) {
            boolean collisionBomb;
            float oldPosition = position.y;

            float movement = Math.min(1.0f, remainingMovement);
            position.y += Math.signum(velocity.y) * movement;
            Rectangle playerMovementRectangle = new Rectangle(position.x, position.y, playerTeste.getCollisionRectangle().getWidth(), playerTeste.getCollisionRectangle().getHeight());
            Rectangle rectangleIntersection = new Rectangle(playerMovementRectangle);
            collisionBomb = isCollisionWithBombs(playerMovementRectangle);
            if (isCollisionWallsOrMap(playerMovementRectangle, rectangleIntersection) || collisionBomb) {
                if (!collisionBomb) {
                    if (rectangleIntersection.getWidth() <= (playerTeste.getCollisionRectangle().getWidth() * .7f) && rectangleIntersection.getWidth() > (playerTeste.getCollisionRectangle().getWidth() * .25f)) {
                        if ((rectangleIntersection.y > playerMovementRectangle.y && rectangleIntersection.x == playerMovementRectangle.x) || (rectangleIntersection.y == playerMovementRectangle.y && rectangleIntersection.x == playerMovementRectangle.x)) {
                            playerTeste.getCollisionRectangle().x += Player.ACCELERATE_PLAYER;
                            playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_3);
                        } else {
                            playerTeste.getCollisionRectangle().x -= Player.ACCELERATE_PLAYER;
                            playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_2);
                        }
                        playerTeste.getCollisionRectangle().y = Math.abs(oldPosition - velocity.y);
                        break;
                    } else if (rectangleIntersection.getWidth() <= (playerTeste.getCollisionRectangle().getWidth() * .25f)) {
                        int round = Math.round(Math.abs(Player.ACCELERATE_PLAYER) / 2f);
                        if ((rectangleIntersection.y > playerMovementRectangle.y && rectangleIntersection.x == playerMovementRectangle.x) || (rectangleIntersection.y == playerMovementRectangle.y && rectangleIntersection.x == playerMovementRectangle.x)) {
                            if (round > rectangleIntersection.width) {
                                playerTeste.getCollisionRectangle().x += rectangleIntersection.width;
                            } else {
                                playerTeste.getCollisionRectangle().x += Math.round(Player.ACCELERATE_PLAYER / 2f);
                            }
                        } else {
                            if (round > rectangleIntersection.width) {
                                playerTeste.getCollisionRectangle().x -= rectangleIntersection.width;
                            } else {
                                playerTeste.getCollisionRectangle().x -= Math.round(Player.ACCELERATE_PLAYER / 2f);
                            }
                        }
                        if (velocity.y > 0)
                            playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_4);
                        else
                            playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_1);
                        break;
                    }
                }
                playerTeste.getCollisionRectangle().y = oldPosition - velocity.y;
                break;
            }
            remainingMovement -= movement;
        }
    }

    private void handleHorizontalCollisions(Vector2 position, Vector2 velocity) {
        float remainingMovement = Math.abs(velocity.x);

        while (remainingMovement > 0) {
            boolean collisionBomb;
            float oldPosition = position.x;

            float movement = Math.min(1.0f, remainingMovement);
            position.x += Math.signum(velocity.x) * movement;
            Rectangle playerMovementRectangle = new Rectangle(position.x, position.y, playerTeste.getCollisionRectangle().getWidth(), playerTeste.getCollisionRectangle().getHeight());
            Rectangle rectangleIntersection = new Rectangle(playerMovementRectangle);
            collisionBomb = isCollisionWithBombs(playerMovementRectangle);
            if (isCollisionWallsOrMap(playerMovementRectangle, rectangleIntersection) || collisionBomb) {
                if (!collisionBomb) {
                    if (rectangleIntersection.getHeight() <= (playerTeste.getCollisionRectangle().getHeight() * .9f) && rectangleIntersection.getHeight() > (playerTeste.getCollisionRectangle().getHeight() * .25f)) {
                        if ((rectangleIntersection.x > playerMovementRectangle.x && rectangleIntersection.y == playerMovementRectangle.y) || (rectangleIntersection.x == playerMovementRectangle.x && rectangleIntersection.y == playerMovementRectangle.y)) {
                            playerTeste.getCollisionRectangle().y += Player.ACCELERATE_PLAYER;
                            playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_4);
                        } else {
                            playerTeste.getCollisionRectangle().y -= Player.ACCELERATE_PLAYER;
                            playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_1);
                        }
                        playerTeste.getCollisionRectangle().x = Math.abs(oldPosition - velocity.x);
                        break;
                    } else if (rectangleIntersection.getHeight() <= (playerTeste.getCollisionRectangle().getHeight() * .25f)) {
                        int round = Math.round(Math.abs(Player.ACCELERATE_PLAYER) / 2f);
                        if ((rectangleIntersection.x > playerMovementRectangle.x && rectangleIntersection.y == playerMovementRectangle.y) || (rectangleIntersection.x == playerMovementRectangle.x && rectangleIntersection.y == playerMovementRectangle.y)) {
                            if (round > rectangleIntersection.height) {
                                playerTeste.getCollisionRectangle().y += rectangleIntersection.height;
                            } else {
                                playerTeste.getCollisionRectangle().y += Math.round(Player.ACCELERATE_PLAYER / 2f);
                            }
                        } else {
                            if (round > rectangleIntersection.height) {
                                playerTeste.getCollisionRectangle().y -= rectangleIntersection.height;
                            } else {
                                playerTeste.getCollisionRectangle().y -= Math.round(Player.ACCELERATE_PLAYER / 2f);
                            }
                        }
                        if (velocity.x > 0)
                            playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_3);
                        else
                            playerTeste.setRowFrame(Player.NUMBER_ROW_FRAMES_2);
                        break;
                    }
                }
                playerTeste.getCollisionRectangle().x = Math.abs(oldPosition - velocity.x);
                break;
            }
            remainingMovement -= movement;
        }
    }

    private boolean isCollisionWallsOrMap(Rectangle playerMovementRectangle, Rectangle rectangleIntersection) {
        return !collisionMap.contains(playerMovementRectangle) || isCollisionWithWalls(playerMovementRectangle, rectangleIntersection);
    }

    private boolean isCollisionWithWalls(Rectangle playerMovementRectangle, Rectangle rectangleIntersection) {
        for (WorldCollision wall : fixedWallColision) {
            if (Intersector.intersectRectangles(playerMovementRectangle, wall.getCollision(), rectangleIntersection)) {
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
                    fixedWallColision.add(new FixedWallCollsion(new Vector2(gridPositionMap[i][j].x, gridPositionMap[i][j].y)));
                }
            }
        }
    }


    private void renderAnimationFrames(float incrementStateTime) {
        playerTeste.update(playerTeste.getRowFrame(), playerTeste.getCollunsFrames());
        view.rect(playerTeste.getCollisionRectangle().x, playerTeste.getCollisionRectangle().y, playerTeste.getCollisionRectangle().getWidth(), playerTeste.getCollisionRectangle().getHeight());
        getGameManager().batch.draw(playerTeste.getCurrentFrame(incrementStateTime), playerTeste.getPosition().x, playerTeste.getPosition().y);
    }

    private void renderObjectsFrames(float delta) {
        if (!activeExplosionsList.isEmpty()) {
            Iterator<Explosion> it = activeExplosionsList.descendingIterator();

            while (it.hasNext()) {
                Explosion explosion = it.next();
                if (explosion.isFinish()) {
                    explosion.getExplosionsArms().forEach(v -> { v.values().forEach(GameObject::dispose); });
                    explosion.dispose();
                    it.remove();
                } else {
                    activeBombsList.forEach(bomb -> {
                        if (explosion.getCollisionRectangle().overlaps(bomb.getCollisionRectangle())) {
                            bomb.setExplode(true);
                        } else {
                            explosion.getExplosionsArms().forEach(v -> {
                                v.values().forEach(armExplosion -> {
                                    if (armExplosion.getCollisionRectangle().overlaps(bomb.getCollisionRectangle())) {
                                        bomb.setExplode(true);
                                    }
                                });
                            });
                        }
                    });
                }
            }
        }

        if (!activeBombsList.isEmpty()) {
            Iterator<Bomb> it = activeBombsList.iterator();

            while (it.hasNext()) {
                Bomb bomb = it.next();
                if (bomb.isExplode()) {
                    activeExplosionsList.push(new Explosion(new Vector2(bomb.getCollisionRectangle().x, bomb.getCollisionRectangle().y), this, 5));

                    bomb.dispose();
                    it.remove();
                }
            }
        }

        if (playerTeste.isPlantBombActive()) {
            boolean overlaps = false;
            if (!activeBombsList.isEmpty()) {
                for (Bomb bomb : activeBombsList) {
                    Rectangle intersect = new Rectangle();
                    if (Intersector.intersectRectangles(playerTeste.getCollisionRectangle(), bomb.getCollisionRectangle(), intersect)) {
                        if (intersect.area() > (playerTeste.getCollisionRectangle().area() * 0.1)) {
                            overlaps = true;
                            break;
                        }
                    }
                }
            }

            if (!overlaps) {
                TreeMap<Float, Rectangle> possiblePlantBomb = new TreeMap<>(Comparator.comparing(area -> -area));
                for (int i = 0; i < COLLUNS_GRID_POSITION; i++) {
                    for (int j = 0; j < LINES_GRID_POSITION; j++) {
                        if (!(i % 2 != 0 && j % 2 != 0)) {
                            if (playerTeste.getCollisionRectangle().overlaps(gridPositionMap[i][j])) {
                                Rectangle intersectPlayerGrid = new Rectangle();
                                Intersector.intersectRectangles(playerTeste.getCollisionRectangle(), gridPositionMap[i][j], intersectPlayerGrid);

                                if (intersectPlayerGrid.area() == 0) {
                                    possiblePlantBomb.put(playerTeste.getCollisionRectangle().area(), gridPositionMap[i][j]);
                                } else {
                                    possiblePlantBomb.put(intersectPlayerGrid.area(), gridPositionMap[i][j]);
                                }
                            }
                        }
                    }
                }

                Rectangle positionPlantBomb = possiblePlantBomb.firstEntry().getValue();
                Bomb bomb = new Bomb(new Vector2(positionPlantBomb.x, positionPlantBomb.y), this);
                activeBombsList.add(bomb);
            }
        }

        if (!activeExplosionsList.isEmpty()) {
            for (Explosion explosion : activeExplosionsList) {
                explosion.update();

                TextureRegion currentFrame = explosion.getCurrentFrame(delta);

                if (!explosion.isFinish()) {
                    if ((!explosion.isCenterCollision() && !explosion.getExplosionsArms().isEmpty()) || (explosion.isCenterCollision() && !explosion.getExplosionsArms().isEmpty()) || (!explosion.isCenterCollision() && explosion.getExplosionsArms().isEmpty())) {
                        getGameManager().batch.draw(currentFrame, explosion.getPosition().x, explosion.getPosition().y);
                        view.rect(explosion.getCollisionRectangle().x, explosion.getCollisionRectangle().y, explosion.getCollisionRectangle().getWidth(), explosion.getCollisionRectangle().getHeight());
                    }

                    explosion.getExplosionsArms().forEach(expArms -> {
                        expArms.values().forEach(explosionArm -> {
                            explosionArm.update();
                            getGameManager().batch.draw(explosionArm.getCurrentFrame(delta), explosionArm.getPosition().x, explosionArm.getPosition().y);
                            view.rect(explosionArm.getCollisionRectangle().x, explosionArm.getCollisionRectangle().y, explosionArm.getCollisionRectangle().getWidth(), explosionArm.getCollisionRectangle().getHeight());
                        });
                    });
                }
            }
        }

        if (!activeBombsList.isEmpty()) {
            for (Bomb bomb : activeBombsList) {
                bomb.update();

                TextureRegion currentFrame = bomb.getCurrentFrame(delta);

                if (!playerTeste.getCollisionRectangle().overlaps(bomb.getCollisionRectangle())) {
                    bomb.setCollisionActive(true);
                }

                if (!bomb.isExplode()) {
                    getGameManager().batch.draw(currentFrame, bomb.getPosition().x, bomb.getPosition().y);
                    view.rect(bomb.getCollisionRectangle().x, bomb.getCollisionRectangle().y, bomb.getCollisionRectangle().getWidth(), bomb.getCollisionRectangle().getHeight());
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
        for (Explosion explosion : activeExplosionsList) {
            explosion.getExplosionsArms().forEach( v -> {
                v.values().forEach(GameObject::dispose);
            });
            explosion.dispose();
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

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public List<Bomb> getActiveBombsList() {
        return activeBombsList;
    }

    public void setActiveBombsList(List<Bomb> activeBombsList) {
        this.activeBombsList = activeBombsList;
    }

    public LinkedList<Explosion> getActiveExplosionsList() {
        return activeExplosionsList;
    }

    public void setActiveExplosionsList(LinkedList<Explosion> activeExplosionsList) {
        this.activeExplosionsList = activeExplosionsList;
    }

    public List<FixedWallCollsion> getFixedWallColision() {
        return fixedWallColision;
    }

    public void setFixedWallColision(List<FixedWallCollsion> fixedWallColision) {
        this.fixedWallColision = fixedWallColision;
    }

    public Rectangle getCollisionMap() {
        return collisionMap;
    }

    public void setCollisionMap(Rectangle collisionMap) {
        this.collisionMap = collisionMap;
    }
}
