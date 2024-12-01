package com.voidvvv.game.base.test;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.base.VSkillCharacter;
import com.voidvvv.game.base.shape.VCube;
import com.voidvvv.game.base.skill.Skill;
import com.voidvvv.game.base.skill.SkillDes;
import com.voidvvv.game.base.skill.base.TestSkill;
import com.voidvvv.game.base.state.bob.BobStatus;
import com.voidvvv.game.context.input.InputActionMapping;
import com.voidvvv.game.render.actor.test.bob.DefaultBobRender;
import lombok.Getter;
import lombok.Setter;

public class Bob extends VSkillCharacter{
    @Getter
    @Setter
    StateMachine<Bob, BobStatus> selfStatusStateMachine;

    private DefaultBobRender defaultBobRender;


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

        if (this.defaultBobRender == null) {
            defaultBobRender = new DefaultBobRender();
        }

        SkillDes lightingBoom = new SkillDes();
        lightingBoom.setSkillClass(TestSkill.class);
        lightingBoom.setDes("闪光炸弹");
        replaceSkill(0, lightingBoom);

    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        defaultBobRender.render(this, batch, parentAlpha);
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
        Skill skill = currentSkill;
        this.getSelfStatusStateMachine().changeState(BobStatus.IDLE);
        if (skill != null) {
            Pools.free(skill);
        }
    }

    @Override
    public void enterStatusForSkill(Skill testSkill) {
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
