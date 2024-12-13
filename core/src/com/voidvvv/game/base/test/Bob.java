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
import com.voidvvv.game.base.state.bob.BobStatus;
import com.voidvvv.game.context.input.InputActionMapping;
import com.voidvvv.game.render.actor.test.bob.DefaultBobRender;
import lombok.Getter;
import lombok.Setter;

public class Bob extends VCharacter {
    @Getter
    @Setter
    StateMachine<Bob, BobStatus> selfStatusStateMachine;

    private DefaultBobRender defaultBobRender;




    @Override
    public void vCAct(float delta) {
        super.vCAct(delta);
        // skill update
        skillUpdate();
        stateUpdate(delta);
    }

    protected void skillUpdate() {
        useSkill(this.skillCode);
        skill.update(Gdx.graphics.getDeltaTime());
        this.skillCode = -1;
    }

    private void stateUpdate(float delta) {
        selfStatusStateMachine.update();

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

        selfStatusStateMachine = new DefaultStateMachine<>(this);
        selfStatusStateMachine.setInitialState(BobStatus.IDLE);

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
        if (skillCode == InputActionMapping.SKILL_W) {
            skill2.use();
        }
    }


    @Override
    public void reset() {
        super.reset();

    }

    @Override
    public void changeStatus(int status) {
        if (status == ActorConstants.STATUS_IDLE) {
            this.selfStatusStateMachine.changeState(BobStatus.IDLE);
        } else if (status == ActorConstants.STATUS_SPELLING_01) {
            this.selfStatusStateMachine.changeState(BobStatus.SPELL_0);
        } else if (status == ActorConstants.STATUS_ATTACK_01) {
            this.selfStatusStateMachine.changeState(BobStatus.ATTACKING_0);

        }
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

    @Override
    public int currentStateId() {
        return this.selfStatusStateMachine.getCurrentState().getId();
    }
}
