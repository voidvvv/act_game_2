package com.voidvvv.game.base.skill.v2;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.plugin.Plugin;
import com.voidvvv.game.plugin.SkillPlugin;


/**
 * plugin skill is for some skill that will apply plugins to character
 */
public abstract class PluginSkill extends Skill {

    public Class<? extends SkillPlugin> pluginClass;


    @Override
    public void does() {

        if (pluginClass!= null) {
            SkillPlugin sp = Pools.obtain(pluginClass);
            sp.skill = this;
            this.owner.getPluginComponent().addPlugin(sp);
        }
    }
}
