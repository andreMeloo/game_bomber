package com.game.model.objects;

import java.util.HashMap;
import java.util.Map;

public class GameObjectAttributes {

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
        setAttribute(ObjectAttributes.SPEED_OBJECT.getName(), 2.75f);
        setAttribute(ObjectAttributes.NUMBER_BOMBS_OBJECT.getName(), 2);
        setAttribute(ObjectAttributes.POWER_FLAMES_OBJECT.getName(), 3);
        setAttribute(ObjectAttributes.THROW_BOMB.getName(), false);
        setAttribute(ObjectAttributes.KICK_BOMB.getName(), false);
        setAttribute(ObjectAttributes.PUNCH_BOMB.getName(), false);
        setAttribute(ObjectAttributes.COLLISION_DESTRUCTIVE_WALL_OBJECT.getName(), true);
        setAttribute(ObjectAttributes.COLLISION_BOMB_OBJECT.getName(), true);
        setAttribute(ObjectAttributes.COLLISION_EXPLOSION_OBJECT.getName(), true);
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
