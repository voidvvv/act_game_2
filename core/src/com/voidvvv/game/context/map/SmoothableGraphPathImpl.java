package com.voidvvv.game.context.map;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.SmoothableGraphPath;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;

public class SmoothableGraphPathImpl extends DefaultGraphPath<VMapNode> implements SmoothableGraphPath<VMapNode, Vector2> {

    Vector2 tmp = new Vector2();
    @Override
    public Vector2 getNodePosition(int index) {
        tmp.set(nodes.get(index).x, nodes.get(index).y);
        return tmp;
    }

    @Override
    public void swapNodes(int index1, int index2) {
        nodes.set(index1, nodes.get(index2));

    }

    @Override
    public void truncatePath(int newLength) {
        nodes.truncate(newLength);

    }
}
