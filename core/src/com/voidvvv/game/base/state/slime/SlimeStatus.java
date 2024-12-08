package com.voidvvv.game.base.state.slime;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.voidvvv.game.base.actors.Slime;
import com.voidvvv.game.base.state.bob.BobStatus;

public enum SlimeStatus implements State<Slime> {
    IDEL() {
        @Override
        public void enter(Slime entity) {
            super.enter(entity);
        }

        @Override
        public void exit(Slime entity) {
            super.exit(entity);
        }

        @Override
        public boolean onMessage(Slime entity, Telegram telegram) {
            return false;
        }

        @Override
        public void update(Slime entity) {
            if (entity.baseMove.len() > 0f) {
                // have velocity
                entity.getDefalutStateMachine().changeState(SlimeStatus.WALKING);
            }
        }
    },
    WALKING() {
        @Override
        public void enter(Slime entity) {
            super.enter(entity);
        }

        @Override
        public void exit(Slime entity) {
            super.exit(entity);
        }

        @Override
        public boolean onMessage(Slime entity, Telegram telegram) {
            return false;
        }

        @Override
        public void update(Slime entity) {
            if (entity.baseMove.len() <= 0) {
                entity.getDefalutStateMachine().changeState(IDEL);
            }
            if (entity.baseMove.x > 0) {
                entity.flip = false;
            } else if (entity.baseMove.x < 0) {
                entity.flip = true;
            }
        }
    },
    DYING{
        @Override
        public void enter(Slime entity) {
            super.enter(entity);
        }

        @Override
        public void exit(Slime entity) {
            super.exit(entity);
        }

        @Override
        public boolean onMessage(Slime entity, Telegram telegram) {
            return false;
        }

        @Override
        public void update(Slime entity) {

        }
    };


    @Override
    public void enter(Slime entity) {
        entity.statusTime = 0f;
    }

    @Override
    public void exit(Slime entity) {
        entity.statusTime = 0f;
    }
}
