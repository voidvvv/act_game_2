package com.voidvvv.game.context;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.voidvvv.game.base.VActor;

import java.util.ArrayList;
import java.util.List;

public class VWorld {
    public static final VActor.VActorCompare DEFAULT_ACTOR_COMPARE = new VActor.VActorCompare();
    private final List<VActor> preUpdateList = new ArrayList<>();

    private final List<VActor> postUpdateList = new ArrayList<>();

    private final List<VActor> actorList = new ArrayList<>();

    private final List<VActor> updateList = new ArrayList<>();

    private final List<VActor> renderList = new ArrayList<>();

    private final World box2dWorld;

    public VWorld() {
        box2dWorld = new World(new Vector2(0,0),false);
    }

    public List<VActor> allActors () {
        return actorList;
    }

    public World getBox2dWorld() {
        return box2dWorld;
    }

    public <T extends VActor> T registerActor(T t) {
        actorList.add(t);
        updateList.add(t);
        renderList.add(t);
        return t;
    }

    public void init () {

    }

    public void update (float delta) {
// physic
        box2dWorld.step(delta,10,10);
//        box2dWorld.step(delta,10,10);
        for (VActor vActor : preUpdateList) {
            vActor.update(delta);
        }
        for (VActor act: updateList) {
            act.update(delta);
        }
        for (VActor act: postUpdateList) {
            act.update(delta);
        }
        actorList.sort(DEFAULT_ACTOR_COMPARE);

    }
}
