package com.voidvvv.game.context.world;

import com.badlogic.gdx.physics.box2d.World;
import com.voidvvv.game.base.Updateable;
import com.voidvvv.game.base.VActor;

import java.util.List;

public interface VWorld {
    void initWorld();
    List<? extends VActor> allActors();

    <T extends VActor> T spawnVActor(Class<T> clazz, VActorSpawnHelper helper);

    public void update(float delta);


    public void addUpdateable(Updateable updateable);

    // remove updatable
    public void removeUpdateable(Updateable updateable);
}
