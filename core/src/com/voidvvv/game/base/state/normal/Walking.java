package com.voidvvv.game.base.state.normal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.MathUtils;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.state.VCharactorStatus;

public class Walking extends VCharactorStatus {
    public static final Walking INSTANCE = new Walking();
    private Walking(){}

    @Override
    public void exec(VCharacter entity) {
        entity.statusTime += Gdx.graphics.getDeltaTime();
        entity.statusProgress += Gdx.graphics.getDeltaTime();


    }

    @Override
    public void enter(VCharacter entity) {
        entity.statusTime = 0f;
        entity.statusProgress = 0f;

        if (entity.baseMove.x < 0) {
            entity.flipX = true;
        } else if (entity.baseMove.x > 0) {
            entity.flipX = false;
        }

        if (entity.baseMove.y > 0) {
            entity.flipY = true;
        } else if (entity.baseMove.y < 0) {
            entity.flipY = false;
        }

    }

    @Override
    public void exit(VCharacter entity) {

    }

    @Override
    protected void onMoveChange(VCharacter entity, Telegram telegram) {
        if (entity.baseMove.x < 0) {
            entity.flipX = true;
        } else if (entity.baseMove.x > 0) {
            entity.flipX = false;
        }

        if (entity.baseMove.y > 0) {
            entity.flipY = true;
        } else if (entity.baseMove.y < 0) {
            entity.flipY = false;
        }

        if (MathUtils.isEqual(entity.baseMove.len(), 0f)) {
            entity.getStateMachine().changeState(Idle.INSTANCE);
        }
    }


    @Override
    public void onMagicSpell(VCharacter entity, Telegram telegram) {
        entity.getStateMachine().changeState(Spelling.INSTANCE);
    }

    @Override
    public void onPhysicSpell(VCharacter entity, Telegram telegram) {
        entity.getStateMachine().changeState(NormalAttack.INSTANCE);
    }
}
