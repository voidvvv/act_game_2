package com.voidvvv.game.battle;

import com.badlogic.gdx.utils.Pool;

public class BattleAttrDeltaData implements Pool.Poolable {
    public float add = 0f; // add or subtract
    public float multi = 1f; // multi

    public boolean dirty = false;

    @Override
    public void reset() {
        this.add = 0f;
        this.multi = 1f;
        dirty = false;
    }

}
