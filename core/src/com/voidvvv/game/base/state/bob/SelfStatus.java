package com.voidvvv.game.base.state.bob;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.test.Bob;

public enum SelfStatus implements State<Bob> {

    IDLE(){
        @Override
        public void enter(Bob entity) {
            ActGame.gameInstance().getSystemNotifyMessageManager().pushMessage("IDLE 进入");

        }

        @Override
        public void update(Bob entity) {
            super.update(entity);
            if (entity.baseMove.len() > 0f) {
                // have velocity
                entity.getSelfStatusStateMachine().changeState(SelfStatus.WALKING);
            }
        }


        @Override
        public boolean onMessage(Bob entity, Telegram telegram) {
            return false;
        }
    },
    WALKING(){
        @Override
        public void enter(Bob entity) {
            if (entity.baseMove.x > 0) {
                entity.flip = false;
            } else if (entity.baseMove.x < 0) {
                entity.flip = true;
            }
            ActGame.gameInstance().getSystemNotifyMessageManager().pushMessage("WALKING 进入");

        }


        @Override
        public boolean onMessage(Bob entity, Telegram telegram) {
            return false;
        }

        @Override
        public void update(Bob entity) {
            super.update(entity);
            if (entity.baseMove.len() <= 0) {
                entity.getSelfStatusStateMachine().changeState(SelfStatus.IDLE);
            }
            if (entity.baseMove.x > 0) {
                entity.flip = false;
            } else if (entity.baseMove.x < 0) {
                entity.flip = true;
            }
        }
    },
    ATTACKING_0(){
        @Override
        public void enter(Bob entity) {
            super.enter(entity);
            ActGame.gameInstance().getSystemNotifyMessageManager().pushMessage("ATTACKING_0 进入");
        }

        @Override
        public void exit(Bob entity) {
            super.exit(entity);
        }

        @Override
        public void update(Bob entity) {
            super.update(entity);
            if (entity.statusTime >= entity.q_standup_time) {
                entity.getSelfStatusStateMachine().changeState(IDLE);
                return;
            }

            entity.baseMove.x = 0f;
            entity.baseMove.y = 0f;
        }

        @Override
        public boolean onMessage(Bob entity, Telegram telegram) {
            return false;
        }
    }
    ;


    @Override
    public void enter(Bob entity) {

    }



    @Override
    public void update(Bob entity) {
        entity.statusTime += Gdx.graphics.getDeltaTime();
    }

    @Override
    public void exit(Bob entity) {
        entity.statusTime = 0;
    }

}
