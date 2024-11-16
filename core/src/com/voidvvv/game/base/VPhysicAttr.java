package com.voidvvv.game.base;

import com.voidvvv.game.base.shape.VBaseShape;

public class VPhysicAttr {
    public float weight;

    public float box2dHx;
    public float box2dHy;

    private VBaseShape baseShape;

    public VBaseShape getBaseShape() {
        return baseShape;
    }

    public void setBaseShape(VBaseShape baseShape) {
        this.baseShape = baseShape;
    }
}
