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

        // physics
        VCube cube = new VCube();
        this.hx = this.physicAttr.box2dHx;
        this.hy = this.physicAttr.box2dHy;
        cube.xLength = hx * 2;
        cube.yLength = hy * 2;
        cube.zLength = 30f;

        physicAttr.setBaseShape(cube);

    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }
}
