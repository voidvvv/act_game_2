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

        float unit = world.unit();
        for (float x = Math.min(x0,x1); x <= Math.max(x0,x1); x += unit) {
            for (float y = Math.min(y0,y1); y <= Math.max(y0,y1); y += unit) {
                VMapNode vMapNode = world.getMap().coordinateToNode(x, y);
                if (vMapNode == null || vMapNode.getType() == VMapNode.OBSTACLE) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean findCollision(Collision<Vector2> outputCollision, Ray<Vector2> inputRay) {
        return false;
    }
}
