package com.voidvvv.game.context.map;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.PathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
}
