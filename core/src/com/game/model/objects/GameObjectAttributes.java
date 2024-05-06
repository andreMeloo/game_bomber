package com.game.model.objects;

import java.util.HashMap;
import java.util.Map;

public class GameObjectAttributes {

    public static final String SPEED_OBJECT = "SPEED_OBJECT";
    public static final String NUMBER_BOMBS_OBJECT = "NUMBER_BOMBS_OBJECT";
    public static final String POWER_FLAMES_OBJECT = "POWER_FLAMES_OBJECT";
    public static final String THROW_BOMB = "THROW_BOMB";
    public static final String KICK_BOMB = "KICK_BOMB";
    public static final String PUNCH_BOMB = "PUNCH_BOMB";
    public static final String COLLISION_DESTRUCTIVE_WALL_OBJECT = "COLLISION_DESTRUCTIVE_WALL_OBJECT";
    public static final String COLLISION_BOMB_OBJECT = "COLLISION_BOMB_OBJECT";
    public static final String COLLISION_EXPLOSION_OBJECT = "COLLISION_EXPLOSION_OBJECT";

    Map<String, Attribute<?>> objectAttributes;

    public GameObjectAttributes(GameObject gameObject) {
        objectAttributes = new HashMap<>();
        constructorMapAttibutes(gameObject);
    }

    private void constructorMapAttibutes(GameObject gameObject) {
        if (gameObject instanceof Player) {
            initializePlayerAttributes();
        }
    }

    private void initializePlayerAttributes() {
        setAttribute(SPEED_OBJECT, 3f);
        setAttribute(NUMBER_BOMBS_OBJECT, 1);
        setAttribute(POWER_FLAMES_OBJECT, 2);
        setAttribute(THROW_BOMB, false);
        setAttribute(KICK_BOMB, false);
        setAttribute(PUNCH_BOMB, false);
        setAttribute(COLLISION_DESTRUCTIVE_WALL_OBJECT, true);
        setAttribute(COLLISION_BOMB_OBJECT, true);
        setAttribute(COLLISION_EXPLOSION_OBJECT, true);
    }

    public <T> void setAttribute(String attributeName, T value) {
        objectAttributes.put(attributeName, new Attribute<>(value));
    }

    public <T> T getAttribute(String attributeName) {
        Attribute<?> attribute = objectAttributes.get(attributeName);
        if (attribute != null) {
            return (T) attribute.value();
        }
        return null;
    }

    public record Attribute<T>(T value) {
    }
}
