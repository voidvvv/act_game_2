package com.voidvvv.game.base.buff;

import com.voidvvv.game.base.VActor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BuffComponent {
    VActor owner;
    private Set<Buff> init = new HashSet<>();
    private List<Buff> buffs = new ArrayList<>();
    private Set<Buff> trashBin = new HashSet<>();

    public VActor getOwner() {
        return owner;
    }

    public void setOwner(VActor owner) {
        this.owner = owner;
    }

    public List<Buff> getBuffs() {
        return buffs;
    }

    public void add(Buff buff) {
        if (buff == null) {
            return;
        }
        buff.setOwner(this.owner);
        init.add(buff);
    }

    public void remove(Buff buff) {
        if (buff == null) {
            return;
        }
        trashBin.add(buff);
    }

    public void update () {
        if (!init.isEmpty()) {
            for (Buff buff : init) {
                buff.enter();
            }
            buffs.addAll(init);
            buffs.sort(BuffCompare.INSTANCE);
            init.clear();
        }
        for (Buff buff : trashBin) {
            buff.exit();
        }
        buffs.removeAll(trashBin);
        trashBin.clear();
    }
}
