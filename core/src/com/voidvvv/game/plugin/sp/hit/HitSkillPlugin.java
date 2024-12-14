package com.voidvvv.game.plugin.sp.hit;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.VPhysicAttr;
import com.voidvvv.game.base.actors.ActorConstants;
import com.voidvvv.game.base.skill.v2.HitSkill;
import com.voidvvv.game.context.FixtureHelper;
import com.voidvvv.game.context.WorldContext;
import com.voidvvv.game.plugin.SkillPlugin;

public class HitSkillPlugin extends SkillPlugin {
    public final static int INIT = 0;
    public final static int GENERATE_HIT = 1;

    float speed = 1f;
    float totalProgress = 1f;

    Fixture generatedFix = null;

    int hitStatus = INIT;
    HitSkillListener listener;
    @Override
    public int version() {
        return 0;
    }

    @Override
    public void start() {
        // add a listener to owner
        VCharacter owner = skill.getOwner();
        owner.changeStatus(ActorConstants.STATUS_SPELLING_02);
        listener = Pools.obtain(HitSkillListener.class);
        listener.hitSkill = this.skill;
        listener.owner = owner;
        owner.getListenerComponent().add(listener);
    }

    @Override
    public void update(float delta) {
        VCharacter owner = skill.getOwner();
        updateProgress(delta);

        float statusProgress = owner.statusProgress;

        if (statusProgress >= 0.5f && hitStatus == INIT) {
            hitStatus = GENERATE_HIT;
            // add an extra fixture to actor's body
            VPhysicAttr physicAttr = owner.getPhysicAttr();
            float box2dHy = physicAttr.box2dHy;
            float box2dHx = physicAttr.box2dHx;
            float box2dHz = physicAttr.box2dHz;
            FixtureHelper helper = new FixtureHelper();
            helper.x1 = -box2dHx * 1.5f;
            helper.x2 = box2dHx * 1.5f;
            helper.y1 = -box2dHy;
            helper.y2 = (-box2dHy + 2f*box2dHz) * 1.5f;
            generatedFix = owner.getWorld().addRectFixture(owner, helper);
        }

        if (statusProgress >= 1f && hitStatus == GENERATE_HIT) {
            owner.changeStatus(ActorConstants.STATUS_IDLE);
            stop();
        }
    }

    private void updateProgress(float delta) {
        VCharacter owner = skill.getOwner();

        speed = owner.getBattleComponent().actualBattleAttr.attackSpeed / WorldContext.DEFAULT_ATTACK_SPEED_COEFFICIENT;
        speed /= totalProgress;
        owner.statusProgress += speed * delta;
    }

    @Override
    public void reset() {
        hitStatus = INIT;
        VCharacter owner = skill.getOwner();
        owner.getBody().destroyFixture(generatedFix);
        owner.getListenerComponent().remove(listener);
        super.reset();
    }

    @Override
    public void stop() {
        // will call reset
        VCharacter owner = skill.getOwner();
        owner.getPluginComponent().removePlugin(this);
        owner.changeStatus(ActorConstants.STATUS_IDLE);

    }
}
