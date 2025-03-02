package com.voidvvv.game.context.machenism;

import com.badlogic.gdx.math.Vector2;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.Camp;
import com.voidvvv.game.base.Updateable;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.actors.slime.Slime;
import com.voidvvv.game.base.btree.BTManager;
import com.voidvvv.game.context.VWorld;
import com.voidvvv.game.render.actor.slime.SlimeSimpleRender;

public class SlimeGenerateMechanism implements Updateable {
    SlimeSimpleRender slimeSimpleRender;
    public VWorld vWorld;

    float stopWatch = 0f;
    @Override
    public void update(float delta) {
        // generate a slime every 5 seconds
        stopWatch += delta;
        if (stopWatch >= 5f) {
            addSlime();
        }
    }

    Vector2 coor = new Vector2();
    public void addSlime() {
        // locate coordinate
        locateCoor();
        addSlime(coor.x,coor.y);
    }

    private void locateCoor() {
        VActor protagonist = vWorld.getProtagonist();
    }

    public void addSlime(float x, float y) {
        if (slimeSimpleRender == null) {
            slimeSimpleRender = new SlimeSimpleRender();
            slimeSimpleRender.init();
        }

        Slime slime = vWorld.spawnVActor(Slime.class, x, y);
        settingForSlime(slime);
    }

    private void settingForSlime(Slime slime) {
        slime.setName("Slime");
        slime.getActualBattleAttr().moveSpeed = 30f;
        slime.getActualBattleAttr().attack = 1;
        slime.getActualBattleAttr().maxHp = 10;
        slime.getActualBattleAttr().hp = 10;
        slime.camp.campBit = Camp.NEGATIVE;
        slime.taregtCamp.campBit = Camp.POSITIVE;
        slime.render = slimeSimpleRender;
        ActGame.gameInstance().getBtManager().addTree(slime, BTManager.SLIME_SIMPLE);
    }

    public VWorld thisWorld() {
        return vWorld;
    }
}
