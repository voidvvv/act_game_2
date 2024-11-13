package com.voidvvv.game.base.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.*;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.VPhysicAttr;
import com.voidvvv.game.base.shape.VCube;
import com.voidvvv.game.context.VWorld;
import com.voidvvv.game.context.WorldContext;

public class Bob extends VCharacter {



    @Override
    public void update(float delta) {
        super.update(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            baseMove.y = 20;
        }else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            baseMove.y = -20;
        }else {
            baseMove.y = 0f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            baseMove.x = 20;
        }else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            baseMove.x = -20;
        }else {
            baseMove.x = 0f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.K)) {
            this.jump();
        }
    }

    @Override
    public void init() {
        super.init();
        // my world
        VWorld world = WorldContext.getWorld();
        world.registerActor(this);

        // physics
        VPhysicAttr physicAttr = new VPhysicAttr();
        VCube cube = new VCube();
        cube.xLength = cube.yLength = cube.zLength = 10.f;
        cube.zLength = 25.f;
        physicAttr.setBaseShape(cube);
        this.setPhysicAttr(physicAttr);

        // box2d
        Fixture roleFixture = WorldContext.createRoleFixture(BodyDef.BodyType.DynamicBody, 20, 20, 5f, 5f);
        this.setFixture(roleFixture);
    }

    @Override
    public void render() {
        super.render();
    }
}
