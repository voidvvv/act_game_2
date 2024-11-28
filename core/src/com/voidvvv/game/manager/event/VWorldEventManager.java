package com.voidvvv.game.manager.event;

import com.badlogic.gdx.utils.Pools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class VWorldEventManager {

    public static final int DAMAGE_EVENT = 1;

    public static final int SPELL_EVENT = 2;

    private Deque<WorldEvent> events;

    private List<WorldEvent> initList = new ArrayList<>();

    private List<WorldEvent> trashBin = new ArrayList<>();

    public VWorldEventManager(){};

    public void init () {
        events = new LinkedList<>();
    }

    private void freeEvent(WorldEvent event) {
        trashBin.add(event);
    }

    public <T extends WorldEvent> T newEvent(Class<T> eventClazz) {
        T event = Pools.obtain(eventClazz);
        if (event != null) {
            initList.add(event);
        }
        return event;
    }

    public Collection<WorldEvent> currentEvents() {
        return events;
    }

    public void update (float delta) {
        if (!initList.isEmpty()) {
            events.addAll(initList);
            initList.clear();
        }
        if (!trashBin.isEmpty()) {
            Iterator<WorldEvent> iterator = trashBin.iterator();
            while (iterator.hasNext()) {
                WorldEvent next = iterator.next();
                Pools.free(next);
                iterator.remove();
            }
        }
        Iterator<WorldEvent> iterator = events.iterator();
        while (iterator.hasNext()) {
            WorldEvent next = iterator.next();
            next.apply();
            if (isEnd(next)) {
                iterator.remove();
                freeEvent(next);
            }
        }
    }

    public boolean isEnd(WorldEvent event) {
        return event.status == WorldEvent.FINISH;
    }
}
