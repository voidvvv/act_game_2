package com.voidvvv.game.base;

import com.badlogic.gdx.utils.Pools;

import java.util.ArrayList;
import java.util.List;

public class VActorListenerComponent {
    public List<VActorListener> initListeners = new ArrayList<VActorListener>();
    public List<VActorListener> listeners = new ArrayList<VActorListener>();
    public List<VActorListener> trashBin = new ArrayList<VActorListener>();


    public void add(VActorListener listener) {
        initListeners.add(listener);
    }

    public void remove(VActorListener listener) {
        trashBin.add(listener);
    }

    public void update () {
        listeners.addAll(initListeners);
        initListeners.clear();
        for (VActorListener listener : trashBin) {
            Pools.free(listener);
            listeners.remove(listener);
        }
        trashBin.clear();
    }


    public void reset() {
        for (VActorListener listener : listeners) {
            Pools.free(listener);
        }
        this.listeners.clear();

        for (VActorListener listener : trashBin) {
            Pools.free(listener);
        }
        this.trashBin.clear();

        for (VActorListener listener : initListeners) {
            Pools.free(listener);
        }
        this.initListeners.clear();
    }
}
