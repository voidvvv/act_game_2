package com.voidvvv.game.base.skill.base;

import com.voidvvv.game.VActorAdaptor;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VActorListener;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.VSkillCharacter;
import com.voidvvv.game.base.skill.Skill;
import com.voidvvv.game.utils.ReflectUtil;

import java.util.HashSet;
import java.util.Set;


public class Hit implements Skill {

    VSkillCharacter owner;
    HitListener listener = new HitListener();
    @Override
    public int type() {
        return 0;
    }

    @Override
    public float percentage() {
        return 0;
    }

    @Override
    public int init(VSkillCharacter character) {
        return 0;
    }

    @Override
    public VSkillCharacter owner() {
        return owner;
    }

    @Override
    public void setOwner(VSkillCharacter owner) {
        this.owner = owner;
    }

    @Override
    public boolean couldBeReplace(Skill skill) {
        return false;
    }

    @Override
    public void start() {

    }

    @Override
    public void process(float delta) {

    }

    @Override
    public void end() {

    }

    @Override
    public boolean isEnding() {
        return false;
    }

    @Override
    public void reset() {
        listener.reset();
    }

    class HitListener extends VActorAdaptor {
        Set<VActor> actorSet = new HashSet<>();

        @Override
        public void afterHitOnActor() {
            VActor lastHitActor = owner.lastHitActor;
            VCharacter character = ReflectUtil.cast(lastHitActor, VCharacter.class);
            if (character == null) {
                return;
            }
            boolean add = actorSet.add(character);
            if (add) {
                // generate an event try to damage the character
            }
        }

        @Override
        public void reset() {
            actorSet.clear();
        }
    }
}
