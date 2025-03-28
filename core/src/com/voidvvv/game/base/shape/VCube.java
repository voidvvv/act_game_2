package com.voidvvv.game.base.shape;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class VCube implements VBaseShape {

    public float xLength;
    public float yLength;
    public float zLength;

    private final Rectangle frontShape = new Rectangle();
    private final Rectangle topShape = new Rectangle();

    @Override
    public Rectangle frontShape(Vector3 position) {
        return frontShape.set(position.x - xLength / 2.f, position.y + position.z, xLength, zLength);
    }

    @Override
    public Rectangle topShape(Vector3 position) {
        return topShape.set(position.x - xLength / 2.f, position.y - yLength/2, xLength, yLength);
    }
    Vector2 bounds = new Vector2();
    @Override
    public Vector2 getBounds() {
        return bounds.set(xLength,yLength);
    }

    @Override
    public float getHeight() {
        return zLength;
    }
}
