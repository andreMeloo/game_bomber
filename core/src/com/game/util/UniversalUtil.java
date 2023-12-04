package com.game.util;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class UniversalUtil {
    public static <T extends Actor> Array<T> filterActorsByType(Class<T> actorType, Array<Actor> actors) {
        Array<T> filteredActors = new Array<>();

        for (Actor actor : actors) {
            if (actorType.isInstance(actor)) {
                filteredActors.add(actorType.cast(actor));
            }
        }

        return filteredActors;
    }
}
