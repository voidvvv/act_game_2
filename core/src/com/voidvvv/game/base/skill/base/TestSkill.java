package com.voidvvv.game.base.skill.base;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.voidvvv.game.base.VSkillCharacter;
import com.voidvvv.game.base.skill.Skill;
import com.voidvvv.game.base.test.TestBullet;
import com.voidvvv.game.context.VActorSpawnHelper;
import com.voidvvv.game.context.WorldContext;

public class TestSkill implements Skill {
    public float triggerTime = 0.7f;

    VSkillCharacter character;
    float progress;
    boolean send = false;

    public float speed = 1f;

    public Vector2 position = new Vector2();
    public Vector2 direction = new Vector2();

    @Override
    public boolean couldBeReplace(Skill skill) {
        return false;
    }

    @Override
    public int type() {
        return 0;
    }

    @Override
    public float percentage() {
        return this.progress;
    }

    @Override
    public int init(VSkillCharacter character) {
        this.character = character;
        this.position.x = character.position.x;
        this.position.y = character.getY();
        this.direction.x = character.getWorld().currentPointerPose.x - character.position.x;
        this.direction.y = character.getWorld().currentPointerPose.y - character.position.y;

        speed = character.getBattleComponent().actualBattleAttr.magicSpeed / WorldContext.DEFAULT_MAGIC_COEFFICIENT;
        return 1;
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
        character.enterStatusForSkill(this);
    }

    @Override
    public void process(float delta) {
        updateProgress(delta);
        character.baseMove.x = 0;
        character.baseMove.y = 0;
//        character.interruptPathFinding();
        if (this.progress >= triggerTime && !send) {
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
                .initX(this.position.x).initY(this.position.y)
                .sensor(true)
                .build();

        TestBullet testBullet = character.getWorld().spawnVActor(TestBullet.class, helper);
        testBullet.targetGroup = WorldContext.BLACK;
        testBullet.getActualBattleAttr().moveSpeed = 500;
        testBullet.setParentVActor(character);
        testBullet.baseMove.set(direction.x,direction.y,0f);

    }

    private void updateProgress(float delta) {
        speed = character.getBattleComponent().actualBattleAttr.magicSpeed / WorldContext.DEFAULT_MAGIC_COEFFICIENT;
        this.progress += (delta * speed);
    }

    @Override
    public void end() {

    }

    @Override
    public void reset() {
        progress = 0f;
        this.character = null;
        send = false;
        position.set(0,0);
        direction.set(1,0);
        speed = 1f;
        end();
    }
}
