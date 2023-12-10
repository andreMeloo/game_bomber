package com.game.model.objects;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
    ScreenAdapter screen;

    private Vector2 position;
    private Vector2 velocity;
    private Rectangle collisionRectangle;
    private Texture animationSheet;
    private TextureRegion[][] frames;

    public GameObject(Vector2 position, ScreenAdapter screen) {
        this.position = position;
        this.screen = screen;
        this.collisionRectangle = new Rectangle();
    }

    public void update(float deltaTime) {
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
    }

    public void accelerate(float accelerationX, float accelerationY) {
        velocity.add(accelerationX, accelerationY);
    }

    // Método para atualizar o retângulo de colisão conforme a posição do objeto
    public void updateCollisionRectangle() {
        collisionRectangle.setPosition(position.x, position.y);
    }

    public void dispose() {
        animationSheet.dispose();
    }

    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
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
}
