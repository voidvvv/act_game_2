package com.voidvvv.game.plugin.sp.hit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.VActorAdaptor;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.actors.ActorConstants;
import com.voidvvv.game.base.b2d.UserData;
import com.voidvvv.game.base.skill.v2.Skill;
import com.voidvvv.game.manager.event.attack.BasePhysicAttackEvent;
import com.voidvvv.game.utils.ReflectUtil;

import java.util.HashSet;
import java.util.Set;

public class HitSkillListener extends VActorAdaptor {

    public VCharacter owner;

    public Skill hitSkill;

    public HitSkillPlugin hsp;

    private Set<VActor> set = new HashSet<>();

    @Override
    public void afterHitOnActor() {
        Fixture lastThisFixture = this.owner.lastThisFixture;
        Fixture lastOtherFixture = this.owner.lastOtherFixture;
        VActor lastHitActor = this.owner.lastHitActor;
        if (lastThisFixture == null || lastOtherFixture == null) {
            return;
        }
        Gdx.app.log("HitSkillListener", this.owner.getName() + " use hit tro to detect: " + lastHitActor.getName());
        UserData userData = ReflectUtil.cast(lastThisFixture.getUserData(), UserData.class);
        if (userData == null || userData.isDerivative()) {
            // this fixture should be skipped
            return;
        }
        boolean validTarget = checkTarget(lastHitActor, lastOtherFixture);
        if (!validTarget) {
            return;
        }
        if (!set.add(lastHitActor)) {
            return;
        }

        // spawn damage event to target
        BasePhysicAttackEvent damageEvent = ActGame.gameInstance().getvWorldEventManager().newEvent(BasePhysicAttackEvent.class);
        damageEvent.setExtraInfo(hitSkill);
        damageEvent.setFromActor(owner);
        damageEvent.setTargetActor(lastHitActor);
        damageEvent.setTriggerObj(hitSkill);
    }

    private boolean checkTarget(VActor lastHitActor, Fixture lastOtherFixture) {
        if (lastHitActor.actorType != ActorConstants.ACTOR_TYPE_CHARACTER) {
            return false;
        }
        UserData userData = ReflectUtil.cast(lastOtherFixture.getUserData(), UserData.class);
        if (userData == null || userData.isDerivative()) {
            return false;
        }

        return lastHitActor.taregtCamp.compatible(owner.camp);
    }

    @Override
    public void reset() {
        super.reset();
        set.clear();
        this.hsp = null;
        this.owner = null;
    }

    @Override
    public void preUserSkill() {
        if (owner.lastPreSkill != this.hitSkill) {
            System.out.println(owner.lastPreSkill.getClass());
//            this.hsp.stop();
        }
    }

    @Override
    public void afterUseSkill() {
        super.afterUseSkill();
    }
}
