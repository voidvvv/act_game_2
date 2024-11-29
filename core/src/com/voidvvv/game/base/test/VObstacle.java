package com.voidvvv.game.base.test;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.shape.VCube;
import com.voidvvv.game.render.actor.VActorRender;
import com.voidvvv.game.render.actor.test.stone.StoneRender;

public class VObstacle extends VActor {
    private boolean initFlag = false;
    public float initX;

    public float initY;

    public float hx;

    public float hy;

    VActorRender<VObstacle> render;

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
        cube.zLength = 18f;

        physicAttr.setBaseShape(cube);

        if (render == null) {
            render = new StoneRender();
            render.init();
        }

    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        render.render(this, batch, parentAlpha);
    }
}
