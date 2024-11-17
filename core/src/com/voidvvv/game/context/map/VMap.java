package com.voidvvv.game.context.map;

import com.badlogic.gdx.ai.pfa.*;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.voidvvv.game.context.VWorld;

public class VMap {
    // finder
    PathFinder<VMapNode> pathFinder;

    VMapIndexGraph mapGraph;

    VWorld world;

    public VMap() {

    }

    public void init (VWorld world) {
        mapGraph = new VMapIndexGraph();
        mapGraph.init(world);
        this.world = world;
        pathFinder = new IndexedAStarPathFinder<>(mapGraph, true);

    }

    public VMapIndexGraph getMapGraph() {
        return mapGraph;
    }

    public VMapNode coordinateToNode(float x, float y) {
        float unit = world.unit();
        Rectangle boundingBox = world.getBoundingBox();
        int row = (int)((y - boundingBox.y) / unit);
        int col = (int)((x - boundingBox.x) / unit);
        return mapGraph.getNode(row, col);
    }

    public void nodeToCoordinate(VMapNode node, Vector2 v2) {
        v2.set(node.x, node.y);
    }



    public boolean findPath (VMapNode start, VMapNode end, Heuristic<VMapNode> heuristic, GraphPath<VMapNode> graphPath) {
        return pathFinder.searchNodePath(start,end,heuristic, graphPath);
    }

    public boolean addObstacle (float x, float y, float xLength, float yLength) {
        VMapNode leftDown = coordinateToNode(x, y);
        VMapNode rightUp = coordinateToNode(x + xLength, y + yLength);
        if (leftDown == null || rightUp == null) {
            return false;
        }
        for (int i = leftDown.row; i <= rightUp.row; i++) {
            for (int j = leftDown.col; j <= rightUp.col; j++) {
                VMapNode node = mapGraph.getNode(i, j);
                node.convertToObstacle();
                if (i == leftDown.row || j == leftDown.col || j == rightUp.col || i == rightUp.row) {
                    Array<Connection<VMapNode>> comeToThis = node.getComeToThis();
                    // break all connection come to this
                    for (Connection<VMapNode> connection : comeToThis) {
                        VMapNode fromNode = connection.getFromNode();
                        fromNode.getConnections().removeValue(connection, true);
                    }
                    comeToThis.clear();

                }
            }
        }
        return true;
    }
}
