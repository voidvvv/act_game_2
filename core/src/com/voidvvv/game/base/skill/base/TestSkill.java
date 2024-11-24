package com.voidvvv.game.base.skill.base;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.voidvvv.game.base.VSkillCharacter;
import com.voidvvv.game.base.skill.Skill;
import com.voidvvv.game.base.test.TestBullet;
import com.voidvvv.game.context.VActorSpawnHelper;
import com.voidvvv.game.context.WorldContext;

public class TestSkill implements Skill {
    public float maxTime = 0.3f;

    VSkillCharacter character;
    float progress;
    boolean send = false;
    @Override
    public boolean couldBeReplace(Skill skill) {
        return false;
    }

    @Override
    public int type() {
        return 0;
    }

    @Override
    public VSkillCharacter owner() {
        return character;
    }

    @Override
    public void setOwner(VSkillCharacter owner) {
        this.character = owner;
    }

    @Override
    public void start() {
        // start
    }

    @Override
    public void process(float delta) {
        updateProgress(delta);
        character.baseMove.x = 0;
        character.baseMove.y = 0;
        character.interruptPathFinding();
        if (this.progress >= maxTime && !send) {
            launch ();
        }
        if (isEnding()) {
            character.tryToBackToNormal();
        }
    }

    @Override
    public boolean isEnding() {
        return this.progress >= 1f && send;
    }

    public void launch () {
        send = true;
        if (character == null) {
            return;
        }
        VActorSpawnHelper helper = VActorSpawnHelper.builder()
                .bodyType(BodyDef.BodyType.DynamicBody)
                .category((short) (WorldContext.ROLE | WorldContext.WHITE)) // who am I
                .mask((short) (WorldContext.OBSTACLE | WorldContext.BLACK | WorldContext.ROLE)) // who do I want to collision
                .hx(5).hy(5)
                .initX(character.position.x).initY(character.getY())
                .build();

        TestBullet testBullet = character.getWorld().spawnVActor(TestBullet.class, helper);
        testBullet.targetGroup = WorldContext.BLACK;
        testBullet.getActualBattleAttr().moveSpeed = 10000 * 1.5f;
        testBullet.setParentVActor(character);
        testBullet.baseMove.set(character.getWorld().currentPointerPose.x - character.position.x, character.getWorld().currentPointerPose.y - character.getY(), 0);

    }

    private void updateProgress(float delta) {
        this.progress += delta;
    }

    @Override
    public void end() {

    }

    @Override
    public void reset() {
        progress = 0f;
        this.character = null;
        send = false;
        end();
    }
}
