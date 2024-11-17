package com.voidvvv.game.context.map;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.utils.Array;

import java.security.PublicKey;

public class VMapNode {
    public static final int PASS = 0;
    public static final int OBSTACLE = 1;

    private int type = PASS;
    private int index;

    public float x;
    public float y;

    public int row;
    public int col;


    private final Array<Connection<VMapNode>> connections = new Array<Connection<VMapNode>>();

    private final Array<Connection<VMapNode>> comeToThis = new Array<Connection<VMapNode>>();

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void convertToObstacle() {
        setType(OBSTACLE);
    }

    public Array<Connection<VMapNode>> getConnections(){
        return connections;
    }

    public Array<Connection<VMapNode>> getComeToThis(){
        return comeToThis;
    }

    public void addNodeComeThis(Connection<VMapNode> connection) {
        comeToThis.add(connection);
    }
}
