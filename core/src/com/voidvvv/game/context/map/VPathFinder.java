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

                currentIndex = 0;
                finalTarget.set(fx, fy);
                currentTarget.set(graphPath.get(currentIndex).x, graphPath.get(currentIndex).y);
                tmp1.set(graphPath.get(currentIndex).x - character.position.x, graphPath.get(currentIndex).y - character.position.y).nor();
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
            float afterDistance = tmp2.add(vector2.scl(0.1f)).len();
            if (beforeDistance < afterDistance) {
                currentIndex++;
                if (currentIndex < graphPath.getCount()) {
                    if (currentIndex == graphPath.getCount() - 1) {
                        currentTarget.set(finalTarget.x, finalTarget.y);
                        tmp1.set(finalTarget.x - character.position.x, finalTarget.y - character.position.y).nor();
                        character.baseMove.x = tmp1.x;
                        character.baseMove.y = tmp1.y;
                    } else {
                        VMapNode vMapNode = graphPath.get(currentIndex);
                        System.out.println("change node!" + vMapNode.x + " " + vMapNode.y);

                        // next node
                        currentTarget.set(vMapNode.x, vMapNode.y);
                        tmp1.set(vMapNode.x - character.position.x, vMapNode.y - character.position.y).nor();
                        character.baseMove.x = tmp1.x;
                        character.baseMove.y = tmp1.y;
                    }
                } else {
                    // end finding
                    character.baseMove.x = character.baseMove.y = 0f;
                    currentIndex = 0;
                    pathing = false;
                }
            } else {

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
        shapeRenderer.begin();
        shapeRenderer.setColor(Color.YELLOW);
//        shapeRenderer.line(1,1,100,100);
        for (int x= 1; x<graphPath.getCount(); x++) {
            VMapNode pre = graphPath.get(x - 1);
            VMapNode cur = graphPath.get(x);

            if (x != graphPath.getCount() - 1) {
                shapeRenderer.line(pre.x, pre.y, cur.x, cur.y);
            } else {
                shapeRenderer.line(pre.x, pre.y, finalTarget.x, finalTarget.y);
            }
        }
        shapeRenderer.end();
        batch.begin();
    }
}
