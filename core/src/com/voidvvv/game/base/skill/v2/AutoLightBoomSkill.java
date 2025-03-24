package com.voidvvv.game.base.skill.v2;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.voidvvv.game.base.b2d.UserData;
import com.voidvvv.game.base.test.TestBullet;
import com.voidvvv.game.context.world.VActorSpawnHelper;
import com.voidvvv.game.context.world.WorldContext;

public class AutoLightBoomSkill extends AutoSkill{
    @Override
    protected void applyCost() {
        this.cooldown = maxCooldown;
    }
    public AutoLightBoomSkill() {
        this.maxCooldown = 2f;
    }
    @Override
    protected boolean checkPrerequisites() {
        return this.cooldown == 0f;
    }

    @Override
    public void does() {
        VActorSpawnHelper helper = VActorSpawnHelper.builder()
                .bodyType(BodyDef.BodyType.DynamicBody)
                .category((short) (WorldContext.ROLE | WorldContext.WHITE)) // who am I
                .mask((short) (WorldContext.OBSTACLE | WorldContext.BLACK | WorldContext.ROLE)) // who do I want to collision
                .hx(5).hy(1f)
                .hz(5f)
                .initX(this.owner.position.x).initY(this.owner.position.y)
                .sensor(true)
                .bdType(UserData.B2DType.SENSOR)
                .derivative(true)
                .build();

        TestBullet testBullet = owner.getWorld().spawnVActor(TestBullet.class, helper);
        testBullet.targetGroup = WorldContext.BLACK;
        testBullet.getActualBattleAttr().moveSpeed = 500;
        testBullet.setParentVActor(owner);
        float random = MathUtils.random() * MathUtils.PI2;
        float sin = MathUtils.sin(random);
        float cos = MathUtils.cos(random);

//        testBullet.baseMove.set(direction.x, direction.y, 0f);
        testBullet.setHorizonVelocity(cos, sin);
        testBullet.taregtCamp.set(owner.taregtCamp);
        testBullet.camp.set(owner.camp);
    }
}
