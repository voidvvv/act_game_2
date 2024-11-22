package com.voidvvv.game.context.map;

import com.badlogic.gdx.ai.utils.Collision;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.math.Vector2;
import com.voidvvv.game.context.VWorld;

public class RaycastCollisionDetectorImpl implements RaycastCollisionDetector<Vector2> {

    VWorld world;

    public RaycastCollisionDetectorImpl(VWorld world) {
        this.world = world;
    }

    @Override
    public boolean collides(Ray<Vector2> ray) {
        float x0 = ray.start.x;
        float y0 = (float)ray.start.y;
        float x1 = (float)ray.end.x;
        float y1 = (float)ray.end.y;

        float steep = Math.abs((y1-y0) / (x1-x0));
        float unit = world.unit()/2;

        float xStep = x0 < x1? unit:-unit;
        float yStep = y0 < y1? steep*unit:-steep*unit;

        if (xStep ==0 && yStep ==0) {
            return false;
        }

        for (float x = x0, y = y0; // init
             ((xStep >= 0 && x <= x1) || (xStep <= 0 && x >= x1))
                     && ((yStep >=0 && y <= y1) || (yStep <= 0 && y >= y1)); // condition
             x+=xStep, y+=yStep) { // step
                VMapNode vMapNode = world.getMap().coordinateToNode(x, y);
                if (vMapNode == null || vMapNode.getType() == VMapNode.OBSTACLE) {
                    return true;
            }
        }

        return false;
    }

    @Override
    public boolean findCollision(Collision<Vector2> outputCollision, Ray<Vector2> inputRay) {
        return false;
    }
}
