package com.game.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

    public static void drawSelectionBorder(Actor selectedActor, ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        shapeRenderer.rect(selectedActor.getX() - 10, selectedActor.getY() - 10,
                selectedActor.getWidth() + 20, selectedActor.getHeight() + 20);

        shapeRenderer.end();
    }
}
