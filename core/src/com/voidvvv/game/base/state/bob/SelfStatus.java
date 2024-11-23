package com.voidvvv.game.base.state.bob;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.voidvvv.game.base.test.Bob;

public enum SelfStatus implements State<Bob> {

    IDLE(){
        @Override
        public void enter(Bob entity) {

        }

        @Override
        public void update(Bob entity) {
            this.time+= Gdx.graphics.getDeltaTime();
        }

        @Override
        public void exit(Bob entity) {

        }

        @Override
        public boolean onMessage(Bob entity, Telegram telegram) {
            return false;
        }
    };
    public float time;

}
