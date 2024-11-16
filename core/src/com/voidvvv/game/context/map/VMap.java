package com.voidvvv.game.context.map;

import com.badlogic.gdx.ai.pfa.PathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.Vector2;
import com.voidvvv.game.context.VWorld;

public class VMap {
    // finder
    PathFinder<VMapNode> pathFinder;

    VMapIndexGraph mapGraph;

    public VMap() {

    }

    public void init (VWorld world) {
        mapGraph = new VMapIndexGraph();
        mapGraph.init(world);
        pathFinder = new IndexedAStarPathFinder<>(mapGraph, true);

    }


    public VMapNode coordinateToNode(float x, float y) {
        return null;
    }

    public void nodeToCoordinate(VMapNode node, Vector2 v2) {

    }
}
