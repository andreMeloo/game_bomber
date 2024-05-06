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
    private float width;
    private Rectangle collisionRectangle;
    private Texture animationSheet;
    private TextureRegion[][] frames;
    private Array<TextureRegion> animationFrames;
    private GameObjectAttributes attributes;

    public GameObject(Vector2 position, Screen screen) {
        this.screen = screen;
        this.collisionRectangle = new Rectangle();
        this.velocity = new Vector2();
        this.position = new Vector2();
        this.stateTime = 0;
        this.collisionRectangle.width = WorldCollision.WIDTH_COLLISION_UNIVERSAL;
        this.collisionRectangle.height = WorldCollision.HEIGHT_COLLISION_UNIVERSAL;
        this.collisionRectangle.setPosition(new Vector2(position.x, position.y));
    }

    public void update() {
        updateCollisionRectangle();
        updatePositionAnimation();
        updateAnimationFrames();
    }

    public void accelerate(float accelerationX, float accelerationY) {
        velocity.set(accelerationX, accelerationY);
    }

    public void stop() {
        velocity.setZero();
    }

    // Método para atualizar o retângulo de colisão conforme a posição do objeto
    public void updateCollisionRectangle() {
        collisionRectangle.setPosition(getCollisionRectangle().x + velocity.x, getCollisionRectangle().y + velocity.y);
    }

    public void updatePositionAnimation() {
        if (width > 0) {
            position.set(getCollisionRectangle().x - (width / 2f), getCollisionRectangle().y);
        } else {
            position.set(getCollisionRectangle().x + (width / 2f), getCollisionRectangle().y);
        }
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

    public TextureRegion getCurrentFrame(float frameDuration, float delta) {
        stateTime += delta;
        int nextFrame = (int) ((getStateTime() / frameDuration) % getAnimationFrames().size);
        return getAnimationFrames().get(nextFrame);
    }

    public void dispose() {
        animationSheet.dispose();
    }

    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    public void setCollisionRectangle(Rectangle collisionRectangle) {
        this.collisionRectangle = collisionRectangle;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity.set(velocity);
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

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public GameObjectAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(GameObjectAttributes attributes) {
        this.attributes = attributes;
    }
}
