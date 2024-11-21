package com.voidvvv.game.base.test;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.shape.VCube;
import com.voidvvv.game.utils.ReflectUtil;

public class TestBullet extends VCharacter {

    public float maxLive = 10f;

    public Vector2 direction = new Vector2();


    public short targetGroup = 0;

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void init() {
        super.init();
        VCube cube = new VCube();
        cube.xLength = this.physicAttr.box2dHx * 2f;
        cube.yLength = this.physicAttr.box2dHy * 2f;
        cube.zLength = 10f;
        physicAttr.setBaseShape(cube);

    }

    @Override
    public void onHit(VActor actor) {
        super.onHit(actor);
        VCharacter character = ReflectUtil.cast(actor,VCharacter.class);
        Fixture fixture = actor.getFixture();
        short categoryBits = fixture.getFilterData().categoryBits;
        boolean isTarget = (categoryBits&targetGroup) == targetGroup;
        if (isTarget && character != null) {
            // enemy target character

        }
    }
}
