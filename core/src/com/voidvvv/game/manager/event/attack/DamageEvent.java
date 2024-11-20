package com.voidvvv.game.manager.event.attack;

import com.voidvvv.game.manager.event.VWorldEventManager;
import com.voidvvv.game.manager.event.WorldEvent;

public class DamageEvent extends WorldEvent {
    public static final int type = VWorldEventManager.DAMAGE_EVENT;
    public final int type () {
        return type;
    }

    @Override
    public void apply() {

    }
}
