package com.voidvvv.game.manager.event;

import com.badlogic.gdx.utils.Pool;
import com.voidvvv.game.manager.event.attack.AttackEvent;
import com.voidvvv.game.manager.event.attack.AttackEventPool;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;

public class VWorldEventManager {
    public static final int ATTACK_EVENT_TYPE = 1;

    private Pool<AttackEvent> attackEventPool;

    private Deque<WorldEvent> events;

    public VWorldEventManager(){};

    public void init () {
        attackEventPool = new AttackEventPool(20);
        events = new LinkedList<>();
    }

    public void applyEvent(WorldEvent event) {
        event.apply();
        events.remove(event);
        freeEvent(event);
    }

    private void freeEvent(WorldEvent event) {
        if (event.getClass().isAssignableFrom(AttackEvent.class)) {
            // attack
            attackEventPool.free((AttackEvent) event);
        }
    }

    public WorldEvent newEvent(int type) {
        WorldEvent event = null;
        if (ATTACK_EVENT_TYPE == type) {
            event = attackEventPool.obtain();
        }
        if (event != null) {
            events.add(event);
        }
        return event;
    }

    public Collection<WorldEvent> currentEvents() {
        return events;
    }
}
