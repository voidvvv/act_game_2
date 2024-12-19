package com.voidvvv.game.base.skill.v2;

import com.badlogic.gdx.ai.msg.Telegram;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.state.VCharactorStatus;
import com.voidvvv.game.manager.event.spell.SpellWorldEvent;
import lombok.Getter;
import lombok.Setter;

public abstract class Skill {
    public static enum SkillType {
        A() {
            @Override
            public void applySkillType(VCharactorStatus status, VCharacter character, Telegram gram) {
                status.onA_Attack(character, gram);
            }
        },
        MAGIC_SPELL() {
            @Override
            public void applySkillType(VCharactorStatus status, VCharacter character, Telegram gram) {
                status.onMagicSpell(character, gram);
            }
        },
        PHYSIC_SPELL() {
            @Override
            public void applySkillType(VCharactorStatus status, VCharacter character, Telegram gram) {
                status.onPhysicSpell(character, gram);
            }
        },
        ROTATE () {
            @Override
            public void applySkillType(VCharactorStatus status, VCharacter character, Telegram gram) {
                status.onRotateSpell(character, gram);
            }
        },
        RANGE () {
            @Override
            public void applySkillType(VCharactorStatus status, VCharacter character, Telegram gram) {
                status.onNoopSkill(character, gram);
            }
        },
        NOOP () {
            @Override
            public void applySkillType(VCharactorStatus status, VCharacter character, Telegram gram) {
                super.applySkillType(status, character, gram);
            }
        }
        ;

        public void applySkillType(VCharactorStatus status, VCharacter character, Telegram gram) {

        }
    }
    public float cooldown;
    public float maxCooldown;

    public boolean shouldDoPost = false;

    public float spellingTime = 1f;

    public SkillType skillType() {
        return SkillType.MAGIC_SPELL;
    }

    @Getter
    @Setter
    protected VCharacter owner;

    public String description() {
        return "null";
    }

    public boolean use() {
        try {
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
