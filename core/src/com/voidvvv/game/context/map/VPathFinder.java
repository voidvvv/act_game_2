package com.voidvvv.game.context.map;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.PathSmoother;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.context.VWorld;

import java.util.ArrayList;
import java.util.List;

/**
 * todo replace the graphPath in update and draw to use another type structure to find path, so we can free from graphPath
 */
public class VPathFinder {
    private VCharacter character;

    private Vector2 currentTarget = new Vector2();
    private Vector2 finalTarget = new Vector2();

    private Vector2 tmp1 = new Vector2();
    private Vector2 tmp2 = new Vector2();
    int pathId = 0;
    boolean pathing = false;

    Heuristic<VMapNode> heuristic;
    SmoothableGraphPathImpl graphPath;
    PathSmoother<VMapNode, Vector2> pathSmoother;

    List<Float> currentPath = new ArrayList<Float>();
    int currentIndex = 0;
    VMap contextMap;

    public VPathFinder(VCharacter character, VWorld world) {
        this.character = character;
        contextMap = world.getMap();
        heuristic = new Heuristic<VMapNode>() {
            @Override
            public float estimate(VMapNode node, VMapNode endNode) {
                return Math.abs(endNode.x - node.x) + Math.abs(endNode.y - node.y);
            }
        };
        graphPath = new SmoothableGraphPathImpl();
        pathSmoother = new PathSmoother<>(new RaycastCollisionDetectorImpl(world));
    }

    public boolean findPath(float fx, float fy) {
        VMapNode dest = contextMap.coordinateToNode(fx, fy);
        VMapNode cur = contextMap.coordinateToNode(character.position.x, character.position.y);
        if (cur == null) {
            return false;
        }
        if (dest == null || dest.getType() == VMapNode.OBSTACLE) {
            // find by ray
            return false;
        }
        if (dest == cur) {
            currentTarget.set(fx, fy);
            finalTarget.set(fx, fy);
            tmp1.set(fx - character.position.x, fy - character.position.y).nor();
        } else {
            graphPath.clear();
            boolean find = contextMap.findPath(cur, dest, heuristic, graphPath);
            if (!find) {
                return false;
            } else {
                pathSmoother.smoothPath(graphPath);
                currentPath.clear();
                currentIndex = 0;
                finalTarget.set(fx, fy);
                // put path to currentPath
                currentPath.add(character.position.x);
                currentPath.add(character.position.y);
                currentTarget.set(character.position.x, character.position.y);

                for (int j = 1; j < graphPath.getCount() -1; j++) {
                    currentPath.add(graphPath.get(j).x);
                    currentPath.add(graphPath.get(j).y);
                }
                currentPath.add(fx);
                currentPath.add(fy);
                tmp1.set(currentPath.get(0) - character.position.x, currentPath.get(1) - character.position.y).nor();

            }
        }
        pathId++;
        pathing = true;
        return true;
    }

    public void interrupt() {
        this.pathing = false;
    }


    public void update(float delta) {
        if (pathing) {
            Vector2 vector2 = character.testVelocity(delta, tmp1);
            float beforeDistance = tmp2.set(character.position.x, character.position.y).sub(currentTarget).len();
            float afterDistance = tmp2.add(vector2.scl(0.01f)).len();
            if (beforeDistance <= afterDistance) {
                ActGame.gameInstance().getSystemNotifyMessageManager().pushMessage("beforeDistance: [RED]" + beforeDistance + "[] afterDistance: [RED]" + afterDistance);
                currentIndex++;
                if (2*currentIndex < currentPath.size()-1) {
                    // move to next point
                    currentTarget.set(currentPath.get(2*currentIndex), currentPath.get(2*currentIndex+1));
                    ActGame.gameInstance().getSystemNotifyMessageManager().pushMessage("切换为下一寻路点: " + currentTarget.x + " - " + currentTarget.y);
                    tmp1.set(currentTarget.x - character.position.x, currentTarget.y - character.position.y).nor();
//                    character.baseMove.x = tmp1.x;
//                    character.baseMove.y = tmp1.y;
                    character.setHorizonVelocity(tmp1.x, tmp1.y);
                } else {
                    // end finding
                    ActGame.gameInstance().getSystemNotifyMessageManager().pushMessage("停止寻路");

                    character.setHorizonVelocity(0f,0f);
                    currentIndex = 0;
                    pathing = false;
                }

            } else {
//                character.baseMove.x = tmp1.x;
//                character.baseMove.y = tmp1.y;
                character.setHorizonVelocity(tmp1.x, tmp1.y);

            }


        } else {

        }
    }

    public void draw(Batch batch, float parentAlpha) {
        if (!pathing) {
            return;
        }
        batch.end();
        ShapeRenderer shapeRenderer = ActGame.gameInstance().getDrawManager().getShapeRenderer();
        shapeRenderer.setProjectionMatrix(ActGame.gameInstance().getCameraManager().getMainCamera().combined);
        shapeRenderer.begin();
        shapeRenderer.setColor(Color.YELLOW);
//        shapeRenderer.line(1,1,100,100);
        for (int i = 1; 2*i < currentPath.size()-1; i++) {
            int pre =  i -1;
            Float cx = currentPath.get(2 * i);
            Float cy = currentPath.get(2 * i + 1);
            Float px = currentPath.get(pre * 2);
            Float py = currentPath.get(pre * 2 + 1);
            shapeRenderer.line(px,py,cx,cy);

        }
        shapeRenderer.end();
        batch.begin();
    }
}
