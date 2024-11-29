package com.voidvvv.game.base.test;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.VSkillCharacter;
import com.voidvvv.game.base.shape.VCube;
import com.voidvvv.game.base.skill.Skill;
import com.voidvvv.game.base.skill.SkillDes;
import com.voidvvv.game.base.skill.base.TestSkill;
import com.voidvvv.game.base.state.bob.BobStatus;
import com.voidvvv.game.context.VActorSpawnHelper;
import com.voidvvv.game.context.WorldContext;
import com.voidvvv.game.context.input.InputActionMapping;
import com.voidvvv.game.manager.event.spell.SpellWorldEvent;
import com.voidvvv.game.render.actor.VActorRender;
import com.voidvvv.game.render.actor.test.bob.DefaultBobRender;
import com.voidvvv.game.utils.ReflectUtil;
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
        this.getActualBattleAttr().maxHp = 1500;
        this.getActualBattleAttr().hp = 1500;

        setName("Bob" + MathUtils.random(10));

        selfStatusStateMachine = new DefaultStateMachine<>(this);
        selfStatusStateMachine.setInitialState(BobStatus.IDLE);

        if (this.vActorRender == null) {
            vActorRender = new DefaultBobRender();
        }

        skills[0] = new SkillDes();
        skills[0].setDes("发射闪光炸弹");
        skills[0].setSkillClass(TestSkill.class);
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
            Skill obtain = Pools.obtain(skills[0].getSkillClass());
            obtain.setOwner(this);
            this.tryToUseSkill(obtain);
        }
    }


    @Override
    public void reset() {
        super.reset();

    }

    @Override
    public void tryToBackToNormal() {
        this.getSelfStatusStateMachine().changeState(BobStatus.IDLE);
    }

    @Override
    public void enterStatusForSkill(TestSkill testSkill) {
        this.getSelfStatusStateMachine().changeState(BobStatus.SPELL_0);
    }

    @Override
    protected void becomeDying() {
        if (this.selfStatusStateMachine.getCurrentState() != BobStatus.DYING) {
            this.selfStatusStateMachine.changeState(BobStatus.DYING);
        }
    }

    @Override
    public boolean isDying() {
        return this.selfStatusStateMachine.getCurrentState() == BobStatus.DYING;
    }


}
