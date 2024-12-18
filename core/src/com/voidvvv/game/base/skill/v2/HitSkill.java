package com.voidvvv.game.base.skill.v2;

import com.voidvvv.game.plugin.sp.LightBoomPlugin;
import com.voidvvv.game.plugin.sp.hit.HitSkillPlugin;

public class HitSkill extends PluginSkill{
    public HitSkill() {
        this.pluginClass = HitSkillPlugin.class;
        this.maxCooldown = 2.f;
    }

    @Override
    protected void applyCost() {
        this.cooldown = maxCooldown;
        owner.getBattleComponent().useMp(20f);
    }

    @Override
    protected boolean checkPrerequisites() {
        return true;
    }

    @Override
    public void does() {
        super.does();
    }
}
