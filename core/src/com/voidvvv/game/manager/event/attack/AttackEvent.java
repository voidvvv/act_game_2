package com.voidvvv.game.manager.event.attack;

import com.voidvvv.game.manager.event.VWorldEventManager;
import com.voidvvv.game.manager.event.WorldEvent;

public class AttackEvent extends WorldEvent {
    public static final int type = VWorldEventManager.ATTACK_EVENT_TYPE;



    public final int type () {
        return type;
    }

    @Override
    public void apply() {

    }
}
