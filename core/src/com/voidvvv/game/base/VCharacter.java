package com.voidvvv.game.base;

import com.badlogic.gdx.ai.steer.behaviors.Jump;
import com.badlogic.gdx.math.Vector3;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.context.WorldContext;

import java.util.List;

public class VCharacter extends VActor{

    protected Vector3 baseMove = new Vector3();

    Vector3[] velAffect = new Vector3[20];
    int velAffectCap = 0;

    private VJump vJump = new VJump();

    public VCharacter() {
        for (int x= 0; x< velAffect.length;x++) {
            velAffect[x] = new Vector3();
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        vJump.update(delta);
        this.velocity.set(vJump.vel).add(baseMove);
        for (int x=0; x<velAffectCap; x++) {
            this.velocity.add(velAffect[x]);
        }
        this.position.z += this.velocity.z * delta;
        if (this.position.z <= 0.f) {
            this.position.z = 0.f;
            vJump.reset();
        }
        this.getBody().setLinearVelocity(this.velocity.x,this.velocity.y);

    }

    @Override
    protected void updateFrameIndex() {
        if (this.currentFrame != ActGame.gameInstance().getFrameId()) {
            this.currentFrame = ActGame.gameInstance().getFrameId();
            this.velAffectCap = 0;
        }
    }

    public void jump () {
        if (isFalling()) {
            return;
        }
        vJump.jump(10f);
    }

    private boolean isFalling() {
        return this.position.z > 0.f;
    }

    public void applyExternalVel (Vector3 vel) {
        updateFrameIndex();
        if (this.velAffectCap >= this.velAffect.length) {
            return;
        }
        this.velAffect[velAffectCap++].set(vel);
    }
}
