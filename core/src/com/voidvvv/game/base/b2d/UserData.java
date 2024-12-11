package com.voidvvv.game.base.b2d;

import com.voidvvv.game.base.VActor;

public class UserData {
    public static enum B2DType {
        FACE, GROUND;
    }
    private float subShifting;

    private long category;

    private long mask;

    VActor actor;

    B2DType type;

    public B2DType getType() {
        return type;
    }

    public void setType(B2DType type) {
        this.type = type;
    }

    public VActor getActor() {
        return actor;
    }

    public void setActor(VActor actor) {
        this.actor = actor;
    }

    public long getCategory() {
        return category;
    }

    public void setCategory(long category) {
        this.category = category;
    }

    public long getMask() {
        return mask;
    }

    public void setMask(long mask) {
        this.mask = mask;
    }

    public float getSubShifting() {
        return subShifting;
    }

    public void setSubShifting(float subShifting) {
        this.subShifting = subShifting;
    }
}
