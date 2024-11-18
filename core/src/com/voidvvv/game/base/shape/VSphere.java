package com.voidvvv.game.base.shape;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class VSphere implements VBaseShape {
    public float radius;
    private final Circle frontCircle = new Circle();
    private final Circle topCircle = new Circle();

    @Override
    public Circle frontShape(Vector3 position) {
        frontCircle.set(position.x, position.y + radius + position.z, radius);
        return frontCircle;
    }

    @Override
    public Circle topShape(Vector3 position) {
        topCircle.set(position.x, position.y + radius, radius);
        return topCircle;
    }

    Vector2 bounds = new Vector2();

    @Override
    public Vector2 getBounds() {
        return bounds.set(radius * 2, radius * 2);
    }
}
