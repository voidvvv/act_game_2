package com.voidvvv.game.base.skill.v2;

import com.voidvvv.game.base.state.VCharactorStatus;
import com.voidvvv.game.base.state.normal.Idle;
import com.voidvvv.game.base.state.normal.Walking;
import com.voidvvv.game.plugin.sp.LightBoomPlugin;

public class LightBoomSkill extends PluginSkill{

    public LightBoomSkill() {
        this.pluginClass = LightBoomPlugin.class;
        this.maxCooldown = 2.f;
    }

    @Override
    protected void applyCost() {
        boolean b = owner.preUseSkill(this);
        if (b) {
            this.cooldown = maxCooldown;
            owner.getBattleComponent().useMp(20f);
        } else {
            throw  new RuntimeException("当前状态不允许释放");
        }
    }

    @Override
    protected boolean checkPrerequisites() {
        if (this.cooldown > 0f) {
            System.out.println("冷却没好");
            return false;
        }
        if (owner.getBattleComponent().actualBattleAttr.mp <= 20) {
            System.out.println("蓝不够");
            return false;
        }
        VCharactorStatus currentState = owner.getStateMachine().getCurrentState();
        if ((currentState != Idle.INSTANCE && currentState != Walking.INSTANCE)) {
            System.out.println("非可使用状态");
            return false;
        }
        System.out.println(owner.getBattleComponent().actualBattleAttr.mp);
        return true;
    }
}
