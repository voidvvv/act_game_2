package com.voidvvv.game.v2.base.world;

import com.voidvvv.game.base.Updateable;
import com.voidvvv.game.context.world.VActorSpawnHelper;
import com.voidvvv.game.v2.base.VActor;

import java.util.List;

public interface VWorld {
    void initWorld();

    void dispose();
    List<? extends VWorldActor> allActors();

    <T extends VWorldActor> T spawnVActor(Class<T> clazz, VActorSpawnHelper helper);

    void resetVActor(VWorldActor actor);

    public void update(float delta);

    void draw();

    public void addUpdateable(Updateable updateable);

    List<Updateable> updatableList();

    // remove updatable
    public void removeUpdateable(Updateable updateable);
}
