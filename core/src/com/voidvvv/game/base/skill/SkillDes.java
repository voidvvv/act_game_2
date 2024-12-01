package com.voidvvv.game.base.skill;


import com.voidvvv.game.base.VSkillCharacter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillDes {
    Class<? extends Skill> skillClass;
    String des;

    public void afterAdd(VSkillCharacter vSkillCharacter) {
    }

    public void afterRemove(VSkillCharacter vSkillCharacter) {

    }
}
