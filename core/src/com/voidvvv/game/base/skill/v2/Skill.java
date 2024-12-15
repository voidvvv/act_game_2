package com.voidvvv.game.base.skill.v2;

import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.manager.event.spell.SpellWorldEvent;
import lombok.Getter;
import lombok.Setter;

public abstract class Skill {

    public float cooldown;
    public float maxCooldown;

    public boolean shouldDoPost = false;

    @Getter
    @Setter
    protected VCharacter owner;

    public String description() {
        return "null";
    }

    public boolean use() {
        try {
            System.out.println("use");
            boolean checkPrerequisites = checkPrerequisites();
            if (!checkPrerequisites) {
                return false;
            }
            applyCost();
            SpellWorldEvent spellWorldEvent = ActGame.gameInstance().getvWorldEventManager().newEvent(currentEventClass());
            spellWorldEvent.setSkill(this);
            spellWorldEvent.setFromActor(owner);
            afterConfigEvent(spellWorldEvent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected void afterConfigEvent(SpellWorldEvent spellWorldEvent) {

    }

    public void update (float delta) {
        this.cooldown -= delta;
        if (this.cooldown <= 0) {
            this.cooldown = 0;
        }
    }

    protected Class<SpellWorldEvent> currentEventClass() {
        return SpellWorldEvent.class;
    }

    protected abstract void applyCost();

    protected abstract boolean checkPrerequisites();

    public abstract void does();
}
