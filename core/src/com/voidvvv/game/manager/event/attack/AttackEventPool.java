package com.voidvvv.game.manager.event.attack;

import com.badlogic.gdx.utils.Pool;

public class AttackEventPool extends Pool<AttackEvent> {

    public AttackEventPool() {
        super();
    }

    public AttackEventPool(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    protected AttackEvent newObject() {
        return new AttackEvent();
    }
}
