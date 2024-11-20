package com.voidvvv.game.manager.event.attack;

import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.manager.event.VWorldEventManager;
import com.voidvvv.game.manager.event.WorldEvent;
import com.voidvvv.game.utils.ReflectUtil;

public class DamageEvent extends WorldEvent {

    @Override
    public void apply() {
        VCharacter from = ReflectUtil.cast(fromActor, VCharacter.class);
        VCharacter to = ReflectUtil.cast(targetActor, VCharacter.class);

        if (from != null && to != null) {
            float attack = from.getAttack();
            float defense = to.getDefense();
            to.getActualBattleAttr().hp -= Math.max((attack - defense),0f);

            // trigger after attack for from
            VWorldEventManager vWorldEventManager = ActGame.gameInstance().getvWorldEventManager();
            AfterAttackEvent afterAttackEvent = vWorldEventManager.newEvent(AfterAttackEvent.class);
            // trigger after be attacked for to
            AfterBeAttackedEvent afterBeAttackedEvent = vWorldEventManager.newEvent(AfterBeAttackedEvent.class);

        }
    }

}