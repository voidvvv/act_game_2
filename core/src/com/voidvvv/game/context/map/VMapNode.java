package com.voidvvv.game.context.map;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.utils.Array;

public class VMapNode {
    private int index;

    public float x;
    public float y;



    private final Array<Connection<VMapNode>> connections = new Array<Connection<VMapNode>>();

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public VMapNode(int index) {
        this.index = index;
    }

    public void addConnection(VMapNode toNode) {
        if (toNode != null) {
            connections.add(new DefaultConnection<>(this, toNode));
        }
    }

    public Array<Connection<VMapNode>> getConnections(){
        return connections;
    }
}
