package com.voidvvv.game.base.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.shape.VCube;

public class Bob extends VCharacter {

    @Override
    public void act(float delta) {
        super.act(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.K)) {
            this.jump();
        }
    }

    @Override
    public void init() {
        super.init();

        // physics
        VCube cube = new VCube();
        cube.xLength = this.physicAttr.box2dHx * 2f;
        cube.yLength = this.physicAttr.box2dHy * 2f;
        cube.zLength = 25.f;
        physicAttr.setBaseShape(cube);
        this.setPhysicAttr(physicAttr);

    }

    @Override
    public void render() {
        super.render();
    }
}
