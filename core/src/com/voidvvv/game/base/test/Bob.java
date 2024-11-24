package com.voidvvv.game.base.test;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.VSkillCharacter;
import com.voidvvv.game.base.shape.VCube;
import com.voidvvv.game.base.state.bob.BobStatus;
import com.voidvvv.game.context.VActorSpawnHelper;
import com.voidvvv.game.context.WorldContext;
import com.voidvvv.game.context.input.InputActionMapping;
import com.voidvvv.game.render.actor.VActorRender;
import com.voidvvv.game.render.actor.test.bob.DefaultBobRender;
import lombok.Getter;
import lombok.Setter;

public class Bob extends VSkillCharacter {
    @Getter
    @Setter
    StateMachine<Bob, BobStatus> selfStatusStateMachine;

    public float statusTime;

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
        cube.zLength = getWorld().unit() *2;
        physicAttr.setBaseShape(cube);
        this.setPhysicAttr(physicAttr);
        this.getActualBattleAttr().hp = 100;
        setName("Bob" + MathUtils.random(10));

        selfStatusStateMachine = new DefaultStateMachine<>(this);
        selfStatusStateMachine.setInitialState(BobStatus.IDLE);

        if (this.vActorRender == null) {
            vActorRender = new DefaultBobRender();
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        super.draw(batch, parentAlpha);
        vActorRender.render(this, batch, parentAlpha);
    }

    public float q_standup_time = 1f;
    public boolean q_consume = false;
    @Override
    public void useSkill(int skillCode) {
        if (skillCode == InputActionMapping.SKILL_Q) {
            if (selfStatusStateMachine.getCurrentState() == BobStatus.ATTACKING_0) {
                return;
            }
            selfStatusStateMachine.changeState(BobStatus.ATTACKING_0);
            this.interruptPathFinding();
            q_standup_time = 1f;
        }
    }

    public void launchQ () {
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

    @Override
    public void reset() {
        super.reset();
    }
}
