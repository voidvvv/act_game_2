package com.voidvvv.game.context.map;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.voidvvv.game.context.VWorld;

import java.util.List;

public class VMapIndexGraph implements IndexedGraph<VMapNode> {
    int nodeCount;
    Rectangle boundingBox;
    VMapNode[][] nodeMap;
    public void init (VWorld world) {
        this.boundingBox = world.getBoundingBox();
        float unit = world.unit();
        float width = boundingBox.getWidth();
        float height = boundingBox.getHeight();
        int i = 0;
        int rowNum = (int)(height/unit);
        int colNum = (int)(width/unit);

        nodeMap = new VMapNode[rowNum][colNum];
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                VMapNode node = new VMapNode(i++);
                node.x = boundingBox.x + col * unit + unit/2;
                node.y = boundingBox.y + row * unit + unit/2;
                node.row = row;
                node.col = col;
                nodeMap[row][col] = node;
                nodeCount++;
            }
        }

        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                addConnection(row,col);
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

    static final int[][] dirs = {
            {-1, 0},
            {-1, -1},
            {-1, 1},

            {0, -1},
            {0, 1},


            {1, 0},
            {1, -1},
            {1,1},

    };

    public void addConnection (int row, int col) {
        VMapNode vMapNode = nodeMap[row][col];
        for (int[] dir : dirs) {
            if (row+dir[0]>=0 && row+dir[0]<nodeMap.length && col+dir[1]>=0 && col+dir[1]<nodeMap[0].length) {
                VMapNode to = nodeMap[row + dir[0]][col + dir[1]];
                DefaultConnection<VMapNode> conn = new DefaultConnection<>(vMapNode, to);
                vMapNode.getConnections().add(conn);
                to.addNodeComeThis(conn);
            }
        }
    }

    public VMapNode getNode(int row, int col) {
        if (row < 0 || row >= nodeMap.length || col < 0 || col >= nodeMap[0].length) {
            return null;
        }
        return nodeMap[row][col];
    }

    public VMapNode[][] mapData () {
        return nodeMap;
    }

}
