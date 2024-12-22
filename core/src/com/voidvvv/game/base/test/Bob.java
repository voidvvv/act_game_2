package com.voidvvv.game.base.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.actors.ActorConstants;
import com.voidvvv.game.base.shape.VCube;
import com.voidvvv.game.base.skill.v2.HitSkill;
import com.voidvvv.game.base.skill.v2.LightBoomSkill;
import com.voidvvv.game.base.skill.v2.Skill;
import com.voidvvv.game.base.state.VCharactorStatus;
import com.voidvvv.game.base.state.normal.Idle;
import com.voidvvv.game.context.input.InputActionMapping;
import com.voidvvv.game.render.actor.test.bob.DefaultBobRender;

public class Bob extends VCharacter {
    StateMachine<VCharacter, VCharactorStatus> statusStateMachine;

    private DefaultBobRender defaultBobRender;





    @Override
    public void vCAct(float delta) {
        super.vCAct(delta);
        stateUpdate(delta);
        // skill update
        skillUpdate();

    }

    protected void skillUpdate() {
        useSkill(this.skillCode);
        skill.update(Gdx.graphics.getDeltaTime());
        this.skillCode = -1;
    }

    private void stateUpdate(float delta) {

        statusStateMachine.update();
    }

    @Override
    public void init() {
        super.init();
        this.actorType = ActorConstants.ACTOR_TYPE_CHARACTER;
        // physics
        this.getActualBattleAttr().maxHp = 1500;
        this.getActualBattleAttr().hp = 1500;
        this.getActualBattleAttr().mp = 0f;

        setName("Bob" + MathUtils.random(10));
        statusStateMachine = new DefaultStateMachine<>(this);
        statusStateMachine.setInitialState(Idle.INSTANCE);
        if (this.defaultBobRender == null) {
            defaultBobRender = new DefaultBobRender();
        }
        if (skill == null) {
            skill = new LightBoomSkill();
            skill.setOwner(this);
        }

        if (skill2 == null) {
            skill2 = new HitSkill();
            skill2.setOwner(this);
        }
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        defaultBobRender.render(this, batch, parentAlpha);
    }

    public float q_standup_time = 1f;
    public boolean q_consume = false;

    public int skillCode = -1;
    @Override
    public void setFrameSkill(int keycode) {
        skillCode = keycode;
    }

    Skill skill;
    Skill skill2;
    @Override
    public void useSkill(int skillCode) {
        if (skillCode == InputActionMapping.SKILL_Q) {
            skill.use();
        }
        if (skillCode == InputActionMapping.SKILL_E) {
            skill2.use();
        }
    }


    @Override
    public void reset() {
        super.reset();

    }

    @Override
    public void changeStatus(int status) {

    }


    @Override
    public boolean isDying() {
        return false;
    }

    @Override
    public int currentStateId() {
        return 0;
    }

    @Override
    public StateMachine<VCharacter, VCharactorStatus> getStateMachine() {
        return statusStateMachine;
    }
}
