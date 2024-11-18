package com.voidvvv.game.base.shape;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public interface VBaseShape {
    public Shape2D frontShape(Vector3 position);

    public Shape2D topShape(Vector3 position);

    Vector2 getBounds();
}
