package com.voidvvv.game.context.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.voidvvv.game.base.Updateable;
import com.voidvvv.game.base.VActor;

import java.util.List;

public class VFlatWorld implements VWorld{
    protected World box2dWorld;

    public World getBox2dWorld() {
        return box2dWorld;
    }

    public void setBox2dWorld(World box2dWorld) {
        this.box2dWorld = box2dWorld;
    }

    @Override
    public void initWorld() {
        box2dWorld = new World(new Vector2(0, 0f), true);
    }

    @Override
    public List<VActor> allActors() {
        return null;
    }

    @Override
    public <T extends VActor> T spawnVActor(Class<T> clazz, VActorSpawnHelper helper) {
        return null;
    }

    @Override
    public void update(float delta) {
        box2dWorld.step(delta, 6, 2);
    }

    @Override
    public void addUpdateable(Updateable updateable) {

    }

    @Override
    public void removeUpdateable(Updateable updateable) {

    }
}
