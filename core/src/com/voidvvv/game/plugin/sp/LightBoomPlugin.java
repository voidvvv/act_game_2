package com.voidvvv.game.plugin.sp;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.VActorAdaptor;
import com.voidvvv.game.base.Camp;
import com.voidvvv.game.base.VActorListener;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.actors.ActorConstants;
import com.voidvvv.game.base.test.TestBullet;
import com.voidvvv.game.context.VActorSpawnHelper;
import com.voidvvv.game.context.WorldContext;
import com.voidvvv.game.plugin.SkillPlugin;

public class LightBoomPlugin extends SkillPlugin {
    public float triggerTime = 0.7f;

    VCharacter character;
    boolean send = false;

    public float speed = 1f;

    public Vector2 position = new Vector2();
    public Vector2 direction = new Vector2();

    InterruptListener listener;

    @Override
    public int version() {
        return 0;
    }

    @Override
    public void start() {
        this.character = skill.getOwner();
        this.position.x = character.position.x;
        this.position.y = character.getY();
        this.direction.x = character.getWorld().currentPointerPose.x - character.position.x;
        this.direction.y = character.getWorld().currentPointerPose.y - character.position.y;

        speed = character.getBattleComponent().actualBattleAttr.magicSpeed / WorldContext.DEFAULT_MAGIC_COEFFICIENT;
        character.changeStatus(ActorConstants.STATUS_SPELLING_01);
        listener = Pools.obtain(InterruptListener.class);
        listener.plugin = this;
        character.getListenerComponent().add(listener);

    }

    @Override
    public void update(float delta) {
        updateProgress(delta);
        character.baseMove.x = 0;
        character.baseMove.y = 0;
//        character.interruptPathFinding();
        if (character.statusProgress >= triggerTime && !send) {
            launch();
        }
        if (isEnding()) {
            character.changeStatus(ActorConstants.STATUS_IDLE);
            this.stop();
        }
    }

    private boolean isEnding() {
        return character.statusProgress >= 1f && send;
    }

    private void launch() {
        send = true;
        if (character == null) {
            return;
        }
        VActorSpawnHelper helper = VActorSpawnHelper.builder()
                .bodyType(BodyDef.BodyType.DynamicBody)
                .category((short) (WorldContext.ROLE | WorldContext.WHITE)) // who am I
                .mask((short) (WorldContext.OBSTACLE | WorldContext.BLACK | WorldContext.ROLE)) // who do I want to collision
                .hx(5).hy(1f)
                .hz(5f)
                .initX(this.position.x).initY(this.position.y)
                .sensor(true)
                .build();

        TestBullet testBullet = character.getWorld().spawnVActor(TestBullet.class, helper);
        testBullet.targetGroup = WorldContext.BLACK;
        testBullet.getActualBattleAttr().moveSpeed = 500;
        testBullet.setParentVActor(character);
        testBullet.baseMove.set(direction.x, direction.y, 0f);
        testBullet.taregtCamp.set(character.taregtCamp);
        testBullet.camp.set(character.camp);

    }

    private void updateProgress(float delta) {
        speed = character.getBattleComponent().actualBattleAttr.magicSpeed / WorldContext.DEFAULT_MAGIC_COEFFICIENT;
        character.statusProgress += (delta * speed);
    }

    @Override
    public void reset() {
        super.reset();
        send = false;
        if (this.listener != null) {
            character.getListenerComponent().remove(listener);
            this.listener = null;
        }
    }

    @Override
    public void stop() {
        character.getPluginComponent().removePlugin(this);
    }

    public static class InterruptListener extends VActorAdaptor {
        public LightBoomPlugin plugin;
        @Override
        public void preUserSkill() {
            plugin.stop();
        }
    }
}
