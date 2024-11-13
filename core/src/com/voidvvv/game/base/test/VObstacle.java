package com.voidvvv.game.base.test;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VPhysicAttr;
import com.voidvvv.game.base.shape.VCube;
import com.voidvvv.game.context.VWorld;
import com.voidvvv.game.context.WorldContext;

public class VObstacle extends VActor {
    private boolean initFlag = false;
    public float initX;

    public float initY;

    public float hx;

    public float hy;

    @Override
    public void init() {
        if (initFlag){
            return;
        }
        initFlag = true;
        super.init();
        VWorld world = WorldContext.getWorld();
        world.registerActor(this);

        // physics
        VPhysicAttr physicAttr = new VPhysicAttr();
        VCube cube = new VCube();
        cube.xLength = hx * 2;
        cube.yLength = hy * 2;
        cube.zLength = 30f;

        physicAttr.setBaseShape(cube);
        this.setPhysicAttr(physicAttr);

        Fixture roleFixture = WorldContext.createObstacle(BodyDef.BodyType.StaticBody, initX, initY, hx, hy);
        this.setFixture(roleFixture);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }
}
