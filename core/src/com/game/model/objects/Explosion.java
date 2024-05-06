package com.game.model.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.game.View.screen.PlayGameScreen;

import java.util.*;

public class Explosion extends GameObject {

    private static final String EXPLOSION_LEFT_SPRITE = "explosion-left.png";
    private static final String EXPLOSION_TOP_SPRITE = "explosion-top.png";
    private static final String EXPLOSION_RIGHT_SPRITE = "explosion-right.png";
    private static final String EXPLOSION_BOT_SPRITE = "explosion-bottom.png";
    private static final String EXPLOSION_MID_SPRITE = "explosion-center.png";
    public static final String LEFT_ARM = "LA";
    public static final String TOP_ARM = "TA";
    public static final String RIGHT_ARM = "RA";
    public static final String BOT_ARM = "BA";
    private static final int TILESET_WIDTH = 7;
    private static final int TILESET_HEIGHT_ARMS = 2;
    private static final int TILESET_HEIGHT_CENTER = 1;
    public static final float FRAME_DURATION_EXPLOSION = .1f;

    private List<TreeMap<String, Explosion>> explosionsArms;
    private int powerFlames;
    private String armDirection;
    private boolean finish;
    private boolean centerCollision;

    public Explosion(Vector2 position, Screen screen, int powerFlames) {
        super(position, screen);
        this.armDirection = "";
        this.explosionsArms = new ArrayList<>();
        this.centerCollision = false;
        this.finish = false;
        this.powerFlames = powerFlames;
        constructorArmsMapPosition();
        setAnimationSheet(new Texture(Gdx.files.internal(EXPLOSION_MID_SPRITE)));
        loadAnimationSheet();
        updatePositionAnimation();
    }

    public Explosion(Vector2 position, Screen screen, String armDirection, int powerFlames) {
        super(position, screen);
        this.armDirection = armDirection;
        this.powerFlames = powerFlames;
        setAnimationSheet(new Texture(Gdx.files.internal(getSprite())));
        loadAnimationSheet();
        updatePositionAnimation();
    }

    private void loadAnimationSheet() {
        setFrames(TextureRegion.split(getAnimationSheet(), getAnimationSheet().getWidth() / TILESET_WIDTH, getAnimationSheet().getHeight() / (this.armDirection.isEmpty() ? TILESET_HEIGHT_CENTER : TILESET_HEIGHT_ARMS)));
        setWidth(getCollisionRectangle().getWidth() - ((float) getAnimationSheet().getWidth() / TILESET_WIDTH));
    }

    public void updateAnimationFrames() {
        setAnimationFrames(new Array<>());

        int localeArm = this.armDirection.isEmpty() ? 0 : Integer.parseInt(armDirection.substring(armDirection.length() - 1));

        int row = this.armDirection.isEmpty() || localeArm != powerFlames ? 0 : 1;

        for (int i = row; i <= row; i++) {
            for (int j = 0; j < TILESET_WIDTH; j++) {
                getAnimationFrames().add(getFrames()[i][j]);
            }
        }
    }

    public TextureRegion getCurrentFrame(float delta) {
        TextureRegion currentFrame = super.getCurrentFrame(FRAME_DURATION_EXPLOSION, delta);
        boolean isAnimationEnding = getStateTime() >= FRAME_DURATION_EXPLOSION * getAnimationFrames().size;

        if (isAnimationEnding && !isFinish()) {
            setFinish(true);
        }

        return currentFrame;
    }

    private void constructorArmsMapPosition() {
        for (Explosion exp : ((PlayGameScreen) getScreen()).getActiveExplosionsList()) {
            if (exp.getCollisionRectangle().overlaps(this.getCollisionRectangle()) && !exp.equals(this)) {
                this.centerCollision = true;
                break;
            }
        }


        for (String direction : getDirectionsExplosion()) {
            boolean collision;
            TreeMap<String, Explosion> armExp = new TreeMap<>(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    // Extrai apenas os números das chaves e compara como inteiros
                    int num1 = extractNumber(o1);
                    int num2 = extractNumber(o2);
                    return Integer.compare(num1, num2);
                }

                // Método para extrair o número de uma string
                private int extractNumber(String str) {
                    return Integer.parseInt(str.replaceAll("\\D", ""));
                }
            });
            for (int i = 1; i <= powerFlames; i++) {
                collision = creatArmExplosion(direction, i, armExp);

                if (collision)
                    break;
            }

            if (!armExp.isEmpty())
                explosionsArms.add(armExp);
        }
    }

    private boolean creatArmExplosion(String direction, Integer powerFlames, Map<String, Explosion> armExp) {
        final String numberArm = direction + powerFlames;
        switch (direction) {
            case LEFT_ARM -> {
                float positionX = getCollisionRectangle().x - (getCollisionRectangle().getWidth() * powerFlames);
                float positionY = getCollisionRectangle().y;

                if (insertExplosionArm(numberArm, positionX, positionY, armExp)) return true;
            }

            case TOP_ARM -> {
                float positionX = getCollisionRectangle().x;
                float positionY = getCollisionRectangle().y + (getCollisionRectangle().getWidth() * powerFlames);

                if (insertExplosionArm(numberArm, positionX, positionY, armExp)) return true;
            }

            case RIGHT_ARM -> {
                float positionX = getCollisionRectangle().x + (getCollisionRectangle().getWidth() * powerFlames);
                float positionY = getCollisionRectangle().y;

                if (insertExplosionArm(numberArm, positionX, positionY, armExp)) return true;
            }

            case BOT_ARM -> {
                float positionX = getCollisionRectangle().x;
                float positionY = getCollisionRectangle().y - (getCollisionRectangle().getWidth() * powerFlames);

                if (insertExplosionArm(numberArm, positionX, positionY, armExp)) return true;
            }
        }

        return false;
    }

    private boolean insertExplosionArm(String numberArm, float positionX, float positionY, Map<String, Explosion> armExp) {
        Rectangle newPositionArm = new Rectangle(positionX, positionY, getCollisionRectangle().getWidth(), getCollisionRectangle().getHeight());

        PlayGameScreen gameScreen = (PlayGameScreen) getScreen();
        for (Bomb bomb : gameScreen.getActiveBombsList()) {
            if (newPositionArm.overlaps(bomb.getCollisionRectangle())) {
                bomb.setExplode(true);
                return true;
            }
        }

        for (Explosion explosionCenter : gameScreen.getActiveExplosionsList()) {
            if (newPositionArm.overlaps(explosionCenter.getCollisionRectangle())) {
                return false;
            }
            for (Map<String, Explosion> armsExplosion : explosionCenter.getExplosionsArms()) {
                for (Map.Entry<String, Explosion> entry : armsExplosion.entrySet()) {
                    String key = entry.getKey();
                    Explosion armExplosion = entry.getValue();

                    if (newPositionArm.overlaps(armExplosion.getCollisionRectangle()) && isOppositeDirection(numberArm, key)) {
                        return true;
                    }
                }
            }
        }

        for (WorldCollision walls : gameScreen.getFixedWallColision()) {
            if (newPositionArm.overlaps(walls.getCollision()) || !gameScreen.getCollisionMap().contains(newPositionArm)) {
                return true;
            }
        }

        armExp.put(numberArm, new Explosion(new Vector2(positionX, positionY), getScreen(), numberArm,this.powerFlames));
        return false;
    }

    public List<String> getDirectionsExplosion() {
        return Arrays.asList(
                LEFT_ARM,
                TOP_ARM,
                RIGHT_ARM,
                BOT_ARM);
    }

    private String getSprite() {
        switch (this.armDirection.replaceAll("[^a-zA-Z]", "")) {
            case LEFT_ARM -> {
                return EXPLOSION_LEFT_SPRITE;
            }

            case TOP_ARM -> {
                return EXPLOSION_TOP_SPRITE;
            }

            case RIGHT_ARM -> {
                return EXPLOSION_RIGHT_SPRITE;
            }

            case BOT_ARM -> {
                return EXPLOSION_BOT_SPRITE;
            }

            default -> {
                return null;
            }
        }
    }

    private boolean isOppositeDirection(String direction1, String direction2) {
        String d1 = direction1.replaceAll("\\d+", "");
        String d2 = direction2.replaceAll("\\d+", "");

        return (d1.equals(LEFT_ARM) && d2.equals(RIGHT_ARM)) || (d1.equals(RIGHT_ARM) && d2.equals(LEFT_ARM)) ||
                (d1.equals(d2)) ||
                (d1.equals(TOP_ARM) && d2.equals(BOT_ARM)) || (d1.equals(BOT_ARM) && d2.equals(TOP_ARM));
    }

    public List<TreeMap<String, Explosion>> getExplosionsArms() {
        return explosionsArms;
    }

    public void setExplosionsArms(List<TreeMap<String, Explosion>> explosionsArms) {
        this.explosionsArms = explosionsArms;
    }

    public int getPowerFlames() {
        return powerFlames;
    }

    public void setPowerFlames(int powerFlames) {
        this.powerFlames = powerFlames;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public boolean isCenterCollision() {
        return centerCollision;
    }

    public void setCenterCollision(boolean centerCollision) {
        this.centerCollision = centerCollision;
    }
}
