package com.voidvvv.game.base.test;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.asset.BobAssetConstant;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.shape.VCube;
import com.voidvvv.game.base.state.bob.SelfStatus;
import com.voidvvv.game.context.VActorSpawnHelper;
import com.voidvvv.game.context.WorldContext;
import com.voidvvv.game.context.input.InputActionMapping;
import com.voidvvv.game.render.actor.VActorRender;
import com.voidvvv.game.render.actor.bob.DefaultBobRender;
import lombok.Getter;
import lombok.Setter;

public class Bob extends VCharacter {
    @Getter
    @Setter
    StateMachine<Bob, SelfStatus> selfStatusStateMachine;

    private VActorRender<Bob> vActorRender;


    @Override
    public void vCAct(float delta) {
        super.vCAct(delta);

        stateUpdate(delta);
    }

    private void stateUpdate(float delta) {
        selfStatusStateMachine.update();
    }

    @Override
    public void init() {
        super.init();
        // physics
        VCube cube = new VCube();
        cube.xLength = this.physicAttr.box2dHx * 2f;
        cube.yLength = this.physicAttr.box2dHy * 2f;
        cube.zLength = this.physicAttr.box2dHy * 2f;
        physicAttr.setBaseShape(cube);
        this.setPhysicAttr(physicAttr);
        this.getActualBattleAttr().hp = 100;
        setName("Bob" + MathUtils.random(10));

        selfStatusStateMachine = new DefaultStateMachine<>(this);
        selfStatusStateMachine.setInitialState(SelfStatus.IDLE);

        if (this.vActorRender == null) {
            vActorRender = new DefaultBobRender();
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        super.draw(batch, parentAlpha);
        vActorRender.render(this, batch, parentAlpha);
    }

    @Override
    public void useSkill(int skillCode) {
        if (skillCode == InputActionMapping.SKILL_Q) {

            VActorSpawnHelper helper = VActorSpawnHelper.builder()
                    .bodyType(BodyDef.BodyType.DynamicBody)
                    .category((short) (WorldContext.ROLE | WorldContext.WHITE)) // who am I
                    .mask((short) (WorldContext.OBSTACLE | WorldContext.BLACK | WorldContext.ROLE)) // who do I want to collision
                    .hx(5).hy(5)
                    .initX(position.x).initY(getY())
                    .build();

            TestBullet testBullet = getWorld().spawnVActor(TestBullet.class, helper);
            testBullet.targetGroup = WorldContext.BLACK;
            testBullet.getActualBattleAttr().moveSpeed = 10000 * 1.5f;
            testBullet.setParentVActor(this);
            testBullet.baseMove.set(getWorld().currentPointerPose.x - position.x, getWorld().currentPointerPose.y - getY(), 0);
        }
    }

    @Override
    public void reset() {
        super.reset();
    }
}
