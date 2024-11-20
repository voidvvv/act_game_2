package com.voidvvv.game.manager.event;

import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.manager.event.attack.DamageEvent;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;

public class VWorldEventManager {
    public static final int DAMAGE_EVENT = 1;

    private Deque<WorldEvent> events;

    public VWorldEventManager(){};

    public void init () {
        events = new LinkedList<>();
    }

    public void applyEvent(WorldEvent event) {
        event.apply();
        events.remove(event);
        freeEvent(event);
    }

    private void freeEvent(WorldEvent event) {
        if (event.getClass().isAssignableFrom(DamageEvent.class)) {
            // attack
            Pools.free(event);
        }
    }

    public WorldEvent newEvent(int type) {
        WorldEvent event = null;
        if (DAMAGE_EVENT == type) {
            event = Pools.obtain(DamageEvent.class);
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
