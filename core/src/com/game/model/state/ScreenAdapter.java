package com.game.model.state;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.game.model.controls.ControlAdapter;

public abstract class ScreenAdapter {



    public abstract ControlAdapter getControler();
    public abstract boolean pressUp();
    public abstract boolean pressDown();
    public abstract boolean pressLeft();
    public abstract boolean pressRight();
    public abstract boolean pressActionA();
    public abstract boolean pressActionY();
    public abstract boolean pressStart();

    public  <T extends Actor> Array<T> filterActorsByType(Class<T> actorType, Array<Actor> actors) {
        Array<T> filteredActors = new Array<>();

        for (Actor actor : actors) {
            if (actorType.isInstance(actor)) {
                filteredActors.add(actorType.cast(actor));
            }
        }

        return filteredActors;
    }
}
