package com.voidvvv.game.base.buff;

import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.base.VActor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BuffComponent {
    VActor owner;
    private Set<Buff> init = new HashSet<>();
    private Set<Buff> buffs = new HashSet<>();
    private Set<Buff> trashBin = new HashSet<>();

    public VActor getOwner() {
        return owner;
    }

    public void setOwner(VActor owner) {
        this.owner = owner;
    }

    public Set<Buff> getBuffs() {
        return buffs;
    }

    public void add(Buff buff) {
        if (buff == null) {
            return;
        }
        init.add(buff);
    }

    public void remove(Buff buff) {
        if (buff == null) {
            return;
        }
        trashBin.add(buff);
    }

    public void update () {
        for (Buff buff : buffs) {
            buff.update();
            if (buff.expire()) {
                remove(buff);
            }
        }
        if (!init.isEmpty()) {
            for (Buff buff : init) {
                boolean add = buffs.add(buff);
                if (add) {
                    buff.setOwner(owner);
                    buff.enter();
                } else {
                    // add fail!
                    Pools.free(buff);
                }
            }
            init.clear();
        }
        buffs.removeAll(trashBin);
        Iterator<Buff> iterator = trashBin.iterator();
        while (iterator.hasNext()) {
            Buff buff = iterator.next();
            Pools.free(buff);
            iterator.remove();
        }
    }
}
