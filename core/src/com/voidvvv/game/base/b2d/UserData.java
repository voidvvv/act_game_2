package com.voidvvv.game.base.b2d;

import com.voidvvv.game.base.VActor;

public class UserData {
    private float subShifting;

    private short category;

    VActor actor;

    public VActor getActor() {
        return actor;
    }

    public void setActor(VActor actor) {
        this.actor = actor;
    }

    public short getCategory() {
        return category;
    }

    public void setCategory(short category) {
        this.category = category;
    }

    public float getSubShifting() {
        return subShifting;
    }

    public void setSubShifting(float subShifting) {
        this.subShifting = subShifting;
    }
}
