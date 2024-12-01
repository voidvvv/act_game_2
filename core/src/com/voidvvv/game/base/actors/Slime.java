package com.voidvvv.game.base.actors;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VSkillCharacter;
import com.voidvvv.game.base.skill.Skill;
import com.voidvvv.game.base.state.bob.BobStatus;
import com.voidvvv.game.base.state.slime.SlimeStatus;

public class Slime extends VSkillCharacter {

    StateMachine<Slime, SlimeStatus> defalutStateMachine;

    @Override
    public void init() {
        super.init();
        if (defalutStateMachine == null) {
            defalutStateMachine = new DefaultStateMachine<>();
        }
    }

    public StateMachine<Slime, SlimeStatus> getDefalutStateMachine() {
        return defalutStateMachine;
    }

    @Override
    public void vCAct(float delta) {
        super.vCAct(delta);

        stateUpdate(delta);
    }

    private void stateUpdate(float delta) {
        defalutStateMachine.update();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void useSkill(int skillCode) {
        super.useSkill(skillCode);
    }

    @Override
    public void reset() {
        super.reset();
    }


    @Override
    public void tryToBackToNormal() {
    }

    @Override
    public void enterStatusForSkill(Skill testSkill) {
    }

    @Override
    protected void becomeDying() {
    }

    @Override
    public boolean isDying() {
        return false;
    }

}
