package com.voidvvv.game.v2.base.world.flat;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.voidvvv.game.base.Updateable;
import com.voidvvv.game.context.world.VActorSpawnHelper;
import com.voidvvv.game.v2.base.VActor;
import com.voidvvv.game.v2.base.world.VRenderdWorld;
import com.voidvvv.game.v2.base.world.VWorldActor;
import com.voidvvv.game.v2.base.world.WorldContext;
import com.voidvvv.game.v2.base.world.components.VWorldActorComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个平面world，没有重力，有物理模拟。
 */
public class VFlatWorld extends VRenderdWorld {
    protected World box2dWorld;

    private WorldContext worldContext;

    private VWorldActorComponent actorComponent;

    // actor related


    public VFlatWorld(WorldContext worldContext) {
        this.worldContext = worldContext;
    }

    public WorldContext getWorldContext() {
        return worldContext;
    }

    public void setWorldContext(WorldContext worldContext) {
        this.worldContext = worldContext;
    }

    @Override
    public void initWorld() {
        actorComponent=new VWorldActorComponent();
        actorComponent.init();
        initBox2dWorld();
    }



    private void initBox2dWorld() {
        box2dWorld = new World(new Vector2(), true);
    }

    @Override
    public void dispose() {
        disposeBox2dWorld();
        actorComponent.dispose();
        actorComponent = null;
    }



    private void disposeBox2dWorld() {
        if (box2dWorld != null) {
            box2dWorld.dispose();
            box2dWorld = null;
        }
    }

    @Override
    public List<? extends VWorldActor> allActors() {
        return actorComponent.allActors();
    }

    @Override
    public <T extends VWorldActor> T spawnVActor(Class<T> clazz, VActorSpawnHelper helper) {
        return null;
    }

    @Override
    public void resetVActor(VWorldActor actor) {
        this.actorComponent.resetActor(actor);
    }

    @Override
    public void update(float delta) {
        box2dWorld.step(delta, 6, 2);
        internalUpdate(delta);
        List<? extends VWorldActor> actors = allActors();
        for (VWorldActor actor : actors) {
            actor.update(delta);
        }
        for (Updateable updateable : updateables) {
            updateable.update(delta);
        }
    }

    private void internalUpdate(float delta) {

    }


    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void addUpdateable(Updateable updateable) {
        updateables.add(updateable);
    }

    List<Updateable> updateables = new ArrayList<>();
    @Override
    public List<Updateable> updatableList() {
        return updateables;
    }

    @Override
    public void removeUpdateable(Updateable updateable) {
        updateables.remove(updateable);
    }
}
