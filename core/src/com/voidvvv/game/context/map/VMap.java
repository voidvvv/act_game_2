package com.voidvvv.game.context.map;

import com.badlogic.gdx.ai.pfa.Graph;
import com.badlogic.gdx.ai.pfa.PathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.context.VWorld;

public class VMap {
    // finder
    PathFinder<VMapNode> pathFinder;

    VMapIndexGraph map;

    public VMap() {

    }

    public void init (VWorld world) {
        map = new VMapIndexGraph();
        map.init(world);
        pathFinder = new IndexedAStarPathFinder<>(map, true);

    }
}
