package com.voidvvv.game.base.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.shape.VCube;
import com.voidvvv.game.context.VActorSpawnHelper;
import com.voidvvv.game.context.WorldContext;
import com.voidvvv.game.context.input.InputActionMapping;

public class Bob extends VCharacter {

    @Override
    public void vAct(float delta) {
        super.vAct(delta);
    }

    @Override
    public void init() {
        super.init();
        // physics
        VCube cube = new VCube();
        cube.xLength = this.physicAttr.box2dHx * 2f;
        cube.yLength = this.physicAttr.box2dHy * 2f;
        cube.zLength = this.physicAttr.box2dHy * 2f;
        physicAttr.setBaseShape(cube);
        this.setPhysicAttr(physicAttr);
        this.getActualBattleAttr().hp = 100;
        setName("Bob" + MathUtils.random(10));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
//        batch.flush();
        BitmapFont baseFont = ActGame.gameInstance().getFontManager().getBaseFont();
        baseFont.setColor(Color.RED);
        ActGame.gameInstance().getDrawManager().enableBlend();
        GlyphLayout layout = Pools.obtain(GlyphLayout.class);
        layout.setText(baseFont, this.getName());
        float height = layout.height;
        float width = layout.width;
        width/=2;
        baseFont.draw(batch, layout, this.position.x - width, this.getY() + height);
        batch.flush();
        Pools.free(layout);

        ActGame.gameInstance().getDrawManager().disableBlend();
    }

    @Override
    public void useSkill(int skillCode) {
        if (skillCode == InputActionMapping.SKILL_Q) {

            VActorSpawnHelper helper = VActorSpawnHelper.builder()
                    .bodyType(BodyDef.BodyType.DynamicBody)
                    .category((short)(WorldContext.ROLE|WorldContext.WHITE)) // who am I
                    .mask((short)(WorldContext.OBSTACLE|WorldContext.BLACK|WorldContext.ROLE)) // who do I want to collision
                    .hx(5).hy(5)
                    .initX(position.x).initY(getY())
                    .build();

            TestBullet testBullet = getWorld().spawnVActor(TestBullet.class, helper);
            testBullet.targetGroup  = WorldContext.BLACK;
            testBullet.getActualBattleAttr().moveSpeed = 10000*1.5f;
            testBullet.setParentVActor(this);
            testBullet.baseMove.set(getWorld().currentPointerPose.x - position.x,getWorld().currentPointerPose.y - getY(),0);
        }
    }
}
