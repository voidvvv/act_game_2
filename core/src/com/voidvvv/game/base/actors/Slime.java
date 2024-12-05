package com.voidvvv.game.base.actors;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibrary;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.btree.slime.Idle;
import com.voidvvv.game.base.shape.VCube;
import com.voidvvv.game.base.state.slime.SlimeStatus;
import com.voidvvv.game.context.input.InputActionMapping;

public class Slime extends VCharacter {

    StateMachine<Slime, State<Slime>> defalutStateMachine;

    BehaviorTree<Slime> behaviorTree;

    @Override
    public void init() {
        super.init();
        VCube cube = new VCube();
        cube.xLength = this.physicAttr.box2dHx * 2f;
        cube.yLength = this.physicAttr.box2dHy * 2f;
        cube.zLength = getWorld().unit() *2;
        physicAttr.setBaseShape(cube);

        if (defalutStateMachine == null) {
            defalutStateMachine = new DefaultStateMachine<>(this);
        }

        if (behaviorTree == null) {
            BehaviorTreeLibraryManager libraryManager = BehaviorTreeLibraryManager.getInstance();
            BehaviorTreeLibrary library = new BehaviorTreeLibrary(BehaviorTreeParser.DEBUG_HIGH);
            library.registerArchetypeTree("slime_normal", new BehaviorTree<Slime>(new Idle()));
            libraryManager.setLibrary(library);
            behaviorTree = libraryManager.createBehaviorTree("slime_normal", this);
        }
    }

    public StateMachine<Slime, State<Slime>> getDefalutStateMachine() {
        return defalutStateMachine;
    }

    @Override
    public void vCAct(float delta) {
        super.vCAct(delta);
        behaviorTree.step();
        defalutStateMachine.changeState(SlimeStatus.IDEL);
        stateUpdate(delta);
    }

    private void stateUpdate(float delta) {
        defalutStateMachine.update();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void useSkill(int skillCode) {

    }

    @Override
    public void reset() {
        super.reset();
    }



    @Override
    protected void becomeDying() {
    }

    @Override
    public boolean isDying() {
        return false;
    }

}
