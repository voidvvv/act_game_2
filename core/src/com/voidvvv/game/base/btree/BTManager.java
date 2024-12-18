package com.voidvvv.game.base.btree;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibrary;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.actors.ActorConstants;
import com.voidvvv.game.base.actors.slime.Slime;
import com.voidvvv.game.base.btree.slime.Jog;
import com.voidvvv.game.utils.ReflectUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BTManager implements Telegraph {
    public static final String SLIME_SIMPLE = "slime_normal";

    BehaviorTreeLibraryManager libraryManager = BehaviorTreeLibraryManager.getInstance();

    Set<BehaviorTree<? extends VActor>> behaviorTrees = new HashSet<>();

    Map<VActor,BehaviorTree> map = new HashMap<>();

    public void init () {
        BehaviorTreeLibrary library = new BehaviorTreeLibrary(BehaviorTreeParser.DEBUG_HIGH);
        library.registerArchetypeTree(BTManager.SLIME_SIMPLE, new BehaviorTree<Slime>(new Jog()));
        libraryManager.setLibrary(library);
    }

    public void addTree(VActor actor, String treeName) {
        BehaviorTree<VActor> behaviorTree = libraryManager.createBehaviorTree(treeName, actor);
        behaviorTrees.add(behaviorTree);
        map.put(actor, behaviorTree);
    }

    public float stepInterval = 0.5f;
    public float interval = 0f;

    public void update(float delta) {
        interval += delta;
        if (interval >= stepInterval) {
            for (BehaviorTree tree: behaviorTrees) {
                tree.step();
            }
            interval -= stepInterval;
        }
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        System.out.println("BTManager handleMessage");
        if (msg.message == ActorConstants.MSG_RESET_ACTOR) {
            VActor actor = ReflectUtil.cast(msg.extraInfo, VActor.class);
            if (actor != null) {
                BehaviorTree behaviorTree = map.get(actor);
                if (behaviorTree != null) {
                    map.remove(actor);
                    behaviorTrees.remove(behaviorTree);
                }
                return true;
            }
        }
        return false;
    }
}