package com.voidvvv.game.base.skill.v2;

import com.voidvvv.game.plugin.sp.LightBoomPlugin;

public class LightBoomSkill extends PluginSkill{

    public LightBoomSkill() {
        this.pluginClass = LightBoomPlugin.class;
        this.maxCooldown = 2.f;
    }

    @Override
    protected void applyCost() {
        this.cooldown = maxCooldown;
        owner.getBattleComponent().useMp(20f);
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
        System.out.println(true);
        return true;
    }
}
