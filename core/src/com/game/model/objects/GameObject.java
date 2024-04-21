package com.game.model.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.game.View.screen.ChoiceCharacterScreen;

public class GameObject {
    private Screen screen;
    private float stateTime;

    private Vector2 position;
    private Vector2 velocity;
    private Rectangle collisionRectangle;
    private Texture animationSheet;
    private TextureRegion[][] frames;
    private Array<TextureRegion> animationFrames;
    private int currentDirectionMove;

    public GameObject(Vector2 position, Screen screen) {
        this.position = position;
        this.screen = screen;
        this.collisionRectangle = new Rectangle();
        this.velocity = new Vector2();
        this.stateTime = 0;
        updateCollisionRectangle();
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        updateCollisionRectangle();
        updateAnimationFrames();
    }

    public void accelerate(float accelerationX, float accelerationY) {
        velocity.add(accelerationX, accelerationY);
    }

    public void stop() {
        velocity.setZero();
    }

    // Método para atualizar o retângulo de colisão conforme a posição do objeto
    public void updateCollisionRectangle() {
        collisionRectangle.setPosition(position.x, position.y);
    }

    public void updateAnimationFrames() {
        animationFrames = new Array<>();
        int rows = frames.length;

        for (int i = 0; i < rows; i++) {
            int coluns = frames[i].length;
            for (int j = 0; j < coluns; j++) {
                animationFrames.add(frames[i][j]);
            }
        }
    }

    public void updateAnimationFrames(int coluns) {
        animationFrames = new Array<>();
        for (int i = currentDirectionMove - 1; i < currentDirectionMove; i++) {
            for (int j = 0; j < coluns; j++) {
                animationFrames.add(frames[i][j]);
            }
        }
    }

    public void dispose() {
        animationSheet.dispose();
    }

    public void pressUp(boolean isTypeKeyPressDOWN) {
    }

    public void pressDown(boolean isTypeKeyPressDOWN) {

    }

    public void pressLeft(boolean isTypeKeyPressDOWN) {
    }

    public void pressRight(boolean isTypeKeyPressDOWN) {
    }

    public void pressActionA(boolean isTypeKeyPressDOWN) {
    }

    public void pressActionY(boolean isTypeKeyPressDOWN) {
    }

    public void pressStart(boolean isTypeKeyPressDOWN) {
    }

    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    public Vector2 leftBottomVectorCollision() {
        return new Vector2(getCollisionRectangle().getX(), getCollisionRectangle().getY());
    }

    public Vector2 leftTopVectorCollision() {
        return new Vector2(getCollisionRectangle().getX(), getCollisionRectangle().getY() + getCollisionRectangle().getHeight());
    }

    public Vector2 rightBottomVectorCollision() {
        return new Vector2(getCollisionRectangle().getX() + getCollisionRectangle().getWidth(), getCollisionRectangle().getY());
    }

    public Vector2 rightTopVectorCollision() {
        return new Vector2(getCollisionRectangle().getX() + getCollisionRectangle().getWidth(), getCollisionRectangle().getY() + getCollisionRectangle().getHeight());
    }

    public void setVelocity(Vector2 velocity) {
        velocity.set(velocity);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
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

    public int getCurrentDirectionMove() {
        return currentDirectionMove;
    }

    public void setCurrentDirectionMove(int currentDirectionMove) {
        this.currentDirectionMove = currentDirectionMove;
    }

    public Array<TextureRegion> getAnimationFrames() {
        return animationFrames;
    }

    public void setAnimationFrames(Array<TextureRegion> animationFrames) {
        this.animationFrames = animationFrames;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }
}
