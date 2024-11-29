package com.voidvvv.game.base.buff;

import java.util.Comparator;

public class BuffCompare implements Comparator<Buff> {
    public static final BuffCompare INSTANCE = new BuffCompare();

    @Override
    public int compare(Buff o1, Buff o2) {
        return o1.getOrder().getOrder() - o2.getOrder().getOrder();
    }
}
