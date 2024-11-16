package com.voidvvv.game.base.debug;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VPhysicAttr;
import com.voidvvv.game.base.shape.VBaseShape;
import com.voidvvv.game.base.shape.VCube;
import com.voidvvv.game.base.shape.VSphere;
import com.voidvvv.game.context.VWorld;
import com.voidvvv.game.utils.ReflectUtil;

public class VDebugShapeRender {

    ShapeRenderer shapeRenderer;

    Box2DDebugRenderer box2DDebugRenderer;

    Matrix4 matrix4;

    public VDebugShapeRender() {
        this.shapeRenderer = ActGame.gameInstance().getDrawManager().getShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        box2DDebugRenderer = ActGame.gameInstance().getDrawManager().getBox2DDebugRenderer();
    }

    public void begin(Matrix4 matrix4) {
        shapeRenderer.setProjectionMatrix(matrix4);
        shapeRenderer.begin();
        this.matrix4 = matrix4;

    }

    public void end() {
        shapeRenderer.end();
    }

    public void render(VWorld vWorld) {
        for (VActor act : vWorld.allActors()) {
            if (act.isVisible())
                render(act);
        }
        // render bound
        Rectangle boundingBox = vWorld.getBoundingBox();
        shapeRenderer.rect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
        vWorld.getStage().getPinpoint().draw(null, 1f);
//        box2DDebugRenderer.render(vWorld.getBox2dWorld(),matrix4);
    }

    public void render(VActor act) {
        VPhysicAttr physicAttr = act.getPhysicAttr();
        if (physicAttr != null) {
            render(act, physicAttr.getBaseShape());
        }
    }

    public void render(VActor act, VBaseShape baseShape) {
        if (baseShape == null) {
            return;
        }
        VCube cube = ReflectUtil.cast(baseShape, VCube.class);
        if (cube != null) {
            Rectangle front = cube.frontShape(act.position);
            Rectangle top = cube.topShape(act.position);
            shapeRenderer.setColor(Color.YELLOW);
            shapeRenderer.rect(top.x, top.y, top.width, top.height);

            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.rect(front.x, front.y, front.width, front.height);
            return;
        }
        VSphere sphere = ReflectUtil.cast(baseShape, VSphere.class);
        if (sphere != null) {
            Circle front = sphere.frontShape(act.position);
            Circle top = sphere.topShape(act.position);
            shapeRenderer.setColor(Color.YELLOW);
            shapeRenderer.circle(top.x, top.y, top.radius);

            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.circle(front.x, front.y, front.radius);
            return;
        }
    }
}
