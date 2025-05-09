package com.voidvvv.game.plugin.sp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.base.VActorAdaptor;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.b2d.UserData;
import com.voidvvv.game.base.test.TestBullet;
import com.voidvvv.game.context.world.VActorSpawnHelper;
import com.voidvvv.game.context.world.WorldContext;
import com.voidvvv.game.plugin.SkillPlugin;

public class LightBoomPlugin extends SkillPlugin {
    public float triggerTime = 0.7f;

    VCharacter character;
    boolean send = false;

    public float speed = 1f;
    public float maxProcess = 1f;

    public Vector2 position = new Vector2();
    public Vector2 direction = new Vector2();

    InterruptListener listener;

    @Override
    public int version() {
        return 0;
    }

    Vector2 tmpV2 = new Vector2();
    @Override
    public void start() {
        this.character = skill.getOwner();
        this.position.x = character.position.x;
        this.position.y = character.getY();
        character.getWorld().getStage().screenToStageCoordinates(tmpV2.set(Gdx.input.getX(), Gdx.input.getY()));
        this.direction.x = tmpV2.x - character.position.x;
        this.direction.y = tmpV2.y - character.position.y;

        speed = character.getBattleComponent().actualBattleAttr.magicSpeed / WorldContext.DEFAULT_MAGIC_COEFFICIENT;
//        character.changeStatus(ActorConstants.STATUS_SPELLING_01);
        listener = Pools.obtain(InterruptListener.class);
        listener.plugin = this;
        character.getListenerComponent().add(listener);
        currentProcess = 0f;

    }

    @Override
    public void update(float delta) {
        updateProgress(delta);
        character.setHorizonVelocity(0f, 0f);
        character.interruptPathFinding();
        if (character.statusProgress >= triggerTime && !send) {
            launch();
        }
        if (isEnding()) {

            this.stop();
        }
    }

    private boolean isEnding() {
        return character.statusProgress >= maxProcess && send;
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
                .bdType(UserData.B2DType.SENSOR)
                .derivative(true)
                .build();

        TestBullet testBullet = character.getWorld().spawnVActor(TestBullet.class, helper);
        testBullet.targetGroup = WorldContext.BLACK;
        testBullet.getActualBattleAttr().moveSpeed = 500;
        testBullet.setParentVActor(character);
//        testBullet.baseMove.set(direction.x, direction.y, 0f);
        testBullet.setHorizonVelocity(direction.x, direction.y);
        testBullet.taregtCamp.set(character.taregtCamp);
        testBullet.camp.set(character.camp);

    }

    float currentProcess = 0f;
    private void updateProgress(float delta) {
        speed = character.getBattleComponent().actualBattleAttr.magicSpeed / WorldContext.DEFAULT_MAGIC_COEFFICIENT;
        currentProcess += ((delta * speed) / maxProcess);
        character.statusProgress = currentProcess;
    }

    @Override
    public float progress() {
        return currentProcess;
    }

    @Override
    public void reset() {
        super.reset();
        send = false;
        currentProcess = 0f;
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
