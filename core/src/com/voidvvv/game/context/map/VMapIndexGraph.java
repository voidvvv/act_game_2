package com.voidvvv.game.context.map;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.voidvvv.game.context.VWorld;

public class VMapIndexGraph implements IndexedGraph<VMapNode> {
    int nodeCount;
    Rectangle boundingBox;

    public void init (VWorld world) {
        this.boundingBox = world.getBoundingBox();
        float unit = world.unit();
        float width = boundingBox.getWidth();
        float height = boundingBox.getHeight();
        int i = 0;
        for (int row = 0; row < (height/unit); row++) {
            for (int col = 0; col < (width/unit); col++) {

            }
        }

    }

    @Override
    public int getIndex(VMapNode node) {
        return node.getIndex();
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public Array<Connection<VMapNode>> getConnections(VMapNode fromNode) {
        return fromNode.getConnections();
    }
}
