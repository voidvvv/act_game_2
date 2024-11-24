package com.voidvvv.game.base.state.bob;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.skill.Skill;
import com.voidvvv.game.base.test.Bob;

public enum BobStatus implements State<Bob> {

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
    },
    SPELL_0{
        @Override
        public void enter(Bob entity) {
            super.enter(entity);
            Skill skill = entity.currentSkill();
            if (skill == null || !entity.skillNew) {
                entity.getSelfStatusStateMachine().changeState(BobStatus.IDLE);
            } else {
                skill.start();
                entity.skillNew = false;
            }
        }

        @Override
        public void exit(Bob entity) {
            super.exit(entity);
            if (entity.currentSkill() != null && !entity.skillNew) {
                entity.currentSkill().end();
                entity.over(entity.currentSkill());
            }
        }

        @Override
        public void update(Bob entity) {
            super.update(entity);
            Skill skill = entity.currentSkill();
            if (skill != null  && !entity.skillNew) {
                skill.process(Gdx.graphics.getDeltaTime());

            } else {
                entity.getSelfStatusStateMachine().changeState(BobStatus.IDLE);
            }
        }

        @Override
        public boolean onMessage(Bob entity, Telegram telegram) {
            return false;
        }

        @Override
        public Skill findSkill(Bob entity) {
            return entity.currentSkill();
        }
    }
    ;


    @Override
    public void enter(Bob entity) {
        entity.statusTime = 0;
    }



    @Override
    public void update(Bob entity) {
        entity.statusTime += Gdx.graphics.getDeltaTime();
    }

    @Override
    public void exit(Bob entity) {
        entity.statusTime = 0;
    }

    public Skill findSkill (Bob entity) {
        return null;
    }
}
