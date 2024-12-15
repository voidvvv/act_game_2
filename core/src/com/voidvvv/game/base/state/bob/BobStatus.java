package com.voidvvv.game.base.state.bob;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.actors.ActorConstants;
import com.voidvvv.game.base.test.Bob;

public enum BobStatus implements State<Bob> {

    IDLE(){
        @Override
        public void enter(Bob entity) {
            super.enter(entity);
        }
        @Override
        public int getId() {
            return ActorConstants.STATUS_IDLE;
        }
        @Override
        public void update(Bob entity) {
            super.update(entity);
            entity.statusProgress += Gdx.graphics.getDeltaTime();

            if (entity.baseMove.len() > 0f) {
                // have velocity
                entity.getSelfStatusStateMachine().changeState(BobStatus.WALKING);
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
        }
        @Override
        public int getId() {
            return ActorConstants.STATUS_WALKING;
        }

        @Override
        public boolean onMessage(Bob entity, Telegram telegram) {
            return false;
        }

        @Override
        public void update(Bob entity) {
            super.update(entity);
            entity.statusProgress += Gdx.graphics.getDeltaTime() * (entity.getBattleComponent().actualBattleAttr.moveSpeed / 75f);

            if (entity.baseMove.len() <= 0) {
                entity.getSelfStatusStateMachine().changeState(BobStatus.IDLE);
            }
            if (entity.baseMove.x > 0) {
                entity.flip = false;
            } else if (entity.baseMove.x < 0) {
                entity.flip = true;
            }
        }
    },
    JUMP(){
        @Override
        public void enter(Bob entity) {
            super.enter(entity);
        }

        @Override
        public void exit(Bob entity) {
            super.exit(entity);
        }


        @Override
        public void update(Bob entity) {
            super.update(entity);
        }

        @Override
        public boolean onMessage(Bob entity, Telegram telegram) {
            return false;
        }
    },
    JUMP_ATTACK(){
        @Override
        public void enter(Bob entity) {
            super.enter(entity);
        }

        @Override
        public void exit(Bob entity) {
            super.exit(entity);
        }

        @Override
        public void update(Bob entity) {
            super.update(entity);
        }

        @Override
        public boolean onMessage(Bob entity, Telegram telegram) {
            return false;
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
        }

        @Override
        public boolean onMessage(Bob entity, Telegram telegram) {
            return false;
        }
    },
    SPELL_0{
        @Override
        public void enter(Bob entity) {
            super.enter(entity);
        }

        @Override
        public int getId() {
            return ActorConstants.STATUS_SPELLING_01;
        }

        @Override
        public void exit(Bob entity) {
            super.exit(entity);
        }

        @Override
        public void update(Bob entity) {
            super.update(entity);

        }

        @Override
        public boolean onMessage(Bob entity, Telegram telegram) {
            return false;
        }

    },
    DYING{
        @Override
        public void enter(Bob entity) {
            super.enter(entity);
            ActGame.gameInstance().getSystemNotifyMessageManager().pushMessage(entity.getName() + " enter - DYING");
        }

        @Override
        public void exit(Bob entity) {
            super.exit(entity);

        }


        @Override
        public void update(Bob entity) {
            super.update(entity);
            entity.baseMove.set(0,0,0);
            if (entity.statusTime >= 2f) {
                entity.getWorld().destroyActor(entity);
            }
        }

        @Override
        public boolean onMessage(Bob entity, Telegram telegram) {
            return false;
        }
    },
    ;


    @Override
    public void enter(Bob entity) {
        entity.statusTime = 0;
        entity.statusProgress = 0f;
    }



    @Override
    public void update(Bob entity) {
        entity.statusTime += Gdx.graphics.getDeltaTime();

    }

    @Override
    public void exit(Bob entity) {
        entity.statusTime = 0;entity.statusProgress = 0f;
    }

    public int getId () {
        return 0;
    }

}
