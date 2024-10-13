package com.game.model.objects;

public enum ObjectAttributes {
    SPEED_OBJECT("SPEED_OBJECT", .25f),
    NUMBER_BOMBS_OBJECT("NUMBER_BOMBS_OBJECT", 1f),
    POWER_FLAMES_OBJECT("POWER_FLAMES_OBJECT", 1f),
    THROW_BOMB("THROW_BOMB", true),
    KICK_BOMB("KICK_BOMB", true),
    PUNCH_BOMB("PUNCH_BOMB", true),
    COLLISION_DESTRUCTIVE_WALL_OBJECT("COLLISION_DESTRUCTIVE_WALL_OBJECT", false),
    COLLISION_BOMB_OBJECT("COLLISION_BOMB_OBJECT", false),
    COLLISION_EXPLOSION_OBJECT("COLLISION_EXPLOSION_OBJECT", false);

    private final String name;
    private final Object valor;  // Usa Object para aceitar qualquer tipo de valor

    ObjectAttributes(String name, Object valor) {
        this.name = name;
        this.valor = valor;
    }

    public String getName() {
        return name;
    }

    public <T> T getValor(Class<T> type) {
        return type.cast(valor);  // Faz o casting para o tipo apropriado
    }
}
