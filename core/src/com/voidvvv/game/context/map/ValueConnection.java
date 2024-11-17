package com.voidvvv.game.context.map;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;

public class ValueConnection <T> extends DefaultConnection<T> {
    private float value = 1f;

    public ValueConnection(T fromNode, T toNode) {
        super(fromNode, toNode);
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public float getCost() {
        return value;
    }
}
