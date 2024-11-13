package com.voidvvv.game.base;

import com.voidvvv.game.base.shape.VBaseShape;

public class VPhysicAttr {
    public float weight;

    private VBaseShape baseShape;

    public VBaseShape getBaseShape() {
        return baseShape;
    }

    public void setBaseShape(VBaseShape baseShape) {
        this.baseShape = baseShape;
    }
}
