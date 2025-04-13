package com.voidvvv.game.v2.world;

import com.voidvvv.game.base.Updateable;
import com.voidvvv.game.context.world.VActorSpawnHelper;

import java.util.List;

public interface VWorld {
    void initWorld();

    void dispose();
    List<? extends VWorldActor> allActors();

    <T extends VWorldActor> T spawnVActor(Class<T> clazz, VActorSpawnHelper helper);

    public void update(float delta);

    void draw();

    public void addUpdateable(Updateable updateable);

    List<Updateable> updatableList();

    // remove updatable
    public void removeUpdateable(Updateable updateable);
}
